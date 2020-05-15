package by.education.arraylist;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<T> extends AbstractList<T> implements List<T> {

	private int size;
	private Object[] elements;
	private static final int DEFAULT_CAPACITY = 16;

	public ArrayList() {
		elements = new Object[DEFAULT_CAPACITY];
	}

	public ArrayList(int initCapacity) {
		if (initCapacity < 0) {
			throw new IllegalArgumentException("Invalid capacity " + initCapacity);
		}
		elements = new Object[initCapacity];
	}

	@Override
	public boolean add(T element) {
		if (needToGrow()) {
			grow();
		}
		elements[size++] = element;
		return true;
	}

	@Override
	public void add(int index, T element) {
		Objects.checkIndex(index, size);
		if (needToGrow()) {
			grow();
		}
		System.arraycopy(elements, index, elements, index + 1, size - index);
		elements[index] = element;
		size++;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean addAll(Collection<? extends T> collection) {
		Object[] objects = collection.toArray();
		for (int i = 0; i < objects.length; i++) {
			add((T) objects[i]);
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean addAll(int startIndex, Collection<? extends T> collection) {
		Objects.checkIndex(startIndex, size);
		Object[] objects = collection.toArray();
		for (int i = 0; i < collection.size(); i++) {
			add(startIndex++, (T) objects[i]);
		}
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	@Override
	public boolean contains(Object element) {
		return indexOf(element) > -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object object : collection) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(int index) {
		Objects.checkIndex(index, size);
		return (T) elements[index];
	}

	@Override
	public int indexOf(Object element) {
		for (int i = 0; i < size; i++) {
			if (element == null) {
				if (elements[i] == null) {
					return i;
				}
			} else {
				if (element.equals(elements[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<T> {
		int currentIndex = -1;

		private IteratorImpl() {
		}

		@Override
		public boolean hasNext() {
			return currentIndex + 1 < size;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (T) elements[++currentIndex];
		}

		@Override
		public void remove() {
			if (currentIndex < 0) {
				throw new IllegalStateException();
			}
			ArrayList.this.remove(currentIndex--);
		}

	}

	private class ListIteratorImpl extends IteratorImpl implements ListIterator<T> {

		public ListIteratorImpl() {
		}

		public ListIteratorImpl(int index) {
			currentIndex = index - 1;
		}

		@Override
		public boolean hasPrevious() {
			return currentIndex - 1 > -1;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T previous() {
			if (currentIndex < 0 || currentIndex >= size) {
				throw new NoSuchElementException();
			}
			return (T) elements[currentIndex--];
		}

		@Override
		public int nextIndex() {
			return currentIndex + 1;
		}

		@Override
		public int previousIndex() {
			return currentIndex;
		}

		@Override
		public void set(T e) {
			if (currentIndex < 0) {
				throw new IllegalStateException();
			}
			ArrayList.this.set(currentIndex, e);
		}

		@Override
		public void add(T e) {
			ArrayList.this.add(++currentIndex, e);
		}

	}

	@Override
	public int lastIndexOf(Object element) {
		for (int i = size - 1; i >= 0; i--) {
			if (element == null) {
				if (elements[i] == null) {
					return i;
				}
			} else {
				if (element.equals(elements[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new ListIteratorImpl();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		Objects.checkIndex(index, size);
		return new ListIteratorImpl(index);
	}

	@Override
	public boolean remove(Object element) {
		int index = indexOf(element);
		if (index != -1) {
			remove(index);
			return true;
		}
		return false;
	}

	@Override
	public T remove(int index) {
		Objects.checkIndex(index, size);
		@SuppressWarnings("unchecked")
		T oldValue = (T) elements[index];
		System.arraycopy(elements, index + 1, elements, index, --size - index);
		elements[size] = null;
		return oldValue;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return removeAll(collection, false);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return removeAll(collection, true);
	}

	@Override
	public T set(int index, T element) {
		Objects.checkIndex(index, size);
		@SuppressWarnings({ "unchecked" })
		T oldElement = (T) elements[index];
		elements[index] = element;
		return oldElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		System.arraycopy(elements, 0, result, 0, size);
		return result;
	}

	@Override
	public <E> E[] toArray(E[] a) {
		@SuppressWarnings("unchecked")
		E[] result = a.length >= size ? a : (E[]) Array.newInstance(a.getClass().getComponentType(), size);
		System.arraycopy(elements, 0, result, 0, size);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof List)) {
			return false;
		}
		return (o.getClass() == ArrayList.class) ? equalsArrayList((ArrayList<?>) o) : equalsRange((List<?>) o);
	}

	private boolean equalsRange(List<?> other) {
		var otherIterator = other.iterator();
		for (int i = 0; i < size; i++) {
			if (!otherIterator.hasNext() || !Objects.equals(elements[i], otherIterator.next())) {
				return false;
			}
		}
		return !otherIterator.hasNext();
	}

	private boolean equalsArrayList(ArrayList<?> other) {
		if (size != other.size) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (!Objects.equals(elements[i], other.elements[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		for (int i = 0; i < size; i++) {
			Object e = elements[i];
			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		StringBuilder result = new StringBuilder();
		result.append('[');
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			T currentElement = iterator.next();
			if (iterator.hasNext()) {
				result.append(currentElement).append(", ");
			} else {
				result.append(currentElement).append("]");
			}
		}
		return result.toString();
	}

	private void grow() {
		int newLength = (int) (elements.length < DEFAULT_CAPACITY ? DEFAULT_CAPACITY * 1.75 : elements.length * 1.75);
		elements = Arrays.copyOf(elements, newLength);
	}

	private boolean needToGrow() {
		return elements.length == size;
	}

	private boolean removeAll(Collection<?> collection, boolean retainAll) {
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			if (retainAll) {
				if (!collection.contains(iterator.next())) {
					iterator.remove();
				}
			} else {
				if (collection.contains(iterator.next())) {
					iterator.remove();
				}
			}
		}
		return true;
	}
}
