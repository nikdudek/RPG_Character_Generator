package com.app.view;

import com.app.controller.Controller;
import com.app.model.CharacterSheet;
import com.app.model.CoreRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InfoPanel extends JPanel implements ActionListener, ItemListener {

    //SINGLETON
    private static InfoPanel instance = null;
    public static synchronized InfoPanel getInstance() {

        if(instance == null)
            instance = new InfoPanel();
        return instance;
    }

    //Instance of characterSheet:
    CharacterSheet characterSheet = CharacterSheet.getInstance();
    CoreRules coreRules = CoreRules.getInstance();
    Controller controller = Controller.getInstance();

    //Public elements:
    JTextField nameField = new JTextField();
    JLabel renameLabel = new JLabel("Character's current name is: ");

    JComboBox levelBox = new JComboBox(coreRules.getLevels());
    JComboBox raceBox = new JComboBox(coreRules.getRaces());
    JComboBox alignmentBox = new JComboBox(coreRules.getAlignments());
    JComboBox backgroundBox = new JComboBox(coreRules.getBackgrounds());
    JComboBox classBox = new JComboBox(coreRules.getClasses());
    JComboBox subClassBox = new JComboBox(controller.getSubClasses(coreRules.DEFAULT));

    private InfoPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(0,0,1366, 80);
        this.setLayout(new GridLayout(4,4));

        //Labels:
        JLabel nameLabel = new JLabel("Character name:");
        JLabel levelLabel = new JLabel("Level:");
        JLabel raceLabel = new JLabel("Race:");
        JLabel alignmentLabel = new JLabel("Alignment:");
        JLabel backgroundLabel = new JLabel("Background:");
        JLabel classLabel = new JLabel("Class:");
        JLabel subClassLabel = new JLabel("Subclass:");

        JButton renameButton = new JButton("SET NAME");
        renameButton.setFocusable(false);
        renameButton.setActionCommand("renameButton");
        renameButton.addActionListener(this);

        //Item listener
        levelBox.addItemListener(this);
        raceBox.addItemListener(this);
        alignmentBox.addItemListener(this);
        backgroundBox.addItemListener(this);
        classBox.addItemListener(this);
        subClassBox.addItemListener(this);

        //Putting elements into Panel
        this.add(nameLabel);
        this.add(nameField);

        this.add(renameButton);
        this.add(renameLabel);

        this.add(levelLabel);
        this.add(levelBox);

        this.add(raceLabel);
        this.add(raceBox);

        this.add(alignmentLabel);
        this.add(alignmentBox);

        this.add(backgroundLabel);
        this.add(backgroundBox);

        this.add(classLabel);
        this.add(classBox);

        this.add(subClassLabel);
        this.add(subClassBox);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "renameButton" -> {
                controller.setName(nameField,renameLabel);
            }
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if (e.getSource() == raceBox) controller.setRace(e);
            else if (e.getSource() == classBox) controller.setClass(e,classBox);
            else if (e.getSource() == subClassBox) controller.setSubClass(e);
            else if (e.getSource() == levelBox) controller.setLevel(e,classBox);
        }
    }
}
