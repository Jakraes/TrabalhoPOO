package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.interfaces.Usable;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.ui.HealthBar;
import pt.iscte.poo.ui.InventorySlot;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Hero extends Entity {
    private static Hero INSTANCE = null;
    private ArrayList<Item> inventory;
    private int selectedSlot = 0;
    private ArrayList<HealthBar> health;

    private Hero(Point2D position) {
        super("Hero", position, 10, 10, 0);
        inventory = new ArrayList<>();
        health = new ArrayList<>();
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

    private void selectSlot(int i) {
        selectedSlot = i;
        ArrayList<Object> temp = Engine.getInstance().getRoom().get(o -> o instanceof InventorySlot);
        for (Object o : temp) {
            if (temp.indexOf(o) == i) {
                ((InventorySlot) o).select();
            }
            else {
                ((InventorySlot) o).unselect();
            }
        }
    }
}
