package csx370.structure;

/************************************************************************************
* @file ExtHashMap.java
* @author John Miller
*/

import java.io.*;
import java.lang.reflect.Array;

import static java.lang.System.out;

import java.util.*;

/************************************************************************************
* This class provides hash maps that use the Extendable Hashing algorithm.
* Buckets are allocated and stored in a hash table and are referenced using
* directory dir.
*/
public class ExtHashMap<K, V> extends AbstractMap<K, V> implements
Serializable, Cloneable, Map<K, V> {
/** Serialize */
private static final long serialVersionUID = 1L;

/** The number of slots (for key-value pairs) per bucket. */
private static final int SLOTS = 4;

/** The class for type K. */
private final Class<K> classK;

/** The class for type V. */
private final Class<V> classV;

/********************************************************************************
* This inner class defines buckets that are stored in the hash table.
*/
private class Bucket {
int nKeys;
K[] key;
V[] value;

@SuppressWarnings("unchecked")
Bucket() {
nKeys = 0;
key = (K[]) Array.newInstance(classK, SLOTS);
value = (V[]) Array.newInstance(classV, SLOTS);
} // constructor
} // Bucket inner class

/** The hash table storing the buckets (buckets in physical order) */
private final List<Bucket> hTable;

/**
* The directory providing access paths to the buckets (buckets in logical
* order)
*/
private final List<Bucket> dir;

/** The modulus for hashing (= 2^D) where D is the global depth */
private int mod;

/** The number of buckets */
private int nBuckets;

/** Counter for the number buckets accessed (for performance testing). */
private int count = 0;

/********************************************************************************
* Construct a hash table that uses Extendable Hashing.
*
* @param _classK the class for keys (K)
* @param _classV the class for keys (V)
* @param initSize the initial number of buckets (a power of 2, e.g., 4)
*/
public ExtHashMap(Class<K> _classK, Class<V> _classV, int initSize) {
classK = _classK;
classV = _classV;
hTable = new ArrayList<>(); // for bucket storage
dir = new ArrayList<>(); // for bucket access
mod = nBuckets = initSize;

for (int x = 0; x < nBuckets; x++) {

Bucket basicBucket = new Bucket();

hTable.add(basicBucket);
dir.add(basicBucket);

}
} // constructor

/********************************************************************************
* Return a set containing all the entries as pairs of keys and values.
*
* @return the set view of the map
*/
public Set<Map.Entry<K, V>> entrySet() {
Set<Map.Entry<K, V>> enSet = new HashSet<>();

for (Bucket bucket : hTable) {
for (int x = 0; x < bucket.nKeys; x++) {
enSet.add(new AbstractMap.SimpleEntry<K, V>(bucket.key[x],
bucket.value[x]));
}
}

return enSet;
} // entrySet

/********************************************************************************
* Given the key, look up the value in the hash table.
*
* @param key the key used for look up
* @return the value associated with the key
*/
public V get(Object key) {

int i = h(key);
Bucket b = dir.get(i);

for (int x = 0; x < b.nKeys; x++) {
if (b.key[x].equals(key)) {
return b.value[x];
}
}
return null;
} // get

/********************************************************************************
* Put the key-value pair in the hash table.
*
* @param key the key to insert
* @param value the value to insert
* @return null (not the previous value)
*/
@SuppressWarnings("unchecked")
public V put(K key, V value) {
if (key == null) {
return null;
}

int i = h(key);
Bucket b = dir.get(i);

// Determine if bucket is full
if (b.nKeys < SLOTS) {
insertIntoBucket(b, key, value);

// Extendible part of the hash table
} else {

K[] tempKey = (K[]) Array.newInstance(classK, SLOTS+1);
V[] tempValue = (V[]) Array.newInstance(classV, SLOTS+1);

for (int x = 0; x < SLOTS; x++) {

tempKey[x] = b.key[x];
tempValue[x] = b.value[x];

}

tempKey[SLOTS] = key;
tempValue[SLOTS] = value;

dir.set(i, new Bucket());

Bucket bucket = new Bucket();

hTable.add(bucket);
dir.add(i, bucket);
mod++;
nBuckets++;

for (int x = 0; x < tempKey.length; x++) {

put(tempKey[x], tempValue[x]);

}
}

return null;
} // put

/**
* Adds a value to a non-full bucket
* @param bucket The bucket to insert into
* @param key The key to insert
* @param value The value to insert
* @return null (not the previous value)
*/
private V insertIntoBucket(Bucket bucket, K key, V value) {

count++;

bucket.key[bucket.nKeys] = key;
bucket.value[bucket.nKeys] = value;
bucket.nKeys++;

return null;
}

/********************************************************************************
* Return the size (SLOTS * number of buckets) of the hash table.
*
* @return the size of the hash table
*/
public int size() {

int count = 0;

for (int x = 0; x < dir.size(); x++) {
for (int y = 0; y < SLOTS; y++) {
if (dir.get(x).key[y] != null) {
count++;
}
}
}

return count;
} // size

/********************************************************************************
* Print the hash table.
*/
private void print() {
out.println("Hash Table (Extendable Hashing)");
out.println("-------------------------------------------");

int bucketCount = 0;
int itemCount = 0;

for (Bucket bucket : dir) {

out.println("------------------Bucket #" + bucketCount
+ "-----------------");

for (int x = 0; x < bucket.nKeys; x++) {

out.print("Item #" + itemCount + ": ");
out.println(bucket.value[x].toString());

itemCount++;
}

bucketCount++;
}

out.println("-------------------------------------------");
} // print

/********************************************************************************
* Hash the key using the hash function.
*
* @param key the key to hash
* @return the location of the directory entry referencing the bucket
*/
private int h(Object key) {
    int rVal = Math.abs(key.hashCode()) % mod;
     if(rVal > mod)
     {
      rVal -= mod;
    }// if

   return rVal;
  } // h

/********************************************************************************
* The main method used for testing.
*
* @param args the command-line arguments (args [0] gives number of keys to
* insert)
*/
public static void main(String[] args) {
ExtHashMap<Integer, Integer> ht = new ExtHashMap<>(Integer.class,
Integer.class, 4);
int nKeys = 10000;
if (args.length == 1)
nKeys = Integer.valueOf(args[0]);
for (int i = 0; i < nKeys; i++)
ht.put(i, i);
ht.print();
for (int i = 0; i < nKeys; i++) {
out.println("key = " + i + " value = " + ht.get(i));
} // for
out.println("-------------------------------------------");
out.println("Average number of buckets accessed = " + ht.count
/ (double) nKeys);
} // main
} // ExtHashMap class