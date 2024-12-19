package sample;

public class ShipPart
{
    // Refactoring: use Coordinates class instead of x and y
    private final Coordinates coordinates;

    // Refactoring: use 'damaged' instead of 'damage' for name
    private boolean damaged;

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
        this.damaged     = false;
    }

    public boolean isDamaged()
    {
        return damaged;
    }

    public void destroy()
    {
        this.damaged = true;
    }

}
