package opgave03;

import java.util.Scanner;

public class Pigs {
    private static int player1Sum = 0;
    private static int player2Sum = 0;
    // Feedback: Forvirrende tilstandsstyring. Flere variable (`player1Turn`, `switchTurn`, `turn` i playOneDie)
    // bruges til at styre spillets flow, hvilket gør koden svær at følge.
    private static boolean player1Turn = true;
    private static boolean switchTurn = false;
    private static int tempSum = 0;
    private static int input = 0;
    private static double player1Tæller = 0;
    private static double player2Tæller = 0;
    private static double player1Nævner = 0;
    private static double player2Nævner = 0;

    public static void main(String[] args) {
        System.out.println("Velkommen til spillet Pigs.");
        printRules();
        System.out.println();

        playOneDie();

        printStatistics();


        System.out.println();
        System.out.println("Tak for at spille Pigs.");
    }

    private static void printRules() {
        System.out.println("=====================================================");
        System.out.println("Regler for Pigs");
        System.out.println("Spilleren ruller to terninger, så længde man lyster.");
        System.out.println("Hvis man slår én 1'er på så gives turen videre, og den midlertidige sum nulstilles");
        System.out.println("Hvis man slår to 1'ere, så gives turen videre, man midster alle dine point, og den midlertidige sum nulstilles");
        System.out.println("=====================================================");
    }

    /*
     * Feedback
     *
     * 1. FORKERT LOGISK FLOW: Turens point (`tempSum`) opdateres FØR, der tjekkes for 1'ere.
     *    Den korrekte rækkefølge er: kast -> tjek for 1'ere -> HVIS ingen 1'ere, opdater `tempSum`.
     *    Koden virker kun "tilfældigt", fordi `updateStatistics` nulstiller `tempSum` bagefter.
     *
     * 2. SPREDT LOGIK: Spillets logik er spredt ud over `playOneDie`, `rollDice` og `updateStatistics`.
     *    Dette gør koden ekstremt svær at forstå og vedligeholde.
     *
     * 3. DÅRLIG LØKKESTYRING: Den indre `while`-løkke styres af en `answer`-streng, som bliver
     *    nulstillet med et hack (`answer = "a"`). En boolean-flag eller do-while-løkke ville være bedre.
     */
    private static void playOneDie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hvor mange point vil i spille til?");
        input = scanner.nextInt();
        System.out.print("Rul en terning? ('ja/nej') ");
        String answer = scanner.nextLine();

        int[] face = new int[2];
        int turn = 0;
        while (player1Sum < input && player2Sum < input) {
            tempSum = 0;

            // Løkkestyringen er forvirrende og bruger et hack (`answer = "a"`)
            while (!answer.equals("nej") && switchTurn == false) {
                if (turn % 2 == 0) {
                    System.out.println("Det er Spiller1's tur");
                } else {
                    System.out.println("Det er Spiller2's tur");
                }
                rollDice(face);
                System.out.println("Spiller1 sum: " + player1Sum);
                System.out.println("Spiller2 sum: " + player2Sum);
                System.out.println("Rundes sum: " + tempSum);

                if (player1Turn) {
                    System.out.println("Spiller1 rullede " + face[0] + " og " + face[1]);
                    System.out.println();
                    player1Tæller++;
                } else {
                    System.out.println("Spiller2 rullede " + face[0] + " og " + face[1]);
                    System.out.println();
                    player2Tæller++;
                }
                updateStatistics(face);
                if (switchTurn == false) {
                    System.out.print("Rul en terning? ('ja/nej') ");
                    answer = scanner.nextLine();
                }
            }

            if (player1Turn) {
                player1Sum += tempSum;
                player1Turn = false;
                player1Nævner++;
            } else {
                player2Sum += tempSum;
                player1Turn = true;
                player2Nævner++;
            }
            switchTurn = false;
            answer = "a"; // Hack for at næste tur kan starte
            turn++;
        }


    }

    // Feedback: Denne metode bør kun rulle terningerne.
    // At opdatere `tempSum` her er forkert, da det sker FØR, der tjekkes for 1'ere.
    private static int[] rollDice(int[] face) {
        for (int i = 0; i < face.length; i++) {
            face[i] = (int) (Math.random() * 6 + 1);
        }
        tempSum += face[0] + face[1];
        return face;
    }

    // Feedback LOGIK: Metodens navn er misvisende, da den indeholder central spil-logik.
    // Logikken for at håndtere 1'ere bør være i `playOneDie`-metoden.
    // De separate `if`-betingelser er også skrøbelige; en `if-else if` ville være bedre.
    private static void updateStatistics(int[] face) {
        if (face[0] == 1 && face[1] == 1 && player1Turn == true) {
            player1Sum = 0;
            switchTurn = true;
        }
        if (face[0] == 1 && face[1] == 1 && player1Turn == false) {
            player2Sum = 0;
            switchTurn = true;
        }
        // Denne betingelse er også sand, hvis der slås to 1'ere.
        if (face[0] == 1 || face[1] == 1) {
            tempSum = 0;
            switchTurn = true;
        }
    }

    private static void printStatistics() {
        System.out.println("\nResults:");
        System.out.println("-------");
        if (player1Sum >= input) {
            System.out.println("Spiller1 vinder");
            System.out.println("===============");
        }
        if (player2Sum >= input) {
            System.out.println("Spiller2 vinder");
            System.out.println("===============");
        }

        System.out.println("Spiller1 lavede i gennemsnit " + 1.0 * player1Tæller / player1Nævner + " kast per runde");
        System.out.println("Spiller2 lavede i gennemsnit " + 1.0 * player2Tæller / player2Nævner + " kast per runde");

    }
}