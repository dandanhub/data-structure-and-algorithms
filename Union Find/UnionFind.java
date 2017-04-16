public class UnionFind {

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<Integer>();
        if (m <= 0 || n <= 0 || positions == null || positions.length == 0 || positions[0].length == 0) {
            return ans;
        }

        int[] roots = new int[m * n];
        Arrays.fill(roots, -1);

        int[][] dirs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

        int count = 0;
        for (int i = 0; i < positions.length; i++) {
            int[] pos = positions[i];
            int index = n * pos[0] + pos[1]; // assume positions are valid
            roots[index] = index;
            count++;

            for (int[] dir : dirs) {
                int x = pos[0] + dir[0];
                int y = pos[1] + dir[1];
                int newIndex = n * x + y;
                if (x < 0 || x >= m || y < 0 || y >= n || roots[newIndex] == -1) {
                    continue;
                }

                int rootNewIndex = findRoot(roots, newIndex);
                if (rootNewIndex != index) {
                    roots[index] = rootNewIndex;
                    index = rootNewIndex; // need to update index to the parent root
                    count--;
                }
            }
            ans.add(count);
        }
        return ans;
    }

    private int findRoot(int[] roots, int id) {
        while (id != roots[id]) {
            id = roots[id];
        }
        return id;
    }
}
