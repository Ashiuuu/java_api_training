package fr.lernejo.navy_battle;

public class Board
{
    enum State {FREE, FIRED, FRIEND_SHIP, ENEMY_SHIP};

    private State[][][] cells; // cells[1][][] is our ships, cells[0][][] is enemy ships

    public Board()
    {
        this.cells = new State[2][10][10];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                for (int k = 0; k < 10; k++)
                {
                    this.cells[i][j][k] = State.FREE;
                }
            }
        }
        place_ships();
    }

    private void place_ships()
    {
        // 5 cells ship
        this.cells[1][0][0] = State.FRIEND_SHIP;
        this.cells[1][0][1] = State.FRIEND_SHIP;
        this.cells[1][0][2] = State.FRIEND_SHIP;
        this.cells[1][0][3] = State.FRIEND_SHIP;
        this.cells[1][0][4] = State.FRIEND_SHIP;

        // 4 cells ship
        this.cells[1][9][9] = State.FRIEND_SHIP;
        this.cells[1][8][9] = State.FRIEND_SHIP;
        this.cells[1][7][9] = State.FRIEND_SHIP;
        this.cells[1][6][9] = State.FRIEND_SHIP;

        // 3 cells ships
        this.cells[1][5][5] = State.FRIEND_SHIP;
        this.cells[1][4][5] = State.FRIEND_SHIP;
        this.cells[1][3][5] = State.FRIEND_SHIP;

        this.cells[1][6][6] = State.FRIEND_SHIP;
        this.cells[1][6][7] = State.FRIEND_SHIP;
        this.cells[1][6][8] = State.FRIEND_SHIP;

        // 2 cells ship
        this.cells[1][1][1] = State.FRIEND_SHIP;
        this.cells[1][1][2] = State.FRIEND_SHIP;
    }

    public boolean is_cell_friend_ship(int x, int y)
    {
        return this.cells[1][x][y] == State.FRIEND_SHIP;
    }

    public void fire_at_cell(int x, int y, boolean enemy)
    { // should be used only by player attacking to note the result
        this.cells[0][x][y] = (enemy ? State.ENEMY_SHIP : State.FIRED);
    }
}
