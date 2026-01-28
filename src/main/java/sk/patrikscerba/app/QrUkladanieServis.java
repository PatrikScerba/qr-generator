package sk.patrikscerba.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


// Servisná trieda na uloženie QR obrázka na disk
public class QrUkladanieServis {

    // Priečinok, do ktorého sa uloží QR obrázok
    private static final String QR_SUBOR = "qr_obrazok";

    // Konštruktor zabezpečí, že existuje priečinok pre QR kódy
    public QrUkladanieServis() {

        pripravPriecinok();
    }

    // Vytvorí priečinok pre QR kód, ak neexistuje
    private void pripravPriecinok() {
        try {
            Files.createDirectories(Path.of(QR_SUBOR));
        } catch (Exception e) {
            throw new IllegalStateException("Chyba pri vytváraní priečinka pre QR kódy.", e);
        }
    }

    // Uloží QR obrázok do priečinka (prepíše existujúci súbor).
    public void ulozQr(BufferedImage qrObrazok) {

        String nazovSuboru = "qr.png";
        Path cestaKSuboru = Path.of(QR_SUBOR, nazovSuboru);

        try {
            ImageIO.write(qrObrazok, "png", cestaKSuboru.toFile());

        } catch (IOException e) {
            throw new IllegalStateException("Chyba pri ukladaní QR obrázka.", e);
        }
    }
}
