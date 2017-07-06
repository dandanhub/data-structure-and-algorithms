## 273. Integer to English Words
Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.

For example,
~~~
123 -> "One Hundred Twenty Three"
12345 -> "Twelve Thousand Three Hundred Forty Five"
1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
~~~

#### Solution
1. 20以下的数字可以直接用一个word表示
2. 如何快速写出简洁代码

Do the dirty job elegantly. :)
~~~
public class Solution {
    public String numberToWords(int num) {
        // edge cases
        if (num < 0) return "";
        if (num == 0) return "Zero";

        return helper(num);
    }

    private String helper(int num) {
        String[] tens = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] digits = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                           "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
                           "Eighteen", "Nineteen"};

        StringBuilder sb = new StringBuilder();

        if (num < 20) { // base case
            sb.append(digits[num]);
        }
        else if (num < 100) { // base case
            sb.append(tens[num/10]).append(" ").append(digits[num%10]);
        }
        else if (num < 1000) {
            sb.append(helper(num / 100)).append(" Hundred ").append(helper(num % 100));
        }
        else if (num < 1000000) {
            sb.append(helper(num / 1000)).append(" Thousand ").append(helper(num % 1000));
        }
        else if (num < 1000000000) {
            sb.append(helper(num / 1000000)).append(" Million ").append(helper(num % 1000000));
        }
        else {
            sb.append(helper(num / 1000000000)).append(" Billion ").append(helper(num % 1000000000));
        }

        return sb.toString().trim();
    }
}
~~~

Verbose Version: took me 40 mins
~~~
public class Solution {
    public String numberToWords(int num) {
        // edge cases
        if (num < 0) return "";
        if (num == 0) return "Zero";

        // pre-defined En words
        String[] units = {"Billion", "Million", "Thousand", ""};
        int[] bases = {1000000000, 1000000, 1000, 1};
        StringBuilder sb = new StringBuilder();

        // parse three digits one by one
        for (int i = 0; i < 4; i++) {
            int index = num / bases[i];
            num = num % bases[i];
            if (index == 0) continue; // skip high unit for small num
            String hundredWord = parseHundredToWord(index);
            sb.append(hundredWord).append(units[i]).append(" ");
        }

        return sb.toString().trim();
    }

    private String parseHundredToWord(int num) {
        String hundred = "Hundred";
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] digits = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                           "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
                           "Eighteen", "Nineteen"};
        int[] bases = {100, 10, 1};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (num == 0) break; // 是0的时候直接跳出

            // 注意20以下的数字可以直接用一个word表示
            if (num < 20) {
                sb.append(digits[num]).append(" ");
                break;
            }

            int index = num / bases[i];
            num = num % bases[i];
            if (index == 0) continue; // skip empty
            if (i == 0) sb.append(digits[index]).append(" ").append(hundred).append(" ");
            else if (i == 1) sb.append(tens[index]).append(" ");
        }

        return sb.toString();
    }
}
~~~
