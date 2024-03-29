package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {
    // Name to print info about
    String name;
    // Ranks for this name
    int[] ranks = new int[NDECADES];

	/* Constructor: NameSurferEntry(line) */
    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     *
     * @param line a string from database with name and ranks
     */
    public NameSurferEntry(String line) {
        // separate the line from a database to name and ranks
        String[] nameAndRank = line.split(" ",2);
        this.name = nameAndRank[0];
        for (int i = 0; i < NDECADES; i++) {
            this.ranks[i] = Integer.parseInt(nameAndRank[1].split(" ")[i]);
        }
    }

	/* Method: getName() */
    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return this.name;
    }

	/* Method: getRank(decade) */
    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     *
     * @param decade a number of the decade for which we need rank
     */
    public int getRank(int decade) {
        // take a rank for an asked decade and parse it to int
        return this.ranks[decade];
    }

	/* Method: toString() */
    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" [");
        // print every rank and space after it
        for (int rank : ranks) {
            result.append(rank).append(" ");
        }
        // delete the latest space
        result.deleteCharAt(result.length() - 1);
        result.append("]");

        return result.toString();
    }
}

