package com.example.InvesmentPortfolio.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 5, message = "Username must be longer than 4 characters")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be null or empty")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotNull(message = "Balance must not be null")
    @Column(columnDefinition = "int not null")
    private int balance;

    @NotNull(message = "Registration date must not be null")
    @Column(columnDefinition = "date not null")
    private LocalDate registrationDate;
//    @ElementCollection
//    private List<Integer> watchlist;
    @Column(columnDefinition = "boolean default false")
    private boolean blocked;
}
