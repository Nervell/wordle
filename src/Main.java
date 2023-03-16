import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // create ArrayList to store word object
    ArrayList<Word> wordList = new ArrayList<Word>();

    // read text file
    try{
      Scanner s = new Scanner(new File("words.txt"));
      String input = s.nextLine();

      // store the values from text file in ArrayList
      for(int i = 0; i < input.length(); i += 8) {
        wordList.add(new Word(input.substring(i+1, i+6)));
      } // end for loop
    } // end try

    catch(Exception e){
      System.out.println("file not found");
    } // end catch

    // make sure things are proper
    System.out.println(wordList.size() + " total words");
    System.out.println("first word: " + wordList.get(0));
    System.out.println("last word: " + wordList.get(wordList.size()-1));

    /*for (int i = 0; i < wordList.size(); i++) {
      if (!wordList.get(i).contains("r") || !wordList.get(i).contains("e")) {
        wordList.remove(i);
        i--;
      }
    }*/

    //Randomly selected correct word from WordList
    Word correctWord = new Word(String.valueOf(wordList.get((int) (Math.random() * wordList.size()))));
    System.out.println("Letters of correct word " + Arrays.toString(letterSeparator(String.valueOf(correctWord))));


    ArrayList<String> wrongLetters = new ArrayList<String>( );
    ArrayList<String> correctLetters = new ArrayList<String>( );

    //This loop represents your 6 chances to guess a word
    for (int p = 0; p < 6; p++) {
      System.out.print("Enter a word: ");
      String word = scanner.nextLine();
      word = word.toLowerCase();


      //Force you to reenter a word if it's length more or less than 5 letters
      if (word.length() != 5) {
        System.out.println("Your word should have exactly 5 letters" + "\n" + "Enter a word again: ");
        word = scanner.nextLine();
        word = word.toLowerCase();
        p--;
      }


      //check if a word that you have entered had 5 letters
      if (word.length() == 5) {
        System.out.println("");
        System.out.println("\n" + YELLOW + "You have left " + (5 - p) + " attempts" + RESET);
        //determine wrong and correct letters in word that you have entered
        String [] arr = letterSeparator(word);
        for (int i = 0; i < arr.length; i++) {
          if (arr[i].equals(correctWord.getFirstLetter()) || arr[i].equals(correctWord.getSecondLetter()) || arr[i].equals(correctWord.getThirdLetter()) || arr[i].equals(correctWord.getFourthLetter()) || arr[i].equals(correctWord.getFifthLetter())) {
            correctLetters.add(arr[i]);

            for (int jj = 0; jj < correctLetters.size(); jj++) {
              if (!(correctWord.getLetter(i).contains(correctLetters.get(jj)))) {
                for (int k = 0; k < correctLetters.size(); k++) {
                  for (int j = k + 1; j < correctLetters.size(); j++) {
                    if (correctLetters.get(k).equals(correctLetters.get(j))) {
                      correctLetters.remove(k);
                      k--;
                      break;
                    }
                  }
                }
              }
            }
          } else {
            wrongLetters.add(arr[i]);
            //remove repeated letters from array lists of wrong letters
            for (int k = 0; k < wrongLetters.size(); k++) {
              for (int j = k + 1; j < wrongLetters.size(); j++) {
                if (wrongLetters.get(k).equals(wrongLetters.get(j))) {
                  wrongLetters.remove(k);
                  k--;
                  break;
                }
              }
            }
          }
        }

        //create an array thar represent your progress of guessing a word with correct order of letters.
        String[] preWord = {"*","*","*","*","*"};

        if (correctLetters.size() > 0) {
          for (int i = 0; i < preWord.length; i++) {
            if (/*correctWord.getLetter(i).equals(correctLetters.get(j)) && */letterSeparator(word)[i].equals(correctWord.getLetter(i))) {
              preWord[i] = correctLetters.get(String.valueOf(correctLetters).indexOf(letterSeparator(word)[i]));
            }
            /*for (int j = 0; j < correctLetters.size(); j++) {

            }*/
          }
        }

        //removing words from wordList
        annihilator(wordList,wrongLetters, correctLetters, preWord);

        //count how many letters you have guessed
        int counter = 0;
        for (int i = 0; i < preWord.length; i++) {
          if (preWord[i].equals(letterSeparator(String.valueOf(correctWord))[i])) {
            counter++;
          }
        }

        //print useful information
        System.out.println(GREEN + "Correct letters in order that you guess " + Arrays.toString(preWord) + RESET);
        System.out.println(RED + "Wrong letters " + wrongLetters + RESET);
        System.out.println(YELLOW + "Correct letters (not in order) " + correctLetters + RESET);
        System.out.println(BLUE + wordList.size() + " total words" + "\n" + RESET);

        //determine if you win or lose and want to play again
        if (counter == 5) {
          System.out.println("You win!");
          break;
        } else if (p > 4 && counter != 5) {
          System.out.print("You lose! Want to play again?" + "\n" + "Yes or No: ");
          String answer = scanner.nextLine();
          answer = answer.toLowerCase();
          if (answer.equals("yes")) {
            p = 0;
          }
        }
      }
    }

  }//end main method


  //separate word by letters
  public static String[] letterSeparator(String word) {
    String[] letters = new String[word.length()];
    for (int i = 0; i < word.length(); i++) {
      letters[i] = word.substring(i, i + 1);
    }
    return letters;
  }

  public static final String RESET = "\033[0m";  // Text Reset
  public static final String RED = "\033[0;31m";     // RED
  public static final String GREEN = "\033[0;32m";   // GREEN
  public static final String YELLOW = "\033[0;33m";  // YELLOW
  public static final String BLUE = "\033[0;34m";    // BLUE

  //removes wrong words from wordList
  public static ArrayList<Word> annihilator(ArrayList<Word> wordList, ArrayList<String> wrongLetters, ArrayList<String> correctLetters, String[] preWord) {
    for (int i = 0; i < wordList.size(); i++) {
      for (int j = 0; j < preWord.length; j++) {
        if (!(preWord[j].equals("*"))) {
          if (!(letterSeparator(String.valueOf(wordList.get(i)))[j].equals(preWord[j]))) {
            wordList.remove(i);
            i--;
            break;
          }
        }
      }
    }

    for (int i = 0; i < wordList.size(); i++) {
    //remove word if it had a word with wrong letter
      for (int j = 0; j < wrongLetters.size(); j++) {
        if (wordList.get(i).contains(wrongLetters.get(j))) {
          wordList.remove(i);
          i--;
          break;
        }
      }
    }

    return wordList;
  }
}