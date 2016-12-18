package pl.com.bottega.photostock.sales.model;

public interface Stack<E> {
	E pop();
	void push(E element);
	boolean isEmpty();
}
