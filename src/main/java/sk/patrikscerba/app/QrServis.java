package sk.patrikscerba.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// Servisná trieda zodpovedná za tvorbu obsahu (vKarta, text) a generovanie QR obrázka
public class QrServis {

    private static final int SIRKA = 350;
    private static final int VYSKA = 350;

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

    // Vytvorí text vizitky vo formáte vCard 3.0 zo zadaných údajov
    private String vytvorVKartu(String meno, String priezvisko, String telefonneCislo, String email) {
        return """
                BEGIN:VCARD
                VERSION:3.0
                N:%s;%s
                FN:%s %s
                TEL:%s
                EMAIL:%s
                END:VCARD
                """.formatted(priezvisko, meno, meno, priezvisko, telefonneCislo, email);
    }

    // Vygeneruje QR obrázok zo zadaného textu pomocou knižnice ZXing.
    // - UTF-8 kvôli diakritike
    // - ErrorCorrectionLevel.M = stredná korekcia chýb (QR sa dá načítať aj pri menšom poškodení)
    private BufferedImage vytvorQrObrazok(String text, int sirka, int vyska) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

            // Prevod textu na maticu bodov QR kódu
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(text, BarcodeFormat.QR_CODE, sirka, vyska, hints);

            // Prevod matice na obrázok( BufferedImage) pre UI
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

        // Poskladanie textu do jedného reťazca, ktorý sa zakóduje do QR
        String text =
                "MENO=" + meno +
                        "; \nPRIEZVISKO=" + priezvisko +
                        "; \nTEL=" + telefonneCislo +
                        "; \nEMAIL=" + email +
                        "; \nIBAN=" + iban +
                        "; \nPOZNAMKA=" + poznamka;

        return vytvorQrObrazok(text, SIRKA, VYSKA);
    }
}
