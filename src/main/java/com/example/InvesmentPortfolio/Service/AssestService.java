package com.example.InvesmentPortfolio.Service;

import com.example.InvesmentPortfolio.Api.ApiException;
import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Repository.AssestRepository;
import com.example.InvesmentPortfolio.Repository.TransactionsRepository;
import com.example.InvesmentPortfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AssestService {
    private final List<Map<String, String>> trends = new ArrayList<>();
    private final AssestRepository assestRepository;
    private final UserRepository userRepository;
    private final TransactionsRepository transactionsRepository;

    public List<Assest> getAllAssest() {
        return assestRepository.findAll();

    }
//DONE
    public void addAssest(int id, Assest assest) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        assestRepository.save(assest);
        assest.setLeftForGoal(assest.getPrice());

    }
//DONE
    public void updateAssest(Integer id,Assest assest) {
        Assest isUpdated = assestRepository.findByid(id);
        if(isUpdated == null) {
            throw new ApiException("assest not found");
        }
        isUpdated.setAssetName(assest.getAssetName());
        isUpdated.setAssetType(assest.getAssetType());
        isUpdated.setDescription(assest.getDescription());
        assestRepository.save(isUpdated);
    }

    //DONE
    public void deleteAssest(Integer id) {
        Assest assest = assestRepository.findByid(id);
        if(assest == null) {
            throw new ApiException("assest not found");
        }
        assestRepository.delete(assest);
    }

    //compare 2 assest
    public String compareAssets(Integer assetId1, Integer assetId2) {
        Assest asset1 = assestRepository.findByid(assetId1);
        Assest asset2 = assestRepository.findByid(assetId2);

        if (asset1 == null || asset2 == null) {
            throw new ApiException("One or both assets not found");
        }

        // Compare assets based on some criteria, for example, their prices
        String comparison = "Comparison between " + asset1.getAssetName() + " and " + asset2.getAssetName() + ":\n";
        comparison += asset1.getAssetName() + " Price: " + asset1.getPrice() + "\n";
        comparison += asset2.getAssetName() + " Price: " + asset2.getPrice() + "\n";

        if (asset1.getPrice() > asset2.getPrice()) {
            comparison += asset1.getAssetName() + " is more expensive. so need to save more for it";
        } else if (asset1.getPrice() < asset2.getPrice()) {
            comparison += asset2.getAssetName() + " is more expensive. so need to save more for it";
        } else {
            comparison += "Both assets have the same price you can save same amount every moth.";
        }

        return comparison;
    }



    public void postMarketTrends(List<Map<String, String>> newTrends) {
        trends.clear(); // Replace existing trends with new ones
        trends.addAll(newTrends);
    }

    public List<Map<String, String>> getMarketTrends() {
        return trends;
    }


}
