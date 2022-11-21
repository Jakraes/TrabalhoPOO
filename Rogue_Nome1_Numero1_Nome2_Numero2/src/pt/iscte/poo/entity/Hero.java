package pt.iscte.poo.entity;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Hero extends Entity {
    private final int inventorySize = 4;
    public ArrayList<Item> inventory;

    private static Hero INSTANCE = null; // Críamos um singleton porque queremos que seja possível apenas criar 1 hero

    private Hero(Point2D position) {
        super("Hero", position, 100, 10, 0);
        inventory = new ArrayList<>();
    }

    public static Hero getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Hero(new Point2D(-1, -1));
        }
        return INSTANCE;
    }

    @Override
    public void attack(Entity e) {
        e.setHp(e.getHp() - getAtk());
        Engine.turns++;
        System.out.println("Hero attacks " + e.getName());
    }

    @Override
    public void move() {
        int key = ImageMatrixGUI.getInstance().keyPressed();
        Point2D newPosition = null;
        switch (key) {
            case VK_W -> newPosition = getPosition().plus(Direction.UP.asVector());
            case VK_S -> newPosition = getPosition().plus(Direction.DOWN.asVector());
            case VK_A -> newPosition = getPosition().plus(Direction.LEFT.asVector());
            case VK_D -> newPosition = getPosition().plus(Direction.RIGHT.asVector());
            case VK_SPACE -> checkForItem();
            case VK_BACK_SPACE -> {
                String temp = "Drop Item: \n";
                for (int i = 0; i < inventory.size(); i++) {
                    temp += (i + 1) + ". " + inventory.get(i).getName() + "\n";
                }
                String input = ImageMatrixGUI.getInstance().askUser(temp);
                dropItem(input);
            }
            case VK_R -> {setHp(getMaxHp()); Engine.getInstance().start();}
        }
        if (newPosition != null) {
            checkForCollision(newPosition);
            if (newPosition.equals(getPosition())) {
                Engine.turns++;
            }
        }
    }

    private void checkForItem() {
        Item temp = null; // Como queremos remover o item da lista de items do room logo quando damos pick, criamos um Item temporario
        for (Item i : Engine.getInstance().getRoom().getItems()) {
            if (i.getPosition().equals(getPosition())) {
                if (inventory.size() < inventorySize) {
                    temp = i;
                    break;
                }
            }
        }
        if (temp != null) {
            inventory.add(temp);
            temp.pick();
        }
    }

    private void dropItem(String input) {
        int i;
        try {
            i = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            i = 0;
        }
        if (i >= 1 && i <= inventory.size()) {
            Item item = inventory.get(i-1);

            inventory.remove(item);

            item.drop(getPosition());
        }
    }
}
