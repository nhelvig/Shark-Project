/* DList1.java */

/**
 *  A DList1 is a mutable doubly-linked list.  (No sentinel, not
 *  circularly linked.)
 */

public class DList1 {

	/**
	 *  head references the first node.
	 *  tail references the last node.
	 *
	 *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
	 */

	protected DListNode1 head;
	protected DListNode1 tail;
	protected DListNode1 prev;
	protected long size;

	/* DList1 invariants:
	 *  1)  head.prev == null.
	 *  2)  tail.next == null.
	 *  3)  For any DListNode1 x in a DList, if x.next == y and x.next != null,
	 *      then y.prev == x.
	 *  4)  For any DListNode1 x in a DList, if x.prev == y and x.prev != null,
	 *      then y.next == x.
	 *  5)  The tail can be accessed from the head by a sequence of "next"
	 *      references.
	 *  6)  size is the number of DListNode1s that can be accessed from the
	 *      head by a sequence of "next" references.
	 */

	/**
	 *  DList1() constructor for an empty DList1.
	 */
	public DList1() {
		head = null;
		tail = null;
		prev = null;
		size = 0;
	}

	/**
	 *  DList1() constructor for a one-node DList1.
	 */
	public DList1(int a) {
		head = new DListNode1();
		tail = head;
		head.item = a;
		size = 1;
	}

	/**
	 *  DList1() constructor for a two-node DList1.
	 */
	public DList1(int a, int b) {
		head = new DListNode1();
		head.item = a;
		tail = new DListNode1();
		tail.item = b;
		head.next = tail;
		tail.prev = head;
		size = 2;
	}

	/**
	 *  insertFront() inserts an item at the front of a DList1.
	 */
	public void insertFront(int i, int x) {
		// Your solution here.
		if (this.head == null){
			this.head = new DListNode1(i, x);
			this.tail = head;
		} else {
			DListNode1 curr = head;
			head = new DListNode1(i, x);
			head.next = curr;
			curr.prev = head;
		} 
		size++;
	}

	/**
	 *  removeFront() removes the first item (and node) from a DList1.  If the
	 *  list is empty, do nothing.
	 */
	public void removeFront() {
		// Your solution here.
		DListNode1 curr = head;
		if (size==1){
			this.head = null;
			this.tail = null;
			size--;
		}
		if (this.head != null){
			this.head = this.head.next;
			head.prev=null;
			size--;
		}

	}

	/**
	 *  toString() returns a String representation of this DList.
	 *
	 *  DO NOT CHANGE THIS METHOD.
	 *
	 *  @return a String representation of this DList.
	 */
	public String toString() {
		String result = "[  ";
		DListNode1 current = head;
		while (current != null) {
			result = result + current.item + "  ";
			current = current.next;
		}
		return result + "]";
	}

}
