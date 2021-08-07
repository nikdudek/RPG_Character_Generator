package com.app.view;

import com.app.controller.Controller;
import com.app.model.CharacterSheet;
import com.app.model.CoreRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InfoPanel extends JPanel implements ItemListener {

    public void setSubClassBoxModel(int classId) {
        switch (classId) {
            case 0 -> subClassBox.setModel(barbarianModel);
            case 1 -> subClassBox.setModel(bardModel);
            case 2 -> subClassBox.setModel(clericModel);
            case 3 -> subClassBox.setModel(druidModel);
            case 4 -> subClassBox.setModel(fighterModel);
            case 5 -> subClassBox.setModel(monkModel);
            case 6 -> subClassBox.setModel(paladinModel);
            case 7 -> subClassBox.setModel(rangerModel);
            case 8 -> subClassBox.setModel(rogueModel);
            case 9 -> subClassBox.setModel(sorcererModel);
            case 10 -> subClassBox.setModel(warlockModel);
            case 11 -> subClassBox.setModel(wizardModel);
        }
    }

    //SINGLETON
    private static InfoPanel instance = null;
    public static synchronized InfoPanel getInstance() {

        if(instance == null)
            instance = new InfoPanel();
        return instance;
    }

    //Instances:
    CharacterSheet characterSheet = CharacterSheet.getInstance();
    CoreRules coreRules = CoreRules.getInstance();
    Controller controller = Controller.getInstance();

    private JComboBox levelBox = new JComboBox(coreRules.getLevels());
    private JComboBox raceBox = new JComboBox(coreRules.getRaces());
    private JComboBox alignmentBox = new JComboBox(coreRules.getAlignments());
    private JComboBox backgroundBox = new JComboBox(coreRules.getBackgrounds());
    private JComboBox classBox = new JComboBox(coreRules.getClasses());

    //Subclasses models:
    DefaultComboBoxModel<String> barbarianModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.BARBARIAN));
    DefaultComboBoxModel<String> bardModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.BARD));
    DefaultComboBoxModel<String> clericModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.CLERIC));
    DefaultComboBoxModel<String> druidModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.DRUID));
    DefaultComboBoxModel<String> fighterModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.FIGHTER));
    DefaultComboBoxModel<String> monkModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.MONK));
    DefaultComboBoxModel<String> paladinModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.PALADIN));
    DefaultComboBoxModel<String> rangerModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.RANGER));
    DefaultComboBoxModel<String> rogueModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.ROGUE));
    DefaultComboBoxModel<String> sorcererModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.SORCERER));
    DefaultComboBoxModel<String> warlockModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.WARLOCK));
    DefaultComboBoxModel<String> wizardModel = new DefaultComboBoxModel<>(coreRules.getSubClasses(coreRules.WIZARD));

    JComboBox subClassBox = new JComboBox();

    private InfoPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(0,0,1366, 80);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(3,4));

        //Labels:
        JLabel levelLabel = new JLabel("Level:");
        JLabel raceLabel = new JLabel("Race:");
        JLabel alignmentLabel = new JLabel("Alignment:");
        JLabel backgroundLabel = new JLabel("Background:");
        JLabel classLabel = new JLabel("Class:");
        JLabel subClassLabel = new JLabel("Subclass:");

        //Item listener
        levelBox.addItemListener(this);
        raceBox.addItemListener(this);
        alignmentBox.addItemListener(this);
        backgroundBox.addItemListener(this);
        classBox.addItemListener(this);
        subClassBox.addItemListener(this);

        //Putting elements into Panel
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

    public JComboBox getClassBox() {
        return classBox;
    }

    public JComboBox getLevelBox() {
        return levelBox;
    }

    public JComboBox getRaceBox() {
        return raceBox;
    }

    public JComboBox getAlignmentBox() {
        return alignmentBox;
    }

    public JComboBox getBackgroundBox() {
        return backgroundBox;
    }

    public JComboBox getSubClassBox() {
        return subClassBox;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if (e.getSource() == raceBox) controller.setRace();
            else if (e.getSource() == alignmentBox) controller.setAlignment();
            else if (e.getSource() == backgroundBox) controller.setBackground();
            else if (e.getSource() == classBox) controller.setClass();
            else if (e.getSource() == subClassBox) controller.setSubClass();
            else if (e.getSource() == levelBox) controller.setLevel();
        }
    }
}
