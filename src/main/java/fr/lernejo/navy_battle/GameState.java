package fr.lernejo.navy_battle;

public class GameState
{
    private boolean turn; // is it our turn ?
    private boolean over;
    private Board board;

    public GameState()
    {
        this.turn = false;
        this.over = false;
        this.board = new Board();
    }

    public boolean is_game_over()
    {
        return this.over;
    }

    public void set_game_over(boolean o)
    {
        this.over = o;
    }

    public void set_turn(boolean n)
    {
        this.turn = n;
    }

    public boolean get_turn()
    {
        return this.turn;
    }

    public boolean check_friend_cell(int x, int y)
    {
        return this.board.is_cell_friend_ship(x, y);
    }

    public boolean check_ships_left()
    {
        for (int x = 0; x < 10; x++)
        {
            for (int y = 0; y < 10; y++)
            {
                if (this.board.is_cell_friend_ship(x, y))
                    return false;
            }
        }
        return true;
    }

    public void fire_at_cell(int x, int y)
    {

    }
}
