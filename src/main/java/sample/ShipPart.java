package sample;

public class ShipPart
{
    // Refactoring: use Coordinates class instead of x and y
    private final Coordinates coordinates;

    // Refactoring: use 'destroyed' instead of 'damage' for name
    private boolean destroyed;

    public int getX()
    {
        return coordinates.x();
    }

    public int getY()
    {
        return coordinates.y();
    }

    public ShipPart(Coordinates coordinates)
    {
        this.coordinates = coordinates;
        this.destroyed = false;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void destroy()
    {
        this.destroyed = true;
    }

}
