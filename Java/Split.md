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

split() accepts a regular expression, so you need to escape . to not consider it as a regex meta character. Here's an exemple :

String[] fn = filename.split("\\.");
return fn[0];
