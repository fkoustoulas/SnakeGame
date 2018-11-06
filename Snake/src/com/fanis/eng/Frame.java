
package com.fanis.eng;

import java.awt.GridLayout;
import javax.swing.JFrame;


public class Frame extends JFrame{
    
    public Frame(){
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setVisible(true);
        setResizable(false);
        setFocusable(true);
        
        init();
    }
    private void init(){
        setLayout(new GridLayout(1, 1, 0, 0));
        Screen s = new Screen();
        addKeyListener(s.key);
        add(s);
        setLocationRelativeTo(null);
        pack();
    }
    
    public static void main(String args[]){
        Frame f = new Frame();
    }
}
