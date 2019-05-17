/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.sql.Time;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Yuan Hao Li
 */
public class Game {

    private int prizeProgress;
    private int prize;
    static QuestionAnswer questionAnswer[] = new QuestionAnswer[8]; // create 8 questions and answers repectively 
    String question = "", a = "", b = "", c = "", d = "", answer = "", hint = "";
    private Player player;

    public Game(Player player) {
        prizeProgress = 0;
        prize = 0;
        System.out.println("Game start");
        System.out.println("enter H to use the HELP");
        this.player = player;
        for (int number = 0; number < 8; number++)//generate the QA classes
        {
            questionAnswer[number] = new QuestionAnswer();
        }
    }

    public void GenerateQA() { // generate answers and question into the array
        Scanner input = new Scanner(System.in);
        String playerAnswer = "";
        for (int number = 0; number < 8; number++) {
            for (int n = 1; n <= 7; n++) {
                if (n == 1) {
                    question = questionAnswer[number].getQuestion();
                    System.out.println("=======================================");
                    System.out.println("Question number " + (number + 1) + "  :" + question);
                }
                if (n == 2) {
                    a = questionAnswer[number].getA();
                    System.out.println(a);
                }
                if (n == 3) {
                    b = questionAnswer[number].getB();
                    System.out.println(b);
                }
                if (n == 4) {
                    c = questionAnswer[number].getC();
                    System.out.println(c);
                }
                if (n == 5) {
                    d = questionAnswer[number].getD();
                    System.out.println(d);
                }
                if (n == 6) {
                    answer = questionAnswer[number].getAnswer();
                }
                if (n == 7) {
                    hint = questionAnswer[number].getHint();
                }

            }

            try {
                do {
                    playerAnswer = input.nextLine();
                } while (playerAnswer.isEmpty());

                boolean success = true;
                do {
                    success = true;
                    if (playerAnswer.toLowerCase().charAt(0) == 'h') { // if the user wants to use the help 
                        int help; 
                        if (player.helpAvalaible()) {
                            help = input.nextInt();
                            if (player.useHelp(help, hint, a, b, c, d, answer)) {

                            } else {
                                System.out.println("sorry this option is not avalaible");
                                help = input.nextInt();
                                player.useHelp(help, hint, a, b, c, d, answer);
                            }

                            do {
                                playerAnswer = input.nextLine();
                            } while (playerAnswer.isEmpty());
                        }

                    }
                     playerAnswer = playerAnswer.replaceAll("\\W", ""); //  detect all of the white spaces
                    if (playerAnswer.toLowerCase().charAt(0) < 97 || playerAnswer.toLowerCase().charAt(0) > 100) { // if the user input unidentified character
                        System.out.println("Please select one of the avalable options for the question");
                        success = false;
                        do {
                            playerAnswer = input.nextLine();
                        } while (playerAnswer.isEmpty());

                    }

                } while (playerAnswer.length() == 0 || playerAnswer.charAt(playerAnswer.length() - 1) == ' ' || success == false); // get rid of spaces
            } catch (NullPointerException e) {
                System.out.println("Please select one of the options");
                do {
                    playerAnswer = input.nextLine();
                } while (playerAnswer.isEmpty());

            }

            
            if (this.checkAnswer(answer.toLowerCase().charAt(8), playerAnswer.toLowerCase().charAt(0), answer)) { // check the answer wether it is correct or not
            } else { 
                questionAnswer[number].resetBuffer();
                break;
            }
        }
    }

    public boolean checkAnswer(char answer, char playerAnswer, String revealAnswer) {
        if (answer == playerAnswer) {
            System.out.println("correct");
            this.prizeUp();
            return true;
        } else {
            System.out.println("wrong");
            System.out.println(revealAnswer);
            System.out.println("You got " + this.prize + " dollars prize!!! ");
            return false;
        }
    }

    public void prizeUp() {
        prize += 500;
    }

    public String getPrize() {
        String result = " "+this.prize;
        return result;
    }
    
    public String getPlayerName()
    {
        String result = player.getPlayerName();
        return result;
    }
}
