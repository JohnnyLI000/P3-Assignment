/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

import java.util.Scanner;

/**
 *
 * @author Yuan Hao LiA
 */
public class Contest {


    public static void main(String[] args) throws FileNotFoundException {
        Contest contest = new Contest();
        String playerName;
        String respond;
        Scanner input = new Scanner(System.in);
        do{
            do {
                System.out.println("Input your name:");
                playerName = input.nextLine();
            } while (playerName.isEmpty()); // check whether is blank or not
        Game game = new Game(new Player(playerName));
        
        game.GenerateQA();//generate question and answers
        String namePrize = game.getPlayerName()+game.getPrize()+"\n";
        
        // File f = new File("PlayerInformation.txt");
        //contest.searchPlayer("PlayerInformation.txt",game.getPlayerName());

        contest.output(namePrize);//appending to file the player info (prize and name)
        
        System.out.println("Enter Y to play again");
        respond = input.nextLine();
        }while(respond.toLowerCase().charAt(0) == 'y');
        
    }
    /*
    public boolean searchPlayer(String fileName,String searchStr) throws FileNotFoundException
    {
        boolean result = false;
        Scanner scan = null;
        try{
            scan = new Scanner(new FileReader(fileName));
            while(scan.hasNextLine()&&!result){
            result = scan.nextLine().indexOf(searchStr)>=0;
            }
        }catch(FileNotFoundException e)
        {
            System.out.println(e);
        }
        System.out.println(result);
        return result;
}
    */
    public void output(String content) {  //output player information into text 
        FileWriter fw = null;
        try {
            File f = new File("PlayerInformation.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
