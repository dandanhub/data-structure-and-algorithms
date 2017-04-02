import java.util.Stack;

public class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0; // how to handle the corner case?
        }
        
        s = s.replaceAll("\\s","");
        if (!s.contains("(") && !s.contains(")")) {
            return calculateHelper(s);
        }
        
        int pos = 0;
        int len = s.length();
        Stack<String> stack = new Stack<String>();
        while (pos < len) {
            if (s.charAt(pos) == ')') {
                StringBuilder sb = new StringBuilder();
                String prev = stack.pop();
                while (!prev.equals("(")) {
                    sb.insert(0, prev);
                    prev = stack.pop();
                }
                int ans = calculateHelper(sb.toString());
                stack.push(Integer.toString(ans));
            }
            else {
                stack.push(String.valueOf(s.charAt(pos)));
            }
            pos++;
        }
        
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            String str = stack.pop();
            sb.insert(0, str);
        }
        
        return calculateHelper(sb.toString());
    }
    
    private int calculateHelper(String s) {
        int index = 0;
        int len = s.length();
        int ans = 0;
        char operator = '+';
        while (index < len) {
            StringBuilder sb = new StringBuilder();
            if (s.charAt(index) == '-') {
                operator = operator == '+' ? '-' : '+';
                index++;
            }
            while (index < len && s.charAt(index) != '+' && s.charAt(index) != '-') {
                sb.append(s.charAt(index++));
            }
            
            if (operator == '+') {
                ans += Integer.parseInt(sb.toString());
            }
            else {
                ans -= Integer.parseInt(sb.toString());
            }
            if (index < len) {
                operator = s.charAt(index++);
            }
        }
        return ans;
    }
    
    public static void main(String[] args) {
        Solution s = new Solution();
        String x = "2-(5-6)";
        System.out.println(x);
        System.out.println(s.calculate(x));
    }
    
    /*
    test case:
    ""
    "   "
    "0"
    "1 + 1"
    " 2-1 + 2 "
    "(1+(4+5+2)-3)+(6+8)"
    "(1+(4+5+2)-3)+((6 + 8) + (6+8))"
    "2-(5-6)"
    */
}