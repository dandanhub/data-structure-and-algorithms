1. Longest Palindrome
给出一个字符串，要返回字符串内最长的回文串的长度

~~~
class Solution {
    public String longestPalindrome(String s) {
        if (s == null) return null;
        if (s.length() == 0) return "";

        int left = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            int l1 = helper(s, i, i);
            int l2 = helper(s, i, i + 1);
            int len = Math.max(l1, l2);
            if (len > right - left + 1) {
                left = i - (len - 1 ) / 2;
                right = i + len / 2;
            }
        }
        return s.substring(left, right + 1);
    }

    private int helper(String s, int left, int right) {
        while (left >= 0 && right < s.length()) {
            if (s.charAt(left) != s.charAt(right)) break;
            left--;
            right++;
        }

        return right - left - 1;
    }
}
~~~

2. Windows Sum
给出一个integer的list或者数组和一个k size的窗口，返回这个list或数组的所有window sum，例如：
[1,3,6,7,11], window size为3，返回[10,16,24] (10=1+3+6，16=3+6+7，24=6+7+11)
注意(arraylist == null || arraylist.size() == 0)要return一个已经初始化的arrayList而不是null，否则会有一个test case过不去

~~~
import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
    public static void main(String[] args) {
        Integer[] arr = {1,3,6,7,11};
        List<Integer> list = Arrays.asList(arr);
        List<Integer> ans = getSum(list, 3);
        System.out.println(ans.toString());
    }

    public static List<Integer> getSum(List<Integer> list, int k) {
        if (list == null || list.size() == 0) {
            return new ArrayList<Integer>();
        }

        List<Integer> ans = new ArrayList<Integer>();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
            if (i >= k) {
                sum -= list.get(i - k);
            }

            if (i >= k - 1) {
                ans.add(sum);
            }
        }
        return ans;
    }
}

~~~

3. Rectangle Overlap
Rectangle Overlap。这题和leetcode 算相交面积的区别：它帮你定义好两个类，一个叫Point，一个叫Rectangle，Rectangle包括左上右下两个Point, Point包括x, y的值.
一个长方形用左上和右下两个的代表，题目给出4个点Point(x,y)，前两个点表示第一个长方形，后两个表示第二个长方形，返回是否重叠了
~~~
// Returns true if two rectangles (l1, r1) and (l2, r2) overlap
public bool doOverlap(Point l1, Point r1, Point l2, Point r2)
{
    // sanity check
    if (l1 == null || r1 == null || l2 == null || r2 == null) {
        return false;
    }

    // If one rectangle is on left side of other
    if (l1.x > r2.x || l2.x > r1.x)
        return false;

    // If one rectangle is above other
    if (l1.y < r2.y || l2.y < r1.y)
        return false;

    return true;
}
~~~

~~~
int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
    int left = max(A,E);
    int right = max(min(C,G), left);
    int bottom = max(B,F);
    int top = max(min(D,H), bottom);
    return (C-A)*(D-B) - (right-left)*(top-bottom) + (G-E)*(H-F);
}
~~~

4. K Closest Five
一个组织发现了外星人，要给他们通信。我们的任务是给太空中的一些有可能有外星人的点发射信号。但是由于天线质量差（真是奇怪的理由），只能给太空中的 k 个点发射信号。现在又已知一个点P，它的坐标是(0,0)，这个点周围最有可能有外星人。好了，给你N个点， 找到这个N个点中离原点P最近的k个。
实质：根据 xx + yy给一个数组排序么。
给出一个List<Point>Point是定义好的类，属性就是x和y坐标，返回离原点(0,0)最近的k个点
~~~
/*
public class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
*/

public List<Point> findKClosest(Point[] p, int k) {
    PriorityQueue<Point> pq = new PriorityQueue<>(10, new Comparator<Point>() {
        @Override
        public int compare(Point a, Point b) {
            return (b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y);
        }
    });

    for (int i = 0; i < p.length; i++) {
        if (i < k)
            pq.offer(p[i]);
        else {
            Point temp = pq.peek();
            if ((p[i].x * p[i].x + p[i].y * p[i].y) - (temp.x * temp.x + temp.y * temp.y) < 0) {
                pq.poll();
                pq.offer(p[i]);
            }
        }
    }

    List<Point> x = new ArrayList<>();
    while (!pq.isEmpty())
        x.add(pq.poll());

    return x;
}
~~~

5. High Five
给出一个List<Score>，Score两个属性，student id和分数，保证每个student id会有至少5个分数，返回类型是一个map，key是student id，value是这个id所有分数里面最高5个点平均分
~~~
class Solution {

    public static void main(String[] args) {
        Result r1 = new Result(1, 95);
        Result r2 = new Result(1, 95);
        Result r3 = new Result(1, 91);
        Result r4 = new Result(1, 91);
        Result r5 = new Result(1, 93);
        Result r6 = new Result(1, 105);

        Result r7 = new Result(2, 6);
        Result r8 = new Result(2, 6);
        Result r9 = new Result(2, 7);
        Result r10 = new Result(2, 6);
        Result r11 = new Result(2, 6);
        Result[] arr = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11};
        Map<Integer, Double> res = getHighFive(arr);

        System.out.println(res.get(1) + " " +res.get(2));
    }

    static class Result{
        int id;
        int value;
        public Result(int id, int value){
            this.id = id;
            this.value = value;
        }
    }

    public static Map<Integer, Double> getHighFive(Result[] results){
        Map<Integer, List<Integer>> resultsMap = new HashMap<Integer, List<Integer>>();
        for (Result res : results){
            int id = res.id;
            List<Integer> list = resultsMap.getOrDefault(id, new ArrayList<Integer>());
            list.add(id);
            resultsMap.put(id, list);
        }

        Map<Integer, Double> map = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, List<Integer>> entry : resultsMap.entrySet()) {
            int id = entry.getKey();
            List<Integer> list = entry.getValue();
            Collections.sort(list);
            double value = 0.0;
            for (int k = 0; k < 5; k++) {
                value += list.get(k);
            }
            map.put(id, value / 5.0);
        }

        return map;
    }
}
~~~

6. Minimum Spanning Tree
给出一个List，Connection类有3个属性，String name1，String name2，int cost，表示在城市1和城市2之间有有个连接，费用为cost。要求在给出的List里面找出能以最小cost总和把所有城市连接起来而且没有环的Connection集合，返回一个List(要求先按城市1的名字排序，相同的按城市2)，如果找不到这样的环，或者说本来输入的List就不能把所有的城市连接起来的话，返回null。
例子1:
输入[[A,B,3],[A,C,3],[B,C,4]]，返回[[A,B,3],[A,C,3]]，因为能连起来且cost之和为6，比[[A,B,3],[B,C,4]]或[[A,C,3],[B,C,4]]的7小
例子2:
输入[[A,B,3],[A,C,3],[B,C,4],[D,E,4]]，返回null，因为不能找到把ABCDE都连起来的Connection集合，或者说连通块数量不为1
~~~
import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {

    public static void main(String[] args) {
        Connection c1 = new Connection("A", "D", 1);
        Connection c2 = new Connection("A", "B", 3);
        Connection c3 = new Connection("D", "B", 3);
        Connection c4 = new Connection("B", "C", 1);
        Connection c5 = new Connection("C", "D", 1);
        Connection c6 = new Connection("E", "D", 6);
        Connection c7 = new Connection("C", "E", 5);
        Connection c8 = new Connection("C", "F", 4);
        Connection c9 = new Connection("E", "F", 2);
        List<Connection> list = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9));
        List<Connection> result = findMinimum(list);
        for (Connection conn : result) {
            System.out.println(conn.city1 + "-" + conn.cost + "-" + conn.city2);
        }
    }

    public static class Connection {
        String city1;
        String city2;
        int cost;
        public Connection(String a, String b, int c) {
            city1 = a;
            city2 = b;
            cost = c;
        }
    }

    public static class UnionFind {
        Map<String, Integer> map; //map中装的是城市->城市所在的集合代号
        int setNum; //城市集合代号
        public UnionFind() {
            map = new HashMap<>();
            setNum = 0;
        }

        /**
         * 对每一个connection做union操作
         * 如果没有union操作，返回FALSE，如果有union操作，返回TRUE
         * 这里跟算法描述中不太一样：这里合并过的城市才会被分配集合代号
         */
        public boolean union(Connection conn) {
            // 两个城市都没有城市代号（都存在于单独的集合，都没有被合并过）
            if (!map.containsKey(conn.city1) && !map.containsKey(conn.city2)) {
                map.put(conn.city1, setNum);
                map.put(conn.city2, setNum);
                setNum++;
                return true;
            }
            // 有一个城市有代号（说明其中有一个是之前合并过的）
            if (map.containsKey(conn.city1) && !map.containsKey(conn.city2)) {
                map.put(conn.city2, map.get(conn.city1));
                return true;
            }
            if (!map.containsKey(conn.city1) && map.containsKey(conn.city2)) {
                map.put(conn.city1, map.get(conn.city2));
                return true;
            }
            // 两个都有代号（那么合并它们分别所在的集合中的所有城市）
            if (map.containsKey(conn.city1) && map.containsKey(conn.city2)) {
                int num1 = map.get(conn.city1);
                int num2 = map.get(conn.city2);
                if (num1 == num2) { //避免出现环
                    return false;
                }
                for (String city : map.keySet()) { //把city1在集合中的所有城市代号改为city2的代号
                    if (map.get(city) == num1) {
                        map.put(city, num2);
                    }
                }
                setNum--;
                return true;
            }
            return false;
        }
    }

    public static List<Connection> findMinimum(List<Connection> list) {
        List<Connection> result = new ArrayList<Connection>(); //最终结果，输出必须排序
        if (list == null || list.size() == 0) {
            return result;
        }

        UnionFind uf = new UnionFind();

        // 首先把边以权重来排序
        Collections.sort(list, new Comparator<Connection>() {
            @Override
            public int compare(Connection conn1, Connection conn2) {
                return Integer.compare(conn1.cost, conn2.cost);
            }
        });

        // 遍历每一条边，进行处理
        for (Connection conn : list) {
            if (uf.union(conn)) {
                result.add(conn);
            }
        }

        if (uf.setNum != 1) {
            return null;
        }

        // 最后把结果再排序一次
        Collections.sort(result, new Comparator<Connection>() {
            @Override
            public int compare(Connection conn1, Connection conn2) {
                if (conn1.city1.equals(conn2.city1)) {
                    return conn1.city2.compareTo(conn2.city2);
                }
                return conn1.city1.compareTo(conn2.city1);
            }
        });
        return result;
    }
}
~~~

7. Copy List with Random Pointer
跟leetcode 138一样，除了random的名字变成了arbitrary
地里有人因为abritary 拼写问题，挂掉OA2,
结构
Node {
int x,
node next,
node abritary
}
test case arbitary
~~~
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return head;

        // duplicate the list wz copying random pointers
        RandomListNode curr = head;
        RandomListNode post = head.next;
        while (curr != null) {
            RandomListNode copied = new RandomListNode(curr.label);
            // insert the copied
            curr.next = copied;
            copied.next = post;
            curr = post;
            if (post != null) post = post.next;
        }

        // duplicate the random pointers
        curr = head;
        while (curr != null) {
            if (curr.random != null) curr.next.random = curr.random.next; // fix 1st bug
            curr = curr.next.next;
        }

        // split the list
        curr = head;
        RandomListNode newHead = head.next;
        RandomListNode newCurr = newHead;
        while (curr != null) {
            curr.next = curr.next.next;
            curr = curr.next;

            if (curr != null) {
                newCurr.next = curr.next; // fix 2nd bug
                newCurr = curr.next;
            }
        }

        return newHead;
    }
}
~~~

8. Process Order
给一个ArrayList<OrderDependency>，OrderDependecy有两个属性，其实就是两个string，即process name，一个是当前的process，一个是当前process的先决process。比如当前process是C，先决process是D，意思是要执行C，一定要先执行D。最后要求返回一个次序list，比如输入是[[C,D],[D,A],[A,B],[A,E],[E,B]]，假设每个[]内前面的是当前，后面的是先决，最后输出[B,E,A,D,C]。input已经保证解的唯一性，即保证是DAG且不为空。
跟leetcode 210相似
~~~
import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {

    public static void main(String[] args) {
        OrderDependency o1 = new OrderDependency("C", "D");
        OrderDependency o2 = new OrderDependency("D", "A");
        OrderDependency o3 = new OrderDependency("A", "B");
        OrderDependency o4 = new OrderDependency("A", "E");
        OrderDependency o5 = new OrderDependency("E", "B");

        List<OrderDependency> list = new ArrayList<>(Arrays.asList(o1, o2, o3, o4, o5));
        List<String> result = findOrder(list);
        System.out.println(result.toString());
    }

    public static class OrderDependency {
        String preq;
        String name;

        public OrderDependency(String a, String b) {
            name = a;
            preq = b;
        }
    }

    public static List<String> findOrder(List<OrderDependency> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList<String>();
        }

        Map<String, Integer> indegreeMap = new HashMap<String, Integer>();
        for (OrderDependency item : list) {
            indegreeMap.put(item.preq, indegreeMap.getOrDefault(item.preq, 0));
            indegreeMap.put(item.name, indegreeMap.getOrDefault(item.name, 0) + 1);
        }

        Queue<String> queue = new LinkedList<String>();
        for (Map.Entry<String, Integer> entry : indegreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        List<String> ans = new ArrayList<String>();
        while (!queue.isEmpty()) {
            String name = queue.poll();
            ans.add(name);
            for (OrderDependency item : list) {
                if (item.preq.equals(name)) {
                    indegreeMap.put(item.name, indegreeMap.get(item.name) - 1);
                    if (indegreeMap.get(item.name) == 0) {
                        queue.offer(item.name);
                    }
                }
            }
        }

        if (ans.size() != indegreeMap.size()) {
            return new ArrayList<String>();
        }
        return ans;
    }

}

public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        if (prerequisites == null || prerequisites.length == 0) {
            for (int i = 0; i < numCourses; i++) {
                ans[i] = i;
            }
            return ans;
        }

        int[] indegree = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            int course = prerequisites[i][0];
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            ans[count++] = node;
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == node) {
                    int course = prerequisites[i][0];
                    indegree[course]--;
                    if (indegree[course] == 0) {
                        queue.offer(course);
                    }
                }
            }
        }

        if (count != numCourses) {
            return new int[0];
        }

        return ans;
    }
}
~~~

9. Company Tree
给出一课多叉树（不是二叉树），每个节点代表公司的一个员工，节点保存的值是工作年限，然后每个节点还有一个数组保存他的下属，要求返回一个员工节点，以这个节点作为根节点的子树具有最大的平均工作年限（自己和下属的工作年限之和除以当前组人头数）。这个节点不能为叶子节点。具体例子看此文件夹下的jpg文件。
~~~
import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {

    static class Node { //这个是题目给好的
        int val;
        ArrayList<Node> children;
        public Node(int val){
            this.val = val;
            children = new ArrayList<Node>();
        }
    }

    //这个类是自己写的,要不不好找,两个成员变量分别是当前的总和和人数
    static class SumCount{
        int sum;
        int count;
        public SumCount(int sum, int count){
            this.sum = sum;
            this.count = count;
        }
    }

    //两个全局变量用来找最小的平均值,和对应的节点
    private static double resAve = Double.MIN_VALUE;
    private static Node result;

    public static Node getHighAve(Node root){
        if (root == null) return null;
        dfs(root);
        return result;
    }

    //后序遍历递归。
    private static SumCount dfs(Node root){
        // 这里必须先把叶子节点刨掉，注意看我的手法，其实没什么。
        if (root.children == null || root.children.size() == 0){
            return new SumCount(root.val, 1);
        }
        //把当前root的材料都准备好
        int curSum = root.val;
        int curCnt = 1;
        //注意了这里开始遍历小朋友了
        for (Node child : root.children) {
            SumCount cSC = dfs(child);
            //每次遍历一个都把sum,count都加上，更新
            curSum += cSC.sum;
            curCnt += cSC.count;
        }

        double curAve = (double) curSum/curCnt;
        //这里看一下最大值要不要更新
        if (resAve < curAve){
            resAve = curAve;
            result = root;
        }

        return new SumCount(curSum,curCnt);
    }
    //这回测试的code行数有点儿多。
    public static void main(String[] args) {
        Node root = new Node(1);
        Node l21 = new Node(2);
        Node l22 = new Node(3);
        Node l23 = new Node(4);
        Node l31 = new Node(5);
        Node l32 = new Node(5);
        Node l33 = new Node(5);
        Node l34 = new Node(5);
        Node l35 = new Node(5);
        Node l36 = new Node(5);

        l21.children.add(l31);
        l21.children.add(l32);

        l22.children.add(l33);
        l22.children.add(l34);

        l23.children.add(l35);
        l23.children.add(l36);

        root.children.add(l21);
        root.children.add(l22);
        root.children.add(l23);

        Node res = getHighAve(root);
        System.out.println(res.val + " " + resAve);
    }

}
~~~
