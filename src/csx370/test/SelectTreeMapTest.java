package csx370.test;

import java.util.ArrayList;

import org.junit.BeforeClass;

import csx370.operator.Table;

/**
 * Tests for sequential select vs. indexed select using the B+ Tree Map
 */
public class SelectTreeMapTest extends SelectTest {

	/** Data Structure prefix name */
	private static final String DS_NAME = "b+tree-map";
	
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
		Student_1000 = new Table("Student", "id name address status", "Integer String String String", "id", "TreeMap");
		Student_2000 = new Table("Student", "id name address status", "Integer String String String", "id", "TreeMap");
		Student_5000 = new Table("Student", "id name address status", "Integer String String String", "id", "TreeMap");
		Student_10000 = new Table("Student", "id name address status", "Integer String String String", "id", "TreeMap");
		Student_50000 = new Table("Student", "id name address status", "Integer String String String", "id", "TreeMap");
		
		Professor_1000 = new Table("Professor", "id name deptId", "Integer String String", "id", "TreeMap");
		Professor_2000 = new Table("Professor", "id name deptId", "Integer String String", "id", "TreeMap");
		Professor_5000 = new Table("Professor", "id name deptId", "Integer String String", "id", "TreeMap");
		Professor_10000 = new Table("Professor", "id name deptId", "Integer String String", "id", "TreeMap");
		Professor_50000 = new Table("Professor", "id name deptId", "Integer String String", "id", "TreeMap");
		
		Course_1000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "TreeMap");
		Course_2000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "TreeMap");
		Course_5000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "TreeMap");
		Course_10000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "TreeMap");
		Course_50000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "TreeMap");
		
		Teaching_1000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "TreeMap");
		Teaching_2000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "TreeMap");
		Teaching_5000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "TreeMap");
		Teaching_10000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "TreeMap");
		Teaching_50000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "TreeMap");
		
		Transcript_1000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "TreeMap");
		Transcript_2000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "TreeMap");
		Transcript_5000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "TreeMap");
		Transcript_10000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "TreeMap");
		Transcript_50000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "TreeMap");
		
		generateData();
		
		//index structure to file
		if (INDEX_STRUCTURES_OUTPUT) {
			indexStructuresToDisk(DS_NAME);
		}
	}
}
