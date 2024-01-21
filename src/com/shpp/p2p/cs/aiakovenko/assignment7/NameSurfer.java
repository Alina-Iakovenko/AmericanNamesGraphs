package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GLabel;
import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    private JButton clearButton;
    private JButton graphButton;
    private JTextField nameField;
    private int textWidth = NameSurferConstants.APPLICATION_WIDTH / 20;
    private NameSurferGraph graph;
    NameSurferDataBase dataBase = new NameSurferDataBase(NAMES_DATA_FILE);

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        /* Add in a title. */
        add(new JLabel("Name"), NORTH);

        nameField = new JTextField(textWidth);
        add(nameField, NORTH);

        graphButton = new JButton("Graph");
        add(graphButton, NORTH);

        clearButton = new JButton("Clear");
        add(clearButton, NORTH);

        addActionListeners();

        graph = new NameSurferGraph();
        add(graph);
    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == graphButton) {
            String name = nameField.getText();
            NameSurferEntry lineInDataBase = dataBase.findEntry(name);
            graph.addEntry(lineInDataBase);
            graph.update();
        } else if (e.getSource() == clearButton) {
            graph.clear(); // from NameSurferGraph
        }
    }
}
