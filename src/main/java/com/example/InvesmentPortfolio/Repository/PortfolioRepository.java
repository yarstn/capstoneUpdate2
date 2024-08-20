package com.example.InvesmentPortfolio.Repository;

import com.example.InvesmentPortfolio.Model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface  PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Portfolio findByid(Integer id);
    List<Portfolio> findByuserId(int userId);

}
