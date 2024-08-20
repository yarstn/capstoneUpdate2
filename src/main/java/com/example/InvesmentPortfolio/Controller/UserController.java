package com.example.InvesmentPortfolio.Controller;

import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user) {
        userService.add(user);
        return ResponseEntity.status(200).body("user added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @Valid @RequestBody User user) {

        userService.update(id, user);
        return ResponseEntity.status(200).body("User updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.status(200).body("user deleted successfully");
    }
//
//    @GetMapping("/{userId}")
//    public List<String> getWatchlist(@PathVariable Integer userId) {
//        return userService.getWatchlist(userId);
//    }

//    @PostMapping("/{userId}/add/{assetId}")
//    public ResponseEntity addToWatchlist(@PathVariable Integer userId, @PathVariable Integer assetId) {
//        userService.addToWatchlist(userId, assetId);
//        return ResponseEntity.status(200).body("Asset added successfully to WishList");
//    }
//
//    @DeleteMapping("/{userId}/remove/{assetId}")
//    public ResponseEntity removeFromWatchlist(@PathVariable Integer userId, @PathVariable Integer assetId) {
//        userService.removeFromWatchlist(userId, assetId);
//        return ResponseEntity.status(200).body("Asset removed successfully From WishList" );
//    }
//
//    @DeleteMapping("/{userId}/clear")
//    public ResponseEntity clearWatchlist(@PathVariable Integer userId) {
//        userService.clearWatchlist(userId);
//        return ResponseEntity.status(200).body("Watchlist cleared successfully ");
//    }
    //BLOCK
    @PutMapping("/{id1}/block/{id2}")
    public ResponseEntity blockUser(@PathVariable int id1, @PathVariable int id2) {
            userService.blockUser(id1, id2);
            return ResponseEntity.ok("User successfully blocked");
    }
    //UNBLOCK
    @PutMapping("/{id1}/unblock/{id2}")
    public ResponseEntity unblockUser(@PathVariable int id1, @PathVariable int id2) {
        userService.unblockUser(id1, id2);
        return ResponseEntity.ok("User successfully unblocked");
    }

}