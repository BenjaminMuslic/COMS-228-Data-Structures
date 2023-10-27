package stack_array_implementation;

//Abstract data type
//LIFO - Last in, first out
//push - adds an item as the top item on the stack
//pop - removes the top item on the stack
//peel - gets the top item on the stack without popping it
//Ideal backing data structure: linked list

//O(1) for push, pop, and peek, when using a linked list
//If you use an array, then push is O(n), because the array may have to be resized
//If you know the maximum number of items that will ever be on the stack, an array might be a good choice
//If memory is tight, an array might be a good choice
//Linked list is ideal

public class Main {

	public static void main(String[] args) {

		ArrayStack stack = new ArrayStack(10);

		stack.push(new Employee("Jane", "Jones", 123));
		stack.push(new Employee("John", "Doe", 4567));
		stack.push(new Employee("Mary", "Smith", 22));
		stack.push(new Employee("Mike", "Wilson", 3245));
		stack.push(new Employee("Bill", "End", 78));

		stack.printStack(); // print all

		System.out.println(stack.peek()); // prints just bill

		System.out.println("Popped: " + stack.pop());

	}

}
