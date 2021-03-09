package ru.irmo.metopt.metopt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.irmo.metopt.metopt.domain.methods.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/1")
public class MainController {
    @GetMapping("optimize")
    public List<List<Double>> optimize(@RequestParam String selected) {
        ArrayList<List<Double>> ans = new ArrayList<>();
        Minimizer minimizer;
        double a5 = 0.1, b5 = 2.5, eps5 = 0.001;
        Function<Double, Double> f5 = x -> (10 * x * Math.log(x) - x * x / 2);
        switch (selected) {
            case ("Dichotomy") :
                minimizer = new DichotomyMinimizer(f5, a5, b5, eps5);
                break;
            case ("Golden") :
                minimizer = new GoldenMinimizer(f5, a5, b5, eps5);
                break;
            case ("Fibonacci"):
                minimizer = new FibonacciMinimizer(f5, a5, b5, eps5);
                break;
            case ("Parabolic"):
                minimizer = new ParabolicMinimizer(f5, a5, b5, eps5);
                break;
            case ("Combination"):
                minimizer = new CombinationMinimizer(f5, a5, b5, eps5);
                break;
            default:
                throw new UnsupportedOperationException("method not found");
        }
        minimizer.restart();
        Section s = new Section(minimizer.getA(), minimizer.getB());
        do {
            ans.add(List.of(s.getA(), s.getB()));
            s = minimizer.next();
        } while (s != null);
        return ans;
    }

    @GetMapping("posts")
    public List<String> findPosts() {
        return null;
    }

}