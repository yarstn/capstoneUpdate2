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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Content must not be empty")
    @Column(columnDefinition = "text not null")
    private String content;

    @NotNull(message = "User ID must not be null")
    @Column(columnDefinition = "int not null")
    private int userId;

    @NotNull(message = "Asset ID must not be null")
    @Column(columnDefinition = "int not null")
    private int assetId;

    @Column(columnDefinition = "int")
    private int parentCommentId; // For replies

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;
}
