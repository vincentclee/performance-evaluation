package csx370.test;

import java.util.ArrayList;

import org.junit.BeforeClass;

import csx370.operator.Table;

/**
 * Tests for sequential select vs. indexed select using the B+ Tree Map
 */
public class SelectBpTreeMapTest extends SelectTest {

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
		Student_1000 = new Table("Student", "id name address status", "Integer String String String", "id", "BpTreeMap");
		Student_2000 = new Table("Student", "id name address status", "Integer String String String", "id", "BpTreeMap");
		Student_5000 = new Table("Student", "id name address status", "Integer String String String", "id", "BpTreeMap");
		Student_10000 = new Table("Student", "id name address status", "Integer String String String", "id", "BpTreeMap");
		Student_50000 = new Table("Student", "id name address status", "Integer String String String", "id", "BpTreeMap");
		
		Professor_1000 = new Table("Professor", "id name deptId", "Integer String String", "id", "BpTreeMap");
		Professor_2000 = new Table("Professor", "id name deptId", "Integer String String", "id", "BpTreeMap");
		Professor_5000 = new Table("Professor", "id name deptId", "Integer String String", "id", "BpTreeMap");
		Professor_10000 = new Table("Professor", "id name deptId", "Integer String String", "id", "BpTreeMap");
		Professor_50000 = new Table("Professor", "id name deptId", "Integer String String", "id", "BpTreeMap");
		
		Course_1000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "BpTreeMap");
		Course_2000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "BpTreeMap");
		Course_5000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "BpTreeMap");
		Course_10000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "BpTreeMap");
		Course_50000 = new Table("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", "BpTreeMap");
		
		Teaching_1000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "BpTreeMap");
		Teaching_2000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "BpTreeMap");
		Teaching_5000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "BpTreeMap");
		Teaching_10000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "BpTreeMap");
		Teaching_50000 = new Table("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", "BpTreeMap");
		
		Transcript_1000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "BpTreeMap");
		Transcript_2000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "BpTreeMap");
		Transcript_5000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "BpTreeMap");
		Transcript_10000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "BpTreeMap");
		Transcript_50000 = new Table("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", "BpTreeMap");
		
		generateData();
		
		//index structure to file
		if (INDEX_STRUCTURES_OUTPUT) {
			indexStructuresToDisk(DS_NAME);
		}
	}
}
