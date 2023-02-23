//package wk4;

import java.util.*;

public class Dijkstra {
    int vertices;
    PriorityQueue<Integer[]> heap;
    int[] dist;
    int[] prev;
    public int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int sta, int stp) {
        vertices = adj.length;
        heap = new PriorityQueue<>((a,b) -> a[0]-b[0]); 

        dist = new int[vertices];
        prev = new int[vertices];
        for (int i = 0; i < vertices; i ++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }

        dist[sta] = 0;
        heap.offer(new Integer[] {dist[sta], sta});
        prev[sta] = sta;
        
        while (!heap.isEmpty()) {
            Integer[] current = heap.poll();
            int currentID = current[1];
            int currentDist = current[0];

            if (currentDist > dist[currentID]) {
                continue;
            }

            if (currentID == stp) {
                return dist[currentID];
            }

            ArrayList<Integer> Adjs = adj[currentID];
            ArrayList<Integer> AdjsCost = cost[currentID];

            for (int i = 0; i < Adjs.size(); i ++) {
                int adjID = Adjs.get(i);
                int adjCost = AdjsCost.get(i);

                int currentAdjCost = currentDist + adjCost;
                
                if (dist[adjID] > currentAdjCost) {
                    dist[adjID] = currentAdjCost;
                    prev[adjID] = currentID;
                    heap.offer(new Integer[] {dist[adjID], adjID});
                }
            }
        }
        
        return -1;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i ++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            int w = scanner.nextInt();
            adj[x].add(y);
            cost[x].add(w);
        }
        int sta = scanner.nextInt() - 1;
        int stp = scanner.nextInt() - 1;

        Dijkstra dj = new Dijkstra();
        System.out.println(dj.distance(adj,cost,sta,stp));
    }    
}
