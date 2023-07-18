package com.jmatch.services;

import com.jmatch.models.Freelancer;
import com.jmatch.models.Portfolio;
import com.jmatch.models.Response;
import com.jmatch.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioService extends BaseService<Portfolio> {
    PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Response<Portfolio> crearPortfolio(Freelancer freelancer, String descripcion) {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolio(portfolio);
        portfolio.setDescripcion(descripcion);
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        if (savedPortfolio.getDescripcion().isEmpty())
            return res(true, savedPortfolio);
        else return res(false, "No se pudo agregar el portafolio");
    }

    public Response<Portfolio> getPortfolio(int idFreelancer) {
        Optional<Portfolio> portfolioFound = portfolioRepository.getPortfolioByIdFreelancer(idFreelancer);
        return portfolioFound.map(portfolio -> res(true, portfolioFound))
                .orElseGet(() -> res(false, "No se pudo obtener el portfolio"));
    }
}
