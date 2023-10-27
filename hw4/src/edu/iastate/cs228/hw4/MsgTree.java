package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class responsible for decoding binary messages
 * 
 * @author
 *
 */

public class MsgTree {

	public char payload_character;
	public MsgTree left; // left child
	public MsgTree right; // right child
	private static String binary_message;

	/**
	 * Default Constructor
	 * 
	 * @param payload_character (for the node)
	 */
	public MsgTree(char payload_character) {
		this.payload_character = payload_character;
		this.left = null;
		this.right = null;
	}

	/**
	 * Decodes binary message using decoding tree
	 * 
	 * @param codes
	 * @param decipher
	 */
	public void decoder(MsgTree codes, String decipher) {
		System.out.println("\n DECODE SUCCESS: \n");
		StringBuffer decrypter = new StringBuffer(); // string construction
		MsgTree current = codes; // current node = root of decoding tree

		for (int i = 0; i < decipher.length(); i++) { // iteration through each character in binary message

			if (decipher.charAt(i) != '0') {
				current = current.right; // traverse to right
			} else {
				current = current.left; // traverse to left
			}

			if (current.payload_character != '^') { // if current node is lead node
				obtainer(codes, current.payload_character, binary_message = "");
				decrypter.append(current.payload_character); // append to decrypted message
				current = codes;
			}
		}
		System.out.println(decrypter.toString()); // print
	}

	/**
	 * Searches for a given character in the decoding tree and stores the binary
	 * pathway to reach that character
	 * 
	 * @param root
	 * @param character
	 * @param pathway
	 * @return
	 */

	private static boolean obtainer(MsgTree root, char character, String pathway) {

		if (root != null) {

			if (root.payload_character == character) {
				binary_message = pathway;
				return true;
			}
			// Recursively search in the left and right subtrees, appending "0" for left
			// child and "1" for right child to the current binary pathway
			return obtainer(root.left, character, pathway + "0") || obtainer(root.right, character, pathway + "1");
		}
		// if not found
		return false;

	}

	/**
	 * Displays the binary codes for each character in the decoding tree.
	 *
	 * @param root
	 * @param code
	 */
	public static void displayCodes(MsgTree root, String code) {

		System.out.println("Code\n-----------------------------");
		for (char character : code.toCharArray()) {
			obtainer(root, character, binary_message = ""); // search character
			System.out.print("    " + (character == '\n' ? "\\n" : character + " ") + "    " + binary_message + "\n"); // print
																														// character
																														// and
																														// binary
																														// code
		}
	}

	/**
	 * Constructs MsgTree object from string using stack (15% extra credit)
	 * 
	 * @param builder_string
	 */
	public MsgTree(String builder_string) {
		if (builder_string != null && builder_string.length() > 2) {
			Stack<MsgTree> message_stack = new Stack<>(); // create stack to keep track of parent nodes during
															// construction
			MsgTree current = this;
			int index = 0;
			Boolean last = true;
			message_stack.push(this);
			this.payload_character = builder_string.charAt(index++);

			while (builder_string.length() > index) {
				MsgTree node = new MsgTree(builder_string.charAt(index++));
				if (last == true) {
					current.left = node; // left child = new node
					if (node.payload_character == '^') {
						current = message_stack.push(node); // push node to message stack
					} else {
						if (!message_stack.isEmpty()) {
							current = message_stack.pop();
						}
						last = false;
					}
				} else {
					current.right = node; // right child = new node
					if (node.payload_character == '^') {
						current = message_stack.push(node);
						last = true;
					} else {
						if (!message_stack.isEmpty()) {
							current = message_stack.pop();
						}
						last = false;
					}
				}
			}
		} else {
			return;
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("File to be decoded: ");
		Scanner file_scanner = new Scanner(System.in); // user input (types in correct file)
		String file_name = file_scanner.nextLine(); // read what user typed
		file_scanner.close();

		Path path = Paths.get(file_name); // create path
		String file_content = Files.readString(path).trim(); // read contents and trim whitespace
		int position = file_content.lastIndexOf('\n'); // find last occurrence of a new line
		String codes = file_content.substring(0, position);
		String message = file_content.substring(position).trim();

		MsgTree root = new MsgTree(codes); // construct new decoding tree
		MsgTree.displayCodes(root, codes); // display codes
		root.decoder(root, message); // decipher
	}

}
