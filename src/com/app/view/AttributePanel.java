package com.app.view;

import com.app.model.DiceRoller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttributePanel extends JPanel implements ActionListener {

    public String attributeToModifier(String attr) {
        int attrVal = (Integer.parseInt(attr) - 10);
        if(attrVal < 0) return String.valueOf((attrVal - 1) / 2);
        else return String.valueOf(attrVal / 2);
    }

    public String attributeToST(String attr) {
        int attrVal = (Integer.parseInt(attr) - 10);
        if(attrVal < 0) return String.valueOf((attrVal - 1) / 2);
        else return String.valueOf(attrVal / 2);
    }

    DiceRoller diceRoller = new DiceRoller();
    int attributeDiceCount = 3;

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

    public AttributePanel() {

        this.setBackground(new Color(144, 111, 25));
        this.setBounds(300,80,400, 320);
        this.setLayout(new GridLayout(7,4));

        //"Roll" Button:
        JButton col1Button = new JButton("Roll!");
        col1Button.setFont(col1Button.getFont().deriveFont(col1Button.getFont().getStyle() | Font.ITALIC));
        col1Button.setActionCommand("col1Button");
        col1Button.addActionListener(this);

        //Titles:
        JLabel col2Label = new JLabel("Base Value");
        col2Label.setFont(col2Label.getFont().deriveFont(col2Label.getFont().getStyle() | Font.ITALIC));
        JLabel col3Label = new JLabel("Modifier");
        col3Label.setFont(col3Label.getFont().deriveFont(col3Label.getFont().getStyle() | Font.ITALIC));
        JLabel col4Label = new JLabel("Saving Throw");
        col4Label.setFont(col4Label.getFont().deriveFont(col4Label.getFont().getStyle() | Font.ITALIC));

        JLabel strLabel = new JLabel("Strength:");
        JLabel dexLabel = new JLabel("Dexterity:");
        JLabel conLabel = new JLabel("Condition:");
        JLabel intLabel = new JLabel("Intelligence:");
        JLabel wisLabel = new JLabel("Wisdom:");
        JLabel chaLabel = new JLabel("Charisma:");

        //Adding elements:
        this.add(col1Button);
        this.add(col2Label);
        this.add(col3Label);
        this.add(col4Label);
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

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "col1Button" -> {
                //Roll Attributes:
                v01aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));
                v02aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));
                v03aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));
                v04aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));
                v05aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));
                v06aLabel.setText(String.valueOf(diceRoller.d6(attributeDiceCount)));

                //Set modifiers:
                v01bLabel.setText(attributeToModifier(v01aLabel.getText()));
                v02bLabel.setText(attributeToModifier(v02aLabel.getText()));
                v03bLabel.setText(attributeToModifier(v03aLabel.getText()));
                v04bLabel.setText(attributeToModifier(v04aLabel.getText()));
                v05bLabel.setText(attributeToModifier(v05aLabel.getText()));
                v06bLabel.setText(attributeToModifier(v06aLabel.getText()));

                //Set saving throws:
                v01cLabel.setText(attributeToModifier(v01aLabel.getText()));
                v02cLabel.setText(attributeToModifier(v02aLabel.getText()));
                v03cLabel.setText(attributeToModifier(v03aLabel.getText()));
                v04cLabel.setText(attributeToModifier(v04aLabel.getText()));
                v05cLabel.setText(attributeToModifier(v05aLabel.getText()));
                v06cLabel.setText(attributeToModifier(v06aLabel.getText()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}