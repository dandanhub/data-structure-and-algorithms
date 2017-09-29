## 133. Clone Graph (Medium) *
Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.


OJ's undirected graph serialization:
Nodes are labeled uniquely.

We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
As an example, consider the serialized graph {0,1,2#1,2#2,2}.

The graph has a total of three nodes, and therefore contains three parts as separated by #.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
Visually, the graph looks like the following:
~~~
       1
      / \
     /   \
    0 --- 2
         / \
         \_/
~~~

#### Solution
**这题的关键是避免重复**

Iterative
~~~
/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return node;

        Map<UndirectedGraphNode, UndirectedGraphNode> visited = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        Stack<UndirectedGraphNode> stack = new Stack<UndirectedGraphNode>();
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        stack.push(node);
        stack.push(newNode);
        visited.put(node, newNode);
        while (!stack.isEmpty()) {
            UndirectedGraphNode newCurr = stack.pop();
            UndirectedGraphNode curr = stack.pop();
            for (UndirectedGraphNode neigbbor : curr.neighbors) {
                if (!visited.containsKey(neigbbor)) {
                    UndirectedGraphNode newNeighbor = new UndirectedGraphNode(neigbbor.label);
                    visited.put(neigbbor, newNeighbor);
                    newCurr.neighbors.add(newNeighbor); // // 注意这里要指向newCurr.neighbors
                    stack.push(neigbbor);
                    stack.push(newNeighbor);
                }
                else {
                    UndirectedGraphNode newNeighbor = visited.get(neigbbor);
                    newCurr.neighbors.add(newNeighbor);
                }    
            }
        }

        return newNode;
    }
}
~~~


~~~
/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    Map<Integer, UndirectedGraphNode> map = new HashMap<Integer, UndirectedGraphNode>();

    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return node;

        // node已经被clone过，不需要再新建
        if (map.containsKey(node.label)) {
            return map.get(node.label);
        }

        UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
        map.put(node.label, newNode); // 注意在这个时候放入
        for (UndirectedGraphNode neighbor : node.neighbors) {
            newNode.neighbors.add(cloneGraph(neighbor));
        }

        return newNode;
    }
}
~~~

## 332. Reconstruct Itinerary (Medium) *
Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.

Note:
~~~
If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
All airports are represented by three capital letters (IATA code).
You may assume all tickets form at least one valid itinerary.
Example 1:
tickets = [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
Return ["JFK", "MUC", "LHR", "SFO", "SJC"].
Example 2:
tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Return ["JFK","ATL","JFK","SFO","ATL","SFO"].
Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"]. But it is larger in lexical order.
~~~

#### Solution
###### 欧拉回路
1. find whether a given graph has a Eulerian Path or not in O(V+E) time.
  - Eulerian Cycle in Undirected Graph <br>
  只考虑有度的vertices, 所有顶点even degree (connected graphs with all vertices of even degree have an Eulerian circuit)
  - Eulerian Path in Undirected Graph <br>
  只考虑有度的vertices, 0/2个顶点的度为odd, 其他所有顶点度为even (For the existence of Eulerian trails it is necessary that zero or two vertices have an odd degree)
  - Pseudo-code <br>
  ~~~
  dfs check if graph is connected (consider all verticals with non-zero degree)
  count vertices with odd degree
  if count == 0, Eulerian Cycle
  else if count == 2, Eulerian Path
  else no Eulerian Cycle/path
  ~~~
  - Eulerian Cycle in Directed Graph <br>
  所有顶点的出度入度相等 (1) All vertices with nonzero degree belong to a single strongly connected component. (2) In degree and out degree of every vertex is same
  - Eulerian Path in Directed Graph <br>
  0/2个顶点的出入度差1

2. Fleury’s Algorithm for printing Eulerian Path or Circuit O(E*E)
  - 如果有度数为odd的vertices (0 or 2个), 那么从度数为odd的顶点开始
  - 每次走一个边，选取edge的时候要尽量先选取non-bridge edge
  - 当遍历完所有边的时候停止

  Time complexity O(E*E), 因为每次遍历到一个顶点（边）要确认每条边是不是bridge edge (DFS判断).

3. Hierholzer’s Algorithm for directed graph O(E)
  - Thus the idea is to keep following unused edges and removing them until we get stuck. Once we get stuck, we back-track to the nearest vertex in our current path that has unused edges, and we repeat the process until all the edges have been used. We can use another container to maintain the final path. Refer to this [discussion](http://wattlebird.github.io/2015/06/28/%E6%AC%A7%E6%8B%89%E8%B7%AF/).
  - 前提是存在欧拉路径（回路）


这题是Hierholzer’s在有向图中在欧拉回路的应用 <br>
**注意最后输出顺序是反序**

Recursive
~~~
public class Solution {
    public List<String> findItinerary(String[][] tickets) {
        List<String> list = new ArrayList<String>();
        Map<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();

        // construct the map
        for (int i = 0; i < tickets.length; i++) {
            PriorityQueue<String> pq = map.getOrDefault(tickets[i][0], new PriorityQueue<String>());
            pq.offer(tickets[i][1]);
            map.put(tickets[i][0], pq);
        }

        // start from "JFK"
        String start = "JFK";
        dfs(map, list, start);

        return list;
    }

    private void dfs(Map<String, PriorityQueue<String>> map, List<String> list, String start) {
        while (map.containsKey(start) && map.get(start).size() > 0) {
            String next = map.get(start).poll();
            dfs(map, list, next);
        }
        list.add(0, start);
    }
}
~~~

Iterative
~~~
public class Solution {
    public List<String> findItinerary(String[][] tickets) {
        List<String> list = new ArrayList<String>();
        Map<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();

        // construct the map
        for (int i = 0; i < tickets.length; i++) {
            PriorityQueue<String> pq = map.getOrDefault(tickets[i][0], new PriorityQueue<String>());
            pq.offer(tickets[i][1]);
            map.put(tickets[i][0], pq);
        }

        // start from "JFK"
        Stack<String> stack = new Stack<String>();
        stack.push("JFK");
        while (!stack.isEmpty()) {
            String node = stack.peek();
            while (map.containsKey(node) && !map.get(node).isEmpty()) {
                node = map.get(node).poll();
                stack.push(node);
            }
            list.add(0, stack.pop());
        }

        return list;
    }
}
~~~

## 269. Alien Dictionary (Hard) *
There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

Example 1:
Given the following words in dictionary,
~~~
[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]
~~~
The correct order is: "wertf".

Example 2:
Given the following words in dictionary,
~~~
[
  "z",
  "x"
]
~~~
The correct order is: "zx".

Example 3:
Given the following words in dictionary,
~~~
[
  "z",
  "x",
  "z"
]
~~~
The order is invalid, so return "".

Note:
You may assume all letters are in lowercase.
You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
If the order is invalid, return an empty string.
There may be multiple valid order of letters, return any one of them is fine.

#### Solution
读入words然后建立graph, 再topological sort. <br>
**这题注意可能会有重复的边** <br>
**这种情况如何处理？** <br>
~~~
Input:
["zy","zx"]
Output:
""
Expected:
"yxz"

Input:
["za","zb","ca","cb"]
Output:
""
Expected:
"abzc"

bug with indegree a0 z0 a1 b2

Input:
["wnlb"]
Output:
""
Expected:
"blnw"
~~~

~~~
public class Solution {
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) return "";

        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        Map<Character, Integer> indegree = new HashMap<Character, Integer>();

        for (String str : words) {
            for (char ch: str.toCharArray()) indegree.put(ch, 0);
        }

        // build the graph
        for (int i = 1; i < words.length; i++) {
            String prev = words[i - 1];
            String curr = words[i];

            int pos = 0;
            for (int j = 0; j < prev.length() && j < curr.length(); j++) {
                char ch1 = prev.charAt(j);
                char ch2 = curr.charAt(j);

                // add graph edge
                if (prev.charAt(j) != curr.charAt(j)) {
                    Set<Character> set = graph.getOrDefault(ch1, new HashSet<Character>());
                    if (!set.contains(ch2)) { // if delete this, what will happen
                        set.add(ch2);
                        graph.put(ch1, set);
                        indegree.put(ch2, indegree.getOrDefault(ch2, 0) + 1);
                    }
                    break;
                }   
            }
        }

        Queue<Character> queue = new LinkedList<Character>();
        for (Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        StringBuilder res = new StringBuilder();
        while (!queue.isEmpty()) {
            char ch = queue.poll();
            res.append(ch);
            Set<Character> set = graph.get(ch);
            if (set != null) {
                for (char next : set) {
                    indegree.put(next, indegree.get(next) - 1);
                    if (indegree.get(next) == 0) queue.offer(next);
                }
            }
        }

        if (res.length() != indegree.size()) return "";
        return res.toString();
    }
}
~~~

## 399. Evaluate Division
Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0.
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

According to the example above:
~~~
equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.
~~~

#### Solution
build graph and then dfs
~~~
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        Map<String, Map<String, Double>> graph = new HashMap<String, Map<String, Double>>();
        for (int i = 0; i < equations.length; i++) {
            Map<String, Double> fMap = graph.getOrDefault(equations[i][0], new HashMap<String, Double>());
            fMap.put(equations[i][1], values[i]);
            graph.put(equations[i][0], fMap);

            if (values[i] != 0) {
                Map<String, Double> bMap = graph.getOrDefault(equations[i][1], new HashMap<String, Double>());
                bMap.put(equations[i][0], 1 / values[i]);
                graph.put(equations[i][1], bMap);
            }
        }

        int len = queries.length;
        double[] ans = new double[len];
        // Set<String> visited = new HashSet<String>();
        for (int i = 0; i < len; i++) {
            ans[i] = dfs(graph, queries[i][0], queries[i][1], 1, new HashSet<String>());
        }
        return ans;
    }

    private double dfs(Map<String, Map<String, Double>> graph, String start,
                       String end, double val, Set<String> visited) {

        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            return -1;
        }

        if (start.equals(end)) {
            return val;
        }

        visited.add(start);
        Map<String, Double> map = graph.get(start);
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (visited.contains(entry.getKey())) continue;
            double ans = dfs(graph, entry.getKey(), end, val * entry.getValue(), visited);
            if (ans != -1) {
                return ans;
            }    
        }
        visited.remove(start);

        return -1;
    }
}
~~~
