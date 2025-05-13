import java.io.FileNotFoundException;

public class Tester {
   public static void main(String[] args) throws FileNotFoundException {
      // one(args[0]);
      // two();
      // three(args[0]);
      four(args[0]);
   }

   public static void four(String r) throws FileNotFoundException {
      QuerySidekick a = new QuerySidekick();
      a.processOldQueries(r);
      a.data.startComp(a.data.root);

   }

   public static void three(String r) throws FileNotFoundException{
      QuerySidekick a = new QuerySidekick();
      a.processOldQueries(r);
      // System.out.println(a.subData);
      // a.data.startComp(a.data.root);
      String [] g = a.guess('v', 0);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);         
      }
      System.out.println();
         
      g = a.guess('e', 1);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();

      g = a.guess('c', 2);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();

      g = a.guess('t', 3);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = a.guess('o', 4);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = a.guess('r', 5);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = a.guess(' ', 6);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();

   }

   public static void two() {
      LinkedTrie<Query> lt = new LinkedTrie<>();
      lt.add("algorithm", 100);
      Node e = lt.find("algo");
      System.out.println(e.full);
      System.out.println(e);
      System.out.println(lt);

   }

   public static void one(String r) throws FileNotFoundException {
      QuerySidekick q = new QuerySidekick();
      q.processOldQueries(r);
      String [] g = q.guess('a', 0);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
         
      g = q.guess('l', 1);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();

      g = q.guess('g', 2);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();

      g = q.guess('o', 3);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('r', 4);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('i', 5);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('t', 6);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('h', 7);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('m', 8);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess(' ', 9);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('s', 10);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('t', 11);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      g = q.guess('y', 12);
      for (int i = 0; i< 5; i++) {
         System.out.println(g[i]);
         
      }
      System.out.println();
      // g = q.guess('y', 13);
      // for (int i = 0; i< 5; i++) {
      //    System.out.println(g[i]);
         
      // }
      // System.out.println();

      String [] g1 = q.guess('l', 1);
      for (int i = 0; i< 5; i++) {
         System.out.print(g1[i] + " ");
         
      }

   }

}