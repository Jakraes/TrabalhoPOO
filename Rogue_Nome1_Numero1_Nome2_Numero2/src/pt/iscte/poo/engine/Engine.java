package pt.iscte.poo.engine;

import pt.iscte.poo.entity.Entity;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

public class Engine implements Observer {
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 11;

    private static Engine INSTANCE = null;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

    // --- --- --- --- --- --- --- --- --- //

    public static int turns;
    public static int currentRoom;
    private String heroName;

    private ArrayList<Room> rooms;

    // --- --- --- --- --- --- --- --- --- //

    public static Engine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Engine();
        }
        return INSTANCE;
    }

    private Engine() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT);
        gui.go();
    }

    // --- --- --- --- --- --- --- --- --- //

    public void start() {
        heroName = "Temp";

        turns = 0;
        currentRoom = 0;

        rooms = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rooms.add(new Room(i));
        }

        rooms.get(currentRoom).passRoom(gui);

        gui.setStatusMessage(heroName + " HP: " + Hero.getInstance().getHp() + " ATK: " + Hero.getInstance().getAtk() + " - Turn: " + turns);
        gui.update();
    }

    public void changeRoom(int leadsToRoom, Point2D leadsToPosition) {
        rooms.get(currentRoom).getEntities().removeIf(i -> i.equals(Hero.getInstance()));
        Hero.getInstance().setPosition(leadsToPosition);
        currentRoom = leadsToRoom;
        rooms.get(currentRoom).passRoom(gui);
    }
    public Room getRoom() {
        return rooms.get(currentRoom);
    }
    // --- --- --- --- --- --- --- --- --- //

    @Override
    public void update(Observed source) {
        ArrayList<Entity> deadEntities = new ArrayList<>();
        ArrayList<Entity> copy = new ArrayList<>(getRoom().getEntities());

        for (Entity e : copy) {
            if (!e.checkAlive()) {
                gui.removeImage(e);
                deadEntities.add(e);
            }
            else {
                e.move();
            }
        }

        getRoom().getEntities().removeAll(deadEntities);

        // Good ol' debug
        System.out.println("________________");
        System.out.println(Hero.getInstance().inventory);
        System.out.println(getRoom().getItems());

        gui.setStatusMessage(heroName + " HP: " + Hero.getInstance().getHp() + " ATK: " + Hero.getInstance().getAtk() + " DEF: " + Hero.getInstance().getDef() + " - Turn: " + turns);
        gui.update();
    }
}
