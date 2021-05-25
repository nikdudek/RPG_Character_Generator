package com.app.view;

import com.app.model.CharacterSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel implements ActionListener {

    //Instance of characterSheet:
    CharacterSheet characterSheet = new CharacterSheet();

    //Public elements:
    JTextField nameField = new JTextField();

    String[] races = {"Dragonborn", "Dwarf", "Elf", "Gnome", "Half-Elf", "Halfling", "Half-Orc", "Human", "Tiefling"};
    JComboBox raceBox = new JComboBox(races);

    String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};
    JComboBox classBox = new JComboBox(classes);

    String[] subClasses = {"DEPENDS ON CLASS.."};
    JComboBox subClassBox = new JComboBox(subClasses);

    Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    JComboBox levelBox = new JComboBox(levels);

    String[] backgrounds = {"Acolyte", "Charlatan", "Criminal/Spy", "Entertainer", "Folk Hero", "Gladiator", "Guild Artisan", "Hermit", "Knight", "Noble", "Outlander", "Pirate", "Sage", "Sailor", "Soldier", "Urchin"};
    JComboBox backgroundBox = new JComboBox(backgrounds);

    String[] alignments = {"Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"};
    JComboBox alignmentBox = new JComboBox(alignments);

    public InfoPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(0,0,1366, 80);
        this.setLayout(new GridLayout(4,4));

        //Labels:
        JLabel nameLabel = new JLabel("Character name:");
        JLabel raceLabel = new JLabel("Race:");
        JLabel classLabel = new JLabel("Class:");
        JLabel subClassLabel = new JLabel("Subclass:");
        JLabel levelLabel = new JLabel("Level:");
        JLabel backgroundLabel = new JLabel("Background:");
        JLabel alignmentLabel = new JLabel("Alignment:");
        JLabel createLabel = new JLabel("Picked all above? -->");

        JButton createButton = new JButton("Create Character!");
        createButton.setActionCommand("createButton");
        createButton.addActionListener(this);

        //Putting elements into Panel
        this.add(nameLabel);
        this.add(nameField);

        this.add(raceLabel);
        this.add(raceBox);

        this.add(classLabel);
        this.add(classBox);

        this.add(subClassLabel);
        this.add(subClassBox);

        this.add(levelLabel);
        this.add(levelBox);

        this.add(backgroundLabel);
        this.add(backgroundBox);

        this.add(alignmentLabel);
        this.add(alignmentBox);

        this.add(createLabel);
        this.add(createButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "createButton" -> {

                characterSheet.setName(nameField.getText());
                characterSheet.setRace(raceBox.getSelectedItem().toString());
                characterSheet.setMainClass(classBox.getSelectedItem().toString());
                characterSheet.setSubClass(subClassBox.getSelectedItem().toString());
                characterSheet.setLevel(Integer.parseInt(levelBox.getSelectedItem().toString()));
                characterSheet.setBackground(backgroundBox.getSelectedItem().toString());
                characterSheet.setAlignment(alignmentBox.getSelectedItem().toString());
            }
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}
