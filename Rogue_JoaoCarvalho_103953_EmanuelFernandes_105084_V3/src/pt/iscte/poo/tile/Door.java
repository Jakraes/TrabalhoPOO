package pt.iscte.poo.tile;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.entity.Hero;
import pt.iscte.poo.item.Item;
import pt.iscte.poo.item.Key;
import pt.iscte.poo.utils.Point2D;

public class Door extends Tile {
    private int toRoom;
    private Point2D toPosition;
    private boolean open = false;
    private int keyNumber;

    public Door(Point2D position, int toRoom, Point2D toPosition, int keyNumber) {
        super("DoorClosed", position);
        this.toRoom = toRoom;
        this.toPosition = toPosition;
        this.keyNumber = keyNumber;
    }

    @Override
    public String getName() {
        if (open) {
            return "DoorOpen";
        }
        return "DoorClosed";
    }

    @Override
    public boolean isWalkable() {
        System.out.println(keyNumber);
        if (open) {
            Engine.getInstance().changeRoom(toRoom);
            Hero.getInstance().setPosition(toPosition);
            for (Object o : Engine.getInstance().getRoom().get(o -> o instanceof Door)) {
                if (((Door) o).getPosition().equals(toPosition)) {
                    ((Door) o).setOpen();
                    break;
                }
            }
        } else {
            if (keyNumber == -1) {
                open = true;
            } else {
                for (Item i : Hero.getInstance().getInventory()) {
                    if (i instanceof Key) {
                        if (((Key) i).getKeyNumber() == keyNumber) {
                            open = true;
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void setOpen() {
        open = true;
    }
}
