import java.util.*;

public class Toposort {
    HashSet<Integer> visitedHS;
    HashSet<Integer> cycleVisited;
    ArrayList<Integer> postVisitedAL;
    ArrayList<Integer> preVisitedAL;
    ArrayList<String> topoSorted;
    HashMap<Integer,HashSet<Integer>> vertiesPostConnectMap;
    HashMap<Integer,HashSet<Integer>> vertiesPreConnectMap;
    
    // reutrn topology sort
    public String getSort(int n, HashMap<Integer,HashSet<Integer>> postHM, 
                          HashMap<Integer,HashSet<Integer>> preHM) {
        vertiesPostConnectMap = postHM;
        vertiesPreConnectMap = preHM;
        visitedHS = new HashSet<Integer>();
        topoSorted = new ArrayList<String>();
        postVisitedAL = new ArrayList<Integer>();
        preVisitedAL = new ArrayList<Integer>();

        DFS();
        
        // System.out.println(postVisitedAL);

        for (int i = 0; i < n; i ++) {
            int index = n - i - 1;
            topoSorted.add(Integer.toString(postVisitedAL.get(index) + 1));
        }

        return String.join(" ", topoSorted);
    }
    
    private void DFS() {
        for (int v : vertiesPostConnectMap.keySet()) {
            if (!visitedHS.contains(v)) {
                int target = v;
                HashSet<Integer> preVertex = vertiesPreConnectMap.get(v);
                if (!preVertex.isEmpty()) {
                    cycleVisited = new HashSet<Integer>();
                    target = findSource(v);
                }
                explore(target);
            }
        }
    }
    // When there is no sink return -1, or return sinkNo.
    private int findSource(int key) {
        HashSet<Integer> preSet = vertiesPreConnectMap.get(key);
        cycleVisited.add(key);

        if (preSet.size() == 0) {
            return key;
        }
        for (int vertex : preSet) {
            if (!cycleVisited.contains(vertex)) {
                return findSource(vertex);
            }
        }
        return -1;
    }

    private void explore(int v) {
        visitedHS.add(v);
        preVisitedAL.add(v);
        HashSet<Integer> postVertexs = vertiesPostConnectMap.get(v);
        for (int vertex : postVertexs) {
            if (!visitedHS.contains(vertex)) {
                explore(vertex);
            }
        }
        postVisitedAL.add(v);
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
        Toposort ts = new Toposort();
        System.out.println(ts.getSort(n,postHM,preHM));
    }
}