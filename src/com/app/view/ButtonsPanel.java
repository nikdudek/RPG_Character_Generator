package com.app.view;

import com.app.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel implements ActionListener {

    private static ButtonsPanel instance = null;
    public static synchronized ButtonsPanel getInstance() {
        if(instance == null)
            instance = new ButtonsPanel();
        return instance;
    }

    private ButtonsPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(600,400,766, 265);
        this.setLayout(new GridLayout(4,1));

        //"Roll Attributes" Button:
        JButton rollButton = new JButton("Roll Attributes!");
        rollButton.setFocusable(false);
        rollButton.setFont(rollButton.getFont().deriveFont(rollButton.getFont().getStyle() | Font.ITALIC));
        rollButton.setActionCommand("rollButton");
        rollButton.addActionListener(this);

        //"Roll All" Button:
        JButton rollAllButton = new JButton("Roll All!!");
        rollAllButton.setFocusable(false);
        rollAllButton.setFont(rollButton.getFont().deriveFont(rollButton.getFont().getStyle() | Font.ITALIC));
        rollAllButton.setActionCommand("rollAllButton");
        rollAllButton.addActionListener(this);

        //"Dice Roller" Button:
        JButton diceRollerButton = new JButton("Open Dice Roller");
        rollButton.setFocusable(false);
        diceRollerButton.setActionCommand("diceItem");
        diceRollerButton.addActionListener(this);

        //"Exit" Button:
        JButton exitButton = new JButton("Exit");
        rollButton.setFocusable(false);
        exitButton.setActionCommand("exitButton");
        exitButton.addActionListener(this);

        //Adding Buttons to Panel:
        this.add(rollButton);
        this.add(rollAllButton);
        this.add(diceRollerButton);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "rollButton" -> {
                Controller controller = Controller.getInstance();
                controller.rollAttributes();
            }
            case "rollAllButton" -> {
                Controller controller = Controller.getInstance();
                controller.rollAll();
            }
            case "exitButton" -> System.exit(0);
            case "diceItem" -> {
                DiceRollerFrame diceFrame = DiceRollerFrame.getSeparate();
            }
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}
