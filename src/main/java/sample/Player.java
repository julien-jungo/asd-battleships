package sample;

import java.util.ArrayList;

public class Player {

    Field area = new Field();
    private ArrayList<Coordinates> attackPositions = new ArrayList<>();
    private ArrayList<AISave> AIsave = null;
    private boolean isHuman;

    Player(boolean isHuman) {
        this.isHuman = isHuman;
    }

    public void saveAttack(int x, int y) {
        this.attackPositions.add(new Coordinates(x, y));
    }

    boolean isAttackPossible(int x, int y) {
        for (Coordinates coordinates : this.attackPositions) {
            if ((coordinates.x() == x) && (coordinates.y() == y)) {
                return false;
            }
        }
        return true;
    }

    public void reset() {
        this.attackPositions = new ArrayList<>();
    }

}
