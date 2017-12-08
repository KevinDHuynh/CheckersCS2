package application;

public interface Marker {
    void move(int x, int y);
    void cancelMove();
    void setTeam(PieceType p);
}
