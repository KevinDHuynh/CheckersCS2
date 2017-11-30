package application;

public class MoveResult 
{
	private MoveType t;

    public MoveType getType() 
    {
        return t;
    }

    private Piece p;

    public Piece getPiece() 
    {
        return p;
    }

    public MoveResult(MoveType t) 
    {
        this(t, null);
    }

    public MoveResult(MoveType t, Piece p) 
    {
        this.t = t;
        this.p = p;
    }
}
