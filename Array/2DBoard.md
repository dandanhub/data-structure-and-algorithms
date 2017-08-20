## 531. Lonely Pixel I
Given a picture consisting of black and white pixels, find the number of black lonely pixels.

The picture is represented by a 2D char array consisting of 'B' and 'W', which means black and white pixels respectively.

A black lonely pixel is character 'B' that located at a specific position where the same row and same column don't have any other black pixels.
~~~
Example:
Input:
[['W', 'W', 'B'],
 ['W', 'B', 'W'],
 ['B', 'W', 'W']]

Output: 3
Explanation: All the three 'B's are black lonely pixels.
~~~

#### Solution
Method 1: O(mn) time and O(m + n) space
~~~
public class Solution {
    public int findLonelyPixel(char[][] picture) {
        if (picture == null || picture.length == 0 || picture[0].length == 0) return 0;
        int m = picture.length;
        int n = picture[0].length;
        int[] rows = new int[m];
        int[] cols = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    rows[i]++;
                    cols[j]++;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B' && rows[i] == 1 && cols[j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }
}
~~~

Method 2: O(mn) time and O(1) space <br>
常用技巧是把个数写在每行和每列的首部，但是两点注意
1. 第一行和第一列会写冲突，所以通常是再额外生成一个变量保存either firstColCount or firstRowCount
2. 针对这一题，如果第一行或者第一列本身是'B'的话，在+的时候要注意控制不让'B'+出界，另外统计的时候注意统计由'B'变成'C'的元素
~~~
public class Solution {
    public int findLonelyPixel(char[][] picture) {
        if (picture == null || picture.length == 0 || picture[0].length == 0) return 0;

        int m = picture.length;
        int n = picture[0].length;

        int firstColCount = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    if (picture[i][0] != 'V' && picture[i][0] <= 'X') picture[i][0]++;
                    if (j == 0) firstColCount++;
                    else if (picture[0][j] != 'V' && picture[0][j] <= 'X') picture[0][j]++;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B' || picture[i][j] == 'C') {
                    if (picture[i][0] != 'C' && picture[i][0] != 'X') continue;
                    if (j == 0) count += firstColCount == 1 ? 1 : 0; // 这里注意判断条件
                    // 刚开始写成错误版本 if (j == 0 && firstColCount == 1) count++;
                    else if (picture[0][j] == 'C' || picture[0][j] == 'X') {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
~~~

## 533. Lonely Pixel II

Given a picture consisting of black and white pixels, and a positive integer N, find the number of black pixels located at some specific row R and column C that align with all the following rules:

Row R and column C both contain exactly N black pixels.
For all rows that have a black pixel at column C, they should be exactly the same as row R
The picture is represented by a 2D char array consisting of 'B' and 'W', which means black and white pixels respectively.

Example:
~~~
Input:                                            
[['W', 'B', 'W', 'B', 'B', 'W'],    
 ['W', 'B', 'W', 'B', 'B', 'W'],    
 ['W', 'B', 'W', 'B', 'B', 'W'],    
 ['W', 'W', 'B', 'W', 'B', 'W']]

N = 3
Output: 6
Explanation: All the bold 'B' are the black pixels we need (all 'B's at column 1 and 3).
        0    1    2    3    4    5         column index                                            
0    [['W', 'B', 'W', 'B', 'B', 'W'],    
1     ['W', 'B', 'W', 'B', 'B', 'W'],    
2     ['W', 'B', 'W', 'B', 'B', 'W'],    
3     ['W', 'W', 'B', 'W', 'B', 'W']]    
row index

Take 'B' at row R = 0 and column C = 1 as an example:
Rule 1, row R = 0 and column C = 1 both have exactly N = 3 black pixels.
Rule 2, the rows have black pixel at column C = 1 are row 0, row 1 and row 2. They are exactly the same as row R = 0.
~~~

Note:
The range of width and height of the input 2D array is [1,200].

#### Solution
先扫描一遍board, 用HashSet存放合法的column, 合法的column必须满足条件
1. B的个数为N
2. 所有包含B的rows都相同
这个方法23ms, 74.79%
~~~
public class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        if (picture == null || picture.length == 0
            || picture[0].length == 0 || N < 0) return 0;

        int m = picture.length;
        int n = picture[0].length;

        Set<Integer> validCols = new HashSet<Integer>();
        for (int j = 0; j < n; j++) {
            int prev = -1;
            int count = 0;
            boolean isValid = true;
            for (int i = 0; i < m; i++) {
                if (picture[i][j] == 'B') {
                    if (count == N || (prev != -1 && !isSame(picture, prev, i))) {
                        isValid = false;
                        break;
                    }
                    prev = i;
                    count++;
                }
            }

            if (count == N && isValid) {
                validCols.add(j);
            }
        }

        // for (int item : validCols) System.out.println(item);

        int[] row = new int[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') row[i]++;
            }
        }

        // System.out.println(Arrays.toString(row));

        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B' && row[i] == N && validCols.contains(j)) ans++;
            }
        }
        return ans;
    }

    private boolean isSame(char[][] picture, int r1, int r2) {
        for (int j = 0; j < picture[0].length; j++) {
            if (picture[r1][j] != picture[r2][j]) return false;
        }
        return true;
    }
}
~~~

用HashMap然后把每行做出String的方法反而不快

这个方法基本是把人脑思考的过程写了一遍，但是由于会提前退出，反而最快
~~~
public class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        if (picture == null || picture.length == 0
            || picture[0].length == 0 || N < 0) return 0;

        int m = picture.length;
        int n = picture[0].length;

        int[] row = new int[m];
        int[] col = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    row[i]++;
                    col[j]++;
                }
            }
        }

        int ans = 0;
        for (int j = 0; j < n; j++) {
            if (col[j] != N) continue;
            for (int i = 0; i < m; i++) {
                if (row[i] != N) continue;
                if (picture[i][j] == 'B') {
                    if (isMatch(picture, i, j)) {
                        ans += N;
                    }
                    break;
                }
            }
        }
        return ans;
    }

    private boolean isMatch(char[][] picture, int row, int col) {
        int m = picture.length;
        int n = picture[0].length;
        for (int i = 0; i < m; i++) {
            if (i == row || picture[i][col] != 'B') continue;
            for (int j = 0; j < n; j++) {
                if (picture[i][j] != picture[row][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
~~~
