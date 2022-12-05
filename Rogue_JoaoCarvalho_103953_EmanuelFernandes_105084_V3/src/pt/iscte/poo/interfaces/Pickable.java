package pt.iscte.poo.interfaces;

import pt.iscte.poo.utils.Point2D;

public interface Pickable {
    void grab();

    void drop(Point2D position);
}
