Resizable-array implementation of the Deque interface. Array deques have no capacity restrictions; they grow as necessary to support usage. They are not thread-safe; in the absence of external synchronization, they do not support concurrent access by multiple threads.

**Null elements are prohibited.**

This class is likely to be faster than Stack when used as a stack, and faster than LinkedList when used as a queue.

Most ArrayDeque operations run in amortized constant time. Exceptions include remove, removeFirstOccurrence, removeLastOccurrence, contains, iterator.remove(), and the bulk operations, all of which run in linear time.


ArrayDeque is best. See this benchmark, which comes from this blog post about the results of benchmarking this. ArrayDeque doesn't have the overhead of node allocations that LinkedList does nor the overhead of shifting the array contents left on remove that ArrayList has. In the benchmark, it performs about 3x as well as LinkedList for large queues and even slightly better than ArrayList for empty queues. For best performance, you'll probably want to give it an initial capacity large enough to hold the number of elements it's likely to hold at a time to avoid many resizes.

Between ArrayList and LinkedList, it seems that it depends on the average number of total elements the queue will contain at any given time and that LinkedList beats ArrayList starting at about 10 elements.
