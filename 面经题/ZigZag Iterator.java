
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
        k = (k + 1) % ilist.size();
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


// lastEle = iterator.next();
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {
    Iterator<Integer> iterator;
    Integer lastEle;

	public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    this.iterator = iterator;
	}

    // Returns the next element in the iteration without advancing the iterator.
	public Integer peek() {
        if (lastEle == null) {
            lastEle = iterator.next();
        }

        return lastEle;
	}

	// hasNext() and next() should behave the same as in the Iterator interface.
	// Override them if needed.
	@Override
	public Integer next() {
	    if (lastEle != null) {
            int ans = lastEle;
            lastEle = null;
            return ans;
        }

        return iterator.next();
	}

	@Override
	public boolean hasNext() {
	    return iterator.hasNext() || lastEle != null;
	}
}


// 341. Flatten Nested List Iterator
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    Stack<NestedInteger> stack;
    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new Stack<NestedInteger>();
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        hasNext();
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            List<NestedInteger> list = stack.pop().getList();
            for (int i = list.size() - 1; i >= 0; i--) {
                stack.push(list.get(i));
            }
        }

        return !stack.isEmpty();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
