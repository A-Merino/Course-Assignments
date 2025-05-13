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

public class QuerySidekickOld
{
    String[] guesses = new String[5];  // 5 guesses from QuerySidekick
    static HashMap<String, Integer> first = new HashMap<>();
    static ArrayList<Query> data = new ArrayList<>();
    static String curLine;
    static List<Query> subData  = new ArrayList<>();
    static int[] letInd = new int[26];

    // initialization of ...
    public QuerySidekickOld()
    {
      char letter = 'a';
      while(letter <= 'z') {
        data.add(new Query(Character.toString(letter), -1));
        letter++;
      }

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
        data.add(new Query(q, first.get(q)));
        
      }
      data.sort(new QComp());

      char lets = 'a';
      int count = 0; 
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).getQuery().equals(Character.toString(lets))) {
          letInd[count] = i;
          count++;
          lets++;
        }
      }

    }

    // based on a character typed in by the user, return 5 query guesses in an array
    // currChar: current character typed in by the user
    // currCharPosition: position of the current character in the query, starts from 0
    public String[] guess(char currChar, int currCharPosition)
    {


      if (currCharPosition == 0) {
        subData = data;
        curLine = curLine + currChar;
        if (currChar - 97 == 25) {
          subData.subList(letInd[currChar - 97], data.size() - 1);
          
        }
        else if (currChar - 97 >= 0) {
          subData.subList(letInd[currChar - 97], letInd[currChar - 96]);
        } else {
          subData.subList(0, letInd[0]);
        }
      } else {
        curLine = curLine + currChar;

      }

      int counter = 0;
      Query[] g1 = new Query[5];
      for (int k = 1; k < subData.size(); k++) {
        if (counter == 5) {
          counter = 0;
        }
        if ((g1[counter] == null || g1[counter].getAmount() < subData.get(k).getAmount()) && (subData.get(k).getQuery().length() <= currCharPosition || subData.get(k).getQuery().substring(0, currCharPosition+1).equalsIgnoreCase(curLine))) {
          g1[counter] = subData.get(k);
          counter++;
        }
      }


        for (int z = 0; z <=4; z++) {
          if (g1[z] == null) {
            guesses[z] = subData.get(z+1).getQuery();
          } else {
            guesses[z] = g1[z].getQuery();

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

    }


    // public static void main(String[] args) throws FileNotFoundException {
    //   QuerySidekick q = new QuerySidekick();
    //   processOldQueries(args[0]);


    //   String [] g = guess('a', 0);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g2 = guess('l', 1);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g2[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g3 = guess('g', 2);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g3[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g4 = guess('o', 3);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g4[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g5 = guess('r', 4);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g5[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g6 = guess('i', 5);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g6[y] + " ");
    //   }
    //   System.out.println();
    //   String [] g7 = guess('t', 6);
    //   for(int y = 0; y < 5; y++) {
    //     System.out.print(g7[y] + " ");
    //   }
    //   System.out.println();
    // }

}
