// Class to hold each line and the amount of times it has appeared
public class Query {

   String queries;
   int amount;

   public Query(String q, int a) {
      queries = q;
      amount = a;
   }

   public String getQuery() {
      return queries;
   }

   public int getAmount() {
      return amount;
   }

   public String toString() {
      return queries + " " + amount;
   }
   

}