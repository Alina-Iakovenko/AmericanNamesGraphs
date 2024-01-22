package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {
    // The horizontal line on the top
    GLine topLine;
    // The horizontal line on the bottom
    GLine bottomLine;
    // Vertical lines for every decade
    final GLine[] decadeLines = new GLine[NDECADES];
    // Text with a year of beginning for every decade
    final GLabel[] decadeLabels = new GLabel[NDECADES];

    // Collection for names from user to print
    final LinkedHashMap<String, int[]> entryDataSet = new LinkedHashMap<>();
    // Collection for all labels to print
    LinkedHashMap<String, GLabel[]> labels = new LinkedHashMap<>();
    // Collection for all lines to print
    LinkedHashMap<String, GLine[]> graphs = new LinkedHashMap<>();
    JButton noNameButton;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        update();
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        if (entryDataSet != null) {
            // method from the class LinkedHashMap to clear the collection
            entryDataSet.clear();
        }
    }

    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        if (entry == null) {
            // if there is no name - create button with this info
            createNoNameButton();
        } else {
            // Get name with the method from the NameSurferEntry class
            String name = entry.getName();
            // Create an array for ranks for the name
            int[] ranks = new int[NDECADES];

            // Get ranks for the name with the method from the NameSurferEntry class
            for (int i = 0; i < NDECADES; i++) {
                ranks[i] = entry.getRank(i);
            }
            // Add info to LinkedHashMap if there isn't the same
            entryDataSet.putIfAbsent(name, ranks);
        }
    }

    /**
     *  Create a button with information for when no match is found for the name.
     **/
    private void createNoNameButton() {
        // create button with text formatted with HTML rules
        noNameButton = new JButton("<html><center>No match with this name found.<br><font size='2'>Click the button to close it and proceed</font></center></html>");
        noNameButton.setFont(new Font("Arial", Font.BOLD, 20));
        noNameButton.setForeground(Color.RED);

        // add actionListener to be able to close the button using anonymous interface implementation
        noNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(noNameButton);
                noNameButton = null;
            }
        });

//        // previous piece of code can be simplified with lambda:
//        noNameButton.addActionListener(e -> {
//            remove(noNameButton);
//            noNameButton = null;
//        });
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        /* Print to horizontal lines (there will be graphs between them)
         * and vertical lines for each decade and year of the beginning
         */
        createDecadesTable();
        // Print info for names (graphs and name with rank for every decade)
        createGraphsOnDecadesTable();
        if (noNameButton != null) {
            add(noNameButton);
            noNameButton.setLocation(getWidth() / 2 - noNameButton.getWidth() / 2, getHeight() / 2 - noNameButton.getHeight() / 2);

        }
    }

    /**
     * Print or reprint vertical lines for each decade
     * and year of the beginning according to window size
     */
    private void createDecadesTable() {
        // calculate the distance between vertical lines according to actual window width
        double distanceBetweenDecades = (double) getWidth() / NDECADES;
        // Create horizontal top line
        topLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        topLine.setColor(Color.BLACK);
        add(topLine);

        // If there is a bottom line - remove it
        if (bottomLine != null) {
            remove(bottomLine);
        }
        // and then create a new one in the appropriate place and size
        bottomLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        bottomLine.setColor(Color.BLACK);
        add(bottomLine);

        // for every decade line
        for (int i = 0; i < NDECADES; i++) {
            // if there is a decade line in the window, delete it
            if (decadeLines[i] != null) {
                remove(decadeLines[i]);
            }
            // and create a new one
            decadeLines[i] = new GLine(i * distanceBetweenDecades + 2, 0.0, i * distanceBetweenDecades + 2, getHeight());
            decadeLines[i].setColor(Color.BLACK);
            add(decadeLines[i]);

            // and the same process for decade labels
            if (decadeLabels[i] != null) {
                remove(decadeLabels[i]);
            }
            String textForLabel = Integer.toString(START_DECADE + i * 10);
            decadeLabels[i] = new GLabel(textForLabel, i * distanceBetweenDecades + 3, getHeight() - 2);
            add(decadeLabels[i]);
        }
    }

    /**
     * Print or reprint graphs and text info for it
     * (name and rank in the decade) for every name chose by user
     */
    private void createGraphsOnDecadesTable() {
        // if there is info on the table - delete it
        if (!(labels.isEmpty()) || !(graphs.isEmpty())) {
            clearTheTable();
        }
        // and create actual in actual sizes
        createInstancesForDisplay();

        // and add it in the window if there is something to add
        if (!(labels.isEmpty()) || !(graphs.isEmpty())) {
            displayInWindow();
        }
    }

    /***
     * Clear the decade table from all info about names
     */
    private void clearTheTable() {
        // remove every graph`s line
        for (Map.Entry<String, GLine[]> entry : graphs.entrySet()) {
            String keyName = entry.getKey();
            for (GLine gLine : graphs.get(keyName)) {
                remove(gLine);
            }
        }
        graphs.clear(); // and empty collection from info

        // remove every name`s label
        for (Map.Entry<String, GLabel[]> entry : labels.entrySet()) {
            String keyName = entry.getKey();
            for (GLabel gLabel : labels.get(keyName)) {
                remove(gLabel);
            }
        }
        labels.clear();// and empty collection from info
    }

    /***
     * Create all labels and lines for every name to display with the appropriate color
     */
    private void createInstancesForDisplay() {
        // set index for the color for the first name
        int colorIndex = 0;

        // create instances for every name's label and graphs line for all names to print
        labels = new LinkedHashMap<>();
        graphs = new LinkedHashMap<>();

        // get every name to print
        Set<String> keys = entryDataSet.keySet();
        // for every name
        for (String key : keys) {
            // Calculate and create y-coordinates for points on the graph
            double[] yCoordinates = getYCoordinates(key);
            // create array for labels
            GLabel[] labelsForName = createLabelsForName(key, yCoordinates, colorIndex);
            // and lines
            GLine[] linesForNamesGraph = createGraphsLinesForName(yCoordinates, colorIndex);

            // add info to the collection if there wasn't the same
            labels.putIfAbsent(key, labelsForName);
            graphs.putIfAbsent(key, linesForNamesGraph);

            // change colorIndex for next name
            colorIndex++;
        }
    }

    /***
     * Create lines for name with given color and appropriate coordinates
     *
     * @param yCoordinates  calculated y-coordinates for points on the graph
     * @param colorIndex    index in a color array
     * @return an array of lines in graph for the name
     */
    private GLine[] createGraphsLinesForName(double[] yCoordinates, int colorIndex) {
        GLine[] linesForNamesGraph = new GLine[NDECADES];
        // create every line in graph except the latest
        for (int i = 0; i < NDECADES - 1; i++) {
            // get coordinates of start and end points
            double x1 = decadeLines[i].getX();
            double y1 = yCoordinates[i];
            double x2 = decadeLines[i + 1].getX();
            double y2 = yCoordinates[i + 1];
            // create line
            linesForNamesGraph[i] = new GLine(x1, y1, x2, y2);
            // and set color for it
            linesForNamesGraph[i].setColor(setColorForName(colorIndex));
        }
        // and the same procedure for the latest line in the graph
        double x1 = decadeLines[NDECADES - 1].getX();
        double y1 = yCoordinates[NDECADES - 1];
        double x2 = decadeLines[NDECADES - 2].getX();
        double y2 = yCoordinates[NDECADES - 2];
        linesForNamesGraph[NDECADES - 1] = new GLine(x1, y1, x2, y2);
        linesForNamesGraph[NDECADES - 1].setColor(setColorForName(colorIndex));

        return linesForNamesGraph;
    }

    /***
     * Create labels for name with given color and appropriate coordinates
     *
     * @param key           name to print
     * @param yCoordinates  calculated y-coordinates for points on the graph
     * @param colorIndex    index in a color array
     * @return array of labels for the name
     */
    private GLabel[] createLabelsForName(String key, double[] yCoordinates, int colorIndex) {
        GLabel[] labelsForName = new GLabel[NDECADES];
        // create every decade name's label
        for (int i = 0; i < NDECADES; i++) {
            // get coordinates
            double x1 = decadeLines[i].getX();
            double y1 = yCoordinates[i];
            // create label
            labelsForName[i] = new GLabel(key + " " + entryDataSet.get(key)[i], x1, y1);
            // and set color
            labelsForName[i].setColor(setColorForName(colorIndex));
        }
        return labelsForName;
    }

    /**
     * Create an array for y-coordinate
     * for points of lines` start/end and labels.
     *
     * @param key the name in collection of names to print
     * @return an array of y-coordinates
     */
    private double[] getYCoordinates(String key) {
        // calculate distance between every rank value (for 1000 ranks) according to window height
        double stepForRankGraph = (bottomLine.getY() - topLine.getY()) / MAX_RANK;
        // create an array
        double[] yCoordinates = new double[NDECADES];
        for (int i = 0; i < NDECADES; i++) {
            // get name`s rank for every decade
            double rankOnDecade = entryDataSet.get(key)[i];
            /* if there wasn't a name in the rank table in the decade,
             * it means the rank = 0, set the position on the bottom line in the window
             */
            if (rankOnDecade == 0) {
                yCoordinates[i] = bottomLine.getY();
            } else {
                // or calculate y-coordinate relatively to window's height
                yCoordinates[i] = topLine.getY() + rankOnDecade * stepForRankGraph;
            }
        }
        return yCoordinates;
    }

    /***
     * Create an array with given colors for names and return one for use
     *
     * @param colorIndex    the number of colors
     * @return the color to use
     */
    private Color setColorForName(int colorIndex) {
        Color[] color = new Color[4];
        color[0] = Color.BLUE;
        color[1] = Color.RED;
        color[2] = Color.MAGENTA;
        color[3] = Color.BLACK;

        // if there are more then 4 names to print, proceed from the beginning
        colorIndex = colorIndex % color.length;
        return color[colorIndex];
    }

    /***
     * Display the info (graph and name's label) in the window
     */
    private void displayInWindow() {
        // for every name in the collection of names from user to print
        for (Map.Entry<String, GLabel[]> label : labels.entrySet()) {
            // get name
            String keyName = label.getKey();
            // for all labels in the collection of labels for this name
            for (GLabel gLabel : labels.get(keyName)) {
                add(gLabel); // print it in the window
            }
        }

        // and the same procedure for graphs` lines
        for (Map.Entry<String, GLine[]> line : graphs.entrySet()) {
            String keyName = line.getKey();
            for (GLine gLine : graphs.get(keyName)) {
                add(gLine);
            }
        }
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
