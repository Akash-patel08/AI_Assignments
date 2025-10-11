import java.util.*;

public class EightPuzzle {

    static String goal = "123456780"; // Goal state (0 = empty tile)
    static int[][] moves = { {1,0}, {-1,0}, {0,1}, {0,-1} }; // Down, Up, Right, Left

    // Display board
    static void printBoard(String state) {
        for (int i = 0; i < 9; i++) {
            System.out.print(state.charAt(i) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
        System.out.println();
    }

    // Generate all possible next states
    static List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        int zeroIndex = state.indexOf('0');
        int x = zeroIndex / 3;
        int y = zeroIndex % 3;

        for (int[] m : moves) {
            int nx = x + m[0], ny = y + m[1];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int newIndex = nx * 3 + ny;
                char[] arr = state.toCharArray();
                char temp = arr[zeroIndex];
                arr[zeroIndex] = arr[newIndex];
                arr[newIndex] = temp;
                neighbors.add(new String(arr));
            }
        }
        return neighbors;
    }

    // ---- BFS Implementation ----
    static void bfs(String start) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(Arrays.asList(start));

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String current = path.get(path.size() - 1);

            if (current.equals(goal)) {
                System.out.println("BFS Solution found in " + (path.size() - 1) + " moves.\n");
                for (String step : path) printBoard(step);
                return;
            }

            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        queue.add(newPath);
                    }
                }
            }
        }
        System.out.println("No solution found using BFS.");
    }

    // ---- DFS Implementation ----
    static void dfs(String start, int depthLimit) {
        Stack<List<String>> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(Arrays.asList(start));

        while (!stack.isEmpty()) {
            List<String> path = stack.pop();
            String current = path.get(path.size() - 1);

            if (current.equals(goal)) {
                System.out.println("DFS Solution found in " + (path.size() - 1) + " moves.\n");
                for (String step : path) printBoard(step);
                return;
            }

            if (path.size() > depthLimit) continue;

            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : getNeighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        stack.push(newPath);
                    }
                }
            }
        }
        System.out.println("No solution found using DFS (depth limit reached).");
    }

    // ---- MAIN ----
    public static void main(String[] args) {
        String start = "123406758"; // Example start state
        System.out.println("Start State:");
        printBoard(start);
        System.out.println("Goal State:");
        printBoard(goal);

        System.out.println("=== BFS SEARCH ===");
        bfs(start);

        System.out.println("=== DFS SEARCH ===");
        dfs(start, 30); // depth limit = 30
    }
}
