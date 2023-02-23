def BFS(m,verticesPostMap) :
    from collections import deque
    dist, Q = [], deque()

    for i in range(m) :
        dist.append(-1)

    for i in range(m) :
        if dist[i] != -1 :
            continue
        else :
            source = i
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

    return dist

if __name__ == "__main__" :
    m, n = map(int,input().split())
    verticesPostMap, verticesTuple = dict(), []
    for i in range(m) :
        verticesPostMap[i] = list()
    for i in range(n) :
        x, y = map(int,input().split())
        verticesTuple.append((x - 1, y - 1))
        postVertices = verticesPostMap.get(x - 1)
        postVertices.append(y - 1)
        verticesPostMap[x - 1] = postVertices
        postVertices = verticesPostMap.get(y - 1)
        postVertices.append(x - 1)
        verticesPostMap[y - 1] = postVertices
    
    dist = BFS(m,verticesPostMap)
    ans = True
    for i in verticesTuple :
        v1, v2 = i[0], i[1] 
        if dist[v1] % 2 != dist[v2] % 2 :
            ans = ans and True
        else : 
            ans = ans and False
            break
    if ans == True :
        print(1)
    else :
        print(0)


