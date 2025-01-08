package sample.assets;

public enum AudioAssets {
    BOMB("res/bomb.wav"),
    MISS("res/miss.wav"),
    MUSIC("res/music.wav"),
    WINNER("res/winner.wav");

    private final String path;

    AudioAssets(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
    }
}
