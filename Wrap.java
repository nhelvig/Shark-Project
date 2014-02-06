public class Wrap {
	static int height = 25;
    static int width = 30;

	public static int wrapX(int x) {
    if (x < 0) {
      x *= -1;
      return width - (x % (width - 1));
    }
    if (x > width - 1) {
      return x % width;
    }
    return x;
  }

  public static int wrapY(int y) {
    if (y < 0) {
      y *= -1;
      return height - (y % (height - 1));
    }
    if (y > height - 1) {
      return y % height;
    }
    return y;
  }

   public static void main(String[] argv) {
    int[][] arr = new int[3][];
    arr[0] = new int[2];
    arr[1] = new int[3];
    arr[2] = new int[4];

   }
}