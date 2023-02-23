def BFS(m,source,target,verticesPostMap) :
    from collections import deque
    dist, Q = [], deque()

    for i in range(m) :
        dist.append(-1)
    dist[source] = 0
    Q.append(source)

    while len(Q) != 0 :
        u = Q.popleft()
        postVertices = verticesPostMap.get(u)
        if len(postVertices) == 0 :
            continue
        for vertex in postVertices :
            if dist[vertex] == -1 :
                Q.append(vertex)
                dist[vertex] = dist[u] + 1
            if vertex == target :
                return dist[vertex]

    return dist[target]

if __name__ == "__main__" :
    m, n = map(int,input().split())
    verticesPostMap = dict()
    for i in range(m) :
        verticesPostMap[i] = list()
    for i in range(n) :
        x, y = map(int,input().split())
        postVertices = verticesPostMap.get(x - 1)
        postVertices.append(y - 1)
        verticesPostMap[x - 1] = postVertices
        postVertices = verticesPostMap.get(y - 1)
        postVertices.append(x - 1)
        verticesPostMap[y - 1] = postVertices

    source, target = map(int,input().split())
    source, target = source - 1, target - 1
    print(BFS(m,source,target,verticesPostMap))

    

