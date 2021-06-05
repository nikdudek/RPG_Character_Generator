package com.app.view;

import com.app.model.CoreRules;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class SkillsPanel extends JPanel {

    //SINGLETON
    private static SkillsPanel instance = null;
    public static synchronized SkillsPanel getInstance() {
        if(instance == null)
            instance = new SkillsPanel();
        return instance;
    }

    //Instance of CoreRules
    CoreRules coreRules = CoreRules.getInstance();
    //Values:
    public JLabel[] skillsLabels = IntStream.range(0, coreRules.ALL_SKILLS).mapToObj(i -> new JLabel("...")).toArray(JLabel[]::new);


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

        this.add(acrLabel);
        this.add(skillsLabels[0]);
        this.add(medLabel);
        this.add(skillsLabels[9]);
        this.add(aniLabel);
        this.add(skillsLabels[1]);
        this.add(natLabel);
        this.add(skillsLabels[10]);
        this.add(arcLabel);
        this.add(skillsLabels[2]);
        this.add(prcLabel);
        this.add(skillsLabels[11]);
        this.add(athLabel);
        this.add(skillsLabels[3]);
        this.add(prfLabel);
        this.add(skillsLabels[12]);
        this.add(decLabel);
        this.add(skillsLabels[4]);
        this.add(prsLabel);
        this.add(skillsLabels[13]);
        this.add(hisLabel);
        this.add(skillsLabels[5]);
        this.add(relLabel);
        this.add(skillsLabels[14]);
        this.add(insLabel);
        this.add(skillsLabels[6]);
        this.add(sleLabel);
        this.add(skillsLabels[15]);
        this.add(intLabel);
        this.add(skillsLabels[7]);
        this.add(steLabel);
        this.add(skillsLabels[16]);
        this.add(invLabel);
        this.add(skillsLabels[8]);
        this.add(surLabel);
        this.add(skillsLabels[17]);
    }
}
