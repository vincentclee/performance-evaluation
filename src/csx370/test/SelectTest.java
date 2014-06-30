package csx370.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import csx370.impl.TupleGenerator;
import csx370.impl.TupleGeneratorImpl;
import csx370.operator.KeyType;
import csx370.operator.Table;

/**
 * Tests for sequential select vs. indexed select
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectTest {
	/** Student Tables */
	private static Table Student_1000, Student_2000, Student_5000, Student_10000, Student_50000;
	
	/** Professor Tables */
	private static Table Professor_1000, Professor_2000, Professor_5000, Professor_10000, Professor_50000;
	
	/** Course Tables */
	private static Table Course_1000, Course_2000, Course_5000, Course_10000, Course_50000;
	
	/** Teaching Tables */
	private static Table Teaching_1000, Teaching_2000, Teaching_5000, Teaching_10000, Teaching_50000;
	
	/** Transcript Tables */
	private static Table Transcript_1000, Transcript_2000, Transcript_5000, Transcript_10000, Transcript_50000;
	
	/** Generated Random Data Storage */
	@SuppressWarnings("rawtypes")
	private static List<Comparable[]> Student_Data, Professor_Data, Course_Data, Teaching_Data, Transcript_Data;
	
	/**
	 * Set up Tables and Data
	 */
	@SuppressWarnings("rawtypes")
	@BeforeClass
	public static void setUpBeforeClass() {
		//Create Random Data Storage
		Student_Data = new ArrayList<Comparable[]>();
		Professor_Data = new ArrayList<Comparable[]>();
		Course_Data = new ArrayList<Comparable[]>();
		Teaching_Data = new ArrayList<Comparable[]>();
		Transcript_Data = new ArrayList<Comparable[]>();
		
		
		//Create Tables
		Student_1000 = new Table("Student", "id name address status", "Integer String String String", "id");
		Student_2000 = new Table("Student", "id name address status", "Integer String String String", "id");
		Student_5000 = new Table("Student", "id name address status", "Integer String String String", "id");
		Student_10000 = new Table("Student", "id name address status", "Integer String String String", "id");
		Student_50000 = new Table("Student", "id name address status", "Integer String String String", "id");
		
		Professor_1000 = new Table("Professor", "id name deptId", "Integer String String", "id");
		Professor_2000 = new Table("Professor", "id name deptId", "Integer String String", "id");
		Professor_5000 = new Table("Professor", "id name deptId", "Integer String String", "id");
		Professor_10000 = new Table("Professor", "id name deptId", "Integer String String", "id");
		Professor_50000 = new Table("Professor", "id name deptId", "Integer String String", "id");
		
		Course_1000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode");
		Course_2000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode");
		Course_5000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode");
		Course_10000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode");
		Course_50000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode");
		
		Teaching_1000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester");
		Teaching_2000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester");
		Teaching_5000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester");
		Teaching_10000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester");
		Teaching_50000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester");
		
		Transcript_1000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester");
		Transcript_2000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester");
		Transcript_5000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester");
		Transcript_10000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester");
		Transcript_50000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester");
		
		
		//Generate Data for those tuples
		TupleGenerator test = new TupleGeneratorImpl();
		
		//Schemas
		test.addRelSchema("Student", "id name address status", "Integer String String String", "id", null);
		test.addRelSchema("Professor", "id name deptId", "Integer String String", "id", null);
		test.addRelSchema("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", null);
		test.addRelSchema("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", null);
		test.addRelSchema("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", null);
		
		//Tuple sizes (all 50,000)
		int[] tups = new int[]{50000, 50000, 50000, 50000, 50000};
		
		//Generate random data
		Comparable[][][] resultTest = test.generate(tups);
		
		//Student Tables
		for (int i = 0; i < resultTest[0].length; i++) {
			if (i < 1000) {Student_1000.insert(resultTest[0][i]);}
			if (i < 2000) {Student_2000.insert(resultTest[0][i]);}
			if (i < 5000) {Student_5000.insert(resultTest[0][i]);}
			if (i < 10000) {Student_10000.insert(resultTest[0][i]);}
			Student_50000.insert(resultTest[0][i]);
			Student_Data.add(resultTest[0][i]);
		}
		//Professor Tables
		for (int i = 0; i < resultTest[1].length; i++) {
			if (i < 1000) {Professor_1000.insert(resultTest[1][i]);}
			if (i < 2000) {Professor_2000.insert(resultTest[1][i]);}
			if (i < 5000) {Professor_5000.insert(resultTest[1][i]);}
			if (i < 10000) {Professor_10000.insert(resultTest[1][i]);}
			Professor_50000.insert(resultTest[1][i]);
			Professor_Data.add(resultTest[1][i]);
		}
		//Course Tables
		for (int i = 0; i < resultTest[2].length; i++) {
			if (i < 1000) {Course_1000.insert(resultTest[2][i]);}
			if (i < 2000) {Course_2000.insert(resultTest[2][i]);}
			if (i < 5000) {Course_5000.insert(resultTest[2][i]);}
			if (i < 10000) {Course_10000.insert(resultTest[2][i]);}
			Course_50000.insert(resultTest[2][i]);
			Course_Data.add(resultTest[2][i]);
		}
		//Teaching Tables
		for (int i = 0; i < resultTest[3].length; i++) {
			if (i < 1000) {Teaching_1000.insert(resultTest[3][i]);}
			if (i < 2000) {Teaching_2000.insert(resultTest[3][i]);}
			if (i < 5000) {Teaching_5000.insert(resultTest[3][i]);}
			if (i < 10000) {Teaching_10000.insert(resultTest[3][i]);}
			Teaching_50000.insert(resultTest[3][i]);
			Teaching_Data.add(resultTest[3][i]);
		}
		//Transcript Tables
		for (int i = 0; i < resultTest[4].length; i++) {
			if (i < 1000) {Transcript_1000.insert(resultTest[4][i]);}
			if (i < 2000) {Transcript_2000.insert(resultTest[4][i]);}
			if (i < 5000) {Transcript_5000.insert(resultTest[4][i]);}
			if (i < 10000) {Transcript_10000.insert(resultTest[4][i]);}
			Transcript_50000.insert(resultTest[4][i]);
			Transcript_Data.add(resultTest[4][i]);
		}
	}
	
	/**
	 * Checks Tables for correct allocation of Tuples (size)
	 */
	@Test
	public void sizeTest() {
		//Student Tables
		assertEquals("Student Table Size", 1000, Student_1000.size());
		assertEquals("Student Table Size", 2000, Student_2000.size());
		assertEquals("Student Table Size", 5000, Student_5000.size());
		assertEquals("Student Table Size", 10000, Student_10000.size());
		assertEquals("Student Table Size", 50000, Student_50000.size());
		
		//Professor Tables
		assertEquals("Professor Table Size", 1000, Professor_1000.size());
		assertEquals("Professor Table Size", 2000, Professor_2000.size());
		assertEquals("Professor Table Size", 5000, Professor_5000.size());
		assertEquals("Professor Table Size", 10000, Professor_10000.size());
		assertEquals("Professor Table Size", 50000, Professor_50000.size());
		
		//Course Tables
		assertEquals("Course Table Size", 1000, Course_1000.size());
		assertEquals("Course Table Size", 2000, Course_2000.size());
		assertEquals("Course Table Size", 5000, Course_5000.size());
		assertEquals("Course Table Size", 10000, Course_10000.size());
		assertEquals("Course Table Size", 50000, Course_50000.size());
		
		//Teaching Tables
		assertEquals("Teaching Table Size", 1000, Teaching_1000.size());
		assertEquals("Teaching Table Size", 2000, Teaching_2000.size());
		assertEquals("Teaching Table Size", 5000, Teaching_5000.size());
		assertEquals("Teaching Table Size", 10000, Teaching_10000.size());
		assertEquals("Teaching Table Size", 50000, Teaching_50000.size());
		
		//Transcript Tables
		assertEquals("Transcript Table Size", 1000, Transcript_1000.size());
		assertEquals("Transcript Table Size", 2000, Transcript_2000.size());
		assertEquals("Transcript Table Size", 5000, Transcript_5000.size());
		assertEquals("Transcript Table Size", 10000, Transcript_10000.size());
		assertEquals("Transcript Table Size", 50000, Transcript_50000.size());
		
		//Generated Random Data
		assertEquals("Student Data Size", 50000, Student_Data.size());
		assertEquals("Professor Data Size", 50000, Professor_Data.size());
		assertEquals("Course Data Size", 50000, Course_Data.size());
		assertEquals("Teaching Data Size", 50000, Teaching_Data.size());
		assertEquals("Transcript Data Size", 50000, Transcript_Data.size());
	}
	
	// ----------------------------------------------------------------------------------
	// Student Tests
	// ----------------------------------------------------------------------------------
	
	/**
	 * 1000 Tuple Student Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_1000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			Student_1000.select(new KeyType(instance[0]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 1000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_1000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_1000.select(t -> t[Student_1000.col("name")].equals(instance[1]) && 
					t[Student_1000.col("address")].equals(instance[2]) && 
					t[Student_1000.col("status")].equals(instance[3]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Student Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_2000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_2000.select(new KeyType(instance[0]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_2000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_2000.select(t -> t[Student_2000.col("name")].equals(instance[1]) && 
					t[Student_2000.col("address")].equals(instance[2]) && 
					t[Student_2000.col("status")].equals(instance[3]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Student Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_5000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_5000.select(new KeyType(instance[0]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_5000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_5000.select(t -> t[Student_5000.col("name")].equals(instance[1]) && 
					t[Student_5000.col("address")].equals(instance[2]) && 
					t[Student_5000.col("status")].equals(instance[3]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Student Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_10000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_10000.select(new KeyType(instance[0]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_10000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_10000.select(t -> t[Student_10000.col("name")].equals(instance[1]) && 
					t[Student_10000.col("address")].equals(instance[2]) && 
					t[Student_10000.col("status")].equals(instance[3]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 50000 Tuple Student Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_50000_IndexedSelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_50000.select(new KeyType(instance[0]));
		}
	}
	
	/**
	 * 50000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_50000_SelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			//Select Test
			Student_50000.select(t -> t[Student_50000.col("name")].equals(instance[1]) && 
					t[Student_50000.col("address")].equals(instance[2]) && 
					t[Student_50000.col("status")].equals(instance[3]));
		}
	}
	
	// ----------------------------------------------------------------------------------
	// Professor Tests
	// ----------------------------------------------------------------------------------
	
	/**
	 * 1000 Tuple Professor Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_1000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			Professor_1000.select(new KeyType(instance[0]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 1000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_1000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_1000.select(t -> t[Professor_1000.col("name")].equals(instance[1]) && 
					t[Professor_1000.col("deptId")].equals(instance[2]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Professor Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_2000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_2000.select(new KeyType(instance[0]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_2000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_2000.select(t -> t[Professor_2000.col("name")].equals(instance[1]) && 
					t[Professor_2000.col("deptId")].equals(instance[2]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Professor Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_5000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_5000.select(new KeyType(instance[0]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_5000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_5000.select(t -> t[Professor_5000.col("name")].equals(instance[1]) && 
					t[Professor_5000.col("deptId")].equals(instance[2]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Professor Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_10000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_10000.select(new KeyType(instance[0]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_10000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_10000.select(t -> t[Professor_10000.col("name")].equals(instance[1]) && 
					t[Professor_10000.col("deptId")].equals(instance[2]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 50000 Tuple Professor Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_50000_IndexedSelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_50000.select(new KeyType(instance[0]));
		}
	}
	
	/**
	 * 50000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_50000_SelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			//Select Test
			Professor_50000.select(t -> t[Professor_50000.col("name")].equals(instance[1]) && 
					t[Professor_50000.col("deptId")].equals(instance[2]));
		}
	}
	
	// ----------------------------------------------------------------------------------
	// Course Tests
	// ----------------------------------------------------------------------------------
	
	/**
	 * 1000 Tuple Course Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_1000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			Course_1000.select(new KeyType(instance[0]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 1000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_1000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_1000.select(t -> t[Course_1000.col("deptId")].equals(instance[1]) && 
					t[Course_1000.col("crsName")].equals(instance[2]) && 
					t[Course_1000.col("descr")].equals(instance[3]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Course Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_2000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_2000.select(new KeyType(instance[0]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_2000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_2000.select(t -> t[Course_2000.col("deptId")].equals(instance[1]) && 
					t[Course_2000.col("crsName")].equals(instance[2]) && 
					t[Course_2000.col("descr")].equals(instance[3]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Course Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_5000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_5000.select(new KeyType(instance[0]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_5000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_5000.select(t -> t[Course_5000.col("deptId")].equals(instance[1]) && 
					t[Course_5000.col("crsName")].equals(instance[2]) && 
					t[Course_5000.col("descr")].equals(instance[3]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Course Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_10000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_10000.select(new KeyType(instance[0]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_10000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_10000.select(t -> t[Course_10000.col("deptId")].equals(instance[1]) && 
					t[Course_10000.col("crsName")].equals(instance[2]) && 
					t[Course_10000.col("descr")].equals(instance[3]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 50000 Tuple Course Index Select - 1 Key
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_50000_IndexedSelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_50000.select(new KeyType(instance[0]));
		}
	}
	
	/**
	 * 50000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_50000_SelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			//Select Test
			Course_50000.select(t -> t[Course_50000.col("deptId")].equals(instance[1]) && 
					t[Course_50000.col("crsName")].equals(instance[2]) && 
					t[Course_50000.col("descr")].equals(instance[3]));
		}
	}
	
	// ----------------------------------------------------------------------------------
	// Teaching Tests
	// ----------------------------------------------------------------------------------
	
	/**
	 * 1000 Tuple Teaching Index Select - 2 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_1000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			Teaching_1000.select(new KeyType(instance[0], instance[1]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 1000 Tuple Teaching Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_1000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_1000.select(t -> t[Teaching_1000.col("profId")].equals(instance[2]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Teaching Index Select - 2 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_2000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_2000.select(new KeyType(instance[0], instance[1]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Teaching Select - 1 Attributes
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_2000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_2000.select(t -> t[Teaching_2000.col("profId")].equals(instance[2]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Teaching Index Select - 2 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_5000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_5000.select(new KeyType(instance[0], instance[1]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Teaching Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_5000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_5000.select(t -> t[Teaching_5000.col("profId")].equals(instance[2]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Teaching Index Select - 2 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_10000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_10000.select(new KeyType(instance[0], instance[1]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Teaching Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_10000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_10000.select(t -> t[Teaching_10000.col("profId")].equals(instance[2]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 50000 Tuple Teaching Index Select - 2 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_50000_IndexedSelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_50000.select(new KeyType(instance[0], instance[1]));
		}
	}
	
	/**
	 * 50000 Tuple Teaching Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_50000_SelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			//Select Test
			Teaching_50000.select(t -> t[Teaching_50000.col("profId")].equals(instance[2]));
		}
	}
	
	// ----------------------------------------------------------------------------------
	// Transcript Tests
	// ----------------------------------------------------------------------------------
	
	/**
	 * 1000 Tuple Transcript Index Select - 3 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_1000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			Transcript_1000.select(new KeyType(instance[0], instance[1], instance[2]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 1000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_1000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_1000.select(t -> t[Transcript_1000.col("grade")].equals(instance[3]));
			
			//stop at 1000 iterations
			if (++counter == 1000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Transcript Index Select - 3 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_2000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_2000.select(new KeyType(instance[0], instance[1], instance[2]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 2000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_2000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_2000.select(t -> t[Transcript_2000.col("grade")].equals(instance[3]));
			
			//stop at 2000 iterations
			if (++counter == 2000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Transcript Index Select - 3 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_5000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_5000.select(new KeyType(instance[0], instance[1], instance[2]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 5000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_5000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_5000.select(t -> t[Transcript_5000.col("grade")].equals(instance[3]));
			
			//stop at 5000 iterations
			if (++counter == 5000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Transcript Index Select - 3 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_10000_IndexedSelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_10000.select(new KeyType(instance[0], instance[1], instance[2]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 10000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_10000_SelectTest() {
		int counter = 0;
		
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_10000.select(t -> t[Transcript_10000.col("grade")].equals(instance[3]));
			
			//stop at 10000 iterations
			if (++counter == 10000) {
				break;
			}
		}
	}
	
	/**
	 * 50000 Tuple Transcript Index Select - 3 Keys
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_50000_IndexedSelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_50000.select(new KeyType(instance[0], instance[1], instance[2]));
		}
	}
	
	/**
	 * 50000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_50000_SelectTest() {
		//iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			//Select Test
			Transcript_50000.select(t -> t[Transcript_50000.col("grade")].equals(instance[3]));
		}
	}
}
