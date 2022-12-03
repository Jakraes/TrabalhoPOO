package pt.iscte.poo.engine;

import pt.iscte.poo.entity.Bat;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.entity.Skeleton;
import pt.iscte.poo.entity.Thug;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.item.Armor;
import pt.iscte.poo.item.HealingPotion;
import pt.iscte.poo.item.Key;
import pt.iscte.poo.item.Sword;
import pt.iscte.poo.tile.Door;
import pt.iscte.poo.tile.Floor;
import pt.iscte.poo.tile.Wall;
import pt.iscte.poo.utils.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Room {
    private int roomNumber;
    private ArrayList<GameElement> elements;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        elements = new ArrayList<>();
        loadRoom();
    }

    private void loadRoom() {
        try {
            Scanner scanner = new Scanner(new File("rooms/room" + roomNumber + ".txt"));
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    break;
                }

                for (int x = 0; x < line.length(); x++) {
                    switch (line.charAt(x)) {
                        case ' ' -> {if (!(x == 0 || x == 9 || y == 0 || y == 9)) elements.add(new Floor(new Point2D(x, y)));}
                        case '#' -> elements.add(new Wall(new Point2D(x, y)));
                    }
                }
                y++;
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineReader = new Scanner(line);
                lineReader.useDelimiter(",");

                switch (lineReader.next())  {
                    case "Hero" -> {
                        Hero.getInstance().setPosition(new Point2D(lineReader.nextInt(), lineReader.nextInt()));
                        add(Hero.getInstance());
                    }
                    case "Skeleton" -> add(new Skeleton(new Point2D(lineReader.nextInt(), lineReader.nextInt())));
                    case "Bat" -> add(new Bat(new Point2D(lineReader.nextInt(), lineReader.nextInt())));
                    case "Thug" -> add(new Thug(new Point2D(lineReader.nextInt(), lineReader.nextInt())));

                    case "Sword" -> add(new Sword(new Point2D(lineReader.nextInt(), lineReader.nextInt())));
                    case "Armor" -> add(new Armor(new Point2D(lineReader.nextInt(), lineReader.nextInt())));
                    case "Key" -> add(new Key(new Point2D(lineReader.nextInt(), lineReader.nextInt()), lineReader.nextInt()));
                    case "HealingPotion" -> add(new HealingPotion(new Point2D(lineReader.nextInt(), lineReader.nextInt())));

                    case "Door" -> {
                        Door temp = null;
                        Point2D position = new Point2D(lineReader.nextInt(), lineReader.nextInt());
                        int toRoom = lineReader.nextInt();
                        Point2D toPosition = new Point2D(lineReader.nextInt(), lineReader.nextInt());
                        try {
                            temp = new Door(position, toRoom, toPosition, lineReader.nextInt());
                        }
                        catch (NoSuchElementException e) {
                            temp = new Door(position, toRoom, toPosition, -1);
                        }
                        add(temp);
                    }
                }
                lineReader.close();
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void renderRoom() {
        for (GameElement e : elements) {
            ImageMatrixGUI.getInstance().addImage(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> get(Predicate<T> predicate) {
        ArrayList<T> temp = new ArrayList<>();
        for (GameElement e : elements) {
            if (predicate.test((T) e)) {
                temp.add((T) e);
            }
        }
        return temp;
    }

    public void add(GameElement e) {
        elements.add(e);
    }

    public void remove(GameElement e) {
        elements.remove(e);
    }

    public void removeAll(ArrayList<GameElement> e) {
        elements.removeAll(e);
    }
}
