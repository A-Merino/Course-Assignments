import java.util.Scanner;

public final class Test {
   public static void main(final String[] args) {
      Scanner scan = new Scanner(System.in, "US-ASCII"); // creates scanners
      final int volume = scan.nextInt();
      int bestSA = 10000000;
      int sa = 0;
      int l = 1;
      int w = 1;
      int h = 1;
      double wholeNum = 0;
      for (int length = 1; length <= volume; length++) {
         wholeNum = volume / length;
         if (wholeNum % 1 != 0) {
            length++;
         }
         for (int height = 1; height <= volume; height++) {
            wholeNum = wholeNum / height;
            if (wholeNum % 1 != 0) {
               height++;
            }
            for (int width = 1; width <= volume; width++) {
               wholeNum = wholeNum / width;
               if (wholeNum % 1 != 0) {
                  width++;
               }
               if (length * height * width == volume) {
                  sa = 2 * ((width * length) + (length * height) + (height * width));
                  if (sa < bestSA) {
                     bestSA = sa;
                     l = length;
                     w = width;
                     h = height;
                  }
               }
            }
         }
      }
      
      System.out.println(h);
      System.out.println(l);
      System.out.println(w);
      System.out.println(bestSA);

      
   }
}