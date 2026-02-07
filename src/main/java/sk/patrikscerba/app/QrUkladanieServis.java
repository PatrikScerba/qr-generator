package sk.patrikscerba.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


// Servisná trieda na uloženie QR obrázka na disk
public class QrUkladanieServis {

    // Priečinok, do ktorého sa uloží QR obrázok
    private static final String QR_PRIECINOK = "qr_obrazok";

    // Konštruktor zabezpečí, že existuje priečinok pre QR obrázky
    public QrUkladanieServis() {

        pripravPriecinok();
    }

    // Vytvorí priečinok pre QR obrázok, ak neexistuje
    private void pripravPriecinok() {
        try {
            Files.createDirectories(Path.of(QR_PRIECINOK));
        } catch (IOException e) {
            throw new IllegalStateException("Chyba pri vytváraní priečinka pre QR kódy.", e);
        }
    }

    // Uloží QR obrázok do priečinka (prepíše existujúci súbor).
    public void ulozQr(BufferedImage qrObrazok) {

        if (qrObrazok == null) {
            throw new IllegalArgumentException("QR obrázok je prázdny (null) – nie je čo uložiť.");
        }

        String nazovSuboru = "qr.png";
        Path cestaKSuboru = Path.of(QR_PRIECINOK, nazovSuboru);

        try {
            ImageIO.write(qrObrazok, "png", cestaKSuboru.toFile());

        } catch (IOException e) {
            throw new IllegalStateException("Chyba pri ukladaní QR obrázka.", e);
        }
    }
}
