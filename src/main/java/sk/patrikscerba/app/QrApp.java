package sk.patrikscerba.app;


import sk.patrikscerba.ui.QrKod;

import javax.swing.*;

public class QrApp {
    public static void main(String[] args) {
        // Spustenie GUI na správnom vlákne (AWT)
        SwingUtilities.invokeLater(() -> {
            new QrKod().setVisible(true);
        });
    }
}

