/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.util.Random;

/**
 *
 * @author Yuan Hao Li
 */
public class Player {

    private String name;
    private int helpTimes;
    private int hintNum = 1, halfNum = 1, audienceNum = 1;
    // private Help help;

    public Player(String name) {
        this.name = name;
        helpTimes = 3;
        System.out.println("Hi " + name + " ,Welcome to Who wants to be millioniare ");
        //auto generate file ;
    }

    public int checkHelpTimes() {
        return helpTimes;
    }

    public String getPlayerName()
    {
        return this.name;
    }
    public boolean useHelp(int helpNum, String hint, String a, String b, String c, String d, String answer) {
        //3 functions count down
        //only 1 of each
        boolean avalaible = false;
        if (helpNum == 1 && hintNum != 0) {
            this.hint(hint);
            hintNum--;
            helpTimes--;
            avalaible = true;
        }
        if (helpNum == 2 && halfNum != 0) {
            this.half(a, b, c, d, answer);
            halfNum--;
            helpTimes--;
            avalaible = true;
        }

        if (helpNum == 3 && audienceNum != 0) {
            this.audience();
            audienceNum--;
            helpTimes--;
            avalaible = true;
        }

        return avalaible;
    }

    public boolean helpAvalaible() { //print out the help functions.
        if (helpTimes > 0) {
            System.out.println("Enter 1 to get Hint");
            System.out.println("Enter 2 to use the HalfHalf option");
            System.out.println("Enter 3 to get the help from an audience");
            System.out.println("");
            System.out.println("You can use Hint for help for " + hintNum + " time.");
            System.out.println("You can use HalfHalf for help for " + halfNum + " time.");
            System.out.println("You can use Audience for help for " + audienceNum + " time.");
            return true;
        } else {
            System.out.println("You alreday used all of the help");
            return false;
        }
    }

    public void hint(String hint) { //help function1 
        System.out.println(hint);
    }

    public void half(String a, String b, String c, String d, String answer) { // help function 2
        // 50 -50
        System.out.println("==== HALF HALF ====");
        Random random = new Random();
        int optionNum;
        boolean success = false;
        do {
            optionNum = random.nextInt(4);
            switch (optionNum) {
                case 0:
                    if (a.charAt(0) != answer.charAt(8)) {
                        System.out.println(a.charAt(0));
                        System.out.println(answer.charAt(8));
                        success = true;
                        break;

                    }
                case 1:
                    if (b.charAt(0) != answer.charAt(8)) {
                        System.out.println(b.charAt(0));
                        System.out.println(answer.charAt(8));
                        success = true;
                        break;
                    }
                case 2:
                    if (c.charAt(0) != answer.charAt(8)) {
                        System.out.println(c.charAt(0));
                        System.out.println(answer.charAt(8));
                        success = true;
                        break;

                    }
                case 3:
                    if (d.charAt(0) != answer.charAt(8)) {
                        System.out.println(d.charAt(0));
                        System.out.println(answer.charAt(8));
                        success = true;
                        break;

                    }
            }
        } while (success == false);
    }

    public void audience() { //help function 3
        System.out.println("You can ask someone about this question");
    }

}
