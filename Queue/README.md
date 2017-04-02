The java codes here basically summarize my work on Queue. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some leetcode problems related about Queue.

# MovingAverage.java
## 346. Moving Average from Data Stream
Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

For example,
~~~~
MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3
~~~~

#### Solution
1. Use a queue as container
2. Use a int variable as the capacity of the container

# SnakeGame.java
### 353. Design Snake Game
Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0,0) with length = 1 unit.

You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.

Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.

When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

Example:
~~~~
Given width = 3, height = 2, and food = [[1,2],[0,1]].

Snake snake = new Snake(width, height, food);

Initially the snake appears at position (0,0) and the food at (1,2).

|S| | |
| | |F|

snake.move("R"); -> Returns 0

| |S| |
| | |F|

snake.move("D"); -> Returns 0

| | | |
| |S|F|

snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )

| |F| |
| |S|S|

snake.move("U"); -> Returns 1

| |F|S|
| | |S|

snake.move("L"); -> Returns 2 (Snake eats the second food)

| |S|S|
| | |S|

snake.move("U"); -> Returns -1 (Game over because snake collides with border)
~~~~

#### Solution
1. Create a private class to represent point. The private class has two variables, int row and int column. We need to override hashCode() and equals() functions in this class.

  *You must override hashCode() in every class that overrides equals(). Failure to do so will result in a violation of the general contract for Object.hashCode(), which will prevent your class from functioning properly in conjunction with all hash-based collections, including HashMap, HashSet, and Hashtable.* Read more from [stackoverflow discussion](http://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java)

2. SnakeGame class has the following instance variables.

  ~~~
  // the width of the screen
  private int width;
  // the height of the screen
  private int height;
  // the food
  private int[][] food;
  // using a deque to represent the body of the snake with order
  private Deque<Point> body;
  // using a set to store all body points of the snake without order
  // by using a hash set we can check whether snake bits its body i O(1) time
  private Set<Point> set;
  // the index of currently appeared food
  private int foodIndex;
   ~~~

3. Move the snake according to direction and handle different cases carefully.
