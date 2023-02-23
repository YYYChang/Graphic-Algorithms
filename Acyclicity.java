import java.util.*;

public class Acyclicity {
    HashSet<Integer> visited;
    HashSet<Integer> cycleVisited;
    HashMap<Integer,HashSet<Integer>> vertiesPostConnectMap;
    HashMap<Integer,HashSet<Integer>> vertiesPreConnectMap;
    
    // When there is at least one cycle exist reutrn 1, or return 1;
    public int acyclic(HashMap<Integer,HashSet<Integer>> postHM, HashMap<Integer,HashSet<Integer>> preHM) {
        visited = new HashSet<Integer>();
        vertiesPostConnectMap = postHM;
        vertiesPreConnectMap = preHM;
        HashSet<Integer> preSet;
        // System.out.println(vertiesPostConnectMap);
        // System.out.println(vertiesPreConnectMap);

        for (int key : vertiesPostConnectMap.keySet()) {
            if (!visited.contains(key)) {
                // System.out.println("key to Sink:" + key);
                cycleVisited = new HashSet<Integer>();
                int sink = findSink(key);
                // System.out.println("Sink: " + sink);
                // System.out.println("cyclevisted:" + cycleVisited);
                if (sink == -1) {
                    return 1;
                }
                visited.add(sink);
                preSet = vertiesPreConnectMap.get(sink);
                for (int vertex : preSet) {
                    HashSet<Integer> connections = vertiesPostConnectMap.get(vertex);
                    connections.remove(sink);
                    vertiesPostConnectMap.put(vertex,connections);

                    // System.out.println("prevertex: " + vertex);
                    if (findLoop(vertex) == 1) {
                        // System.out.println("findloop = 1");
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    
    // When there is no sink return -1, or return sinkNo.
    private int findSink(int key) {
        HashSet<Integer> postSet = vertiesPostConnectMap.get(key);
        cycleVisited.add(key);

        if (postSet.size() == 0) {
            // System.out.println("1: " + postSet);
            return key;
        }
        for (int vertex : postSet) {
            if (cycleVisited.contains(vertex)) {
                return -1;
            }
            // System.out.println("V: " + vertex);
            return findSink(vertex);
        }
        return -1;
    }

    private int findLoop(int vertex) {
        // System.out.println("findloop Vertex: " + vertex);
        HashSet<Integer> postSet = vertiesPostConnectMap.get(vertex);
        HashSet<Integer> preSet = vertiesPreConnectMap.get(vertex);
        visited.add(vertex);
        for (int post : postSet) {
            // System.out.println("findloop post: " + post);
            if (!visited.contains(post)) {
                cycleVisited = new HashSet<>();
                int sink = findSink(post);
                if (sink == -1){
                    return 1;
                }
                else {
                    visited.add(sink);
                    if (preSet.size() > 0) {
                        for (int pre : preSet) {
                            HashSet<Integer> connections = vertiesPostConnectMap.get(pre);
                            connections.remove(sink);
                            vertiesPostConnectMap.put(pre,connections);
                            if (findLoop(pre) == 1) {
                                // System.out.println("findloop findloop = 1");
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // vertices
        int m = sc.nextInt(); // edges
        HashMap<Integer,HashSet<Integer>> postHM = new HashMap<Integer,HashSet<Integer>>();
        HashMap<Integer,HashSet<Integer>> preHM = new HashMap<Integer,HashSet<Integer>>();

        for (int i = 0 ; i < n ; i ++ ) {
            postHM.put(i,new HashSet<Integer>());
            preHM.put(i,new HashSet<Integer>());
        }
        for (int i = 0 ; i < m ; i ++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            HashSet<Integer> connections = postHM.get(x - 1);
            connections.add(y - 1);
            postHM.put(x - 1, connections);
            connections = preHM.get(y - 1);
            connections.add(x - 1);
            preHM.put(y - 1, connections);
        }
        Acyclicity ac = new Acyclicity();
        System.out.println(ac.acyclic(postHM,preHM));
    }
}