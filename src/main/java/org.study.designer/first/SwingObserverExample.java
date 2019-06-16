package org.study.designer.first;

import org.omg.CORBA.FREE_MEM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ExpanseWong
 */
public class SwingObserverExample {
    JFrame frame;

    public static void main(String[] args){
        SwingObserverExample example = new SwingObserverExample();
        example.go();
    }

    public void go(){
        frame = new JFrame();

        JButton button = new JButton("Should I do it?");
        button.addActionListener(new AngeListener());
        button.addActionListener(new DevilListener());
        frame.getContentPane().add(BorderLayout.CENTER,button);
        frame.setBounds(400,200,700,700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    class AngeListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Don't do id ,you might regret it!");
        }
    }

    class DevilListener implements ActionListener{

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Come on ,do it!");
        }
    }
}
