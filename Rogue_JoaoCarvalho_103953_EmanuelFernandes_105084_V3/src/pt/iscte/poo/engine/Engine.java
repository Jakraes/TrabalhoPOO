package pt.iscte.poo.engine;

import pt.iscte.poo.entity.Entity;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.ui.Black;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;


public class Engine implements Observer {
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 11;

    public static Engine INSTANCE = null;
    private static int turns;
    private static int currentRoom;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
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

    public static int getTurns() {
        return turns;
    }

    public static void addTurn() {
        turns++;
    }

    public void start() {
        gui.setName("Dungeon of the Nimrods");
        gui.clearImages();

        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                gui.addImage(new Black(new Point2D(x, y)));
            }
        }

        Hero.resetHero();

        String temp = null;
        while (temp == null) {
            temp = gui.askUser("Qual é o teu nome?");
        }
        Hero.getInstance().setHeroName(temp);

        rooms = new ArrayList<>();

        turns = 0;
        currentRoom = 0;

        for (int i = 0; i < 8; i++) {
            rooms.add(new Room(i));
        }

        getRoom().renderRoom();
        gui.setStatusMessage(Hero.getInstance().getHeroName() + " - Turno: " + turns);
        gui.update();
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

    @Override
    public void update(Observed source) {
        ArrayList<Object> temp = getRoom().get(o -> o instanceof Entity && !(o instanceof Hero));
        temp.add(0, Hero.getInstance());

        for (Object o : temp) {
            if (((Entity) o).getHp() > 0) {
                ((Entity) o).move();
            } else {
                ((Entity) o).onDeath();
            }
        }

        Hero.getInstance().drawHp();
        Hero.getInstance().drawInventory();

        if (Hero.getInstance().getHp() > 0) {
            gui.setStatusMessage(Hero.getInstance().getHeroName() + " - Turno: " + turns);
            gui.update();
        } else {
            gui.setMessage("Morreste!");
            start();
        }
    }
}