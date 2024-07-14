package controllers;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import models.Player;
import models.Revolver;

public class BaseplateController implements Initializable{

	
		@FXML
	    private Label bulletsShot_label;

	    @FXML
	    private Button check_button;

	    @FXML
	    private ChoiceBox<String> choiceBox_playersCount;

	    @FXML
	    private Label conversation_label;

	    @FXML
	    private Label deadPeople_label;

	    @FXML
	    private Pane gameplayPane;
	    
	    @FXML
	    private Pane informationPane;
	    
	    @FXML
	    private Label informationLabel;
	    
	    @FXML
	    private Button continueButton;

	    @FXML
	    private Label nextPlayerName_label;

	    @FXML
	    private Label playerName_label;

	    @FXML
	    private Button playersConfirmation_button;

	    @FXML
	    private Pane playersCountPane;
	    
	    @FXML
	    private Pane tamborPane;

	    @FXML
	    private Pane playersCustomPane;

	    @FXML
	    private Button shootButton;

	    @FXML
	    private Button spinShootButton;
	    
	    @FXML
	    private Button peekTambor;

	    @FXML
	    private TextField textfield_name;

	    @FXML
	    private Label timesSpinned_label;
	    
	    @FXML
	    private Circle position1;

	    @FXML
	    private Circle position2;

	    @FXML
	    private Circle position3;

	    @FXML
	    private Circle position4;

	    @FXML
	    private Circle position5;

	    @FXML
	    private Circle position6;

    
    //String[] namesList = new String[6];
    String[] namesList;
    Queue<Player> listPlayers = new ArrayDeque<>();
    int contador = 0;
    int bulletsShot = 0;
    int playersDead = 0;
    int timesSpinned = 0;
    Revolver pistol = new Revolver();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		playersCountPane.setVisible(true);
		playersCustomPane.setVisible(false);
		gameplayPane.setVisible(false);
		informationPane.setVisible(false);


		addChoices();
		
		playersConfirmation_button.setOnMouseClicked((event) -> customizarPlayers(choiceBox_playersCount.getValue()));
		
		check_button.setOnMouseClicked((event) -> guardarNom(choiceBox_playersCount.getValue()));
		
		shootButton.setOnMouseClicked((event) -> shot(1));
		spinShootButton.setOnMouseClicked((event) -> shot(2));
		continueButton.setOnMouseClicked((event) -> hideInfoPanel());
		
		peekTambor.setOnMouseClicked((event) -> enseñarTambor());
		
	}
	
	public void actualizarBalas() {
		int c = 0;
		int f = 0;
		position1.setVisible(false);
		position2.setVisible(false);
		position3.setVisible(false);
		position4.setVisible(false);
		position5.setVisible(false);
		position6.setVisible(false);
		
		for (Integer position : pistol.getTambor()) {
			
			c++;
			
			if (position==1) {
				f = c;
			}

			
		}
		
		
		switch (f) {
		case 1:
			position1.setVisible(true);
			break;
		case 2:
			position2.setVisible(true);
			break;
		case 3:
			position3.setVisible(true);
			break;
		case 4:
			position4.setVisible(true);
			break;
		case 5:
			position5.setVisible(true);
			break;
		case 6:
			position6.setVisible(true);
			break;
		default:
			break;
		}
		
		
	}
	
	public void enseñarTambor() {

		if (tamborPane.isVisible()) {
			tamborPane.setVisible(false);

		} else {
			tamborPane.setVisible(true);

		}


		
	}
	
	public void hideInfoPanel() {
		informationPane.setVisible(false);
		
		spinShootButton.setDisable(false);
		shootButton.setDisable(false);
	}
	
	public void customizarPlayers(String choice) {
		int n = Integer.parseInt(choice.substring(0,1));
		
		playersCountPane.setVisible(false);
		playersCustomPane.setVisible(true);
		namesList = new String[n];

	}
	
	public void guardarNom(String choice) {
		
		int n = Integer.parseInt(choice.substring(0,1));

		if (contador >= n-1) {
			
			namesList[contador] = textfield_name.getText();
			playersCountPane.setVisible(false);
			playersCustomPane.setVisible(false);
			gameplayPane.setVisible(true);
			gameplayStart();
			
		} else {

			namesList[contador] = textfield_name.getText();
			textfield_name.clear();
			

			contador++;
			
			String word = null;
			
			switch (contador) {
			case 0:
				word = "one";
				break;
			case 1:
				word = "two";
				break;
			case 2:
				word = "three";
				break;
			case 3:
				word = "four";
				break;
			case 4:
				word = "five";
				break;
			case 5:
				word = "six";
				break;
			}
			
			conversation_label.setText("Good night, visitor "+word+", I need your name to see if you are on the whitelist, or perhaps the blacklist...");
			
		}
		
		
	}
	
	public void gameplayStart(){
		
		for (String string : namesList) {
			Player player = new Player(string);
			listPlayers.add(player);
		}
		 
		updateNames();

	}
	
	public void addChoices() {
		choiceBox_playersCount.getItems().add("2 Players");
		choiceBox_playersCount.getItems().add("3 Players");
		choiceBox_playersCount.getItems().add("4 Players");
		choiceBox_playersCount.getItems().add("5 Players");
		choiceBox_playersCount.getItems().add("6 Players");
	}
	
	public void shot(int choice) {
		tamborPane.setVisible(false);
		actualizarBalas();
		bulletsShot++;
//		for (Integer x : pistol.getTambor()) {
//			System.out.println(x);
//		}
		

		//shot
		if (choice == 1) {
		
			if (pistol.disparo()) {
				//Ha mort
				playersDead++;
				showDeathNotice(listPlayers.poll(), 0);	
				pistol.spinYdisparo();
				
			} else {
				//Torna enrere a la cua
				listPlayers.add(listPlayers.poll());
			}
		
		//spin and shot
		} else {
			timesSpinned++;
			if (pistol.spinAndShoot()) {
				//Ha mort
				playersDead++;
				showDeathNotice(listPlayers.poll(), 0);
				pistol.spinYdisparo();
				
			} else {
				//Torna enrere a la cua
				listPlayers.add(listPlayers.poll());
			}
			
		}
				
		if (listPlayers.size() == 1) {
			
			showDeathNotice(listPlayers.poll(), 1);
			
		} else {
			updateNames();
		}
		
	}
	
	public void showDeathNotice(Player player, int option) {
		
		if (option == 0) {
			informationPane.setVisible(true);
			informationLabel.setText(player.getName().toUpperCase() + " HAS DIED");
		} else {
			informationPane.setVisible(true);
			informationLabel.setText(player.getName().toUpperCase() + " SURVIVED");
			playersCustomPane.setVisible(false);
			gameplayPane.setVisible(false);
			playersCountPane.setVisible(true);

			
		}
		
		spinShootButton.setDisable(true);
		shootButton.setDisable(true);
			
	}
	
	public void updateNames() {
		actualizarBalas();
        // ITERAR sobre una queue hardcore ya te digo
        Iterator<Player> iterator = listPlayers.iterator();
        iterator.next();
        Player secondItem = iterator.next();
        
		playerName_label.setText(listPlayers.peek().getName());
		nextPlayerName_label.setText(secondItem.getName());
		
		bulletsShot_label.setText(bulletsShot+"");
		deadPeople_label.setText(playersDead+"");
		timesSpinned_label.setText(timesSpinned+"");

	}

}