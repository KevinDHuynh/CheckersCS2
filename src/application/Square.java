package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle
{
	private Piece piece;
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	public boolean hasPiece()
	{
		return piece != null;
	}
	public Piece getPiece()
	{
		return piece;
	}
	public Square(boolean color, int x, int y)
	{
		setWidth(Board.SQUARE);
		setHeight(Board.SQUARE);
		
		relocate(x * Board.SQUARE,y * Board.SQUARE);
		
		setFill(color ? Color.WHITE : Color.GREEN);
		setStrokeWidth(1);
	}
}
