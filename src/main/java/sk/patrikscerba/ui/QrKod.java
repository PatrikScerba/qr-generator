package sk.patrikscerba.ui;

import sk.patrikscerba.app.QrServis;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class QrKod extends JFrame {


    private JPanel mainPanel;
    private JTextField jTextKrstneMeno;
    private JTextField jTextPriezvisko;
    private JTextField jTextTelefonneCislo;
    private JTextField jTextEmail;
    private JTextField jTextIban;
    private JTextField JTextPoznamka;

    private JLabel krstneMenoLabel;
    private JLabel priezviskoLabel;
    private JLabel telefonneCisloLabel;
    private JLabel emailLabel;
    private JLabel ibanLabel;
    private JLabel poznamkaLabel;

    private JLabel qrObrazokLabel;

    private JButton generovatQrButton;
    private JButton generovatTextButton;
    private JButton ulozitQrButton;
    private JButton znovaVygenerovatButton;

    private final QrServis qrServis = new QrServis();

    // UI okno pre generovanie QR kódov
    public QrKod() {
        setContentPane(mainPanel);
        setTitle("QR generovanie");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        generovatQrButton.addActionListener(e -> vygeneruj());

        znovaVygenerovatButton.addActionListener(e -> vycistitFormular());

        generovatTextButton.addActionListener(e -> vygenerujTextQr());

    }

    // Načíta údaje z formulára a cez QR servis vygeneruje vizitkoví QR kód, ktorý zobrazí v UI
    private void vygeneruj() {
        String meno = jTextKrstneMeno.getText();
        String priezvisko = jTextPriezvisko.getText();
        String telefon = jTextTelefonneCislo.getText();
        String email = jTextEmail.getText();

        try {
            BufferedImage qr = qrServis.vygenerujVizitkuQr(meno, priezvisko, telefon, email);

            generovatQrButton.setEnabled(false);
            znovaVygenerovatButton.setEnabled(true);

            // Zobrazenie QR kódu v UI
            qrObrazokLabel.setIcon(new ImageIcon(qr));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri generovaní QR kódu: " + e.getMessage(),
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Načíta údaje z formulára a cez QR servis vygeneruje textový QR kód, ktorý zobrazí v UI
    private void vygenerujTextQr() {

        String meno = jTextKrstneMeno.getText();
        String priezvisko = jTextPriezvisko.getText();
        String telefon = jTextTelefonneCislo.getText();
        String email = jTextEmail.getText();
        String iban = jTextIban.getText();
        String poznamka = JTextPoznamka.getText();


        try {
            BufferedImage qr = qrServis.vygenerujTextQr(meno, priezvisko, telefon, email, iban, poznamka);
            qrObrazokLabel.setIcon(new ImageIcon(qr));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri generovaní QR: " + e.getMessage(),
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Vyčistí všetky vstupné polia formulára a anuluje zobrazenie QR kódu.
    private void vycistitFormular() {
        jTextKrstneMeno.setText("");
        jTextPriezvisko.setText("");
        jTextTelefonneCislo.setText("");
        jTextEmail.setText("");
        jTextIban.setText("");
        JTextPoznamka.setText("");

        qrObrazokLabel.setIcon(null);

        generovatQrButton.setEnabled(true);
        znovaVygenerovatButton.setEnabled(true);
    }
}
