package pt.iscte.poo.engine;

import pt.iscte.poo.entity.*;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.item.Armor;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.item.Sword;
import pt.iscte.poo.tile.Door;
import pt.iscte.poo.tile.Floor;
import pt.iscte.poo.tile.Tile;
import pt.iscte.poo.tile.Wall;
import pt.iscte.poo.utils.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Room {
    private int room; // Número da sala (room = 0 -> room0.txt)
    private ArrayList<Tile> tileArrayList; // ArrayList onde se guardam as tiles todas da sala
    private ArrayList<Entity> entityArrayList; // Entidades da sala
    private ArrayList<Item> itemArrayList; // Items da sala

    public Room(int room) {
        this.room = room;
        tileArrayList = new ArrayList<>();
        entityArrayList = new ArrayList<>();
        itemArrayList = new ArrayList<>();

        load();
    }

    private void load() {
        try {
            Scanner scanner = new Scanner(new File("rooms/room" + room + ".txt")); // Abrimos o ficheiro de acordo com o numero dado no construtor
            int y = 0;

            while (scanner.hasNextLine()) { // Enquanto ainda houver linhas por ler no ficheiro
                String line = scanner.nextLine(); // Obter a próxima linha
                if (line.equals("")) { // Se for a linha depois do mapa
                    break;
                }

                for (int x = 0; x < line.length(); x++) {
                    Tile tile = null;
                    switch (line.charAt(x)) {
                        case ' ' -> {if (!(x == 0 || x == 9 || y == 0 || y == 9)) tile = new Floor(new Point2D(x, y));}
                        case '#' -> tile = new Wall(new Point2D(x, y));
                    }
                    if (tile != null) {
                        tileArrayList.add(tile);
                    }
                }

                y++;
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner temp = new Scanner(line);
                GameElement element = null;
                switch (temp.next()) {
                    case "Hero" -> Hero.getInstance().setPosition(new Point2D(temp.nextInt(), temp.nextInt()));
                    case "Skeleton" -> element = new Skeleton(new Point2D(temp.nextInt(), temp.nextInt()));
                    case "Bat" -> element = new Bat(new Point2D(temp.nextInt(), temp.nextInt()));
                    case "Thug" -> element = new Thug(new Point2D(temp.nextInt(), temp.nextInt()));

                    case "Sword" -> element = new Sword(new Point2D(temp.nextInt(), temp.nextInt()));
                    case "Armor" -> element = new Armor(new Point2D(temp.nextInt(), temp.nextInt()));
                    //case "Hammer" -> element = new Hammer(new Point2D(temp.nextInt(), temp.nextInt()));
                    //case "Key" -> element = new Key(new Point2D())

                    case "Door" -> element = new Door(new Point2D(temp.nextInt(), temp.nextInt()), temp.nextInt(), new Point2D(temp.nextInt(),temp.nextInt()));
                }

                if (element != null) {
                    if (element instanceof Entity) {
                        entityArrayList.add((Entity) element);
                    }
                    else if (element instanceof Item) {
                        itemArrayList.add((Item) element);
                    }
                    else {
                        tileArrayList.add((Tile) element);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void passRoom(ImageMatrixGUI gui) {
        entityArrayList.add(Hero.getInstance());
        gui.clearImages();
        entityArrayList.forEach(e -> gui.addImage(e));
        itemArrayList.forEach(i -> gui.addImage(i));
        tileArrayList.forEach(t -> gui.addImage(t));
    }

    public ArrayList<Entity> getEntities() {
        return entityArrayList;
    }

    public ArrayList<Item> getItems() {
        return itemArrayList;
    }

    public ArrayList<Tile> getTiles() {
        return tileArrayList;
    }
}
