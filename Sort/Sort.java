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
    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });

        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i].end > intervals[i + 1].start) {
                return false;
            }
        }
        return true;
    }

    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                if (i1.start == i2.start) {
                    return i1.end - i2.end;
                }
                return i1.start - i2.start;
            }
        });

        Queue<Interval> pq = new PriorityQueue<Interval>(new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                return i1.end - i2.end;
            }
        });

        pq.offer(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            Interval prev = pq.poll();
            if (prev.end <= intervals[i].start) {
                prev.end = intervals[i].end;
            }
            else {
                pq.offer(intervals[i]);
            }
            pq.offer(prev);
        }
        return pq.size();
    }

    // 280. Wiggle Sort (Medium)
    // Solution 1, naiive way with O(n^2) time and O(1) space
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 1) {
                int temp = nums[nums.length - 1];
                for (int j = nums.length - 1; j > i ; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[i] = temp;
            }
        }
    }

    /*
    // 280. Wiggle Sort (Medium)
    // Solution 2, in-place and one-pass
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 1 && nums[i] < nums[i - 1]) {
                swap(nums, i, i - 1);
            }
            else if (i % 2 == 0 && i > 0 && nums[i] > nums[i - 1]) {
                swap(nums, i, i - 1);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    */

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
