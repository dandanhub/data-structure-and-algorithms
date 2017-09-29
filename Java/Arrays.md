## Arrays
- binarySearch(int[] a, int fromIndex, int toIndex, int key)
Searches a range of the specified array of ints for the specified value using the binary search algorithm.

~~~
binarySearch
public static int binarySearch(int[] a,
               int fromIndex,
               int toIndex,
               int key)
Searches a range of the specified array of ints for the specified value using the binary search algorithm. The range must be sorted (as by the sort(int[], int, int) method) prior to making this call. If it is not sorted, the results are undefined. If the range contains multiple elements with the specified value, there is no guarantee which one will be found.

Parameters:
a - the array to be searched
fromIndex - the index of the first element (inclusive) to be searched
toIndex - the index of the last element (exclusive) to be searched
key - the value to be searched for

Returns:
index of the search key, if it is contained in the array within the specified range; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element in the range greater than the key, or toIndex if all elements in the range are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.

Throws:
IllegalArgumentException - if fromIndex > toIndex
ArrayIndexOutOfBoundsException - if fromIndex < 0 or toIndex > a.length
Since:
1.6
~~~

- binarySearch
public static int binarySearch(int[] a, int key)
Searches the specified array of ints for the specified value using the binary search algorithm. The array must be sorted (as by the sort(int[]) method) prior to making this call. If it is not sorted, the results are undefined. If the array contains multiple elements with the specified value, there is no guarantee which one will be found.
Parameters:
a - the array to be searched
key - the value to be searched for
Returns:
index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
