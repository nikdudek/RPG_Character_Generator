package com.app.view;

import javax.swing.JPanel;
import java.awt.Color;

public class BackgroundPanel extends JPanel {

    private static BackgroundPanel instance = null;
    public static synchronized BackgroundPanel getInstance() {
        if(instance == null)
            instance = new BackgroundPanel();
        return instance;
    }

    private BackgroundPanel() {

        this.setBackground(new Color(222, 181, 84));
        this.setBounds(600,400,766, 300);
    }
}
