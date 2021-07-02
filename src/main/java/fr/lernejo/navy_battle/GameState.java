package fr.lernejo.navy_battle;

public class GameState
{
    private final boolean[] turn = {false}; // is it our turn ?
    private final boolean[] over = {false};
    private final Board[] board = {null};

    private final String[] opponent_address = {""};
    private final String own_address;

    public GameState(String address)
    {
        this.board[0] = new Board();
        this.own_address = address;
    }

    public GameState newGame() {
        this.board[0] = new Board();
        this.over[0] = false;
        this.turn[0] = false;
        return this;
    }

    public boolean is_game_over()
    {
        return this.over[0];
    }

    public GameState set_game_over(boolean o) { this.over[0] = o; return this; }

    public GameState set_turn(boolean t) { this.turn[0] = t; return this; }

    public boolean get_turn()
    {
        return this.turn[0];
    }

    public boolean check_ships_left() {
        return board[0].shipLeft();
    }

    public GameState fireAtCell(BoardPosition p, boolean enemy) {
        // we fire at cell with GET request, update result using this method
        if (!this.is_game_over())
            this.board[0].updateCell(p.getX(), p.getY(), enemy);
        return this;
    }

    public String takeFireFromEnemy(int x, int y) {
        if (!this.is_game_over()) {
            Board.FireResult result = this.board[0].takeFireFromEnemy(new BoardPosition(x, y));
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
        this.opponent_address[0] = o;
    }

    public String getOpponentAddress() {
        return this.opponent_address[0];
    }

    public Board.State getPosState(int x, int y) {
        if (!this.is_game_over())
            return this.board[0].getCellState(x, y);
        return null;
    }

    public int getPort() { return Integer.parseInt(this.own_address.split(":")[2]); }

    public String getOwnAddress() { return this.own_address; }
}
