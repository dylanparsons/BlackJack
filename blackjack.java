package blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class blackjack extends Application {
    private final Deck deck = new Deck(1);  
    private final Hand hand = new Hand();  
    private final Hand dealer = new Hand();  
    
    private boolean busted; 
    private boolean playerTurn; 
    
    FlowPane cards = new FlowPane(Orientation.HORIZONTAL);
    FlowPane dealerCards = new FlowPane(Orientation.HORIZONTAL);
    Label totalLabel = new Label();
    Label totalLabelDealer = new Label();
    
    Label dealerLbl = new Label("Dealer Hand"); 
    Label playerLbl = new Label("Your Hand"); 
    
    Label status = new Label();
    Image imageback = new Image("file:resources/table.png");
    
    public void drawCard(Hand hand, FlowPane pane, Label l){
        try{
            Card card = deck.dealCard(); 
            ImageView img = new ImageView(card.getCardImage()); 
            pane.getChildren().add(img); 
            hand.addCard(card); 
            
            int handTotal = hand.evaluateHand(); 
            
            StringBuilder total = new StringBuilder(); 
            if(hand.getSoft() > 0){
                total.append(handTotal-10).append("/");
            }
            total.append(handTotal); 
            l.setText(total.toString()); 
        } catch (Exception ex){
            System.out.println(ex.getMessage()); 
        }
    }
    
    public void newDeck(){
        deck.restoreDeck(); 
        deck.shuffle(); 
        System.out.println("We'ce shuffled the deck"); 
    }
    
    public void newHand(){
        // check if we should shuffle 
        if(deck.getNumberOfCardsRemaining() <= deck.getSizeOfDeck()*0.2){
            newDeck(); 
        }
        
        // clear everything 
        hand.discardHand(); 
        dealer.discardHand(); 
        cards.getChildren().removeAll(cards.getChildren());  
        dealerCards.getChildren().removeAll(dealerCards.getChildren()); 
        totalLabel.setText(""); 
        totalLabelDealer.setText(""); 
        
        busted = false; 
        playerTurn = true; 
        
        // draw cards for the initial hands, player gets 2, dealer 1 
        drawCard(hand, cards, totalLabel); 
        drawCard(dealer, dealerCards, totalLabelDealer); 
        drawCard(hand, cards, totalLabel); 
        
        status.setText("Your turn"); 
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Update all text colors and fonts
        totalLabel.setFont(new Font("Arial", 24));
        totalLabel.setTextFill(Color.web("#FFF"));
        
        totalLabelDealer.setFont(new Font("Arial", 24));
        totalLabelDealer.setTextFill(Color.web("#FFF")); 
        
        status.setTextFill(Color.web("#FFF")); 
        status.setFont(new Font("Arial", 24)); 
        
        dealerLbl.setTextFill(Color.web("#FFF")); 
        dealerLbl.setFont(new Font("Arial", 24)); 
        
        playerLbl.setTextFill(Color.web("#FFF")); 
        playerLbl.setFont(new Font("Arial", 24));
        
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        
        Button drawbtn = new Button();
        drawbtn.setText("Hit");
        drawbtn.setOnAction((e) -> {
            if(playerTurn == true && busted != true){
                drawCard(hand, cards, totalLabel); 

                if(hand.evaluateHand() > 21){
                    // you busted 
                    System.out.println("You've busted"); 
                    busted = true; 
                    playerTurn = false; 
                    status.setText("You've busted"); 
                }
            }
        });
        
        Button standbtn = new Button();
        standbtn.setText("Stand");
        standbtn.setOnAction((e) -> {
            if(playerTurn == true && busted != true){
                playerTurn = false; 
                while(dealer.evaluateHand() < 17){
                    drawCard(dealer, dealerCards, totalLabelDealer);
                } 
                
                int playerTotal = hand.evaluateHand(); 
                int dealerTotal = dealer.evaluateHand(); 
                
                System.out.println("Player Hand: "+hand); 
                System.out.println("Dealer Hand: "+dealer); 

                if(dealerTotal <= 21 && playerTotal == dealerTotal){
                    // tie, push 
                    System.out.println("You've pushed");
                    status.setText("You've pushed"); 
                } else if(dealerTotal <= 21 && playerTotal <= dealerTotal){
                    // you lost 
                    System.out.println("You've lost");  
                    status.setText("You've lost"); 
                } else {
                    // you won 
                    System.out.println("You've won"); 
                    status.setText("You've won"); 
                }
            }
        });
        
        Button newbtn = new Button();
        newbtn.setText("New Hand");
        newbtn.setOnAction((e) -> {
            newHand(); 
        });
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5); 
        
        grid.add(dealerCards, 0, 0, 3, 1); 
        grid.add(dealerLbl, 0, 1);
        grid.add(totalLabelDealer, 1, 1, 2, 1); 
        
        // padding
        Pane p = new Pane(); 
        p.setPrefSize(0, 100); 
        grid.add(p, 0, 2); 
        
        grid.add(cards, 0, 3, 3, 1); 
        grid.add(playerLbl, 0, 4);
        grid.add(totalLabel, 1, 4, 2, 1);  
        grid.add(drawbtn,0,5);
        grid.add(standbtn,1,5);
        grid.add(newbtn, 2, 5); 
        grid.add(status, 0, 6, 3, 1);
        grid.setBackground(background);
        
        Scene scene = new Scene(grid, 1600, 900);
        
        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        newDeck(); 
        newHand(); 
    }
    
    public static void main(String[] args) {
        launch(args);

    }
}
