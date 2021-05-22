package com.app.view;

import javax.swing.*;
import java.awt.*;

public class AttributePanel extends JPanel {

    public AttributePanel() {

        this.setBackground(new Color(144, 111, 25));
        this.setBounds(300,80,400, 320);
        this.setLayout(new GridLayout(6,4));

        //Titles:
        JLabel strLabel = new JLabel("Strength:");
        JLabel dexLabel = new JLabel("Dexterity:");
        JLabel conLabel = new JLabel("Condition:");
        JLabel intLabel = new JLabel("Intelligence:");
        JLabel wisLabel = new JLabel("Wisdom:");
        JLabel chaLabel = new JLabel("Charisma:");

        //Values:
        JLabel v01aLabel = new JLabel("/Value/");
        JLabel v01bLabel = new JLabel("/Value/");
        JLabel v01cLabel = new JLabel("/Value/");
        JLabel v02aLabel = new JLabel("/Value/");
        JLabel v02bLabel = new JLabel("/Value/");
        JLabel v02cLabel = new JLabel("/Value/");
        JLabel v03aLabel = new JLabel("/Value/");
        JLabel v03bLabel = new JLabel("/Value/");
        JLabel v03cLabel = new JLabel("/Value/");
        JLabel v04aLabel = new JLabel("/Value/");
        JLabel v04bLabel = new JLabel("/Value/");
        JLabel v04cLabel = new JLabel("/Value/");
        JLabel v05aLabel = new JLabel("/Value/");
        JLabel v05bLabel = new JLabel("/Value/");
        JLabel v05cLabel = new JLabel("/Value/");
        JLabel v06aLabel = new JLabel("/Value/");
        JLabel v06bLabel = new JLabel("/Value/");
        JLabel v06cLabel = new JLabel("/Value/");

        //Adding elements:
        this.add(strLabel);
        this.add(v01aLabel);
        this.add(v01bLabel);
        this.add(v01cLabel);
        this.add(dexLabel);
        this.add(v02aLabel);
        this.add(v02bLabel);
        this.add(v02cLabel);
        this.add(conLabel);
        this.add(v03aLabel);
        this.add(v03bLabel);
        this.add(v03cLabel);
        this.add(intLabel);
        this.add(v04aLabel);
        this.add(v04bLabel);
        this.add(v04cLabel);
        this.add(wisLabel);
        this.add(v05aLabel);
        this.add(v05bLabel);
        this.add(v05cLabel);
        this.add(chaLabel);
        this.add(v06aLabel);
        this.add(v06bLabel);
        this.add(v06cLabel);

    }
}