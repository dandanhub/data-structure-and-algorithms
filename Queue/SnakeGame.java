import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;


public class SnakeGame {
    
    private class Point {
        private int row;
        private int column;
        
        public Point(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            
            if (!(o instanceof Point)) {
                return false;
            }
            
            Point p = (Point) o;
            return this.row == p.row && this.column == p.column;
        }
        
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + this.row;
            result = 31 * result + this.column;
            return result;
        }
    }
    
    private int width;
    private int height;
    private int[][] food;
    private Deque<Point> body;
    private Set<Point> set;
    private int foodIndex;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        body = new ArrayDeque<Point>();
        Point head = new Point(0, 0);
        body.add(head);
        set = new HashSet<Point>();
        set.add(head);
        foodIndex = 0;
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        Point head = body.peekLast();
        Point next = new Point(head.row, head.column);
        switch (direction) {
            case "U":
                next.row -= 1;
                break;
            case "D":
                next.row += 1;
                break;
            case "L":
                next.column -= 1;
                break;
            case "R":
                next.column += 1;
                break;
        }
        
        // snake collides with border
        if (next.row < 0 || next.row >= height || next.column < 0 || next.column >= width) {
            return -1;
        }
        
        // snake eats the food at index
        if (foodIndex < food.length && next.row == food[foodIndex][0] && next.column == food[foodIndex][1]) {
            body.add(next);
            set.add(next);
            foodIndex++;
            return body.size() - 1;
        }
        
        // snake moves to the next
        Point removed = body.removeFirst();
        set.remove(removed);
        
        // snake bites its body
        if (set.contains(next)) {
            return -1;
        }
        
        body.addLast(next);
        set.add(next);
        return body.size() - 1;
    }
    
    public static void main(String[] args) {
        int[][] food = new int[4][4];
        food[0][0] = 0;
        food[0][1] = 1;
        food[1][0] = 0;
        food[1][1] = 2;
        food[2][0] = 1;
        food[2][1] = 2;
        food[3][0] = 1;
        food[3][1] = 1;
        SnakeGame obj = new SnakeGame(3, 2, food);
        System.out.println(obj.move("R"));
        System.out.println(obj.move("R"));
        System.out.println(obj.move("D"));
        System.out.println(obj.move("L"));
        System.out.println(obj.move("L"));
        System.out.println(obj.move("U"));
        System.out.println(obj.move("R"));
        System.out.println(obj.move("D"));
       
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */