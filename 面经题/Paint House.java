/* 256. Paint House
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.
*/

// O(n) time and O(1) space
class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0
            || costs[0].length == 0) return 0;

        int lastRed = 0;
        int lastGreen = 0;
        int lastBlue = 0;
        for (int i = 0; i < costs.length; i++) {
            int currRed = Math.min(lastGreen, lastBlue) + costs[i][0];
            int currGreen = Math.min(lastRed, lastBlue) + costs[i][1];
            int currBlue = Math.min(lastRed, lastGreen) + costs[i][2];
            lastRed = currRed;
            lastGreen = currGreen;
            lastBlue = currBlue;
        }

        return Math.min(Math.min(lastRed, lastGreen), lastBlue);
    }
}

/*
265. Paint House II
There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Follow up:
Could you solve it in O(nk) runtime?
*/

// O(nk) time and O(1) space
class Solution {
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0
            || costs[0].length == 0) return 0;

        int n = costs.length;
        int k = costs[0].length;
        int lastMin = 0;
        int lastMinIndex = -1;
        int lastSecondMin = 0;

        for (int i = 0; i < n; i++) {
            int currMin = Integer.MAX_VALUE;
            int currMinIndex = -1;
            int currSecondMin = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int cost = costs[i][j];
                if (j == lastMinIndex) {
                    cost += lastSecondMin;
                } else {
                    cost += lastMin;
                }

                if (cost < currMin) {
                    currSecondMin = currMin;
                    currMin = cost;
                    currMinIndex = j;
                } else if (cost < currSecondMin) {
                    currSecondMin = cost;
                }
            }
            lastMin = currMin;
            lastMinIndex = currMinIndex;
            lastSecondMin = currSecondMin;
        }

        return lastMin;
    }
}

/*
Paint house with path output
*/

package Google;

import java.util.List;

public class PaintHouse {
    public static void main(String[] args) {
        int[][] costs = {{1, 2, 3}, {3, 3, 3}, {2, 1, 8}};
        String path = minCost(costs);
        System.out.println(path);
    }

    public static String minCost(int[][] costs) {
        if (costs == null || costs.length == 0
            || costs[0].length == 0) return "";

        int lastRed = 0;
        int lastGreen = 0;
        int lastBlue = 0;

        int n = costs.length;
        int[][] dp = new int[n][3];

        for (int i = 0; i < costs.length; i++) {
            int currRed = Math.min(lastGreen, lastBlue) + costs[i][0];
            dp[i][0] = lastGreen < lastBlue ? 1 : 2;
            int currGreen = Math.min(lastRed, lastBlue) + costs[i][1];
            dp[i][1] = lastRed < lastBlue ? 0 : 2;
            int currBlue = Math.min(lastRed, lastGreen) + costs[i][2];
            dp[i][2] = lastRed < lastGreen ? 0 : 1;

            lastRed = currRed;
            lastGreen = currGreen;
            lastBlue = currBlue;
        }

        int index = -1;
        int min = Math.min(Math.min(lastRed, lastGreen), lastBlue);
        if (min == lastRed) {
            index = 0;
        } else if (min == lastGreen) {
            index = 1;
        } else if (min == lastBlue) {
            index = 2;
        }

        System.out.println(min);

        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i >= 0; i--) {
            sb.append(index);
            index = dp[i][index];
        }

        return sb.reverse().toString();
    }
}
