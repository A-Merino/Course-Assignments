import java.util.ArrayList;
public final class Main{
   public static void main(final String[] args) {
      ArrayList<Integer> norm = new ArrayList<Integer>();
      MyArrayList mine = new MyArrayList();
      Integer haha = 8;
      norm.add(7);

      mine.add(8);
      mine.add(7);
      mine.add(8);
      mine.add(8);   
      mine.add(8);
      mine.add(7);
      mine.add(8);
      mine.add(8);
      mine.add(8);
      mine.add(7);
      mine.add(8);
      mine.set(2,12);
      mine.add(8);
      mine.remove(haha);
      mine.remove(4);



      System.out.println(norm);
      System.out.println(mine);
      System.out.println(mine.get(7));
      System.out.println(norm.size());
      System.out.println(mine.size());

   }
}