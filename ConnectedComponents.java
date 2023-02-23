import java.util.*;

public class ConnectedComponents {
    HashSet<Integer> visited;
    HashMap<Integer,HashSet<Integer>> vertiesConnectMap;
    public int numberOfComponets(HashMap<Integer,HashSet<Integer>> hm) {
        visited = new HashSet<Integer>();
        vertiesConnectMap = hm;
        int count = 0;
        for (int key : vertiesConnectMap.keySet()) {
            if (!visited.contains(key)) {
                count ++;
                explore(key);
            }
        }
        return count;
    }

    private void explore(int key) {
        visited.add(key);
        for (int vertex : vertiesConnectMap.get(key)) {
            if (!visited.contains(vertex)) {
                explore(vertex);
            }
        }
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
        ConnectedComponents cc = new ConnectedComponents();
        System.out.println(cc.numberOfComponets(hm));
    }
}