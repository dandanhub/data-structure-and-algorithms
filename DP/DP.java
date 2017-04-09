public class DP {

  //139. Word Break
  public boolean wordBreakI(String s, List<String> wordDict) {
        // s is non-empty
        // wordDict containing a list of non-empty words
        if (wordDict == null || wordDict.size() == 0) {
            return false;
        }

        Set<String> set = new HashSet<String>();
        for (String str : wordDict) {
            set.add(str);
        }
        int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (dp[j] && set.contains(s.substring(j, i + 1))) {
                    dp[i + 1] = true;
                    break;
                }
            }
        }
        return dp[len];
    }

  // 140. Word Break II
  public List<String> wordBreak(String s, List<String> wordDict) {
      // a non-empty string s and a dictionary wordDict containing a list of non-empty words
      Set<String> wordSet = new HashSet<String>();
      for (String str : wordDict) {
          wordSet.add(str);
      }

      return wordBreakHelper(s, wordSet, new HashMap<String, List<String>>());
  }

  private List<String> wordBreakHelper(String str, Set<String> wordSet, HashMap<String, List<String>> map) {
      if (map.containsKey(str)) {
          return map.get(str);
      }

      List<String> list = new ArrayList<String>();
      if (str.length() == 0) {
          list.add("");
          return list;
      }

      for (int i = 0; i < str.length(); i++) {
          String word = str.substring(0, i + 1);
          if (wordSet.contains(word)) {
              List<String> sublist = wordBreakHelper(str.substring(i + 1), wordSet, map);
              for (String sub : sublist) {
                  list.add(word + (sub.length() == 0 ? "" : " ") + sub);
              }
          }
      }
      map.put(str, list);
      return list;
  }
}
