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

## 398. Random Pick Index
Given an array of integers with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.

Note:
The array size can be very large. Solution that uses too much extra space will not pass the judge.

Example:
~~~
int[] nums = new int[] {1,2,3,3,3};
Solution solution = new Solution(nums);

// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
solution.pick(3);

// pick(1) should return 0. Since in the array only nums[0] is equal to 1.
solution.pick(1);
~~~

#### Solution
O(n)生成equal probability random的算法，注意和384的区别
~~
public class Solution {

    Map<Integer, List<Integer>> map;
    Random random;

    public Solution(int[] nums) {
        map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = map.getOrDefault(nums[i], new ArrayList<Integer>());
            list.add(i);
            map.put(nums[i], list);
        }
        random = new Random();
    }

    public int pick(int target) {
        List<Integer> list = map.get(target);

        int num = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            int r = random.nextInt(i + 1);
            if (r == i) num = list.get(i);
        }
        return num;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */
~~~
