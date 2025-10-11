import java.util.*;

class Edge {
    String to;
    int cost;
    Edge(String to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

class Node implements Comparable<Node> {
    String name;
    int g; // cost so far
    int f; // total cost (g + h)

    Node(String name, int g, int f) {
        this.name = name;
        this.g = g;
        this.f = f;
    }

    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}

public class AStarSearch {

    // Heuristic values
    static Map<String, Integer> heuristic = Map.of(
        "Pune", 150,
        "Expressway", 100,
        "OldHighway", 120,
        "CoastalRoad", 180,
        "Mumbai", 0
    );

    // Graph
    static Map<String, List<Edge>> graph = new HashMap<>();
    static {
        graph.put("Pune", Arrays.asList(
            new Edge("Expressway", 50),
            new Edge("OldHighway", 30),
            new Edge("CoastalRoad", 20)
        ));
        graph.put("Expressway", Arrays.asList(new Edge("Mumbai", 100)));
        graph.put("OldHighway", Arrays.asList(new Edge("Mumbai", 120)));
        graph.put("CoastalRoad", Arrays.asList(new Edge("Mumbai", 180)));
        graph.put("Mumbai", new ArrayList<>());
    }

    static List<String> aStar(String start, String goal) {
        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> closed = new HashSet<>();

        gScore.put(start, 0);
        open.add(new Node(start, 0, heuristic.get(start)));

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (current.name.equals(goal)) {
                List<String> path = new ArrayList<>();
                String node = goal;
                while (node != null) {
                    path.add(node);
                    node = parent.get(node);
                }
                Collections.reverse(path);
                System.out.println("Total cost = " + gScore.get(goal) + " km");
                return path;
            }

            if (closed.contains(current.name)) continue;
            closed.add(current.name);

            for (Edge e : graph.get(current.name)) {
                int tentativeG = gScore.get(current.name) + e.cost;
                if (!gScore.containsKey(e.to) || tentativeG < gScore.get(e.to)) {
                    gScore.put(e.to, tentativeG);
                    parent.put(e.to, current.name);
                    int f = tentativeG + heuristic.get(e.to);
                    open.add(new Node(e.to, tentativeG, f));
                }
            }
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        String start = "Pune";
        String goal = "Mumbai";

        List<String> path = aStar(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Optimal Path: " + String.join(" -> ", path));
        } else {
            System.out.println("No path found.");
        }
    }
}
