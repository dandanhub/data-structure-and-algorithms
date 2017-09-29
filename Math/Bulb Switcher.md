## 319. Bulb Switcher
There are n bulbs that are initially off. You first turn on all the bulbs. Then, you turn off every second bulb. On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb. Find how many bulbs are on after n rounds.

Example:
~~~
Given n = 3.

At first, the three bulbs are [off, off, off].
After first round, the three bulbs are [on, on, on].
After second round, the three bulbs are [on, off, on].
After third round, the three bulbs are [on, off, off].

So you should return 1, because there is only one bulb is on.
~~~

#### Solution
一个灯泡最后亮着如果被toggle了奇数次，bulb b会在第i轮被toggle，如果i是b的divisor.
对于一个数divisor是成对出现的，除非这个是divisor的平方，所以这题就是求1-n里有多少个可以开平方的数

~~~
class Solution {
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }
}
~~~
