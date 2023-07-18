package com.jmatch.controllers;

import com.jmatch.models.Freelancer;
import com.jmatch.models.Portfolio;
import com.jmatch.services.FreelancerService;
import com.jmatch.services.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jmatch.models.Response;

import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController extends BaseController {

    PortfolioService portfolioService;
    FreelancerService freelancerService;
    public PortfolioController(PortfolioService portfolioService, FreelancerService freelancerService) {
        this.portfolioService = portfolioService;
        this.freelancerService = freelancerService;
    }
    @PostMapping("/create")
    ResponseEntity<?> createPortfolio(@RequestBody Map<String, Object> requestBody) {
        int idFreelancer = Integer.parseInt(requestBody.get("freelancer").toString());
        String descripcion = requestBody.get("descripcion").toString();
        Response<Freelancer> resFreelancer = freelancerService.getFreelancer(idFreelancer);
        if (resFreelancer.isSuccess()) {
            if (idFreelancer != 0 && !descripcion.isEmpty()) {
                Response<Portfolio> res = portfolioService.crearPortfolio(resFreelancer.getData(), descripcion);
                if (res.isSuccess()) return created(successJson().putPOJO("portfolio", res.getData()));
                else return unprocessable(res.getError());
            } else return insuficientParams();
        } else return badRequest(resFreelancer.getError());
    }

    @GetMapping("/get")
    ResponseEntity<?> getPortfolio(@RequestBody Map<String, Integer> requestBody) {
        int idFreelancer = requestBody.get("id");
        if (idFreelancer != 0) {
            Response<Portfolio> res = portfolioService.getPortfolio(idFreelancer);
            if (res.isSuccess()) return res(successJson().putPOJO("portfolio", res.getData()));
            else return unprocessable(res.getError());
        } else return insuficientParams();
    }
}
