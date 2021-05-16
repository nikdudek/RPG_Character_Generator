package com.app.view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    //Menu Bar:
    JMenuBar optionsMenu = new JMenuBar();

    //Menus:
    JMenu fileMenu = new JMenu("File");
    JMenu pagesMenu = new JMenu("Pages");
    JMenu aboutMenu = new JMenu("About");

    //File - items:
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem exitItem = new JMenuItem("Exit");

    //Pages - items:
    JMenuItem mainItem = new JMenuItem("Main page");
    JMenuItem bioItem = new JMenuItem("Second page");
    JMenuItem spellsItem = new JMenuItem("Spells");


    public MainFrame() {
        this.setTitle("RPG Character Generator"); //sets title of main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits from an app by clicking "X" button
        this.setResizable(false); //prevents frame from being resized
        this.setSize(800,600); //sets the dimensions of a frame
        this.setLayout(null); //no layout
        this.setVisible(true); //makes the frame visible


        //Adding ActionListener to items
        saveItem.setActionCommand("saveItem");
        saveItem.addActionListener(this);

        loadItem.setActionCommand("loadItem");
        loadItem.addActionListener(this);

        exitItem.setActionCommand("exitItem");
        exitItem.addActionListener(this);


        //Adding items to File
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);

        //Adding items to Pages
        pagesMenu.add(mainItem);
        pagesMenu.add(bioItem);
        pagesMenu.add(spellsItem);


        //Adding menus to menu bar
        optionsMenu.add(fileMenu);
        optionsMenu.add(pagesMenu);
        optionsMenu.add(aboutMenu);


        //Adding panels to frame
        this.setJMenuBar(optionsMenu);

        InfoPanel infoPanel = new InfoPanel();
        this.add(infoPanel);

        CombatValuesPanel combatValuesPanel = new CombatValuesPanel();
        this.add(combatValuesPanel);

        AttributePanel attributePanel = new AttributePanel();
        this.add(attributePanel);

        SkillsPanel skillsPanel = new SkillsPanel();
        this.add(skillsPanel);

        WeaponsPanel weaponsPanel = new WeaponsPanel();
        this.add(weaponsPanel);

        InventoryPanel inventoryPanel = new InventoryPanel();
        this.add(inventoryPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "loadItem" -> System.out.println("Loading..");
            case "saveItem" -> System.out.println("Saving..");
            case "exitItem" -> System.exit(0);
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}
