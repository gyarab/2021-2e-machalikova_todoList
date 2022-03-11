/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rp;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import java.util.Scanner;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author Admin
 */

public class FXMLDocumentController {
    private static String[] soubor;
    private static String[] seznam;
    
    @FXML
    private static VBox v_soubor;

    
    @FXML
    public void nahratSoubor(MouseEvent event) {
        try {
            FileReader fr = new FileReader("seznam.txt");
            Scanner sc = new Scanner(fr);
            String read = sc.nextLine();
            for (int i = 0; read != null; i++) {
                read = sc.nextLine();
                v_soubor.getChildren().add(new Button(read));
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Soubor nenalezen");
        } catch (IOException ex) {
            System.out.println("Chyba");
        }
        
    }

    @FXML
    void novaPolozka(MouseEvent event) {

    }

    @FXML
    void ulozitSeznam(MouseEvent event) {

    }

    @FXML
    void ulozitSoubor(MouseEvent event) {
        
    }
    

}

