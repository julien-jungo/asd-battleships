package sample;

public class AISave {
    // Refactoring: use Coordinates instead of x and y
    private Coordinates coordinates;
    private Direction direction;

    public int getX()
    {
        return coordinates.x();
    }

    public int getY()
    {
        return coordinates.y();
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public AISave(Coordinates coordinates, Direction direction)
    {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public AISave(Coordinates coordinates)
    {
        this.coordinates = coordinates;
        direction=null;
    }
}
