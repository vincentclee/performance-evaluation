package csx370.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import csx370.impl.TupleGenerator;
import csx370.impl.TupleGeneratorImpl;
import csx370.operator.Table;

/**
 * @author vincentlee
 *
 */
public class SequentialSelectTest {
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
	
	/**
	 * Set up Tables and Data
	 */
	@SuppressWarnings("rawtypes")
	@BeforeClass
	public static void setUpBeforeClass() {
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
		
		
		
		
		
		
		TupleGenerator test = new TupleGeneratorImpl();
		
		test.addRelSchema("Student", "id name address status", "Integer String String String", "id", null);
		test.addRelSchema("Professor", "id name deptId", "Integer String String", "id", null);
		test.addRelSchema("Course", "crsCode deptId crsName descr", "String String String String", "crsCode", null);
		test.addRelSchema("Teaching", "crsCode semester profId", "String String Integer", "crsCode semester", null);
		test.addRelSchema("Transcript", "studId crsCode semester grade", "Integer String String String", "studId crsCode semester", null);
		
		int[] tups = new int[]{50000, 50000, 50000, 50000, 50000};
		
		Comparable[][][] resultTest = test.generate(tups);
		
		//Student Tables
		for (int i = 0; i < resultTest[0].length; i++) {
			if (i < 1000) {Student_1000.insert(resultTest[0][i]);}
			if (i < 2000) {Student_2000.insert(resultTest[0][i]);}
			if (i < 5000) {Student_5000.insert(resultTest[0][i]);}
			if (i < 10000) {Student_10000.insert(resultTest[0][i]);}
			Student_50000.insert(resultTest[0][i]);
		}
		//Professor Tables
		for (int i = 0; i < resultTest[1].length; i++) {
			if (i < 1000) {Professor_1000.insert(resultTest[1][i]);}
			if (i < 2000) {Professor_2000.insert(resultTest[1][i]);}
			if (i < 5000) {Professor_5000.insert(resultTest[1][i]);}
			if (i < 10000) {Professor_10000.insert(resultTest[1][i]);}
			Professor_50000.insert(resultTest[1][i]);
		}
		//Course Tables
		for (int i = 0; i < resultTest[2].length; i++) {
			if (i < 1000) {Course_1000.insert(resultTest[2][i]);}
			if (i < 2000) {Course_2000.insert(resultTest[2][i]);}
			if (i < 5000) {Course_5000.insert(resultTest[2][i]);}
			if (i < 10000) {Course_10000.insert(resultTest[2][i]);}
			Course_50000.insert(resultTest[2][i]);
		}
		//Teaching Tables
		for (int i = 0; i < resultTest[3].length; i++) {
			if (i < 1000) {Teaching_1000.insert(resultTest[3][i]);}
			if (i < 2000) {Teaching_2000.insert(resultTest[3][i]);}
			if (i < 5000) {Teaching_5000.insert(resultTest[3][i]);}
			if (i < 10000) {Teaching_10000.insert(resultTest[3][i]);}
			Teaching_50000.insert(resultTest[3][i]);
		}
		//Transcript Tables
		for (int i = 0; i < resultTest[4].length; i++) {
			if (i < 1000) {Transcript_1000.insert(resultTest[4][i]);}
			if (i < 2000) {Transcript_2000.insert(resultTest[4][i]);}
			if (i < 5000) {Transcript_5000.insert(resultTest[4][i]);}
			if (i < 10000) {Transcript_10000.insert(resultTest[4][i]);}
			Transcript_50000.insert(resultTest[4][i]);
		}
	}
	
//	@Test
	public void test() {
		Student_1000.print();
		Student_2000.print();
		
		Professor_1000.print();
		Professor_2000.print();
		
		Course_1000.print();
		Course_2000.print();
		
		Transcript_1000.print();
		Transcript_2000.print();
		
		Teaching_1000.print();
		Teaching_2000.print();
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
	}
}
