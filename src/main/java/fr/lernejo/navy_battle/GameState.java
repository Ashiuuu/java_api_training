package fr.lernejo.navy_battle;

public class GameState
{
    private boolean turn; // is it our turn ?
    private boolean over;
    private Board board;

    private String opponent_address;

    public GameState()
    {
        this.turn = false;
        this.over = false;
        this.board = new Board();
    }

    public void newGame() {
        this.board = new Board();
        this.over = false;
        this.turn = false;
    }

    public boolean is_game_over()
    {
        return this.over;
    }

    public void set_game_over(boolean o)
    {
        this.over = o;
    }

    public void set_turn(boolean t)
    {
        this.turn = t;
    }

    public boolean get_turn()
    {
        return this.turn;
    }

    public boolean check_ships_left() {
        boolean check = board.shipLeft();
        this.over = !check;
        return check;
    }

    public void fireAtCell(BoardPosition p, boolean enemy) {
        // we fire at cell with GET request, update result using this method
        this.board.updateCell(p.getX(), p.getY(), enemy);
    }

    public String takeFireFromEnemy(int x, int y) {
        Board.FireResult result = this.board.takeFireFromEnemy(new BoardPosition(x, y));
        if (result.equals(Board.FireResult.HIT)) {
            return "hit";
        }
        else if (result.equals(Board.FireResult.SUNK)) {
            return "sunk";
        }
        else
            return "miss";
    }

    public void setOpponentAddress(String o) {
        this.opponent_address = o;
    }

    public String getOpponentAddress() {
        return this.opponent_address;
    }

    public Board.State getPosState(int x, int y) {
        return this.board.getCellState(x, y);
    }
}
