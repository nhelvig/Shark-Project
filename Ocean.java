/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  private int width;
  private int height;
  private int starve;
  private int[][] oceanArray;
  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
    width = i;
    height = j;
    starve = starveTime;
    oceanArray = new int[width][height];
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
    // Replace the following line with your solution.
    return starve;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    x = wrapX(x);
    y = wrapY(y);
    if (oceanArray[x][y] == EMPTY) {
      oceanArray[x][y] = FISH;
    }
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    x = wrapX(x);
    y = wrapY(y);
    if (oceanArray[x][y] == EMPTY) {
      addShark(x, y, starve);
    }
  }

  public void addEmpty(int x, int y) {
    x = wrapX(x);
    y = wrapY(y);
    oceanArray[x][y] = Ocean.EMPTY;
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    // Replace the following line with your solution.
    x = wrapX(x);
    y = wrapY(y);
    if (oceanArray[x][y] != FISH && oceanArray[x][y] != EMPTY) {
      return SHARK; //Needed to do this since I used varying numbers to represent sharks.
    }
    return oceanArray[x][y];
  }

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
    // Replace the following line with your solution.
    Ocean newSea = new Ocean(width, height, starve);
    int x = 0;
    int y = 0;
    for (x = 0; x < width; x++) {
      for (y = 0; y < height; y++) {
        x = wrapX(x);
        y = wrapY(y);
        int contents = cellContents(x, y);
        if (contents == SHARK) {
          if (sharkRule(x, y)) {
            newSea.addShark(x, y, starve);
          } else if (sharkFeeding(x, y) == 0) {
            newSea.oceanArray[x][y] = EMPTY;
          } else {
            newSea.addShark(x, y, sharkFeeding(x,y)/10 - 1);
          }
        } else if (contents == FISH) {
            int numSharks = fishRule(x, y);
            if (numSharks == 0) {
              newSea.addFish(x,y);
            } else if (numSharks == 1) {
              newSea.oceanArray[x][y] = EMPTY;
            } else if (numSharks >= 2) {
              newSea.addShark(x, y, starve);
            }
        } else if (contents == EMPTY) {
          int fish = emptyRule(x,y)[0];
          int sharks  = emptyRule(x,y)[1];
          if (fish < 2) {
            newSea.oceanArray[x][y] = EMPTY;
          } else if (sharks <= 1) {
            newSea.addFish(x,y); 
          } else {
            newSea.addShark(x, y, starve);
          }
        }
      }
    }
    return newSea;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    x = wrapX(x);
    y = wrapY(y);
    if (oceanArray[x][y] == EMPTY) {
      oceanArray[x][y] = 11 + 10 * feeding;
    }
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    x = wrapX(x);
    y = wrapY(y);
    if (oceanArray[x][y] / 10 > 1) {
    return oceanArray[x][y] - 10;
   }
    return 0;
  }

  //Creates an array that stores all the values of the objects around this position
  private int[] arrayOfNeighbors(int x, int y) {
    int [] neighbors = new int[8];
    neighbors[0] = cellContents(wrapX(x-1), wrapY(y-1));
    neighbors[1] = cellContents(wrapX(x), wrapY(y+1));
    neighbors[2] = cellContents(wrapX(x+1), wrapY(y+1));
    neighbors[3] = cellContents(wrapX(x-1), wrapY(y+1));
    neighbors[4] = cellContents(wrapX(x), wrapY(y-1));
    neighbors[5] = cellContents(wrapX(x+1), wrapY(y-1));
    neighbors[6] = cellContents(wrapX(x-1), wrapY(y));
    neighbors[7] = cellContents(wrapX(x+1), wrapY(y));
    return neighbors;
  }

  //Performs the first rule to see if a shark can eat a nearby fish
  private boolean sharkRule(int x, int y) {
    int[] neighbors = arrayOfNeighbors(x, y);
    int i;
    boolean fishies = false;
    for (i = 0; i < neighbors.length; i++) {
      if (neighbors[i] == FISH) {
        fishies = true;
      }
    }
    return fishies;
  }


  //Returns the number of sharks that are around the fish in order to see if the 
  //fish will be eaten, remain, or a new shark will be born. 
  private int fishRule(int x, int y) {
    int[] neighbors = arrayOfNeighbors(x, y);
    int i;
    int totalSharks = 0;
    for (i = 0; i < neighbors.length; i++) {
      if (neighbors[i] == SHARK){
        totalSharks += 1;
      }
    }
    return totalSharks;
  }

  //Functions to see if anything will be added to replace the empty spot by creating an 
  //array that will be used in timeStep() where the first element is the number of fish
  //and the second is the number of sharks
  private int[] emptyRule(int x, int y) {
    int[] neighbors = arrayOfNeighbors(x, y);
    int i;
    int [] fishSharks = new int[2];
    for (i = 0; i < neighbors.length; i++) {
      if (neighbors[i] == FISH) {
        fishSharks[0] += 1;
      } else if (neighbors[i] == SHARK) {
        fishSharks[1] += 1;
      }
    }
    return fishSharks;
  }

  private int wrapX(int x) {
    if (x < 0) {
      x *= -1;
      return width - (x % (width - 1));
    }
    if (x > width - 1) {
      return x % width;
    }
    return x;
  }

  private int wrapY(int y) {
    if (y < 0) {
      y *= -1;
      return height - (y % (height - 1));
    }
    if (y > height - 1) {
      return y % height;
    }
    return y;
  }
}