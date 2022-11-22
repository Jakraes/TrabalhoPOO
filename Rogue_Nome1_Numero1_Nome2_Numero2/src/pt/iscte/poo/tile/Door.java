package pt.iscte.poo.tile;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.utils.Point2D;

public class Door extends Tile {
    private boolean open = false;
    private int leadsToRoom;
    private Point2D leadsToPosition;
    public Door(Point2D position, int leadsToRoom, Point2D leadsToPosition) {
        super("DoorClosed", position, false);
        this.leadsToRoom = leadsToRoom;
        this.leadsToPosition = leadsToPosition;
    }

    @Override
    public boolean isWalkable() {
        if (!open) {
            Engine.getInstance().changeRoom(leadsToRoom, leadsToPosition);
            for (Tile t : Engine.getInstance().getRoom().getTiles()) {
                if (t instanceof Door d) {
                    if (d.getPosition().equals(leadsToPosition)) {
                        d.setOpen();
                    }
                }
            }
        }
        open = true;
        return false;
    }

    @Override
    public String getName() {
        if (open) {
            return "DoorOpen";
        }
        return "DoorClosed";
    }

    public void setOpen() {
        open = true;
    }
}
