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

    // 73. Set Matrix Zeroes
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        int corner = 1;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                corner = 0;
            }
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j > 0 ; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (corner == 0) {
                matrix[i][0] = 0;
            }
        }
    }


    // 220. Contains Duplicate III
    // 1. Naiive Solution O(nk) will get time out.
    // public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    //     if (nums == null || nums.length == 0) return false;
    //     if (k <= 0) return false;
    //     if (t < 0) return false;
    //
    //     long cap = (long) t;
    //     long diff = 0L;
    //     for (int i = 0; i < nums.length - 1; i++) {
    //         for (int j = 1; j <= k && i + j < nums.length; j++) {
    //             diff = Math.abs((long) nums[i] - (long) nums[i + j]);
    //             if (diff <= cap) return true;
    //         }
    //     }
    //     return false;
    // }

    // 220. Contains Duplicate III
    // 2. Top solution Using bucket from leetcode discussion O(n)
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0) return false;
        if (k <= 0 || t < 0) return false;

        Map<Long, Long> map = new HashMap<Long, Long>();
        for (int i = 0; i < nums.length; i++) {
            long num = (long) nums[i] - Integer.MIN_VALUE;
            long bucketNum = num / ((long) t + 1);
            if (map.containsKey(bucketNum)
                || (map.containsKey(bucketNum - 1) && num - map.get(bucketNum - 1) <= t)
                || (map.containsKey(bucketNum + 1) && map.get(bucketNum + 1) - num <= t)) {
                return true;
            }
            if (map.entrySet().size() >= k) {
                long last = ((long) nums[i - k] - Integer.MIN_VALUE) / ((long) t + 1);
                map.remove(last);
            }
            map.put(bucketNum, num);
        }
        return false;
    }

    // 220. Contains Duplicate III
    // 3. O(nlogk) using TreeSet
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0) return false;
        if (k <= 0 || t < 0) return false;

        SortedSet<Long> set = new TreeSet<Long>();
        for (int i = 0; i < nums.length; i++) {
            SortedSet<Long> subset = set.subSet((long)nums[i] - t, (long)nums[i] + t + 1);
            if (subset != null && subset.size() != 0 ) return true;
            if (set.size() >= k) set.remove((long)nums[i - k]);
            set.add((long)nums[i]);
        }
        return false;
    }
}
