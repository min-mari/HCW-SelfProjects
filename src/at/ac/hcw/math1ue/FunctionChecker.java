package at.ac.hcw.math1ue;

import java.util.Scanner;

public class FunctionChecker
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        System.out.println("UE3 – Aufgabe 10 (a–f)");
        System.out.println("=======================");
        System.out.println("Wähle Teilaufgabe: a, b, c, d, e oder f");
        System.out.print("Eingabe: ");

        String choice = in.nextLine().trim().toLowerCase();

        switch (choice)
        {
            case "a":
            {
                caseA();
                break;
            }
            case "b":
            {
                caseB();
                break;
            }
            case "c":
            {
                caseC();
                break;
            }
            case "d":
            {
                caseD();
                break;
            }
            case "e":
            {
                caseE();
                break;
            }
            case "f":
            {
                caseF();
                break;
            }
            default:
            {
                System.out.println("Solche Teilaufgabe existiert nicht! Bitte Programm neu starten und a, b, c, d, e oder f eingeben.");
            }
        }

        in.close();
    }

    //(a)
    private static void caseA()
    {
        double c = 2.0;
        System.out.println();
        System.out.println("(a)  x^2 + y^2 = " + c);
        System.out.println("Geometrie: Kreis mit Radius r = sqrt(2).");
        System.out.println("Funktionsfrage: KEINE Funktion, da für x in (-r, r) zwei y-Werte existieren (±sqrt(c - x^2)).");
        System.out.println("Skizze (Punkte auf dem Kreis werden mit '*', Achsen mit '|' und '-' dargestellt):");
        drawImplicit((x, y) -> approxEqual(x * x + y * y, c, 0.35));
    }

    //(b)
    private static void caseB()
    {
        double k = 1.0;
        System.out.println();
        System.out.println("(b)  x / y > " + k);
        System.out.println("Geometrie: Ungleichung beschreibt einen Bereich (mit y ≠ 0).");
        System.out.println("Funktionsfrage: KEINE Funktion (Bereich ≠ eindeutigem Graphen y(x)).");
        System.out.println("Skizze (Bereich als '.' schattiert; Achsen mit '|' und '-'):");
        drawRegion((x, y) -> y != 0 && (x / y) > k);
    }

    //(c)
    private static void caseC()
    {
        double k = 2.0;
        System.out.println();
        System.out.println("(c)  x + |y| = " + k);
        System.out.println("Umformung: |y| = " + k + " - x  ⇒ Lösungen nur für x ≤ " + k + ".");
        System.out.println("Für die meisten x ≤ " + k + " existieren zwei y-Werte (±(k - x)) ⇒ KEINE Funktion.");
        System.out.println("Skizze (zwei Äste eines nach +x geöffneten 'V'):");
        drawImplicit((x, y) -> approxEqual(Math.abs(y), k - x, 0.25) && (x <= k + 1e-9));
    }

    //(d)
    private static void caseD()
    {
        double k = 2.0;
        System.out.println();
        System.out.println("(d)  y + |x| = " + k);
        System.out.println("Umformung: y = " + k + " - |x|.");
        System.out.println("Für jedes x genau ein y ⇒ IST eine Funktion. Peak bei (0, " + k + ").");
        System.out.println("Skizze (auf dem Gitter als umgekehrtes 'V'):");
        drawImplicit((x, y) -> approxEqual(y, k - Math.abs(x), 0.25));
    }

    //(e)
    private static void caseE()
    {
        System.out.println();
        System.out.println("(e)  f(x) = größter Primteiler von x für x ∈ {2,3,4,5,6,7}, sonst 0");
        System.out.println("Eindeutig für jedes reelle x (Nicht-Ganzzahlen und x ∉ {2..7} → 0) ⇒ IST eine Funktion.");
        System.out.println("Beispielwerte (x = 1..8):");
        for (int x = 1; x <= 8; x++)
        {
            System.out.printf("f(%d) = %d%n", x, largestPrimeDivisorOrZero(x));
        }
        System.out.println("(Graphisch hier nicht sinnvoll zu skizzieren; es handelt sich um eine diskrete Abbildung.)");
    }

    //(f)
    private static void caseF()
    {
        System.out.println();
        System.out.println("(f)  f(x) = jeder Primteiler von x für x ∈ {2,3,4,5,6,7}, sonst 0");
        System.out.println("Mehrdeutig (z. B. x = 6 hat die Bilder 2 und 3) ⇒ KEINE Funktion.");
        System.out.println("Primteiler in {2..7}:");
        for (int x = 2; x <= 7; x++)
        {
            System.out.printf("x = %d → Primteiler: ", x);
            printPrimeDivisors(x);
            System.out.println();
        }
        System.out.println("(Kein eindeutiger Funktionswert → kein Funktionsgraph.)");
    }


    //für die Graphen
    private interface ImplicitTest
    {
        boolean hit(double x, double y);
    }

    private interface RegionTest
    {
        boolean inside(double x, double y);
    }

    //Punkte für die Gleichung
    private static void drawImplicit(ImplicitTest test)
    {
        int min = -6, max = 6;

        for (int y = max; y >= min; y--)
        {
            StringBuilder row = new StringBuilder();
            for (int x = min; x <= max; x++)
            {
                char ch;
                if (test.hit(x, y))
                {
                    ch = '*';     //Kurvenpunkt
                }
                else if (x == 0 && y == 0)
                {
                    ch = '+';     //Ursprung
                }
                else if (x == 0)
                {
                    ch = '|';     //y-Achse
                }
                else if (y == 0)
                {
                    ch = '-';     //x-Achse
                }
                else
                {
                    ch = ' ';
                }
                row.append(ch);
            }
            System.out.println(row);
        }
        System.out.println();
    }

    // "Schraffur"
    private static void drawRegion(RegionTest test)
    {
        int min = -6, max = 6;

        for (int y = max; y >= min; y--)
        {
            StringBuilder row = new StringBuilder();
            for (int x = min; x <= max; x++)
            {
                char ch;
                if (test.inside(x, y))
                {
                    ch = '.';     //schattiert
                }
                else if (x == 0 && y == 0)
                {
                    ch = '+';     //Ursprung
                }
                else if (x == 0)
                {
                    ch = '|';     //y-Achse
                }
                else if (y == 0)
                {
                    ch = '-';     //x-Achse
                }
                else
                {
                    ch = ' ';
                }
                row.append(ch);
            }
            System.out.println(row);
        }
        System.out.println();
    }

    //Math helpers

    //Num vergleich
    private static boolean approxEqual(double a, double b, double tol)
    {
        return Math.abs(a - b) <= tol;
    }

    //gr. Primteiler von n (n ≥ 2) bestimmen oder 0
    private static int largestPrimeDivisorOrZero(int n)
    {
        if (n < 2) return 0;
        int largest = 1;
        for (int p = 2; p <= n; p++)
        {
            if (isPrime(p) && n % p == 0)
            {
                largest = p;
            }
        }
        return largest;
    }

    //Primal-test für kleine n
    private static boolean isPrime(int n)
    {
        if (n < 2) return false;
        for (int d = 2; d * d <= n; d++)
        {
            if (n % d == 0) return false;
        }
        return true;
    }

    //Primteiler von n (aufst.)
    private static void printPrimeDivisors(int n)
    {
        boolean first = true;
        for (int p = 2; p <= n; p++)
        {
            if (isPrime(p) && n % p == 0)
            {
                if (!first) System.out.print(", ");
                System.out.print(p);
                first = false;
            }
        }
    }
}
