/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p3.assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Yuan Hao Li
 */
public class QuestionAnswer {

    static FileReader file;
    static BufferedReader buffer;
    static String question, a, b, c, d, answer,hint;

    public QuestionAnswer() {//reading the question from the text file
        try {
            file = new FileReader("QUESTIONSANSWERS.txt");
            buffer = new BufferedReader(file);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public String getQuestion() // Method to return the question
    {
        try {
            question = buffer.readLine();
        } catch (IOException e) {
            System.out.println("Question could not read");
        }
        return question;
    }

    public String getA() {
        try {
            a = buffer.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return a;
    }

    public String getB() {
        try {
            b = buffer.readLine();

        } catch (IOException e) {
            System.out.println(e);
        }
        return b;
    }

    public String getC() {
        try {
            c = buffer.readLine();

        } catch (IOException e) {
            System.out.println(e);
        }
        return c;
    }

    public String getD() {
        try {
            d = buffer.readLine();
        } catch (IOException e) {
            System.out.println(e);

        }
        return d;
    }

    public String getAnswer() {
        try {
            answer = buffer.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return answer;
    }

    public String getHint() {
        try {
            hint = buffer.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return hint;
    }

    public void resetBuffer() {
        try {
            file.close();
            buffer.close();
            file = new FileReader("QUESTIONSANSWERS.txt");
            buffer = new BufferedReader(file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
