package gui;

import casovani.Casovac;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kolekce.KolekceException;
import krizovatka.IKrizovatka;
import krizovatka.IKrizovatka.Smer;
import krizovatka.KrizovatkaSimulator;

/**
 *
 * @author user
 */
public class GuiMainSimulaceKrizovatky extends Application {

    private IKrizovatka krizovatka;
    private ListView<String> list;
    private HBox choiceBox;
    private GridPane menuGridPane;
    private boolean running; //Pomocna na osetreni bugu

    TextField autaZeSeveruTxtField;
    TextField autaZJihuTxtField;
    TextField autaZVychoduTxtField;
    TextField autaZeZapaduTxtField;
    TextField semaforTxtField;

    @Override
    public void start(Stage primaryStage) throws KolekceException {
        running = false;
        krizovatka = new KrizovatkaSimulator();
        nastavHlaseni();

        BorderPane root = new BorderPane();
        root.paddingProperty().set(new Insets(5));

        Scene scene = new Scene(root, 700, 500);

        menuGridPane = createMenu();
        list = createListView(350, 400);

        root.setLeft(list);
        root.setRight(menuGridPane);
        primaryStage.setTitle("Simulace křižovatky - Vondra Tomáš");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void nastavHlaseni() {
        krizovatka.setHlaseniPrijezduZeSmeru(
                IKrizovatka.Smer.SEVER,
                s -> {
                    addToListView("Prijezd ze severu " + s);
                    autaZeSeveruTxtField.setText(
                            String.valueOf(
                                    krizovatka.getPocetCekajicichZeSmeru(
                                            Smer.SEVER)));
                }
        );
        krizovatka.setHlaseniOdjezduZeSmeru(
                IKrizovatka.Smer.SEVER,
                s -> {
                    addToListView("Odjezd ze severu " + s);
                });

        krizovatka.setHlaseniPrijezduZeSmeru(
                IKrizovatka.Smer.JIH,
                s -> {
                    addToListView("Prijezd z jihu " + s);
                    autaZJihuTxtField.setText(
                            String.valueOf(
                                    krizovatka.getPocetCekajicichZeSmeru(
                                            Smer.JIH)));
                });
        krizovatka.setHlaseniOdjezduZeSmeru(
                IKrizovatka.Smer.JIH,
                s -> {
                    addToListView("Odjezd z jihu " + s);
                });

        krizovatka.setHlaseniPrijezduZeSmeru(
                IKrizovatka.Smer.ZAPAD,
                s -> {
                    addToListView("Prijezd ze zapadu " + s);
                    autaZeZapaduTxtField.setText(
                            String.valueOf(
                                    krizovatka.getPocetCekajicichZeSmeru(
                                            Smer.ZAPAD)));
                });
        krizovatka.setHlaseniOdjezduZeSmeru(
                IKrizovatka.Smer.ZAPAD,
                s -> {
                    addToListView("Odjezd ze zapadu " + s);
                });

        krizovatka.setHlaseniPrijezduZeSmeru(
                IKrizovatka.Smer.VYCHOD,
                s -> {
                    addToListView("Prijezd z vychodu " + s);
                    autaZVychoduTxtField.setText(
                            String.valueOf(
                                    krizovatka.getPocetCekajicichZeSmeru(
                                            Smer.VYCHOD)));
                });
        krizovatka.setHlaseniOdjezduZeSmeru(
                IKrizovatka.Smer.VYCHOD,
                s -> {
                    addToListView("Odjezd z vychodu " + s);
                });

        krizovatka.setHlaseniSemaforu(
                s -> {
                    addToListView("---Prepnuti semaforu---");
                    semaforTxtField.setText(s.toString());
                });
    }

    private void addToListView(String text) {

        Platform.runLater(()
                -> {
            if (list.getItems().size() >= 50) {
                list.getItems().remove(0);
            }
            list.getItems().add(
                    String.format("Cas: %05.1f\t%s", getTime(), text));
        });

    }

    private double getTime() {
        double output = 0;
        try {
            output = (Casovac.instance().getTime() / 1000); //Sekundy
        } catch (KolekceException ex) {
            Logger.getLogger(GuiMainSimulaceKrizovatky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    private ListView createListView(int width, int height) {
        ListView temp = new ListView();
        temp.setPrefWidth(width);
        temp.setPrefHeight(height);

        return temp;
    }

    private HBox createChoiceBox() {
        HBox temp = new HBox(8);
        Button configButton = new Button("Konfigurace");
        configButton.setOnAction((s) -> {
            GuiConfig config = new GuiConfig(krizovatka);
            krizovatka.pause();
            config.showAndWait();
            if (running) {
                krizovatka.start();
            }
        });
        Button startButton = new Button("Start");
        startButton.setOnAction((s) -> {
            krizovatka.start();
            running = true;
        });
        Button stopButton = new Button("Stop");
        stopButton.setOnAction((s) -> {
            krizovatka.pause();
            running = false;
        });
        Button clearButton = new Button("Vycisti");
        clearButton.setOnAction((s) -> list.getItems().clear());

        temp.getChildren().addAll(configButton, startButton, stopButton,
                clearButton);

        return temp;
    }

    private GridPane createMenu() {
        GridPane menu = new GridPane();
        menu.setHgap(10);
        menu.setVgap(10);

        Label autaZeSeveruLabel = new Label("Auta ze severu");
        Label autaZJihuLabel = new Label("Auta z jihu");
        Label autaZVychoduLabel = new Label("Auta z vychodu");
        Label autaZeZapaduLabel = new Label("Auta ze zapadu");
        Label semaforLabel = new Label("Semafor: ");

        autaZeSeveruTxtField = new TextField();
        autaZJihuTxtField = new TextField();
        autaZVychoduTxtField = new TextField();
        autaZeZapaduTxtField = new TextField();
        semaforTxtField = new TextField(); //TODO Ošetření, pouze na zobrazení

        menu.add(autaZeSeveruLabel, 0, 0);
        menu.add(autaZVychoduLabel, 0, 1);
        menu.add(autaZJihuLabel, 0, 2);
        menu.add(autaZeZapaduLabel, 0, 3);
        menu.add(semaforLabel, 0, 4);

        menu.add(autaZeSeveruTxtField, 1, 0);
        menu.add(autaZVychoduTxtField, 1, 1);
        menu.add(autaZJihuTxtField, 1, 2);
        menu.add(autaZeZapaduTxtField, 1, 3);
        menu.add(semaforTxtField, 1, 4);

        choiceBox = createChoiceBox();

        menu.add(choiceBox, 0, 5, 2, 1);

        return menu;
    }

}
