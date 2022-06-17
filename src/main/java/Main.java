import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import javax.imageio.ImageIO;

import dao.PersonDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Adresse;
import model.Emailadresse;
import model.Freund;
import model.Geschaeftspartner;
import model.Geschlecht;
import model.Person;
import model.Sprache;

public class Main extends Application {
	
	PersonDAO personDAO;
	private Person aktuellePerson;
	private TextField vornameTextField;
	private TextField nachnameTextField;
	private ImageView bildView;
	private DatePicker geburtsdatumDatePicker;
	private RadioButton maennlichRadioButton;
	private RadioButton weiblichRadioButton;
	private TextField kommentarTextField;
	private TextField strasseHausnummerTextField;
	private TextField plzOrtTextField;
	private ListView<Emailadresse> emailadresseListView;
	private ListView<Sprache> sprachenListView;
	private TextField geschaeftspartnerPositionTextField;
	private TextField freundFreundeskreisTextField;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		personDAO = new PersonDAO();
		
		primaryStage.setTitle("JPA Kursprojekt");
		
		BorderPane border = new BorderPane();
		
		ListView<Person> list = new ListView<>();
		ObservableList<Person> items = loadItemsFromDatabase();
		list.setItems(items);
		
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Gewählt wurde " + list.getSelectionModel().getSelectedItem());
				updateFieldsWithNewPerson (list.getSelectionModel().getSelectedItem());
				
			}
			
		});
		
		border.setLeft(list);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		
		Label vornameLabel = new Label("Vorname");
		grid.add(vornameLabel, 0, 0);
		
		vornameTextField = new TextField();
		grid.add(vornameTextField, 1, 0);
		
		Label nachnameLabel = new Label("Nachname");
		grid.add(nachnameLabel, 0, 1);
		
		nachnameTextField = new TextField();
		grid.add(nachnameTextField, 1, 1);
		
		Label bildLabel = new Label("Passbild");
		grid.add(bildLabel, 0, 2);
		
		// Image bild = new Image("file:maxmustermann.png", 0, 100, true, false);
		bildView = new ImageView();
		// bildView.setImage(bild);
		grid.add(bildView, 1, 2);
		
		FileChooser fileChooser = new FileChooser();
		
		Button neuesBildButton = new Button("Bild wählen");
		
		neuesBildButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					try {
						InputStream is = new BufferedInputStream(new FileInputStream(file));
						Image image = new Image (is,0,100,true, false);
						bildView.setImage(image);				
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		grid.add(neuesBildButton, 1, 3);
		
		Label geburtsdatumLabel = new Label("Geburtsdatum");
		grid.add(geburtsdatumLabel, 0, 4);
		
		geburtsdatumDatePicker = new DatePicker();
		grid.add(geburtsdatumDatePicker, 1, 4);
		
		Label geschlechtLabel = new Label("Geschlecht");
		grid.add(geschlechtLabel, 0, 5);
		
		FlowPane geschlechtPane = new FlowPane();
		
		ToggleGroup toggleGroup = new ToggleGroup();
		
		maennlichRadioButton = new RadioButton("Männlich");
		maennlichRadioButton.setToggleGroup(toggleGroup);
		maennlichRadioButton.setSelected(true);
		maennlichRadioButton.setPadding(new Insets(0,10,0,0));
		
		weiblichRadioButton = new RadioButton("Weiblich");
		weiblichRadioButton.setToggleGroup(toggleGroup);
		
		geschlechtPane.getChildren().addAll(maennlichRadioButton, weiblichRadioButton);
		
		grid.add(geschlechtPane, 1, 5);
		
		Label kommentarLabel = new Label("Kommentar");
		grid.add(kommentarLabel, 0, 6);
		
		kommentarTextField = new TextField();
		grid.add(kommentarTextField, 1, 6);
		
		Label strasseHausnummerLabel = new Label("Str. und Nr.");
		grid.add(strasseHausnummerLabel, 0, 7);
		
		strasseHausnummerTextField = new TextField();
		grid.add(strasseHausnummerTextField, 1, 7);
		
		Label plzOrtLabel = new Label("PLZ und Ort");
		grid.add(plzOrtLabel, 0, 8);
		
		plzOrtTextField = new TextField();
		grid.add(plzOrtTextField, 1, 8);
		
		
		Label emailaddressLabel = new Label("Emails");
		grid.add(emailaddressLabel, 0, 9);
		
		emailadresseListView = new ListView<Emailadresse>();
		grid.add(emailadresseListView, 1, 9);
		
		FlowPane emailaddressModificationButtonFlowPane = new FlowPane();
		
		Button emailadresseHinzufuegenButton = new Button("Emailadresse hinzufügen");
		
		
		emailadresseHinzufuegenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Emailadresse hinzufügen");
				
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Emailadresse hinzufügen");
				dialog.setHeaderText("Die folgende Emailadresse soll hinzugefügt werden.");
				dialog.setContentText("Emailadresse: ");
				
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent())
				{
					aktuellePerson.addEmailadresse(result.get());
					emailadresseListView.getItems().clear();
					emailadresseListView.setItems(aktuellePerson.getEmailadresses());
				}
				
			}
		});
		
		Button emailadresseEntfernenButton = new Button ("Emailadresse entfernen");
		
		emailadresseEntfernenButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Emailadresse löschen");
				aktuellePerson.removeEmailadresse(emailadresseListView.getSelectionModel().getSelectedItem());
				emailadresseListView.getItems().clear();
				emailadresseListView.setItems(aktuellePerson.getEmailadresses());
			}
		});
		
		emailaddressModificationButtonFlowPane.getChildren().addAll(emailadresseHinzufuegenButton, emailadresseEntfernenButton);
		
		grid.add(emailaddressModificationButtonFlowPane, 1, 10);
		
		
		Label spracheLabel = new Label("Sprachen");
		grid.add(spracheLabel, 0, 11);
		
		sprachenListView = new ListView<Sprache>();
		sprachenListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		ObservableList<Sprache> sprachen = FXCollections.observableArrayList(personDAO.findAllSprachen());
		sprachenListView.setItems(sprachen);
		
		grid.add(sprachenListView, 1, 11);
			
		Label geschaeftspartnerPositionLabel = new Label("Position");
		grid.add(geschaeftspartnerPositionLabel, 0, 12);

		geschaeftspartnerPositionTextField = new TextField();
		geschaeftspartnerPositionTextField.setDisable(true);
		grid.add(geschaeftspartnerPositionTextField, 1, 12);
		
		Label freundFreundeskreisLabel = new Label("Kreis");
		grid.add(freundFreundeskreisLabel, 0, 13);

		freundFreundeskreisTextField = new TextField();
		freundFreundeskreisTextField.setDisable(true);
		grid.add(freundFreundeskreisTextField, 1, 13);
		
				
		
		FlowPane buttonPane = new FlowPane();
		
		Button neuGeschaeftspartnerButton = new Button("Neuen Geschäftspartner");
		
		neuGeschaeftspartnerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println ("Neuer Geschaeftspartner anlegen");
				aktuellePerson = new Geschaeftspartner();
				updateFieldsWithNewPerson(aktuellePerson);
			}
		});
		
		Button neuFreundButton = new Button("Neuer Freund");
		
		neuFreundButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println ("Neuer Freund anlegen");
				aktuellePerson = new Freund();
				updateFieldsWithNewPerson(aktuellePerson);
			}
		});
		
		
		Button speicherButton = new Button("Speichere Person");
		
		speicherButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Speichere Person");
				
				updatePersonInDatabaseWithFields();
				list.getItems().clear();
				list.setItems(loadItemsFromDatabase());
			}
		});
		
		Button loeschenButton = new Button("Lösche Person");
		
		loeschenButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Lösche Person");
				personDAO.delete(aktuellePerson.getId());
				list.getItems().clear();
				list.setItems(loadItemsFromDatabase());
			}
		});
		
		
		Button leerenButton = new Button("Leeren");
		leerenButton.setStyle("-fx-base: #FF9D88;");
		
		leerenButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				personDAO.deleteAll();
				list.getItems().clear();
				list.setItems(loadItemsFromDatabase());
			}
		});
		
		
		buttonPane.getChildren().addAll(neuGeschaeftspartnerButton,neuFreundButton,speicherButton,loeschenButton,leerenButton);
		
		grid.add(buttonPane, 0, 14, 2, 1);
		
				
		border.setCenter(grid);
		
		primaryStage.setScene(new Scene(border,770,680));
		primaryStage.show();
		
	}
	
	protected void updatePersonInDatabaseWithFields() {
				
		aktuellePerson.setVorname(vornameTextField.getText());
		aktuellePerson.setNachnamen(nachnameTextField.getText());
				
		if (geburtsdatumDatePicker.getValue() != null) {
			Date geburtsdatum = java.sql.Date.valueOf(geburtsdatumDatePicker.getValue());
			aktuellePerson.setGeburtsdatum(geburtsdatum);
		} else
			aktuellePerson.setGeburtsdatum(null);

		if (maennlichRadioButton.isSelected())
			aktuellePerson.setGeschlecht(Geschlecht.MAENNLICH);
		else
			aktuellePerson.setGeschlecht(Geschlecht.WEIBLICH);
			
		if (bildView.getImage() != null)
			try {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(SwingFXUtils.fromFXImage(bildView.getImage(), null), "png", outputStream);
				aktuellePerson.setPassbild(outputStream.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			aktuellePerson.setPassbild(null);
		
		aktuellePerson.setKommentar(kommentarTextField.getText());
		
		Adresse adresse = new Adresse();
		adresse.setStrasseHausnummer(strasseHausnummerTextField.getText());
		adresse.setPlzOrt(plzOrtTextField.getText());
		
		aktuellePerson.setAdresse(adresse);
		adresse.setPerson(aktuellePerson);
		
		aktuellePerson.clearSprachen();
		for (Sprache sprache : sprachenListView.getSelectionModel().getSelectedItems()) {
			aktuellePerson.addSprache(sprache);
		}	
		
		
		if (aktuellePerson instanceof Geschaeftspartner)
			((Geschaeftspartner)aktuellePerson).setPosition(geschaeftspartnerPositionTextField.getText());
		else
			((Freund)aktuellePerson).setFreundeskreis(freundFreundeskreisTextField.getText());
		
	
		personDAO.persist(aktuellePerson);
		
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		personDAO.shutdown();
	}

	private ObservableList<Person> loadItemsFromDatabase() {
		return FXCollections.observableArrayList(personDAO.findAll());
	}
	
	public void updateFieldsWithNewPerson(Person selectedItem) {
		aktuellePerson = selectedItem;
		
		vornameTextField.setText(aktuellePerson.getVorname());
		nachnameTextField.setText(aktuellePerson.getNachnamen());
		
		if (aktuellePerson.getPassbild() != null)
		{
			Image img = new Image(new ByteArrayInputStream(aktuellePerson.getPassbild(),0,aktuellePerson.getPassbild().length),0,100,true,false);
			bildView.setImage(img);
		}
		else
			bildView.setImage(null);
			
		if (aktuellePerson.getGeburtsdatum() !=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(aktuellePerson.getGeburtsdatum());
			LocalDate date = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
			geburtsdatumDatePicker.setValue(date);
		}
		else
			geburtsdatumDatePicker.setValue(null);
		
		if (aktuellePerson.getGeschlecht() != null) {
		if (aktuellePerson.getGeschlecht()== Geschlecht.MAENNLICH) {
			maennlichRadioButton.setSelected(true);
			weiblichRadioButton.setSelected(false);
		}
		else
		{
			maennlichRadioButton.setSelected(false);
			weiblichRadioButton.setSelected(true);
		} 
		}
		
		kommentarTextField.setText(aktuellePerson.getKommentar());
		
		if (aktuellePerson.getAdresse()!= null)
		{
			strasseHausnummerTextField.setText(aktuellePerson.getAdresse().getStrasseHausnummer());
			plzOrtTextField.setText(aktuellePerson.getAdresse().getPlzOrt());
		}
		
		emailadresseListView.getItems().clear();
		emailadresseListView.setItems(aktuellePerson.getEmailadresses());

		sprachenListView.getSelectionModel().clearSelection();
		
		for (Sprache sprache : aktuellePerson.getSprachen())
			sprachenListView.getSelectionModel().select(sprache);
		
		
		if (aktuellePerson instanceof Geschaeftspartner)
		{
			geschaeftspartnerPositionTextField.setText(((Geschaeftspartner) aktuellePerson).getPosition());
			freundFreundeskreisTextField.setText("");
			geschaeftspartnerPositionTextField.setDisable(false);
			freundFreundeskreisTextField.setDisable(true);
		}
		else
		{
			geschaeftspartnerPositionTextField.setText("");
			freundFreundeskreisTextField.setText(((Freund) aktuellePerson).getFreundeskreis());
			geschaeftspartnerPositionTextField.setDisable(true);
			freundFreundeskreisTextField.setDisable(false);
		}
		
		
		
		
	}
}
