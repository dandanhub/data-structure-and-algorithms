// In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents.
class Solution {
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] can1 = {-1, -1};
        int[] can2 = {-1, -1};
        int[] parent = new int[edges.length + 1];
        for (int i = 0; i < edges.length; i++) {
            if (parent[edges[i][1]] == 0) {
                parent[edges[i][1]] = edges[i][0];
            } else {
                can2 = new int[] {edges[i][0], edges[i][1]};
                can1 = new int[] {parent[edges[i][1]], edges[i][1]};
                edges[i][1] = 0; // remove the can2 edge
            }
        }
        for (int i = 0; i < edges.length; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < edges.length; i++) {
            if (edges[i][1] == 0) {
                continue;
            }
            int child = edges[i][1], father = edges[i][0];
            if (root(parent, father) == child) { // found cycle
                if (can1[0] == -1) { // if no two parent situation, return the edge in cycle
                    return edges[i];
                }
                return can1; // return the can1
            }
            parent[child] = father;
        }
        return can2; // if the tree is valid after removing can2 edge, return can2
    }

    int root(int[] parent, int i) {
        while (i != parent[i]) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }
}

// In this problem, a tree is an undirected graph that is connected and has no cycles.
// detect whether a cycle exists
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int[] parents = new int[2001];
        for (int i = 0; i < parents.length; i++) parents[i] = i;

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (find(parents, u) == find(parents, v)) {
                return edge;
            } else {
                union(parents, u, v);
            }
        }
        return null;
    }

    private int find(int[] parents, int node) {
        while (parents[node] != node) {
            parents[node] = parents[parents[node]];
            node = parents[node];
        }
        return node;
    }

    private void union(int[] parents, int n1, int n2) {
        int p1 = find(parents, n1);
        int p2 = find(parents, n2);

        parents[p1] = p2;
    }
}
