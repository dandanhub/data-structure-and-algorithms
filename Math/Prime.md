## 204. Count Primes
Description:

Count the number of prime numbers less than a non-negative number, n.

#### Solution
Method 1:  O(n^1.5) TLE
~~~
class Solution {
    public int countPrimes(int n) {
        if (n <= 0) return 0;
        int count = 0;
        for (int i = 2; i < n; i++) {
            boolean flag = true;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) count++;
        }
        return count;
    }
}
~~~

~~~
public int countPrimes(int n) {
   int count = 0;
   for (int i = 1; i < n; i++) {
      if (isPrime(i)) count++;
   }
   return count;
}

private boolean isPrime(int num) {
   if (num <= 1) return false;
   // Loop's ending condition is i * i <= num instead of i <= sqrt(num)
   // to avoid repeatedly calling an expensive function sqrt().
   for (int i = 2; i * i <= num; i++) {
      if (num % i == 0) return false;
   }
   return true;
}
~~~

Method 2: The Sieve of Eratosthenes uses an extra O(n) memory and its runtime complexity is O(n log log n).
~~~

~~~
