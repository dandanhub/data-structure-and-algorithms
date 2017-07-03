## 269. Alien Dictionary
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

## 210. Course Schedule II
There are a total of n courses you have to take, labeled from 0 to n - 1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

For example:

2, [[1,0]]
There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1]

4, [[1,0],[2,0],[3,1],[3,2]]
There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3]. Another correct ordering is[0,2,1,3].

Note:
The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
You may assume that there are no duplicate edges in the input prerequisites.

#### Solution
1. 读入prerequisites统计indegree, 可以不用建graph
2. 如果build map的话用链表数组比map快，因为这道题的节点是有序的
3. DFS解法

Method 1: Topological sort BFS
~~~
public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        if (prerequisites == null || prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++) {
                ans[i] = i;
            }
            return ans;
        }

        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int course = prerequisites[i][0];
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            ans[count++] = node;
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == node) {
                    int course = prerequisites[i][0];
                    indegree[course]--;
                    if (indegree[course] == 0) {
                        queue.offer(course);
                    }
                }
            }
        }

        if (count != numCourses) {
            return new int[0];
        }

        return ans;
    }
}
~~~

Method 2: 3-Color DFS 
~~~
public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
        for (int i = 0; i < prerequisites.length; i++){
            int pre = prerequisites[i][1];
            int ready = prerequisites[i][0];
            Set<Integer> set = graph.getOrDefault(ready, new HashSet<Integer>());
            set.add(pre);
            graph.put(ready, set);
        }

        int[] visited = new int[numCourses];
        int[] result = new int[numCourses];
        for (int i = 0; i < numCourses; i++){
            if (isCyclic(graph, visited, i, result))
                return new int[0];
        }
        return result;
    }

    private int index = 0;
    private boolean isCyclic(Map<Integer, Set<Integer>> graph, int[] visited, int course, int[] result) {
        if (visited[course] == 1)
            return true;
        if (visited[course] == 2)
            return false;

        visited[course] = 1;
        Set<Integer> set = graph.get(course);
        if (set != null) {
            for (int neighbor : set) {
                if (isCyclic(graph, visited, neighbor, result)) {
                    return true;
                }
            }
        }

        visited[course] = 2;
        result[index++] = course;
        return false;
    }
}
~~~
