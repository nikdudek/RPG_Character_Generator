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
    public JLabel[] skillsLabels = IntStream.range(0, coreRules.ALL_SKILLS).mapToObj(i -> new JLabel()).toArray(JLabel[]::new);

    private SkillsPanel() {

        this.setBackground(new Color(182, 144, 51));
        this.setBounds(700,80,666, 320);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(9,4));

        for(JLabel j : skillsLabels) {
            j.setHorizontalAlignment(JLabel.CENTER);
            j.setBorder(BorderFactory.createBevelBorder(1));
        }

        //Titles:
        JLabel acrLabel = new JLabel("Acrobatics:");
        acrLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel aniLabel = new JLabel("Animal Handling:");
        aniLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel arcLabel = new JLabel("Arcana:");
        arcLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel athLabel = new JLabel("Athletics:");
        athLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel decLabel = new JLabel("Deception:");
        decLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel hisLabel = new JLabel("History:");
        hisLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel insLabel = new JLabel("Insight:");
        insLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel intLabel = new JLabel("Intimidation:");
        intLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel invLabel = new JLabel("Investigation:");
        invLabel.setBorder(BorderFactory.createBevelBorder(1));

        JLabel medLabel = new JLabel("Medicine:");
        medLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel natLabel = new JLabel("Nature:");
        natLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel prcLabel = new JLabel("Perception:");
        prcLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel prfLabel = new JLabel("Performance:");
        prfLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel prsLabel = new JLabel("Persuasion:");
        prsLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel relLabel = new JLabel("Religion:");
        relLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel sleLabel = new JLabel("Sleight of Hand:");
        sleLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel steLabel = new JLabel("Stealth:");
        steLabel.setBorder(BorderFactory.createBevelBorder(1));
        JLabel surLabel = new JLabel("Survival:");
        surLabel.setBorder(BorderFactory.createBevelBorder(1));

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
