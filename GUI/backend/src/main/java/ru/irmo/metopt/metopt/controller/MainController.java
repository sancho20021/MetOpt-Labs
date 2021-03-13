package ru.irmo.metopt.metopt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.irmo.metopt.metopt.domain.methods.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/1")
public class MainController {
    private static final double a5 = 0.1;
    private static final double b5 = 2.5;
    private static final double eps5 = 0.001;
    private static final Function<Double, Double> f5 = x -> (10 * x * Math.log(x) - x * x / 2);

    @GetMapping("optimize")
    public List<List<Double>> optimize(@RequestParam String selected) {
        ArrayList<List<Double>> ans = new ArrayList<>();
        Minimizer minimizer;
        switch (selected) {
            case ("Dichotomy"):
                minimizer = new DichotomyMinimizer(f5, a5, b5, eps5);
                break;
            case ("Golden"):
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
            ans.add(List.of(s.getA(), s.getB(), minimizer.getCurrentXMin()));
            s = minimizer.next();
        } while (s != null);
        return ans;
    }

    @GetMapping("parabola")
    public List<String> parabolaParams() {
        ParabolicMinimizer minimizer = new ParabolicMinimizer(f5, a5, b5, eps5);
        minimizer.restart();
        List<String> ans = new ArrayList<>();
        do {
            ans.add(minimizer.getCurrentParabolaJS());
        } while (minimizer.next() != null);
        return ans;
    }

    @GetMapping("posts")
    public List<String> findPosts() {
        return null;
    }

}