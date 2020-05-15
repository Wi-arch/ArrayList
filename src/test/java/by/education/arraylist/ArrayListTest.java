package by.education.arraylist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class ArrayListTest {

	private List<String> testArrayList;
	private List<String> javaUtilArrayList;
	private static final String VALID_STRING = "Test";
	private static final Collection<String> VALID_COLLECTION = Stream.of(VALID_STRING).collect(Collectors.toSet());

	@Before
	public void init() {
		testArrayList = new ArrayList<>();
		javaUtilArrayList = new java.util.ArrayList<>();
		testArrayList.add(VALID_STRING);
		testArrayList.add(VALID_STRING + VALID_STRING);
		testArrayList.add(VALID_STRING);
		javaUtilArrayList.add(VALID_STRING);
		javaUtilArrayList.add(VALID_STRING + VALID_STRING);
		javaUtilArrayList.add(VALID_STRING);
	}

	@Test
	public void testConstructor() {
		testArrayList = new ArrayList<>();
		assertNotNull(testArrayList);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithParameter() {
		testArrayList = new ArrayList<>(-5);
	}

	@Test
	public void testAddMethod() {
		testArrayList.add(VALID_STRING);
		javaUtilArrayList.add(VALID_STRING);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testAddMethodWithIndex() {
		testArrayList.add(1, VALID_STRING);
		javaUtilArrayList.add(1, VALID_STRING);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testAddAll() {
		testArrayList.addAll(javaUtilArrayList);
		javaUtilArrayList.addAll(javaUtilArrayList);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testClear() {
		testArrayList.clear();
		javaUtilArrayList.clear();
		assertEquals(testArrayList, javaUtilArrayList);
		assertEquals(testArrayList.size(), javaUtilArrayList.size());
	}

	@Test
	public void testContains() {
		boolean expected = testArrayList.contains(VALID_STRING);
		boolean actual = javaUtilArrayList.contains(VALID_STRING);
		assertEquals(expected, actual);
	}

	@Test
	public void testContainsAll() {
		boolean expected = testArrayList.containsAll(javaUtilArrayList);
		boolean actual = testArrayList.containsAll(javaUtilArrayList);
		assertEquals(expected, actual);
	}

	@Test
	public void testEquals() {
		assertTrue(testArrayList.equals(testArrayList));
		assertTrue(testArrayList.equals(javaUtilArrayList));
		assertTrue(javaUtilArrayList.equals(testArrayList));
	}

	@Test
	public void testGetMethod() {
		String expected = testArrayList.get(2);
		String actual = javaUtilArrayList.get(2);
		assertEquals(expected, actual);
	}

	@Test
	public void testHashCodeMethod() {
		assertEquals(testArrayList.hashCode(), testArrayList.hashCode());
	}

	@Test
	public void testMethodIndexOf() {
		int expected = testArrayList.indexOf(VALID_STRING);
		int actual = javaUtilArrayList.indexOf(VALID_STRING);
		assertEquals(expected, actual);
	}

	@Test
	public void testIsEmptyMethod() {
		assertFalse(testArrayList.isEmpty());
		assertTrue(new ArrayList<String>().isEmpty());
	}

	@Test
	public void testMethodLastIndexOf() {
		int expected = testArrayList.lastIndexOf(VALID_STRING);
		int actual = javaUtilArrayList.lastIndexOf(VALID_STRING);
		assertEquals(expected, actual);
	}

	@Test
	public void testRemoveByIndexMethod() {
		String expected = testArrayList.remove(2);
		String actual = javaUtilArrayList.remove(2);
		assertEquals(javaUtilArrayList, testArrayList);
		assertEquals(expected, actual);
	}

	@Test
	public void testRemoveByObject() {
		boolean expected = testArrayList.remove(VALID_STRING);
		boolean actual = javaUtilArrayList.remove(VALID_STRING);
		assertEquals(javaUtilArrayList, testArrayList);
		assertEquals(expected, actual);
	}

	@Test
	public void testRemoveAllMethod() {
		testArrayList.removeAll(VALID_COLLECTION);
		javaUtilArrayList.removeAll(VALID_COLLECTION);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testRetainAllMethod() {
		testArrayList.retainAll(VALID_COLLECTION);
		javaUtilArrayList.retainAll(VALID_COLLECTION);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testSetMethod() {
		String newElement = "Test new element";
		testArrayList.set(2, newElement);
		javaUtilArrayList.set(2, newElement);
		assertEquals(javaUtilArrayList, testArrayList);
	}

	@Test
	public void testSizeMethod() {
		assertEquals(javaUtilArrayList.size(), testArrayList.size());
	}

	@Test
	public void testSubListMethod() {
		assertEquals(javaUtilArrayList.subList(1, 2), testArrayList.subList(1, 2));
	}

	@Test
	public void testToArrayMethod() {
		boolean result = Arrays.equals(javaUtilArrayList.toArray(), testArrayList.toArray());
		assertTrue(result);
	}

	@Test
	public void testToArrayWithParameterMethod() {
		String[] actual = new String[testArrayList.size()];
		String[] expected = new String[javaUtilArrayList.size()];
		boolean result = Arrays.equals(javaUtilArrayList.toArray(actual), testArrayList.toArray(expected));
		assertTrue(result);
		assertTrue(Arrays.equals(actual, expected));
	}

	@Test
	public void testToStringMethod() {
		String actual = testArrayList.toString();
		String expected = javaUtilArrayList.toString();
		assertEquals(expected, actual);
	}
}
