## 600. Non-negative Integers without Consecutive Ones
Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary representations do NOT contain consecutive ones.

Example 1:
~~~
Input: 5
Output: 5
Explanation:
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.
~~~
Note: 1 <= n <= 109

#### Solution
~~~
public class Solution {
    public int findIntegers(int num) {
        if (num <= 0) return 0;

        String binStr = Integer.toBinaryString(num);
        int len = binStr.length();

        int[] a = new int[len]; // num of binary string ends with 0
        int[] b = new int[len]; // num of binary string ends with 1
        a[0] = 1;
        b[0] = 1;

        for (int i = 1; i < len; i++) {
            a[i] = a[i - 1] + b[i - 1];
            b[i] = a[i - 1];
        }

        // for (int i = 0; i < len; i++) {
        //     System.out.println(i + ":" + a[i] + " " + b[i]);
        // }

        int res = a[len - 1] + b[len - 1];
        for (int i = 1; i < len; i++) {
            if (binStr.charAt(i) == '0' && binStr.charAt(i - 1) == '0') {
                res -= b[len - i - 1];
            }
            else if (binStr.charAt(i) == '1' && binStr.charAt(i - 1) == '1') break;
        }

        return res;

    }
}
~~~

~~~
第一轮： Non-negative Integers without Consecutive Ones
第二轮： LC 254 Factor Combinations
第三轮： LC 上那道dungeon games. 我记得是hard难度的. from: 1point3acres.com/bbs
第四轮： 设计Monitor system来管理各个node报告的service exception.
第五轮： LC 291 Word Pattern II
面的不是很理想，但这个公司是不错的，很多组员都是从linkedin, google, facebook过来的。收到拒信很沮丧。
~~~
