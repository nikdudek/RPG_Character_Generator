package com.app.view;

import com.app.controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        this.setTitle("RPG Character Generator"); //sets title of main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits from an app by clicking "X" button
        this.setResizable(false); //prevents frame from being resized
        this.setSize(1366,700); //sets the dimensions of a frame
        this.setLayout(null); //no layout
        this.setVisible(true); //makes the frame visible


        //Adding panels to frame
        InfoPanel infoPanel = InfoPanel.getInstance();
        this.add(infoPanel);

        CombatValuesPanel combatValuesPanel = CombatValuesPanel.getInstance();
        this.add(combatValuesPanel);

        AttributePanel attributePanel = AttributePanel.getInstance();
        this.add(attributePanel);

        SkillsPanel skillsPanel = SkillsPanel.getInstance();
        this.add(skillsPanel);

        FeatsPanel featsPanel = FeatsPanel.getInstance();
        this.add(featsPanel);

        ButtonsPanel buttonsPanel = ButtonsPanel.getInstance();
        this.add(buttonsPanel);

        Controller controller = Controller.getInstance();
        controller.setAllDefault();
    }
}
