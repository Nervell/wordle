public class Word {
  private String word; 

  public Word(String w) {
    word = w;
  }

  public String getFirstLetter() {
    return word.substring(0, 1);
  }

  public String getLetter(int index) {
    return String.valueOf(word.charAt(index));
  }
  public String getSecondLetter() {
    return word.substring(1, 2);
  }

  public String getThirdLetter() {
    return word.substring(2, 3);
  }

  public String getFourthLetter() {
    return word.substring(3, 4);
  }

  public String getFifthLetter() {
    return word.substring(4);
  }

  public boolean contains(String letter) {
    return word.indexOf(letter) >= 0;
  }

  /*public int letterCounter(String letter) {
    int counter = 0;
    for (int i = 0; i < word.length(); i++) {
      if () {
        
      }
    }
  }*/

  public String toString() {
    return this.word;
  }
}