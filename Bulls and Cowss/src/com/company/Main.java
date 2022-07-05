package com.company;

import java.util.Scanner;
import java.util.*;

class Grade {
    int bulls;
    int cows;

    Grade(int bulls, int cows) {            //Класс храниения быков и коров
        this.bulls = bulls;
        this.cows = cows;
    }
}

class Game {
    int turn = 1;

    private static int readInput() {
        int inputCode = 0;
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextInt()) {
            inputCode = scanner.nextInt();
        } else {
            String inputUser = scanner.nextLine();
            System.out.println("Error: " + "\"" + inputUser + "\"" + " isn't a valid number.");
            System.exit(1);
        }
        return inputCode;
    } //!метод для считывание ввода

    private static int setLengthSecretCode() {
        System.out.println("Please, enter the secret code's length:");
        int lengthSecretCode = readInput();

        if (lengthSecretCode > 36) {
            System.out.println("Error: secret length cannot be greater than 36");
            System.exit(1);
        } else if (lengthSecretCode < 1) {
            System.out.println("Error: secret length cannot be less than 1");
            System.exit(1);
        }
        return lengthSecretCode;
    } //!метод для ввода длинны секретного кода

    private static int setLengthSecretChar() {
        System.out.println("Input the number of possible symbols in the code:");
        int lengthSecretChar = readInput();

        if (lengthSecretChar > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(1);
        } else if (lengthSecretChar < 1) {
            System.out.println("Error: symbols range length cannot be less than 1");
            System.exit(1);
        }
        return lengthSecretChar;
    }
    //!метод для ввода интервала символов

    private static String setUserCode() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }  //!метод ввода пользовательского кода

    private static String secretPrepared(int lengtSecretChar, String[] secretChars) {
        if (lengtSecretChar < 10) {
            return ("(0-" + secretChars[lengtSecretChar - 1] + ").");
        } else {
            return ("(0-9, a-" + secretChars[lengtSecretChar - 1] + ").");
        }
    }  //!вывод зашифрованого кода

    private static String setSecretCode(int lengthSecret, int lengthSecretChar) {

        if (lengthSecretChar < lengthSecret) {
            System.out.println("Error: it's not possible to generate a code with a length of " + lengthSecret + " with " + lengthSecretChar + " unique symbols.");
            System.exit(1);
        }

        Random random = new Random();
        StringBuilder secretCode = new StringBuilder();
        String[] secretChars = "0123456789abcdefghijklmnopqrstuvwxyz".split("");

        while (secretCode.length() < lengthSecret) {
            int randomSymbol = random.nextInt(lengthSecretChar);
            String chars = secretChars[randomSymbol];

            if (secretCode.indexOf(chars) == -1) {
                secretCode.append(chars);
            }
        }

        StringBuilder encryptedCode = new StringBuilder();
        String symblos = secretPrepared(lengthSecretChar, secretChars);

        while (encryptedCode.length() != secretCode.length()) {
            encryptedCode.append("*");
        }
        System.out.print("The secret is prepared: " + encryptedCode + " ");
        System.out.println(symblos);

        return secretCode.toString();
    }

    Grade getGrade(String secretCode, String userCode) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < userCode.length(); i++) {
            if (userCode.charAt(i) == secretCode.charAt(i)) {
                bulls += 1;
            } else if (secretCode.indexOf(userCode.charAt(i)) != -1) {
                //?Идем по секретному коду и смотрим, есть ли одинаковые числа
                cows += 1;
            }
        }
        return new Grade(bulls, cows);
    }  //!Считаем количество быков и коров

    private void print(Grade grade) {
        if (grade.bulls != 0 && grade.cows != 0) {
            System.out.println("Grade: " + grade.bulls + " bull(s) and " + grade.cows + " cow(s)");
        } else if (grade.bulls != 0) {
            System.out.println("Grade: " + grade.bulls + " bull(s)");
        } else if (grade.cows != 0) {
            System.out.println("Grade: " + grade.cows + " cow(s)");
        } else {
            System.out.println("None.");
        }
    }  //!Метод вывода

    public void play() {
        String userCode;  //?ввод пользователя
        int lengthCode = setLengthSecretCode();  //?длина секретного кода
        int lengthChar = setLengthSecretChar();  //?длина секретного символов

        String secret = setSecretCode(lengthCode, lengthChar);
        System.out.println("Okay, let's start a game!");
        do {
            System.out.println("Turn " + turn + ":");
            userCode = setUserCode();
            Grade grade = getGrade(secret, userCode);
            print(grade);
            turn++;
        } while (!userCode.equals(secret));
        System.out.println("Congratulations! You guessed the secret code.");
    }  //!Игра
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
