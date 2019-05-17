/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Yuan Hao Li
 */
public class ContestGUI extends JFrame{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
    public static Connection conn;
    public static String url = "jdbc:derby://localhost:1527/Question&Answer;create=true";
    public static String username = "johnny";
    public static String password = "johnny";

    private String question = "", a = "", b = "", c = "", d = "", answer = "", hint = "", playerAnswer = "";
    private JPanel informationPanel, questionPanel, userInputPanel, optionsPanel,prizePanel,displayPanel;
    private JButton aButton,bButton,cButton,dButton,hintButton,audienceButton,halfButton;
    private JLabel questionLabel,nextPrize,currentPrize,displayLabel;
    private ImageIcon logoIcon = new ImageIcon("Logo2.png");
    private JTextField inputName;
    private String playerName;
    public boolean check = true;
    
    Color gold = new Color(233, 139, 42);
    Color pink = new Color(254,223,225);
    Color gray = new Color(218,201,166);
    private int questionID = 0;
    private int prize = 0 ,prizeGoal=100;
    public ContestGUI() {
        super();
        startGame();
        playerInformationFrame(); // let the contest frame wait ,by using thread 

    }

    
    public void startGame()
    {
        establishMySQLConnection();
        this.setSize(width / 3 * 2, height / 3 * 2);
        this.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3, 1));
        informationPanel();
        this.add(informationPanel);
        getQA();
        questionPanel();
        userInputPanel();
        this.add(questionPanel);
        this.add(userInputPanel);
        this.setVisible(true);
    }
    
    public void establishMySQLConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(url + "  connected...");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ResultSet getQA() // if it is correct ,get the  qa ; INITIALIZE Q , a,b,c,d,hint,anser  ;; get QA , generate questionpanel and userinput panel. ;; doesnot function the repaint 
    {
        ResultSet rs = null;
        try {
            Statement statement = conn.createStatement();
            questionID++;
            String sqlQuery = "select * from QA where QUESTIONID = " + questionID;
            rs = statement.executeQuery(sqlQuery);
            rs.next();
            question = rs.getString("QUESTION");
            System.out.println(question);
            a = rs.getString(3);
            b = rs.getString(4);
            c = rs.getString(5);
            d = rs.getString(6);
            answer = rs.getString(7);
            hint = rs.getString(8);
            prizeCount(questionID);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return (rs);
    }

    public void informationPanel() {
        informationPanel = new JPanel();
        informationPanel.setBackground(Color.blue);
        informationPanel.setLayout(new GridLayout(1, 3));
        displayPanel();
        prizePanel();
        informationPanel.add(scorePanel());
        informationPanel.add(displayPanel);
        informationPanel.add(prizePanel);
    }

    public void userInputPanel() {
        userInputPanel = new JPanel();
        userInputPanel.setBackground(Color.green);
        userInputPanel.setLayout(new BorderLayout());
        optionsPanel();
        userInputPanel.add(optionsPanel);
        userInputPanel.add(helpPanel(), BorderLayout.EAST);
    }

    public void displayPanel() {// display panel shows : who wants to be millionare and hints message 
        displayPanel = new JPanel();
        displayLabel = new JLabel();
        displayLabel.setIcon(logoIcon);
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel,BorderLayout.CENTER);
        displayPanel.setBackground(gray);
    }

    public JPanel scorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.red);
        return scorePanel;
    }

  public void prizePanel() {
        // Panel Creation
        // Create the JLabel
        // Put it in the grid layout of 1 and 2
        // Set the text of prize
        // Set text for the nextPrize
        prizePanel = new JPanel();
        currentPrize = new JLabel("Current Prize: "+String.valueOf(prize));
        nextPrize = new JLabel("Prize Goal: "+String.valueOf(prizeGoal));
        currentPrize.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        nextPrize.setFont(new Font("Comic Sans MS", Font.BOLD, 44));

        prizePanel.setLayout(new GridLayout(2,1));
        prizePanel.setBackground(gray);
        //currentPrize.setText("Current Prize: " + String.valueOf(prize));
        //nextPrize.setText("Next Prize: " + String.valueOf(prize * 2));
        prizePanel.add(currentPrize);
        prizePanel.add(nextPrize);
    }


    public void questionPanel() { // win: repaint    BUGS: the font is too big  ;;doesnot do the repaint
        questionPanel = new JPanel();
        questionPanel.setBackground(gray);
        questionPanel.setLayout(new BorderLayout());
       
        questionLabel = new JLabel();

        questionLabel.setText(question);
        questionLabel.setPreferredSize(new Dimension(width , height/3));
        questionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        questionLabel.setForeground(Color.black);
        
        questionPanel.add(questionLabel);
    }

    public void optionsPanel() // when press a button , pass the buttons value , check the answer wether if its correct 
    {
        optionsPanel = new JPanel();
        optionsPanel.setBackground(gray);
        optionsPanel.setLayout(new GridLayout(2, 2, 20, 20));
         aButton = new JButton(a);
        bButton = new JButton(b);
        cButton = new JButton(c);
         dButton = new JButton(d);

        optionButtonsMouseListener optionButtonListener = new optionButtonsMouseListener();
        
        aButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        aButton.setBackground(Color.white);
        aButton.setForeground(Color.black);
        aButton.setBorder(new LineBorder(Color.BLACK, 4));
        aButton.addMouseListener(optionButtonListener);
        bButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        bButton.setBackground(Color.white);
        bButton.setForeground(Color.black);
        bButton.setBorder(new LineBorder(Color.BLACK, 4));
        bButton.addMouseListener(optionButtonListener);
        cButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        cButton.setBackground(Color.white);
        cButton.setForeground(Color.black);
        cButton.setBorder(new LineBorder(Color.BLACK, 4));
        cButton.addMouseListener(optionButtonListener);
        dButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        dButton.setBackground(Color.white);
        dButton.setForeground(Color.black);
        dButton.setBorder(new LineBorder(Color.BLACK, 4));
        dButton.addMouseListener(optionButtonListener);
        
        optionsPanel.add(aButton);
        optionsPanel.add(bButton);
        optionsPanel.add(cButton);
        optionsPanel.add(dButton);
    }

    private class optionButtonsMouseListener implements MouseListener {

        JButton jb;

        @Override
        public void mouseClicked(MouseEvent e) {
            jb = (JButton) e.getSource();
            playerAnswer = jb.getText();
            check = checkAnswer(playerAnswer.charAt(0));
            System.out.println(checkAnswer(playerAnswer.charAt(0)));
            if (check) {
                getQA();
                questionLabel.setText(question);
                aButton.setText(a);  
                bButton.setText(b);
                cButton.setText(c);
                dButton.setText(d);
                questionPanel.revalidate();
                
                currentPrize.setText("Current Prize: "+ String.valueOf(prize));
                nextPrize.setText("Prize Goal: "+String.valueOf(prizeGoal));
                prizePanel.revalidate();
                
                displayLabel.setText("");
                displayLabel.setIcon(logoIcon);
                displayPanel.revalidate();
               
            }
            else
            {
                exitGame();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jb = (JButton) e.getSource();
            jb.setBackground(Color.black);
            jb.setForeground(gold);
            jb.setBorder(new LineBorder(gold, 4));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            jb = (JButton) e.getSource();
            jb.setBackground(Color.white);
            jb.setForeground(Color.black);
            jb.setBorder(new LineBorder(Color.black, 4));
        }
    }
    private class helpButtonsMouseListener implements MouseListener
    {

        JButton jb;
        @Override
        public void mouseClicked(MouseEvent e) {
            jb = (JButton) e.getSource();
            if(jb.equals(hintButton))
            {
                System.out.println("hii");
                
                displayPanel.removeAll();
                displayLabel = new JLabel(hint);
                displayLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
                displayPanel.add(displayLabel);
                displayPanel.revalidate();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

         @Override
         public void mouseEntered(MouseEvent e) {
            jb = (JButton) e.getSource();
            jb.setBackground(Color.black);
            jb.setForeground(gold);
            jb.setBorder(new LineBorder(gold, 4));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            jb = (JButton) e.getSource();
            jb.setBackground(Color.white);
            jb.setForeground(Color.black);
            jb.setBorder(new LineBorder(Color.black, 4));
        }
    }
    public JPanel helpPanel() {
        JPanel helpPanel = new JPanel();
        helpPanel.setBackground(gray);
        hintButton = new JButton("     Hint     ");
        audienceButton = new JButton("     Audience     ");
        halfButton = new JButton("     Half Half     ");
        
        helpButtonsMouseListener helpButtonsMouseListener = new helpButtonsMouseListener();

        hintButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        hintButton.setBackground(Color.white);
        hintButton.setForeground(Color.black);
        hintButton.setBorder(new LineBorder(Color.BLACK, 4));
        hintButton.addMouseListener(helpButtonsMouseListener);
        audienceButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        audienceButton.setBackground(Color.white);
        audienceButton.setForeground(Color.black);
        audienceButton.setBorder(new LineBorder(Color.BLACK, 4));
        audienceButton.addMouseListener(helpButtonsMouseListener);
        halfButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        halfButton.setBackground(Color.white);
        halfButton.setForeground(Color.black);
        halfButton.setBorder(new LineBorder(Color.BLACK, 4));
        halfButton.addMouseListener(helpButtonsMouseListener);

        
        
        helpPanel.setLayout(new GridLayout(3, 1, 0, 20));
        helpPanel.add(hintButton);
        helpPanel.add(audienceButton);
        helpPanel.add(halfButton);
        return helpPanel;
    }

    public boolean checkAnswer(char playerAnswer) {
        return playerAnswer == answer.charAt(0);
    }

    public void exitGame()
    {
        System.out.println("You got : "+prize +"    Your goal :"+prizeGoal);
        System.exit(0);
        this.dispose();
        this.setVisible(false);
    }
    public void prizeCount(int questionNo)  // start from 100 , next question is a double the prize of the privous one 
    {
        prize= 0 ;
        prizeGoal= 100;
        for(int i = 1;i <questionNo; i++)
        {
            prize+=prizeGoal;
            prizeGoal *=2;
        }
    }
    
    public void playerInformationFrame()
    {
       JFrame playerInformationFrame = new JFrame();
       playerInformationFrame.setSize(width / 3 , height / 3 );
       playerInformationFrame.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
       playerInformationFrame.setLayout(new GridLayout(2,1,100,100));
         JPanel inputPanel = new JPanel();  
         playerInformationFrame.add(inputPanel);
         inputPanel.setBackground(Color.red);
       inputName= new JTextField("Player Name: ");

       JButton submitButton = new JButton("Submit");
       
      
       playerInformationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       playerInformationFrame.setVisible(true);
    }
    public static void main(String[] args) {

        ContestGUI contestFrame = new ContestGUI();
    }
}
