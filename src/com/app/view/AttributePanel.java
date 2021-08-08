package com.app.view;

import com.app.model.CoreRules;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class AttributePanel extends JPanel {

    //SINGLETON
    private static AttributePanel instance = null;
    public static synchronized AttributePanel getInstance() {
        if(instance == null)
            instance = new AttributePanel();
        return instance;
    }

    CoreRules coreRules = CoreRules.getInstance();
    //Values:
    JLabel[] valueLabels = IntStream.range(0, coreRules.ALL_ATTRIBUTES).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
    JLabel[] modifierLabels = IntStream.range(0, coreRules.ALL_ATTRIBUTES).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);
    JLabel[] stLabels = IntStream.range(0, coreRules.ALL_ATTRIBUTES).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);

    //Getters for Labels:
    public JLabel[] getValueLabels() {
        return valueLabels;
    }
    public JLabel[] getModifierLabels() {
        return modifierLabels;
    }
    public JLabel[] getStLabels() {
        return stLabels;
    }

    private AttributePanel() {

        this.setBackground(new Color(144, 111, 25));
        this.setBounds(300,80,400, 320);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(7,4));

        //Attribute Label
        JLabel attrLabel = new JLabel("ATTRIBUTES:");
        attrLabel.setFont(attrLabel.getFont().deriveFont(attrLabel.getFont().getStyle() | Font.ITALIC));
        attrLabel.setHorizontalAlignment(JLabel.CENTER);

        //Titles:
        JLabel col2Label = new JLabel("Base Value");
        col2Label.setFont(col2Label.getFont().deriveFont(col2Label.getFont().getStyle() | Font.ITALIC));
        JLabel col3Label = new JLabel("Modifier");
        col3Label.setFont(col3Label.getFont().deriveFont(col3Label.getFont().getStyle() | Font.ITALIC));
        JLabel col4Label = new JLabel("Saving Throw");
        col4Label.setFont(col4Label.getFont().deriveFont(col4Label.getFont().getStyle() | Font.ITALIC));

        JLabel strLabel = new JLabel("Strength:");
        JLabel dexLabel = new JLabel("Dexterity:");
        JLabel conLabel = new JLabel("Constitution:");
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

        for(JLabel x : valueLabels) {
            x.setBorder(BorderFactory.createBevelBorder(1));
            x.setHorizontalAlignment(JLabel.CENTER);
        }
        for(JLabel x : modifierLabels) {
            x.setBorder(BorderFactory.createBevelBorder(1));
            x.setHorizontalAlignment(JLabel.CENTER);
        }
        for(JLabel x : stLabels) {
            x.setBorder(BorderFactory.createBevelBorder(1));
            x.setHorizontalAlignment(JLabel.CENTER);
        }

        //Adding elements:
        this.add(attrLabel);
        this.add(col2Label);
        this.add(col3Label);
        this.add(col4Label);
        this.add(strLabel);
        this.add(valueLabels[0]);
        this.add(modifierLabels[0]);
        this.add(stLabels[0]);
        this.add(dexLabel);
        this.add(valueLabels[1]);
        this.add(modifierLabels[1]);
        this.add(stLabels[1]);
        this.add(conLabel);
        this.add(valueLabels[2]);
        this.add(modifierLabels[2]);
        this.add(stLabels[2]);
        this.add(intLabel);
        this.add(valueLabels[3]);
        this.add(modifierLabels[3]);
        this.add(stLabels[3]);
        this.add(wisLabel);
        this.add(valueLabels[4]);
        this.add(modifierLabels[4]);
        this.add(stLabels[4]);
        this.add(chaLabel);
        this.add(valueLabels[5]);
        this.add(modifierLabels[5]);
        this.add(stLabels[5]);
    }
}