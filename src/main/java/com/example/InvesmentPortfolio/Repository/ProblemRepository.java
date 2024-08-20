package com.example.InvesmentPortfolio.Repository;

import com.example.InvesmentPortfolio.Model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    List<Problem> findByUserId(Integer userId);
}
