package com.jmatch.services;

import com.jmatch.interfaces.UserRelated;
import com.jmatch.models.Project;
import com.jmatch.models.Response;
import com.jmatch.models.User;
import com.jmatch.repositories.UserRepository;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.requestModel.UpdateProfileRequest;
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
            if (Utils.comparePassword(password, userFound.get().getPasswd()))
                return res(true, userFound.get());
            else return res(false, Const.Errors.INCORRECT_PASSWORD);
        } else return res(false, Const.Errors.USER_NOT_FOUND);
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

    public Response<User> updateFreelancerProfile(UpdateProfileRequest updateProfileRequest) {
        long id = updateProfileRequest.getId();
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isPresent()) {
            User savedUser = userRepository.save(userFound.get());
            if (savedUser.getId() != 0) return res(true);
            else return res(false, Const.Errors.SOMETHING_WENT_WRONG);
        } else return res(false, Const.Errors.USER_NOT_FOUND);
    }

    public boolean isTheirPassword(String password, int idUser) {
        Optional<User> userFound = userRepository.findById((long) idUser);
        return userFound.map(user ->
                        Utils.comparePassword(password, userFound.get().getPasswd()))
                .orElseGet(() -> false);
    }

    public Response<User> changePassword(String password, int idUser) {
        Optional<User> userFound = userRepository.findById((long) idUser);
        if (userFound.isPresent()) {
            User user = userFound.get();
            user.setPasswd(Utils.encryptPassword(password));
            userRepository.save(user);
            return res(true);
        } else return res(false, Const.Errors.USER_NOT_FOUND);

    }
}
