## 251. Flatten 2D Vector
Implement an iterator to flatten a 2d vector.

For example,
Given 2d vector =
~~~
[
  [1,2],
  [3],
  [4,5,6]
]
~~~
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,2,3,4,5,6].

Follow up:
As an added challenge, try to code it using only iterators in C++ or iterators in Java.

#### Solution
**注意声明Iterator的格式Iterator<List<Integer>> out; Iterator<Integer> in;**

熟悉Interface接口Interface Iterator<E> ：
1. boolean hasNext()
2. E next() <br>
Returns the next element in the iteration. NoSuchElementException - if the iteration has no more elements.
3. default void remove() <br>
Removes from the underlying collection the last element returned by this iterator (optional operation). This method can be called only once per call to next().

Java ArrayList Source Code
~~~
  777        /**
  778        * An optimized version of AbstractList.Itr
  779        */
  780       private class Itr implements Iterator<E> {
  781           int cursor;       // index of next element to return
  782           int lastRet = -1; // index of last element returned; -1 if no such
  783           int expectedModCount = modCount;
  784   
  785           public boolean hasNext() {
  786               return cursor != size;
  787           }
  788   
  789           @SuppressWarnings("unchecked")
  790           public E next() {
  791               checkForComodification();
  792               int i = cursor;
  793               if (i >= size)
  794                   throw new NoSuchElementException();
  795               Object[] elementData = ArrayList.this.elementData;
  796               if (i >= elementData.length)
  797                   throw new ConcurrentModificationException();
  798               cursor = i + 1;
  799               return (E) elementData[lastRet = i];
  800           }
  801   
  802           public void remove() {
  803               if (lastRet < 0)
  804                   throw new IllegalStateException();
  805               checkForComodification();
  806   
  807               try {
  808                   ArrayList.this.remove(lastRet);
  809                   cursor = lastRet;
  810                   lastRet = -1;
  811                   expectedModCount = modCount;
  812               } catch (IndexOutOfBoundsException ex) {
  813                   throw new ConcurrentModificationException();
  814               }
  815           }
  816   
  817           final void checkForComodification() {
  818               if (modCount != expectedModCount)
  819                   throw new ConcurrentModificationException();
  820           }
  821       }
  822   
~~~

Attempts: Using Iterator 5 <br>
确认是否需要在hasNext()中判断NoSuchElementException()的逻辑 <br>
如果要求加入remove功能
~~~
public class Vector2D implements Iterator<Integer> {
    List<List<Integer>> vec2d;
    Iterator<List<Integer>> out;
    Iterator<Integer> in;

    public Vector2D(List<List<Integer>> vec2d) {
        this.vec2d = vec2d;
        if (vec2d != null && vec2d.size() != 0) {
            out = vec2d.iterator();
            in = out.next().iterator();
        }
    }

    @Override
    public Integer next() {
        Integer ans = in.next();
        return ans;
    }

    @Override
    public boolean hasNext() {
        if (out == null || in == null) return false;
        while (!in.hasNext() && out.hasNext()) {
            in = out.next().iterator();
        }

        if (in.hasNext()) return true;
        return false;
    }
}

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D i = new Vector2D(vec2d);
 * while (i.hasNext()) v[f()] = i.next();
 */
~~~

Attempt: 2 Using row and col pointers <br>
~~~
public class Vector2D implements Iterator<Integer> {
    List<List<Integer>> vec2d;
    int row;
    int col;

    public Vector2D(List<List<Integer>> vec2d) {
        if (vec2d == null || vec2d.size() == 0) return;
        this.vec2d = vec2d;
        this.row = 0;
        this.col = 0;
    }

    @Override
    public Integer next() {
        Integer ans = vec2d.get(row).get(col++);
        return ans;
    }

    @Override
    public boolean hasNext() {
        if (vec2d == null || vec2d.size() == 0) return false;
        // while (col >= vec2d.get(row).size() && row < vec2d.size()) { // bug
        while (row < vec2d.size() && col >= vec2d.get(row).size()) {
            row++;
            col = 0;
        }

        if (row < vec2d.size()) return true;
        return false;
    }

    @Override
    public void remove() {

    }
}

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D i = new Vector2D(vec2d);
 * while (i.hasNext()) v[f()] = i.next();
 */
~~~

~~~
爱彼迎 2016-2-10
题目很简单： 2D iterator+ remove。 写完还有11 分钟。问问题。没了。
请问地里的大牛们，他家phd实习是不是有两轮coding啊
~~~
