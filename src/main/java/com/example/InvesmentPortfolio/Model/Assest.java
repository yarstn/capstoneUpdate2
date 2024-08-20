package com.example.InvesmentPortfolio.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Asset name must not be empty")
    @Size(max = 50, message = "Asset name must not exceed 50 characters")
    @Column(columnDefinition = "varchar(50) not null")
    private String assetName;

    @NotEmpty(message = "Asset type must not be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String assetType;


    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(columnDefinition = "varchar(255)")
    private String description;
    @NotNull(message = "price must not be null")
    @Column(columnDefinition = "int not null")
    private int price;

    private int leftForGoal;
    private  String status;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdated;



}
