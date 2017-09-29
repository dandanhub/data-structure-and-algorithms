#### String.indexof(substring)
Java indexOf function complexity is O(n*m) where n is length of the text and m is a length of pattern.

#### String.contains(substring)
"Efficiency" is all about tradeoffs, and the "best" algorithm will depend on many factors. In the case of indexOf(), one of those factors is the expected size of strings.

The JDK's algorithm is based on simple indexed reference into existing character arrays. The Knuth-Morris-Pratt that you reference needs to create a new int[] that's the same size as the input string. For Boyer-Moore, you need several external tables, at least one of which is two-dimensional (I think; I've never implemented B-M).

So the question becomes: are allocating the additional objects and building lookup tables offset by the increased performance of the algorithm? Remember, we're not talking about a change from O(N2) to O(N), but simply a reduction in the number of steps taken for each N.

And I would expect that the JDK designers said something like "for strings less than X characters, the simple approach is faster, we don't expect regular use of strings longer than that, and people who do use longer strings will know how to optimize their searches."
