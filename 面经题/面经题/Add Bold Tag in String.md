## 616. Add Bold Tag in String (Medium)

Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.

Example 1:
~~~
Input:
s = "abcxyz123"
dict = ["abc","123"]
Output:
"<b>abc</b>xyz<b>123</b>"
~~~

Example 2:
~~~
Input:
s = "aaabbcc"
dict = ["aaa","aab","bc"]
Output:
"<b>aaabbc</b>c"
~~~

Note:
1. The given dict won't contain duplicates, and its length won't exceed 100.
2. All the strings in input have length in range [1, 1000].

#### Solution
Similar to merge sort <br>
一开始没有考虑到一个word在s中出现多次的情况，e.g.: "aaabbcc" ["a","b","c"]
~~~
class Solution {
    public String addBoldTag(String s, String[] dict) {
        if (dict == null || dict.length == 0) return s;

        List<int[]> list = new ArrayList<int[]>();
        for (String word : dict) {
            int index = s.indexOf(word);
            while (index != -1) {
                int[] interval = {index, index + word.length() - 1};
                list.add(interval);
                index += 1;
                index = s.indexOf(word, index);
            }
        }

        List<int[]> mergedList = mergerInterval(list);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (int[] pos : mergedList) {
            sb.append(s.substring(index, pos[0]));
            sb.append("<b>");
            sb.append(s.substring(pos[0], pos[1] + 1));
            sb.append("</b>");
            index = pos[1] + 1;
        }
        sb.append(s.substring(index, s.length()));
        return sb.toString();
    }

    private List<int[]> mergerInterval(List<int[]> intervals) {
        Collections.sort(intervals, new Comparator<int[]>() {
           public int compare(int[] i1, int[] i2) {
               if (i1[0] == i2[0]) return Integer.compare(i1[1], i2[1]);
               return Integer.compare(i1[0], i2[0]);
           }
        });

        List<int[]> ans = new ArrayList<int[]>();
        int i = 0;
        while (i < intervals.size()) {
            int s = intervals.get(i)[0];
            int e = intervals.get(i)[1];
            i++;
            while (i < intervals.size() && intervals.get(i)[0] <= e + 1) {
                e = Math.max(e, intervals.get(i)[1]);
                i++;
            }
            int[] newInterval = {s, e};
            ans.add(newInterval);
        }
        return ans;
    }
}
~~~

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
