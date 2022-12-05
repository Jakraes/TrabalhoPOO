package pt.iscte.poo.util;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.entity.Hero;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Highscore {
    private static String file = "Highscore.txt";

    public static void write() {
        try {
            int turn = Engine.getTurns();
            int kills = Hero.getInstance().getKills();
            String name = Hero.getInstance().getHeroName();

            ArrayList<Integer> turnList = new ArrayList<>();
            ArrayList<Integer> killList = new ArrayList<>();
            ArrayList<String> nameList = new ArrayList<>();

            // Name - Turns: <turns> | Kills: <kills>
            Scanner scanner = new Scanner(new File(file));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                nameList.add(line[0]);
                turnList.add(Integer.parseInt(line[3]));
                killList.add(Integer.parseInt(line[6]));
            }
            scanner.close();
            for (int i = 0; i < 5; i++) {
                if (turn < turnList.get(i)) {
                    nameList.add(i, name);
                    turnList.add(i, turn);
                    killList.add(i, kills);
                    break;
                }
            }

            PrintWriter writer = new PrintWriter(file);
            writer.write("");
            for (int i = 0; i < 5; i++) {
                writer.println(nameList.get(i) + " - Turns: " + turnList.get(i) + " | Kills: " + killList.get(i));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
