package sk.patrikscerba.ui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

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


    // Nastavenie okna pre generovanie QR kódov
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

    // Načíta údaje z formulára, vytvorí vCard text, vygeneruje QR a zobrazí ho v UI
    private void vygeneruj() {
        String meno = jTextKrstneMeno.getText().trim();
        String priezvisko = jTextPriezvisko.getText().trim();
        String telefon = jTextTelefonneCislo.getText().trim();
        String email = jTextEmail.getText().trim();
        String iban = jTextIban.getText().trim();
        String poznamka = JTextPoznamka.getText().trim();

        // Overenie, či je aspoň jedno pole vyplnené
        if (meno.isEmpty()
                && priezvisko.isEmpty()
                && telefon.isEmpty()
                && email.isEmpty()
                && iban.isEmpty()
                && poznamka.isEmpty()

        ) {
            JOptionPane.showMessageDialog(this,
                    "Prosím, vyplňte aspoň jedno pole.",
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String vKarta = vytvorVKartu(meno, priezvisko, telefon, email);

        try {
            BufferedImage qrImage = vytvorQrObrazok(vKarta, 300, 300);

            generovatQrButton.setEnabled(false);
            znovaVygenerovatButton.setEnabled(true);

            // Zobrazenie QR kódu v UI
            qrObrazokLabel.setIcon(new ImageIcon(qrImage));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri generovaní QR kódu: " + e.getMessage(),
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Vyčistí formulár pre nové zadanie údajov
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

    // Vytvorí vCard (VERSION 3.0) text z údajov z formulára
    private String vytvorVKartu(String meno, String priezvisko, String telefon, String email) {
        return """
                BEGIN:VCARD
                VERSION:3.0
                N:%s;%s
                FN:%s %s
                TEL:%s
                EMAIL:%s
                END:VCARD
                """.formatted(priezvisko, meno, meno, priezvisko, telefon, email);
    }

    // Vygeneruje QR obrázok zo zadaného textu pomocou knižnice ZXing
    private BufferedImage vytvorQrObrazok(String text, int sirka, int vyska) throws Exception {
        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, sirka, vyska);

        return MatrixToImageWriter.toBufferedImage(matrix);

    }

    // Vygeneruje QR kód zo zadaného textu
    private void vygenerujTextQr() {
        String meno = jTextKrstneMeno.getText().trim();
        String priezvisko = jTextPriezvisko.getText().trim();
        String telefon = jTextTelefonneCislo.getText().trim();
        String email = jTextEmail.getText().trim();
        String iban = jTextIban.getText().trim();
        String poznamka = JTextPoznamka.getText().trim();

        if (meno.isEmpty() && priezvisko.isEmpty() && telefon.isEmpty()
                && email.isEmpty() && iban.isEmpty() && poznamka.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Prosím, vyplňte aspoň jedno pole.",
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String text =
                "\nMENO=" + meno +
                        "; \nPRIEZVISKO=" + priezvisko +
                        "; \nTEL=" + telefon +
                        "; \nEMAIL=" + email +
                        "; \nIBAN=" + iban +
                        "; \nPOZNAMKA=" + poznamka;

        try {
            BufferedImage qrImage = vytvorQrObrazok(text, 350, 350);
            qrObrazokLabel.setIcon(new ImageIcon(qrImage));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Chyba pri generovaní QR: " + e.getMessage(),
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}