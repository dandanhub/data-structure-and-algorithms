## 118. Pascal's Triangle
Given numRows, generate the first numRows of Pascal's triangle.

For example, given numRows = 5,
Return
~~~
[
     [1],
    [1,1],
   [1,2,1],
  [1,3,3,1],
 [1,4,6,4,1]
]
~~~

#### Solution
~~~
class Solution {
    public List<List<Integer>> generate(int numRows) {
        if (numRows <= 0) {
            return new ArrayList<List<Integer>>();
        }

        List<List<Integer>> ans = new ArrayList<List<Integer>>();

        // add first list
        List<Integer> first = new ArrayList<Integer>();
        first.add(1);
        ans.add(first);

        for (int i = 1; i < numRows; i++) {
            List<Integer> item = new ArrayList<Integer>();
            item.add(1);
            for (int j = 1; j < i; j++) {
                int num = ans.get(i - 1).get(j - 1) + ans.get(i - 1).get(j);
                item.add(num);
            }
            item.add(1);
            ans.add(item);
        }
        return ans;
    }
}
~~~

## 119. Pascal's Triangle II
Given an index k, return the kth row of the Pascal's triangle.

For example, given k = 3,
Return [1,3,3,1].

Note:
Could you optimize your algorithm to use only O(k) extra space?

#### Solution

~~~
class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            list.add(1);
            for (int j = i - 1; j > 0; j--) {
                int num = list.get(j) + list.get(j - 1);
                list.set(j, num);
            }
        }
        return list;
    }
}
~~~
