package pt.iscte.poo.interfaces;

import pt.iscte.poo.utils.Point2D;

public interface Pickable {
    void pick();
    void drop(Point2D position);
}
