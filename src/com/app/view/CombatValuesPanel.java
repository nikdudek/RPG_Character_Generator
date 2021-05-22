package com.app.view;

import javax.swing.*;
import java.awt.*;

public class CombatValuesPanel extends JPanel {

    public CombatValuesPanel() {

        this.setBackground(new Color(182, 144, 51));
        this.setBounds(0,80,300, 320);
        this.setLayout(new GridLayout(6,2));

        //Titles:
        JLabel iniLabel = new JLabel("Initiative:");
        JLabel speLabel = new JLabel("Speed:");
        JLabel proLabel = new JLabel("Proficiency:");
        JLabel hidLabel = new JLabel("Hit Dice:");
        JLabel hipLabel = new JLabel("Hit Points:");
        JLabel armLabel = new JLabel("Armor Class:");

        //Values:
        JLabel v01Label = new JLabel("/Value/");
        JLabel v02Label = new JLabel("/Value/");
        JLabel v03Label = new JLabel("/Value/");
        JLabel v04Label = new JLabel("/Value/");
        JLabel v05Label = new JLabel("/Value/");
        JLabel v06Label = new JLabel("/Value/");

        //Adding elements:
        this.add(iniLabel);
        this.add(v01Label);
        this.add(speLabel);
        this.add(v02Label);
        this.add(proLabel);
        this.add(v03Label);
        this.add(hidLabel);
        this.add(v04Label);
        this.add(hipLabel);
        this.add(v05Label);
        this.add(armLabel);
        this.add(v06Label);
    }
}
