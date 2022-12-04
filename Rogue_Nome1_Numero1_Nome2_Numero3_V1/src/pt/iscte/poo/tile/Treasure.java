package pt.iscte.poo.tile;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Treasure extends Tile {
    public Treasure(Point2D position) {
        super("Treasure", position);
    }

    @Override
    public boolean isWalkable() {
        String temp = ImageMatrixGUI.getInstance().askUser("Chegaste ao tesouro! Queres recomeçar?\n" +
                                                                   "                    Y                    N");
        if (temp != null) {
            if (temp.equals("Y") || temp.equals("y")) {
                Engine.getInstance().start();
            }
        }
        return false;
    }
}
