package com.shpp.p2p.cs.aiakovenko.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    /* Buttons and text field on the top */
    private JButton clearButton;
    private JButton graphButton;
    private JTextField nameField;
    private NameSurferGraph graph;
    /* Instance of NameSurferDataBase with a database for names */
    private NameSurferDataBase DATA_BASE;
    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        try {
            DATA_BASE = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /* Add in a title. */
        add(new JLabel("Name"), NORTH);

        int textWidth = NameSurferConstants.APPLICATION_WIDTH / 20;
        nameField = new JTextField(textWidth);
        nameField.setActionCommand("EnterPressed");
        nameField.addActionListener(this);
        add(nameField, NORTH);

        graphButton = new JButton("Graph");
        add(graphButton, NORTH);

        clearButton = new JButton("Clear");
        add(clearButton, NORTH);

        /* Add component for painting graphs */
        graph = new NameSurferGraph();
        add(graph);

        addActionListeners();
    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (e.getSource() == graphButton) {
            // get text that user printed in text field
            String name = nameField.getText();
            // check if there is such a name in database file and create an instance for it
            NameSurferEntry nameInfo = DATA_BASE.findEntry(name);
            // add info to LinkedHashMap - collection of instance to print
            graph.addEntry(nameInfo);
            // display info in the window
            graph.update();
        } else if (e.getSource() == clearButton) {
            // method from the class NameSurferGraph to reset all names
            graph.clear();
            // and clear the window
            graph.update();
            nameField.setText("");
        }
        else if (cmd.equals("EnterPressed")) {
            // get text that user printed in text field
            String name = nameField.getText();
            // check if there is such a name in database file and create an instance for it
            NameSurferEntry nameInfo = DATA_BASE.findEntry(name);
            // add info to LinkedHashMap - collection of instance to print
            graph.addEntry(nameInfo);
            // display info in the window
            graph.update();
        }
    }
}
