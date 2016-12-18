package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.ArrayStack;

public class ArrayStackTest {
	public static void main(String[] args) {
		ArrayStack<String> store = new ArrayStack<>();

		for (int i = 1; i < 13; i++) {
			store.push(String.valueOf(i));
			System.out.println(store);
		}

		store.pop();
		System.out.println(store);

		store.pop();
		System.out.println(store);

		store.push("44");
		System.out.println(store);
	}
}
