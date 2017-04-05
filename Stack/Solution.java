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
    
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<String>();
        String[] paths = path.split("/");
        int len = paths.length;
        for (int i= 0; i < len; i++) {
            if(paths[i].length() == 0) {
                continue;
            }
            
            if (paths[i].equals("..") && !stack.empty()) {
                stack.pop();
            }
            else if (!paths[i].equals(".") && !paths[i].equals("..")) {
                stack.push(paths[i]);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        
        if (stack.empty()) {
            sb.append("/");
        }
        
        while (!stack.empty()) {
            sb.insert(0, stack.pop());
            sb.insert(0, "/");
        }
        
        return sb.toString();
    }
    
    public int evalRPN(String[] tokens) {
        int res = 0;
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < tokens.length; i++) {
            if ((tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") 
                    || tokens[i].equals("/")) && !stack.empty()) {
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                switch (tokens[i]) {
                    case "+":
                        res = num2 + num1;
                        break;
                    case "-":
                        res = num2 - num1;
                        break;  
                    case "*":
                        res = num2 * num1;
                        break;
                    case "/":
                        res = num2 / num1;
                        break;
                }
                stack.push(Integer.toString(res));
            }
           else {
                stack.push(tokens[i]);
            }
        }
        if (!stack.empty()) {
            res = Integer.parseInt(stack.pop());
        }
        return res;
    }

    // 255. Verify Preorder Sequence in Binary Search Tree
    // Solution 1
    public boolean verifyPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return true;
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < preorder.length; i++) {
            if (stack.isEmpty() || preorder[i] < stack.peek()) {
                if (preorder[i] < prev) {
                    return false;
                }
                stack.push(preorder[i]);
            }
            else {
                while (!stack.isEmpty() && preorder[i] > stack.peek()) {
                    prev = stack.pop();
                }
                stack.push(preorder[i]);
            }
        }
        return true;
    }

    /*
    // 255. Verify Preorder Sequence in Binary Search Tree
    // Solution 2
    public boolean verifyPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return true;
        }
        
        int prev = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < preorder.length; i++) {
            if (preorder[i] < prev) {
                return false;
            }
            while (index >= 0 && preorder[i] > preorder[index]) {
                prev = preorder[index];
                index--;
            }
        
            preorder[++index] = preorder[i];
        }
        return true;
    }
    */
}