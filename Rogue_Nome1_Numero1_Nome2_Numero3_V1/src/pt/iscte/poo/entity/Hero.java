package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.interfaces.Usable;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.ui.HealthBar;
import pt.iscte.poo.ui.InventorySlot;
import pt.iscte.poo.util.Utils;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Hero extends Entity {
    private static Hero INSTANCE = null;
    private ArrayList<Item> inventory;
    private int selectedSlot = 0;

    private ArrayList<HealthBar> health;
    private ArrayList<InventorySlot> slots;

    private Hero(Point2D position) {
        super("Hero", position, 10, 10, 0);
        inventory = new ArrayList<>();
        health = new ArrayList<>();
        slots = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            health.add(new HealthBar(new Point2D(i, Engine.GRID_HEIGHT - 1)));
            ImageMatrixGUI.getInstance().addImage(health.get(i));
        }
        for (int i = 0; i < 4; i++) {
            slots.add(new InventorySlot(new Point2D(i + 6, Engine.GRID_HEIGHT - 1)));
            ImageMatrixGUI.getInstance().addImage(slots.get(i));
        }
    }

    public static Hero getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Hero(new Point2D(-1, -1));
        }
        return INSTANCE;
    }

    @Override
    public void attack(Entity e) {
        if (Math.random() > e.getDef() / 100.0) {
            e.setHp(e.getHp() - getAtk());
        }
    }

    @Override
    public void move() {
        int key = ImageMatrixGUI.getInstance().keyPressed();

        Point2D nextPosition = null;
        switch (key) {
            case VK_W -> nextPosition = getPosition().plus(Direction.UP.asVector());
            case VK_S -> nextPosition = getPosition().plus(Direction.DOWN.asVector());
            case VK_A -> nextPosition = getPosition().plus(Direction.LEFT.asVector());
            case VK_D -> nextPosition = getPosition().plus(Direction.RIGHT.asVector());

            case VK_Q -> {
                for (Object o : Engine.getInstance().getRoom().get(o -> o instanceof Item)) {
                    if (((Item) o).getPosition().equals(getPosition()) && inventory.size() < 4) {
                        ((Item) o).grab();
                        addItem((Item) o);
                        break;
                    }
                }
            }
            case VK_E -> {
                if (selectedSlot < inventory.size()) {
                    inventory.get(selectedSlot).drop(getPosition());
                    removeItem(inventory.get(selectedSlot));
                }
            }

            case VK_SPACE -> {if (getItem(selectedSlot) instanceof Usable) ((Usable) getItem(selectedSlot)).use();}

            case VK_1 -> selectSlot(0);
            case VK_2 -> selectSlot(1);
            case VK_3 -> selectSlot(2);
            case VK_4 -> selectSlot(3);
        }
        if (nextPosition != null) {
            checkForCollision(nextPosition);
        }
        updateTurn();
    }

    public Item getItem(int index) {
        if (index < inventory.size()) {
            return inventory.get(index);
        }
        return null;
    }

    public void addItem(Item item) {
        if (inventory.size() < 4) {
            inventory.add(item);
        }
    }

    public void removeItem(Item item) {
        if (inventory.size() > 0) {
            inventory.remove(item);
        }
    }

    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void drawHp() {
        double bars = getHp() * 6 / (float) getMaxHp();

        double result = Utils.halfRound(bars);
        for (int i = 0; i < health.size(); i++) {
            HealthBar currentBar = health.get(i);
            if (i <= result - 1) {
                currentBar.setCurrentMode(0);
            }
            else {
                if (result % 1 == 0.5 && i == (int) result) {
                    currentBar.setCurrentMode(2);
                }
                else {
                    currentBar.setCurrentMode(1);
                }
            }
        }
    }

    public void drawInventory() {
        for (int i = 0; i < 4; i++) {
            slots.get(i).removeItem();
        }
        for (int i = 0; i < inventory.size(); i++) {
            Item item = getItem(i);
            if (item != null) {
                slots.get(i).addItem(item);
            }
        }
    }

    private void selectSlot(int i) {
        selectedSlot = i;
        for (int j = 0; j < 4; j++) {
            if (j == i) {
                slots.get(j).select();
            }
            else {
                slots.get(j).unselect();
            }
        }
    }
}
