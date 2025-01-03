package sample;

import java.util.ArrayList;
import java.util.Random;

//In der Klasse sind alle Eigenschaften, die ein Player hat
public class Player {
    Field area = new Field();

    private ArrayList<Coordinates> attackpositions = new ArrayList<>();

    private ArrayList<AISave> AIsave = null;

    /*SaveAttack speichert alle Attacken in die Arraylist*/
    public void SaveAttack(int x, int y) {
        this.attackpositions.add(new Coordinates(x, y));
    }

    /*Wir verhindern doppelten Angriff. Wir schauen, mit der foreach Schleife, ob die Übergebenen x,y von attackPossible
    schon in einer der gespeicherten Stellen in unserer ArrayList attackpositions enthalten ist.*/
    boolean attackPossible(int x, int y) {
        for (Coordinates a : this.attackpositions) {
            if ((a.x() == x) && (a.y() == y)) {
                return false;
            }
        }
        return true;
    }

    /*Reset überschreibt unsere Klassenarraylist, die wir oben erstellt haben, mit einer Leeren Arraylist --> Resetet es*/
    public void Reset() {
        this.attackpositions = new ArrayList<>();
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    private boolean isHuman;

    Player(boolean isHuman) {
        this.isHuman = isHuman;
    }


    //Ab hier ist AI (ist nicht implementiert)


    public boolean AISet() {
        if (!isHuman) {
            return false;
        } else {
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(3);
            this.AISetting(3);
            this.AISetting(3);
            this.AISetting(4);
            this.AISetting(4);
            this.AISetting(5);
            return true;
        }
    }

    private void AISetting(int length) {
        int x, y;
        Direction direction;
        Random random = new Random();
        do {
            x = random.nextInt((9 - 0) + 1) + 0;
            y = random.nextInt((9 - 0) + 1) + 0;
            direction = Direction.RIGHT;
            switch (random.nextInt((3 - 0) + 1) + 0) {
                case 0:
                    direction = Direction.RIGHT;
                    break;
                case 1:
                    direction = Direction.UP;
                    break;
                case 2:
                    direction = Direction.LEFT;
                    break;
                case 3:
                    direction = Direction.DOWN;
                    break;
            }
        } while (this.area.setShip(new Coordinates(x, y), new Coordinates(0, 0), direction, length));
    }

    public boolean simpleAIAttack(Player enemy) {
        int x, y;
        Random random = new Random();
        do {
            x = random.nextInt((9 - 0) + 1) + 0;
            y = random.nextInt((9 - 0) + 1) + 0;
        } while (this.attackPossible(x, y));
        this.SaveAttack(x, y);
        return enemy.area.attack(new Coordinates(x, y));
    }

    public boolean complexAIAttack(Player enemy) {
        Random random = new Random();
        boolean result;
        int x, y;
        Direction direction;
        if (this.AIsave == null) {

            do {
                x = random.nextInt((9 - 0) + 1) + 0;
                y = random.nextInt((9 - 0) + 1) + 0;
            } while (this.attackPossible(x, y));
            this.SaveAttack(x, y);
            result = enemy.area.attack(new Coordinates(x, y));
            if (enemy.area.isDestroyed(new Coordinates(x, y)) != null) {
                return true;
            } else if (!result) {
                return false;
            } else {
                AIsave = new ArrayList<>();
                AIsave.add(new AISave(new Coordinates(x, y)));
            }
        } else if (AIsave.get(0).getDirection() == null) {
            direction = Direction.DOWN;
            x = AIsave.get(0).getX();
            y = AIsave.get(0).getY();
            do
            {
                switch (random.nextInt((3 - 0) + 1) + 0)
                {
                    case 0:
                        direction = Direction.RIGHT;
                        x++;
                        break;
                    case 1:
                        direction = Direction.UP;
                        y--;
                        break;
                    case 2:
                        direction = Direction.LEFT;
                        x--;
                        break;
                    case 3:
                        direction = Direction.DOWN;
                        y++;
                        break;
                }
            } while (this.attackPossible(x, y));
            result = enemy.area.attack(new Coordinates(x, y));
            this.SaveAttack(x, y);
            if (result)
            {
                AIsave.add(new AISave(new Coordinates(x, y), direction));
                return true;
            } else {
                return false;
            }
        } else {
            x = AIsave.get(0).getX();
            y = AIsave.get(0).getY();
            switch (AIsave.get(0).getDirection()) {
                case RIGHT:
                    x += (int) AIsave.size();
                    break;
                case LEFT:
                    x -= (int) AIsave.size();
                    break;
                case DOWN:
                    y += (int) AIsave.size();
                    break;
                case UP:
                    y -= (int) AIsave.size();
                    break;
            }
            if (this.attackPossible(x, y)) {
                this.SaveAttack(x, y);
                result = enemy.area.attack(new Coordinates(x, y));
                if (result) {
                    AIsave.add(new AISave(new Coordinates(x, y), AIsave.get(0).getDirection()));
                    return result;
                } else {
                    switch (AIsave.get(0).getDirection()) {
                        case RIGHT:
                            x--;
                            break;
                        case LEFT:
                            x++;
                            break;
                        case DOWN:
                            y--;
                            break;
                        case UP:
                            y++;
                            break;
                    }
                    if (this.attackPossible(x, y)) {
                        AISave a = AIsave.get(0);
                        direction = Direction.LEFT;
                        switch (AIsave.get(0).getDirection()) {
                            case RIGHT:
                                direction = Direction.LEFT;
                                break;
                            case LEFT:
                                direction = Direction.RIGHT;
                                break;
                            case DOWN:
                                direction = Direction.UP;
                                break;
                            case UP:
                                direction = Direction.DOWN;
                                break;
                        }
                        a.setDirection(direction);
                        AIsave = new ArrayList<>();
                        AIsave.add(a);
                        AIsave.add(new AISave(new Coordinates(x, y), direction));
                        return true;
                    } else {
                        AIsave = null;
                        return false;
                    }

                }
            }


        }


        return false;
    }

}
