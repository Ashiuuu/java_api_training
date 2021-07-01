package fr.lernejo.navy_battle;

public class GameState
{
    private boolean turn; // is it our turn ?
    private boolean over;
    private Board board;

    private String opponent_address;
    private final String own_address;

    public GameState(String address)
    {
        this.turn = false;
        this.over = false;
        this.board = new Board();
        this.own_address = address;
    }

    public GameState newGame() {
        this.board = new Board();
        this.over = false;
        this.turn = false;
        return this;
    }

    public boolean is_game_over()
    {
        return this.over;
    }

    public GameState set_game_over(boolean o) { this.over = o; return this; }

    public GameState set_turn(boolean t) { this.turn = t; return this; }

    public boolean get_turn()
    {
        return this.turn;
    }

    public boolean check_ships_left() {
        boolean check = board.shipLeft();
        this.over = !check;
        return check;
    }

    public GameState fireAtCell(BoardPosition p, boolean enemy) {
        // we fire at cell with GET request, update result using this method
        if (!this.is_game_over())
            this.board.updateCell(p.getX(), p.getY(), enemy);
        return this;
    }

    public String takeFireFromEnemy(int x, int y) {
        if (!this.is_game_over()) {
            Board.FireResult result = this.board.takeFireFromEnemy(new BoardPosition(x, y));
            if (result.equals(Board.FireResult.HIT)) {
                return "hit";
            } else if (result.equals(Board.FireResult.SUNK)) {
                return "sunk";
            } else
                return "miss";
        }
        return "";
    }

    public void setOpponentAddress(String o) {
        this.opponent_address = o;
    }

    public String getOpponentAddress() {
        return this.opponent_address;
    }

    public Board.State getPosState(int x, int y) {
        if (!this.is_game_over())
            return this.board.getCellState(x, y);
        return null;
    }

    public int getPort() { return Integer.parseInt(this.own_address.split(":")[2]); }

    public String getOwnAddress() { return this.own_address; }
}
