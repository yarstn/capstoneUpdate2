package com.example.InvesmentPortfolio.Service;

import com.example.InvesmentPortfolio.Api.ApiException;
import com.example.InvesmentPortfolio.Model.Assest;
import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Repository.AssestRepository;
import com.example.InvesmentPortfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AssestRepository assestRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void add(User user) {
        user.setRegistrationDate(LocalDate.now());
        userRepository.save(user);
    }
    public void update(Integer id, User user) {
        User existingUser = userRepository.findUserById(id);
        if (existingUser == null) {
            throw new ApiException("No user found");
        }
        user.setId(existingUser.getId());
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setBalance(user.getBalance());
        userRepository.save(user);
    }

    public void delete(Integer id) {
        User user = userRepository.findUserById(id);
        if(user == null) {
            throw  new ApiException("no user found");
        }
        userRepository.delete(user);
    }
    //WISHlIST
//        public List<String> getWatchlist(Integer userId) {
//            User user = userRepository.findUserById(userId);
//            if (user == null) {
//                throw new ApiException("No user found");
//            }
//            // Fetch asset names based on IDs in the watchlist
//            return user.getWatchlist().stream()
//                    .map(assetId -> {
//                        Assest asset = assestRepository.findByid(assetId);
//                        return asset != null ? asset.getAssetName() : null;
//                    })
//                    .collect(Collectors.toList());
//        }

//    public String addToWatchlist(Integer userId, Integer assetId) {
//        User user = userRepository.findUserById(userId);
//        Assest asset = assestRepository.findByid(assetId);
//
//        if (user == null || asset == null) {
//            throw new ApiException("No user or asset found");
//        }
//        // Check if the asset is already in the user's watchlist
//        if (user.getWatchlist().contains(assetId)) {
//            return "Asset is already added to the watchlist";
//        }
//        user.getWatchlist().add(assetId);
//        userRepository.save(user);
//        return asset.getAssetName(); // Return the asset name after adding to wishhlist
//    }
//    public void removeFromWatchlist(Integer userId, Integer assetId) {
//        User user = userRepository.findUserById(userId);
//        if (user == null) {
//            throw new ApiException("No user found");
//        }
//        user.getWatchlist().remove(assetId);
//        userRepository.save(user);
//    }
//    public void clearWatchlist(Integer userId) {
//        User user = userRepository.findUserById(userId);
//        if (user == null) {
//            throw new ApiException("No user found");
//        }
//        user.getWatchlist().clear();
//        userRepository.save(user);
//    }
    //BLOCK
    public void blockUser(int id1, int id2) {
        // Check if id1 is the admin
        if (id1 != 1) {
            throw new RuntimeException("Only admin can block users");
        }
        // Find the user to block
        User userToBlock = userRepository.findById(id2).orElseThrow(() -> new RuntimeException("User with ID " + id2 + " not found"));
        // Block the user
        userToBlock.setBlocked(true);
        userRepository.save(userToBlock);
    }
    //Call the support
    public void unblockUser(int id1, int id2) {
        if (id1 != 1) {
            throw new RuntimeException("Only admin can unblock users");
        }
        User userUnBlock = userRepository.findById(id2).orElseThrow(() -> new RuntimeException("User with ID " + id2 + " not found"));
        userUnBlock.setBlocked(false);
        userRepository.save(userUnBlock);
    }
}
