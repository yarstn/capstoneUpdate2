package com.example.InvesmentPortfolio.Repository;

import com.example.InvesmentPortfolio.Model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
List<Transactions> findByUserId(int userId);

//Transactions findByAssestId(int assestId);
//    List<Transactions> findByTransactionType(String transactionType);
//    @Query("SELECT SUM(t.quantity) FROM Transactions t WHERE t.userId = :userId AND t.assestId = :assestId AND t.transactionType = 'buy'")
//    int findQuantityByUserIdAndAssestId(@Param("userId") int userId, @Param("assestId") int assestId);

List<Transactions> findByPortfolioId(int portfolioId);
}
