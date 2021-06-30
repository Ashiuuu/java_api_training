package fr.lernejo.navy_battle;

public class BoardPosition
{
    private final int x;
    private final int y;

    public BoardPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || o.getClass() != this.getClass())
            return false;

        final BoardPosition other = (BoardPosition) o;
        return other.x == this.x && other.y == this.y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
