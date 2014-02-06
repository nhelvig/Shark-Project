/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
  private int width;
  private int height;
  private int starve;
  private int[] runTypes;
  private int[] runLengths;
  private DList1 dlist;
  private DListNode1 curr;


  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
    this(i, j, starveTime, null, null); 
  }
  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
    this.width = i;
    this.height = j;
    this.starve = starveTime;
    this.runTypes = runTypes;
    this.runLengths = runLengths;
    this.dlist = new DList1();
    this.curr = new DListNode1();
    int counter = 0;
    for (int x = this.runTypes.length - 1; x >= 0; x--) {
      dlist.insertFront(runTypes[x], runLengths[x]);
    }
    this.curr = this.dlist.head;
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as an
   *  array of two ints), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
    this.curr = this.dlist.head;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  an array of two ints (constructed here), representing the type and the
   *  size of the run, in that order.
   *  @return the next run in the enumeration, represented by an array of
   *          two ints.  The int at index zero indicates the run type
   *          (Ocean.EMPTY, Ocean.SHARK, or Ocean.FISH).  The int at index one
   *          indicates the run length (which must be at least 1).
   */

  public int[] nextRun() {
    // Replace the following line with your solution.
    int[] run = new int[2];
    if (this.curr != null) {
      if (this.curr.item != Ocean.FISH && this.curr.item != Ocean.EMPTY) {
        run[0] = Ocean.SHARK;
      } else {
        run[0] = this.curr.item;
      }
      run[1] = this.curr.item2;
      this.curr = this.curr.next;
      return run;
    }
    return null;
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
    // Replace the following line with your solution.
    Ocean newOcean = new Ocean(this.width, this.height, this.starve);
    int x;
    int y;
    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        if (this.dlist.head != null) {
        if (dlist.head.item == Ocean.FISH) {
          newOcean.addFish(x,y);
          dlist.head.item2--;
        } else if (dlist.head.item != Ocean.FISH && dlist.head.item != Ocean.EMPTY) {
          newOcean.addShark(x, y, dlist.head.item / 10 - 1);
          dlist.head.item2--;
        } else if (dlist.head.item == Ocean.EMPTY) {
          newOcean.addEmpty(x,y);
          dlist.head.item2--;
        } 
        if (this.dlist.head.item2 == 0) {
          this.dlist.head = this.dlist.head.next;
        }
      }
      } }
    return newOcean;
  }

  

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    this.width = sea.width();
    this.height = sea.height();
    this.starve = sea.starveTime();
    this.runTypes = null;
    this.runLengths = null;
    this.curr = new DListNode1();
    this.dlist = new DList1();
    for (int y = 0; y < this.height; y++) {
      for  (int x = 0; x < this.width; x++) {
        if (dlist.size == 0) {
          if (sea.cellContents(x,y) == Ocean.SHARK) {
            dlist.insertFront(sea.sharkFeeding(x,y) + 10, 1);
          } else {
            dlist.insertFront(sea.cellContents(x,y), 1);
          }
        } else if (sea.cellContents(x,y) == Ocean.SHARK) {
          if (sea.sharkFeeding(x,y) + 10 == dlist.tail.item) {
            dlist.tail.item2++;
          } else {

            DListNode1 curr = dlist.head;
            while (curr.next != null) {
              curr = curr.next;
            }
            curr.next = new DListNode1(sea.sharkFeeding(x,y) + 10, 1);
            dlist.tail = curr.next;
            dlist.size++;

          }
        } else if (sea.cellContents(x, y) == dlist.tail.item) {
          dlist.tail.item2++;
        } else {
            DListNode1 curr = dlist.head;
            while (curr.next != null) {
              curr = curr.next;
            }
            curr.next = new DListNode1(sea.cellContents(x,y), 1);
            dlist.tail = curr.next;
            dlist.size++;
        }
      }
    }
    this.curr = this.dlist.head;
    check();
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    int finish = x * y;
    while (curr.item2 - finish > 0) {
      finish -= this.curr.item2;
      this.curr.prev = this.curr;
      this.curr = this.curr.next;
    }
    if (this.curr.item2 - finish == 0 && this.curr.item == Ocean.EMPTY) {
      this.curr.item = Ocean.FISH;
      if (this.curr.prev != null && this.curr.item == this.curr.prev.item) {
        if (this.curr.next != null && this.curr.item == this.curr.next.item) {
          this.curr.item2 += this.curr.prev.item2 + this.curr.next.item2;
          this.curr.prev = this.curr.prev.prev;
          this.curr.next = this.curr.next.next;
        } else {
          this.curr.item2 += this.curr.prev.item2;
          this.curr.prev = this.curr.prev.prev;
        }
      } else if (this.curr.next != null && this.curr.item == this.curr.next.item) {
          this.curr.item2 += this.curr.next.item2;
          this.curr.next = this.curr.next.next;
      }
    } else if (this.curr.item2 - finish < 0 && this.curr.item == Ocean.EMPTY) {
        if (this.curr.prev != null) {
          this.curr.prev.prev = this.curr.prev;
          this.curr.prev.item = Ocean.EMPTY;
          this.curr.prev.item2 = finish - 1;
        }
        if (this.curr.next != null) {
          this.curr.next.next = this.curr.next;
          this.curr.next.item = Ocean.EMPTY;
          this.curr.next.item2 = this.curr.item2 - finish;
        }
        this.curr.item = Ocean.FISH;
        this.curr.item2 = 1;
    }
    this.dlist.head = this.curr;
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.


    int finish = x * y;
    DListNode1 pointer = new DListNode1();
    while (this.curr != null && curr.item2 - finish > 0) {
      finish -= this.curr.item2;
      pointer = this.curr.prev = this.curr;
      this.curr = this.curr.next;
    }
    if (this.curr != null && this.curr.item2 - finish == 0 && this.curr.item == Ocean.EMPTY) {
      this.curr.item = Ocean.SHARK;
      if (this.curr.prev != null && this.curr.item == this.curr.prev.item) {
        if (this.curr.next != null && this.curr.item == this.curr.next.item) {
          this.curr.item2 += this.curr.prev.item2 + this.curr.next.item2;
          this.curr.prev = this.curr.prev.prev;
          this.curr.next = this.curr.next.next;
        } else {
          this.curr.item2 += this.curr.prev.item2;
          this.curr.prev = this.curr.prev.prev;
        }
      } else if (this.curr.next != null && this.curr.item == this.curr.next.item) {
          this.curr.item2 += this.curr.next.item2;
          this.curr.next = this.curr.next.next;
      }
    } else if (this.curr != null && this.curr.item2 - finish < 0 && this.curr.item == Ocean.EMPTY) {
        if (this.curr.prev != null) {
          this.curr.prev.prev = this.curr.prev;
          this.curr.prev.item = Ocean.EMPTY;
          this.curr.prev.item2 = finish - 1;
        }
        if (this.curr.next != null) {
          this.curr.next.next = this.curr.next;
          this.curr.next.item = Ocean.EMPTY;
          this.curr.next.item2 = this.curr.item2 - finish;
        }
        this.curr.item = Ocean.SHARK;
        this.curr.item2 = 1;
    }
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
    int total = 0;
    while (this.curr != null) {
      if (this.curr.item2 < 1) {
        System.out.println("WARNING! ILL-FORMED LINKED LISTqweqweqwe");
        break;
      }
      if (this.curr.prev != null && this.curr.item == this.curr.prev.item ) {
        System.out.println("WARNING! ILL-FORMED LINKED LISTasdasd");
        break;
      } else {
        total += this.curr.item2;
        this.curr = this.curr.next;
      }
    }
    if (total != this.width * this.height) {
      System.out.println("WARNING! ILL-FORMED LINKED LISThhhhh" + total + " != " + this.width * this.height);
    }
    this.curr = this.dlist.head;
  }

}
