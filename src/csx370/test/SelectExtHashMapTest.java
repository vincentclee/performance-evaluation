package csx370.test;

import java.util.ArrayList;

import org.junit.BeforeClass;

import csx370.operator.Table;

/**
 * Tests for sequential select vs. indexed select using the Extendable Hash Map
 */
public class SelectExtHashMapTest extends SelectTest {

	/** Data Structure prefix name */
	private static final String DS_NAME = "extendable-hash-map";
	
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
		Student_1000 = new Table("Student", "id name address status", "Integer String String String", "id", "ExtHashMap");
		Student_2000 = new Table("Student", "id name address status", "Integer String String String", "id", "ExtHashMap");
		Student_5000 = new Table("Student", "id name address status", "Integer String String String", "id", "ExtHashMap");
		Student_10000 = new Table("Student", "id name address status", "Integer String String String", "id", "ExtHashMap");
		Student_50000 = new Table("Student", "id name address status", "Integer String String String", "id", "ExtHashMap");
		
		Professor_1000 = new Table("Professor", "id name deptId", "Integer String String", "id", "ExtHashMap");
		Professor_2000 = new Table("Professor", "id name deptId", "Integer String String", "id", "ExtHashMap");
		Professor_5000 = new Table("Professor", "id name deptId", "Integer String String", "id", "ExtHashMap");
		Professor_10000 = new Table("Professor", "id name deptId", "Integer String String", "id", "ExtHashMap");
		Professor_50000 = new Table("Professor", "id name deptId", "Integer String String", "id", "ExtHashMap");
		
		Course_1000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "ExtHashMap");
		Course_2000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "ExtHashMap");
		Course_5000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "ExtHashMap");
		Course_10000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "ExtHashMap");
		Course_50000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "ExtHashMap");
		
		Teaching_1000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "ExtHashMap");
		Teaching_2000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "ExtHashMap");
		Teaching_5000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "ExtHashMap");
		Teaching_10000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "ExtHashMap");
		Teaching_50000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "ExtHashMap");
		
		Transcript_1000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "ExtHashMap");
		Transcript_2000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "ExtHashMap");
		Transcript_5000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "ExtHashMap");
		Transcript_10000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "ExtHashMap");
		Transcript_50000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "ExtHashMap");
		
		generateData();
		
		//index structure to file
		if (INDEX_STRUCTURES_OUTPUT) {
			indexStructuresToDisk(DS_NAME);
		}
	}
}
