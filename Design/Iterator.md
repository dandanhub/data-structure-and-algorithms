## 281. Zigzag Iterator (+)
Given two 1d vectors, implement an iterator to return their elements alternately.

For example, given two 1d vectors:
~~~
v1 = [1, 2]
v2 = [3, 4, 5, 6]
~~~
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1, 3, 2, 4, 5, 6].

Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?

Clarification for the follow up question - Update (2015-09-18):
The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:
~~~
[1,2,3]
[4,5,6,7]
[8,9]
~~~
It should return [1,4,8,2,5,9,3,6,7].

#### Solution

~~~
public class ZigzagIterator {

    List<Iterator<Integer>> ilist;
    int k;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        ilist = new ArrayList<Iterator<Integer>>();
        ilist.add(v1.iterator());
        ilist.add(v2.iterator());
        k = 0;
    }

    public int next() {
        Iterator<Integer> iterator = ilist.get(k);
        k = (k + 1) % 2;
        return iterator.next();
    }

    public boolean hasNext() {
        if (ilist.get(k).hasNext()) return true;

        int count = 0;
        while (!ilist.get(k).hasNext() && count < ilist.size()) {
            k = (k + 1) % ilist.size();
            count++;
        }
        return ilist.get(k).hasNext();
    }
}

/**
 * Your ZigzagIterator object will be instantiated and called as such:
 * ZigzagIterator i = new ZigzagIterator(v1, v2);
 * while (i.hasNext()) v[f()] = i.next();
 */
~~~

**变形体**
~~~
很实用的资料。我在Google的面试就被要求实现Zigzag Iterator。只是条件比楼主给出的略为苛刻，面试官是这么问的：

Given an iterator A of iterators B's , alternately output the contents in all the B's

也就是说我现在有一群iterator B1, B2, ... Bk，分别是Collection C1, C2, ... , Ck的iterator. 但是不把这些iterator的引用直接给你，而是把这些iterator再放到一个Collection （比如记为B）中，然后给你的只是B的一个iterator BI。输出方式就是按楼主所说的zigzag形。

我当时说可以用BI先把B遍历一遍，把所有的iterator B1, B2, ... , Bk先拷贝到一个能够随机访问的Collection中，然后用楼主上面给出的方法。但是面试官似乎不太认可这个答案，但是也没有明说到底哪里应该改进。我觉得他可能是想让我找一个不用预先遍历的方法，或者是甚至不需要用到额外O(k)内存的方法。但是我到最后也没想出来。
~~~

## 251. Flatten 2D Vector (+++)
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

Attempt: Using Iterator 5 <br>
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
