## 56. Merge Intervals
Given a collection of intervals, merge all overlapping intervals.

For example,
Given [1,3],[2,6],[8,10],[15,18],
return [1,6],[8,10],[15,18].

#### Solution

~~~
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return intervals;

        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                if (i1.start == i2.start) return i1.end - i2.end;
                return i1.start - i2.start;
            }
        });

        List<Interval> list = new ArrayList<Interval>();
        list.add(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval i1 = list.get(list.size() - 1);
            Interval i2 = intervals.get(i);
            if (i2.start <= i1.end) {
                Interval mergedInterval = new Interval(i1.start, Math.max(i1.end, i2.end));
                list.set(list.size() - 1, mergedInterval);
            }
            else {
                list.add(i2);
            }
        }

        return list;
    }
}
~~~

## 57. Insert Interval
Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:
Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].

Example 2:
Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].

This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].

#### Solution
比较繁琐的方法
1. 先把newInterval插入到ArrayList里面
2. 对新的ArrayList进行merge Interval
~~~
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> ans = new ArrayList<Interval>();
        if (intervals == null || intervals.size() == 0) {
            ans.add(newInterval);
            return ans;
        }

        // find the insertion pos and insert new interval
        List<Interval> newIntervals = new ArrayList<Interval>();
        int i = 0;
        while (i < intervals.size() && intervals.get(i).start < newInterval.start) {
            newIntervals.add(intervals.get(i));
            i++;
        }
        newIntervals.add(newInterval);
        while (i < intervals.size()) {
            newIntervals.add(intervals.get(i));
            i++;
        }

        // merge intervals
        ans.add(newIntervals.get(0));
        int pos = 1;
        while (pos < newIntervals.size()) {
            Interval last = ans.get(ans.size() - 1);
            if (last.end >= newIntervals.get(pos).start) {
                int s = last.start;
                int e = Math.max(last.end, newIntervals.get(pos).end);
                Interval in = new Interval(s, e);
                ans.set(ans.size() - 1, in); // update to the merged one
            }
            else {
                ans.add(newIntervals.get(pos));
            }
            pos++;
        }

        return ans;
    }
}
~~~
