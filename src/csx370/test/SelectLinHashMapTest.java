package csx370.test;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import csx370.operator.Table;

/**
 * Tests for sequential select vs. indexed select using the B+ Tree Map
 */
public class SelectLinHashMapTest extends SelectTest {

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
		Student_1000 = new Table("Student", "id name address status", "Integer String String String", "id", "LinHashMap");
		Student_2000 = new Table("Student", "id name address status", "Integer String String String", "id", "LinHashMap");
		Student_5000 = new Table("Student", "id name address status", "Integer String String String", "id", "LinHashMap");
		Student_10000 = new Table("Student", "id name address status", "Integer String String String", "id", "LinHashMap");
		Student_50000 = new Table("Student", "id name address status", "Integer String String String", "id", "LinHashMap");
		
		Professor_1000 = new Table("Professor", "id name deptId", "Integer String String", "id", "LinHashMap");
		Professor_2000 = new Table("Professor", "id name deptId", "Integer String String", "id", "LinHashMap");
		Professor_5000 = new Table("Professor", "id name deptId", "Integer String String", "id", "LinHashMap");
		Professor_10000 = new Table("Professor", "id name deptId", "Integer String String", "id", "LinHashMap");
		Professor_50000 = new Table("Professor", "id name deptId", "Integer String String", "id", "LinHashMap");
		
		Course_1000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "LinHashMap");
		Course_2000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "LinHashMap");
		Course_5000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "LinHashMap");
		Course_10000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "LinHashMap");
		Course_50000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "LinHashMap");
		
		Teaching_1000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "LinHashMap");
		Teaching_2000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "LinHashMap");
		Teaching_5000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "LinHashMap");
		Teaching_10000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "LinHashMap");
		Teaching_50000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "LinHashMap");
		
		Transcript_1000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "LinHashMap");
		Transcript_2000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "LinHashMap");
		Transcript_5000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "LinHashMap");
		Transcript_10000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "LinHashMap");
		Transcript_50000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "LinHashMap");
		
		generateData();
		
		//index structure to file
		if (INDEX_STRUCTURES_OUTPUT) {
			indexStructuresToDisk(DS_NAME);
		}
	}
	
	/**
	 * Ignores the size test for Linear Hash Map
	 */
	@Ignore @Test
	public void sizeTest() {
		
	}
}
