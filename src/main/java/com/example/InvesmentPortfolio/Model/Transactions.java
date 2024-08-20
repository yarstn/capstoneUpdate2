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
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID must not be null")
    @Column(columnDefinition = "int not null")
    private int userId;

    @NotNull(message = "Portfolio ID must not be null")
    @Column(columnDefinition = "int not null")
    private int portfolioId;

    @NotNull(message = "Asset ID must not be null")
    @Column(columnDefinition = "int not null")
    private int assestId;

    @NotEmpty(message = "Transaction type must not be empty")
    @Pattern(regexp = "charge|complete", message = "Transaction type must be 'complete' or 'charge' ")
    @Column(columnDefinition = "varchar(10) not null")
    private String transactionType;
    @NotNull(message = "saving must not be null")
    @Column(columnDefinition = "int not null")
private int saving;
    @NotNull(message = "Date must not be null")
    @Column(columnDefinition = "DATE not null")
    private Date date;
}
