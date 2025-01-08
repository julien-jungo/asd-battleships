package sample.assets;

public enum ImageAssets {
    START_MENU("file:res/start.png"),
    WON_LEFT("file:res/spieler1_gewonnen.png"),
    WON_RIGHT("file:res/spieler2_gewonnen.png"),
    MASK_LEFT_FIELD("file:res/Insel_Unten_1.png"),
    MASK_RIGHT_FIELD("file:res/Insel_Unten_2.png"),
    BACKGROUND("file:res/BattleshipsBackground.png"),
    WATER_HIT_MARKER("file:res/Waterhitmarker.png"),
    HIT("file:res/Hit.png"),
    SHIP_1X2_DESTROYED("file:res/1x2_Ship_Destroyed.png"),
    SHIP_1X3_DESTROYED("file:res/1x3_Ship_Destroyed.png"),
    SHIP_1X4_DESTROYED("file:res/1x4_Ship_Destroyed.png"),
    SHIP_1X5_DESTROYED("file:res/1x5_Ship_Destroyed.png"),
    SHIP_1X2_HORIZONTAL("file:res/1x2_Schiff_Horizontal_1_Fertig.png"),
    SHIP_1X3_HORIZONTAL("file:res/1x3_Schiff_Horizontal_1_Fertig.png"),
    SHIP_1X4_HORIZONTAL("file:res/1x4_Schiff_Horizontal_1_Fertig.png"),
    SHIP_1X5_HORIZONTAL("file:res/1x5_Schiff_Horizontal_1_Fertig.png");

    private final String path;

    ImageAssets(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
