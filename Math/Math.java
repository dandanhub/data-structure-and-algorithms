public class Math {

  // 50. Pow(x, n)
  // Solution 1: Recursive
  public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        int m = n / 2;
        if (n < 0) {
            m = -m;
            x =  1 / x;
        }
        return n % 2 == 0 ? myPow(x * x, m) : x * myPow(x * x, m);
  }

  // Solution 2: Iterative
  public double myPow(double x, int n) {
       if (n == 0) {
           return 1;
       }

       long m = (long) n ;
       if (n < 0) {
           m = -m;
           x =  1 / x;
       }
       double ans = 1;
       while (m > 0) {
           if (m % 2 == 1) {
               ans *= x;
           }
           x = x * x;
           m = m / 2;
       }
       return ans;
   }
}
