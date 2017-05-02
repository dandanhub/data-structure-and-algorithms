## 323. Number of Connected Components in an Undirected Graph
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.

Example 1:
~~~~
     0          3
     |          |
     1 --- 2    4
~~~~
Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.

Example 2:
~~~~
     0           4
     |           |
     1 --- 2 --- 3
~~~~
Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.

Note:
You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges

#### Solution
DFS

## 417. Pacific Atlantic Water Flow
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:
The order of returned grid coordinates does not matter.
Both m and n are less than 150.
Example:

Given the following 5x5 matrix:
~~~~
 Pacific ~   ~   ~   ~   ~
      ~  1   2   2   3  (5) *
      ~  3   2   3  (4) (4) *
      ~  2   4  (5)  3   1  *
      ~ (6) (7)  1   4   5  *
      ~ (5)  1   1   2   4  *
         *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
~~~~

#### Solution
Use two boolean array to mark whether pacific and atlantic water can flow into a point matrix[i][j].
Use DFS to mark matrix just like water flow.
