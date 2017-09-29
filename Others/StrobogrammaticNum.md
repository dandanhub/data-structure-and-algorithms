## 246. Strobogrammatic Number
A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Write a function to determine if a number is strobogrammatic. The number is represented as a string.

For example, the numbers "69", "88", and "818" are all strobogrammatic.

#### Solution

~~~
class Solution {
	public boolean isStrobogrammatic(String num) {
		Map<Character, Character> map = new HashMap<Character, Character>();
        map.put('6', '9');
        map.put('9', '6');
        map.put('8', '8');
        map.put('1', '1');
        map.put('0', '0');
        int s = 0;
        int e = num.length() - 1;
        while (s <= e) {
	        char ch = num.charAt(s);
	        if (!map.containsKey(ch) || num.charAt(e) != map.get(ch)) {
	        return false;
            }
            s++;
            e--;
        }
        // // check the digit in middle
        // if (s == e) {
        // return num.charAt(s) == '8' || num.charAt(s) == '1'
        //         || num.charAt(s) == '0';
        // }
        return true;
    }
}

~~~
