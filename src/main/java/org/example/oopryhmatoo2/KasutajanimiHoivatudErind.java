package org.example.oopryhmatoo2;

/**
 * Erind, mis visatakse, kui proovitakse registreerida kasutajanimega, mis on juba võetud.
 */
public class KasutajanimiHoivatudErind extends Exception {
    public KasutajanimiHoivatudErind(String message) {
        super(message);
    }
} 