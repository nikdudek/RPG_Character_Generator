package com.app.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.app.model.DiceRoller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceRollerFrame implements ActionListener {

    JFrame frame = new JFrame("Dice Roller");
    DiceRoller diceRoller = new DiceRoller();

    //Buttons & Text Fields declaration:
    JButton d2Button = new JButton("Roll 2-sided Dice");
    JTextField d2Field = new JTextField();

    JButton d3Button = new JButton("Roll 3-sided Dice");
    JTextField d3Field = new JTextField();

    JButton d4Button = new JButton("Roll 4-sided Dice");
    JTextField d4Field = new JTextField();

    JButton d6Button = new JButton("Roll 6-sided Dice");
    JTextField d6Field = new JTextField();

    JButton d8Button = new JButton("Roll 8-sided Dice");
    JTextField d8Field = new JTextField();

    JButton d10Button = new JButton("Roll 10-sided Dice");
    JTextField d10Field = new JTextField();

    JButton d12Button = new JButton("Roll 12-sided Dice");
    JTextField d12Field = new JTextField();

    JButton d20Button = new JButton("Roll 20-sided Dice");
    JTextField d20Field = new JTextField();

    DiceRollerFrame() {

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,520);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        //Buttons & Text Fields:
        d2Button.setBounds(0,0,250,60);
        d2Field.setBounds(251,0,49,60);

        d3Button.setBounds(0,60,250,60);
        d3Field.setBounds(251,60,49,60);

        d4Button.setBounds(0,120,250,60);
        d4Field.setBounds(251,120,49,60);

        d6Button.setBounds(0,180,250,60);
        d6Field.setBounds(251,180,49,60);

        d8Button.setBounds(0,240,250,60);
        d8Field.setBounds(251,240,49,60);

        d10Button.setBounds(0,300,250,60);
        d10Field.setBounds(251,300,49,60);

        d12Button.setBounds(0,360,250,60);
        d12Field.setBounds(251,360,49,60);

        d20Button.setBounds(0,420,250,60);
        d20Field.setBounds(251,420,49,60);


        //Adding ActionListener to buttons:
        d2Button.setActionCommand("d2Button");
        d2Button.addActionListener(this);

        d3Button.setActionCommand("d3Button");
        d3Button.addActionListener(this);

        d4Button.setActionCommand("d4Button");
        d4Button.addActionListener(this);

        d6Button.setActionCommand("d6Button");
        d6Button.addActionListener(this);

        d8Button.setActionCommand("d8Button");
        d8Button.addActionListener(this);

        d10Button.setActionCommand("d10Button");
        d10Button.addActionListener(this);

        d12Button.setActionCommand("d12Button");
        d12Button.addActionListener(this);

        d20Button.setActionCommand("d20Button");
        d20Button.addActionListener(this);

        //Adding elements:
        frame.add(d2Button);
        frame.add(d2Field);

        frame.add(d3Button);
        frame.add(d3Field);

        frame.add(d4Button);
        frame.add(d4Field);

        frame.add(d6Button);
        frame.add(d6Field);

        frame.add(d8Button);
        frame.add(d8Field);

        frame.add(d10Button);
        frame.add(d10Field);

        frame.add(d12Button);
        frame.add(d12Field);

        frame.add(d20Button);
        frame.add(d20Field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "d2Button" -> d2Field.setText(String.valueOf(diceRoller.d2()));
            case "d3Button" -> d3Field.setText(String.valueOf(diceRoller.d3()));
            case "d4Button" -> d4Field.setText(String.valueOf(diceRoller.d4()));
            case "d6Button" -> d6Field.setText(String.valueOf(diceRoller.d6()));
            case "d8Button" -> d8Field.setText(String.valueOf(diceRoller.d8()));
            case "d10Button" -> d10Field.setText(String.valueOf(diceRoller.d10()));
            case "d12Button" -> d12Field.setText(String.valueOf(diceRoller.d12()));
            case "d20Button" -> d20Field.setText(String.valueOf(diceRoller.d20()));
            default -> throw new IllegalStateException("Unexpected value: " + e.getActionCommand());
        }
    }
}
