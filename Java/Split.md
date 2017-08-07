~~~
public static void main(String[] args) {
   String str = "a,b,c,d,e,";
   String[] tokens = str.split(",");
   System.out.println(tokens.length);
   for (int i = 0; i < tokens.length; i++)
     System.out.println(tokens[i]);
 }
 ~~~


5
a
b
c
d
e


~~~
public static void main(String[] args) {
   String str = ",a,b,c,d,e,";
   String[] tokens = str.split(",");
   System.out.println(tokens.length);
   for (int i = 0; i < tokens.length; i++)
     System.out.println(tokens[i]);
 }
~~~

6

a
b
c
d
e
