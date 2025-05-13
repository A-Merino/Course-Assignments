/*

  Authors (group members): Alex Merino, Jorge Vucanovic Castillo, T'Avion Rodgers
  Email addresses of group members: amerino2022@my.fit.edu, 
  Group name: JAT

  Course: CSE 2010
  Section: 14

  Description of the overall algorithm:


*/

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuerySidekick3
{
    String[] guesses = new String[5];  // 5 guesses from QuerySidekick
    static HashMap<String, Integer> first = new HashMap<>();
    LinkedTrie3<Query> data = new LinkedTrie3<>();
    static String curLine = "";
    static List<Query> subData  = new ArrayList<>();
    static int[] letInd = new int[26];

    // initialization of ...
    public QuerySidekick3()
    {

    }

    // process old queries from oldQueryFile
    //
    // to remove extra spaces with one space
    // str2 = str1.replaceAll("\\s+", " ");
    public void processOldQueries(String oldQueryFile) throws FileNotFoundException
    {
      File file = new File(oldQueryFile);
      Scanner scan = new Scanner(file);

      
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        line = line.replaceAll("\\s+", " ");
        if (first.containsKey(line)) {
          first.put(line, first.get(line) + 1);
        } else {
          first.put(line, 1);
        }
      }

      Set<String> s = first.keySet();

      for (String q : s) {
        data.add(q, first.get(q));        
      }

    }

    // based on a character typed in by the user, return 5 query guesses in an array
    // currChar: current character typed in by the user
    // currCharPosition: position of the current character in the query, starts from 0
    public String[] guess(char currChar, int currCharPosition)
    {
      curLine = curLine + currChar;

      Node cur = data.find(curLine);

      guesses = data.best(cur);

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

      if (isCorrectGuess) {
        // System.out.println(correctQuery + "   " + curLine);
      }
      
      if (correctQuery != null) {
        curLine = "";
      }
    }

}
