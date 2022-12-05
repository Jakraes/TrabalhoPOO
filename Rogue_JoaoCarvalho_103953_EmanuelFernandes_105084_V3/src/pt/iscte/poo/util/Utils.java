package pt.iscte.poo.util;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.entity.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static double halfRound(double i) {
        double min = (int) i;
        double max = min + 1;
        double middle = min + 0.5;
        double result;

        if (i >= middle) {
            double dist1 = Math.abs(i - middle);
            double dist2 = Math.abs(max - i);
            result = (dist1 > dist2) ? max : middle;
        } else {
            double dist1 = Math.abs(middle - i);
            double dist2 = Math.abs(i - min);
            result = (dist1 > dist2) ? min : middle;
        }
        return result;
    }

    public static int lim(int x, int min, int max) {
        if (x < min)
            return min;
        else if (x > max)
            return max;
        return x;
    }

    public static void writeHighscore() {
        try {
            int turn = Engine.getTurns();
            int kills = Hero.getInstance().getKills();
            String name = Hero.getInstance().getHeroName();

            ArrayList<Integer> turnList = new ArrayList<>();
            ArrayList<Integer> killList = new ArrayList<>();
            ArrayList<String> nameList = new ArrayList<>();

            // Name - Turns: <turns> | Kills: <kills>
            Scanner scanner = new Scanner(new File("Highscore.txt"));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                nameList.add(line[0]);
                turnList.add(Integer.parseInt(line[3]));
                killList.add(Integer.parseInt(line[6]));
            }
            scanner.close();

            int j;
            for (j = 0; j < lim(nameList.size(), 0, 5); j++) {
                if (turn < turnList.get(j)) {
                    break;
                }
            }
            nameList.add(j, name);
            turnList.add(j, turn);
            killList.add(j, kills);

            PrintWriter writer = new PrintWriter(new File("Highscore.txt"));
            writer.write("");
            for (int i = 0; i < lim(nameList.size(), 0, 5); i++) {
                writer.println(nameList.get(i) + " - Turns: " + turnList.get(i) + " | Kills: " + killList.get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
