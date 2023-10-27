package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Implementation of the list interface based on linked nodes that store
 *         multiple items per node. Rules for adding and removing elements
 *         ensure that each node (except possibly the last one) is at least half
 *         full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int node_size;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException("Invalid node size in public StoutList");

		this.node_size = nodeSize;
		tail = new Node();
		head = new Node();
		head.next = tail;
		tail.previous = head;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.node_size = nodeSize;
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(E item) {

		if (item == null) { // Check if the item to be added is null.
			throw new NullPointerException("item is null");
		}

		if (size != 0) { // Check if the StoutList is not empty.
			if (tail.previous.count < node_size) { // Check if the previous node of the tail node has space to
													// accommodate the item.
				tail.previous.addItem(item); // If so, add the item to the previous node of the tail node.
			} else {
				Node node = new Node(); // If not, create a new node.
				node.addItem(item); // Add the item to the new node.
				link(tail.previous, node); // Link the new node to the previous node of the tail node.
			}
		} else {
			Node node = new Node(); // If the StoutList is empty, create a new node.
			node.addItem(item); // Add the item to the new node.
			link(head, node); // Link the new node to the head of the StoutList.
		}

		size++; // Increase the size of the StoutList.
		return true; // Return true to indicate that the item has been successfully added.
	}

	@Override
	public void add(int pos, E item) {
		if (item == null) {
			throw new NullPointerException("item is null");
		}

		if (pos < 0 || pos > size) { // Check if the position is out of bounds.
			throw new IndexOutOfBoundsException("Out of bounds");
		}

		if (head.next == tail || pos == size) { // Check if the StoutList is empty or the position is at the end of the
												// StoutList.
			add(item); // If so add the item to the end of the StoutList using the add()
						// method.
			return;
		}

		nodeInformation node_info = find(pos); // Find the node and offset corresponding to the position.
		Node temp = node_info.node; // Get the node from the nodeInformation object.
		int offset = node_info.offset; // Get the offset from the nodeInformation object.

		if (offset == 0) { // Check if the offset is at the beginning of the node.

			if (temp.previous.count < node_size && temp.previous != head) { // Check if the previous node has space to
																			// accommodate the item.
				temp.previous.addItem(item); // If so, add the item to the previous node.
				size++; // Increase the size of the StoutList.
				return;
			}

			else if (temp == tail) { // Check if the current node is the last node.
				add(item); // If so, add the item to the end of the StoutList using the add()
							// method.
				return;
			}
		}

		if (temp.count < node_size) { // Check if the current node has space to accommodate the item.
			temp.addItem(offset, item); // If so, add the item to the current node at the specified offset.
			size++; // Increase the size of the StoutList.
			return;
		}

		Node new_node = new Node(); // Create a new node to split the current node and accommodate the item.
		int half = node_size / 2; // Calculate the half of the maximum capacity of a node.
		int count = 0;
		while (count < half) { // Move half of the elements from the current node to the new node.
			new_node.addItem(temp.data[half]);
			temp.removeItem(half);
			count++;
		}

		link(temp, new_node); // Link the new node to the StoutList.

		if (offset <= node_size / 2) { // Check if the offset is within the first half of the node.
			temp.addItem(offset, item); // If so, add the item to the current node at the specified offset.
		} else {
			new_node.addItem((offset - node_size / 2), item); // Otherwise, add the item to the new node at the adjusted
																// offset.
		}
		size++; // Increase the size of the StoutList.
	}

	@Override
	public E remove(int pos) {
		if (pos < 0 || pos >= size) { // check if position is out of bounds
			throw new IndexOutOfBoundsException("out of bounds");
		}
		nodeInformation info = find(pos); // Find the node and offset corresponding to the position.
		Node node = info.node; // Get the node from the nodeInformation object.
		int offset = info.offset; // Get the offset from the nodeInformation object.
		E removed = node.data[offset]; // Get the element to be removed from the node's data.

		if (node.next == tail && node.count == 1) { // Check if the node is the last node and contains only one element.
			unlink(node); // If so, unlink the node from the StoutList.
		}

		else if (node.next == tail || node.count > node_size / 2) { // If the node is not the last node, and has more
																	// elements than half the maximum capacity of a
																	// node.
			node.removeItem(offset); // Remove the item from the node's data.
		}

		else {
			Node next_node = node.next; // Get the next node after the current node.
			node.removeItem(offset); // Remove the item from the current node's data array.
			if (next_node.count > node_size / 2) { // If the next node has more elements than half the maximum capacity
													// of a node.
				node.addItem(next_node.data[0]); // Move the first element of the next node to the current node.
				next_node.removeItem(0); // Remove the first element from the next node.
			} else if (next_node.count <= node_size / 2) { // If the next node has equal to or less elements than half
															// the maximum capacity of a node.
				for (int i = 0; i < next_node.count; i++) { // Move all elements from the next node to the current node.
					node.addItem(next_node.data[i]);
				}
				unlink(next_node); // Unlink the next node from the StoutList.
			}
		}

		size--; // Decrease the size of the StoutList.
		return removed; // Return the removed element.
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] array = getArray(this);

		Comparator<E> compare = new Comparator<E>() { // Create a Comparator object to define the comparison logic for
														// sorting.

			@Override
			public int compare(E left, E right) { // Override the compare method to specify how to compare two elements
													// of type E.

				return left.compareTo(right); // Use the compareTo method of type E to compare the left and right
												// elements.
			}
		};

		insertionSort(array, compare); // Sort the array using the insertion sort algorithm

		for (int i = 0; i < array.length; i++) { // Iterate through the sorted array.
			if (array[i] == null) { // Check if any element in the array is null.
				throw new NullPointerException("The element E is null");
			}
			add(array[i]); // Add each element from the sorted array back to the StoutList.
		}

	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		E[] arr = getArray(this);

		bubbleSort(arr); // Sort the array in reverse order using bubble sort.

		for (int i = 0; i < arr.length; i++) {
			add(arr[i]); // Add the sorted elements back to the StoutList in reverse order.
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Inserts newNode into the list after current without updating size.
	 * Precondition: current != null, newNode != null
	 */
	private void link(Node current, Node new_node) {
		new_node.previous = current;
		new_node.next = current.next;
		current.next.previous = new_node;
		current.next = new_node;
	}

	/**
	 * Removes current from the list without updating size.
	 */
	private void unlink(Node current) {
		current.previous.next = current.next;
		current.next.previous = current.previous;
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < node_size; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[node_size];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= node_size) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= node_size) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
			// useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < node_size; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	/**
	 * Specific point in list node and offset
	 */
	private class nodeInformation {
		public Node node;
		public int offset;

		public nodeInformation(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * To locate item
	 * 
	 * @param position
	 * @return NodeInformation
	 */
	private nodeInformation find(int position) {

		// check if out of bounds
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Out of bounds");
		}

		Node temp = head.next; // temporary node = first node after the head node.
		int num_elements = 0; // number of elements visited

		// loop through until tail is reached
		while (temp != tail) {

			// Check if the position is within the range of elements in the current node.
			if (position < num_elements + temp.count) {
				// create nodeInformation with current node and the relative position within
				// that node
				nodeInformation node_info = new nodeInformation(temp, position - num_elements);
				return node_info; // return that node
			}
			num_elements += temp.count; // Update the counter with the number of elements in the current node.
			temp = temp.next; // move to the next
		}

		// If the position is not found in any of the nodes, return object with the
		// previous node of the tail node,
		// and the count of elements in that node
		return new nodeInformation(tail.previous, tail.previous.count);
	}

	/**
	 * wipes data in new array (type E)
	 * 
	 * @param list
	 * @return
	 */
	private E[] getArray(StoutList list) {

		E[] array = (E[]) new Comparable[size]; // new array of type E = size of the StoutList.
		Node temp = head.next;
		int index = 0;

		while (temp != tail) { // Iterate through each node in the StoutList until the tail node is reached.
			for (int i = 0; i < temp.count; i++) { // Iterate through the data of each node.
				array[index++] = temp.data[i]; // Assign the elements from the data of the node to the
												// corresponding index in the array.
			}
			temp.data = null; // Set the data array of the node to null.
			temp = temp.next; // Move to the next node.
			head.next = temp; // Set the next node after the head node to the current node.
		}

		size = 0; // Set the size of the StoutList to 0, as all elements have been moved to the
					// array.
		return array; // Return the array containing the converted data from the StoutList.
	}

	private class StoutListIterator implements ListIterator<E> {
		// constants you possibly use ...

		private static final int FRONT = -1;
		private static final int BEHIND = 1;
		private static final int NONE = 0;

		private Node current_node; // Represents the current node in the StoutList
		private int index; // Represents the current index of the iterator in the StoutList
		private int offset; // Represents the current offset in the data array of the current node
		private int direction; // Represents the direction of the iterator's movement (FRONT, BEHIND, or NONE)

		// instance variables ...

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			this(0);
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {

			if (pos < 0 || pos > size)
				throw new IndexOutOfBoundsException("" + pos);

			nodeInformation node_info = find(pos); // Find the node and offset corresponding to the specified position
			index = pos; // Set the index to the specified position
			offset = node_info.offset; // Set the offset to the offset obtained from the find() method
			current_node = node_info.node; // Set the current node to the node obtained from the find() method

		}

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException("Nothing in next");

			E return_value = current_node.data[offset++]; // Get the element at the current node's data with the
															// current offset value and then increment the offset
			if (offset >= current_node.count) { // If the offset becomes greater than or equal to the count of elements
												// in the current node's data
				current_node = current_node.next; // Set the current node to its next node
				offset = 0; // Set the offset to 0
			}

			direction = FRONT; // Set the direction to FRONT
			index++; // Increase the index by 1
			return return_value; // Return the retrieved element

		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public boolean hasPrevious() {
			return index > 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) // If there is no previous element
				throw new NoSuchElementException("Nothing in previous");

			offset--; // Decrease the offset by 1
			if (offset == -1) {
				current_node = current_node.previous; // Set the current node to its previous node
				offset = current_node.count - 1; // Set the offset to the count of elements in the current node's data
													// array minus 1
			}
			index--; // decrease index by 1
			direction = BEHIND;
			return current_node.data[offset]; // Return the element at the current node's data with the current offset
												// value
		}

		@Override
		public int previousIndex() {
			return index - 1; // previous index
		}

		public void remove() {
			if (direction == NONE) { // If the direction is NONE
				throw new IllegalStateException("direction is none");
			} else {
				if (direction != BEHIND) { // If the direction is not BEHIND
					StoutList.this.remove(index - 1); // Remove the element from the StoutList at the index of
														// (index - 1)
					if (offset != 0) { // If the offset is not 0
						--offset; // Decrease the offset by 1
					}
					--index; // Decrease the index by 1
				} else {
					StoutList.this.remove(index); // Remove the element from the StoutList at the index of index
				}
				direction = NONE; // Set the direction to NONE
			}
		}

		@Override
		public void set(E element) {
			if (direction != BEHIND) { // If the direction is not BEHIND
				if (offset - 1 == -1) { // If the offset - 1 is equal to -1
					int in_place = current_node.previous.count - 1; // Set "in place" to the count of elements in the
																	// previous
					// node's data minus 1
					current_node.previous.data[in_place] = element; // Set the element in the last "in place" of the
																	// previous node's data array
				} else {
					current_node.data[offset - 1] = element; // Set the element in the data array of the current node at
																// the index of offset - 1
				}
			} else {
				current_node.data[offset] = element; // Set the element in the current node's data array at the index of
														// offset
			}

		}

		@Override
		public void add(E element) {
			direction = NONE; // Set the direction to NONE

			StoutList.this.add(index++, element); // Add the element to the StoutList at the current index and then
													// increment the index
			nodeInformation temp = find(index); // Find the node and offset corresponding to the updated index
			current_node = temp.node; // Set the current node to the node obtained from the find() method
			offset = temp.offset; // Set the offset to the offset obtained from the find() method
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {

		int array_length = arr.length;

		int j; // position tracker during algorithm execution

		for (int i = 1; i < array_length; ++i) { // start @ second element

			j = i; // position = current index

			// Compare the current element with the element before it using compare(Object
			// o1, Object o2) from Comparator interface
			// If the current element is smaller, swap it with the element before it
			// Continue this process until the element is in its correct sorted position
			while (j > 0 && comp.compare(arr[j], arr[j - 1]) < 0) {
				E temp = arr[j - 1]; // Store the element before the current element in temp
				arr[j - 1] = arr[j]; // Swap the current element with the element before it
				arr[j] = temp; // Place the temp containing the original element in the previous position
				j--; // Move to the previous position to continue the comparison and swap process
			}
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		int array_length = arr.length;
		int i, j;
		for (i = 0; i < array_length - 1; i++) { // passes number
			for (j = 0; j < array_length - i - 1; j++) { // comparisons within each pass

				if (arr[j].compareTo(arr[j + 1]) < 0) { // use .compareTo() from Comparable interface to compare
														// adjacent items in the array

					// perform swap IF the elements are in the wrong order
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}

}