package blackjack;

import java.util.*;

public class Rank {
   private String name;
   private String symbol;
    
   public final static Rank ACE = new Rank( "Ace", "a" );
   public final static Rank TWO = new Rank( "Two", "2" );
   public final static Rank THREE = new Rank( "Three", "3" );
   public final static Rank FOUR = new Rank( "Four", "4" );
   public final static Rank FIVE = new Rank( "Five", "5" );
   public final static Rank SIX = new Rank( "Six", "6" );
   public final static Rank SEVEN = new Rank( "Seven", "7" );
   public final static Rank EIGHT = new Rank( "Eight", "8" );
   public final static Rank NINE = new Rank( "Nine", "9" );
   public final static Rank TEN = new Rank( "Ten", "t" );
   public final static Rank JACK = new Rank( "Jack", "j" );
   public final static Rank QUEEN = new Rank( "Queen", "q" );
   public final static Rank KING = new Rank( "King", "k" );

   public final static java.util.List VALUES =
      Collections.unmodifiableList( Arrays.asList( new Rank[] { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
                                     EIGHT, NINE, TEN, JACK, QUEEN, KING } ) );
   
   private Rank( String nameValue, String symbolValue ) {
      name = nameValue;
      symbol = symbolValue;
   }

   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return name;
   }

   public String getSymbol() {
      return symbol;
   }
}                                                                 