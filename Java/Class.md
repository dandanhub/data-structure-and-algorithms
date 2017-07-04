## Override equals, hashCode, compareTo functions

## Comparator 和 Comparable 的区别
http://www.cnblogs.com/skywang12345/p/3324788.html

## Hash Table
| HashSet    | LinkedHashSet     | TreeSet  |
| :------------- | :------------- | :------------- |
| doesn’t maintain any kind of order of its elements.        |  maintains the insertion order. Elements gets sorted in the same sequence in which they have been added to the Set.       |   sorts the elements in ascending order.    |

#### Load factor

An instance of HashMap has two parameters that affect its performance: initial capacity and load factor. The capacity is the number of buckets in the hash table, and the initial capacity is simply the capacity at the time the hash table is created. The load factor is a measure of how full the hash table is allowed to get before its capacity is automatically increased. When the number of entries in the hash table exceeds the product of the load factor and the current capacity, the hash table is rehashed (that is, internal data structures are rebuilt) so that the hash table has approximately twice the number of buckets.
As a general rule, the default load factor (.75) offers a good tradeoff between time and space costs. Higher values decrease the space overhead but increase the lookup cost (reflected in most of the operations of the HashMap class, including get and put). The expected number of entries in the map and its load factor should be taken into account when setting its initial capacity, so as to minimize the number of rehash operations. If the initial capacity is greater than the maximum number of entries divided by the load factor, no rehash operations will ever occur.
