package sk.patrikscerba.ui;

import sk.patrikscerba.app.QrUkladanieServis;
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

    private JLabel KrstneMenoLabel;
    private JLabel priezviskoLabel;
    private JLabel telefonneCisloLabel;
    private JLabel emailLabel;
    private JLabel ibanLabel;
    private JLabel poznamkaLabel;
    private JLabel autorLabel;
    private JLabel infoPopisLabel1;
    private JLabel infoPopisLabel2;


    private JLabel qrObrazokLabel;

    private JButton generovatVizitkuButton;
    private JButton generovatTextButton;
    private JButton ulozitQrButton;
    private JButton znovaVygenerovatButton;
    private JPanel contentPanel;
    private JPanel previewPanel;
    private JPanel PopisPanel;


    private final QrUkladanieServis qrUkladanieServis = new QrUkladanieServis();
    private final QrServis qrServis = new QrServis();

    private BufferedImage poslednyQrObrazok;

    // UI okno pre generovanie QR kódov
    public QrKod() {
        setContentPane(mainPanel);
        setTitle("Generovanie QR kódov");
        setSize(900,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        ulozitQrButton.setEnabled(false);
        generovatTextButton.setVisible(true);
        znovaVygenerovatButton.setEnabled(false);
        generovatVizitkuButton.setVisible(true);

        generovatVizitkuButton.addActionListener(e -> vygenerujVizitku());

        znovaVygenerovatButton.addActionListener(e -> vycistitFormular());

        generovatTextButton.addActionListener(e -> vygenerujTextQr());

        ulozitQrButton.addActionListener(e -> {

            if (poslednyQrObrazok == null) return;
            qrUkladanieServis.ulozQr(poslednyQrObrazok);

        });

    }

    // Načíta údaje z formulára a cez QR servis vygeneruje vizitkoví QR kód, ktorý zobrazí v UI
    private void vygenerujVizitku() {
        String meno = jTextKrstneMeno.getText();
        String priezvisko = jTextPriezvisko.getText();
        String telefon = jTextTelefonneCislo.getText();
        String email = jTextEmail.getText();

        try {
            BufferedImage qr = qrServis.vygenerujVizitkuQr(meno, priezvisko, telefon, email);

            // Zobrazenie QR kódu v UI
            qrObrazokLabel.setText(null);
            qrObrazokLabel.setIcon(new ImageIcon(qr));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);
            poslednyQrObrazok = qr;
            generovatVizitkuButton.setVisible(false);
            znovaVygenerovatButton.setEnabled(true);
            generovatTextButton.setVisible(false);
            ulozitQrButton.setEnabled(true);

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

            poslednyQrObrazok = qr;
            qrObrazokLabel.setText(null);
            qrObrazokLabel.setIcon(new ImageIcon(qr));
            qrObrazokLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qrObrazokLabel.setVerticalAlignment(SwingConstants.CENTER);
            generovatVizitkuButton.setVisible(false);
            generovatTextButton.setVisible(false);
            ulozitQrButton.setEnabled(true);
            znovaVygenerovatButton.setEnabled(true);


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
        qrObrazokLabel.setText("- QR -");
        poslednyQrObrazok = null;

        generovatVizitkuButton.setVisible(true);
        znovaVygenerovatButton.setEnabled(false);
        generovatTextButton.setVisible(true);

        ulozitQrButton.setEnabled(false);
    }
}

