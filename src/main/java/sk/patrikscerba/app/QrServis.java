package sk.patrikscerba.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;

// Servisná trieda zodpovedná za tvorbu obsahu (vKarta, text) a generovanie QR obrázka
public class QrServis {

    private static final int SIRKA = 300;
    private static final int VYSKA = 300;

    // Vytvorí QR kód typu vizitka (vCard) na základe zadaných údajov
    public BufferedImage vygenerujVizitkuQr(

            String meno,
            String priezvisko,
            String telefonneCislo,
            String email
    ) {

        meno = meno.trim();
        priezvisko = priezvisko.trim();
        telefonneCislo = telefonneCislo.trim();
        email = email.trim();

        if (meno.isEmpty() &&
                priezvisko.isEmpty() &&
                telefonneCislo.isEmpty() &&
                email.isEmpty()) {

            throw new IllegalStateException("Nie sú zadané žiadne údaje pre generovanie QR kódu.");
        }
        String vKarta = vytvorVKartu(meno, priezvisko, telefonneCislo, email);
        return vytvorQrObrazok(vKarta, SIRKA, VYSKA);

    }

    // Vytvorí text vCard formátu zo zadaných kontaktných údajov
    private String vytvorVKartu(String meno, String priezvisko, String telefonCislo, String email) {
        return """
                BEGIN:VCARD
                VERSION:3.0
                N:%s;%s
                FN:%s %s
                TEL:%s
                EMAIL:%s
                END:VCARD
                """.formatted(priezvisko, meno, meno, priezvisko, telefonCislo, email);
    }

    // Vygeneruje QR obrázok zo zadaného textu pomocou knižnice ZXing.
    private BufferedImage vytvorQrObrazok(String text, int SIRKA, int VYSKA) {
        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(text, BarcodeFormat.QR_CODE, SIRKA, VYSKA);

            return MatrixToImageWriter.toBufferedImage(matrix);

        } catch (Exception e) {
            throw new IllegalStateException("Nepodarilo sa vygenerovať QR kód.", e);
        }
    }

    // Pripraví textový obsah QR kódu z rôznych údajov
    public BufferedImage vygenerujTextQr(String meno, String priezvisko, String telefonneCislo, String email, String iban, String poznamka) {

        meno = meno.trim();
        priezvisko = priezvisko.trim();
        telefonneCislo = telefonneCislo.trim();
        email = email.trim();
        iban = iban.trim();
        poznamka = poznamka.trim();

        if (meno.isEmpty() && priezvisko.isEmpty() && telefonneCislo.isEmpty()
                && email.isEmpty() && iban.isEmpty() && poznamka.isEmpty()) {
            throw new IllegalStateException("Nie sú zadané žiadne údaje pre generovanie QR kódu.");
        }

        String text =
                "\nMENO=" + meno +
                        "; \nPRIEZVISKO=" + priezvisko +
                        "; \nTEL=" + telefonneCislo +
                        "; \nEMAIL=" + email +
                        "; \nIBAN=" + iban +
                        "; \nPOZNAMKA=" + poznamka;

        return vytvorQrObrazok(text, SIRKA, VYSKA);
    }
}
