package com.app.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame implements ActionListener {

    //Menu Bar:
    JMenuBar optionsMenu = new JMenuBar();

    //Menus:
    JMenu fileMenu = new JMenu("File");
    JMenu pagesMenu = new JMenu("Pages");
    JMenu toolsMenu = new JMenu("Tools");
    JMenu aboutMenu = new JMenu("About");

    //File - items:
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem exitItem = new JMenuItem("Exit");

    //Pages - items:
    JMenuItem mainItem = new JMenuItem("Main page");
    JMenuItem bioItem = new JMenuItem("Second page");
    JMenuItem inventoryItem = new JMenuItem("Inventory");

    //Tools - items:
    JMenuItem diceItem = new JMenuItem("Dice roller");


    public MainFrame() {
        this.setTitle("RPG Character Generator"); //sets title of main frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits from an app by clicking "X" button
        this.setResizable(false); //prevents frame from being resized
        this.setSize(1366,768); //sets the dimensions of a frame
        this.setLayout(null); //no layout
        this.setVisible(true); //makes the frame visible


        //Adding ActionListener to items
        newItem.setActionCommand("newItem");
        newItem.addActionListener(this);

        saveItem.setActionCommand("saveItem");
        saveItem.addActionListener(this);

        loadItem.setActionCommand("loadItem");
        loadItem.addActionListener(this);

        exitItem.setActionCommand("exitItem");
        exitItem.addActionListener(this);

        //Mnemonic function for menus:
        fileMenu.setMnemonic(KeyEvent.VK_F);
        newItem.setMnemonic(KeyEvent.VK_N);
        saveItem.setMnemonic(KeyEvent.VK_S);
        loadItem.setMnemonic(KeyEvent.VK_L);
        exitItem.setMnemonic(KeyEvent.VK_E);

        pagesMenu.setMnemonic(KeyEvent.VK_P);

        toolsMenu.setMnemonic(KeyEvent.VK_T);
        diceItem.setMnemonic(KeyEvent.VK_D);

        aboutMenu.setMnemonic(KeyEvent.VK_A);


        //Adding items to File
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);

        //Adding items to Pages
        pagesMenu.add(mainItem);
        pagesMenu.add(bioItem);
        pagesMenu.add(inventoryItem);

        //Adding items to Tools
        toolsMenu.add(diceItem);


        //Adding menus to menu bar
        optionsMenu.add(fileMenu);
        optionsMenu.add(pagesMenu);
        optionsMenu.add(toolsMenu);
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

        SpellsPanel spellsPanel = new SpellsPanel();
        this.add(spellsPanel);
    }

    // ActionListener commands below:
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "loadItem" -> {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null); //CHOOSE PARENT
                //3:12:19 -> zapisywanie i wczytywanie
            }
            case "saveItem" -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null); //CHOOSE PARENT
            }
            case "exitItem" -> System.exit(0);
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}
