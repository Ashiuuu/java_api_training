package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    enum State {FREE, FIRED, HIT}
    enum FireResult {MISS, HIT, SUNK}

    private final State[][] cells;
    private final List<Ship> ships = new ArrayList<>();

    public Board()
    {
        this.cells = new State[10][10];
        for (int j = 0; j < 10; j++)
        {
            for (int k = 0; k < 10; k++)
            {
                this.cells[j][k] = State.FREE;
            }
        }
        placeShips();
    }

    private void placeShips()
    {
        this.ships.add(new Ship(0, 0, 5, Ship.Orientation.VERTICAL));
        this.ships.add(new Ship(6, 9, 4, Ship.Orientation.HORIZONTAL));
        this.ships.add(new Ship(3,5, 3, Ship.Orientation.HORIZONTAL));
        this.ships.add(new Ship(6, 6, 3, Ship.Orientation.VERTICAL));
        this.ships.add(new Ship(1, 1, 2, Ship.Orientation.VERTICAL));
    }

    public FireResult takeFireFromEnemy(BoardPosition p) {
        for (Ship s: this.ships) {
            if (s.shootAtShip(p)) {
                if (s.isShipSunk()) {
                    this.ships.remove(s);
                    return FireResult.SUNK;
                }
                return FireResult.HIT;
            }
        }
        return FireResult.MISS;
    }

    public boolean shipLeft() {
        return !this.ships.isEmpty();
    }

    public void updateCell(int x, int y, boolean enemy) {
        // should be used only by player attacking to note the result
        this.cells[x][y] = (enemy ? State.HIT : State.FIRED);
    }

    public State getCellState(int x, int y) {
        return this.cells[x][y];
    }
}
