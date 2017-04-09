public class Solution {
    // 131. Palindrome Partitioning
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<List<String>>();
        if (s == null) {
            return ans;
        }
        if (s.length() == 0) {
            ans.add(new ArrayList<String>());
            return ans;
        }
        backtracking(ans, new ArrayList<String>(), s);
        return ans;
    }

    // Classical backtracking trick
    private void backtracking(List<List<String>> ans, List<String> list, String s) {
        if (s.length() == 0) {
            List<String> newList = new ArrayList<String>(list);
            ans.add(newList);
        }

        for (int i = 1; i <= s.length(); i++) {
            String substr = s.substring(0, i);
            if (isPalindrome(substr)) {
                list.add(substr);
                backtracking(ans, list, s.substring(i));
                list.remove(list.size() - 1);
            }
        }
    }

    // Check whether a given string is a valid palindrome or not
    private boolean isPalindrome(String s) {
        if (s == null) {
            //TO-DO;
        }
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /*
    // 132. Palindrome Partitioning II
    // Backtracking time out
    public int minCut(String s) {
        if (s == null) {
            // TO-DO
        }
        if (s.length() == 0 || isPalindrome(s)) {
            return 0;
        }

        int minCount = Integer.MAX_VALUE;
        for (int i = 1; i < s.length(); i++) {
            String substr = s.substring(0, i);
            if (isPalindrome(substr)) {
                int next = minCut(s.substring(i));
                minCount = Math.min(minCount, next + 1);
            }
        }
        return minCount;
    }

    // Check whether a given string is a valid palindrome or not
    private boolean isPalindrome(String s) {
        if (s == null) {
            //TO-DO;
        }
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    */

    /*
    test case
    "fifgbeajcacehiicccfecbfhhgfiiecdcjjffbghdidbhbdbfbfjccgbbdcjheccfbhafehieabbdfeigbiaggchaeghaijfbjhi"
    */

    // 132. Palindrome Partitioning II
    // Dynamic Programming
    public int minCut(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int len = s.length();
        boolean[][] pal = new boolean[len][len];
        int[] dp = new int[len];

        for (int i = 0; i < len; i++) {
            dp[i] = i;
            for (int j = 0; j < i; j++) {
                if (s.charAt(j) == s.charAt(i) && (j + 1 >= i - 1 || pal[j + 1][i - 1] == true)) {
                    dp[i] = Math.min(dp[i], j == 0 ? 0 : dp[j - 1] + 1);
                    pal[j][i] = true;
                }
                else {
                    dp[i] = Math.min(dp[i], dp[i - 1] + 1);
                }
            }
        }
        return dp[len - 1];
    }
    /*
    test case
    ""
    "a"
    "aa"
    "ab"
    "aacecaaa"
    "aaaaaaa"
    "aaacec"
    "abcddcbaaa"
    */

    // 266. Palindrome Permutation (Easy)
    public boolean canPermutePalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true; // TO-DO
        }

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            }
            else {
                map.put(ch, 1);
            }
        }

        boolean odd = false;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                if (odd == false) {
                    odd = true;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    // 267. Palindrome Permutation II
    public List<String> generatePalindromes(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            }
            else {
                map.put(ch, 1);
            }
        }

        String odd = "";
        List<Character> chars = new ArrayList<Character>();
        int size = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                if (odd.length() == 0) {
                    for (int i = 0; i < entry.getValue() / 2; i++) {
                        chars.add(entry.getKey());
                    }
                    odd = String.valueOf(entry.getKey());
                }
                else {
                    return ans;
                }
            }
            else {
                for (int i = 0; i < entry.getValue() / 2; i++) {
                    chars.add(entry.getKey());
                }
            }
        }

        Collections.sort(chars);
        backtracking(ans, new StringBuilder(), chars, new boolean[chars.size()], odd);

        return ans;
    }

    private void backtracking(List<String> list, StringBuilder sb, List<Character> chars, boolean[] used, String odd) {
        if (sb.length() == chars.size()) {
            list.add(sb.toString() + odd + sb.reverse().toString());
            sb.reverse();
            return;
        }

        for (int i = 0; i < chars.size(); i++) {
            if (i > 0 && chars.get(i) == chars.get(i - 1) && !used[i - 1]) {
                continue;
            }

            if (!used[i]) {
                used[i] = true;
                sb.append(chars.get(i));
                backtracking(list, sb, chars, used, odd);
                sb.deleteCharAt(sb.length() - 1);
                used[i] = false;
            }
        }

        // 214. Shortest Palindrome


        /*
        test cases
        ""
        "a"
        "ab"
        "abc"
        "abcd"
        "aacecaaa"
        "abcd"
        "aaaaaaaaaaabbbbbbcccc"
        "aaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbccccaaaaaaaaaaabbbbbbcccc"
        "ababbbabbaba"
        */
    }
}
