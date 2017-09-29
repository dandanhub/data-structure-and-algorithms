## 294. Flip Game II
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

#### Solution
Method 1: 我想到的Backtrack 219ms 18.87% <br>
Without memo <br>
Time complexity: O(n!)
~~~
class Solution {
    public boolean canWin(String s) {
        if (s == null || s.length() < 2) return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' && i + 1 < s.length() && s.charAt(i + 1) == '+') {
                StringBuilder sb = new StringBuilder();
                sb.append(s.substring(0, i)).append("--").append(s.substring(i + 2));
                if (!canWin(sb.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
~~~

Memo: With Memo 24ms 65% <br>
Time complexity:
~~~
class Solution {
    Map<String, Boolean> map = new HashMap<String, Boolean>();
    public boolean canWin(String s) {
        if (s == null || s.length() < 2) return false;
        if (map.containsKey(s)) return map.get(s);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' && i + 1 < s.length() && s.charAt(i + 1) == '+') {
                StringBuilder sb = new StringBuilder();
                sb.append(s.substring(0, i)).append("--").append(s.substring(i + 2));
                if (!canWin(sb.toString())) {
                    map.put(s, true);
                    return true;
                }
            }
        }
        map.put(s, false);
        return false;
    }
}
~~~
