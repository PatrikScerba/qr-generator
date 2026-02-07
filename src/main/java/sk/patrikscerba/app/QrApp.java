package sk.patrikscerba.app;

import com.formdev.flatlaf.FlatDarculaLaf;
import sk.patrikscerba.ui.QrKod;

import javax.swing.*;

//
public class QrApp {
    public static void main(String[] args) {

        // Nastavenie moderného vzhľadu FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("Nepodarilo sa načítať FlatLaf: " + e.getMessage());
        }

        // Spustenie GUI na správnom vlákne (AWT)
        SwingUtilities.invokeLater(() -> {
            new QrKod().setVisible(true);
        });
    }
}

