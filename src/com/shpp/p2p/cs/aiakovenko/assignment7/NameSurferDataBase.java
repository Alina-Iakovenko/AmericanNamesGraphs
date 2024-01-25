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

import acm.util.ErrorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NameSurferDataBase implements NameSurferConstants {
    // collection with info from the database file
    ArrayList<NameSurferEntry> namesDataArray;

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) throws IOException {
        BufferedReader br = null;
        this.namesDataArray = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(filename));
            // create instance of bufferedReader to read file
            // create instance of ArrayList to collect info from the file

            while (true) {
                // read all lines while not empty
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                // add info to ArrayList
                namesDataArray.add(new NameSurferEntry(line));
            }

            /* For reference, let's see how many lines there are. */
            System.out.println("Read " + namesDataArray.size() + " names.");
        } catch (IOException e) {
            throw new ErrorException("Something wrong with data file.");
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /* Method: findEntry(name) */

    /**
     * Finds the info for the name in the database
     *
     * @param name string with name to find
     *             (we take it from textField in the class NameSurfer)
     * @return Returns the NameSurferEntry associated with this name,
     * if one exists. If the name does not appear in the database,
     * this method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        /*
         * For every line in dataBase (while name isn't found)
         * check if the name from user is equal to the name in the line
         */
        for (int i = 0; i < namesDataArray.size(); i++) {
            String nameFromDataBase = namesDataArray.get(i).getName().toLowerCase();
            if (nameFromDataBase.equals(name.toLowerCase())) {
                return namesDataArray.get(i);
            }
        }
        // return a message to console
        System.out.println("Sorry - we haven`t info for this name");
        return null;
    }
}

