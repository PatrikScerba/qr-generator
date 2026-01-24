package sk.patrikscerba.ui;

import javax.swing.*;

public class QrKod extends JFrame {

    private JPanel mainPanel;
    private JTextField jTextKrstneMeno;
    private JTextField jTextPriezvisko;
    private JTextField jTextTelefonneCislo;
    private JTextField jTextEmail;

    private JLabel krstneMenoLabel;
    private JLabel priezviskoLabel;
    private JLabel telefonneCisloLabel;
    private JLabel emailLabel;

    private JLabel qrObrazokLabel;

    private JButton generovatQrButton;
    private JButton ulozitQrButton;
    private JButton znovaVygenerovatButton;


    public QrKod() {
        setContentPane(mainPanel);
        setTitle("QR generovanie");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generovatQrButton.addActionListener(e -> vygeneruj());

    }

    // Príprava vCard textu (QR generovanie príde v ďalšom kroku)
    private void vygeneruj() {
        String meno = jTextKrstneMeno.getText().trim();
        String priezvisko = jTextPriezvisko.getText().trim();
        String telefon = jTextTelefonneCislo.getText().trim();
        String email = jTextEmail.getText().trim();

        // Overenie, či je aspoň jedno pole vyplnené
        if (meno.isEmpty()
                && priezvisko.isEmpty()
                && telefon.isEmpty()
                && email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Prosím, vyplňte aspoň jedno pole.",
                    "Chyba",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String vKarta = vytvorVKartu(meno, priezvisko, telefon, email);
        System.out.println(vKarta);
    }

    // Vytvorenie vCard formátu
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
}
