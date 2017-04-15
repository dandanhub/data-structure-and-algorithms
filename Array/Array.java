public class Array {

  // 243. Shortest Word Distance
  public int shortestDistance(String[] words, String word1, String word2) {
        int min = Integer.MAX_VALUE;
        int pos1 = -1;
        int pos2 = -1;
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            if (str.equals(word1)) {
                pos1 = i;
            } else if (str.equals(word2)) {
                pos2 = i;
            }
            if (pos1 != -1 && pos2 != -1) {
                min = Math.min(min, Math.abs(pos1 - pos2));
            }
        }
        return min;
    }

  // 244. Shortest Word Distance II (Medium)
  public class WordDistance {
    private Map<String, List<Integer>> map;

    public WordDistance(String[] words) {
        map = new HashMap<String, List<Integer>>();
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            if (map.containsKey(str)) {
                map.get(str).add(i);
            } else {
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                map.put(str, list);
            }
        }
    }

    public int shortest(String word1, String word2) {
        int min = Integer.MAX_VALUE;
        if (!map.containsKey(word1) || !map.containsKey(word2)) {
            return min;
        }
        // we can do better by using linear probing
        // since word1List and word2List are sorted already,
        // we can use the idea of merge sort and perform the comparison in O(n + m) time,
        // rather than O(n * m)
        List<Integer> word1List = map.get(word1);
        List<Integer> word2List = map.get(word2);
        for (int i = 0; i< word1List.size(); i++) {
            for (int j = 0; j < word2List.size(); j++) {
                min = Math.min(min, Math.abs(word1List.get(i) - word2List.get(j)));
            }
        }
        return min;
      }

    /**
     * Your WordDistance object will be instantiated and called as such:
     * WordDistance obj = new WordDistance(words);
     * int param_1 = obj.shortest(word1,word2);
     */
   }

   // 245. Shortest Word Distance III
   public int shortestWordDistance(String[] words, String word1, String word2) {
        int min = Integer.MAX_VALUE;
        int pos1 = -1;
        int pos2 = -1;
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            if (str.equals(word1) && str.equals(word2)) {
              if (pos1 <= pos2) {
                  pos1 = i;
              }  else {
                  pos2 = i;
              }
            } else if (str.equals(word1)) {
                pos1 = i;
            } else if (str.equals(word2)) {
                pos2 = i;
            }
            if (pos1 != -1 && pos2 != -1) {
                min = Math.min(min, Math.abs(pos1 - pos2));
            }
        }
        return min;
    }
}
