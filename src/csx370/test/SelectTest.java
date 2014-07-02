package csx370.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import csx370.impl.TupleGenerator;
import csx370.impl.TupleGeneratorImpl;
import csx370.operator.KeyType;
import csx370.operator.Table;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectTest {
	/** Write Index Structures to Disk */
	protected static final boolean INDEX_STRUCTURES_OUTPUT = true;
	
	/** Home folder absolute location */
	protected static Path path;
	
	/** Relative path for storage directory */
	protected static final String DIRECTORY = "index-structure" + File.separator + "sequential-select-vs-indexed-select";
	
	
	/** Student Tables */
	protected static Table Student_1000, Student_2000, Student_5000,
			Student_10000, Student_50000;

	/** Professor Tables */
	protected static Table Professor_1000, Professor_2000, Professor_5000,
			Professor_10000, Professor_50000;

	/** Course Tables */
	protected static Table Course_1000, Course_2000, Course_5000, Course_10000,
			Course_50000;

	/** Teaching Tables */
	protected static Table Teaching_1000, Teaching_2000, Teaching_5000,
			Teaching_10000, Teaching_50000;

	/** Transcript Tables */
	protected static Table Transcript_1000, Transcript_2000, Transcript_5000,
			Transcript_10000, Transcript_50000;

	/** Generated Random Data Storage */
	@SuppressWarnings("rawtypes")
	protected static List<Comparable[]> Student_Data, Professor_Data,
			Course_Data, Teaching_Data, Transcript_Data;

	/**
	 * Generates data for the tests
	 */
	@SuppressWarnings("rawtypes")
	protected static void generateData() {

		// Generate Data for those tuples
		TupleGenerator test = new TupleGeneratorImpl();

		// Schemas
		test.addRelSchema("Student", "id name address status",
				"Integer String String String", "id", null);
		test.addRelSchema("Professor", "id name deptId",
				"Integer String String", "id", null);
		test.addRelSchema("Course", "crsCode deptId crsName descr",
				"String String String String", "crsCode", null);
		test.addRelSchema("Teaching", "crsCode semester profId",
				"String String Integer", "crsCode semester", null);
		test.addRelSchema("Transcript", "studId crsCode semester grade",
				"Integer String String String", "studId crsCode semester", null);

		// Tuple sizes (all 50,000)
		int[] tups = new int[] { 50000, 50000, 50000, 50000, 50000 };

		// Generate random data
		Comparable[][][] resultTest = test.generate(tups);

		// Student Tables
		for (int i = 0; i < resultTest[0].length; i++) {
			if (i < 1000) {
				Student_1000.insert(resultTest[0][i]);
			}
			if (i < 2000) {
				Student_2000.insert(resultTest[0][i]);
			}
			if (i < 5000) {
				Student_5000.insert(resultTest[0][i]);
			}
			if (i < 10000) {
				Student_10000.insert(resultTest[0][i]);
			}
			Student_50000.insert(resultTest[0][i]);
			Student_Data.add(resultTest[0][i]);
		}
		// Professor Tables
		for (int i = 0; i < resultTest[1].length; i++) {
			if (i < 1000) {
				Professor_1000.insert(resultTest[1][i]);
			}
			if (i < 2000) {
				Professor_2000.insert(resultTest[1][i]);
			}
			if (i < 5000) {
				Professor_5000.insert(resultTest[1][i]);
			}
			if (i < 10000) {
				Professor_10000.insert(resultTest[1][i]);
			}
			Professor_50000.insert(resultTest[1][i]);
			Professor_Data.add(resultTest[1][i]);
		}
		// Course Tables
		for (int i = 0; i < resultTest[2].length; i++) {
			if (i < 1000) {
				Course_1000.insert(resultTest[2][i]);
			}
			if (i < 2000) {
				Course_2000.insert(resultTest[2][i]);
			}
			if (i < 5000) {
				Course_5000.insert(resultTest[2][i]);
			}
			if (i < 10000) {
				Course_10000.insert(resultTest[2][i]);
			}
			Course_50000.insert(resultTest[2][i]);
			Course_Data.add(resultTest[2][i]);
		}
		// Teaching Tables
		for (int i = 0; i < resultTest[3].length; i++) {
			if (i < 1000) {
				Teaching_1000.insert(resultTest[3][i]);
			}
			if (i < 2000) {
				Teaching_2000.insert(resultTest[3][i]);
			}
			if (i < 5000) {
				Teaching_5000.insert(resultTest[3][i]);
			}
			if (i < 10000) {
				Teaching_10000.insert(resultTest[3][i]);
			}
			Teaching_50000.insert(resultTest[3][i]);
			Teaching_Data.add(resultTest[3][i]);
		}
		// Transcript Tables
		for (int i = 0; i < resultTest[4].length; i++) {
			if (i < 1000) {
				Transcript_1000.insert(resultTest[4][i]);
			}
			if (i < 2000) {
				Transcript_2000.insert(resultTest[4][i]);
			}
			if (i < 5000) {
				Transcript_5000.insert(resultTest[4][i]);
			}
			if (i < 10000) {
				Transcript_10000.insert(resultTest[4][i]);
			}
			Transcript_50000.insert(resultTest[4][i]);
			Transcript_Data.add(resultTest[4][i]);
		}
	}

	/**
	 * Checks Tables for correct allocation of Tuples (size)
	 */
	@Test
	public void sizeTest() {
		// Student Tables
		assertEquals("Student Table Size", 1000, Student_1000.size());
		assertEquals("Student Table Size", 2000, Student_2000.size());
		assertEquals("Student Table Size", 5000, Student_5000.size());
		assertEquals("Student Table Size", 10000, Student_10000.size());
		assertEquals("Student Table Size", 50000, Student_50000.size());

		// Professor Tables
		assertEquals("Professor Table Size", 1000, Professor_1000.size());
		assertEquals("Professor Table Size", 2000, Professor_2000.size());
		assertEquals("Professor Table Size", 5000, Professor_5000.size());
		assertEquals("Professor Table Size", 10000, Professor_10000.size());
		assertEquals("Professor Table Size", 50000, Professor_50000.size());

		// Course Tables
		assertEquals("Course Table Size", 1000, Course_1000.size());
		assertEquals("Course Table Size", 2000, Course_2000.size());
		assertEquals("Course Table Size", 5000, Course_5000.size());
		assertEquals("Course Table Size", 10000, Course_10000.size());
		assertEquals("Course Table Size", 50000, Course_50000.size());

		// Teaching Tables
		assertEquals("Teaching Table Size", 1000, Teaching_1000.size());
		assertEquals("Teaching Table Size", 2000, Teaching_2000.size());
		assertEquals("Teaching Table Size", 5000, Teaching_5000.size());
		assertEquals("Teaching Table Size", 10000, Teaching_10000.size());
		assertEquals("Teaching Table Size", 50000, Teaching_50000.size());

		// Transcript Tables
		assertEquals("Transcript Table Size", 1000, Transcript_1000.size());
		assertEquals("Transcript Table Size", 2000, Transcript_2000.size());
		assertEquals("Transcript Table Size", 5000, Transcript_5000.size());
		assertEquals("Transcript Table Size", 10000, Transcript_10000.size());
		assertEquals("Transcript Table Size", 50000, Transcript_50000.size());

		// Generated Random Data
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			Student_1000.select(new KeyType(instance[0]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_1000.select(t -> t[Student_1000.col("name")]
					.equals(instance[1])
					&& t[Student_1000.col("address")].equals(instance[2])
					&& t[Student_1000.col("status")].equals(instance[3]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_2000.select(new KeyType(instance[0]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_2000.select(t -> t[Student_2000.col("name")]
					.equals(instance[1])
					&& t[Student_2000.col("address")].equals(instance[2])
					&& t[Student_2000.col("status")].equals(instance[3]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_5000.select(new KeyType(instance[0]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_5000.select(t -> t[Student_5000.col("name")]
					.equals(instance[1])
					&& t[Student_5000.col("address")].equals(instance[2])
					&& t[Student_5000.col("status")].equals(instance[3]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_10000.select(new KeyType(instance[0]));

			// stop at 10000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_10000.select(t -> t[Student_10000.col("name")]
					.equals(instance[1])
					&& t[Student_10000.col("address")].equals(instance[2])
					&& t[Student_10000.col("status")].equals(instance[3]));

			// stop at 10000 iterations
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
		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_50000.select(new KeyType(instance[0]));
		}
	}

	/**
	 * 50000 Tuple Student Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Student_50000_SelectTest() {
		// iterate through all tuples
		for (Comparable[] instance : Student_Data) {
			// Select Test
			Student_50000.select(t -> t[Student_50000.col("name")]
					.equals(instance[1])
					&& t[Student_50000.col("address")].equals(instance[2])
					&& t[Student_50000.col("status")].equals(instance[3]));
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			Professor_1000.select(new KeyType(instance[0]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_1000.select(t -> t[Professor_1000.col("name")]
					.equals(instance[1])
					&& t[Professor_1000.col("deptId")].equals(instance[2]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_2000.select(new KeyType(instance[0]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_2000.select(t -> t[Professor_2000.col("name")]
					.equals(instance[1])
					&& t[Professor_2000.col("deptId")].equals(instance[2]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_5000.select(new KeyType(instance[0]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_5000.select(t -> t[Professor_5000.col("name")]
					.equals(instance[1])
					&& t[Professor_5000.col("deptId")].equals(instance[2]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_10000.select(new KeyType(instance[0]));

			// stop at 10000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_10000.select(t -> t[Professor_10000.col("name")]
					.equals(instance[1])
					&& t[Professor_10000.col("deptId")].equals(instance[2]));

			// stop at 10000 iterations
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
		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_50000.select(new KeyType(instance[0]));
		}
	}

	/**
	 * 50000 Tuple Professor Select - 2 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Professor_50000_SelectTest() {
		// iterate through all tuples
		for (Comparable[] instance : Professor_Data) {
			// Select Test
			Professor_50000.select(t -> t[Professor_50000.col("name")]
					.equals(instance[1])
					&& t[Professor_50000.col("deptId")].equals(instance[2]));
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			Course_1000.select(new KeyType(instance[0]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_1000.select(t -> t[Course_1000.col("deptId")]
					.equals(instance[1])
					&& t[Course_1000.col("crsName")].equals(instance[2])
					&& t[Course_1000.col("descr")].equals(instance[3]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_2000.select(new KeyType(instance[0]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_2000.select(t -> t[Course_2000.col("deptId")]
					.equals(instance[1])
					&& t[Course_2000.col("crsName")].equals(instance[2])
					&& t[Course_2000.col("descr")].equals(instance[3]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_5000.select(new KeyType(instance[0]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_5000.select(t -> t[Course_5000.col("deptId")]
					.equals(instance[1])
					&& t[Course_5000.col("crsName")].equals(instance[2])
					&& t[Course_5000.col("descr")].equals(instance[3]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_10000.select(new KeyType(instance[0]));

			// stop at 10000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_10000.select(t -> t[Course_10000.col("deptId")]
					.equals(instance[1])
					&& t[Course_10000.col("crsName")].equals(instance[2])
					&& t[Course_10000.col("descr")].equals(instance[3]));

			// stop at 10000 iterations
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
		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_50000.select(new KeyType(instance[0]));
		}
	}

	/**
	 * 50000 Tuple Course Select - 3 Attributes ANDed
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Course_50000_SelectTest() {
		// iterate through all tuples
		for (Comparable[] instance : Course_Data) {
			// Select Test
			Course_50000.select(t -> t[Course_50000.col("deptId")]
					.equals(instance[1])
					&& t[Course_50000.col("crsName")].equals(instance[2])
					&& t[Course_50000.col("descr")].equals(instance[3]));
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			Teaching_1000.select(new KeyType(instance[0], instance[1]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_1000.select(t -> t[Teaching_1000.col("profId")]
					.equals(instance[2]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_2000.select(new KeyType(instance[0], instance[1]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_2000.select(t -> t[Teaching_2000.col("profId")]
					.equals(instance[2]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_5000.select(new KeyType(instance[0], instance[1]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_5000.select(t -> t[Teaching_5000.col("profId")]
					.equals(instance[2]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_10000.select(new KeyType(instance[0], instance[1]));

			// stop at 10000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_10000.select(t -> t[Teaching_10000.col("profId")]
					.equals(instance[2]));

			// stop at 10000 iterations
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
		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_50000.select(new KeyType(instance[0], instance[1]));
		}
	}

	/**
	 * 50000 Tuple Teaching Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Teaching_50000_SelectTest() {
		// iterate through all tuples
		for (Comparable[] instance : Teaching_Data) {
			// Select Test
			Teaching_50000.select(t -> t[Teaching_50000.col("profId")]
					.equals(instance[2]));
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			Transcript_1000.select(new KeyType(instance[0], instance[1],
					instance[2]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_1000.select(t -> t[Transcript_1000.col("grade")]
					.equals(instance[3]));

			// stop at 1000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_2000.select(new KeyType(instance[0], instance[1],
					instance[2]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_2000.select(t -> t[Transcript_2000.col("grade")]
					.equals(instance[3]));

			// stop at 2000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_5000.select(new KeyType(instance[0], instance[1],
					instance[2]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_5000.select(t -> t[Transcript_5000.col("grade")]
					.equals(instance[3]));

			// stop at 5000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_10000.select(new KeyType(instance[0], instance[1],
					instance[2]));

			// stop at 10000 iterations
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

		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_10000.select(t -> t[Transcript_10000.col("grade")]
					.equals(instance[3]));

			// stop at 10000 iterations
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
		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_50000.select(new KeyType(instance[0], instance[1],
					instance[2]));
		}
	}

	/**
	 * 50000 Tuple Transcript Select - 1 Attribute
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void Transcript_50000_SelectTest() {
		// iterate through all tuples
		for (Comparable[] instance : Transcript_Data) {
			// Select Test
			Transcript_50000.select(t -> t[Transcript_50000.col("grade")]
					.equals(instance[3]));
		}
	}
	

	/**
	 * Prints out the Index Structure to file.
	 * 
	 * Naming convention:
	 * [data-structure-name]-[table-name]-[#-of-tuples].txt
	 */
	public static void indexStructuresToDisk(String DS_NAME) {
		try {
			//set path to project path
			path = Paths.get(System.getProperty("user.dir"));
			
			//resolve desired path to index structure files
			path = path.resolve(DIRECTORY);
			
			//create directory path
			Files.createDirectories(path);
			
			//save state of original output
			PrintStream stdout = System.out;
			
			
			// --------------------- Student
			
			//Student 1000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-student-1000.txt").toFile())));
			Student_1000.printIndex();
			
			//Student 2000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-student-2000.txt").toFile())));
			Student_2000.printIndex();
			
			//Student 5000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-student-5000.txt").toFile())));
			Student_5000.printIndex();
			
			//Student 10000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-student-10000.txt").toFile())));
			Student_10000.printIndex();
			
			//Student 50000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-student-50000.txt").toFile())));
			Student_50000.printIndex();
			
			
			// --------------------- Professor
			
			//Professor 1000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-professor-1000.txt").toFile())));
			Professor_1000.printIndex();
			
			//Professor 2000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-professor-2000.txt").toFile())));
			Professor_2000.printIndex();
			
			//Professor 5000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-professor-5000.txt").toFile())));
			Professor_5000.printIndex();
			
			//Professor 10000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-professor-10000.txt").toFile())));
			Professor_10000.printIndex();
			
			//Professor 50000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-professor-50000.txt").toFile())));
			Professor_50000.printIndex();
			
			
			// --------------------- Course
			
			//Course 1000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-course-1000.txt").toFile())));
			Course_1000.printIndex();
			
			//Course 2000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-course-2000.txt").toFile())));
			Course_2000.printIndex();
			
			//Course 5000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-course-5000.txt").toFile())));
			Course_5000.printIndex();
			
			//Course 10000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-course-10000.txt").toFile())));
			Course_10000.printIndex();
			
			//Course 50000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-course-50000.txt").toFile())));
			Course_50000.printIndex();
			
			
			// --------------------- Teaching
			
			//Teaching 1000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-teaching-1000.txt").toFile())));
			Teaching_1000.printIndex();
			
			//Teaching 2000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-teaching-2000.txt").toFile())));
			Teaching_2000.printIndex();
			
			//Teaching 5000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-teaching-5000.txt").toFile())));
			Teaching_5000.printIndex();
			
			//Teaching 10000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-teaching-10000.txt").toFile())));
			Teaching_10000.printIndex();
			
			//Teaching 50000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-teaching-50000.txt").toFile())));
			Teaching_50000.printIndex();
			
			
			// --------------------- Transcript
			
			//Transcript 1000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-transcript-1000.txt").toFile())));
			Transcript_1000.printIndex();
			
			//Transcript 2000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-transcript-2000.txt").toFile())));
			Transcript_2000.printIndex();
			
			//Transcript 5000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-transcript-5000.txt").toFile())));
			Transcript_5000.printIndex();
			
			//Transcript 10000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-transcript-10000.txt").toFile())));
			Transcript_10000.printIndex();
			
			//Transcript 50000
			System.setOut(new PrintStream(new FileOutputStream(path.resolve(DS_NAME + "-transcript-50000.txt").toFile())));
			Transcript_50000.printIndex();
			
			
			//reset to standard output
			System.setOut(stdout);
		} catch (Exception e) {
			System.out.println("System encountered an error writing index structures to disk.");
		}
	}
}
