/* DListNode1.java */

/**
 *  A DListNode1 is a node in a DList1 (doubly-linked list).
 */

public class DListNode1 {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   */

  public int item;
  public int item2;
  public DListNode1 prev;
  public DListNode1 next;

  /**
   *  DListNode1() constructor.
   */
  DListNode1() {
    item = 0;
    item2 = 0;
    prev = null;
    next = null;
  }

  DListNode1(int i) {
    item = i;
    item2 = 0;
    prev = null;
    next = null;
  }

  DListNode1(int i, int i2) {
    item = i;
    item2 = i2;
    prev = null;
    next = null;
  }
  
}
