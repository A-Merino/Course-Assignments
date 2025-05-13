import java.util.Comparator;

// Comparator class to help sort edges in order alphabetically

public class NodeComp implements Comparator<GraphNode> {
   public int compare(GraphNode f, GraphNode o) {
      if (f.getPerson().compareTo(o.getPerson()) > 0) {
         return 1;
      } else if (f.getPerson().compareTo(o.getPerson()) < 0) {
         return -1;
      } else {
         return 0;
      }
   }
}