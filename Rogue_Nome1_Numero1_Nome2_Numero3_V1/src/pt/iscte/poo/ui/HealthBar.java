package pt.iscte.poo.ui;

import pt.iscte.poo.engine.GameElement;
import pt.iscte.poo.utils.Point2D;

public class HealthBar extends GameElement {
    private int currentMode;

    public HealthBar(Point2D position) {
        super("Green", position, 0);
        currentMode = 0;
    }

    @Override
    public String getName() {
        String temp = null;
        switch (currentMode) {
            case 0 -> temp = "Green";
            case 1 -> temp = "Red";
            case 2 -> temp = "GreenRed";
        }
        return temp;
    }

    public void setCurrentMode(int i) {
        if (i >= 0 && i <= 2) {
            currentMode = i;
        }
    }
}
