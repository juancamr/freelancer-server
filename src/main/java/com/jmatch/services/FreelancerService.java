package com.jmatch.services;

import com.jmatch.interfaces.UserRelated;
import com.jmatch.models.Categoria;
import com.jmatch.models.Freelancer;
import com.jmatch.models.Response;
import com.jmatch.repositories.CategoriaRepository;
import com.jmatch.repositories.FreelancerRepository;
import com.jmatch.requestModel.RegisterRequest;
import com.jmatch.requestModel.UpdateProfileRequest;
import com.jmatch.utils.Const;
import com.jmatch.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreelancerService extends BaseService<Freelancer> {

    private final FreelancerRepository freelancerRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public FreelancerService(FreelancerRepository freelancerRepository, CategoriaRepository categoriaRepository) {
        this.freelancerRepository = freelancerRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Response<Freelancer> getFreelancerById(int id) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findById((long) id);
        return freelancerFound.map(freelancer -> res(true, freelancer)).orElseGet(() -> res(false, "No se pudo obtener el freelancer"));
    }

    public Response<Freelancer> loginService(String username, String password) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findByUsername(username);
        if (freelancerFound.isPresent()) {
            if (Utils.comparePassword(password, freelancerFound.get().getPasswd()))
                return res(true, freelancerFound.get());
            else return res(false, Const.Errors.INCORRECT_PASSWORD);
        } else return res(false, Const.Errors.USER_NOT_FOUND);
    }

    public Response<Freelancer> registerService(RegisterRequest registerRequest) {
        Optional<Freelancer> freelancerFoundByUsername = freelancerRepository.findByUsername(registerRequest.getUsername());
        if (freelancerFoundByUsername.isPresent()) {
            return res(false, Const.Errors.USER_ALREADY_EXIST);
        } else {
            Optional<Freelancer> freelancerFoundByEmail = freelancerRepository.findByEmail(registerRequest.getCorreo());
            if (freelancerFoundByEmail.isPresent()) {
                return res(false, Const.Errors.EMAIL_ALREADY_USED);
            } else {
                UserRelated savedFreelancer = freelancerRepository.save(new Freelancer.Builder()
                        .username(registerRequest.getUsername())
                        .nombre((registerRequest.getNombres()))
                        .apellido(registerRequest.getApellidos())
                        .correo(registerRequest.getCorreo())
                        .passwd(Utils.encryptPassword(registerRequest.getPassword()))
                        .build());
                if (savedFreelancer.getUsername().isEmpty()) return res(false, Const.Errors.SOMETHING_WENT_WRONG);
                else return res(true, savedFreelancer);
            }
        }
    }

    public Response<Freelancer> assignCategory(int idFreelancer, int idCategoria) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findById((long) idFreelancer);
        if (freelancerFound.isPresent()) {
            Optional<Categoria> categoryFound = categoriaRepository.findById((long) idCategoria);
            if (categoryFound.isPresent()) {
                Freelancer freelancer = freelancerFound.get();
                freelancer.setCategoria(categoryFound.get());
                Freelancer savedFreelancer = freelancerRepository.save(freelancer);
                if (savedFreelancer.getCategoria().getNombre().isEmpty())
                    return res(false, Const.Errors.SOMETHING_WENT_WRONG);
                else return res(true);
            } else return res(false, "La categoria no existe");
        } else return res(false, Const.Errors.USER_NOT_FOUND);

    }

    public Response<Freelancer> getAllForUsers() {
        List<?> freelancers = freelancerRepository.findAllForUsers();
        return res(true, freelancers);
    }

    public Response<Freelancer> getFreelancer(int id) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findById((long) id);
        return freelancerFound.map(freelancer -> res(true, freelancer)).orElseGet(() -> res(false, "El freelancer no existe"));
    }

    public Response<Freelancer> updateFreelancerProfile(UpdateProfileRequest updateProfileRequest) {
        long id = updateProfileRequest.getId();
        Optional<Freelancer> freelancerFound = freelancerRepository.findById(id);
        if (freelancerFound.isPresent()) {
            Freelancer savedFreelancer = freelancerRepository.save(freelancerFound.get());
            if (savedFreelancer.getId() != 0) return res(true);
            else return res(false, Const.Errors.SOMETHING_WENT_WRONG);
        } else return res(false, Const.Errors.USER_NOT_FOUND);
    }

    public boolean isTheirPassword(String password, int idFreelancer) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findById((long) idFreelancer);
        return freelancerFound.map(freelancer -> Utils.comparePassword(password, freelancerFound.get().getPasswd())).orElseGet(() -> false);
    }

    public Response<Freelancer> changePassword(String password, int idFreelancer) {
        Optional<Freelancer> freelancerFound = freelancerRepository.findById((long) idFreelancer);
        if (freelancerFound.isPresent()) {
            Freelancer freelancer = freelancerFound.get();
            freelancer.setPasswd(Utils.encryptPassword(password));
            freelancerRepository.save(freelancer);
            return res(true);
        } else return res(false, Const.Errors.USER_NOT_FOUND);

    }
}
