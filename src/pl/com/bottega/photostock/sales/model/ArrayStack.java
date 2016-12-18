package pl.com.bottega.photostock.sales.model;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {
	private E[] elements;
	private int index = 0;
	private static final int INITIAL_CAPACITY = 8;

	public ArrayStack() {
		elements = (E[]) new Object[INITIAL_CAPACITY];
	}

	@Override
	public void push(E element) {
		increaseCapacity();
		elements[index++] = element;
	}

	@Override
	public E pop() {
		if (isEmpty())
			throw new EmptyStackException();
		else {
			E element = elements[--index];
			elements[index] = null;
			return element;
		}
	}

	@Override
	public boolean isEmpty() {
		return index == 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < index - 1)
			sb.append(elements[i++]).append(" ");
		sb.append(elements[i]);
		return sb.toString();
	}

	private void increaseCapacity() {
		if (index == elements.length)
			elements = Arrays.copyOf(elements, 2 * index);
	}
}
