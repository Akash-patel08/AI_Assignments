import java.util.*;

public class FamilyTreeParser {

    // Knowledge Base
    static Map<String, List<String>> father = new HashMap<>();
    static Map<String, List<String>> mother = new HashMap<>();

    // --- Add Facts ---
    static void addFather(String f, String c) {
        father.computeIfAbsent(f, k -> new ArrayList<>()).add(c);
    }

    static void addMother(String m, String c) {
        mother.computeIfAbsent(m, k -> new ArrayList<>()).add(c);
    }

    // --- Rules ---
    // Parent Rule: returns list of children
    static List<String> getChildren(String p) {
        List<String> result = new ArrayList<>();
        if (father.containsKey(p)) result.addAll(father.get(p));
        if (mother.containsKey(p)) result.addAll(mother.get(p));
        return result;
    }

    // Sibling Rule
    static List<String> getSiblings(String name) {
        List<String> result = new ArrayList<>();

        // check in father map
        for (Map.Entry<String, List<String>> entry : father.entrySet()) {
            List<String> children = entry.getValue();
            if (children.contains(name)) {
                for (String c : children) {
                    if (!c.equals(name) && !result.contains(c))
                        result.add(c);
                }
            }
        }

        // check in mother map
        for (Map.Entry<String, List<String>> entry : mother.entrySet()) {
            List<String> children = entry.getValue();
            if (children.contains(name)) {
                for (String c : children) {
                    if (!c.equals(name) && !result.contains(c))
                        result.add(c);
                }
            }
        }

        return result;
    }

    // Grandparent Rule
    static List<String> getGrandchildren(String gp) {
        List<String> result = new ArrayList<>();

        if (father.containsKey(gp)) {
            for (String child : father.get(gp)) {
                result.addAll(getChildren(child));
            }
        }
        if (mother.containsKey(gp)) {
            for (String child : mother.get(gp)) {
                result.addAll(getChildren(child));
            }
        }

        return result;
    }

    // --- Main ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- FACTS ---
        addFather("Mohan", "Ram");
        addFather("Mohan", "Shyam");
        addFather("Mohan", "Gita");
        addMother("Sita", "Ram");
        addMother("Sita", "Shyam");
        addMother("Sita", "Gita");
        addFather("Shyam", "Anu");
        addFather("Shyam", "Raju");

        System.out.println("Family Tree Knowledge Base Parser");
        System.out.println("----------------------------------");
        System.out.println("Queries Supported:");
        System.out.println("1. children <name>");
        System.out.println("2. siblings <name>");
        System.out.println("3. grandchildren <name>");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("Enter query: ");
            String query = sc.next();
            if (query.equalsIgnoreCase("exit"))
                break;
            String name = sc.next();

            if (query.equalsIgnoreCase("children")) {
                List<String> res = getChildren(name);
                if (res.isEmpty())
                    System.out.println(name + " has no children.");
                else
                    System.out.println("Children of " + name + ": " + String.join(" ", res));
            }

            else if (query.equalsIgnoreCase("siblings")) {
                List<String> res = getSiblings(name);
                if (res.isEmpty())
                    System.out.println(name + " has no siblings.");
                else
                    System.out.println("Siblings of " + name + ": " + String.join(" ", res));
            }

            else if (query.equalsIgnoreCase("grandchildren")) {
                List<String> res = getGrandchildren(name);
                if (res.isEmpty())
                    System.out.println(name + " has no grandchildren.");
                else
                    System.out.println("Grandchildren of " + name + ": " + String.join(" ", res));
            }

            else {
                System.out.println("Unknown query type!");
            }
        }

        sc.close();
    }
}
