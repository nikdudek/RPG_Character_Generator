package com.app.view;

import javax.swing.JPanel;
import java.awt.Color;

public class FeatsPanel extends JPanel {

    private static FeatsPanel instance = null;
    public static synchronized FeatsPanel getInstance() {
        if(instance == null)
            instance = new FeatsPanel();
        return instance;
    }

    private FeatsPanel() {

        this.setBackground(new Color(111, 84, 11));
        this.setBounds(0,400,600, 300);
    }
}
