package org.example.oopryhmatoo2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AndmeHaldur {

    private Map<String, String> nimiParool = new HashMap<>();
    private static final String ANDMEFAIL = "andmed.txt";

    public AndmeHaldur() {
        laeAndmed();
    }

    private Map<String, String> laeAndmed() {
        String kasutajanimi;
        String parool;
        int punkte;

        try (BufferedReader br = new BufferedReader(new FileReader(ANDMEFAIL))) {
            while (true) {
                kasutajanimi = br.readLine();
                parool = br.readLine();
                if (kasutajanimi == null)
                    break;
                nimiParool.put(kasutajanimi, parool);
            }

        } catch (IOException e) {
            System.err.println("Viga andmete laadimisel failist " + ANDMEFAIL + ": " + e.getMessage());
            // Määrame vaikimisi väärtused, kui faili lugemine ebaõnnestub, et vältida NullPointerExceptionit
            kasutajanimi = "";
            parool = "";
        }
        return nimiParool;
    }

    public boolean kontrolliSisselogimist(String pakutavKasutajanimi, String pakutavParool) throws ValedKasutajaAndmed {
        for (Map.Entry<String, String> stringStringEntry : nimiParool.entrySet()) {
            if (stringStringEntry.getKey().equals(pakutavKasutajanimi) &&
                stringStringEntry.getValue().equals(pakutavParool))
                return true;
        }
        throw new ValedKasutajaAndmed("Vale kasutajanimi või parool.");
    }

    /**
     * Registreerib uue kasutaja.
     * Kontrollib, kas kasutajanimi on juba olemas. Kui ei, siis lisab kasutaja arvuti andmekandjale failina.
     * @param uusKasutajanimi Soovitud kasutajanimi.
     * @param uusParool Soovitud parool.
     * @throws KasutajanimiHoivatudErind Kui kasutajanimi on juba võetud.
     * @throws IOException Kui andmete faili kirjutamisel tekib viga.
     */
    public void registreeriKasutaja(String uusKasutajanimi, String uusParool) throws KasutajanimiHoivatudErind, IOException {
        // Kontrollime, kas kasutajanimi on juba failis
        if (nimiParool.containsKey(uusKasutajanimi)) {
            throw new KasutajanimiHoivatudErind("Kasutajanimi '" + uusKasutajanimi + "' on juba võetud.");
        }

        nimiParool.put(uusKasutajanimi, uusParool);

        // Lisame uue kasutaja andmed faili lõppu
        // Kasutame try-with-resources, et faili kirjutaja kindlasti suletaks
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ANDMEFAIL, true))) { // true tähendab, et lisame faili lõppu (append)
            bw.write(uusKasutajanimi);
            bw.newLine();
            bw.write(uusParool);
            bw.newLine();
        }
    }
}
