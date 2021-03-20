package ru.job4j.io;

import java.io.*;
import java.util.*;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private final List<String> botAnswerList;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
        botAnswerList = new ArrayList<>();
    }

    public void run() {
        String in = "";
        boolean pause = false;
        List<String> list = new ArrayList<>();
        while (!in.equals(OUT)) {
            Scanner input = new Scanner(System.in);
            System.out.println("Задайте вопрос или напишите что-нибудь: ");
            in = input.nextLine();
            list.add(in);
            if (in.equals(STOP)) {
                pause = true;
            } else if (in.equals(CONTINUE)) {
                pause = false;
            }
            if (!pause && !in.equals(OUT)) {
                String answer = "Ответ: " + answer();
                System.out.println(answer);
                list.add(answer);
            }
        }
        logSave(list);
    }

    private void logSave(List<String> list) {
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(
                new FileOutputStream(path)))) {
            for (String str : list) {
                writer.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String answer() {
        if (botAnswerList.size() == 0) {
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader(botAnswers))) {
                while ((line = reader.readLine()) != null) {
                    botAnswerList.add(line);
                }
                if (botAnswerList.size() <= 1) {
                    throw new IndexOutOfBoundsException(
                            "Bot answer list is empty or have only one answer");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return botAnswerList.get((int) (Math.random() * botAnswerList.size()));
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("logs.txt", "botAnswers.txt");
        cc.run();
    }
}