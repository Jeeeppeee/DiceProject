package opgave02;

import java.util.Scanner;

public class Craps {
    private static int rollCount = 0;
    private static boolean won = false;
    private static boolean lose = false;
    // BEMÆRK: 'firstRoll' bruges til at gemme spillets "point", hvilket er forvirrende.
    private static int firstRoll = 0;
    // BEMÆRK: 'points' bruges til at gemme summen af det *nuværende* kast. Navnet er meget forvirrende.
    private static int points = 0;

    public static void main(String[] args) {
        System.out.println("Velkommen til spillet, rul en terning.");
        printRules();
        System.out.println();

        playTwoDie();

        System.out.println();
        System.out.println("Tak for at spille, rul en terning.");
    }

    private static void printRules() {
        System.out.println("=====================================================");
        System.out.println("Regler for Craps terning: ");
        System.out.println(
                "Spillet foregår med to terninger.\n" +
                        "Udfaldet af hvert kast er summen af de to terningers øjne.\n" +
                        "Første kast kaldes 'come out roll'.\n" +
                        "Hvis summen er 7 eller 11, vinder spilleren med det samme.\n" +
                        "Hvis summen er 2, 3 eller 12, taber spilleren med det samme.\n" +
                        "Hvis summen er 4, 5, 6, 8, 9 eller 10, etableres dette tal som spillerens 'point'.\n" +
                        "Spilleren fortsætter med at kaste:\n" +
                        "  - Hvis han kaster sit point igen, vinder han.\n" +
                        "  - Hvis han kaster 7, taber han.\n" +
                        "  - Alle andre udfald ignoreres, og spilleren kaster igen."
        );

        System.out.println("=====================================================");
    }

    /*
     *  Feedback
     *
     * 1. FORKERT SPIL-FLOW: Koden adskiller ikke "come-out roll" (første kast) fra "point-fasen".
     *    Logikken for at vinde/tabe på første kast (7, 11, 2, 3, 12) bliver fejlagtigt kørt
     *    på HVERT ENESTE kast i spillet.
     *
     * 2. KUN ÉT SPIL: Spil-løkken stopper efter første sejr eller tab og spørger ikke,
     *    om spilleren vil spille igen.
     *
     * 3. FORVIRRENDE LOGIK: Spillets tilstand (sejr/tab/point) bliver ændret i helper-metoder
     *    (`rollDie`, `updateStatistics`), hvilket gør koden meget svær at følge.
     */
    private static void playTwoDie() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Rul en terning? ('ja/nej': ) ");
        String answer = scanner.nextLine();

        // Denne løkke stopper efter én sejr eller ét tab.
        while (!answer.equals("nej") && won == false && lose == false) {
            int[] face = new int[2];
            rollDie(face);
            System.out.println(face[0] + " & " + face[1]);

            updateStatistics(face);

            System.out.println("Første kast: " + firstRoll);
            // Feedback: Denne if/else-blok bliver kørt på alle kast, ikke kun det første.
            if (firstRoll == 7 || firstRoll == 11) {
                won = true;
                System.out.println("Du vandt med det samme.");

            } else if (firstRoll == 2 || firstRoll == 3 || firstRoll == 12) {
                lose = true;
                System.out.println("Du tabte med det samme.");
            } else {
                System.out.println("Point er sat til: " + firstRoll);
                if (points == firstRoll) {
                    System.out.println("Du ramte dit point igen og vandt!");
                }
                if (lose) {
                    System.out.println("Du slog 7 før dit point. Du tabte!");
                }

            }
            if (won == false && lose == false) {
                System.out.println("Rul en terning? ('ja/nej') ");
                System.out.println(points + " points - slå " + firstRoll + " igen, for at vinde.");
                answer = scanner.nextLine();
            }
        }

        printStatistics();
        scanner.close();
    }

    // Feedback: Denne metode bør kun rulle terningerne.
    // Den indeholder kritisk spil-logik, der bestemmer "firstRoll" (point) og "points" (nuværende sum),
    // hvilket gør koden uoverskuelig.
    private static int[] rollDie(int[] face) {
        for (int i = 0; i < face.length; i++) {
            face[i] = (int) (Math.random() * 6 + 1);
        }
        if (rollCount == 0) {
            firstRoll = face[0] + face[1];
        }
        points = face[0] + face[1];

        return face;
    }

    // Feedback: Denne metode bør kun opdatere statistik (som rollCount).
    // I stedet indeholder den kritisk spil-logik, der afgør, om spilleren vinder eller taber
    // i point-fasen.
    private static void updateStatistics(int [] face) {
        rollCount++;
        if (points == firstRoll && rollCount > 1){
            won = true;
        }
        if (points == 7 && rollCount > 1){
            lose = true;
        }
    }

    private static void printStatistics() {
        System.out.println("\nResults:");
        System.out.println("-------");
        System.out.printf("%17s %4d\n", "Antal rul:", rollCount);
    }

}