package com.app.view;

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
    public JLabel iniValLabel = new JLabel();
    public JLabel speValLabel = new JLabel();
    public JLabel proValLabel = new JLabel();
    public JLabel hidValLabel = new JLabel();
    public JLabel hipValLabel = new JLabel();
    public JLabel armValLabel = new JLabel();

    private CombatValuesPanel() {

        this.setBackground(new Color(182, 144, 51));
        this.setBounds(0,80,300, 320);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(6,2));

        //Adding borders and alignments:
        iniLabel.setBorder(BorderFactory.createBevelBorder(1));
        iniValLabel.setBorder(BorderFactory.createBevelBorder(1));
        iniValLabel.setHorizontalAlignment(JLabel.CENTER);
        speLabel.setBorder(BorderFactory.createBevelBorder(1));
        speValLabel.setBorder(BorderFactory.createBevelBorder(1));
        speValLabel.setHorizontalAlignment(JLabel.CENTER);
        proLabel.setBorder(BorderFactory.createBevelBorder(1));
        proValLabel.setBorder(BorderFactory.createBevelBorder(1));
        proValLabel.setHorizontalAlignment(JLabel.CENTER);
        hidLabel.setBorder(BorderFactory.createBevelBorder(1));
        hidValLabel.setBorder(BorderFactory.createBevelBorder(1));
        hidValLabel.setHorizontalAlignment(JLabel.CENTER);
        hipLabel.setBorder(BorderFactory.createBevelBorder(1));
        hipValLabel.setBorder(BorderFactory.createBevelBorder(1));
        hipValLabel.setHorizontalAlignment(JLabel.CENTER);
        armLabel.setBorder(BorderFactory.createBevelBorder(1));
        armValLabel.setBorder(BorderFactory.createBevelBorder(1));
        armValLabel.setHorizontalAlignment(JLabel.CENTER);

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