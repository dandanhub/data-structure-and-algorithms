public class ValidWordAbbr {
    // Solution 1
    // Map<String, List<String>> map;
    //
    // public ValidWordAbbr(String[] dictionary) {
    //     map = new HashMap<String, List<String>>();
    //     for (String str : dictionary) {
    //         String abbr = wordAbbr(str);
    //         if (map.containsKey(abbr)) {
    //             map.get(abbr).add(str);
    //         }
    //         else {
    //             List<String> list = new ArrayList<String>();
    //             list.add(str);
    //             map.put(abbr, list);
    //         }
    //     }
    // }
    //
    // private String wordAbbr(String str) {
    //     if (str.length() <= 2) {
    //         return str;
    //     }
    //
    //     StringBuilder sb = new StringBuilder();
    //     sb.append(str.charAt(0));
    //     sb.append(str.length() - 2);
    //     sb.append(str.charAt(str.length() - 1));
    //
    //     return sb.toString();
    // }
    //
    // public boolean isUnique(String word) {
    //     String abbr = wordAbbr(word);
    //     if (!map.containsKey(abbr)) {
    //         return true;
    //     }
    //
    //     List<String> list = map.get(abbr);
    //     for (int i = 0; i < list.size(); i++) {
    //         if (!list.get(i).equals(word)) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // Solution 2
    private Map<String, String> map;

    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap<String, String>();
        for (String str : dictionary) {
            String abbr = wordAbbr(str);
            if (!map.containsKey(abbr)) {
                map.put(abbr, str);
            }
            else if (!map.get(abbr).equals(str)) {
                map.put(abbr, "");
            }
        }
    }

    private String wordAbbr(String str) {
        if (str.length() <= 2) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0));
        sb.append(str.length() - 2);
        sb.append(str.charAt(str.length() - 1));

        return sb.toString();
    }

    public boolean isUnique(String word) {
        String abbr = wordAbbr(word);
        if (!map.containsKey(abbr)) {
            return true;
        }

        if (map.get(abbr).equals(word)) {
            return true;
        }
        return false;
    }
    /*
    test cases
    ["ValidWordAbbr","isUnique"]
    [[["a","a"]],["a"]]
    ["ValidWordAbbr","isUnique"]
    [[["hello"]],["hello"]]
    */
}

/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */
