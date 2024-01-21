package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.graphics.GLabel;
import acm.util.ErrorException;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NameSurferDataBase implements NameSurferConstants {
    ArrayList<String> namesDataArray;

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(NAMES_DATA_FILE));
            this.namesDataArray = new ArrayList<>();

            while (true) {
                String word = br.readLine();
                if (word == null) {
                    break;
                }
                namesDataArray.add(word);
            }
            br.close();
            /* For reference, let's see how many words there are. */
            System.out.println("Read " + namesDataArray.size() + " names.");
        } catch (IOException e) {
            System.out.println("Something wrong with data file.");
            throw new ErrorException(e);
        }
    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        NameSurferEntry infoForName;
        for (int i = 0; i < namesDataArray.size(); i++) {
            infoForName = new NameSurferEntry(namesDataArray.get(i));
            if (infoForName.getName().toLowerCase().equals(name.toLowerCase())) {
                return infoForName;
            }
        }
        System.out.println("Sorry - we haven`t info for this name");
        return null;
    }
}

