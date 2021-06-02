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

    //Instance of characterSheet:
    CharacterSheet characterSheet = CharacterSheet.getInstance();

    //Titles:
    JLabel iniLabel = new JLabel("Initiative:");
    JLabel speLabel = new JLabel("Speed:");
    JLabel proLabel = new JLabel("Proficiency:");
    JLabel hidLabel = new JLabel("Hit Dice:");
    JLabel hipLabel = new JLabel("Hit Points:");
    JLabel armLabel = new JLabel("Armor Class:");

    //Values:
    JLabel iniValLabel = new JLabel("...");
    JLabel speValLabel = new JLabel("...");
    JLabel proValLabel = new JLabel("...");
    JLabel hidValLabel = new JLabel("...");
    JLabel hipValLabel = new JLabel("...");
    JLabel armValLabel = new JLabel("...");

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

    public void refreshValues() {
        iniValLabel.setText(String.valueOf(characterSheet.getInitiative()));
        speValLabel.setText(String.valueOf(characterSheet.getSpeed()));
        proValLabel.setText(String.valueOf(characterSheet.getProficiency()));
        hidValLabel.setText(characterSheet.getHitDiceCount() + "d" + characterSheet.getHitDiceType());
        hipValLabel.setText(String.valueOf(characterSheet.getHitPointsMax()));
        armValLabel.setText(String.valueOf(characterSheet.getArmorClass()));
    }
}