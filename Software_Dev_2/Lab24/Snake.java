/*
 * Author: Alex Merino, amerino2022@my.fit.edu
 * Course: CSE 1002, Section 03, Fall 2022
 * Project: Snake
*/

// javac -cp .;stddraw.jar Snake.java
// java -cp .;stddraw.jar Snake

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public final class Snake {

   // class variables
   private static Snakey apple; // holds apple cords since Snakey is just x and y
   public static final int CAN_CEN = 25; // center of canvas

   record Snakey (double xCord, double yCord) { // Snakey Class

   }

   public static void main (final String[] args) {
      // initializes main variables (magic number checkstyle)
      final int endDelay = 1500;
      final int scaleS = -2;
      final int scaleB = 52;

      // sets canvas color and size 
      StdDraw.enableDoubleBuffering();
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.setScale(scaleS, scaleB);

      // prepares game for being played
      startSeq();
      drawWall();

      // game runs during this call and returns score
      final int score = game();

      // when game ends game over screen follows
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.text(CAN_CEN, CAN_CEN, "Game Over");
      StdDraw.show();
      StdDraw.pause(endDelay);
      StdDraw.clear(StdDraw.BLACK);
      final String words = "Score: " + score;
      StdDraw.text(CAN_CEN, CAN_CEN, words);
      StdDraw.show();
   }



   public static void startSeq () {
      // goes "Ready? Set GO!" before game starts to prep player
      final int second = 1000;
      StdDraw.setPenColor(StdDraw.WHITE);

      StdDraw.text(CAN_CEN, CAN_CEN, "Ready?");
      StdDraw.show();
      StdDraw.pause(second);

      StdDraw.clear(StdDraw.BLACK);
      StdDraw.text(CAN_CEN, CAN_CEN, "Set");
      StdDraw.show();
      StdDraw.pause(second);

      StdDraw.clear(StdDraw.BLACK);
      StdDraw.text(CAN_CEN, CAN_CEN, "GO!");
      StdDraw.show();
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.pause(second);
   }



   public static void drawWall () {
      // distances and middle points for outer wall
      final double halfW = 26;
      final double halfH = 0.5;
      final double smCord = -0.5;
      final double lgCord = 50.5;

      // draws wall
      StdDraw.filledRectangle(smCord, halfW - 1, halfH, halfW);
      StdDraw.filledRectangle(lgCord, halfW - 1, halfH, halfW);
      StdDraw.filledRectangle(halfW - 1, smCord, halfW, halfH);
      StdDraw.filledRectangle(halfW - 1, lgCord, halfW, halfH);
      StdDraw.show();
   }



   public static int game () {
      // initializes magic numbers
      final int startL = 4;
      final int delay = 60;

      // new arraylist which is the snake
      ArrayList<Snakey> snake = new ArrayList<Snakey>();

      for (int f = 0; f < startL; f++) {
         // snake that players start with
         snake.add(new Snakey(CAN_CEN - f, CAN_CEN));
      }
      boolean playing = true; // changes when game ends
      char direction = 'R'; // starts automatically to the right
      apple = makeAplCords(snake); // creates first apple

      while (playing) {
         // ensures that you cannot go immediately to opposite direction as well as 
         // changes direction
         if ((StdDraw.isKeyPressed(KeyEvent.VK_UP) || StdDraw.isKeyPressed(KeyEvent.VK_W))
               && direction != 'D') {
            direction = 'U';
         }
         if ((StdDraw.isKeyPressed(KeyEvent.VK_LEFT) || StdDraw.isKeyPressed(KeyEvent.VK_A)) 
               && direction != 'R') {
            direction = 'L';
         } 
         if ((StdDraw.isKeyPressed(KeyEvent.VK_DOWN) || StdDraw.isKeyPressed(KeyEvent.VK_S))
               && direction != 'U') {
            direction = 'D';
         }
         if ((StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) || StdDraw.isKeyPressed(KeyEvent.VK_D)) 
               && direction != 'L') {
            direction = 'R';
         }
         snake = moveSnake(snake, direction); // moves snake based on direction pressed
         playing = dead(snake); // checks if snake head touches itself or wall
         StdDraw.pause(delay); // so snake doesnt zoom around
         StdDraw.show();
      }
      return snake.size() - startL; // score is length gained from apples
   }



   public static ArrayList<Snakey> moveSnake (final ArrayList<Snakey> snake, final char dir) {
      // new snake arrayList
      final ArrayList<Snakey> moved = new ArrayList<Snakey>();

      // depending on which way snake moved, head goes one of four ways
      if (dir == 'R') {
         moved.add(new Snakey(snake.get(0).xCord + 1, snake.get(0).yCord));
      } else if (dir == 'L') {
         moved.add(new Snakey(snake.get(0).xCord - 1, snake.get(0).yCord));
      } else if (dir == 'U') {
         moved.add(new Snakey(snake.get(0).xCord, snake.get(0).yCord + 1));
      } else {
         moved.add(new Snakey(snake.get(0).xCord, snake.get(0).yCord - 1));
      }
      
      // the rest of the snake from the previous position gets added on
      for (final Snakey body : snake) {
         moved.add(body);
      }

      final double center = 0.5;

      if (moved.get(0).xCord == apple.xCord && moved.get(0).yCord == apple.yCord) {
         // if the snake head gets apple then create new apple
         apple = makeAplCords(moved);
      } else {  
         // if not then the snake doesn't gro
         // which means last part of snake from last position doesn't exist 
         moved.remove(moved.size() - 1);

         // fills back in the last spot
         StdDraw.setPenColor(StdDraw.BLACK);
         StdDraw.filledSquare(snake.get(snake.size() - 1).xCord - center,
               snake.get(snake.size() - 1).yCord - center, center);
         StdDraw.square(snake.get(snake.size() - 1).xCord - center,
               snake.get(snake.size() - 1).yCord - center, center);
      }

      // paints in new snake
      StdDraw.setPenColor(StdDraw.WHITE);
      for (final Snakey body : moved) {
         StdDraw.filledSquare(body.xCord - center, body.yCord - center, center);
      }
      StdDraw.show();
      return moved; // returns new position of snake

   }



   public static Snakey makeAplCords (final ArrayList<Snakey> snake) {
      // uses board size and rng to create cords for apple 
      final int boardSize = 50;
      final double x = Math.ceil(Math.random() * boardSize);
      final double y = Math.ceil(Math.random() * boardSize);

      // if the cords of the apple are the same as the snake then retry cords
      for (final Snakey part : snake) {
         if (part.xCord == x && part.yCord == y) {
            makeAplCords(snake);
         }
      }

      // make apple red and visible
      final double cent = 0.5;
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.filledSquare(x - cent, y - cent, cent);
      StdDraw.show();
      return new Snakey(x, y);
   }



   public static boolean dead (final ArrayList<Snakey> snake) {
      final int highBord = 50;

      // if the head hits any other part of the snake then the game stops
      for (int d = 1; d < snake.size(); d++) {
         if (snake.get(0).xCord == snake.get(d).xCord
               && snake.get(0).yCord == snake.get(d).yCord) {
            return false;
         }
      }

      // if the head hits any wall then the game stops
      if (snake.get(0).xCord < 0 || snake.get(0).xCord > highBord
            || snake.get(0).yCord < 0 || snake.get(0).yCord > highBord) {
         return false;
      }

      return true; // if neither happen the game continues
   }
}
