package com.app.view;

import com.app.model.CharacterSheet;

import javax.swing.*;
import java.awt.*;

public class CombatValuesPanel extends JPanel {

    //SINGLETON
    private static CombatValuesPanel instance = null;
    public static synchronized CombatValuesPanel getInstance() {
        if(instance == null)
            instance = new CombatValuesPanel();
        return instance;
    }

    //Titles:
    JLabel iniLabel = new JLabel("Initiative:");
    JLabel speLabel = new JLabel("Speed:");
    JLabel proLabel = new JLabel("Proficiency:");
    JLabel hidLabel = new JLabel("Hit Dice:");
    JLabel hipLabel = new JLabel("Hit Points:");
    JLabel armLabel = new JLabel("Armor Class:");

    //Values:
    public JLabel iniValLabel = new JLabel("...");
    public JLabel speValLabel = new JLabel("...");
    public JLabel proValLabel = new JLabel("...");
    public JLabel hidValLabel = new JLabel("...");
    public JLabel hipValLabel = new JLabel("...");
    public JLabel armValLabel = new JLabel("...");

    private CombatValuesPanel() {

        this.setBackground(new Color(182, 144, 51));
        this.setBounds(0,80,300, 320);
        this.setLayout(new GridLayout(6,2));

        //Adding elements:
        this.add(iniLabel);
        this.add(iniValLabel);
        this.add(speLabel);
        this.add(speValLabel);
        this.add(proLabel);
        this.add(proValLabel);
        this.add(hidLabel);
        this.add(hidValLabel);
        this.add(hipLabel);
        this.add(hipValLabel);
        this.add(armLabel);
        this.add(armValLabel);
    }
}