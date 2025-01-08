package sample;

import java.util.ArrayList;
import java.util.List;

public class Ship
{
    // Refactoring: use List instead of ArrayList, make final & name to camel case
    private final List<ShipPart> shipParts = new ArrayList<>();

    // Refactoring: use Coordinates instead of x and y
    private final Coordinates coordinates;

    // Refactoring: make final
    private final int length;

    // Refactoring: make final
    private final Direction direction;

    // Refactoring: use Coordinates instead of x and y
    private final Coordinates diffVector;

    public int getX()
    {
        return coordinates.x();
    }

    public int getY()
    {
        return coordinates.y();
    }

    public int getDiffX()
    {
        return diffVector.x();
    }

    public int getDiffY()
    {
        return diffVector.y();
    }

    // Refactoring: use Coordinates instead of x and y (i.e. parameter objects)
    public Ship(Coordinates coordinates, Coordinates diffVector, Direction directions, int length)
    {
        this.coordinates = coordinates;
        this.diffVector  = diffVector;
        this.direction   = directions;
        this.length      = length;

        initializeShipParts(coordinates, directions, length);
    }

    public int getLength()
    {
        return length;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public List<ShipPart> getShipParts()
    {
        return shipParts;
    }

    // Refactoring: change method name & use Coordinates instead of x and y
    private void initializeShipParts(Coordinates coordinates, Direction directions, int length)
    {
        var x = coordinates.x();
        var y = coordinates.y();

        for (int i = 0; i < length; i++)
        {
            shipParts.add(new ShipPart(new Coordinates(x, y)));

            // Refactoring: use enhanced switch statement
            switch (directions) {
                case UP    -> y--;
                case RIGHT -> x++;
                case LEFT  -> x--;
                case DOWN  -> y++;
            }
        }
    }

    // Refactoring: use Coordinates instead of x and y
    public boolean attack(Coordinates coordinates)
    {
        for (ShipPart shipPart : this.shipParts)
        {
            if (shipPart.getX() == coordinates.x() && shipPart.getY() == coordinates.y())
            {
                shipPart.destroy();
                return true;
            }
        }
        return false;
    }

    // Refactoring: use 'isDestroyed' instead of 'checkIfDestroyed' for name
    public boolean isDestroyed()
    {
        for (ShipPart shippart : this.shipParts)
        {
            if (!shippart.isDestroyed())
            {
                return false;
            }
        }
        return true;
    }
}
