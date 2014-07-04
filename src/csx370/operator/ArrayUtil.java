package csx370.operator;

/****************************************************************************************
 * @file   ArrayUtil.java
 * @author John Miller
 */

import java.util.Arrays;

import static java.lang.System.arraycopy;

/*****************************************************************************************
 * The ArrayUtil class provides functions related to arrays. A concat method 
 * concatenate two arrays of type T to form a new wider array.
 */
class ArrayUtil {
	/************************************************************************************
	 * Concatenate two arrays of type T to form a new wider array.
	 * http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
	 *
	 * @param arr1 the first array
	 * @param arr2 the second array
	 * @param <T>  type parameter
	 * @return     a wider array containing all the values from arr1 and arr2
	 */
	public static <T> T[] concat(T[] arr1, T[] arr2) {
		T[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
		arraycopy(arr2, 0, result, arr1.length, arr2.length);
		return result;
	} // concat
} // ArrayUtil class