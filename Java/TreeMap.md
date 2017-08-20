## TreeMap

A Red-Black tree based NavigableMap implementation. The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time, depending on which constructor is used.

This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations. Algorithms are adaptations of those in Cormen, Leiserson, and Rivest's Introduction to Algorithms.


1. Map.Entry<K,V>	pollFirstEntry()
~~~
Removes and returns a key-value mapping associated with the least key in this map, or null if the map is empty.
~~~

2. Map.Entry<K,V>	pollLastEntry()
~~~
Removes and returns a key-value mapping associated with the greatest key in this map, or null if the map is empty.
~~~

## TreeSet
1. 	lower(E e)
~~~
Returns the greatest element in this set strictly less than the given element, or null if there is no such element.
~~~

2. higher(E e)
~~~
Returns the least element in this set strictly greater than the given element, or null if there is no such element.
~~~

3. ceiling(E e)
~~~
Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
~~~

4. floor(E e)
~~~
Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
~~~
