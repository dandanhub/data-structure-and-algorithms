## 217. Contains Duplicate (Easy)
Given an array of integers, find if the array contains any duplicates. Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.

#### Solution

~~~
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
        }
        return false;
    }
}
~~~

## 219. Contains Duplicate II
Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at most k.

#### Solution (33.82%)

~~~
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return false;

        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (set.size() > k) {
                int prev = nums[i - k - 1];
                set.remove(prev);
            }
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
        }

        return false;
    }
}
~~~

## 220. Contains Duplicate III
Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.

#### Solution (TreeSet)

Method 1: TreeSet O(nlgk)
~~~
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0 || t < 0) return false;

        TreeSet<Long> treeSet = new TreeSet<Long>();
        for (int i = 0; i < nums.length; i++) {
            long num = (long) nums[i];

            Long ceil = treeSet.ceiling(num - t);
            if (ceil != null && ceil <= num + t) return true;

            treeSet.add(num);
            if (treeSet.size() > k) {
                long prev = (long) nums[i - k];
                treeSet.remove(prev);
            }
        }

        return false;
    }
}
~~~

- TreeSet
  - guarantees log(n) time cost for the basic operations (add, remove and contains)
  - guarantees that elements of set will be sorted (ascending, natural, or the one specified by you via its constructor) (implements SortedSet)
  - doesn't offer any tuning parameters for iteration performance
  - offers a few handy methods to deal with the ordered set like first(), last(), headSet(), and tailSet() etc（本题就用了ceiling()和flooring()两个method）

- ceiling
  - Returns the least element in this set greater than or equal to the given element, or null if there is no such element.

- public SortedSet<E> subSet(E fromElement, E toElement)
  - Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive. (If fromElement and toElement are equal, the returned set is empty.) The returned set is backed by this set, so changes in the returned set are reflected in this set, and vice-versa. The returned set supports all optional set operations that this set supports.
