package com.app.view;

import com.app.model.CharacterSheet;

import javax.swing.*;
import java.awt.*;

public class SkillsPanel extends JPanel {

    //SINGLETON
    private static SkillsPanel instance = null;
    public static synchronized SkillsPanel getInstance() {
        if(instance == null)
            instance = new SkillsPanel();
        return instance;
    }

    //Instance of characterSheet:
    CharacterSheet characterSheet = CharacterSheet.getInstance();

    private SkillsPanel() {

        this.setBackground(new Color(182, 144, 51));
        this.setBounds(700,80,666, 320);
        this.setLayout(new GridLayout(9,4));

        //Titles:
        JLabel acrLabel = new JLabel("Acrobatics:");
        JLabel aniLabel = new JLabel("Animal Handling:");
        JLabel arcLabel = new JLabel("Arcana:");
        JLabel athLabel = new JLabel("Athletics:");
        JLabel decLabel = new JLabel("Deception:");
        JLabel hisLabel = new JLabel("History:");
        JLabel insLabel = new JLabel("Insight:");
        JLabel intLabel = new JLabel("Intimidation:");
        JLabel invLabel = new JLabel("Investigation:");

        JLabel medLabel = new JLabel("Medicine:");
        JLabel natLabel = new JLabel("Nature:");
        JLabel prcLabel = new JLabel("Perception:");
        JLabel prfLabel = new JLabel("Performance:");
        JLabel prsLabel = new JLabel("Persuasion:");
        JLabel relLabel = new JLabel("Religion:");
        JLabel sleLabel = new JLabel("Sleight of Hand:");
        JLabel steLabel = new JLabel("Stealth:");
        JLabel surLabel = new JLabel("Survival:");

        //Values:
        JLabel v01Label = new JLabel("...");
        JLabel v02Label = new JLabel("...");
        JLabel v03Label = new JLabel("...");
        JLabel v04Label = new JLabel("...");
        JLabel v05Label = new JLabel("...");
        JLabel v06Label = new JLabel("...");
        JLabel v07Label = new JLabel("...");
        JLabel v08Label = new JLabel("...");
        JLabel v09Label = new JLabel("...");
        JLabel v10Label = new JLabel("...");
        JLabel v11Label = new JLabel("...");
        JLabel v12Label = new JLabel("...");
        JLabel v13Label = new JLabel("...");
        JLabel v14Label = new JLabel("...");
        JLabel v15Label = new JLabel("...");
        JLabel v16Label = new JLabel("...");
        JLabel v17Label = new JLabel("...");
        JLabel v18Label = new JLabel("...");

        //Adding elements:
        this.add(acrLabel);
        this.add(v01Label);
        this.add(medLabel);
        this.add(v10Label);
        this.add(aniLabel);
        this.add(v02Label);
        this.add(natLabel);
        this.add(v11Label);
        this.add(arcLabel);
        this.add(v03Label);
        this.add(prcLabel);
        this.add(v12Label);
        this.add(athLabel);
        this.add(v04Label);
        this.add(prfLabel);
        this.add(v13Label);
        this.add(decLabel);
        this.add(v05Label);
        this.add(prsLabel);
        this.add(v14Label);
        this.add(hisLabel);
        this.add(v06Label);
        this.add(relLabel);
        this.add(v15Label);
        this.add(insLabel);
        this.add(v07Label);
        this.add(sleLabel);
        this.add(v16Label);
        this.add(intLabel);
        this.add(v08Label);
        this.add(steLabel);
        this.add(v17Label);
        this.add(invLabel);
        this.add(v09Label);
        this.add(surLabel);
        this.add(v18Label);
    }
}
