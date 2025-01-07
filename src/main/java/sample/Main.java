package sample;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.ui.GameBoard;

import java.io.File;


public class Main extends Application {

    // Refactoring: Replace Magic Number with Symbolic Constant
    final GameBoard player1TopBoard = new GameBoard(new Coordinates(440 + 40, 40 + 40), new Coordinates(440 + 440, 440 + 40));
    final GameBoard player1BottomBoard = new GameBoard(new Coordinates(440 + 40, 40 + 440 + 40 + 40), new Coordinates(440 + 440, 40 + 920));
    final GameBoard player2TopBoard = new GameBoard(new Coordinates(440 + 40 + 10 * 40 + 2 * 40, 40 + 40), new Coordinates(440 + 440 + 440 + 40, 440 + 40));
    final GameBoard player2BottomBoard = new GameBoard(new Coordinates(2 * 440 + 40 + 40, 40 + 440 + 40 + 40), new Coordinates(440 + 440 + 40 + 440, 920 + 40));

    private Player player1 = new Player(true);
    private Player player2 = new Player(true);
    private int gameround = 1;
    private boolean shipscomplete = false; //zu testzwecken auf true später muss auf false gestellt werden

    private Button buttonSaveShipsLeft = new Button("Schiffe speichern");
    private Button buttonSaveShipsRight = new Button("Schiffe Speichern");
    private Button newGame = new Button("Neues Spiel");
    private Button exit = new Button("Ka Lust mehr! EXIT");
    private Button reset = new Button("Neustart");
    private Button seeShips1 = new Button("Zeige meine Schiffe");
    private Button seeShips2 = new Button("Zeige meine Schiffe");
    private Button cont = new Button("Hier gehts weiter");

    private ImageView startmenu = new ImageView("file:res/start.png");
    private ImageView wonleft = new ImageView("file:res/spieler1_gewonnen.png");
    private ImageView wonright = new ImageView("file:res/spieler2_gewonnen.png");
    private ImageView maskleftfield = new ImageView("file:res/Insel_Unten_1.png");
    private ImageView maskrightfield = new ImageView("file:res/Insel_Unten_2.png");

    private Rectangle indicate1 = new Rectangle(439, 481, 442, 7);
    private Rectangle indicate2 = new Rectangle(919, 481, 442, 7);


    private Media bomb = new Media(new File("res/bomb.wav").toURI().toString());
    private MediaPlayer bombplay = new MediaPlayer(bomb);
    private Media miss = new Media(new File("res/miss.wav").toURI().toString());
    private MediaPlayer missplay = new MediaPlayer(miss);
    private Media music = new Media(new File("res/music.wav").toURI().toString());
    private MediaPlayer musicplay = new MediaPlayer(music);
    private Media winner = new Media(new File("res/winner.wav").toURI().toString());
    private MediaPlayer winnerplay = new MediaPlayer(winner);

    private Image bships[] = {
            new Image("file:res/1x2_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x3_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x4_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x5_Schiff_Horizontal_1_Fertig.png")
    };


    //Schiffe SPieler 1
    ImageShip imageShip1[] = {
            new ImageShip(new Coordinates(1520, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1520, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1520, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1520, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1520, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1520, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1520, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1520, 800), 4, bships[2]),
            new ImageShip(new Coordinates(1520, 800), 4, bships[2]),
            new ImageShip(new Coordinates(1520, 880), 5, bships[3])
    };
    //Schiffe Spieler 2
    ImageShip imageShip0[] = {
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 640), 2, bships[0]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 720), 3, bships[1]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 800), 4, bships[2]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 800), 4, bships[2]),
            new ImageShip(new Coordinates(1800 - 1520 - 3 * 40, 880), 5, bships[3])
    };


    private Pane battleshipcontainer = new Pane();

    private void drawGUI() {
        musicplay.setCycleCount(500);
        musicplay.play();

        for (int i = 0; i < imageShip0.length; i++) {
            battleshipcontainer.getChildren().add(imageShip0[i].getImageView());
            battleshipcontainer.getChildren().add(imageShip1[i].getImageView());
        }

        battleshipcontainer.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    // Refactoring: Unnecessary Field Removed, the variables pressedX and pressedY are only used once
                    double pressedX = event.getSceneX();
                    double pressedY = event.getSceneY();
                    attack(new Coordinates((int) Math.round(pressedX), (int) Math.round(pressedY)));
                }
            }
        });


        buttonSaveShipsLeft.setLayoutX(1800 - 1520 - 3 * 40);
        buttonSaveShipsLeft.setLayoutY(500);
        buttonSaveShipsLeft.setPrefSize(120, 10);

        // Refactoring: Method Extraction
        // Refactoring: Anonymous Class -> Lambda Expression
        buttonSaveShipsLeft.setOnAction((ActionEvent event) -> handlePlayer1SaveShips());


        buttonSaveShipsRight.setLayoutX(1520);
        buttonSaveShipsRight.setLayoutY(500);
        buttonSaveShipsRight.setPrefSize(120, 10);

        // Refactoring: Method Extraction
        // Refactoring: Anonymous Class -> Lambda Expression
        buttonSaveShipsRight.setOnAction((ActionEvent event) -> handlePlayer2SaveShips());

        startmenu.setVisible(true);
        seeShips1.setLayoutX(1520);
        seeShips1.setLayoutY(550);
        seeShips1.setPrefSize(120, 10);
        seeShips1.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        changeMask();
                    }
                }
        );

        seeShips2.setLayoutX(160);
        seeShips2.setLayoutY(550);
        seeShips2.setPrefSize(120, 10);
        seeShips2.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        changeMask();
                    }
                }
        );

        indicate1.setFill(Color.RED);
        indicate2.setFill(Color.RED);

        battleshipcontainer.getChildren().add(seeShips1);
        battleshipcontainer.getChildren().add(seeShips2);
        battleshipcontainer.getChildren().addAll(buttonSaveShipsLeft, buttonSaveShipsRight, maskleftfield, maskrightfield,
                startmenu, indicate1, indicate2);

        reset.setVisible(false);
        maskleftfield.setVisible(false);
        maskrightfield.setVisible(false);
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        indicate1.setVisible(false);
        indicate2.setVisible(false);
        changeMask();
    }

    private void activateMask() {
        maskleftfield.setVisible(true);
        maskrightfield.setVisible(true);
    }

    private void deactivateMask() {
        maskleftfield.setVisible(false);
        maskrightfield.setVisible(false);
    }

    private void changeMask() {
        if (gameround % 2 == 1) {
            maskleftfield.setVisible(false);
            maskrightfield.setVisible(true);
        } else if (gameround % 2 == 0) {
            maskleftfield.setVisible(true);
            maskrightfield.setVisible(false);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundImage background = new BackgroundImage(new Image("file:res/BattleshipsBackground.png", 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        //  ImageView Verdeckung = new ImageView("file:res/Spielfeldverdeckung.png");
        maskleftfield.setX(439);
        maskleftfield.setY(39 + 440 + 40);
        maskrightfield.setX(439 + 440 + 40);
        maskrightfield.setY(39 + 440 + 40);


        battleshipcontainer.setBackground(new Background(background));
        drawGUI();

        reset.setLayoutX(440);
        reset.setLayoutY(10);
        reset.setPrefHeight(10);

        reset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                reset();
                Scene scenel = new Scene(battleshipcontainer, 1800, 1000);
                primaryStage.setScene(scenel);
                primaryStage.show();
            }
        });
        battleshipcontainer.getChildren().add(reset);
        newGame.setLayoutX(700);
        newGame.setLayoutY(300);
        newGame.setMinSize(400, 150);
        Font font = new Font(30);
        newGame.setFont(font);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    reset();
                                    Scene scenel = new Scene(battleshipcontainer, 1800, 1000);
                                    primaryStage.setScene(scenel);
                                    primaryStage.show();

                                }
                            }
        );

        battleshipcontainer.getChildren().add(newGame);


        exit.setLayoutX(700);
        exit.setLayoutY(500);
        exit.setMinSize(400, 150);
        exit.setFont(font);
        exit.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 System.exit(0);
                             }
                         }
        );


        battleshipcontainer.getChildren().add(exit);
        cont.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        reset();
                        reset.setVisible(false);
                        battleshipcontainer.getChildren().add(newGame);
                        battleshipcontainer.getChildren().add(exit);
                        startmenu.setVisible(true);
                        newGame.setVisible(true);
                        exit.setVisible(true);
                        Scene scenel = new Scene(battleshipcontainer, 1800, 1000);
                        primaryStage.setScene(scenel);
                        primaryStage.show();
                    }
                }
        );

        Scene scene = new Scene(battleshipcontainer, 1800, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*Wir berechnen x und y relativ zum jeweiligen spielfeld und kriegen eine zahl zwischen 0 und 9 raus.*/
    private int[] calculateXY(int imageshipx, int imageshipy, int p1x, int p1y, int p2x, int p2y) {
        int result[] = new int[2];

        //Checkt ob die Koordinaten vom Schiff im richtigen Feld liegen
        if (imageshipx >= p1x && imageshipx <= p2x && imageshipy >= p1y && imageshipy <= p2y) {
            int vectorx, vectory;
            //berechnet Relation zum Spielfeld
            vectorx = imageshipx - p1x;
            vectory = imageshipy - p1y;
            //Damit es eine Zahl zwischen 0 und 9 ist (denke ich!!)
            result[0] = vectorx / 40;
            result[1] = vectory / 40;
            return result;
        }
        return null;
    }

    // Refactoring: Method extraction
    private void handlePlayer1SaveShips() {
        handleButtonSavedClicked(imageShip0, player1, player1BottomBoard.bottomLeftCorner(), player1BottomBoard.topRightCorner());
    }

    // Refactoring: Method extraction
    private void handlePlayer2SaveShips() {
        handleButtonSavedClicked(imageShip1, player2, player2BottomBoard.bottomLeftCorner(), player2BottomBoard.topRightCorner());
    }

    // Refactoring: Method extraction
    // Refactoring: Change Function Declaration (name changed from saveShips)
    private void handleButtonSavedClicked(ImageShip[] imageShip, Player player, Coordinates bottomLeftOfPositionBoard, Coordinates topRightOfPositionBoard) {
        saveShips(imageShip, player, bottomLeftOfPositionBoard, topRightOfPositionBoard);
        if (player.area.isFleetComplete()) {
            startNextGamePhase(player);
        }
        shipsComplete();
    }

    // Refactoring: Introduce Parameter Object - Coordinates
    // Refactoring: Extract Method
    private void saveShips(ImageShip imageShip[], Player player, Coordinates bottomLeftOfPositionBoard, Coordinates topRightOfPositionBoard) {
        for (ImageShip imageship : imageShip) {
            placeShipOnBoard(imageship, player, bottomLeftOfPositionBoard, topRightOfPositionBoard);
        }
    }

    // Refactoring: Extract Method
    private void startNextGamePhase(Player player) {
        gameround++;
        if (player == player1) {
            letPlayer2PostionShips();
        } else {
            changeModeToBattlePhase();
        }
    }

    // Refactoring: Extract Method
    private void letPlayer2PostionShips() {
        changeMask();
        buttonSaveShipsLeft.setVisible(false);
    }

    // Refactoring: Extract Method
    private void changeModeToBattlePhase() {
        activateMask();
        buttonSaveShipsRight.setVisible(false);
        seeShips1.setVisible(true);
        seeShips2.setVisible(true);
        indicate1.setVisible(true);
    }

    // Refactoring: Introduce Parameter Object
    // Refactoring: Extract Method
    // Refactoring: Replace Nested Conditional with Guard Clauses
    private void placeShipOnBoard(ImageShip imageship, Player player, Coordinates bottomLeftOfPositionBoard, Coordinates topRightOfPositionBoard) {
        if (imageship.isDisable()) {
            return;
        }
        int a[] = calculateXY(imageship.getCoordinates().x(), imageship.getCoordinates().y(), bottomLeftOfPositionBoard.x(), bottomLeftOfPositionBoard.y(), topRightOfPositionBoard.x(), topRightOfPositionBoard.y());

        if (a == null) {
            imageship.changePosition(new Coordinates(0, 0));
            imageship.rotateTo(Direction.RIGHT);
            return;
        }

        if (!player.area.setShip(new Coordinates(a[0], a[1]), new Coordinates(imageship.getDiffVectorX(), imageship.getDiffVectorY()), imageship.getDirection(), imageship.getLength())) {
            imageship.changePosition(new Coordinates(0, 0));
            imageship.rotateTo(Direction.RIGHT);
            return;
        }
        imageship.lock();
    }

    // Refactoring: Replace Nested Conditional with Guard Clauses
    private void attack(Coordinates targetField) {
        if (player1.area.gameOver() || player2.area.gameOver()) {
            return;
        }

        if (!shipscomplete) {
            return;
        }

        if (gameround % 2 == 1) {
            int[] a = calculateXY(targetField.x(), targetField.y(), player1TopBoard.bottomLeftCorner().x(), player1TopBoard.bottomLeftCorner().y(), player1TopBoard.topRightCorner().x(), player1TopBoard.topRightCorner().y());

            if (a != null) {
                executeAttack(player1, player2, a, targetField);
            }
            if (player2.area.gameOver()) {
                showGameEndScreenPlayer1Won();
            }

        } else {
            int[] a = calculateXY(targetField.x(), targetField.y(), player2TopBoard.bottomLeftCorner().x(), player2TopBoard.bottomLeftCorner().y(), player2TopBoard.topRightCorner().x(), player2TopBoard.topRightCorner().y());

            if (a != null) {
                executeAttack(player2, player1, a, targetField);
            }
            if (player1.area.gameOver()) {
                showGameEndScreenPlayer2Won();
            }
        }
    }

    // Refactoring: Extract Method
    private void executeAttack(Player attackingPlayer, Player attackedPlayer, int[] a, Coordinates targetField) {
        if (attackingPlayer.attackPossible(a[0], a[1])) {
            if (attackedPlayer.area.attack(new Coordinates(a[0], a[1]))) {
                drawAttack(a[0], a[1], targetField.x(), targetField.y(), attackedPlayer);
                attackingPlayer.SaveAttack(a[0], a[1]);
                activateMask();
                bombplay.stop();
                bombplay.play();

            } else {
                drawMiss(targetField.x(), targetField.y());
                attackingPlayer.SaveAttack(a[0], a[1]);
                activateMask();
                indicate1.setVisible(false);
                indicate2.setVisible(true);
                missplay.stop();
                missplay.play();
            }
        }
    }

    // Refactoring: Extract Method
    private void showGameEndScreenPlayer1Won() {
        System.out.println("Spieler 1 hat gewonnen");
        deactivateMask();
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        reset.setVisible(false);
        battleshipcontainer.getChildren().add(wonleft);
        wonleft.setX(50);
        wonleft.setY(520);
        winnerplay.stop();
        winnerplay.play();
        battleshipcontainer.getChildren().add(cont);
        cont.setLayoutX(160);
        cont.setLayoutY(850);
        cont.setVisible(true);
    }

    // Refactoring: Extract Method
    private void showGameEndScreenPlayer2Won() {
        System.out.println("Spieler 2 hat gewonnen");
        deactivateMask();
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        reset.setVisible(false);
        battleshipcontainer.getChildren().add(wonright);
        wonright.setX(1450);
        wonright.setY(520);
        winnerplay.stop();
        winnerplay.play();
        battleshipcontainer.getChildren().add(cont);
        cont.setLayoutX(1520);
        cont.setLayoutY(850);
        cont.setVisible(true);
    }

    /*Wasserzeichen, gerundet auf die richtige Stelle setzen*/
    private void drawMiss(double x, double y) {
        int diffx = (int) x % 40;
        x -= diffx;

        int diffy = (int) y % 40;
        y -= diffy;
        ImageView miss = new ImageView("file:res/Waterhitmarker.png");
        miss.setX(x);
        miss.setY(y);
        battleshipcontainer.getChildren().add(miss);
        gameround++;

    }

    /*Feuerzeichen, gerundet auf die richtige Stelle. Wenn Schiff zerstört, richtiges destroyed Schiff setzen*/
    private void drawAttack(int xx, int yy, double xreal, double yreal, Player player) {
        ImageShip imageShipl;

        int diffx = (int) xreal % 40;
        xreal -= diffx;

        int diffy = (int) yreal % 40;
        yreal -= diffy;

        ImageView hit = new ImageView("file:res/Hit.png");
        hit.setX(xreal);
        hit.setY(yreal);
        battleshipcontainer.getChildren().addAll(hit);


        Image image = new Image("file:res/1x2_Ship_Destroyed.png");
        /*Objekt ship wird entweder null oder ein Schiff zugewiesen (Siehe Klasse Ship, Methode isDestroyed). Wenn
        das Schiff zerstört ist, wird im switch case gefragt welche Länge und dementsprechen setzen wir das Schiff*/
        Ship ship = player.area.isDestroyed(new Coordinates(xx, yy));

        if (ship != null) {
            System.out.println("zerstört");
            switch (ship.getLength()) {
                case 0:
                    break;
                case 2:
                    image = new Image("file:res/1x2_Ship_Destroyed.png");
                    break;
                case 3:
                    image = new Image("file:res/1x3_Ship_Destroyed.png");
                    break;
                case 4:
                    image = new Image("file:res/1x4_Ship_Destroyed.png");
                    break;
                case 5:
                    image = new Image("file:res/1x5_Ship_Destroyed.png");
                    break;
            }

            int x, y;
            //*40 um auf unsere Spielfeldkoordinaten zu kommen
            x = ship.getX() * 40;
            y = ship.getY() * 40;
            //Wird immer in das gegenüberliegende Feld gesetzt, deshalb stehen hier die Koordinaten vom Spieler 2
            if (player == player1) {
                x += 2 * 440 + 40 + 40;
                y += 2 * 40;

            } else {
                x += (440 + 40);
                y += (2 * 40);


            }

            /*Schiff kreiert und zum Battleshipcontainer dazugehaut und lock==true, um es nicht bewegbar zu machen*/
            imageShipl = new ImageShip(new Coordinates(x - ship.getDiffX(), y - ship.getDiffY()), ship.getLength(), image);
            battleshipcontainer.getChildren().add(imageShipl.getImageView());
            imageShipl.rotateTo(ship.getDirection());
            imageShipl.lock();
        }
    }

    //Alle Schiffe beider Spieler sind gesetzt, dann true
    private void shipsComplete() {
        if (player1.area.isFleetComplete() && player2.area.isFleetComplete()) {
            this.shipscomplete = true;
        }

    }

    //Für einzelne Methoden, siehe entsprechende Klassen. Canvas wird zurückgesetzt
    private void reset() {

        for (int i = 0; i < imageShip0.length; i++) {
            imageShip1[i].rotateTo(Direction.RIGHT);
            imageShip0[i].rotateTo(Direction.RIGHT);
            imageShip0[i].reset();
            imageShip1[i].reset();

        }
        player1.area.removeAll();
        player2.area.removeAll();
        player1.Reset();
        player2.Reset();
        gameround = 1;
        shipscomplete = false;
        buttonSaveShipsRight.setVisible(true);
        buttonSaveShipsLeft.setVisible(true);
        battleshipcontainer = new Pane();
        BackgroundImage background = new BackgroundImage(new Image("file:res/BattleshipsBackground.png", 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        battleshipcontainer.setBackground(new Background(background));
        drawGUI();
        battleshipcontainer.getChildren().add(reset);
        reset.setVisible(true);
        startmenu.setVisible(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
