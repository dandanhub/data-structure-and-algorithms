## 46. Permutations
Given a collection of distinct numbers, return all possible permutations.

For example,
[1,2,3] have the following permutations:
~~~
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
~~~

#### Solution
如何快速的写出bug free的版本 <br>

Attempt: 3
~~~
public class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans =  new ArrayList<List<Integer>>();
        if (nums == null) return ans;
        helper(ans, new ArrayList<Integer>(), nums);
        return ans;
    }

    private void helper(List<List<Integer>> ans, List<Integer> list, int[] nums) {
        if (list.size() == nums.length) {
            ans.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!list.contains(nums[i])) {
                list.add(nums[i]);
                helper(ans, list, nums);
                list.remove(list.size() - 1); // never forget to remove
            }
        }
    }
}
~~~

## 47. Permutations II
Given a collection of numbers that might contain duplicates, return all possible unique permutations.

For example,
[1,1,2] have the following unique permutations:
~~~
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
~~~

#### Solution
**这题的关键是如何避免重复** <br>
使用used数组标记数组元素是否使用过

Attempt: 2
~~~
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null) return ans;
        Arrays.sort(nums);
        helper(ans, new ArrayList<Integer>(), nums, new boolean[nums.length]);
        return ans;
    }

    private void helper(List<List<Integer>> ans, List<Integer> list, int[] nums, boolean[] used) {
        if (list.size() == nums.length) {
            ans.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                continue;
            }
            list.add(nums[i]);
            used[i] = true;
            helper(ans, list, nums, used);
            list.remove(list.size() - 1);
            used[i] = false;
        }
    }
}
~~~

## 384. Shuffle an Array
Shuffle a set of numbers without duplicates.

Example:
~~~
// Init an array with set 1, 2, and 3.
int[] nums = {1,2,3};
Solution solution = new Solution(nums);

// Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
solution.shuffle();

// Resets the array back to its original configuration [1,2,3].
solution.reset();

// Returns the random shuffling of array [1,2,3].
solution.shuffle();
~~~

#### Solution
1. 随机生成数，然后利用swap调转
2. 模拟permutation选数的过程，把数字放入list，然后生成随机数，去拿list对应位置的数


- 注意Random的使用语法, new Random().nextInt(bound)
- 数组复制nums.clone(); <br>

|  System.arraycopy()  |  Object.clone()     | Arrays.copyOf()  |
| :------------- | :------------- | :------------- |
| String[] x = {"one", "two", "three", "four", "five"}; <br> String[] y = new String[2]; <br> System.arraycopy(x, 3, y, 0, 2); | int[] x = {1, 2, 3, 4, 5}; <br> int[] y = x.clone(); | String[] x = {"one", "two", "three", "four", "five"}; <br> String[] y = Arrays.copyOf(x, x.length);|

~~~
public class Solution {

    int[] nums;
    Random random;

    public Solution(int[] nums) {
        this.nums = nums;  
        random = new Random();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int[] shuffle = nums.clone();
        for (int i = 1; i < nums.length; i++) {
            int r = random.nextInt(i + 1);
            if (r != i) swap(shuffle, i, r);
        }
        return shuffle;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
~~~

~~~
public class Solution {

    private int[] nums;
    private Random random;

    public Solution(int[] nums) {
        this.nums = nums;
        random = new Random();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int l = nums.length;
        int[] newNums = new int[l];
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < l; i++) list.add(nums[i]);
        for (int i = 0; i < l; i++) {
            int r = random.nextInt(list.size());
            newNums[i] = list.get(r);
            list.remove(r);
        }
        return newNums;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
~~~

## 31. Next Permutation
Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

The replacement must be in-place, do not allocate extra memory.

Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
1,2,3 → 1,3,2
3,2,1 → 1,2,3
1,1,5 → 1,5,1

#### Solution
三步走
1. 从右向左找到最长递增子序列
2. 在右边子序列中找到比pivot-1元素大的最小的元素, swap这个元素和pivot-1
3. 反转右边的子序列

Attempt: 2
~~~
public class Solution {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        // find the longest increasing sequence from right to left
        int pivot = nums.length - 1;
        while (pivot > 0 && nums[pivot - 1] >= nums[pivot]) {
            pivot--;
        }

        // edge case, rearrange it as the lowest order
        if (pivot == 0) {
            reverse(nums, 0, nums.length - 1);
            return;
        }

        // find the smallest number in right subseq, which is larger than nums[pivot - 1]
        int index = nums.length - 1;
        while (index >= pivot && nums[index] <= nums[pivot - 1]) {
            index--;
        }

        // swap pivot - 1 and index
        swap(nums, pivot - 1, index); // fix bug swap between pivot - 1 and index

        // reverse the right subsequence
        reverse(nums, pivot, nums.length - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start, int end) {
        for (int i = 0; i <= (end - start) / 2; i++) {
            swap(nums, start + i, end - i);
        }
    }
}
~~~

## 60. Permutation Sequence
The set [1,2,3,…,n] contains a total of n! unique permutations.

By listing and labeling all of the permutations in order,
We get the following sequence (ie, for n = 3):

"123"
"132"
"213"
"231"
"312"
"321"
Given n and k, return the kth permutation sequence.

Note: Given n will be between 1 and 9 inclusive.

#### Solution
数数的方法，每次得到一个index，然后从list里面取数据

Attempt: 1
~~~
public class Solution {
    public String getPermutation(int n, int k) {
        // edge case
        if (n <= 0) return "";

        // cal (n - 1)!
        int base = 1;
        for (int i = 2; i < n; i++) {
            base *= i;
        }

        if (k > base * n) return "";

        StringBuilder sb = new StringBuilder();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        k = k - 1; // convert k to 0 based index
        while (k != 0) {
            // get the index of next num
            int index = k / base;
            sb.append(list.get(index));
            list.remove(index);
            k = k % base;
            base /= list.size();
        }

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));    
        }

        return sb.toString();
    }
}
~~~

## 267. Palindrome Permutation II
Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

For example:

Given s = "aabb", return ["abba", "baab"].

Given s = "abc", return [].

#### Solution
Backtracking

方法1： 用Map统计字符，用List做Backtracking
~~~
public class Solution {
    public List<String> generatePalindromes(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null) return ans;

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

            if (set.contains(ch)) set.remove(ch);
            else set.add(ch);
        }

        if (set.size() > 1) return ans;

        // if there is a valid char with lonely 1 occurence set it to ch
        Character ch = null;
        if (set.size() == 1) ch = set.iterator().next();

        // covert the map to char list and sort in lexicography order
        List<Character> list = new ArrayList<Character>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue() / 2; i++) list.add(entry.getKey());
        }
        Collections.sort(list);

        helper(ans, list, new StringBuilder(), ch, new boolean[list.size()]);
        return ans;
    }

    private void helper(List<String> ans, List<Character> list, StringBuilder sb, Character ch, boolean[] used) {
        // base case
        if (sb.length() == list.size()) {
            String reversed = new StringBuilder(sb).reverse().toString();
            StringBuilder newSb = new StringBuilder(sb);
            if (ch != null) newSb.append(ch);
            newSb.append(reversed);
            ans.add(newSb.toString());
            return;
        }

        // backtracking
        for (int i = 0; i < list.size(); i++) {
            if (used[i] || (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1])) {
                continue;
            }

            sb.append(list.get(i));
            used[i] = true;
            helper(ans, list, sb, ch, used);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }
}
~~~

方法2：用int[256]数组统计字符，用char[]数组做bakctracking
~~~
public class Solution {
    public List<String> generatePalindromes(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null) return ans;

        int[] dict = new int[256];
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            dict[ch]++;
        }

        char[] chars = new char[s.length() / 2];
        int index = 0;
        boolean odd = false;
        Character ch = null;
        for (int i = 0; i < 256; i++) {
            if (dict[i] % 2 != 0) {
                if (odd) return ans;
                odd = true;
                ch = (char) i;
            }
            for (int j = 0; j < dict[i] / 2; j++) {
                chars[index++] = (char) i;
            }
        }

        helper(ans, chars, new StringBuilder(), ch, new boolean[s.length() / 2]);
        return ans;
    }

    private void helper(List<String> ans, char[] chars, StringBuilder sb, Character ch, boolean[] used) {
        // base case
        if (sb.length() == chars.length) {
            String reversed = new StringBuilder(sb).reverse().toString();
            StringBuilder newSb = new StringBuilder(sb);
            if (ch != null) newSb.append(ch);
            newSb.append(reversed);
            ans.add(newSb.toString());
            return;
        }

        // backtracking
        for (int i = 0; i < chars.length; i++) {
            if (used[i] || (i > 0 && chars[i] == chars[i - 1] && !used[i - 1])) {
                continue;
            }

            sb.append(chars[i]);
            used[i] = true;
            helper(ans, chars, sb, ch, used);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }
}
~~~
