The java codes here basically summarize my work on Linked List.
The codes are driven by CMU course 08-722 Data Structures for Application Programmers
and some leetcode problems related about Linked List.

# SinglyLinkedList.java
The code is about the basic operations on singly-linked list.
It contains solutions to the following LeetCode problems:

### 203. Remove Linked List Elements
Remove all elements from a linked list of integers that have value val.

Example
Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
Return: 1 --> 2 --> 3 --> 4 --> 5
##### Solution
O(n) time and O(1) space.
New a dummy header pointer, use prev and curr pointer to iterate the list and check val.

### 206. Reverse Linked List
Reverse a linked list iteratively and recursively.
##### Solution
O(n) time and O(1) space.
Use prev, curr, and next pointer to iterate the list.

### 92. Reverse Linked List II
Reverse a linked list from position m to n.
Do it in-place and in one-pass.
##### Solution
O(n) time and O(1) space.
Based on problem 206, use an additional counter to mark the nodes
between position m and position n.

### 25. Reverse Nodes in k-Group
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
k is a positive integer and is less than or equal to the length of the linked list.
If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
You may not alter the values in the nodes, only nodes itself may be changed.
Only constant memory is allowed.
For example,
Given this linked list: 1->2->3->4->5
For k = 2, you should return: 2->1->4->3->5
For k = 3, you should return: 3->2->1->4->5
##### Solution
O(n) time and O(1) space.
Based on problem 206, do the reverse k by k. The trick is to maintain a pointer to the end node
of the last k group.
And be careful with corner cases.

### 143. Reorder List
Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
You must do this in-place without altering the nodes' values.
For example,
Given {1,2,3,4}, reorder it to {1,4,2,3}.
##### Solution
We can resolve the problem in 3 steps:
1. Find the node in the middle, with a fast pointer that moves 2 steps every time and a slow pointer that moves 1 step every time.
2. Reverse the right half of the list.
3. Merge the left half of the list with the reversed right half of the list.

### 160. Intersection of Two Linked Lists
Write a program to find the node at which the intersection of two singly linked lists begins.
For example, the following two linked lists:
~~~~
A:          a1 → a2
                   ↘
                     c1 → c2 → c3
                   ↗            
B:     b1 → b2 → b3
~~~~
begin to intersect at node c1.

Notes:
If the two linked lists have no intersection at all, return null.
The linked lists must retain their original structure after the function returns.
You may assume there are no cycles anywhere in the entire linked structure.
Your code should preferably run in O(n) time and use only O(1) memory.
##### Solution
1) We can resolve the problem in 2 steps:
1.1) get the length of list A and list B in O(n) time.
1.2) first move the pointer of longer list lenA - lenB steps,
and then move two list pointers at the same time.
2) Java solution without knowing the difference in len, from [this leetcode discussion](https://discuss.leetcode.com/topic/28067/java-solution-without-knowing-the-difference-in-len/16)
Do this in two iteration. For the end of first iteration, we just set the pointer to the head
of another list. The iteration in first iteration will help us get the difference of length between
two lists.

### 328. Odd Even Linked List
Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.
You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
Example:
Given 1->2->3->4->5->NULL,
return 1->3->5->2->4->NULL.
Note:
The relative order inside both the even and odd groups should remain as it was in the input.
The first node is considered odd, the second node even and so on ...
##### Solution
Use two pointers to odd and even position, and a third pointer to mark the head of even position.

### 19. Remove Nth Node From End of List
Given a linked list, remove the nth node from the end of list and return its head.

For example,
~~~~
   Given linked list: 1->2->3->4->5, and n = 2.

   After removing the second node from the end, the linked list becomes 1->2->3->5.
~~~~
Note:
Given n will always be valid.
Try to do this in one pass.

##### Solution
1. Use first pointer to move N steps at first.
2. Move first and second pointers at the same time. When the first pointer reaches the end of the list, the second pointer in to the Nth node from the end of the list.
3. Remove the Nth node from the list.

### 21. Merge Two Sorted Lists
Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

#### Solution
Simply use two pointers to iterate through two lists.
Compare the node value of two pointers and insert the smaller one.

### 23. Merge k Sorted Lists (Hard)
Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

#### Solution
Use PriorityQueue to select the current smallest node value and insert the node into next position. The time complexity is O(nlogn).

### 24. Swap Nodes in Pairs (Medium)
Given a linked list, swap every two adjacent nodes and return its head.

For example,
Given 1->2->3->4, you should return the list as 2->1->4->3.

Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.

#### Solution
Simply use two pointers to iterate the list.

### 445. Add Two Numbers II
You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Follow up:
What if you cannot modify the input lists? In other words, reversing the lists is not allowed.

Example:
~~~~
Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7
~~~~

#### Solution
1. An intuitive solution is to reverse the two lists and then add from the lest significant digit.
2. If we cannot modify the input, instead we can use a stack to achieve the same result as reversing lists.

# RandomLinkedList.java
The code is for leetcode 138. Copy List with Random Pointer.

A linked list is given such that each node contains an additional random pointer
which could point to any node in the list or null.
Return a deep copy of the list.
##### Solution
1. O(n) time and O(n) space.
The naiive solution is to use a hash map which stores the mapping from random pointer
in original list to its corresponding node in copy list.
In such way, we can copy the random pointer in 2nd scan.
2. O(n) time and O(1) space.
A better solution from [this LeetCode discussion](https://discuss.leetcode.com/topic/7594/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n).
"The idea is to associate the original node with its copy node in a single linked list.
In this way, we don't need extra space to keep track of the new nodes."
a) 1st iteration: duplicate each node and insert duplicated note right behind the original node
b) 2nd iteration: copy random pointer by using cur.next.random = cur.random.next.
c) 3rd iteration: separate the list to two list: the old one and the new copy
