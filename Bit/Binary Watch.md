## 401. Binary Watch
A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).

Each LED represents a zero or one, with the least significant bit on the right.


For example, the above binary watch reads "3:25".

Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.

Example:
~~~
Input: n = 1
Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
~~~

Note:
- The order of output does not matter.
- The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
- The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".

#### Solution
Backtrack
~~~
class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> list = new ArrayList<String>();
        int[] hnums = {8, 4, 2, 1};
        int[] mnums = {32, 16, 8, 4, 2, 1};
        for (int i = 0; i <= num; i++) {
            List<Integer> hours = new ArrayList<Integer>();
            helper(hours, 0, i, hnums, 0);
            List<Integer> mins = new ArrayList<Integer>();
            helper(mins, 0, num - i, mnums, 0);

            for (int h : hours) {
                if (h > 11) continue;
                for (int m : mins) {
                    if (m > 59) continue;
                    StringBuilder sb = new StringBuilder();
                    sb.append(h).append(":");
                    if (m < 10) sb.append("0");
                    sb.append(m);
                    list.add(sb.toString());
                }
            }
        }

        return list;
    }


    private void helper(List<Integer> list, int sum, int count, int[] nums, int pos) {
        if (count == 0) {
            list.add(sum);
            return;
        }

        for (int i = pos; i < nums.length; i++) {
            helper(list, sum + nums[i], count - 1, nums, i + 1);
        }
    }
}
~~~

Bit manipulation
~~~
class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> list = new ArrayList<String>();
        for (int h = 0; h < 12; h++) {
            for (int m = 0; m < 60; m++) {
                if (Integer.bitCount(64 * h + m) == num) {
                    list.add(String.format("%d:%02d", h, m));
                }
            }
        }

        return list;
    }
}
~~~
