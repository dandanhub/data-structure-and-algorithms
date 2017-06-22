/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Sort {
    // 296. Best Meeting Point
    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        List<Integer> row = new ArrayList<Integer>();
        List<Integer> col = new ArrayList<Integer>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    row.add(i);
                    col.add(j);
                }
            }
        }

        int len = row.size();
        Collections.sort(row);
        Collections.sort(col);

        int mRow = len % 2 == 1 ? row.get(len / 2) : (row.get(len / 2) + row.get(len / 2 - 1)) / 2;
        int mCol = len % 2 == 1 ? col.get(len / 2) : (col.get(len / 2) + col.get(len / 2 - 1)) / 2;

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans += Math.abs(row.get(i) - mRow) + Math.abs(col.get(i) - mCol);
        }

        return ans;
    }

    // 524. Longest Word in Dictionary through Deleting
    public String findLongestWord(String s, List<String> d) {
        if (s == null || s.length() == 0 || d == null || d.size() == 0) {
            return "";
        }

        // sort the string list
        // 1. by length
        // 2. with lexicographical order
        Collections.sort(d, new Comparator<String>() {
            public int compare(String a, String b) {
                // TO-DO handle null
                if (a.length() == b.length()) {
                    return a.compareTo(b);
                }
                return b.length() - a.length();
            }
        });

        for (int i = 0; i < d.size(); i++) {
            String str = d.get(i);
            if (str.length() > s.length()) {
                continue;
            }

            int pos = 0;
            for (int j = 0; j < s.length() && pos < str.length(); j++) {
                if (s.charAt(j) == str.charAt(pos)) {
                    pos++;
                }
            }
            if (pos == str.length()) {
                return str;
            }
        }

        return "";
    }
}
