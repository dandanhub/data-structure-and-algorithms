// 给一个list of words，当且仅当word2可以由word1通过插入一个字母达到的时候，可以将word2放在word1之后，比如. Waral 鍗氬鏈夋洿澶氭枃绔ð,
// a-ab-acb-tabc-tabqc- tabpqc.
// 每个这样的结构必须从长度为1的单词开始，求返回list中word可以组成的长度最长的结构的最后一个单词，如果上面的例子就是tabpqc。
// 相当水了，大概讲了5分钟思路，写了10分钟，go through了code，analysis，我说可能有地方可以improve但是我暂时想不到，有没有提示什么的，他说他也不知道，looks good。

import java.util.*;
import java.io.*;

class Solution {
    public static void main(String[] args) {
        String[] strs = {"a","ab","acb","abc","tabc","tabqc","tabpqc","tabpc", "tabpcd", "tabpdcd"};
        List<String> list = Arrays.asList(strs);
        System.out.println(longestWord(list));
        // System.out.println(isReachable("abc", "abec"));

    }

    public static String longestWord(List<String> list) {
        if(list == null || list.size() == 0) return "";

        Map<Integer, Set<String>> map = new HashMap<Integer, Set<String>>();
        for (String str : list) {
            int len = str.length();
            Set<String> set = map.getOrDefault(len, new HashSet<String>());
            set.add(str);
            map.put(len, set);
        }

        if (!map.containsKey(1)) return "";
        Set<String> currSet = map.get(1);
        int size = 1;

        while (true) {
            if (!map.containsKey(size + 1)) {
                return currSet.iterator().next();
            }

            Set<String> nextSet = map.get(size + 1);
            Set<String> candidatesSet = new HashSet<String>();
            for (String curr : currSet) {
                for (String next : nextSet) {
                    if (isReachable(curr, next)) {
                        candidatesSet.add(next);
                    }
                }
            }

            // next loop
            if (candidatesSet.size() == 0) {
                return currSet.iterator().next();
            }
            else {
                currSet = candidatesSet;
                size++;
            }
        }
    }

    private static boolean isReachable(String str1, String str2) {
        if (str1.length() + 1 != str2.length()) return false;

        int i = 0, j = 0, diff = 0;
        while (i < str1.length() && j < str2.length() && diff <= 1) {
            if (str1.charAt(i) != str2.charAt(j)) {
                diff++;
                j++;
            } else {
                i++;
                j++;
            }
        }

        return i == str1.length();
    }
}
