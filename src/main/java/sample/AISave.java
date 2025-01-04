package sample;

public class AISave {
    private Coordinates coordinates;
    private Direction direction;
    private boolean water;

    public AISave(Coordinates coordinates, boolean water) {
        this.coordinates = coordinates;
        this.water = water;
        direction = null;
    }

    public AISave(Coordinates coordinates, Direction direction, boolean water) {
        this.coordinates = coordinates;
        this.direction = direction;
        this.water = water;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }
}
