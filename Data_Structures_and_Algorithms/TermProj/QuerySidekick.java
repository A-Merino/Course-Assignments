/*

  Authors (group members): Alex Merino, Jorge Vucanovic Castillo, T'Avion Rodgers
  Email addresses of group members: amerino2022@my.fit.edu, 
  Group name: JAT

  Course: CSE 2010
  Section: 14

  Description of the overall algorithm:


*/

// import important apis
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuerySidekick
{
    String[] guesses = new String[5];  // 5 guesses from QuerySidekick
    // data structures to hold input then distribute to Trie
    HashMap<String, Integer> first = new HashMap<>();
    public LinkedTrie<Query> data = new LinkedTrie<>();
    String curLine = "";
    String beg = "";
    boolean reset = false;

    // initialization of ...
    public QuerySidekick()
    {

    }

    // process old queries from oldQueryFile
    public void processOldQueries(String oldQueryFile) throws FileNotFoundException
    {
      // Collect first file and create a scanner for it
      File file = new File(oldQueryFile);
      Scanner scan = new Scanner(file);

      
      /* 
        For each line in the file:
          Collect the line and remove extra spaces
          if it is in the hashmap then add one to frequency
            if not then add to hashmap with frequency of one
      */
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        line = line.replaceAll("\\s+", " ");
        if (first.containsKey(line)) {
          first.put(line, first.get(line) + 1);
        } else {
          first.put(line, 1);
        }
      }

      // collects a Set of all the strings from the HashMap 
      // then for each of them add it to the Trie
      Set<String> s = first.keySet();

      for (String q : s) {
        data.add(q, first.get(q));        
      }

      data.startComp(data.root);

      // collect garbage to lower memory usage
      first.clear();
      s.clear();
      System.gc();

    }

    // based on a character typed in by the user, return 5 query guesses in an array
    // currChar: current character typed in by the user
    // currCharPosition: position of the current character in the query, starts from 0
    public String[] guess(char currChar, int currCharPosition)
    {

      /*
        Add char to the entire current line of query 
      */

      curLine = curLine + currChar;
      Node cur = null;

      // if the first letter of query, input line
      // if not, then input current char
      if (currCharPosition == 0 || reset) {
        cur = data.find(curLine);
        reset = false;
      } else {
        cur = data.find(currChar);
      }


      /*
        If the find method does not find next letter 
          OR the current letter is "space" and it has no guesses

          Add to beg String and then reset search at root node

      */

      if (cur == null || (currChar == ' ' && cur.best[0] == null)) {
        beg = beg + curLine;
        reset = true;
        // cur = data.find(curLine.substring(0,1)); // Increases accuracy but loses a lot of time
        curLine = "";
        return guesses;
      }

      // gather the array of Strings from the current node and return to Eval
      
      guesses = data.best(cur);
      for (int g = 0; g < 5; g++) {
        if (guesses[g] != null) { // if there are guesses then add to beg String
          guesses[g] = beg + guesses[g]; 
        } else if (beg.length() < 1) { // if beg is zero and no guess, guess current line
          guesses[g] = curLine; 
        } else { // guess current line
          guesses[g] = beg + curLine;
        }
      }
      return guesses;
    
    }



    // feedback on the 5 guesses from the user
    // isCorrectGuess: true if one of the guesses is correct
    // correctQuery: 3 cases:
    // a.  correct query if one of the guesses is correct
    // b.  null if none of the guesses is correct, before the user has typed in 
    //            the last character
    // c.  correct query if none of the guesses is correct, and the user has 
    //            typed in the last character
    // That is:
    // Case       isCorrectGuess      correctQuery   
    // a.         true                correct query
    // b.         false               null
    // c.         false               correct query
    public void feedback(boolean isCorrectGuess, String correctQuery)        
    {

      if (!isCorrectGuess && correctQuery != null) {
        // System.out.println(correctQuery + " "+ beg);
        data.add(correctQuery, 2); // add new data to Trie
        // System.gc();
      }

      // clear curLine variable
      if (correctQuery != null) {
        curLine = "";
        beg = "";
      }
    }

}
