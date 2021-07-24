package com.app.view;

import com.app.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttributePanel extends JPanel implements ActionListener {

    //SINGLETON
    private static AttributePanel instance = null;
    public static synchronized AttributePanel getInstance() {
        if(instance == null)
            instance = new AttributePanel();
        return instance;
    }

    //Values:
    JLabel strValueLabel = new JLabel();
    JLabel strModifierLabel = new JLabel();
    JLabel strSTLabel = new JLabel();
    JLabel dexValueLabel = new JLabel();
    JLabel dexModifierLabel = new JLabel();
    JLabel dexSTLabel = new JLabel();
    JLabel conValueLabel = new JLabel();
    JLabel conModifierLabel = new JLabel();
    JLabel conSTLabel = new JLabel();
    JLabel intValueLabel = new JLabel();
    JLabel intModifierLabel = new JLabel();
    JLabel intSTLabel = new JLabel();
    JLabel wisValueLabel = new JLabel();
    JLabel wisModifierLabel = new JLabel();
    JLabel wisSTLabel = new JLabel();
    JLabel chaValueLabel = new JLabel();
    JLabel chaModifierLabel = new JLabel();
    JLabel chaSTLabel = new JLabel();

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
        this.setBorder(BorderFactory.createBevelBorder(1));
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

        //Adding borders and alignments:
        strLabel.setBorder(BorderFactory.createBevelBorder(1));
        dexLabel.setBorder(BorderFactory.createBevelBorder(1));
        conLabel.setBorder(BorderFactory.createBevelBorder(1));
        intLabel.setBorder(BorderFactory.createBevelBorder(1));
        wisLabel.setBorder(BorderFactory.createBevelBorder(1));
        chaLabel.setBorder(BorderFactory.createBevelBorder(1));

        col2Label.setBorder(BorderFactory.createBevelBorder(1));
        col2Label.setHorizontalAlignment(JLabel.CENTER);
        col3Label.setBorder(BorderFactory.createBevelBorder(1));
        col3Label.setHorizontalAlignment(JLabel.CENTER);
        col4Label.setBorder(BorderFactory.createBevelBorder(1));
        col4Label.setHorizontalAlignment(JLabel.CENTER);

        strValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        strValueLabel.setHorizontalAlignment(JLabel.CENTER);
        strModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        strModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        strSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        strSTLabel.setHorizontalAlignment(JLabel.CENTER);
        dexValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        dexValueLabel.setHorizontalAlignment(JLabel.CENTER);
        dexModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        dexModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        dexSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        dexSTLabel.setHorizontalAlignment(JLabel.CENTER);
        conValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        conValueLabel.setHorizontalAlignment(JLabel.CENTER);
        conModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        conModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        conSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        conSTLabel.setHorizontalAlignment(JLabel.CENTER);
        intValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        intValueLabel.setHorizontalAlignment(JLabel.CENTER);
        intModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        intModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        intSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        intSTLabel.setHorizontalAlignment(JLabel.CENTER);
        wisValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        wisValueLabel.setHorizontalAlignment(JLabel.CENTER);
        wisModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        wisModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        wisSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        wisSTLabel.setHorizontalAlignment(JLabel.CENTER);
        chaValueLabel.setBorder(BorderFactory.createBevelBorder(1));
        chaValueLabel.setHorizontalAlignment(JLabel.CENTER);
        chaModifierLabel.setBorder(BorderFactory.createBevelBorder(1));
        chaModifierLabel.setHorizontalAlignment(JLabel.CENTER);
        chaSTLabel.setBorder(BorderFactory.createBevelBorder(1));
        chaSTLabel.setHorizontalAlignment(JLabel.CENTER);

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