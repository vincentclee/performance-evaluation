package csx370.structure;

/************************************************************************************
 * @file BpTreeMap.java
 *
 * @author  John Miller
 */

import java.io.*;
import java.lang.reflect.Array;

import static java.lang.System.out;

import java.util.*;

/************************************************************************************
 * This class provides B+Tree maps. B+Trees are used as multi-level index
 * structures that provide efficient access for both point queries and range
 * queries.
 */
public class BpTreeMap<K extends Comparable<K>, V> extends AbstractMap<K, V> 
	implements Serializable, Cloneable, SortedMap<K, V> {
	
	/** Debugger */
	private static final boolean DEBUG = false;
	
	/** Serialize */
	private static final long serialVersionUID = 1L;
	
	/** The maximum fanout for a B+Tree node. */
	private static final int ORDER = 5;
	
	/** The FLOOR(maximum fanout / 2) for a B+Tree node. */
	private static final int HALF = ORDER/2;
	
	/** The CEILING(maximum fanout / 2) for a B+Tree node. */
	private static final int MIDDLE = (int) Math.ceil(ORDER/2.0);
	
	/** The class for type K. */
	private final Class <K> classK;
	
	/** The class for type V. */
	@SuppressWarnings("unused")
	private final Class <V> classV;
	
	/********************************************************************************
	 * This inner class defines nodes that are stored in the B+tree map.
	 */
	private class Node {
		boolean isLeaf;
		int nKeys;
		K[] key;
		Object[] ref;
		@SuppressWarnings("unchecked")
		Node(boolean _isLeaf) {
			isLeaf = _isLeaf;
			nKeys = 0;
			key = (K[]) Array.newInstance(classK, ORDER - 1);
			if (isLeaf) {
				ref = new Object[ORDER];
			} else {
				ref = (Node[]) Array.newInstance(Node.class, ORDER);
			} // if
		} // constructor
	} // Node inner class
	
	/** The root of the B+Tree */
	private Node root;
	
	/** The counter for the number nodes accessed (for performance testing). */
	private int count = 0;
	
	/********************************************************************************
	 * Construct an empty B+Tree map.
	 * @param _classK  the class for keys (K)
	 * @param _classV  the class for values (V)
	 */
	public BpTreeMap(Class<K> _classK, Class<V> _classV) {
		classK = _classK;
		classV = _classV;
		root = new Node(true);
	} // constructor
	
	/********************************************************************************
	 * Return null to use the natural order based on the key type.  This requires the
	 * key type to implement Comparable.
	 */
	public Comparator<? super K> comparator() {
		return null;
	} // comparator
	
	/********************************************************************************
	 * Return a set containing all the entries as pairs of keys and values.
	 * @return  the set view of the map
	 */
	@SuppressWarnings("unchecked")
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> enSet = new LinkedHashSet<>();
		
		//set the starting node
		Node node = root;
		
		//go to first leaf
		while (!node.isLeaf) {
			node = (Node) node.ref[0];
		}
		
		//add entries until full
		while (node != null) {
			//iterate through current node
			for (int i = 0; i < node.nKeys; i++) {
				enSet.add(new SimpleEntry<K, V>(node.key[i], (V) node.ref[i]));
			}
			
			//get next linked leaf
			node = (Node) node.ref[ORDER-1];
		}
		
		return enSet;
	} // entrySet
	
	/********************************************************************************
	 * Given the key, look up the value in the B+Tree map.
	 * @param key  the key used for look up
	 * @return  the value associated with the key
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		//correctly type key
		K keyy = (K) key;
		
		//set the starting node
		Node node = root;
		
		//find the value
		count++;
		for (int i = 0; i < node.nKeys; i++) {
			//node is a leaf
			if (node.isLeaf) {
				//match
				if (keyy.compareTo(node.key[i]) == 0) {
					return (V) node.ref[i];
				} else if (keyy.compareTo(node.key[i]) < 0) { //key is less than node's current value
					return null;
				} continue;
			}
			
			//traverse non-leaf
			if (i == 0 && keyy.compareTo(node.key[i]) < 0) { //first reference
				node = (Node) node.ref[i];
				i = -1;
				count++;
			} else if (i > 0 && keyy.compareTo(node.key[i-1]) >= 0 && keyy.compareTo(node.key[i]) < 0) { //go left
				node = (Node) node.ref[i];
				i = -1;
				count++;
			} else if (i == node.nKeys-1) { //go right
				node = (Node) node.ref[i+1];
				i = -1;
				count++;
			}
		}
		
		return null;
	} // get
	
	/********************************************************************************
	 * Put the key-value pair in the B+Tree map.
	 * @param key    the key to insert
	 * @param value  the value to insert
	 * @return  null (not the previous value)
	 */
	public V put(K key, V value) {
		insert(key, value, root, null);
		return null;
	} // put
	
	/********************************************************************************
	 * Return the first (smallest) key in the B+Tree map.
	 * @return  the first key in the B+Tree map.
	 */
	public K firstKey() {
		//checks for first time
		boolean first = true;
		
		//contains smallest value
		K smallest = null;
		
		//loop through whole map
		for (Map.Entry<K, V> item : entrySet()) {
			//get key from set
			K key = item.getKey();
			
			//checks if smallest is initialized
			if (first) {
				smallest = key;
				first = false;
			} else { //checks whether current key is less than 'smallest'
				if (key.compareTo(smallest) < 0) {
					smallest = key;
				}
			}
		}
		
		//return smallest item
		return smallest;
	} // firstKey
	
	/********************************************************************************
	 * Return the last (largest) key in the B+Tree map.
	 * @return  the last key in the B+Tree map.
	 */
	public K lastKey() {
		//checks for first time
		boolean first = true;
		
		//contains largest value
		K largest = null;
		
		//loop through whole map
		for (Map.Entry<K, V> item : entrySet()) {
			//get key from set
			K key = item.getKey();
			
			//checks if largest is initialized
			if (first) {
				largest = key;
				first = false;
			} else { //checks whether current key is less than 'largest'
				if (key.compareTo(largest) > 0) {
					largest = key;
				}
			}
		}
		
		//return largest item
		return largest;
	} // lastKey
	
	/********************************************************************************
	 * Return the portion of the B+Tree map where key {@literal <} toKey.
	 * @return  the submap with keys in the range [firstKey, toKey)
	 */
	public SortedMap<K,V> headMap(K toKey) {
		return subMap(firstKey(), toKey);
	} // headMap
	
	/********************************************************************************
	 * Return the portion of the B+Tree map where fromKey {@literal <}= key.
	 * @return  the submap with keys in the range [fromKey, lastKey]
	 */
	@SuppressWarnings("unchecked")
	public SortedMap<K,V> tailMap(K fromKey) {
		SortedMap<K,V> mapper = new TreeMap<>();
		
		//set the starting node
		Node node = root;
		
		//go to first leaf
		while (!node.isLeaf) {
			node = (Node) node.ref[0];
		}
		
		//add entries
		while (node != null) {
			//iterate through current node
			for (int i = 0; i < node.nKeys; i++) {
				if (fromKey.compareTo(node.key[i]) <= 0) {
					mapper.put(node.key[i], (V) node.ref[i]);
				}
			}
			
			//get next linked leaf
			node = (Node) node.ref[ORDER-1];
		}
		
		return mapper;
	} // tailMap
	
	/********************************************************************************
	 * Return the portion of the B+Tree map whose keys are between fromKey and toKey,
	 * i.e., fromKey {@literal <}= key {@literal <} toKey.
	 * @return  the submap with keys in the range [fromKey, toKey)
	 */
	@SuppressWarnings("unchecked")
	public SortedMap<K,V> subMap(K fromKey, K toKey) {
		SortedMap<K,V> mapper = new TreeMap<>();
		
		//set the starting node
		Node node = root;
		
		//go to first leaf
		while (!node.isLeaf) {
			node = (Node) node.ref[0];
		}
		
		//add entries
		while (node != null) {
			//iterate through current node
			for (int i = 0; i < node.nKeys; i++) {
				if (fromKey.compareTo(node.key[i]) <= 0 && toKey.compareTo(node.key[i]) > 0) {
					mapper.put(node.key[i], (V) node.ref[i]);
				}
			}
			
			//get next linked leaf
			node = (Node) node.ref[ORDER-1];
		}
		
		return mapper;
	} // subMap
	
	/********************************************************************************
	 * Return the size (number of keys) in the B+Tree.
	 * @return  the size of the B+Tree
	 */
	@SuppressWarnings("unchecked")
	public int size() {
		int sum = 0;
		
		//set the starting node
		Node node = root;
		
		//go to first leaf
		while (!node.isLeaf) {
			node = (Node) node.ref[0];
		}
		
		//loop through bottom row
		while (node != null) {
			sum += node.nKeys;
			
			//get next linked leaf
			node = (Node) node.ref[ORDER-1];
		}
		
		return sum;
	} // size
	
	/********************************************************************************
	 * Debugger method listing vitial stats about the B+ Tree during testing
	 */
	@SuppressWarnings("unused")
	private void debug() {
		System.out.println("First:" + firstKey());
		System.out.println("Last:" + lastKey());
		System.out.println("Size:" + size());
		System.out.println("Entry:" + entrySet());
		
		System.out.println("ROOT" + Arrays.toString(root.key));
		System.out.println("ROOT" + Arrays.toString(root.ref));
	}
	
	/********************************************************************************
	 * Print the B+Tree using a pre-order traveral and indenting each level.
	 * @param n      the current node to print
	 * @param level  the current level of the B+Tree
	 */
	@SuppressWarnings("unchecked")
	private void print(Node n, int level) {
		if (level == 0)  {
			out.println("BpTreeMap @ Level");
			out.println("-------------------------------------------");
		}
		
		//print node's level
		out.print(level);
		
		for (int j = 0; j < level; j++) {
			out.print("\t");
		}
		out.print("[.");
		for (int i = 0; i < n.nKeys; i++) {
			out.print(n.key [i] + ".");
		}
		
		out.println("]");
		
		if (!n.isLeaf) {
			for (int i = 0; i <= n.nKeys; i++) {
				print((Node) n.ref[i], level + 1);
			}
		} // if
		
		if (n.isLeaf && n.ref[ORDER - 1] == null) {
			out.println ("-------------------------------------------");
		}
	} // print
	
	/********************************************************************************
	 * Recursive helper function for finding a key in B+trees.
	 * @param key  the key to find
	 * @param n  the current node
	 * @return value
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private V find(K key, Node n) {
		count++;
		for (int i = 0; i < n.nKeys; i++) {
			K k_i = n.key[i];
			if (key.compareTo(k_i) <= 0) {
				if (n.isLeaf) {
					return (key.equals(k_i)) ? (V) n.ref[i] : null;
				} else {
					return find(key, (Node) n.ref[i]);
				} // if
			} // if
		} // for
		return (n.isLeaf) ? null : find (key, (Node) n.ref[n.nKeys]);
	} // find
	
	/********************************************************************************
	 * Non-Recursive helper function for inserting a key in B+trees.
	 * @param key  the key to insert
	 * @param ref  the value/node to insert
	 * @param n    the current node
	 * @param p    the parent node
	 */
	@SuppressWarnings("unchecked")
	private void insert(K key, V ref, Node n, Node p) {
		if (DEBUG) out.println("insert--" + key);
		
		//only one node
		if (root.isLeaf) {
			//empty or not full yet
			if (n.nKeys < ORDER - 1) {
				wedge(key, ref, n, 0);
			} else { //is full
				//key is a duplicate
				if (Arrays.stream(n.key).anyMatch(key::equals)) {
					wedge(key, ref, n, 0);
				} else { //not duplicate
					//split the node n
					Node right = split(key, ref, n);
					
					//push up first key of right to new root node
					root = new Node(false);
					root.key[0] = right.key[0];
					root.nKeys++;
					
					//link root to n and right
					root.ref[0] = n;
					root.ref[1] = right;
				}
			}
		} else { //more than one node
			//keep track of path through tree
			Stack<Node> stack = new Stack<Node>();
			
			//find leaf to insert on
			Node node = locate(key, stack);
			
			if (DEBUG) out.println("STACK: " + stack);
			
			//empty or not full yet
			if (node.nKeys < ORDER - 1) {
				wedge(key, ref, node, 0);
			} else { //is full
				//key is a duplicate
				if (Arrays.stream(node.key).anyMatch(key::equals)) {
					wedge(key, ref, node, 0);
				} else { //not duplicate
					//split the node n
					Node right = split(key, ref, node);
					
					//get parent - push up first key of right to parent node
					Node parent = stack.pop();
					
					//not full parent add
					if (parent.nKeys < ORDER-1) {
						wedge(right.key[0], (V) right, parent, 1);
						return;
					}
					//full parent split
					else {
						if (DEBUG) out.println("Parent is full " + parent.nKeys);
						
						//split internal node
						Node rParent = new Node(false);
						K middleKey = iSplit(right.key[0], (V) right, parent, rParent);
						
						//root
						if (parent == root) {
							Node newRoot = new Node(false);
							newRoot.key[0] = middleKey;
							newRoot.ref[0] = parent;
							newRoot.ref[1] = rParent;
							newRoot.nKeys++;
							
							//set new root
							root = newRoot;
							
							return;
						} else { // not root
							if (DEBUG) out.println("=STEP ONE");
							
							//don't need to check, bc parent not root, must be good
							Node parent1 = stack.pop();
							
							//parent is not full
							if (parent1.nKeys < ORDER -1) {
								//link parent to right child
								wedge(middleKey, (V) rParent, parent1, 1);
								return;
							} else { //its full split
								//split the parent
								Node rParent1 = new Node(false);
								K middleKey1 = iSplit(middleKey, (V) rParent, parent1, rParent1);
								
								//parent is root
								if (parent1 == root) {
									Node newRoot = new Node(false);
									newRoot.key[0] = middleKey1;
									newRoot.ref[0] = parent1;
									newRoot.ref[1] = rParent1;
									newRoot.nKeys++;
									
									//set new root
									root = newRoot;
									
									return;
								} else { //not root
									if (DEBUG) out.println("=STEP TWO");
									
									//don't need to check, bc parent not root, must be good
									Node parent2 = stack.pop();
									
									//parent is not full
									if (parent2.nKeys < ORDER -1) {
										//link parent to right child
										wedge(middleKey1, (V) rParent1, parent2, 1);
										return;
									} else { //its full split
										//split the parent
										Node rParent2 = new Node(false);
										K middleKey2 = iSplit(middleKey1, (V) rParent1, parent2, rParent2);
										
										//parent is root
										if (parent2 == root) {
											Node newRoot = new Node(false);
											newRoot.key[0] = middleKey2;
											newRoot.ref[0] = parent2;
											newRoot.ref[1] = rParent2;
											newRoot.nKeys++;
											
											//set new root
											root = newRoot;
											
											return;
										} else { //not root
											if (DEBUG) out.println("=STEP THREE");
											
											//don't need to check, bc parent not root, must be good
											Node parent3 = stack.pop();
											
											//parent is not full
											if (parent3.nKeys < ORDER -1) {
												//link parent to right child
												wedge(middleKey2, (V) rParent2, parent3, 1);
												return;
											} else { //its full split
												//split the parent
												Node rParent3 = new Node(false);
												K middleKey3 = iSplit(middleKey2, (V) rParent2, parent3, rParent3);
												
												//parent is root
												if (parent3 == root) {
													Node newRoot = new Node(false);
													newRoot.key[0] = middleKey3;
													newRoot.ref[0] = parent3;
													newRoot.ref[1] = rParent3;
													newRoot.nKeys++;
													
													//set new root
													root = newRoot;
													
													return;
												} else { //not root
													if (DEBUG) out.println("=STEP FOUR");
													
													//don't need to check, bc parent not root, must be good
													Node parent4 = stack.pop();
													
													//parent is not full
													if (parent4.nKeys < ORDER -1) {
														//link parent to right child
														wedge(middleKey3, (V) rParent3, parent4, 1);
														return;
													} else { //its full split
														//split the parent
														Node rParent4 = new Node(false);
														K middleKey4 = iSplit(middleKey3, (V) rParent3, parent4, rParent4);
														
														//parent is root
														if (parent4 == root) {
															Node newRoot = new Node(false);
															newRoot.key[0] = middleKey4;
															newRoot.ref[0] = parent4;
															newRoot.ref[1] = rParent4;
															newRoot.nKeys++;
															
															//set new root
															root = newRoot;
															
															return;
														} else { //not root
															if (DEBUG) out.println("=STEP FIVE");
															
															//don't need to check, bc parent not root, must be good
															Node parent5 = stack.pop();
															
															//parent is not full
															if (parent5.nKeys < ORDER -1) {
																//link parent to right child
																wedge(middleKey4, (V) rParent4, parent5, 1);
																return;
															} else { //its full split
																//split the parent
																Node rParent5 = new Node(false);
																K middleKey5 = iSplit(middleKey4, (V) rParent4, parent5, rParent5);
																
																//parent is root
																if (parent5 == root) {
																	Node newRoot = new Node(false);
																	newRoot.key[0] = middleKey5;
																	newRoot.ref[0] = parent5;
																	newRoot.ref[1] = rParent5;
																	newRoot.nKeys++;
																	
																	//set new root
																	root = newRoot;
																	
																	return;
																} else { //not root
																	if (DEBUG) out.println("=STEP SIX");
																	
																	//don't need to check, bc parent not root, must be good
																	Node parent6 = stack.pop();
																	
																	//parent is not full
																	if (parent6.nKeys < ORDER -1) {
																		//link parent to right child
																		wedge(middleKey5, (V) rParent5, parent6, 1);
																		return;
																	} else { //its full split
																		//split the parent
																		Node rParent6 = new Node(false);
																		K middleKey6 = iSplit(middleKey5, (V) rParent5, parent6, rParent6);
																		
																		//parent is root
																		if (parent6 == root) {
																			Node newRoot = new Node(false);
																			newRoot.key[0] = middleKey6;
																			newRoot.ref[0] = parent6;
																			newRoot.ref[1] = rParent6;
																			newRoot.nKeys++;
																			
																			//set new root
																			root = newRoot;
																			
																			return;
																		} else { //not root
																			if (DEBUG) out.println("=STEP SEVEN");
																			
																			//don't need to check, bc parent not root, must be good
																			Node parent7 = stack.pop();
																			
																			//parent is not full
																			if (parent7.nKeys < ORDER -1) {
																				//link parent to right child
																				wedge(middleKey6, (V) rParent6, parent7, 1);
																				return;
																			} else { //its full split
																				//split the parent
																				Node rParent7 = new Node(false);
																				K middleKey7 = iSplit(middleKey6, (V) rParent6, parent7, rParent7);
																				
																				//parent is root
																				if (parent7 == root) {
																					Node newRoot = new Node(false);
																					newRoot.key[0] = middleKey7;
																					newRoot.ref[0] = parent7;
																					newRoot.ref[1] = rParent7;
																					newRoot.nKeys++;
																					
																					//set new root
																					root = newRoot;
																					
																					return;
																				} else { //not root
																					if (DEBUG) out.println("=STEP EIGHT");
																					
																					//don't need to check, bc parent not root, must be good
																					Node parent8 = stack.pop();
																					
																					//parent is not full
																					if (parent8.nKeys < ORDER -1) {
																						//link parent to right child
																						wedge(middleKey7, (V) rParent7, parent8, 1);
																						return;
																					} else { //its full split
																						//split the parent
																						Node rParent8 = new Node(false);
																						K middleKey8 = iSplit(middleKey7, (V) rParent7, parent8, rParent8);
																						
																						//parent is root
																						if (parent8 == root) {
																							Node newRoot = new Node(false);
																							newRoot.key[0] = middleKey8;
																							newRoot.ref[0] = parent8;
																							newRoot.ref[1] = rParent8;
																							newRoot.nKeys++;
																							
																							//set new root
																							root = newRoot;
																							
																							return;
																						} else { //not root
																							if (DEBUG) out.println("=STEP NINE");
																							
																							//don't need to check, bc parent not root, must be good
																							Node parent9 = stack.pop();
																							
																							//parent is not full
																							if (parent9.nKeys < ORDER -1) {
																								//link parent to right child
																								wedge(middleKey8, (V) rParent8, parent9, 1);
																								return;
																							} else { //its full split
																								//split the parent
																								Node rParent9 = new Node(false);
																								K middleKey9 = iSplit(middleKey8, (V) rParent8, parent9, rParent9);
																								
																								//parent is root
																								if (parent9 == root) {
																									Node newRoot = new Node(false);
																									newRoot.key[0] = middleKey9;
																									newRoot.ref[0] = parent9;
																									newRoot.ref[1] = rParent9;
																									newRoot.nKeys++;
																									
																									//set new root
																									root = newRoot;
																									
																									return;
																								} else { //not root
																									if (DEBUG) out.println("=STEP TEN");
																									
																									//don't need to check, bc parent not root, must be good
																									Node parent10 = stack.pop();
																									
																									//parent is not full
																									if (parent10.nKeys < ORDER -1) {
																										//link parent to right child
																										wedge(middleKey9, (V) rParent9, parent10, 1);
																										return;
																									} else { //its full split
																										//split the parent
																										Node rParent10 = new Node(false);
																										K middleKey10 = iSplit(middleKey9, (V) rParent9, parent10, rParent10);
																										
																										//parent is root
																										if (parent10 == root) {
																											Node newRoot = new Node(false);
																											newRoot.key[0] = middleKey10;
																											newRoot.ref[0] = parent10;
																											newRoot.ref[1] = rParent10;
																											newRoot.nKeys++;
																											
																											//set new root
																											root = newRoot;
																											
																											return;
																										} else {
																											if (DEBUG) System.out.println("HELLO");
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	} // insert
	
	/********************************************************************************
	 * Non-Recursive helper function for locating tuple where insertion will occur
	 * @param key  the key to insert
	 * @param stack    trace to root
	 * @return located node
	 */
	@SuppressWarnings("unchecked")
	private Node locate(K key, Stack<Node> stack) {
		//set the starting node
		Node node = root;
		
		//find the value
		for (int i = 0; i < node.nKeys; i++) {
			if (node.isLeaf) { //node is a leaf
				return node;
			} else if (i == 0 && key.compareTo(node.key[i]) < 0) { //first reference
				stack.push(node); //adds parent to stack
				node = (Node) node.ref[i];
				i = -1;
			} else if (i > 0 && key.compareTo(node.key[i-1]) >= 0 && key.compareTo(node.key[i]) < 0) { //go left
				stack.push(node);
				node = (Node) node.ref[i];
				i = -1;
			} else if (i == node.nKeys-1) { //go right
				stack.push(node);
				node = (Node) node.ref[i+1];
				i = -1;
			}
		}
		
		if (DEBUG) System.out.println("RRRRRRRRRRRRRR");
		
		return null;
	} // locate
	
	/********************************************************************************
	 * Wedge the key-ref pair into node n.
	 * @param key  the key to insert
	 * @param ref  the value/node to insert
	 * @param n    the current node
	 * @param i    the insertion position offset within node n
	 */
	@SuppressWarnings("unchecked")
	private void wedge(K key, V ref, Node n, int i) {
		//empty Node
		if (n.nKeys == 0) {
			n.key[n.nKeys] = key;
			n.ref[n.nKeys] = ref;
			
			//increment # of keys
			n.nKeys++;
		} 
		//not empty nodes
		else {
			//Map to store node key and ref pairs
			Map<K, V> sortedMap = new TreeMap<K, V>();
			
			//Add node current pairs to map
			for (int j = 0; j < n.nKeys; j++) {
				sortedMap.put(n.key[j], (V) n.ref[j+i]);
			}
			//Add key and ref to add
			sortedMap.put(key, ref);
			
			//set # of keys
			n.nKeys = sortedMap.size();
			
			/*
			 * Override values with sorted values
			 * src, srcPos, dest, destPos, length
			 */
			System.arraycopy(sortedMap.keySet().toArray(), 0, n.key, 0, n.nKeys);
			System.arraycopy(sortedMap.values().toArray(), 0, n.ref, i, n.nKeys);
		}
	} // wedge
	
	/********************************************************************************
	 * Split internal node n and return the key to be bubbled up the tree
	 * @param key  the key to insert
	 * @param ref  the value/node to insert
	 * @param left    the current node
	 * @param right    the newer node
	 * @return key
	 */
	@SuppressWarnings("unchecked")
	private K iSplit(K key, V ref, Node left, Node right) {
		//Map to store node key and ref pairs
		Map<K, V> sortedMap = new TreeMap<K, V>();
		
		//Add node current pairs to map
		for (int j = 0; j < left.nKeys; j++) {
			sortedMap.put(left.key[j], (V) left.ref[j+1]);
		}
		//Add key and ref to add
		sortedMap.put(key, ref);
		
		//clean array - a, fromIndex, toIndex, val
		Arrays.fill(left.key, 0, ORDER - 1, null);
		Arrays.fill(left.ref, 1, ORDER, null);
		
		//left - src, srcPos, dest, destPos, length
		System.arraycopy(sortedMap.keySet().toArray(), 0, left.key, 0, HALF);
		System.arraycopy(sortedMap.values().toArray(), 0, left.ref, 1, HALF);
		left.nKeys = HALF;
		
		//right - src, srcPos, dest, destPos, length
		System.arraycopy(sortedMap.keySet().toArray(), MIDDLE, right.key, 0, HALF);
		System.arraycopy(sortedMap.values().toArray(), MIDDLE-1, right.ref, 0, HALF+1);
		right.nKeys = HALF;
		
		if (DEBUG) System.out.println("MIDDLER: " + (K) sortedMap.keySet().toArray()[MIDDLE -1]);
		
		return (K) sortedMap.keySet().toArray()[MIDDLE -1];
	} // split
	
	/********************************************************************************
	 * Split node n and return the newly created node.
	 * @param key  the key to insert
	 * @param ref  the value/node to insert
	 * @param n    the current node
	 * @return right split node
	 */
	@SuppressWarnings("unchecked")
	private Node split(K key, V ref, Node n) {
		/*
		 * Node n START
		 * [3, 5, 7, 9]
		 * [9, 25, 49, 81, null]
		 * key = 1
		 * ref = 1
		 */
		
		//Creates a new right node
		Node right = new Node(n.isLeaf);
		
		//Map to store node key and ref pairs
		Map<K, V> sortedMap = new TreeMap<K, V>();
		
		//Add node current pairs to map
		for (int i = 0; i < n.nKeys; i++) {
			sortedMap.put(n.key[i], (V) n.ref[i]);
		}
		//Add key and ref to insert
		sortedMap.put(key, ref);
		
		/*
		 * Fill with null to 'n'
		 * a, fromIndex, toIndex, val
		 * [3, 5, null, null]
		 * [9, 25, null, null, null]
		 */
		Arrays.fill(n.key, HALF, ORDER - 1, null);
		Arrays.fill(n.ref, HALF, ORDER - 1, null);
		
		/*
		 * Copy over first half to 'n'
		 * src, srcPos, dest, destPos, length
		 * [1, 3, null, null]
		 * [1, 9, null, null, null]
		 */
		System.arraycopy(sortedMap.keySet().toArray(), 0, n.key, 0, HALF);
		System.arraycopy(sortedMap.values().toArray(), 0, n.ref, 0, HALF);
		n.nKeys = HALF; //set n.nKeys
		
		/*
		 * Copy over rest to 'right'
		 * src, srcPos, dest, destPos, length
		 * [5, 7, 9, null]
		 * [25, 49, 81, null, null]
		 */
		System.arraycopy(sortedMap.keySet().toArray(), HALF, right.key, 0, ORDER-HALF);
		System.arraycopy(sortedMap.values().toArray(), HALF, right.ref, 0, ORDER-HALF);
		right.nKeys = ORDER-HALF; //set right.nKeys
		
		//link n to right
		if (n.isLeaf && right.isLeaf) {
			if (DEBUG) System.out.println("both are leaf");
			//right -> (n.next)
			right.ref[ORDER - 1] = n.ref[ORDER - 1];
			
			//n -> right
			n.ref[ORDER - 1] = right;
			
		}
		
		return right;
	} // split
	
	/********************************************************************************
	 * The main method used for testing.
	 * @param args  the command-line arguments (args [0] gives number of keys to insert)
	 */
	public static void main(String [] args) {
		BpTreeMap<Integer, Integer> bpt = new BpTreeMap<>(Integer.class, Integer.class);
		int totKeys = 10;
		
		if (args.length == 1) {
			totKeys = Integer.valueOf(args[0]);
		}
		
		for (int i = 1; i < totKeys; i += 2) {
			bpt.put(i, i * i);
		}
		
		bpt.print(bpt.root, 0);
		
		for (int i = 0; i < totKeys; i++) {
			out.println ("key = " + i + " value = " + bpt.get (i));
		} // for
		
		out.println ("-------------------------------------------");
		out.println ("Average number of nodes accessed = " + bpt.count / (double) totKeys);
	} // main
} // BpTreeMap class
