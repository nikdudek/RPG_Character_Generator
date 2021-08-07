package com.app.view;

import javax.swing.*;
import java.awt.*;

public class FeatsPanel extends JPanel {

    private static FeatsPanel instance = null;
    public static synchronized FeatsPanel getInstance() {
        if(instance == null)
            instance = new FeatsPanel();
        return instance;
    }

    private JTextArea featsTextArea = new JTextArea();
    private JScrollPane featsPane = new JScrollPane(featsTextArea);

    private FeatsPanel() {

        this.setBackground(new Color(111, 84, 11));
        this.setBounds(0,400,600, 265);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setLayout(new GridLayout(1,1));

        this.add(featsPane);
    }

    public JTextArea getFeatsTextArea() {
        return featsTextArea;
    }
}
