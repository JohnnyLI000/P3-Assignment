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
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Yuan Hao Li
 */
public class PlayerInformationGUI extends JFrame implements Runnable{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
    private JTextField inputName;
//    private String playerName;
    private JButton submitButton;
    private boolean isClosed = false;

    private Frame frame;
    public  PlayerInformationGUI(JFrame frame)
    {
        this.frame = frame;
        System.out.println("This is player GUI");
        
    }
    public void playerInformationFrame() //;;have not finish this part yet 
    {
        JFrame playerInformationFrame = new JFrame("Player Enter");
        playerInformationFrame.setSize(width / 2, height / 2);
        playerInformationFrame.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
        playerInformationFrame.setLayout(new GridLayout(3, 1));  // seperate it into two parts 

        //Top Panel
        JPanel topPanel = new JPanel();
        JLabel playerName = new JLabel("Player Name:");
        inputName = new JTextField(20);
        playerName.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        topPanel.setBackground(Color.red);
        topPanel.add(playerName);
        topPanel.add(inputName);

        //NEED CENTER PANEL
        JPanel centerPanel = new JPanel();
        JLabel text = new JLabel("");
        text.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        centerPanel.add(text);
        centerPanel.setBackground(Color.yellow);

        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        bottomPanel.setBackground(Color.green);
        bottomPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str;
                do{
                    str = inputName.getText();
                }while(str.contains(" ") || str.equals("")|| str == null);
                
                System.out.println("Name Printing");
                text.setText(str);
                centerPanel.repaint();
                output(str);
                isClosed = true;
                System.out.println("Setted the isclosed = true");
                notifyUser();
                playerInformationFrame.setVisible(false);
                
            }
        });

        //Adding to frame
        playerInformationFrame.add(topPanel, BorderLayout.NORTH);
        playerInformationFrame.add(centerPanel, BorderLayout.CENTER);
        playerInformationFrame.add(bottomPanel, BorderLayout.SOUTH);
        playerInformationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerInformationFrame.setVisible(true);

    }

    public void output(String content) {  //output player information into text 
       int intialScore = 0;
       String space = " ";
        FileWriter fw = null;
        try {
            File f = new File("PlayerInformation.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content + space + intialScore );
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getIsClosed()
    {
        return isClosed;
    }
    
    public String getPlayerName()
    {
        return inputName.getText();
    }
    public synchronized void notifyUser() {
        synchronized(frame)
        {
       System.out.println("notifyyyyy");
       frame.notifyAll();
        }
    }

    @Override
    public void run() {
        this.playerInformationFrame();
    }
}
