import java.util.Comparator;

// Allows for sorting of Queries based on frequency

public class QComp implements Comparator<Node> {
   public int compare(Node f, Node s) {
      if (f.children.size() > s.children.size()) {
         return 1;
      } else if (f.children.size() < s.children.size()) {
         return -1;
      } else {
         return f.getName().compareTo(s.getName());
      }
   }
}