package com.app.view;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(0,0,1366, 80);
        this.setLayout(new GridLayout(4,4));

        JLabel nameLabel = new JLabel("Character name:");
        JTextField nameField = new JTextField();

        JLabel raceLabel = new JLabel("Race:");
        String[] races = {"Dragonborn", "Dwarf", "Elf", "Gnome", "Half-Elf", "Halfling", "Half-Orc", "Human", "Tiefling"};
        JComboBox raceBox = new JComboBox(races);

        JLabel classLabel = new JLabel("Class:");
        String[] classes = {"Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard"};
        JComboBox classBox = new JComboBox(classes);

        JLabel subClassLabel = new JLabel("Subclass:");
        String[] subClasses = {"DEPENDS ON CLASS.."};
        JComboBox subClassBox = new JComboBox(subClasses);

        JLabel levelLabel = new JLabel("Level:");
        Integer[] levels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        JComboBox levelBox = new JComboBox(levels);

        JLabel backgroundLabel = new JLabel("Background:");
        String[] backgrounds = {"Acolyte", "Charlatan", "Criminal/Spy", "Entertainer", "Folk Hero", "Gladiator", "Guild Artisan", "Hermit", "Knight", "Noble", "Outlander", "Pirate", "Sage", "Sailor", "Soldier", "Urchin"};
        JComboBox backgroundBox = new JComboBox(backgrounds);

        JLabel alignmentLabel = new JLabel("Alignment:");
        String[] alignments = {"Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"};
        JComboBox alignmentBox = new JComboBox(alignments);

        JLabel expLabel = new JLabel("Experience:");
        JTextField expField = new JTextField();

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

        this.add(expLabel);
        this.add(expField);

    }
}
