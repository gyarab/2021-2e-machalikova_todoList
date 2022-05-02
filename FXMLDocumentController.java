package rp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Třída, která je Controllorem pro všechny fxml soubory v tomto projektu
 *
 * @author Hana Machalíková
 */
public class FXMLDocumentController {

    private static ArrayList<Button> arrSouboru = new ArrayList<>();            //ArrayList Buttonů pro levou - souborovou část okna
    private static ArrayList<Button> arrSeznamu = new ArrayList<>();            //ArrayList Buttonů pro pravou - seznamovou část okna
    private static ArrayList<Button> pomoc = new ArrayList<>();                 //pomocný Arraylist
    private static Boolean aktivni = false;                                     //počítadlo, kolikrát se již kliklo na tlačítka v interaktivní části; false = sudé, true = liché
    //pomocná tlačítka
    private static Button tlacitko = new Button("");
    private static Button zmacknute;

    //deklarace elementů z fxml souborů
    @FXML
    private Button nahrat;                        //tlačítko k náhrání k soubru na úvodní scéně

    @FXML
    private Button ulozitsoubor;                    //tlačítko k uložení levé části splitpanu úvodní scény s názvem soubor

    @FXML
    private Button pridat;                          //tlačítko na hlavní scéně pro přidání noého tlačítka do části Seznam

    @FXML
    private Button ulozitseznam;                    //tlačítko k uložení pravé části splitpanu s názvem seznam

    @FXML
    private VBox vSoubor;                           //VBox pro pro zobrazení ArrayListu arrSoubor

    @FXML
    private VBox vSeznam;                           //VBox pro pro zobrazení ArrayListu arrSeznam

    @FXML
    private TextField nazevS;                       //TextFild pro dialogová okna

    @FXML
    private Button ulozit;                          //tlačítko uložit v dialogovém okně pro uložení souboru

    @FXML
    private Button ulozit2;                         //tlačítk uložit v dialogovém okně pro uložení seznamu

    @FXML
    private Button zpet;                            //tlačítko zpět v dialogovém okně pro uložení souboru

    @FXML
    private Button zpet2;                         //tlačítko zpět v dialogovém okně pro uložení seznamu

    @FXML
    private TextField nacitaniS;                    //TextField do neúspěšného pokusu k dialogovému kn pro nahrání souboru

    @FXML
    private Button ulozit3;                         //tlačítko k neúspěšnému pokusu s dialogovým oknem pro nahrání souboru

    @FXML
    private Button zpet3;                           //tlačítko k neúspěšnému pokusu s dialogovým oknem pro nahrání souboru

    @FXML
    private TextField nazevP;                       //TextField do dialogového okna pro přejmenování proměnné

    @FXML
    private Button prejmenovat;                      //tlačítko v dialogovém okně na přejmenování proměnné

    @FXML
    private Button zpet4;                            //tlačítko zpět v dialogovém okně k přejmenování proměnné

    /**
     * metoda pro přejmenování položky
     *
     * @param event kontrola tlačítka prejmenovat, zda-li bylo již stisknuto
     *
     */
    @FXML
    void Prejmenovat(ActionEvent event) throws IOException {
        tlacitko.setText(nazevP.getText());
        Stage stage = new Stage();
        stage = (Stage) zpet4.getScene().getWindow();
        stage.close();
        if (arrSouboru.contains(tlacitko)) {
            PrepisVBoxu(arrSouboru, vSoubor);
        } else {
            PrepisVBoxu(arrSeznamu, vSeznam);
        }
    }

    /**
     * metoda pro nahrání souboru do SplitPanu na levou stranu - soubor
     *
     * @param event kontrola tlačítka nahrat, zda-li bylo již stisknuto
     * @throws IOException, FileNotFoundException
     *
     */
    @FXML
    void NahratSoubor(ActionEvent event) {
        try {
            vSoubor.getChildren().clear();
            FileReader fr = new FileReader("soubor.txt");
            Scanner sc = new Scanner(fr);
            for (int i = 0; sc.hasNext(); i++) {
                String read = sc.nextLine();
                Button bt = new Button(read);
                bt.setId(i + "");
                SetOnAction(bt);
                arrSouboru.add(bt);
                vSoubor.getChildren().add(bt);
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Soubor nenalezen");
        } catch (IOException ex) {
            System.out.println("Chyba");
        }
    }

    /**
     * metoda pro odstranění tlačítka z aktuální pozice v seznamu pomocí metody
     * OdstranitPolozku
     *
     * @param bt tlačítko, které bude v rámci přesouvání odstraněno z aktuální
     * pozice v seznamu pomocí metody OdstranitPolozku
     *
     */
    void Presun1(Button bt) throws IOException {
        if (arrSouboru.contains(bt)) {
            OdstranitPolozku(arrSouboru, vSoubor, bt);
        } else {
            OdstranitPolozku(arrSeznamu, vSeznam, bt);
        }
    }

    /**
     * metoda pro dokončení přesunu - 2. část tlačítko co je umístěno před
     * tlačítko kam
     *
     * @param event kontrola tlačítka s metodou SetOnAction, zda-li bylo již
     * stisknuto
     * @param co tlačítko, které bude přesunuto
     * @param kam tlačítko, před které bude umístěno co
     *
     */
    @FXML
    void Presun2(ActionEvent event, Button co, Button kam) throws IOException {
        if (arrSeznamu.contains((Button) event.getSource())) {
            VlozitPolozku(arrSeznamu, vSeznam, co, kam);
        } else {
            VlozitPolozku(arrSouboru, vSoubor, co, kam);
        }
        aktivni = false;
    }

    /**
     * metoda pro přidání položky do pravé části SplitPanu - seznam
     *
     * @param event kontrola tlačítka pridat, zda-li bylo již stisknuto
     *
     */
    @FXML
    void PridatPolozku(ActionEvent event) throws IOException {
        Button bt = new Button("");
        SetOnAction(bt);
        vSeznam.getChildren().add(bt);
        arrSeznamu.add(bt);
        bt.setId(arrSeznamu.size() - 1 + "");
    }

    /**
     * metoda pro přejmenování uložení seznamu, které se nachází v pravé části
     * SplitPanu
     *
     * @param event kontrola tlačítka ulozit2, zda-li bylo již stisknuto
     *
     */
    @FXML
    void UlozitSeznam(ActionEvent event) throws IOException {
        Dialog(event, ulozitseznam, zpet2, "napis2.fxml");
        Ulozit(event, ulozit2, arrSeznamu);

    }

    /**
     * metoda pro přejmenování uložení souboru, které se nachází v levé části
     * SplitPanu
     *
     * @param event kontrola tlačítka ulozit, zda-li bylo již stisknuto
     *
     */
    @FXML
    void UlozitSoubor(ActionEvent event) throws IOException {
        Dialog(event, ulozitsoubor, zpet, "napis.fxml");
        Ulozit(event, ulozit, arrSouboru);
    }

    /**
     * metoda, která vykreslí po zadání parametrů dialogové okno s tlačítkem
     * zpět, pomocí kterého se lze dostat zpět na původní scénu
     *
     * @param bt1 tlačítko na původní scéně
     * @param bt2 tlačítko zpět na nové scéně - v dialogovém okně
     * @param fxml String s názvem fxml souboru, kterého je bt2 součástí a které
     * chce uživatel vykreslit
     * @param event kontrola tlačítka , zda-li bylo již stisknuto
     * @throws IOException
     *
     */
    void Dialog(ActionEvent event, Button bt1, Button bt2, String fxml) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == bt1) {
            stage = new Stage();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(bt1.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) bt2.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * metoda pro uložení souboru/seznamu do příslušného souboru, dle toho, co
     * uživatel zadá do Textieldu
     *
     * @param event kontrola tlačítka, zda-li bylo již stisknuto
     * @param arr ArrayList položek, které chceme uložit
     * @param bt tlačítko, ktaré spouští akci
     * @throws IOException
     *
     */
    void Ulozit(ActionEvent event, Button bt, ArrayList<Button> arr) throws IOException {
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

    /**
     * metoda pro odstranění tlačítka na dané pozici dle tlačítkova ID a
     * následné zavolání metody pro přepis VBoxu
     *
     * @param arr ArrayList položek, ze kterého chceme položku odebrat
     * @param vbox vbox propojený s arr
     * @param bt tlačítko, díky jemuž ID získáme pozici v arr, kde ho máme
     * vymazat
     *
     */
    void OdstranitPolozku(ArrayList<Button> arr, VBox vbox, Button bt) throws IOException {
        int pozice = Integer.parseInt(bt.getId());
        arr.remove(pozice);
        PrepisVBoxu(arr, vbox);
    }

    /**
     * metoda pro vložení položky = co na místo před kam a následné cyklické
     * posunutí Arraylistu a vykreslení vboxu
     *
     * @param arr ArrayList položek, kam chceme položku vložit
     * @param vbox spojený s arr
     * @param co tlačítko, které chceme vložit do arr a vboxu
     * @param kam tlačítko reprezentující index, na kterém se má následně
     * nacházet co - co bude před ním
     *
     */
    void VlozitPolozku(ArrayList<Button> arr, VBox vbox, Button co, Button kam) throws IOException {
        int pozice = Integer.parseInt(kam.getId());
        Button k = new Button("");
        k.setId(arr.size() + "");
        arr.add(k);
        Button pred;
        if ((arrSouboru.contains(co) && arrSouboru.contains(kam) && Integer.parseInt(co.getId()) < Integer.parseInt(kam.getId()))
        || (arrSeznamu.contains(co) && arrSeznamu.contains(kam) && Integer.parseInt(co.getId()) < Integer.parseInt(kam.getId()))) {
            for (int i = arr.size() - 1; i >= pozice; i--) {
                pred = arr.get(i - 1);
                arr.set(i, pred);
            }
        } else {
            for (int i = arr.size() - 1; i >= pozice + 1; i--) {
                pred = arr.get(i - 1);
                arr.set(i, pred);
            }
            arr.set(pozice, co);
            PrepisVBoxu(arr, vbox);
        }
    }

    /**
     * metoda pro zapnutí a nastavení setOnAction pro tlačítka, která nejsou
     * definována v fxml souborech
     *
     * @param bt tlačítko, pro které s má setOnAction nastavit
     * @throws IOException
     *
     */
    void SetOnAction(Button bt) throws IOException {
        bt.setOnAction((ActionEvent e) -> {
            Button n = (Button) e.getSource();
            if (!aktivni) {
                tlacitko = n;
                aktivni = true;
            } else {
                if (tlacitko != n) {
                    try {
                        Presun1(tlacitko);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Presun2(e, tlacitko, n);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    aktivni = false;
                } else {
                    zmacknute = n;
                    aktivni = false;
                    try {
                        Okno(e, n, zpet4, "upravaTlacitka.fxml");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * metoda, která vykreslí okno pro přejmenování položky a následně umožní
     * položku přejmenovat
     *
     * @param event kontrola tlačítka, zda-li bylo již stisknuto
     * @param bt1 tlačítko z původní scény
     * @param bt2 tlačítko z dialogového okna
     * @param fxml Sting názvu fxml souboru, který se má vykreslit
     *
     */
    void Okno(ActionEvent event, Button bt1, Button bt2, String fxml) throws IOException {
        Dialog(event, bt1, bt2, fxml);
        if (event.getSource() == prejmenovat) {
            Prejmenovat(event);
        }
    }

    /**
     * metoda k přepsání vboxu a opravení ID tlačítek, aby odpovídaly indexům
     *
     * @param vbox VBox, který chceme překleslit
     * @param arr ArrayList spjatý s vboxem
     *
     */
    void PrepisVBoxu(ArrayList<Button> arr, VBox vbox) throws IOException {
        vbox.getChildren().clear();
        pomoc = (ArrayList<Button>) arr.clone();
        arr.clear();
        for (int a = 0; a < pomoc.size(); a++) {
            Button b = new Button(pomoc.get(a).getText());
            b.setId(a + "");
            SetOnAction(b);
            arr.add(b);
            vbox.getChildren().add(arr.get(a));
        }
    }
    //původní pokus nahrání souboru s pomocí dialogového okna, nepovedlo se mi zprovoznit
    /*@FXML
    void NahratSoubor(ActionEvent event) {
        try {
            Dialog(event, nahrat, zpet3, "napis3.fxml");
            if (event.getSource() == ulozit3) {
                FileReader fr = new FileReader(nacitaniS.getText());
                Scanner sc = new Scanner(fr);
                while (sc.hasNext()) {
                    String read = sc.nextLine();
                    Button bt = new Button(read);
                    vSoubor.getChildren().add(bt);
                    arrSouboru.add(bt);
                    vSeznam.getChildren().add(bt);
                }
                fr.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Soubor nenalezen");
        } catch (IOException ex) {
            System.out.println("Chyba");
        }
    }*/
}
