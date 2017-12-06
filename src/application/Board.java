package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Board extends Application 
{
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static final int SQUARE = 100;
	
	private Group squares = new Group();
	private Group pieces =  new Group();
	
	private Square[][] boardContent = new Square[WIDTH][HEIGHT];
	
	private Parent createContent()
	{
		VBox root = new VBox(5);
		root.setPadding(new Insets(5,5,5,5));
		
		HBox hBox = new HBox(5);
		hBox.setAlignment(Pos.CENTER);
		
		Pane pane = new Pane();
		pane.setPrefSize(WIDTH*SQUARE, HEIGHT*SQUARE);
		pane.getChildren().addAll(squares, pieces);

		//Button play = new Button("Let's Party");

		logic Logic = new logic();
		int[][] board = Logic.getBoard();

		for(int y = 0; y < HEIGHT; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				Square square = new Square((x + y) % 2 == 0, x, y);
				square.setStroke(Color.BLACK);
				square.setStrokeWidth(1);
				boardContent[x][y] = square;
				squares.getChildren().add(square);

				Piece p = null;
				if (board[y][x] == 1){
                    p = makePiece(PieceType.RED, x, y);
				}
                if (board[y][x] == 2){
                    p = makePiece(PieceType.BLACK, x, y);
                }

                /**
				Piece p = null;
				
				if (y <= 2 && (x + y) % 2 != 0) 
				{
                    p = makePiece(PieceType.RED, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) 
                {
                    p = makePiece(PieceType.BLACK, x, y);
                }
                **/
                if (p != null) 
                {
                    square.setPiece(p);
                    pieces.getChildren().add(p);
                }

			}
		}
		root.getChildren().addAll(pane, hBox);
		
		//hBox.getChildren().add(play);
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		Scene scene = new Scene(createContent());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Checkers");
		primaryStage.show();
	}
	
	public int boardLocation(double num)
	{
		return (int)(num + SQUARE / 2) / SQUARE;
	}
	
	public int getSquareSize()
	{
		return SQUARE;
	}
	
	private MoveResult tryMove(Piece piece, int newX, int newY)
	{
	    /**
        if (boardContent[newX][newY].hasPiece() //|| (newX + newY) % 2 == 0
        )
        {
            return new MoveResult(MoveType.NONE);
        }
         **/

        int x0 = boardLocation(piece.getLastX());
        int y0 = boardLocation(piece.getLastY());

        if (boardContent[newX][newY].hasPiece())
            return new MoveResult(MoveType.NONE);

        if (piece.getTeam() == PieceType.BLACK){
            if (newY == y0 - 2 && (newX == x0 -2) && boardContent[x0-1][y0-1].getPiece().getTeam() == PieceType.RED) {
                boardContent[x0][y0].removePiece();
                //boardContent[x0 +1][y0 +1].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.BLACK,newX,newY));
                return new MoveResult (MoveType.KILL, boardContent[x0-1][y0-1].getPiece());
            }
            if (newY == y0 - 2 && (newX == x0 +2) && boardContent[x0+1][y0-1].getPiece().getTeam() == PieceType.RED) {
                boardContent[x0][y0].removePiece();
                //boardContent[x0 +1][y0 -1].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.BLACK,newX,newY));
                return new MoveResult(MoveType.KILL, boardContent[x0+1][y0-1].getPiece());
            }
            if (newY == y0 - 1 && ((newX == x0 -1)||(newX == x0 +1))) {
                boardContent[x0][y0].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.BLACK,newX,newY));
                return new MoveResult(MoveType.NORMAL); }
        }

        if (piece.getTeam() == PieceType.RED){
            if (newY == y0 + 2 && (newX == x0 -2) && boardContent[x0-1][y0+1].getPiece().getTeam() == PieceType.BLACK) {
                boardContent[x0][y0].removePiece();
                //boardContent[x0 +1][y0 +1].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.RED,newX,newY));
                return new MoveResult (MoveType.KILL, boardContent[x0-1][y0+1].getPiece());
            }
            if (newY == y0 + 2 && (newX == x0 +2) && boardContent[x0+1][y0+1].getPiece().getTeam() == PieceType.BLACK) {
                boardContent[x0][y0].removePiece();
                //boardContent[x0 -1][y0 +1].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.RED,newX,newY));
                return new MoveResult(MoveType.KILL, boardContent[x0+1][y0+1].getPiece());
            }
            if (newY == y0 + 1 && ((newX == x0 -1)||(newX == x0 +1))) {
                boardContent[x0][y0].removePiece();
                boardContent[newX][newY].setPiece(new Piece(PieceType.RED,newX,newY));
                return new MoveResult(MoveType.NORMAL);
            }
        }
        /**
        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getTeam().moveDir) 
        {
            return new MoveResult(MoveType.NORMAL);
        } 
        else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getTeam().moveDir * 2) 
        {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (boardContent[x1][y1].hasPiece() && boardContent[x1][y1].getPiece().getTeam() != piece.getTeam()) 
            {
                return new MoveResult(MoveType.KILL, boardContent[x1][y1].getPiece());
            }
        }
        **/
        return new MoveResult(MoveType.NONE);
    }
	
	private Piece makePiece(PieceType type, int x, int y) 
	{
        Piece piece = new Piece(type, x, y);
        piece.setOnMouseReleased(e -> 
        {
            int newX = boardLocation(piece.getLayoutX());
            int newY = boardLocation(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) 
            {
                result = new MoveResult(MoveType.NONE);
            } 
            else 
            {
                result = tryMove(piece, newX, newY);
            }

            int x0 = boardLocation(piece.getLastX());
            int y0 = boardLocation(piece.getLastX());

            switch (result.getType()) 
            {
                case NONE:
                    piece.cancelMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    boardContent[x0][y0].setPiece(null);
                    boardContent[newX][newY].setPiece(piece);
                            break;
                            case KILL:
                            piece.move(newX, newY);
                            boardContent[x0][y0].setPiece(null);
                            boardContent[newX][newY].setPiece(piece);

                            Piece otherPiece = result.getPiece();

                                boardContent[boardLocation(otherPiece.getLastX())][boardLocation(otherPiece.getLastY())].removePiece();

                            pieces.getChildren().remove(otherPiece);
                            break;
                            }
                            });

                            return piece;
                            }

    public static void main(String[] args){
        launch(args);
        }
}
