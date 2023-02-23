import java.util.*;

public class StronglyConnected {
    HashSet<Integer> visitedHS;
    HashSet<Integer> cycleVisited;
    ArrayList<Integer> postVisitedAL;
    ArrayList<Integer> preVisitedAL;
    ArrayList<Integer> topoSorted;
    HashMap<Integer,HashSet<Integer>> vertiesPostConnectMap;
    HashMap<Integer,HashSet<Integer>> vertiesPreConnectMap;
    
    // reutrn topology sort
    public int getSCC(int n, HashMap<Integer,HashSet<Integer>> postHM, 
                          HashMap<Integer,HashSet<Integer>> preHM) {
        vertiesPostConnectMap = postHM;
        vertiesPreConnectMap = preHM;
        visitedHS = new HashSet<Integer>();
        topoSorted = new ArrayList<Integer>();
        postVisitedAL = new ArrayList<Integer>();
        preVisitedAL = new ArrayList<Integer>();

        DFS();

        for (int i = 0; i < n; i ++) {
            int index = n - i - 1;
            topoSorted.add(postVisitedAL.get(index));
        }

        visitedHS = new HashSet<Integer>();
        int SCCcount = 0;
        for (int i = 0; i < n; i ++) {
            int currentVertex = topoSorted.get(i);
            if (!visitedHS.contains(currentVertex)) {
                SCCcount ++;
                explorePost(currentVertex);
            }
        }

        return SCCcount;
    }
    
    // create linear sort of reverse direction
    private void DFS() {
        for (int v : vertiesPostConnectMap.keySet()) {
            if (!visitedHS.contains(v)) {
                int target = v;
                explorePre(target);
            }
        }
    }

    private void explorePost(int v) {
        visitedHS.add(v);
        // preVisitedAL.add(v);
        HashSet<Integer> postVertexs = vertiesPostConnectMap.get(v);
        for (int vertex : postVertexs) {
            if (!visitedHS.contains(vertex)) {
                explorePost(vertex);
            }
        }
        // postVisitedAL.add(v);
    }

    private void explorePre(int v) {
        visitedHS.add(v);
        preVisitedAL.add(v);
        HashSet<Integer> preVertexs = vertiesPreConnectMap.get(v);
        for (int vertex : preVertexs) {
            if (!visitedHS.contains(vertex)) {
                explorePre(vertex);
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
        StronglyConnected scc = new StronglyConnected();
        System.out.println(scc.getSCC(n,postHM,preHM));
    }
}