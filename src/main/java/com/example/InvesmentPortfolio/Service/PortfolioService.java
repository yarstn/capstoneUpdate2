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

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final TransactionsRepository transactionsRepository;
    private final AssestRepository assestRepository;

    public List<Portfolio> getAllPortfolio() {
        return portfolioRepository.findAll();
    }

    public void add(Portfolio portfolio) {
        User user = userRepository.findUserById(portfolio.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }
        portfolioRepository.save(portfolio);
    }

    public void updatePortfolio(Integer id, Portfolio portfolio) {
        Portfolio existingPortfolio = portfolioRepository.findByid(id);
        if (existingPortfolio == null) {
            throw new ApiException("Portfolio not found");
        }
        existingPortfolio.setPortfolioName(portfolio.getPortfolioName());
        existingPortfolio.setUserId(portfolio.getUserId());
        existingPortfolio.setDate(portfolio.getDate());
        portfolioRepository.save(existingPortfolio);
    }

    public void deletePortfolio(Integer id) {
        Portfolio portfolio = portfolioRepository.findByid(id);
        if (portfolio == null) {
            throw new ApiException("Portfolio not found");
        }
        portfolioRepository.delete(portfolio);
    }
    public Map<String, Double> calculateAssetRatios(Integer portfolioId) {
        // Get all transactions for the given portfolio
        List<Transactions> transactions = transactionsRepository.findByPortfolioId(portfolioId);

        if (transactions.isEmpty()) {
            throw new ApiException("No transactions found for this portfolio");
        }

        // Calculate the total portfolio value based on the `saving` field
        double totalPortfolioSaving = 0.0;
        Map<Integer, Double> assetSavings = new HashMap<>();
        for (Transactions transaction : transactions) {
            if ("charge".equalsIgnoreCase(transaction.getTransactionType())) {
                Assest asset = assestRepository.findByid(transaction.getAssestId());
                double assetSaving = transaction.getSaving();
                totalPortfolioSaving += assetSaving;

                assetSavings.put(asset.getId(), assetSavings.getOrDefault(asset.getId(), 0.0) + assetSaving);
            }
        }

        if (totalPortfolioSaving == 0.0) {
            throw new ApiException("No charge transactions found for this portfolio");
        }

        // Calculate the ratio of each asset's saving to the total portfolio saving
        Map<String, Double> assetRatios = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : assetSavings.entrySet()) {
            Assest asset = assestRepository.findByid(entry.getKey());
            double ratio = (entry.getValue() / totalPortfolioSaving) * 100;
            assetRatios.put(asset.getAssetName() + " saving", ratio);
        }

        return assetRatios;
    }

}