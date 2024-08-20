package com.example.InvesmentPortfolio.Service;

import com.example.InvesmentPortfolio.Api.ApiException;
import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Model.Portfolio;
import com.example.InvesmentPortfolio.Model.Transactions;
import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Repository.AssestRepository;
import com.example.InvesmentPortfolio.Repository.PortfolioRepository;
import com.example.InvesmentPortfolio.Repository.TransactionsRepository;
import com.example.InvesmentPortfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final PortfolioRepository portfolioRepository;
    private final AssestRepository assestRepository;
    private final UserRepository userRepository;

    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public void addTransaction(Transactions transactions) {
        Portfolio portfolio = portfolioRepository.findByid(transactions.getPortfolioId());
        Assest assest = assestRepository.findByid(transactions.getAssestId());
        if (portfolio == null || assest == null) {
            throw new ApiException("Portfolio or Asset not found");
        }

        User user = userRepository.findUserById(transactions.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (user.isBlocked()) {
            throw new RuntimeException("User is blocked and cannot perform transactions");
        }

        if ("charge".equalsIgnoreCase(transactions.getTransactionType())) {
            if (transactions.getSaving() > user.getBalance()) {
                throw new ApiException("Insufficient balance");
            }

//            // Ensure that the saving amount does not exceed the leftForGoal amount
//            if (transactions.getSaving() > assest.getLeftForGoal()) {
//                throw new ApiException("Saving exceeds the goal amount");
//            }

            user.setBalance(user.getBalance() - transactions.getSaving());
            assest.setLeftForGoal(assest.getPrice() - transactions.getSaving());

            System.out.println("User balance after charge: " + user.getBalance());  // Debugging line
            System.out.println("Left for goal after charge: " + assest.getLeftForGoal());  // Debugging line

        } else if ("complete".equalsIgnoreCase(transactions.getTransactionType())) {
            if (assest.getLeftForGoal() != 0 ) {
                throw new ApiException("Goal not completed yet");
            }

            // Return the total saved amount to the user's balance
            int amountToReturn = assest.getPrice();
            user.setBalance(user.getBalance() + amountToReturn);

            System.out.println("Returning amount to user: " + amountToReturn);  // Debugging line
            System.out.println("User balance after goal completion: " + user.getBalance());  // Debugging line

            assest.setStatus("complete");
            assest.setLeftForGoal(0); // Ensure it's set to zero after completion
        }

        userRepository.save(user);
        assestRepository.save(assest);
        transactionsRepository.save(transactions);
    }


    public List<Transactions> getChargeOrCompleteTransactions(int userId) {
        List<Transactions> allTransactions = transactionsRepository.findByUserId(userId);
        List<Transactions> filteredTransactions = new ArrayList<>();

        for (Transactions transaction : allTransactions) {
            if ("charge".equalsIgnoreCase(transaction.getTransactionType()) ||
                    "complete".equalsIgnoreCase(transaction.getTransactionType())) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }



//    public void updateTransaction(Integer id,Transactions transactions) {
//        Transactions transaction = transactionsRepository.findByTransactionId(id);
//        if (transaction == null) {
//            throw new ApiException("Transaction not found");
//        }
//        transaction.setAssestId(transactions.getAssestId());
//        transaction.setPrice(transactions.getPrice());
//        transaction.setQuantity(transactions.getQuantity());
//        transaction.setPortfolioId(transactions.getPortfolioId());
//        transactionsRepository.save(transaction);
//    }
//    public void deleteTransaction(Integer id) {
//        Transactions transaction = transactionsRepository.findByTransactionId(id);
//        if (transaction == null) {
//            throw new ApiException("Transaction not found");
//        }
//        transactionsRepository.delete(transaction);
//    }

    public Map<String, Object> getTransactionReport(int userId) {
        List<Transactions> transactions = transactionsRepository.findByUserId(userId);
        Assest assest = assestRepository.findByid(transactions.get(0).getAssestId());

        int allSaving = 0;
        int completedCount = 0;
        List<Map<String, Object>> detailedTransactions = new ArrayList<>();

        for (Transactions transaction : transactions) {
            Map<String, Object> transactionDetails = new HashMap<>();
            transactionDetails.put("userId", transaction.getUserId());
            transactionDetails.put("portfolioId", transaction.getPortfolioId());
            transactionDetails.put("assestId", transaction.getAssestId());
            transactionDetails.put("transactionType", transaction.getTransactionType());
            transactionDetails.put("saving", transaction.getSaving());
            transactionDetails.put("date", transaction.getDate());

            if ("charge".equalsIgnoreCase(transaction.getTransactionType())) {
                allSaving += transaction.getSaving();
            } else if ("complete".equalsIgnoreCase(transaction.getTransactionType())) {
                completedCount++;
            }

            detailedTransactions.add(transactionDetails);
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("all saving", allSaving);
        summary.put("completed", completedCount);

        Map<String, Object> report = new HashMap<>();
        report.put("summary", summary);
        report.put("detailedTransactions", detailedTransactions);

        return report;
    }

}
