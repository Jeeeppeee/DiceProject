package opgave03;

import java.util.Scanner;

public class Pigs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player1Score = 0;
        int player2Score = 0;
        int targetScore = 100;
        int currentPlayer = 1;

        System.out.println("Velkommen til Pig-spillet! Første spiller, der når 100 point, vinder.");
        System.out.println("Tryk 'r' for at rulle eller 's' for at stoppe på din tur.");

        while (player1Score < targetScore && player2Score < targetScore) {
            int turnScore = 0;
            boolean turnOver = false;

            System.out.println("\nSpiller " + currentPlayer + "'s tur.");

            while (!turnOver) {
                System.out.print("Indtast 'r' for at rulle eller 's' for at stoppe: ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("r")) {
                    int roll = (int) (Math.random() * 6) + 1;
                    System.out.println("Du rullede: " + roll);

                    if (roll == 1) {
                        System.out.println("Du rullede en 1'er! Ingen point denne runde.");
                        turnScore = 0;
                        turnOver = true;
                    } else {
                        turnScore += roll;
                        System.out.println("Dine point denne tur: " + turnScore);
                    }
                } else if (choice.equalsIgnoreCase("s")) {
                    System.out.println("Du valgte at stoppe.");
                    turnOver = true;
                } else {
                    System.out.println("Ugyldigt input! Brug 'r' for roll og 's' for stop.");
                }
            }

            // Tilføj turpoint til spillerens samlede score
            if (currentPlayer == 1) {
                player1Score += turnScore;
                System.out.println("Spiller 1 samlet score: " + player1Score);
                currentPlayer = 2;
            } else {
                player2Score += turnScore;
                System.out.println("Spiller 2 samlet score: " + player2Score);
                currentPlayer = 1;
            }
        }

        // Vinderen
        if (player1Score >= targetScore) {
            System.out.println("\nSpiller 1 vinder med " + player1Score + " point!");
        } else {
            System.out.println("\nSpiller 2 vinder med " + player2Score + " point!");
        }

        scanner.close();
    }
}