package sample.assets;

public enum ButtonAssets {
    SAVE_SHIPS("Schiffe speichern"),
    NEW_GAME("Neues Spiel"),
    EXIT("Ka Lust mehr! EXIT"),
    RESET("Neustart"),
    SEE_SHIPS("Zeige meine Schiffe"),
    CONTINUE("Hier geht's weiter");

    private final String string;

    ButtonAssets(String string) {
        this.string = string;
    }
    public String getString() {
        return this.string;
    }
}
