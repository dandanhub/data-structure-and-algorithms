~~~
@Override
public boolean equals(Object o) {
    if(o == null)                return false;
    if(!(o instanceof) Employee) return false;

    Employee other = (Employee) o;
    return this.employeeId == other.employeeId;
  }

@Override
public int hashCode(){
    return (int) employeeId *
                firstName.hashCode() *
                lastName.hashCode();
  }
~~~


The part of the contract here which is important is: objects which are .equals() MUST have the same .hashCode().

Note that they don't have to be the same object; they just have to be equals. Arrays in Java extends from Object, whose default implementation of equals returns true only on object identity; hence why it prints false in above snippet.

https://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method

A for nearly all cases reasonable good implementation was proposed in Josh Bloch's Effective Java in item 8. The best thing is to look it up there because the author explains there why the approach is good.

A short version

Create a int result and assign a non-zero value.


For every field f tested in the equals() method, calculate a hash code c by:
- If the field f is a boolean: calculate (f ? 0 : 1);
- If the field f is a byte, char, short or int: calculate (int)f;
- If the field f is a long: calculate (int)(f ^ (f >>> 32));
- If the field f is a float: calculate Float.floatToIntBits(f);
- If the field f is a double: calculate Double.doubleToLongBits(f) and handle the - return value like every long value;
- If the field f is an object: Use the result of the hashCode() method or 0 if f == null;
If the field f is an array: see every field as separate element and calculate the hash value in a recursive fashion and combine the values as described next.
Combine the hash value c with result:
result = 37 * result + c
Return result
This should result in a proper distribution of hash values for most use situations.
