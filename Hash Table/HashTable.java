public class HashTable {
    // 202. Happy Number
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<Integer>();
        set.add(n);
        while (n != 1) {
            n = sum(n);
            if (set.contains(n)) {
                return false;
            }
            set.add(n);
        }
        return true;
    }

    private int sum(int n) {
        int ans = 0;
        int digit = 0;
        while (n != 0) {
            digit = n % 10;
            n = n / 10;
            ans += digit * digit;
        }
        return ans;
    }

    // 187. Repeated DNA Sequences
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String sub = s.substring(i, i + 10);
            if (map.containsKey(sub)) {
                map.put(sub, map.get(sub) + 1);
            }
            else {
                map.put(sub, 1);
            }
        }

        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            if (entry.getValue() > 1) {
                ans.add(entry.getKey());
            }
        }
        return ans;
    }

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

    // 290. Word Pattern
    public boolean wordPattern(String pattern, String str) {
        if (pattern == null || pattern.length() == 0) {
            return str == null || str.length() == 0;
        }

        String[] tokens = str.split("\\s");
        if (pattern.length() != tokens.length) {
            return false;
        }
        Map<Character, String> map = new HashMap<Character, String>();
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            String s = tokens[i];
            if (!map.containsKey(ch)) {
                if (map.containsValue(s)) {
                    return false;
                }
                map.put(ch, s);
            }
            else if (!s.equals(map.get(ch))) {
                return false;
            }
        }

        return true;
    }
}
