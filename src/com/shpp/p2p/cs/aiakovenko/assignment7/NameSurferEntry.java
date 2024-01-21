package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import com.sun.source.doctree.LiteralTree;

import java.util.Arrays;

public class NameSurferEntry implements NameSurferConstants {
    String name;
    String[] ranks;


	/* Constructor: NameSurferEntry(line) */
    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        String[] nameAndRank = line.split(" ",2);
        this.name = nameAndRank[0];
        this.ranks = nameAndRank[1].split(" ");
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
     */
    public int getRank(int decade) {
        return Integer.parseInt(this.ranks[decade]);
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
        for (String rank : ranks) {
            result.append(rank).append(" ");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");

        return result.toString();
    }
}

