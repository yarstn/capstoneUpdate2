package com.example.InvesmentPortfolio.Service;

import com.example.InvesmentPortfolio.Model.Problem;
import com.example.InvesmentPortfolio.Model.User;
import com.example.InvesmentPortfolio.Repository.ProblemRepository;
import com.example.InvesmentPortfolio.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final UserRepository UserRepository;
    private final UserRepository userRepository;

    public Problem addProblem(Problem problem) {
        User user = userRepository.findUserById(problem.getUserId());
        if (user == null) {
            throw  new RuntimeException("User not found");
        }
        problem.setCreatedDate(LocalDateTime.now());
        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public List<Problem> getProblemsByUserId(Integer userId) {
        return problemRepository.findByUserId(userId);
    }

    public Problem markProblemAsSolved(Integer problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem.setStatus("solved");
        return problemRepository.save(problem);
    }
}
