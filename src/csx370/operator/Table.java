package csx370.operator;

/****************************************************************************************
 * @file   Table.java
 * @author John Miller
 */

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.lang.System.err;

/****************************************************************************************
 * This class implements relational database tables (including attribute names,
 * domains and a list of tuples. Five basic relational algebra operators are
 * provided: project, select, union, minus join. The insert data manipulation
 * operator is also provided. Missing are update and delete data manipulation
 * operators.
 */
public class Table implements Serializable {
	/** Debugger */
	private static final boolean CONSOLE_OUTPUT = false;
	
	/** Serialization */
	private static final long serialVersionUID = 1L;
	
	/** Relative path for storage directory */
	private static final String DIR = "store" + File.separator;
	
	/** Filename extension for database files */
	private static final String EXT = ".dbf";
	
	/** Counter for naming temporary tables. */
	private static int count = 0;
	
	/** Table name. */
	private final String name;
	
	/** Array of attribute names. */
	private final String[] attribute;
	
	/**
	 * Array of attribute domains: a domain may be integer types: Long, Integer,
	 * Short, Byte real types: Double, Float string types: Character, String
	 */
	@SuppressWarnings("rawtypes")
	private final Class[] domain;
	
	/** Collection of tuples (data storage). */
	@SuppressWarnings("rawtypes")
	private final List<Comparable[]> tuples;
	
	/** Primary key. */
	private final String[] key;
	
	/** Index into tuples (maps key to tuple number). */
	@SuppressWarnings("rawtypes")
	private final Map<KeyType, Comparable[]> index;
	
	// ----------------------------------------------------------------------------------
	// Constructors
	// ----------------------------------------------------------------------------------
	
	/************************************************************************************
	 * Construct an empty table from the meta-data specifications.
	 * 
	 * @param _name      the name of the relation
	 * @param _attribute the string containing attributes names
	 * @param _domain    the string containing attribute domains (data types)
	 * @param _key       the primary key
	 */
	@SuppressWarnings("rawtypes")
	public Table(String _name, String[] _attribute, Class[] _domain, String[] _key) {
		name = _name;
		attribute = _attribute;
		domain = _domain;
		key = _key;
		tuples = new ArrayList<>();
		index = new TreeMap<>(); // also try BPTreeMap, LinHashMap or ExtHashMap
	} // constructor
	
	/************************************************************************************
	 * Construct a table from the meta-data specifications and data in _tuples
	 * list.
	 * 
	 * @param _name      the name of the relation
	 * @param _attribute the string containing attributes names
	 * @param _domain    the string containing attribute domains (data types)
	 * @param _key       the primary key
	 * @param _tuples    the list of tuples containing the data
	 */
	@SuppressWarnings("rawtypes")
	public Table(String _name, String[] _attribute, Class[] _domain,
			String[] _key, List<Comparable[]> _tuples) {
		name = _name;
		attribute = _attribute;
		domain = _domain;
		key = _key;
		tuples = _tuples;
		index = new TreeMap<>(); // also try BPTreeMap, LinHashMap or ExtHashMap
	} // constructor
	
	/************************************************************************************
	 * Construct an empty table from the raw string specifications.
	 * 
	 * @param name       the name of the relation
	 * @param attributes the string containing attributes names
	 * @param domains    the string containing attribute domains (data types)
	 * @param _key       the string containing table key
	 */
	public Table(String name, String attributes, String domains, String _key) {
		this(name, attributes.split(" "), findClass(domains.split(" ")), _key
				.split(" "));
		
		if (CONSOLE_OUTPUT) {
			out.println("DDL> create table " + name + " (" + attributes + ")");
		}
	} // constructor
	
	// ----------------------------------------------------------------------------------
	// Public Methods
	// ----------------------------------------------------------------------------------
	
	/************************************************************************************
	 * Project the tuples onto a lower dimension by keeping only the given
	 * attributes. Check whether the original key is included in the projection.
	 * 
	 * #usage movie.project ("title year studioNo")
	 * 
	 * @param attributes the attributes to project onto
	 * @return           a table of projected tuples
	 */
	@SuppressWarnings("rawtypes")
	public Table project(String attributes) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".project (" + attributes + ")");
		}
		String[] attrs = attributes.split(" ");
		Class[] colDomain = extractDom(match(attrs), domain);
		String[] newKey = (Arrays.asList(attrs).containsAll(Arrays.asList(key))) ? key
				: attrs;
		
		List<Comparable[]> rows = null;
		
		// Intialize data structure
		rows = new ArrayList<Comparable[]>();
		
		/*
		 * Used for checking if projected tuple is a duplicate (Avoids O(n^2)
		 * searching) hashCode() is from original object not the projected
		 * object?
		 */
		List<Integer> deepHashCodes = new ArrayList<Integer>();
		
		// Iterate through tuples
		for (Map.Entry<KeyType, Comparable[]> e : index.entrySet()) {
			// use the extract method to only select the colums needed
			Comparable[] tuple = extract(e.getValue(), attrs);
			
			// Generate a Deep Hash Code
			int deepHashCode = Arrays.deepHashCode(tuple);
			
			// If this tuple is a duplicate, don't add to rows List
			if (deepHashCodes.contains(deepHashCode)) {
				continue;
			}
			// add to the rows List
			rows.add(tuple);
			
			// add to the deepHashCodes List
			deepHashCodes.add(deepHashCode);
		}
		
		return new Table(name + count++, attrs, colDomain, newKey, rows);
	} // project
	
	/************************************************************************************
	 * Select the tuples satisfying the given predicate (Boolean function).
	 * 
	 * #usage movie.select (t -{@literal >} t[movie.col("year")].equals (1977))
	 * 
	 * @param predicate the check condition for tuples
	 * @return          a table with tuples satisfying the predicate
	 */
	@SuppressWarnings("rawtypes")
	public Table select(Predicate<Comparable[]> predicate) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".select (" + predicate + ")");
		}
		
		List<Comparable[]> rows = null;
		
		// Parallel Reduction with predicate filter
		rows = tuples.parallelStream().filter(predicate).collect(Collectors.toList());
		
		return new Table(name + count++, attribute, domain, key, rows);
	} // select
	
	/************************************************************************************
	 * Select the tuples satisfying the given key predicate (key = value). Use
	 * an index (Map) to retrieve the tuple with the given key value.
	 * 
	 * @param keyVal the given key value
	 * @return       a table with the tuple satisfying the key predicate
	 */
	@SuppressWarnings("rawtypes")
	public Table select(KeyType keyVal) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".select (" + keyVal + ")");
		}
		
		List<Comparable[]> rows = null;
		
		// Intialize data structure
		rows = new ArrayList<Comparable[]>();
		
		// Get the tuple which has the corresponding key
		rows.add(index.get(keyVal));
		
		return new Table(name + count++, attribute, domain, key, rows);
	} // select
	
	/************************************************************************************
	 * Union this table and table2. Check that the two tables are compatible.
	 * 
	 * #usage movie.union (show)
	 * 
	 * @param table2 the rhs table in the union operation
	 * @return       a table representing the union
	 */
	@SuppressWarnings("rawtypes")
	public Table union(Table table2) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".union (" + table2.name + ")");
		}
		
		if (!compatible(table2)) {
			return null;
		}
		
		List<Comparable[]> rows = null;
		
		// Intialize data structure
		rows = new ArrayList<>();
		
		// Iterate through current tuples
		for (Map.Entry<KeyType, Comparable[]> e : index.entrySet())
			// Add current table tupples
			rows.add(e.getValue());
		
		// Iterate through table2 tuples
		for (Map.Entry<KeyType, Comparable[]> e : table2.index.entrySet())
			// Add table2 table tupples
			rows.add(e.getValue());
		
		return new Table(name + count++, attribute, domain, key, rows);
	} // union
	
	/************************************************************************************
	 * Take the difference of this table and table2. Check that the two tables
	 * are compatible.
	 * 
	 * #usage movie.minus (show)
	 * 
	 * @param table2 The rhs table in the minus operation
	 * @return       a table representing the difference
	 */
	@SuppressWarnings("rawtypes")
	public Table minus(Table table2) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".minus (" + table2.name + ")");
		}
		
		if (!compatible(table2)) {
			return null;
		}
		List<Comparable[]> rows = null;
		
		/*
		 * http://www.orafaq.com/wiki/Minus
		 * 
		 * MINUS is a SQL set operation that selects elements from the first
		 * table and then removes rows that are also returned by the second
		 * SELECT statement.
		 * 
		 * For example, the below query will return all rows that are in
		 * table_A, but not in table_B:
		 * 
		 * SELECT * FROM table_A MINUS SELECT * FROM table_B;
		 * 
		 * Additionally, if there are two identical rows in table_A, and that
		 * same row exists in table_B, BOTH rows from table_A will be removed
		 * from the result set.
		 */
		
		// Intialize data structure
		rows = new ArrayList<Comparable[]>();
		
		// Iterate through tuples
		for (Map.Entry<KeyType, Comparable[]> e : index.entrySet())
			// If key is in the table2 map then do not add to List
			if (!table2.index.containsKey(e.getKey())) {
				rows.add(e.getValue());
			}
		
		return new Table(name + count++, attribute, domain, key, rows);
	} // minus
	
	/************************************************************************************
	 * Join this table and table2 by performing an equijoin. Tuples from both
	 * tables are compared requiring attributes1 to equal attributes2.
	 * Disambiguate attribute names by append "2" to the end of any duplicate
	 * attribute name.
	 * 
	 * #usage movie.join ("studioNo", "name", studio) #usage movieStar.join
	 * ("name == s.name", starsIn)
	 * 
	 * @param attribute1 the attributes of this table to be compared (Foreign Key)
	 * @param attribute2 the attributes of table2 to be compared (Primary Key)
	 * @param table2     the rhs table in the join operation
	 * @return           a table with tuples satisfying the equality predicate
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public Table join(String attribute1, String attribute2, Table table2) {
		if (CONSOLE_OUTPUT) {
			out.println("RA> " + name + ".join (" + attribute1 + ", " + attribute2 + ", " + table2.name + ")");
		}
		
		String[] t_attrs = attribute1.split(" ");
		String[] u_attrs = attribute2.split(" ");
		
		List<Comparable[]> rows = null;
		
		// Intialize data structure
		rows = new ArrayList<Comparable[]>();
		
		// Iterate through tuples
		for (Map.Entry<KeyType, Comparable[]> e : index.entrySet()) {
			// Get the tuple from table2 which matches with the foreign key from
			// current table
			Comparable[] table2Temp = table2.index.get(new KeyType(extract(
					e.getValue(), t_attrs)));
			
			// Check if tupple from table2 exists
			if (table2Temp == null) {
				continue;
			}
			// Create a new tupple for concat
			Comparable[] combined = new Comparable[attribute.length
					+ table2.attribute.length];
			
			// Do the concat
			System.arraycopy(e.getValue(), 0, combined, 0, e.getValue().length);
			System.arraycopy(table2Temp, 0, combined, e.getValue().length,
					table2Temp.length);
			
			// Add tupple to List
			rows.add(combined);
		}
		
		return new Table(name + count++, ArrayUtil.concat(attribute,
				table2.attribute), ArrayUtil.concat(domain, table2.domain),
				key, rows);
	} // join
	
	/************************************************************************************
	 * Return the column position for the given attribute name.
	 * 
	 * @param attr the given attribute name
	 * @return     a column position
	 */
	public int col(String attr) {
		for (int i = 0; i < attribute.length; i++) {
			if (attr.equals(attribute[i])) {
				return i;
			}
		} // for
		
		return -1; // not found
	} // col
	
	/************************************************************************************
	 * Insert a tuple to the table.
	 * 
	 * #usage movie.insert ("'Star_Wars'", 1977, 124, "T", "Fox", 12345)
	 * 
	 * @param tup the array of attribute values forming the tuple
	 * @return    whether insertion was successful
	 */
	@SuppressWarnings("rawtypes")
	public boolean insert(Comparable[] tup) {
		if (CONSOLE_OUTPUT) {
			out.println("DML> insert into " + name + " values ( " + Arrays.toString(tup) + " )");
		}
		
		if (typeCheck(tup, this.domain)) {
			tuples.add(tup);
			Comparable[] keyVal = new Comparable[key.length];
			int[] cols = match(key);
			for (int j = 0; j < keyVal.length; j++) {
				keyVal[j] = tup[cols[j]];
			}
			index.put(new KeyType(keyVal), tup);
			return true;
		} else {
			return false;
		} // if
	} // insert
	
	/************************************************************************************
	 * Get the name of the table.
	 * 
	 * @return the table's name
	 */
	public String getName() {
		return name;
	} // getName
	
	/************************************************************************************
	 * Print this table.
	 */
	@SuppressWarnings("rawtypes")
	public void print() {
		out.println("\n Table " + name);
		out.print("|-");
		for (int i = 0; i < attribute.length; i++) {
			out.print("---------------");
		}
		out.println("-|");
		out.print("| ");
		for (String a : attribute) {
			out.printf("%15s", a);
		}
		out.println(" |");
		out.print("|-");
		for (int i = 0; i < attribute.length; i++) {
			out.print("---------------");
		}
		out.println("-|");
		for (Comparable[] tup : tuples) {
			out.print("| ");
			for (Comparable attr : tup) {
				out.printf("%15s", attr);
			}
			out.println(" |");
		} // for
		out.print("|-");
		for (int i = 0; i < attribute.length; i++) {
			out.print("---------------");
		}
		out.println("-|");
	} // print
	
	/************************************************************************************
	 * Print this table's index (Map).
	 */
	@SuppressWarnings("rawtypes")
	public void printIndex() {
		out.println("\n Index for " + name);
		out.println("-------------------");
		for (Map.Entry<KeyType, Comparable[]> e : index.entrySet()) {
			out.println(e.getKey() + " -> " + Arrays.toString(e.getValue()));
		} // for
		out.println("-------------------");
	} // printIndex
	
	/************************************************************************************
	 * Load the table with the given name into memory.
	 * 
	 * @param name the name of the table to load
	 * @return     a Table
	 */
	public static Table load(String name) {
		Table tab = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					DIR + name + EXT));
			tab = (Table) ois.readObject();
			ois.close();
		} catch (IOException ex) {
			err.println("load: IO Exception");
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			err.println("load: Class Not Found Exception");
			ex.printStackTrace();
		} // try
		return tab;
	} // load
	
	/************************************************************************************
	 * Save this table in a file.
	 */
	public void save() {
		try {
			//Create the directory if it does not exist
			File oosDirectory = new File(DIR);
			if(!oosDirectory.exists()) {
				oosDirectory.mkdir();
			} 
			
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(DIR + name + EXT));
			
			oos.writeObject(this);
			oos.close();
		} catch (IOException ex) {
			err.println("save: IO Exception");
			ex.printStackTrace();
		} // try
	} // save
	
	// ----------------------------------------------------------------------------------
	// Private Methods
	// ----------------------------------------------------------------------------------
	
	/************************************************************************************
	 * Determine whether the two tables (this and table2) are compatible, i.e.,
	 * have the same number of attributes each with the same corresponding
	 * domain.
	 * 
	 * @param table2 the rhs table
	 * @return       whether the two tables are compatible
	 */
	private boolean compatible(Table table2) {
		if (domain.length != table2.domain.length) {
			out.println("compatible ERROR: table have different arity");
			return false;
		} // if
		for (int j = 0; j < domain.length; j++) {
			if (domain[j] != table2.domain[j]) {
				out.println("compatible ERROR: tables disagree on domain " + j);
				return false;
			} // if
		} // for
		return true;
	} // compatible
	
	/************************************************************************************
	 * Match the column and attribute names to determine the domains.
	 * 
	 * @param column the array of column names
	 * @return       an array of column index positions
	 */
	private int[] match(String[] column) {
		int[] colPos = new int[column.length];

		for (int j = 0; j < column.length; j++) {
			boolean matched = false;
			for (int k = 0; k < attribute.length; k++) {
				if (column[j].equals(attribute[k])) {
					matched = true;
					colPos[j] = k;
				} // for
			} // for
			if (!matched) {
				out.println("match: domain not found for " + column[j]);
			} // if
		} // for
		
		return colPos;
	} // match
	
	/************************************************************************************
	 * Extract the attributes specified by the column array from tuple t.
	 * 
	 * @param t      the tuple to extract from
	 * @param column the array of column names
	 * @return       a smaller tuple extracted from tuple t
	 */
	@SuppressWarnings("rawtypes")
	private Comparable[] extract(Comparable[] t, String[] column) {
		Comparable[] tup = new Comparable[column.length];
		int[] colPos = match(column);
		for (int j = 0; j < column.length; j++)
			tup[j] = t[colPos[j]];
		return tup;
	} // extract
	
	/************************************************************************************
	 * Check the size of the tuple (number of elements in list) as well as the
	 * type of each value to ensure it is from the right domain.
	 * 
	 * @param t      the tuple as a list of attribute values
	 * @param domain the set of possible acceptabtle classes for t
	 * @return       whether the tuple has the right size and values that comply with
	 *               the given domains
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean typeCheck(Comparable[] t, Class[] domain) {
		if (t == null || domain == null) {
			return false;
		}
		
		boolean currentItemValid;
		
		// Iterate through all inputs
		for (Comparable item : t) {
			
			currentItemValid = false;
			
			// Verify input against all possibly classes within the domain
			for (Class classTypes : domain) {
				
				// If item is some subclass of a given type within domain,
				// accept it
				if (classTypes.isAssignableFrom(item.getClass())) {
					currentItemValid = true;
					break;
				}
			}
			
			// If the type of the given item within the input is not of
			// subclassable type of any item within the domain, reject the input
			if (!currentItemValid) {
				return false;
			}
		}
		return true;
	}
	
	/************************************************************************************
	 * Find the classes in the "java.lang" package with given names.
	 * 
	 * @param className the array of class name (e.g., {"Integer", "String"})
	 * @return          an array of Java classes
	 */
	@SuppressWarnings("rawtypes")
	private static Class[] findClass(String[] className) {
		Class[] classArray = new Class[className.length];
		
		for (int i = 0; i < className.length; i++) {
			try {
				classArray[i] = Class.forName("java.lang." + className[i]);
			} catch (ClassNotFoundException ex) {
				err.println("findClass: " + ex);
			} // try
		} // for
		
		return classArray;
	} // findClass
	
	/************************************************************************************
	 * Extract the corresponding domains.
	 * 
	 * @param colPos the column positions to extract.
	 * @param group  where to extract from
	 * @return       the extracted domains
	 */
	@SuppressWarnings("rawtypes")
	private Class[] extractDom(int[] colPos, Class[] group) {
		Class[] obj = new Class[colPos.length];
		
		for (int j = 0; j < colPos.length; j++) {
			obj[j] = group[colPos[j]];
		} // for
		
		return obj;
	} // extractDom
	
	// returns number of tuples (for validation)
	public int size() {
		// return the one with higher value
		return (index.size() > tuples.size()) ? index.size() : tuples.size();
	}
} // Table class