package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ImageShip {
    private final int length;
    private Coordinates coordinates;
    private final int beginX;
    private final int beginY;
    /**
     * Wichtige Vektoren: Sind dafür da, dass die Bilder und Schiffe gleich rotiert sind und richtig liegen. Da wir es
     * händisch hinein schreiben müssen, dass die "digitalen" ships (nicht die Bilder) auch rotiert sind quasi.
     * Noch bestätigen, ob das stimmt bitte!!
     */
    private int diffVectorX;
    private int diffVectorY;
    private double startX;
    private double startY;
    private double moveX;
    private double moveY;
    private final ImageView imageView;
    private final Image image;
    private Direction direction;
    private boolean disable = false;

    /**
     * Konstruktor, mit dem wir die Schiffe in der Main (großer Block am Anfang) erstellen.
     * Jedes Schiff hat die Eigenschaften und Funktionen, die hier drinnen stehen. z.B Es sind alle Schiffe automatisch nach rechts orientiert.
     */
    public ImageShip(Coordinates coordinates, int length, Image image) {
        this.coordinates = coordinates;
        this.beginX = coordinates.x();
        this.beginY = coordinates.y();
        this.length = length;
        this.image = image;
        this.direction = Direction.RIGHT;

        this.imageView = new ImageView(image);
        imageView.setX(coordinates.x());
        imageView.setY(coordinates.y());

        this.setDiffVectorX(0);
        this.setDiffVectorY(0);

        imageView.addEventHandler(MouseEvent.ANY, event -> {
            if (!disable) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton().equals(MouseButton.PRIMARY)) {
                    handleMousePressed(event);
                } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton().equals(MouseButton.PRIMARY)) {
                    handleMouseDragged(event);
                } else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton().equals(MouseButton.SECONDARY)) {
                    rotate();
                }
            }
        });
    }

    private void handleMousePressed(MouseEvent event) {
        startX = event.getSceneX();
        startY = event.getSceneY();
        moveX = ((ImageView) event.getSource()).getTranslateX();
        moveY = ((ImageView) event.getSource()).getTranslateY();
    }

    private void handleMouseDragged(MouseEvent event) {
        double setX = event.getSceneX() - startX;
        double setY = event.getSceneY() - startY;

        double newX = moveX + setX;
        double newY = moveY + setY;

        newX = adjustPosition(newX);
        newY = adjustPosition(newY);

        ((ImageView) event.getSource()).setTranslateX(newX);
        ((ImageView) event.getSource()).setTranslateY(newY);

        updateCoordinates(newX, newY);
    }

    private double adjustPosition(double value) {
        int diff = (int) value % 40;
        return value - diff;
    }

    private void updateCoordinates(double newX, double newY) {
        this.coordinates = new Coordinates(beginX + getDiffVectorX() + (int) newX, beginY + getDiffVectorY() + (int) newY);
        int[] a = calculateXY(this.coordinates.x(), this.coordinates.y(), 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
        if (a != null) {
            System.out.println("x= " + (a[0] + 1) + "y= " + (a[1] + 1));
        }
    }

    /**
     * Gelocked wird, wenn saveShips in der main ein Schiff gespeichert wird oder wenn ein zerstörtes Schiff gezeichnet wird.
     * Dient dafür, dass man es nicht mehr draggen kann.
     */
    public void lock() {
        this.disable = true;
    }

    public boolean isDisable() {
        return this.disable;
    }

    /**
     * Wir übergeben zwar x und y = 0 wenn wir die Methode aufrufen, bedeuetet aber nur, dass es zur
     * Ursprungskoordinate zurückspringt (wird von dort alles relativ gerechnet). Ermöglicht durch this.x=xx...
     * Position muss von den ursprugort angegeben werden und nicht von 0/0
     */
    public void changePosition(Coordinates coordinates) {
        this.imageView.setTranslateX(coordinates.x());
        this.imageView.setTranslateY(coordinates.y());
        this.coordinates = new Coordinates(coordinates.x() + this.beginX + diffVectorX, coordinates.y() + this.beginY + diffVectorY);
    }

    /**
     * Nach dem reseten, soll das Schiff wieder zum Ursprungsort zurück
     */
    public void reset() {
        this.disable = false;
        this.changePosition(new Coordinates(0, 0));
    }

    // Rotiert das Bild und das im code angelegte Schiff

    /**
     * Die rotate Methode rotiert immer um die Mitte eines Objektes.
     * Das ist ein Problem bei Geraden Schiffen weil sie nach dem Rotieren zwischen zwei Feldern liegen würden.
     * Hier verhindern wir das, durch Differezenaufsummierung, je nachdem wie oft gedreht wurde.
     */
    private void rotate() {
        double value = imageView.getRotate();
        imageView.setRotate(value - 90);
        if (getLength() % 2 == 1) {
            return;
        }
        imageView.setX(imageView.getX() + 20);
        imageView.setY(imageView.getY() - 20);

        updateCoordinatesBasedOnRotation();

        int[] a = calculateXY(this.coordinates.x(), this.coordinates.y(), 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
        if (a != null) {
            System.out.println("x= " + (a[0] + 1) + "y= " + (a[1] + 1));
        }
    }

    /**
     * Switch ist dafür da, um die Bilder die wir drehen und die ImageShips ("Digital angelegte
     * Schiffe" die wir erstellen, nach dem Rotieren abzugleichen. Weil nur weil wir das Bild drehen,
     * heißt es ja nicht, dass sich unsere ImageShips mitdrehen. Sind ja zwei verschiedene
     * Entitäten. Immer Abhängig von welcher Richtung man dreht, ändern wir manuell dann die
     * Direction mit den dementsprechenden Rechnungen auch um.
     */
    private void updateCoordinatesBasedOnRotation() {
        switch (direction) {
            case UP:
                direction = Direction.LEFT;
                adjustCoordinatesAfterUpRotation();
                break;
            case DOWN:
                direction = Direction.RIGHT;
                adjustCoordinatesAfterDownRotation();
                break;
            case LEFT:
                direction = Direction.DOWN;
                adjustCoordinatesAfterLeftRotation();
                break;
            case RIGHT:
                direction = Direction.UP;
                adjustCoordinatesAfterRightRotation();
                break;
        }
    }

    private void adjustCoordinatesAfterUpRotation() {
        if (getLength() % 2 == 1) {
            this.coordinates = new Coordinates(this.coordinates.x() + 40 * (getLength() / 2), this.coordinates.y() - 40 * (getLength() / 2));
        } else if (getLength() == 2) {
            return; // No coordinate adjustment needed
        } else {
            this.coordinates = new Coordinates(this.coordinates.x() + 40, this.coordinates.y() - 40);
        }
        adjustDiffVectorsAfterRotation(40, -40);
    }

    private void adjustCoordinatesAfterDownRotation() {
        if (getLength() % 2 == 1) {
            this.coordinates = new Coordinates(this.coordinates.x() - 40 * (getLength() / 2), this.coordinates.y() + 40 * (getLength() / 2));
        } else if (getLength() == 2) {
            this.coordinates = new Coordinates(this.coordinates.x() - 40, this.coordinates.y() + 40);
        } else {
            this.coordinates = new Coordinates(this.coordinates.x() - 2 * 40, this.coordinates.y() + 2 * 40);
        }
        adjustDiffVectorsAfterRotation(-40, 40);
    }

    private void adjustCoordinatesAfterLeftRotation() {
        if (getLength() % 2 == 1) {
            this.coordinates = new Coordinates(this.coordinates.x() - 40 * (getLength() / 2), this.coordinates.y() - 40 * (getLength() / 2));
        } else if (getLength() == 2) {
            this.coordinates = new Coordinates(this.coordinates.x(), this.coordinates.y() - 40);
        } else {
            this.coordinates = new Coordinates(this.coordinates.x() - 40, this.coordinates.y() - 2 * 40);
        }
        adjustDiffVectorsAfterRotation(-40, -40);
    }

    private void adjustCoordinatesAfterRightRotation() {
        if (getLength() % 2 == 1) {
            this.coordinates = new Coordinates(this.coordinates.x() + 40 * (getLength() / 2), this.coordinates.y() + 40 * (getLength() / 2));
        } else if (getLength() == 2) {
            this.coordinates = new Coordinates(this.coordinates.x() + 40, this.coordinates.y());
        } else {
            this.coordinates = new Coordinates(this.coordinates.x() + 2 * 40, this.coordinates.y() + 40);
        }
        adjustDiffVectorsAfterRotation(40, 40);
    }

    private void adjustDiffVectorsAfterRotation(int deltaX, int deltaY) {
        setDiffVectorX(getDiffVectorX() + deltaX);
        setDiffVectorY(getDiffVectorY() + deltaY);
    }

    /**
     * Verwenden wir beim reset button in der Main Methode, um auf RIGHT zu rotieren z.B. Es dreht solange, bis es der
     * übergebenen Direction entspricht.
     */
    public void rotateTo(Direction directionRotate) {
        while (this.direction != directionRotate) {
            this.rotate();
        }
    }

    private int[] calculateXY(int x, int y, int p1x, int p1y, int p2x, int p2y) {
        int[] result = new int[2];
        if (x >= p1x && x <= p2x && y >= p1y && y <= p2y) {
            int vectorX;
            int vectorY;
            vectorX = x - p1x;
            vectorY = y - p1y;
            result[0] = vectorX / 40;
            result[1] = vectorY / 40;
            return result;
        }
        return null;
    }

    private void setDiffVectorX(int diffVectorX) {
        this.diffVectorX = diffVectorX;
    }

    private void setDiffVectorY(int diffVectorY) {
        this.diffVectorY = diffVectorY;
    }

    public int getDiffVectorX() {
        return diffVectorX;
    }


    public int getDiffVectorY() {
        return diffVectorY;
    }


    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getBeginX() {
        return beginX;
    }

    public int getBeginY() {
        return beginY;
    }

    public Image getImage() {
        return image;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
