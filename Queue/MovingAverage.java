import java.util.LinkedList;
import java.util.Queue;

public class MovingAverage {

    int capacity;
    Queue<Integer> queue;
    
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        capacity = size;
        queue = new LinkedList<Integer>();    
    }
    
    public double next(int val) {
        if (queue.size() == capacity) {
            queue.poll();
        }
        queue.add(val);
        int sum = 0;
        for (int i = 0; i < queue.size(); i++) {
            int curr = queue.poll();
            sum += curr;
            queue.add(curr);
        }        
        double ans = sum * 1.0 / queue.size();
        return ans;
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */