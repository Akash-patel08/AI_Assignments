import java.util.*;

class Rule {
    List<String> premises;
    String conclusion;

    Rule(List<String> premises, String conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }
}

public class BackwardChaining {

    static boolean backwardChain(String goal, Set<String> facts, List<Rule> rules, Set<String> visited) {
        // Avoid infinite recursion
        if (visited.contains(goal)) return false;
        visited.add(goal);

        // If goal is already a known fact
        if (facts.contains(goal)) {
            System.out.println("Goal '" + goal + "' is a known fact.");
            return true;
        }

        // Check rules that can infer the goal
        for (Rule r : rules) {
            if (r.conclusion.equals(goal)) {
                boolean allPremisesTrue = true;
                for (String premise : r.premises) {
                    if (!backwardChain(premise, facts, rules, visited)) {
                        allPremisesTrue = false;
                        break;
                    }
                }
                if (allPremisesTrue) {
                    System.out.println("Goal '" + goal + "' inferred using rule: IF " + r.premises + " THEN " + r.conclusion);
                    facts.add(goal); // Add to facts
                    return true;
                }
            }
        }

        // Goal cannot be proven
        return false;
    }

    public static void main(String[] args) {
        // Known facts
        Set<String> facts = new HashSet<>();
        facts.add("Fever");
        facts.add("Cough");

        // Rules
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(Arrays.asList("Fever", "Cough"), "Flu"));
        rules.add(new Rule(Arrays.asList("Flu"), "TakeMedicine"));
        rules.add(new Rule(Arrays.asList("Cough"), "DrinkWater"));
        rules.add(new Rule(Arrays.asList("Headache"), "TakeRest"));

        // Goal to prove
        String goal = "TakeMedicine";

        System.out.println("Backward Chaining to prove goal: " + goal);
        boolean result = backwardChain(goal, facts, rules, new HashSet<>());

        if (result) {
            System.out.println("\nGoal '" + goal + "' is TRUE.");
        } else {
            System.out.println("\nGoal '" + goal + "' cannot be proven.");
        }

        System.out.println("Final Facts: " + facts);
    }
}
