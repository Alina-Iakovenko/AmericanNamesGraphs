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
import acm.program.Program;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;

import static acm.util.JTFTools.pause;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {
    GLine topLine;
    GLine bottomLine;
    GLine[] decadeLines = new GLine[NDECADES];
    GLabel[] decadeLabels = new GLabel[NDECADES];
    LinkedHashMap<String, int[]> entryDataSet = new LinkedHashMap<>();
    LinkedHashMap<String, GLabel[]> labels = new LinkedHashMap<>();
    LinkedHashMap<String, GLine[]> graphs = new LinkedHashMap<>();

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
            entryDataSet.clear();
            update();
        }
    }


    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        try {
            String name = entry.getName();
            int[] ranks = new int[NDECADES];

            for (int i = 0; i < NDECADES; i++) {
                ranks[i] = entry.getRank(i);
            }
            entryDataSet.putIfAbsent(name, ranks);
        } catch (NullPointerException e) {
            throw new NullPointerException("No match in data base for this name");
        }
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        topLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        topLine.setColor(Color.BLACK);
        add(topLine);

        if (bottomLine != null) {
            remove(bottomLine);
        }
        bottomLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        bottomLine.setColor(Color.BLACK);
        add(bottomLine);

        createDecadesTable();
        createGraphsOnDecadesTable();
    }

    private void createGraphsOnDecadesTable() {
        if (!(labels.isEmpty()) || !(graphs.isEmpty())) {
            clearTheTable();
        }

        createInstancesForDisplay();

        if (!(labels.isEmpty()) || !(graphs.isEmpty())) {
            displayInWindow();
        }
    }

    private void createInstancesForDisplay() {
        double stepForRankGraph = (bottomLine.getY() - topLine.getY()) / MAX_RANK;
        int colorIndex = 0;

        labels = new LinkedHashMap<>();
        graphs = new LinkedHashMap<>();

        Set<String> keys = entryDataSet.keySet();
        for (String key : keys) {
            GLabel[] labelsForName = new GLabel[NDECADES];
            GLine[] linesForNamesGraph = new GLine[NDECADES];

            double[] yCoordinates = new double[NDECADES];
            for (int i = 0; i < NDECADES; i++) {
                if (entryDataSet.get(key)[i] == 0) {
                    yCoordinates[i] = bottomLine.getY();
                } else {
                    yCoordinates[i] = topLine.getY() + entryDataSet.get(key)[i] * stepForRankGraph;
                }
            }

            for (int i = 0; i < NDECADES - 1; i++) {
                double x1 = decadeLines[i].getX();
                double y1 = yCoordinates[i];
                labelsForName[i] = new GLabel(key + " " + entryDataSet.get(key)[i], x1, y1);
                labelsForName[i].setColor(setColorForName(colorIndex));

                double x2 = decadeLines[i + 1].getX();
                double y2= yCoordinates[i+1];
                linesForNamesGraph[i] = new GLine(x1, y1, x2, y2);
                linesForNamesGraph[i].setColor(setColorForName(colorIndex));
            }
            double x1 = decadeLines[NDECADES - 1].getX();
            double y1 = yCoordinates[NDECADES - 1];
            labelsForName[NDECADES - 1] = new GLabel(key + " " + entryDataSet.get(key)[NDECADES - 1], x1, y1);
            labelsForName[NDECADES - 1].setColor(setColorForName(colorIndex));

            double x2 = decadeLines[NDECADES - 2].getX();
            double y2 = yCoordinates[NDECADES - 2];
            linesForNamesGraph[NDECADES - 1] = new GLine(x1, y1, x2, y2);
            linesForNamesGraph[NDECADES - 1].setColor(setColorForName(colorIndex));

            labels.putIfAbsent(key, labelsForName);
            graphs.putIfAbsent(key, linesForNamesGraph);

            colorIndex++;
        }
    }

    private Color setColorForName(int colorIndex) {
        Color[] color = new Color[4];
        color[0] = Color.BLUE;
        color[1] = Color.RED;
        color[2] = Color.MAGENTA;
        color[3] = Color.BLACK;
        colorIndex = colorIndex % color.length;
        return color[colorIndex];
    }

    private void displayInWindow() {
        for (Map.Entry<String, GLabel[]> label : labels.entrySet()) {
            String keyName = label.getKey();
            for (GLabel gLabel : labels.get(keyName)) {
                add(gLabel);
            }
        }
        for (Map.Entry<String, GLine[]> line : graphs.entrySet()) {
            String keyName = line.getKey();
            for (GLine gLine : graphs.get(keyName)) {
                add(gLine);
            }
        }
    }

    private void clearTheTable() {
        for (Map.Entry<String, GLine[]> entry : graphs.entrySet()) {
            String keyName = entry.getKey();
            for (GLine gLine : graphs.get(keyName)) {
                remove(gLine);
            }
        }
        graphs.clear();
        for (Map.Entry<String, GLabel[]> entry : labels.entrySet()) {
            String keyName = entry.getKey();
            for (GLabel gLabel : labels.get(keyName)) {
                remove(gLabel);
            }
        }
        labels.clear();
    }

    private void createDecadesTable() {
        double distanceBetweenDecades = getWidth() / NDECADES;
        for (int i = 0; i < NDECADES; i++) {
            if (decadeLines[i] != null) {
                remove(decadeLines[i]);
            }
            decadeLines[i] = new GLine(i * distanceBetweenDecades + 2, 0.0, i * distanceBetweenDecades + 2, getHeight());
            decadeLines[i].setColor(Color.BLACK);
            add(decadeLines[i]);

            if (decadeLabels[i] != null) {
                remove(decadeLabels[i]);
            }
            String textForLabel = Integer.toString(START_DECADE + i * 10);
            decadeLabels[i] = new GLabel(textForLabel, i * distanceBetweenDecades + 3, getHeight() - 2);
            add(decadeLabels[i]);
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
