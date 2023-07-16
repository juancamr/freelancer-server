package com.jmatch.services;

import com.jmatch.interfaces.UserRelated;
import com.jmatch.models.Response;
import com.jmatch.models.User;
import com.jmatch.repositories.UserRepository;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.utils.Const;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService extends BaseService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response<User> loginService(String username, String password) {
        Optional<User> userFound = userRepository.findByUsername(username);
        if (userFound.isPresent()) {
            UserRelated user = userFound.get();
            if (Utils.comparePassword(password, user.getPasswd()))
                return res(true, user);
            else
                return res(false, Const.Errors.INCORRECT_PASSWORD);
        } else {
            return res(false, Const.Errors.USER_NOT_FOUND);
        }
    }

    public Response<User> registerService(RegisterRequest data) {
        Optional<User> userFoundByUsername = userRepository.findByUsername(data.getUsername());
        if (userFoundByUsername.isPresent())
            return res(false, Const.Errors.USER_ALREADY_EXIST);
        else {
            Optional<User> userFoundByEmail = userRepository.findByEmail(data.getCorreo());
            if (userFoundByEmail.isPresent()) {
                return res(false, Const.Errors.EMAIL_ALREADY_USED);
            } else {
                UserRelated savedUser = userRepository.save(new User.Builder()
                        .nombre(data.getNombres())
                        .apellido(data.getApellidos())
                        .username(data.getUsername())
                        .correo(data.getCorreo())
                        .passwd(Utils.encryptPassword(data.getPassword()))
                        .build());
                if (savedUser.getNombre() == null) {
                    return res(false, Const.Errors.SOMETHING_WENT_WRONG);
                } else {
                    return res(true, savedUser);
                }
            }
        }
    }
}
