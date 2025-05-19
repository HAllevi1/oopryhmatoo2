package org.example.oopryhmatoo2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AndmeHaldur {

    private Map<String, String> nimiParool = new HashMap<>();

    public AndmeHaldur() {
        laeAndmed();
    }

    private Map<String, String> laeAndmed() {
        String ANDMEFAIL = "andmed.txt";
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
}
