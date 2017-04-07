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
}
