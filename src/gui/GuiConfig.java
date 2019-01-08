package gui;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import krizovatka.IKrizovatka;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import krizovatka.IKrizovatka.Smer;
import krizovatka.IKrizovatka.SmerPrujezdu;

/**
 *
 * @author Tomáš Vondra
 */
public class GuiConfig extends Dialog<Object> {

    private TextField cetnostSeverTxtField;
    private TextField cetnostJihTxtField;
    private TextField cetnostZapadTxtField;
    private TextField cetnostVychodTxtField;
    private TextField dobaPrujezduSeverJihTxtField;
    private TextField dobaPrujezduVychodZapadTxtField;
    private TextField dobaOdjezduTxtField;

    private IKrizovatka krizovatka;

    public GuiConfig(IKrizovatka krizovatka) {
        ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(
                okButtonType, ButtonType.CANCEL);

        this.krizovatka = krizovatka;
        setHeaderText("Parametry křižovatky");
        setTitle("Konfigurace");

        GridPane gridPane = createContent();

        getDialogPane().setContent(gridPane);
        fillForm();

        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                setConfig();
            }
            return null;
        });
    }

    private void setConfig() {
        krizovatka.setCetnostPrijezdu(Smer.SEVER,
                Long.valueOf(cetnostSeverTxtField.getText()));
        krizovatka.setCetnostPrijezdu(Smer.JIH,
                Long.valueOf(cetnostJihTxtField.getText()));
        krizovatka.setCetnostPrijezdu(Smer.VYCHOD,
                Long.valueOf(cetnostVychodTxtField.getText()));
        krizovatka.setCetnostPrijezdu(Smer.ZAPAD,
                Long.valueOf(cetnostZapadTxtField.getText()));
        
        krizovatka.setSemaforDobaZelena(SmerPrujezdu.SEVER_JIH,
                Long.valueOf(dobaPrujezduSeverJihTxtField.getText()));
        krizovatka.setSemaforDobaZelena(SmerPrujezdu.VYCHOD_ZAPAD,
                Long.valueOf(dobaPrujezduVychodZapadTxtField.getText()));
        
        krizovatka.setDobaPrujezdu(Long.valueOf(dobaOdjezduTxtField.getText()));
    }

    private void fillForm() {
        cetnostSeverTxtField.setText(
                String.valueOf(
                        krizovatka.getCetnostPrijezdu(IKrizovatka.Smer.SEVER)));
        cetnostJihTxtField.setText(
                String.valueOf(
                        krizovatka.getCetnostPrijezdu(IKrizovatka.Smer.JIH)));
        cetnostZapadTxtField.setText(
                String.valueOf(
                        krizovatka.getCetnostPrijezdu(IKrizovatka.Smer.ZAPAD)));
        cetnostVychodTxtField.setText(
                String.valueOf(
                        krizovatka.getCetnostPrijezdu(IKrizovatka.Smer.VYCHOD)));
        dobaOdjezduTxtField.setText(
                String.valueOf(
                        krizovatka.getDobaPrujezdu()));
        dobaPrujezduSeverJihTxtField.setText(
                String.valueOf(
                        krizovatka.getSemaforDobaZelena(
                                IKrizovatka.SmerPrujezdu.SEVER_JIH)));
        dobaPrujezduVychodZapadTxtField.setText(
                String.valueOf(
                        krizovatka.getSemaforDobaZelena(
                                IKrizovatka.SmerPrujezdu.VYCHOD_ZAPAD)));

    }

    private GridPane createContent() {
        GridPane content = new GridPane();
        content.setVgap(10);
        content.setHgap(10);

        Label cetnostSever = new Label("Cetnost ze severu");
        Label cetnostJih = new Label("Cetnost z jihu");
        Label cetnostZapad = new Label("Cetnost ze zapadu");
        Label cetnostVychod = new Label("Cetnost z vychodu");
        Label dobaPrujezduSeverJih = new Label("Doba prujezdu Sever-Jih");
        Label dobaPrujezduVychodZapad = new Label("Doba prujezdu Vychod-Zapad");
        Label dobaOdjezdu = new Label("Doba odjezdu");

        cetnostSeverTxtField = new TextField();
        cetnostJihTxtField = new TextField();
        cetnostZapadTxtField = new TextField();
        cetnostVychodTxtField = new TextField();
        dobaPrujezduSeverJihTxtField = new TextField();
        dobaPrujezduVychodZapadTxtField = new TextField();
        dobaOdjezduTxtField = new TextField();

        content.add(cetnostSever, 0, 0);
        content.add(cetnostJih, 0, 1);
        content.add(cetnostZapad, 0, 2);
        content.add(cetnostVychod, 0, 3);
        content.add(dobaPrujezduSeverJih, 0, 4);
        content.add(dobaPrujezduVychodZapad, 0, 5);
        content.add(dobaOdjezdu, 0, 6);

        content.add(cetnostSeverTxtField, 1, 0);
        content.add(cetnostJihTxtField, 1, 1);
        content.add(cetnostZapadTxtField, 1, 2);
        content.add(cetnostVychodTxtField, 1, 3);
        content.add(dobaPrujezduSeverJihTxtField, 1, 4);
        content.add(dobaPrujezduVychodZapadTxtField, 1, 5);
        content.add(dobaOdjezduTxtField, 1, 6);

        for (int i = 0; i < 4; i++) {
            Label cetnost = new Label("Aut/min");
            content.add(cetnost, 2, i);
        }
        for (int i = 4; i < 7; i++) {
            Label doba = new Label("ms");
            content.add(doba, 2, i);
        }

        return content;
    }

}
