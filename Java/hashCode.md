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
