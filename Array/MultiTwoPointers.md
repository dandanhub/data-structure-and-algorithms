## 632. Smallest Range (+++)

You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the k lists.

We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

Example 1:
~~~
Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
Output: [20,24]
Explanation:
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].
~~~

Note:
1. The given list may contain duplicates, so ascending order means >= here.
2. 1 <= k <= 3500
3. -105 <= value of elements <= 105.
4. For Java users, please note that the input type has been changed to List<List<Integer>>. And after you reset the code template, you'll see this point.

#### Solution
比较繁琐的自己的版本：
1. 先merge n lists，得到一个大的sorted list，这个sorted list中每个元素都有对应的来自哪个list的index
2. 然后sliding window根据规则求出最小的range包含至少一个来自各个list的数字
3. Time: merge O(mnlog(n)), sliding window O(mn) -> O(mnlog(n))
4. Space: O(mn)

Attempts: 1
~~~
public class Solution {

    class ListNum {
        int index;
        int pos;
        int num;

        ListNum(int num, int index, int pos) {
            this.num = num;
            this.index = index;
            this.pos = pos;
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        if (nums == null || nums.size() == 0) return new int[2];

        List<ListNum> list = merge(nums);
        int[] dict = new int[nums.size()];
        int i = 0, j = 0;
        int count = 0;
        int[] ans = {list.get(0).num, list.get(list.size() - 1).num};
        while (j < list.size()) {
            ListNum numj = list.get(j);
            if (dict[numj.index] == 0) count++;
            dict[numj.index]++;

            while (count == nums.size()) { // we have a valid range
                int[] range = {list.get(i).num, list.get(j).num};
                if (compare(range, ans) < 0) ans = range;

                ListNum numi = list.get(i);
                dict[numi.index]--;
                if (dict[numi.index] == 0) count--;
                i++;
            }

            j++;
        }

        return ans;
    }

    // merge sorted array list
    private List<ListNum> merge(List<List<Integer>> nums) {
        List<ListNum> list = new ArrayList<ListNum>();

        PriorityQueue<ListNum> pq = new PriorityQueue<ListNum>(new Comparator<ListNum>() {
            public int compare(ListNum n1, ListNum n2) {
                return n1.num - n2.num;
            }
        });

        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) != null && nums.get(i).size() != 0) {
                ListNum listNum = new ListNum(nums.get(i).get(0), i, 0);
                pq.offer(listNum);
            }
        }

        while (!pq.isEmpty()) {
            ListNum item = pq.poll();
            list.add(item);
            int listIndex = item.index;
            int listPos = item.pos;
            if (listPos + 1 < nums.get(listIndex).size()) {
                int nextNum = nums.get(listIndex).get(listPos + 1);
                ListNum nextItem = new ListNum(nextNum, listIndex, listPos + 1);
                pq.offer(nextItem);
            }
        }

        return list;
    }

    //
    private int compare(int[] a, int[] b) {
        if (b[1] - b[0] == a[1] - a[0]) return a[0] - b[0];
        return (a[1] - a[0]) - (b[1] - b[0]);
    }
}
~~~
