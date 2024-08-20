package com.example.InvesmentPortfolio.Controller;

import com.example.InvesmentPortfolio.Model.Problem;
import com.example.InvesmentPortfolio.Service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping("/add")
    public ResponseEntity addProblem(@Valid @RequestBody Problem problem) {
        Problem createdProblem = problemService.addProblem(problem);
        return ResponseEntity.ok(createdProblem);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getProblemsByUserId(@PathVariable int userId) {
        List<Problem> problems = problemService.getProblemsByUserId(userId);
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/admin/{userId}")
    public ResponseEntity getAllProblems(@PathVariable int userId) {
        if (userId != 1) {
            return ResponseEntity.status(403).body(null);  // Only admin (userId = 1) can access
        }
        List<Problem> problems = problemService.getAllProblems();
        return ResponseEntity.ok(problems);
    }

    @PutMapping("/{problemId}/solved")
    public ResponseEntity markProblemAsSolved(@PathVariable int problemId) {
        Problem updatedProblem = problemService.markProblemAsSolved(problemId);
        return ResponseEntity.ok(updatedProblem);
    }
}
