package com.app.view;

import com.app.controller.Controller;
import com.app.model.CharacterSheet;
import com.app.model.CoreRules;
import com.app.model.DiceRoller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.CoderResult;

public class AttributePanel extends JPanel implements ActionListener {

    //SINGLETON
    private static AttributePanel instance = null;
    public static synchronized AttributePanel getInstance() {
        if(instance == null)
            instance = new AttributePanel();
        return instance;
    }

    //Instance of characterSheet:
    CharacterSheet characterSheet = CharacterSheet.getInstance();


    //Values:
    JLabel strValueLabel = new JLabel("...");
    JLabel strModifierLabel = new JLabel("...");
    JLabel strSTLabel = new JLabel("...");
    JLabel dexValueLabel = new JLabel("...");
    JLabel dexModifierLabel = new JLabel("...");
    JLabel dexSTLabel = new JLabel("...");
    JLabel conValueLabel = new JLabel("...");
    JLabel conModifierLabel = new JLabel("...");
    JLabel conSTLabel = new JLabel("...");
    JLabel intValueLabel = new JLabel("...");
    JLabel intModifierLabel = new JLabel("...");
    JLabel intSTLabel = new JLabel("...");
    JLabel wisValueLabel = new JLabel("...");
    JLabel wisModifierLabel = new JLabel("...");
    JLabel wisSTLabel = new JLabel("...");
    JLabel chaValueLabel = new JLabel("...");
    JLabel chaModifierLabel = new JLabel("...");
    JLabel chaSTLabel = new JLabel("...");

    //Getters for Labels:

    public JLabel getStrValueLabel() {
        return strValueLabel;
    }

    public JLabel getStrModifierLabel() {
        return strModifierLabel;
    }

    public JLabel getStrSTLabel() {
        return strSTLabel;
    }

    public JLabel getDexValueLabel() {
        return dexValueLabel;
    }

    public JLabel getDexModifierLabel() {
        return dexModifierLabel;
    }

    public JLabel getDexSTLabel() {
        return dexSTLabel;
    }

    public JLabel getConValueLabel() {
        return conValueLabel;
    }

    public JLabel getConModifierLabel() {
        return conModifierLabel;
    }

    public JLabel getConSTLabel() {
        return conSTLabel;
    }

    public JLabel getIntValueLabel() {
        return intValueLabel;
    }

    public JLabel getIntModifierLabel() {
        return intModifierLabel;
    }

    public JLabel getIntSTLabel() {
        return intSTLabel;
    }

    public JLabel getWisValueLabel() {
        return wisValueLabel;
    }

    public JLabel getWisModifierLabel() {
        return wisModifierLabel;
    }

    public JLabel getWisSTLabel() {
        return wisSTLabel;
    }

    public JLabel getChaValueLabel() {
        return chaValueLabel;
    }

    public JLabel getChaModifierLabel() {
        return chaModifierLabel;
    }

    public JLabel getChaSTLabel() {
        return chaSTLabel;
    }

    private AttributePanel() {

        this.setBackground(new Color(144, 111, 25));
        this.setBounds(300,80,400, 320);
        this.setLayout(new GridLayout(7,4));

        //"Roll" Button:
        JButton rollButton = new JButton("Roll!");
        rollButton.setFocusable(false);
        rollButton.setFont(rollButton.getFont().deriveFont(rollButton.getFont().getStyle() | Font.ITALIC));
        rollButton.setActionCommand("rollButton");
        rollButton.addActionListener(this);

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
        this.add(rollButton);
        this.add(col2Label);
        this.add(col3Label);
        this.add(col4Label);
        this.add(strLabel);
        this.add(strValueLabel);
        this.add(strModifierLabel);
        this.add(strSTLabel);
        this.add(dexLabel);
        this.add(dexValueLabel);
        this.add(dexModifierLabel);
        this.add(dexSTLabel);
        this.add(conLabel);
        this.add(conValueLabel);
        this.add(conModifierLabel);
        this.add(conSTLabel);
        this.add(intLabel);
        this.add(intValueLabel);
        this.add(intModifierLabel);
        this.add(intSTLabel);
        this.add(wisLabel);
        this.add(wisValueLabel);
        this.add(wisModifierLabel);
        this.add(wisSTLabel);
        this.add(chaLabel);
        this.add(chaValueLabel);
        this.add(chaModifierLabel);
        this.add(chaSTLabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "rollButton" -> {
                Controller controller = Controller.getInstance();
                controller.rollAttributes();
            }
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}