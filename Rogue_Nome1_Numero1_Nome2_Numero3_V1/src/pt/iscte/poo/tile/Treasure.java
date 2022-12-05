package pt.iscte.poo.tile;

import pt.iscte.poo.engine.Engine;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.util.Highscore;
import pt.iscte.poo.utils.Point2D;

import java.io.File;
import java.io.PrintWriter;

public class Treasure extends Tile {
    public Treasure(Point2D position) {
        super("Treasure", position);
    }

    @Override
    public boolean isWalkable() {
        ImageMatrixGUI.getInstance().setMessage("Chegaste ao tesouro em " + Engine.getTurns() + " turnos!");
        Highscore.write();
        Engine.getInstance().start();

        return false;
    }
}
