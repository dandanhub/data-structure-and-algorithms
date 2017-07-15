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
