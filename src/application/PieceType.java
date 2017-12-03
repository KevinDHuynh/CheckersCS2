package application;

public enum PieceType 
{
	RED(1), BLACK(-1), REDKING(2), BLACKKING(2),KILL(0);
	
	final int moveDir;
	
	PieceType(int move)
	{
		this.moveDir = move;
	}
}
