import java.util.*;

public class color_mapping {

    static List<String> regions = new ArrayList<>();
    static Map<String, List<String>> adjacency = new HashMap<>();
    static List<String> colors = new ArrayList<>();

    // Check if assigning a color is valid
    static boolean isValid(Map<String, String> assignment, String region, String color) {
        for (String neighbor : adjacency.get(region)) {
            if (assignment.get(neighbor) != null && assignment.get(neighbor).equals(color)) {
                return false; // Neighbor has same color
            }
        }
        return true;
    }

    // Backtracking function
    static boolean solveColoring(int index, Map<String, String> assignment) {
        if (index == regions.size()) {
            return true; // All regions assigned
        }

        String region = regions.get(index);
        for (String color : colors) {
            if (isValid(assignment, region, color)) {
                assignment.put(region, color);
                if (solveColoring(index + 1, assignment)) {
                    return true;
                }
                assignment.put(region, null); // Backtrack
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input regions
        System.out.print("Enter number of regions: ");
        int n = sc.nextInt();
        System.out.println("Enter region names:");
        for (int i = 0; i < n; i++) {
            regions.add(sc.next());
        }

        // Input colors
        System.out.print("Enter number of colors: ");
        int m = sc.nextInt();
        System.out.println("Enter color names:");
        for (int i = 0; i < m; i++) {
            colors.add(sc.next());
        }

        // Input adjacency list
        System.out.println("Enter adjacency list (neighbors for each region):");
        for (String region : regions) {
            System.out.print("Number of neighbors of " + region + ": ");
            int k = sc.nextInt();
            List<String> neighbors = new ArrayList<>();
            System.out.println("Enter neighbors of " + region + ":");
            for (int j = 0; j < k; j++) {
                neighbors.add(sc.next());
            }
            adjacency.put(region, neighbors);
        }

        // Initialize assignment
        Map<String, String> assignment = new HashMap<>();
        for (String r : regions) {
            assignment.put(r, null);
        }

        // Solve
        if (solveColoring(0, assignment)) {
            System.out.println("\nSolution found:");
            for (String r : regions) {
                System.out.println(r + " -> " + assignment.get(r));
            }
        } else {
            System.out.println("\nNo solution exists.");
        }

        sc.close();
    }
}
