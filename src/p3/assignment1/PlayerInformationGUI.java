/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Yuan Hao Li
 */
public class PlayerInformationGUI extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;

    private JTextField inputName;
    private String playerName;

    public void playerInformationFrame() //;;have not finish this part yet 
    {
        JFrame playerInformationFrame = new JFrame("Player Enter");
        playerInformationFrame.setSize(width / 2, height / 2);
        playerInformationFrame.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
        playerInformationFrame.setLayout(new GridLayout(2, 1, 100, 100));  // seperate it into two parts 

        JPanel topPanel = new JPanel();
        JLabel playerName = new JLabel("Player Name:");
        inputName = new JTextField(20);
        
        playerName.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        topPanel.setBackground(Color.red);
        topPanel.add(playerName);
        topPanel.add(inputName);

//        playerInformationFrame.add(topPanel);
////        playerInformationFrame.add(playerName);
//       topPanel.setBackground(Color.red);
//       inputName= new JTextField();
//       topPanel.add(inputName);
        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        bottomPanel.setBackground(Color.green);
        bottomPanel.add(submitButton);
        
        playerInformationFrame.add(topPanel, BorderLayout.NORTH);
        playerInformationFrame.add(bottomPanel, BorderLayout.SOUTH);
        playerInformationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerInformationFrame.setVisible(true);
    }
}
