package opgave01;

import java.util.Arrays;
import java.util.Scanner;

public class RollTwoDie {
    private static int rollCount = 0;
    private static int totalSum = 0; // Ger gemmes summen af alle kast
    private static int doubleCount = 0;
    private static int highestEye = 0;
    private static int[] eyeCount = new int[6]; // holder antal af 1’ere, 2’ere osv.

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
        System.out.println("Regler for rul en terning: ");
        System.out.println("Spilleren ruller en terning, så længde man lyster.");
        System.out.println("=====================================================");
    }

    private static void playTwoDie() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Rul en terning? ('ja/nej') ");
        String answer = scanner.nextLine();

        while (!answer.equals("nej")) {
            int[] face = new int[2];
            rollDie(face);
            System.out.println(face[0] + " & " + face[1]);

            int currentSum = sumCount(face);
            totalSum += sumCount(face); // læg summen af kastet til total

            if (face[0] == face[1]) {
                doubleCount++;
            }

            // Feedback: Summen af det nuværende kast (currentSum) bliver lagt til totalSum for anden gang.
            // Dette vil medføre, at den endelige sum bliver dobbelt så høj som den korrekte værdi.
            totalSum += currentSum; //læg til total sum

            if (currentSum > highestEye) {
                highestEye = currentSum;
            }
            updateStatistics(face);

            System.out.print("Rul en terning? ('ja/nej') ");
            answer = scanner.nextLine();
        }
        printStatistics();
        scanner.close();
    }

    private static int sumCount(int[] face) {
        return face[0] + face[1];
    }

    // Feedback: Projektbeskrivelsen foreslår en metode `public static int[] rollDice()`,
    // der returnerer et nyt array. Denne implementering bruger i stedet en metode,
    // der modificerer et eksisterende array (`rollDie(int[] face)`).
    // Selvom det virker, er det en anden tilgang end den foreslåede.
    private static int[] rollDie(int[] face) {
        for (int i = 0; i < face.length; i++) {
            face[i] = (int) (Math.random() * 6 + 1);
        }
        return face;
    }

    private static void updateStatistics(int[] face) {
        rollCount++;
        eyeCount[face[0] - 1]++;
        eyeCount[face[1] - 1]++;
    }

    private static void printStatistics() {
        System.out.println("\nResults:");
        System.out.println("-------");
        System.out.printf("%17s %4d\n", "Antal rul:", rollCount);
        System.out.println("Summen af alle kast er: " + totalSum);
        System.out.println("Antal doubblte slag (samme øjne): " + doubleCount);
        System.out.println("Største antal øjne i et kast: " + highestEye);
        System.out.println("Antal af hver øjne i alle kastene: ");
        for (int i = 0; i < eyeCount.length; i++) {
            System.out.println("du har slået: " + (i + 1) + "'ere, " + eyeCount[i] + " gange. ");
        }
    }
}


/*
Programmer klassen RollTwoDice, så strukturen (metoderne) er de samme som i spillet
RollOneDie, men opdateret til 2 terninger. For hvert kast udskrives, hvad de to terninger viser.
Bemærk, at metoden
public static int[] rollDice()
skal returnere et array, der viser, hvad der er slået med de to terninger (derfor ændres navnet
fra rollDie() til rollDice()).
*/
