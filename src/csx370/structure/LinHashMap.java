package csx370.structure;

/************************************************************************************
 * @file LinHashMap.java
 *
 * @author  John Miller
 */

import java.io.*;
import java.lang.reflect.Array;
import static java.lang.System.out;
import java.util.*;

/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm. A hash
 * table is created that is an array of buckets.
 *
 */
public class LinHashMap<K, V> extends AbstractMap<K, V> implements
		Serializable, Cloneable, Map<K, V> {
	/**
	 * The number of slots (for key-value pairs) per bucket.
	 */
	private static final int SLOTS = 4;

	/**
	 * The max load factor for the hashtable
	 */
	private static final float LOAD_FACTOR = .75f;

	/**
	 * The class for type K.
	 */
	private final Class<K> classK;

	/**
	 * The class for type V.
	 */
	private final Class<V> classV;

	/********************************************************************************
	 * This inner class defines buckets that are stored in the hash table.
	 */
	private class Bucket {
		int nKeys;
		K[] key;
		V[] value;
		Bucket next;

		@SuppressWarnings("unchecked")
		Bucket(Bucket n) {
			nKeys = 0;
			key = (K[]) Array.newInstance(classK, SLOTS);
			value = (V[]) Array.newInstance(classV, SLOTS);
			next = n;
		} // constructor
	} // Bucket inner class

	/**
	 * The list of buckets making up the hash table.
	 */
	private final List<Bucket> hTable;

	/**
	 * The modulus for low resolution hashing
	 */
	private int mod1;

	/**
	 * The modulus for high resolution hashing
	 */
	private int mod2;

	/**
	 * Counter for the number buckets accessed (for performance testing).
	 */
	private int count = 0;

	/**
	 * The index of the next bucket to split.
	 */
	private int split = 0;

	/**
	 * Number of pairs currently held in the hash map
	 */
	private int pairs = 0;

	/********************************************************************************
	 * Construct a hash table that uses Linear Hashing.
	 * 
	 * @param _classK
	 *            the class for keys (K)
	 * @param _classV
	 *            the class for keys (V)
	 * @param initSize
	 *            the initial number of home buckets (a power of 2, e.g., 4)
	 */
	public LinHashMap(Class<K> _classK, Class<V> _classV, int initSize) {
		classK = _classK;
		classV = _classV;
		hTable = new ArrayList<>();
		for (int i = 0; i < initSize; i++) {
			hTable.add(new Bucket(null));
		}// for
		mod1 = initSize;
		mod2 = 2 * mod1;
	} // constructor

	/********************************************************************************
	 * Return a set containing all the entries as pairs of keys and values.
	 * 
	 * @return the set view of the map
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> enSet = new HashSet<>();

		// iterate through hash table to add all pairs
		for (int i = 0; i < hTable.size(); i++) {
			Bucket bucket = hTable.get(i);

			// iterate through the bucket chain at this position in the hash
			// table
			while (bucket != null) {
				// iterate through pairs stored in this bucket and place them
				// into the set
				for (int j = 0; j < bucket.nKeys; j++) {
					enSet.add(new AbstractMap.SimpleEntry<K, V>(bucket.key[j],
							bucket.value[j]));
				}// for

				bucket = bucket.next;
			}// while
		}// for

		return enSet;
	} // entrySet

	/********************************************************************************
	 * Given the key, look up the value in the hash table.
	 * 
	 * @param key
	 *            the key used for look up
	 * @return the value associated with the key or null if key is not present
	 */
	public V get(Object key) {
		// call private get method that increments count for performance testing
		return this.get(key, true);
	}// get

	/********************************************************************************
	 * Given the key, look up the value in the hash table.
	 * 
	 * @param key
	 *            the key used for look up
	 * @param updateCount
	 *            true to update this.count for performance testing, false
	 *            otherwise
	 * @return the value associated with the key or null if key is not present
	 */
	private V get(Object key, boolean updateCount) {
		// get index of hashtable where desired value is stored
		int i = hash(key);

		// look for key in the ith bucket chain
		Bucket potentialBucket = hTable.get(i);
		while (potentialBucket != null) {
			if (updateCount) {
				this.count++;
			}// if

			// iterate through this bucket's key array to check for the key
			for (int j = 0; j < SLOTS; j++) {
				if (key.equals(potentialBucket.key[j])) {
					return potentialBucket.value[j];
				}// if
			}// for

			// check next bucket in the chain if this one doesn't have the key
			potentialBucket = potentialBucket.next;
		}// while

		// if the key is not found, return null
		return null;
	} // get

	/********************************************************************************
	 * Put the key-value pair in the hash table.
	 * 
	 * @param key
	 *            the key to insert
	 * @param value
	 *            the value to insert
	 * @return null (not the previous value)
	 */
	public V put(K key, V value) {
		// prevent storage of duplicates, don't increment this.count here
		if (value == this.get(key, false)) {
			return null;
		}// if

		// determine index in hashtable where the value needs to be put
		int i = hash(key);
		insert(key, value, i);
		pairs++;

		// determine if a bucket needs to be split
		if (pairs / size() > LOAD_FACTOR) {
			// extend hashtable by one
			hTable.add(new Bucket(null));

			// put values from bucket that will be split into temp storage
			ArrayList<K> tempKeys = new ArrayList<K>();
			ArrayList<V> tempValues = new ArrayList<V>();
			Bucket bucketToEmpty = hTable.get(split);
			while (bucketToEmpty != null) {
				for (int j = 0; j < bucketToEmpty.nKeys; j++) {
					tempKeys.add(bucketToEmpty.key[j]);
					tempValues.add(bucketToEmpty.value[j]);
				}// for

				bucketToEmpty = bucketToEmpty.next;
			}// while

			// create new, empty bucket at the index of the bucket to split
			hTable.set(split, new Bucket(null));

			// increment split here so that the hash method works properly in
			// the next section
			split++;

			// redistribute values from the emptied bucket
			for (int j = 0; j < tempKeys.size(); j++) {
				insert(tempKeys.get(j), tempValues.get(j),
						hash(tempKeys.get(j)));
			}// for

			// update split and mod values if necessary
			if (split == mod1) {
				split = 0;
				mod1 *= 2;
				mod2 *= 2;
			}// if
		}// if

		return null;
	} // put

	/********************************************************************************
	 * Return the size (SLOTS * number of home buckets) of the hash table.
	 * 
	 * @return the size of the hash table
	 */
	public int size() {
		return SLOTS * (mod1 + split);
	} // size

	/********************************************************************************
	 * Print the hash table.
	 */
	private void print() {
		out.println("Hash Table (Linear Hashing)");
		out.println("-------------------------------------------");
		out.println("Key\t->\tValue");

		// iterate through entry set and print each entry
		Iterator<Map.Entry<K, V>> itr = this.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<K, V> entry = itr.next();
			out.println(entry.getKey() + "\t->\t" + entry.getValue());
		}// while

		out.println("-------------------------------------------");
	} // print

	/********************************************************************************
	 * Insert the given key and value into the bucket chain at the given index
	 * of the hashtable
	 *
	 * @param key
	 *            the key to insert into the bucket
	 * @param value
	 *            the value to insert into the bucket
	 * @param index
	 *            the index of the bucket chain to insert the key and value into
	 */
	private void insert(K key, V value, int index) {
		Bucket insertBucket = hTable.get(index);

		// look for the first spot and insert it
		boolean inserted = false;
		while (!inserted) {
			// if this bucket has an empty spot, then insert the goods
			if (insertBucket.nKeys < SLOTS) {
				insertBucket.key[insertBucket.nKeys] = key;
				insertBucket.value[insertBucket.nKeys] = value;
				insertBucket.nKeys++;
				inserted = true;
			}// if
				// if this bucket is full, then move to the next one
			else {
				// if there is no next bucket, then create it
				if (insertBucket.next == null) {
					Bucket newBucket = new Bucket(null);
					insertBucket.next = newBucket;
				}// if

				insertBucket = insertBucket.next;
			}// else
		}// while
	}// insert

	/********************************************************************************
	 * Hash the key into the proper index. This method will determine whether to
	 * use the high or low resolution hash
	 *
	 * @param key
	 *            the key to hash
	 * @return the location of the bucket chain containing the key-value pair
	 */
	private int hash(Object key) {
		int i = h(key);

		// if the current index has already been split, use the higher
		// resolution hash
		if (i < split) {
			i = h2(key);
		}// if

		return i;
	}// hash

	/********************************************************************************
	 * Hash the key using the low resolution hash function.
	 * 
	 * @param key
	 *            the key to hash
	 * @return the location of the bucket chain containing the key-value pair
	 */
	private int h(Object key) {
		return key.hashCode() % mod1;
	} // h

	/********************************************************************************
	 * Hash the key using the high resolution hash function.
	 * 
	 * @param key
	 *            the key to hash
	 * @return the location of the bucket chain containing the key-value pair
	 */
	private int h2(Object key) {
		return key.hashCode() % mod2;
	} // h2

	/********************************************************************************
	 * The main method used for testing.
	 * 
	 * @param args
	 *            the command-line arguments (args [0] gives number of keys to
	 *            insert)
	 */
	public static void main(String[] args) {
		LinHashMap<Integer, Integer> ht = new LinHashMap<>(Integer.class,
				Integer.class, 11);
		int nKeys = 30;
		if (args.length == 1)
			nKeys = Integer.valueOf(args[0]);
		for (int i = 0; i < nKeys; i++)
			ht.put(i, i * i);
		ht.print();
		for (int i = 0; i < nKeys; i++) {
			out.println("key = " + i + " value = " + ht.get(i));
		} // for
		out.println("-------------------------------------------");
		out.println("Average number of buckets accessed = " + ht.count
				/ (double) nKeys);
	} // main
} // LinHashMap