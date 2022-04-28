package rp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLDocumentController {

    private static ArrayList<Button> arrSouboru = new ArrayList<>();
    private static ArrayList<Button> arrSeznamu = new ArrayList<>();

    @FXML
    private Button nahrat;

    @FXML
    private Button zpet2;

    @FXML
    private Button ulozitsoubor;

    @FXML
    private Button pridat;

    @FXML
    private Button ulozitseznam;

    @FXML
    private GridPane gridSoubor = new GridPane();

    @FXML
    private GridPane gridSeznam = new GridPane();

    @FXML
    private TextField nazevS;

    @FXML
    private Button ulozit;

    @FXML
    private Button ulozit2;

    @FXML
    private Button zpet;

    @FXML
    private TextField nacitaniS;

    @FXML
    private Button ulozit3;

    @FXML
    private Button zpet3;
    @FXML
    void NahratSoubor(ActionEvent event) throws IOException {
        try {
            Dialog(event, nahrat, zpet3, "napis3.fxml");
            Stage stage = new Stage();
            if (event.getSource() == ulozit3) {
                FileReader fr = new FileReader(nacitaniS.getText());
                stage = (Stage) zpet3.getScene().getWindow();
                stage.close();
                Scanner sc = new Scanner(fr);
                while (sc.hasNext()) {
                    String read = sc.nextLine();
                    Button bt = new Button(read);
                    arrSouboru.add(bt);
                    System.out.println(read);
                }
                System.out.println("wehi"); 
 //               gridSoubor.add(new Button("12"), 0, 0);
                System.out.println(arrSouboru.size());
                for (int x = 0; x < arrSouboru.size(); x++) {
                    Button b = new Button(arrSouboru.get(x).getText());
                    gridSoubor.add(b, 0, x);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Soubor nenalezen");
        } catch (IOException ex) {
            System.out.println("Chyba");
        }
    }

    @FXML
    void PridatPolozku(ActionEvent event) {
        Button bt = new Button("");
        gridSeznam.getChildren().add(bt);
        arrSeznamu.add(bt);

    }

    @FXML
    void UlozitSeznam(ActionEvent event) throws IOException {
        Dialog(event, ulozitseznam, zpet2, "napis2.fxml");
        Ulozit(event, ulozit2, arrSeznamu);

    }

    @FXML
    void UlozitSoubor(ActionEvent event) throws IOException {
        Dialog(event, ulozitsoubor, zpet, "napis.fxml");
        Ulozit(event, ulozit, arrSouboru);
    }

    void Dialog(ActionEvent event, Button bt1, Button bt2, String fxml) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == bt1) {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource(fxml));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(bt1.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) bt2.getScene().getWindow();
            stage.close();
        }
    }

    void Ulozit(ActionEvent event, Button bt, ArrayList<Button> arr) throws IOException {
        Stage stage = new Stage();
        if (event.getSource() == bt) {
            File file = new File(nazevS.getText());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(file));
            if (file.createNewFile()) {
                bw2.newLine();
            }
            for (int i = 0; i < arr.size(); i++) {
                bw.write(arr.get(i).getText());
                bw.newLine();
            }
            bw.flush();
        }
    }

}
