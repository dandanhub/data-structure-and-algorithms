## 146. LRU Cache
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache( 2 /* capacity \*/ );
~~~
cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
~~~

#### Solution
1. Java比较简洁方便的写法是用LinkedHashMap
2. 用Map和DoubleLinkedList
- get从map里面get元素，如果元素存在，把该元素放入队首
- put 1) 当小于capacity的时候直接放入map和list首 2) 当大于capacity的时候，list尾的元素即为LRU元素，从list从删除这个元素，同时从map中移除

~~~
public class LRUCache {

    Map<Integer, Integer> map;
    int capacity;

    public LRUCache(int capacity) {
        map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
        this.capacity = capacity;
    }

    public int get(int key) {
        return map.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        map.put(key, value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
~~~

Attempt: 2
**不要忘记处理当put的key已经存在的情况**
~~~
public class LRUCache {

    private class DoublyLinkedNode {
        DoublyLinkedNode prev;
        DoublyLinkedNode next;
        int key;
        int val;

        public DoublyLinkedNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    Map<Integer, DoublyLinkedNode> map;
    DoublyLinkedNode head;
    DoublyLinkedNode tail;
    int capacity;

    public LRUCache(int capacity) {
        map = new HashMap<Integer, DoublyLinkedNode>();
        head = new DoublyLinkedNode(0, 0);
        tail = new DoublyLinkedNode(0, 0);
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        DoublyLinkedNode node = map.get(key);
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        // TODO edge case when capacity <= 0

        // when key already exist
        if (map.containsKey(key)) {
            DoublyLinkedNode node = map.get(key);
            node.val = value;
            moveToHead(node);
            return;
        }

        DoublyLinkedNode newNode = new DoublyLinkedNode(key, value);
        if (map.size() >= capacity) {
             // evict LRU
            DoublyLinkedNode toRemoved = moveFromTail();
            map.remove(toRemoved.key);
        }

        // put new element
        map.put(key, newNode);
        moveToHead(newNode);
    }

    private void moveToHead(DoublyLinkedNode node) {
        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    private DoublyLinkedNode moveFromTail() {
        DoublyLinkedNode lastNode = tail.prev;
        lastNode.prev.next = tail;
        tail.prev = lastNode.prev;
        lastNode.prev = null;
        lastNode.next = null;
        return lastNode;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
~~~

## 460. LFU Cache
Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LFUCache cache = new LFUCache( 2 /* capacity \*/ );
~~~
cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
~~~

#### Solution
1. 用三个map + LinkedHashSet来实现
2. 注意几个细节
- 处理capacity <= 0的情况
- key在map中已经存在，这个情况相当于更新value，然后做一次get操作
- 注意细节处理leastFreq的更新

~~~
public class LFUCache {
    Map<Integer, Integer> entryMap; // store <key, value> pair
    Map<Integer, Integer> countMap; // store <key, freq> pair
    Map<Integer, LinkedHashSet<Integer>> freqMap; // store <freq, Set<key>> pair
    int capacity;
    int leastFreq;

    public LFUCache(int capacity) {
        entryMap = new HashMap<Integer, Integer>();
        countMap = new HashMap<Integer, Integer>();
        freqMap = new HashMap<Integer, LinkedHashSet<Integer>>();
        this.capacity = capacity;
        leastFreq = 0;
    }

    public int get(int key) {
        if (!entryMap.containsKey(key)) return -1;

        // get the value result
        int value = entryMap.get(key);

        // increment its freq
        int freq = countMap.get(key);
        countMap.remove(key);
        countMap.put(key, freq + 1);

        // remove it from its curr freq set and add to next freq set
        LinkedHashSet<Integer> oldSet = freqMap.get(freq);
        oldSet.remove(key);
        LinkedHashSet<Integer> newSet = freqMap.getOrDefault(freq + 1, new LinkedHashSet<Integer>());
        newSet.add(key);
        freqMap.put(freq + 1, newSet);

        // if old set is with the least freq, check whether lease freq set is empty
        // if so, increment the least freq
        if (freq == leastFreq && oldSet.isEmpty()) {
            leastFreq += 1;
        }

        return value;
    }

    public void put(int key, int value) {
        // edge case
        if (capacity <= 0) return;

        // key already existed
        if (entryMap.containsKey(key)) {
            entryMap.put(key, value);
            get(key);
            return;
        }

        // reach capacity
        if (entryMap.size() >= capacity) {
            // evict LFU
            LinkedHashSet<Integer> leastFreqSet = freqMap.get(leastFreq);
            int toEvicted = leastFreqSet.iterator().next();
            leastFreqSet.remove(toEvicted);

            entryMap.remove(toEvicted);
            countMap.remove(toEvicted);

            if (leastFreqSet.isEmpty()) {
                leastFreq += 1;
            }
        }

        // new key
        // add <key, value> pair to map
        entryMap.put(key, value);

        // add <key, freq> pair to count
        int freq = 1;
        countMap.put(key, freq);

        // add new entry to freq
        LinkedHashSet<Integer> set = freqMap.getOrDefault(freq, new LinkedHashSet<Integer>());
        set.add(key);
        freqMap.put(freq, set);

        // reset least freq
        leastFreq = freq;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
~~~
