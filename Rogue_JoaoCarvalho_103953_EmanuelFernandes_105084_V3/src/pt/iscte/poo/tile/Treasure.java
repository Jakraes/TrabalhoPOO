package pt.iscte.poo.tile;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.util.Utils;
import pt.iscte.poo.utils.Point2D;

public class Treasure extends Tile {
    public Treasure(Point2D position) {
        super("Treasure", position);
    }

    @Override
    public boolean isWalkable() {
        ImageMatrixGUI.getInstance().setMessage("Chegaste ao tesouro em " + Engine.getTurns() + " turnos!");
        Utils.writeHighscore();
        String temp = ImageMatrixGUI.getInstance().askUser("Queres recomeçar? \n Y N");
        if (temp.equals("y") || temp.equals("Y")) {
            Engine.getInstance().start();
        }
        else {
            ImageMatrixGUI.getInstance().dispose();
            System.exit(0);
            //Engine.stopRunning();
        }

        return false;
    }
}
