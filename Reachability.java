import java.util.*;

public class Reachability {
    HashSet<Integer> visited;
    HashMap<Integer,HashSet<Integer>> vertiesConnectMap;
    public int reach(HashMap<Integer,HashSet<Integer>> hm, int u, int v) {
        visited = new HashSet<Integer>();
        vertiesConnectMap = hm;
        // System.out.println(u);
        // System.out.println(v);
        // System.out.println(vertiesConnectMap);
        if (explore(u,v)) {
            return 1;
        }
        return 0;
    }

    private boolean explore(int u, int v) {
        visited.add(u);
        for (int vertex : vertiesConnectMap.get(u)) {
            if (vertex == v) {
                return true;
            }
            else if (!visited.contains(vertex)) {
                if (explore(vertex, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // vertices
        int m = sc.nextInt(); // edges
        HashMap<Integer,HashSet<Integer>> hm = new HashMap<Integer,HashSet<Integer>>();
        for (int i = 0 ; i < n ; i ++ ) {
            hm.put(i,new HashSet<Integer>());
        }
        for (int i = 0 ; i < m ; i ++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            HashSet<Integer> connections = hm.get(x - 1);
            connections.add(y - 1);
            hm.put(x - 1, connections);
            connections = hm.get(y - 1);
            connections.add(x - 1);
            hm.put(y - 1, connections);
        }
        int u = sc.nextInt() - 1; // start
        int v = sc.nextInt() - 1; // end
        Reachability rch = new Reachability();
        System.out.println(rch.reach(hm,u,v));
    }
}