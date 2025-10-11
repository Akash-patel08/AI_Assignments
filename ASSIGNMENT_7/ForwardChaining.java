import java.util.*;

class Rule {
    List<String> premises; // Conditions
    String conclusion;     // Inferred fact

    Rule(List<String> premises, String conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }
}

public class ForwardChaining {

    public static void main(String[] args) {
        // Initial Facts
        Set<String> facts = new HashSet<>();
        facts.add("Fever");
        facts.add("Cough");

        // Rules: If premises satisfied, add conclusion
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(Arrays.asList("Fever", "Cough"), "Flu"));
        rules.add(new Rule(Arrays.asList("Flu"), "TakeMedicine"));
        rules.add(new Rule(Arrays.asList("Cough"), "DrinkWater"));
        rules.add(new Rule(Arrays.asList("Headache"), "TakeRest"));

        System.out.println("Initial Facts: " + facts);
        System.out.println("Rules:");
        for (Rule r : rules) {
            System.out.println("IF " + r.premises + " THEN " + r.conclusion);
        }

        // Forward Chaining Inference
        boolean newFactAdded;
        do {
            newFactAdded = false;
            for (Rule r : rules) {
                if (facts.containsAll(r.premises) && !facts.contains(r.conclusion)) {
                    facts.add(r.conclusion);
                    System.out.println("Inferred: " + r.conclusion);
                    newFactAdded = true;
                }
            }
        } while (newFactAdded);

        System.out.println("\nFinal Facts after Forward Chaining: " + facts);
    }
}
