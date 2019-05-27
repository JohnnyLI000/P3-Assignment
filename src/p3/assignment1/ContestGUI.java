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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
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
public class ContestGUI extends JFrame implements Runnable {
//testo

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;
    public static Connection conn;
    public static String url = "jdbc:derby:Question&Answer;create=true";
    public static String username = "johnny";
    public static String password = "johnny";

    private String question = "", a = "", b = "", c = "", d = "", answer = "", hint = "", playerAnswer = "", audience = "Ask Someone Next To You";
    private JPanel informationPanel, questionPanel, userInputPanel, optionsPanel, prizePanel, displayPanel;
    private JButton aButton, bButton, cButton, dButton, hintButton, audienceButton, halfButton;
    private JLabel questionLabel, nextPrize, currentPrize, displayLabel, playerLabel;
    private ImageIcon logoIcon = new ImageIcon(new ImageIcon("Logo2.png").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
    private JTextField inputName;
    private String playerName;
    public boolean check = true;
    PlayerInformationGUI playerGUI;
    Color gold = new Color(233, 139, 42);
    Color pink = new Color(254, 223, 225);
    Color gray = new Color(218, 201, 166);
    private int questionID = 0;
    private int prize = 0, prizeGoal = 100;
    private String[] playerArray; // top 3 players

    public ContestGUI() {
        super();
        playerGUI = new PlayerInformationGUI(this);
    }

    public void startGame() // generate the Game frame and connect to the database
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

    public void run() {
        synchronized (this) {
            if (!playerGUI.getIsClosed()) {
                try {
                    System.out.println("contest start to wait");
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("finished waitting");
            this.startGame();
        }
    }

    public void establishMySQLConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println(url + "  connected...");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ResultSet getQA() {
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
        displayPanel.add(displayLabel, BorderLayout.CENTER);
        displayPanel.setBackground(gray);
    }

    public JPanel scorePanel() { //NEEDS TO BE DONE , this display the player previous score and the top 3 
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(4, 1));
        playerLabel = new JLabel();
        playerLabel.setText("Current Player: " + playerGUI.getPlayerName());
        playerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        scorePanel.setBackground(gray);
        scorePanel.add(playerLabel);
//        findTheTop3();

        JLabel top1Label = new JLabel("Top 1 ");
        JLabel top2Label = new JLabel("Top 2 ");
        JLabel top3Label = new JLabel("Top 3 ");
        top1Label.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        top2Label.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        top3Label.setFont(new Font("Comic Sans MS", Font.BOLD, 44));

        scorePanel.add(top1Label);
        scorePanel.add(top2Label);
        scorePanel.add(top3Label);

        return scorePanel;
    }

    public void prizePanel() {
        prizePanel = new JPanel();
        currentPrize = new JLabel("Current Prize: " + String.valueOf(prize));
        nextPrize = new JLabel("Prize Goal: " + String.valueOf(prizeGoal));
        currentPrize.setFont(new Font("Comic Sans MS", Font.BOLD, 44));
        nextPrize.setFont(new Font("Comic Sans MS", Font.BOLD, 44));

        prizePanel.setLayout(new GridLayout(2, 1));
        prizePanel.setBackground(gray);
        prizePanel.add(currentPrize);
        prizePanel.add(nextPrize);
    }

    public void questionPanel() {
        questionPanel = new JPanel();
        questionPanel.setBackground(gray);
        questionPanel.setLayout(new BorderLayout());

        questionLabel = new JLabel();

        questionLabel.setText(question);
        questionLabel.setPreferredSize(new Dimension(width, height / 3));
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

                currentPrize.setText("Current Prize: " + String.valueOf(prize));
                nextPrize.setText("Prize Goal: " + String.valueOf(prizeGoal));
                prizePanel.revalidate();

                displayLabel.setText("");
                displayLabel.setIcon(logoIcon);
                displayPanel.revalidate();

            } else {
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

    private class helpButtonsMouseListener implements MouseListener // have not done the Audience and half half yet 
    {

        JButton jb;

        @Override
        public void mouseClicked(MouseEvent e) {
            jb = (JButton) e.getSource();
            if (jb.equals(hintButton)) {
                System.out.println("Hint Pressed");

                displayPanel.removeAll();
                displayLabel = new JLabel(hint);
                displayLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
                displayPanel.add(displayLabel);
                displayPanel.revalidate();
            } else if (jb.equals(audienceButton)) // display audience panel
            {
                System.out.println("Auidence Pressed");

                displayPanel.removeAll();
                displayLabel = new JLabel(audience);
                displayLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
                displayPanel.add(displayLabel);
                displayPanel.revalidate();
            } else if (jb.equals(halfButton)) // display audience panel
            {
                System.out.println("HalfButton Pressed");

                displayPanel.removeAll();
//                Random random = new Random();
//                int optionNum;
//                boolean success = false;
//                do {
//                    optionNum = random.nextInt(4);
//                    switch (optionNum) {
//                        case 0:
//                            if (a.charAt(0) != answer.charAt(8)) {
//                                System.out.println(a.charAt(0));
//                                System.out.println(answer.charAt(8));
//                                success = true;
//                                break;
//
//                            }
//                        case 1:
//                            if (b.charAt(0) != answer.charAt(8)) {
//                                System.out.println(b.charAt(0));
//                                System.out.println(answer.charAt(8));
//                                success = true;
//                                break;
//                            }
//                        case 2:
//                            if (c.charAt(0) != answer.charAt(8)) {
//                                System.out.println(c.charAt(0));
//                                System.out.println(answer.charAt(8));
//                                success = true;
//                                break;
//
//                            }
//                        case 3:
//                            if (d.charAt(0) != answer.charAt(8)) {
//                                System.out.println(d.charAt(0));
//                                System.out.println(answer.charAt(8));
//                                success = true;
//                                break;
//
//                            }
//                    }
//                } while (success == false);
//                displayLabel = new JLabel(audience);
//                displayLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
//                displayPanel.add(displayLabel);
//                displayPanel.revalidate();
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

    public void exitGame() {
        System.out.println("You got : " + prize + "    Your goal :" + prizeGoal);
        outputTheScore(prize);
        System.exit(0);
        this.dispose();
        this.setVisible(false);
    }

    public void outputTheScore(int score) {
        try {
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("PlayerInformation.txt", true));
            outputStream.println(":" + score);
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file out.txt." + e.getMessage());
        }

    }

    public void prizeCount(int questionNo) // start from 100 , next question is a double the prize of the privous one 
    {
        prize = 0;
        prizeGoal = 100;
        for (int i = 1; i < questionNo; i++) {
            prize += prizeGoal;
            prizeGoal *= 2;
        }
    }

    public static Map<String, Integer> readPlayerInfo() {
        Map<String, Integer> playerFile = new HashMap<>();
        BufferedReader br = null;
        try {
            //read scores txt file
            br = new BufferedReader(new FileReader("PlayerInformation.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitString = line.split(" ");
                playerFile.put(splitString[0], Integer.valueOf(splitString[1]));
            }
        } catch (IOException ex) {
            Logger.getLogger(ContestGUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(ContestGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return playerFile;
    }

    public static void writeToPlayerInfo(Map<String, Integer> records) {
        PrintWriter pw = null;
        try {
            //create scores txt file
            pw = new PrintWriter("PlayerInformation.txt");
            for (Map.Entry<String, Integer> recordEntry : records.entrySet()) {
                pw.println(recordEntry.getKey() + " " + recordEntry.getValue());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContestGUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
    
    public static String parseInput(Scanner scanner,Map<String,Integer> records){
        String input = scanner.nextLine();
            writeToPlayerInfo(records);
            System.out.println("File saved.");
            System.exit(0);
        return input;
    }
    
//    public void findTheTop3() {
//        int top1=0, top2=0, top3=0;
//        ArrayList<Integer> scoreList = new ArrayList<Integer>();
//        playerArray = new String[3];
//
//        //try to find the best 3 player's scores 
//        try {
//            FileReader fr = new FileReader("PlayerInformation.txt");
//            BufferedReader inputStream = new BufferedReader(fr);
//            String line = null;
//            while ((line = inputStream.readLine()) != null) {
//                for (int i = 0; i < line.length(); i++) {
//                    if (line.charAt(i) == ':') // for example  Johnny Li,1000   : comma behind is score
//                    {
//                        try {
//                            System.out.println(line.substring(0, line.length()));
//                            Integer score = Integer.valueOf(line.substring(i + 1, line.length()));
//                            scoreList.add(score);
//                        } catch (NumberFormatException e) {
//                            System.err.println(e);
//                        }
//                    }
//                }
//
//            }
//            inputStream.close();
//
//        } catch (IOException e) {
//            System.out.println("File  not found.");
//        }
//
//        
//        Collections.sort(scoreList);//BUG if its below 3 players 
//      for(int i = 0 ;i<scoreList.size();i++)
//        {
//            System.out.println("Score : "+i +":  "+scoreList.get(i));
//        }
//      
//      
//        if (!scoreList.isEmpty()) {
//            top1 = scoreList.get(scoreList.size()-1);
//            if (scoreList.size()>1) {
//                top2 = scoreList.get(scoreList.size()-2);
//                if (scoreList.size()>2) {
//                    top3 = scoreList.get(scoreList.size()-3);
//                }
//            }
//
//        }
//        // try to find the best three player's name 
//        try {
//            FileReader fr = new FileReader("PlayerInformation.txt");
//            BufferedReader inputStream = new BufferedReader(fr);
//            String line = null;
//              int round =0 ;
//            while ((line = inputStream.readLine()) != null) {
//                for (int i = 0; i < line.length(); i++) {
//                    if (line.charAt(i) == ':') // for example  Johnny Li,1000   : comma behind is score
//                    {
//                        Integer score = Integer.valueOf(line.substring(i + 1, line.length()));
//                        
//                        if(top1 == top2&&top1 == top3) 
//                        {
//                            if(round<3)
//                            {
//                                playerArray[round] = line;
//                                round++;
//                                System.out.println(round + "Round top 1 == top2 == top3 ");
//                                continue;
//                            }
//                        }else if(top1 == top2)
//                        {
//                            if(round<2)
//                            {
//                                playerArray[round] = line;
//                                round++;
//                                System.out.println(round + "Round top 1 == top2 ");
//                                continue;
//                            }
//                        }
//                       
//                
//                        
//                        if(top2 == top3)
//                        {
//                          if(round<2)
//                          {
//                          playerArray[round+1] = line;
//                          System.out.println(round+1 + "Round top 2 == top3 ");
//                          round++;
//                          continue;
//                          }
//                  
//                        }
//                        
//      // ==================================================================================BUG ===============================
//                          if (score == top1) {
//                            playerArray[0] = line;
//                        }
//                        if(score == top2)
//                        {
//                            playerArray[1] = line;
//                        }
//                        if(score == top3)
//                        {
//                            playerArray[2] = line;
//                        }
//                    }
//                }
//
//            }
//            inputStream.close();
//
//        } catch (IOException e) {
//            System.out.println("File  not found.");
//        }
//        
//        for (int i = 0; i < playerArray.length; i++) {
//            System.out.println(i+" :"+playerArray[i]);
//        }
//
//
//    }

    public static void main(String[] args) {

        ContestGUI contestFrame = new ContestGUI();
        Thread contestThread = new Thread(contestFrame);
        Thread playerThread = new Thread(contestFrame.playerGUI);
        contestThread.start();
        playerThread.start();
        
        Map<String,Integer> records = readPlayerInfo();
        System.out.println(records);
        // Need to parse the input so file can be saved
        // 
    }
}
