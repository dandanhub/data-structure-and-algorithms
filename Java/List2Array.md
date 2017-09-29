accepted
Either:

Foo[] array = list.toArray(new Foo[list.size()]);
or:

Foo[] array = new Foo[list.size()];
list.toArray(array); // fill the array
Note that this works only for arrays of reference types. For arrays of primitive types, use the traditional way:

List<Integer> list = ...;
int[] array = new int[list.size()];
for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
