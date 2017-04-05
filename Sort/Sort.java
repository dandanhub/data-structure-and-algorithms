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
}
