/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
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
       playerInformationFrame.setSize(width / 3 , height / 3 );
       playerInformationFrame.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
       playerInformationFrame.setLayout(new GridLayout(2,1,100,100));  // seperate it into two parts 
       
       JPanel inputPanel = new JPanel();  
        playerInformationFrame.add(inputPanel);
       inputPanel.setBackground(Color.red);
       inputName= new JTextField("Player Name: ");
       inputPanel.add(inputName);
       
       JButton submitButton = new JButton("Submit");
       playerInformationFrame.add(submitButton);
      
       
       
       
       playerInformationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       playerInformationFrame.setVisible(true);
    }
}
