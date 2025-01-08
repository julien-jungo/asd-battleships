package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Field
{
    private static final class Dimensions {
        private static final int MIN_X = 0;
        private static final int MAX_X = 9;
        private static final int MIN_Y = 0;
        private static final int MAX_Y = 9;
    }

    // Refactoring: replace magic numbers with mapping of ship lengths to counts
    private static final Map<Integer, Integer> SHIP_COUNT_BY_LENGTH = Map.of(
            2, 4,
            3, 3,
            4, 2,
            5, 1
    );

    // Refactoring: change type from ArrayList to List, use 'ships' instead of 'fleet' for name & make final
    private final List<Ship> ships = new ArrayList<>();

    // Refactoring: remove unused getter for fleet

    private boolean isFree(int x, int y)
    {
        // Refactoring: use 'ship' instead of 'warship'
        for (Ship ship : ships)
        {
            for (ShipPart part : ship.getShipParts())
            {
                if (part.getX() == x && part.getY() == y)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int x, int y) {
        // Refactoring: use constants instead of magic numbers
        return x >= Dimensions.MIN_X &&
               x <= Dimensions.MAX_X &&
               y >= Dimensions.MIN_Y &&
               y <= Dimensions.MAX_Y;
    }

    // Refactoring: use coordinates instead of x and y
    private boolean isAreaFree(Coordinates coordinates, Direction dir, int length)
    {
        var x = coordinates.x();
        var y = coordinates.y();

        for (int i = 0; i < length; i++)
        {
            // Refactoring: extract condition to method
            if (!isValid(x, y))
            {
                // Refactoring: remove commented code
                return false;
            }

            if (!isFree(x, y))
            {
                return false;
            }

            // Refactoring: use enhanced switch statement
            switch (dir) {
                case UP    -> y--;
                case RIGHT -> x++;
                case LEFT  -> x--;
                case DOWN  -> y++;
            }
        }

        return true;
    }

    private int shipCount(int length)
    {
        int count = 0;

        for (Ship warship : this.ships)
        {
            if (warship.getLength() == length)
            {
                count++;
            }
        }
        return count;
    }

    public boolean isFleetComplete()
    {
        // Refactoring: use mapping instead of magic numbers
        return SHIP_COUNT_BY_LENGTH.entrySet().stream()
                .allMatch(entry -> shipCount(entry.getKey()) == entry.getValue());
    }

    // Refactoring: use coordinates instead of x and y
    public boolean setShip(Coordinates coordinates, Coordinates diffVector, Direction dir, int length)
    {
        if (!SHIP_COUNT_BY_LENGTH.containsKey(length)) {
            return false;
        }

        // Refactoring: use mapping instead of switch statement
        if (shipCount(length) >= SHIP_COUNT_BY_LENGTH.get(length))
        {
            return false;
        }

        if (isAreaFree(coordinates, dir, length))
        {
            ships.add(new Ship(coordinates, diffVector, dir, length));
            return true;
        } else
        {
            return false;
        }
    }

    // Refactoring: use coordinates instead of x and y
    public boolean attack(Coordinates coordinates)
    {
        // Refactoring: use 'ship' instead of 'warship'
        for (Ship ship : ships)
        {
            if (ship.attack(coordinates))
            {
                return true;
            }
        }

        return false;
    }

    // Refactoring: use coordinates instead of x and y
    public Ship isDestroyed(Coordinates coordinates)
    {
        // Refactoring: use 'ship' instead of 'warship'
        for (Ship ship : ships)
        {
            // Refactoring: use 'shipPart' instead of 'part'
            for (ShipPart shipPart : ship.getShipParts())
            {
                if (
                        shipPart.getX() == coordinates.x() &&
                        shipPart.getY() == coordinates.y() &&
                        ship.isDestroyed()
                )
                {
                    return ship;
                }
            }
        }

        return null;
    }

    public boolean gameOver()
    {
        // Refactoring: use 'ship' instead of 'warship'
        for (Ship ship : ships)
        {
            if (!ship.isDestroyed())
            {
                return false;
            }
        }

        return true;
    }

    public void removeAll()
    {
        // Refactoring: use clear() instead of creating new list
        ships.clear();
    }

}
