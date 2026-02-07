package sk.patrikscerba.app;

import com.formdev.flatlaf.FlatDarculaLaf;
import sk.patrikscerba.ui.QrKod;

import javax.swing.*;

// Trieda zodpovedá za nastavenie vzhľadu a spustenie hlavného GUI okna
public class QrApp {
    public static void main(String[] args) {

        // Nastavenie moderného vzhľadu aplikácie pomocou knižnice FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("Nepodarilo sa načítať FlatLaf: " + e.getMessage());
        }

        // Spustenie GUI na správnom vlákne (AWT)
        // Zabezpečuje bezpečné vykresľovanie Swing komponentov
        SwingUtilities.invokeLater(() -> {

            // Vytvorenie a zobrazenie hlavného okna aplikácie
            new QrKod().setVisible(true);
        });
    }
}

