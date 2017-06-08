public class Solution {
    /*
    * 28. Implement strStr()
    * Using KMP
    */
    public int strStr(String haystack, String needle) {
        // "", "", return 0
        if (needle == null || needle.length() == 0) return 0;
        if (haystack == null || haystack.length() == 0) return -1;

        int[] KMP = buildKMP(needle);
        int i = 0, j = 0;
        // O(n)
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
                i++;
            }
            else {
                if (j > 0) {
                    // 移动位数 = 已匹配的字符数 - 对应的部分匹配值
                    j = KMP[j - 1]; // move j to KMP[j - 1] and keep i in cur pos
                }
                else { // the first char not matching
                  i++;
                }
            }
        }
        if (j == needle.length()) return i - needle.length();
        return -1;
    }

    // O(m)
    private int[] buildKMP(String str) {
        if (str == null || str.length() == 0) return new int[0];

        int len = str.length();
        int[] KMP = new int[len];
        int index = 0;
        for (int i = 1; i < len; i++) {
            index = KMP[i - 1];
            if (str.charAt(index) == str.charAt(i)) {
                KMP[i] = index + 1;
            }
            else {
                while (index > 0 && str.charAt(i) != str.charAt(index)) {
                    index = KMP[index - 1];
                }
                if (str.charAt(i) == str.charAt(index)) KMP[i] = index + 1;
            }
        }
        System.out.println(Arrays.toString(KMP));
        return KMP;
    }
}
