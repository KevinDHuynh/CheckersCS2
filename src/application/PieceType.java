package application;

public enum PieceType 
{
	RED(1), REDKING(2),BLACK(-1), BLACKKING(2);
	
	final int moveDir;
	
	PieceType(int move)
	{
		this.moveDir = move;
	}
}
