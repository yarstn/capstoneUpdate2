package com.example.InvesmentPortfolio.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID must not be null")
    @Column(columnDefinition = "int not null")
    private int userId;

    @NotEmpty(message = "Portfolio name must not be empty")
    @Size(max = 50, message = "Portfolio name must not exceed 50 characters")
    @Column(columnDefinition = "varchar(50) not null")
    private String portfolioName;

    @NotNull(message = "Date must not be null")
    @Column(columnDefinition = "DATE not null")
    private Date date;
}
