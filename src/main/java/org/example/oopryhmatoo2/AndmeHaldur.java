package org.example.oopryhmatoo2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AndmeHaldur {

    private static final String ANDMEFAIL = "andmed.txt";
    private String oigeKasutajanimi;
    private String oigeParool;

    public AndmeHaldur() {
        laeAndmed();
    }

    private void laeAndmed() {
        try (BufferedReader br = new BufferedReader(new FileReader(ANDMEFAIL))) {
            oigeKasutajanimi = br.readLine();
            oigeParool = br.readLine();
        } catch (IOException e) {
            System.err.println("Viga andmete laadimisel failist " + ANDMEFAIL + ": " + e.getMessage());
            // Määrame vaikimisi väärtused, kui faili lugemine ebaõnnestub, et vältida NullPointerExceptionit
            oigeKasutajanimi = "";
            oigeParool = "";
        }
    }

    public boolean kontrolliSisselogimist(String kasutajanimi, String parool) throws ValedKasutajaAndmed {
        if (oigeKasutajanimi != null && oigeKasutajanimi.equals(kasutajanimi) &&
            oigeParool != null && oigeParool.equals(parool)) {
            return true;
        }
        throw new ValedKasutajaAndmed("Vale kasutajanimi või parool.");
    }
}
