package com.example.InvesmentPortfolio.Repository;

import com.example.InvesmentPortfolio.Model.Assest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AssestRepository extends JpaRepository<Assest, Integer> {
    Assest findByid(Integer id);
    //TREND


}
