package com.example.InvesmentPortfolio.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotEmpty
    @Column(columnDefinition = "varchar(255) not null")
    private String description;

    @NotNull
    @Column(columnDefinition = "varchar(20) not null")
    private String status;  //  "open", "solved"

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;
}
