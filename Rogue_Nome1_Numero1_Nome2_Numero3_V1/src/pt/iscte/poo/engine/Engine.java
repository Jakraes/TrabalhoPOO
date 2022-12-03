package pt.iscte.poo.engine;

import pt.iscte.poo.entity.Entity;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.ui.HealthBar;
import pt.iscte.poo.ui.InventorySlot;
import pt.iscte.poo.util.Utils;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;


public class Engine implements Observer {
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 11;

    public static Engine INSTANCE = null;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

    private static int turns;
    private int currentRoom;
    private ArrayList<Room> rooms;

    private Engine() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT);
        gui.go();
    }

    public static Engine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Engine();
        }
        return INSTANCE;
    }

    public void start() {
        gui.clearImages();

        rooms = new ArrayList<>();

        turns = 0;
        currentRoom = 0;

        for (int i = 0; i < 4; i++) {
            rooms.add(new Room(i));

            for (int j = 0; j < 4; j++) {
                rooms.get(i).add(new InventorySlot(new Point2D(j + 6, GRID_HEIGHT - 1)));
            }
        }

        getRoom().renderRoom();
        gui.update();
    }

    public static int getTurns() {
        return turns;
    }

    public static void addTurn() {
        turns++;
    }

    public Room getRoom() {
        return rooms.get(currentRoom);
    }

    public void changeRoom(int roomNumber) {
        getRoom().remove(Hero.getInstance());
        getRoom().get(o -> o instanceof GameElement).forEach(o -> gui.removeImage((GameElement) o));
        currentRoom = roomNumber;
        getRoom().add(Hero.getInstance());
        getRoom().renderRoom();
    }

    private void drawHeroInventory() {
        ArrayList<Object> temp = getRoom().get(o -> o instanceof InventorySlot);
        for (int i = 0; i < temp.size(); i++) {
            Item item = Hero.getInstance().getItem(i);
            ((InventorySlot) temp.get(i)).removeItem();
            if (item != null) {
                ((InventorySlot) temp.get(i)).addItem(item);
            }
        }
    }

    @Override
    public void update(Observed source) {
        ArrayList<Object> temp = getRoom().get(o -> o instanceof Entity && !(o instanceof Hero));
        temp.add(0, Hero.getInstance());

        for (Object o : temp) {
            ((Entity) o).move();
        }

        Hero.getInstance().drawHp();
        drawHeroInventory();
        /* ------ DEBUG ------ //
        for (Object o : getRoom().get(o -> o instanceof Entity)) {
            System.out.println(((Entity) o).getName() + " - HP: " + ((Entity) o).getHp() + " - ATK: " + ((Entity) o).getAtk());
        }
        System.out.println("--------------");
        // ------ DEBUG ------ */
        gui.update();
    }
}
