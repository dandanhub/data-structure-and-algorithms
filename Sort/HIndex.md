## 274. H-Index
Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."

For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively. Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, his h-index is 3.

Note: If there are several possible values for h, the maximum one is taken as the h-index.

#### Solution
Method 1: Sort then count, time O(nlgn)
~~~
class Solution {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        Arrays.sort(citations);
        int len = citations.length;
        int i = 0;
        while (i < len && citations[len - 1 - i] > i) {
            i++;
        }
        return i;
    }
}
~~~

Method 2: can you improve it to O(n)?
~~~
~~~

## 275. H-Index II
Follow up for H-Index: What if the citations array is sorted in ascending order? Could you optimize your algorithm?

#### Solution
Apply binary search
~~~
class Solution {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        int len = citations.length;
        int l = 0;
        int r = len - 1;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            if (citations[len - 1 - m] > m) {
                l = m + 1;
            }
            else {
                r = m - 1;
            }
        }
        return l;
    }
}
~~~
