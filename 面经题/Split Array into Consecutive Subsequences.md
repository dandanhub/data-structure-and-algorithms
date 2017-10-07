## 659. Split Array into Consecutive Subsequences
You are given an integer array sorted in ascending order (may contain duplicates), you need to split them into several subsequences, where each subsequences consist of at least 3 consecutive integers. Return whether you can make such a split.

Example 1:
~~~
Input: [1,2,3,3,4,5]
Output: True
Explanation:
You can split them into two consecutive subsequences :
1, 2, 3
3, 4, 5
~~~

Example 2:
~~~
Input: [1,2,3,3,4,4,5,5]
Output: True
Explanation:
You can split them into two consecutive subsequences :
1, 2, 3, 4, 5
3, 4, 5
~~~

Example 3:
~~~
Input: [1,2,3,4,4,5]
Output: False
~~~

Note:
The length of the input is in range of [1, 10000]

#### Solution
对于一个数字，有两种合法情况：
1. 把它append到已有的consecutive array中
2. 以这个数字为开头另起一个consecutive array
如果一个数组两个条件都满足，我们要怎么做？答案是把它append到已有的consecutive array中，我们不用担心第二个情况，因为我们也可以把它后续的数组都同样append到consecutive array中
~~~
class Solution {
    public boolean isPossible(int[] nums) {
        if (nums == null || nums.length < 3) return false;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        Map<Integer, Integer> conMap = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (map.get(num) == 0) continue;
            if (conMap.getOrDefault(num, 0) > 0) {
                conMap.put(num, conMap.get(num) - 1);
                conMap.put(num + 1, conMap.getOrDefault(num + 1, 0) + 1);
            }
            else if (map.getOrDefault(num + 1, 0) > 0 && map.getOrDefault(num + 2, 0) > 0) {
                map.put(num + 1, map.get(num + 1) - 1);
                map.put(num + 2, map.get(num + 2) - 1);
                conMap.put(num + 3, conMap.getOrDefault(num + 3, 0) + 1);
            }
            else {
                return false;
            }
            map.put(num, map.get(num) - 1);
        }
        return true;
    }
}
~~~
