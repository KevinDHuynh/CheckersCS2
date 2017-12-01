package application;
import java.util.Scanner;
public class logic {


        int red = 1;
        int black = 2;
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            int[][] board = new int[8][8];
            Scanner in = new Scanner(System.in);
            boardReset(board);

            while(!winYet(board)) {
                printBoard(board);
                System.out.println("Select Piece: ");
                String piece = in.nextLine();
                if (canMove(parseNotation(piece),board)||canCapture(parseNotation(piece),board)) {
                    validMoves(parseNotation(piece), board);
                    System.out.println("Select Space: ");
                    String space = in.nextLine();
                    System.out.println("       "+piece + " -> " + space);
                    attemptMove(parseNotation(piece),parseNotation(space),board);

                }

            }
            System.out.println("Game has ended");

        }
        public static void printBoard(int[][] board) {
            System.out.println("     a b c d e f g h");
            System.out.println("     ===============");
            for(int i = 0; i<8;i++) {
                System.out.print(8-i+ " || ");
                for(int k = 0; k<8; k++) {
                    System.out.print(board[i][k]+" ");
                }
                System.out.println();
            }
        }
        public static boolean winYet(int[][] board) {
            boolean red = false;
            boolean black = false;
            for(int i = 0; i<8;i++) {
                for(int k = 0; k<8; k++) {
                    if (board[i][k] == 1) red = true;
                    if (board[i][k] == 2) black = true;
                }
            }
            if (red ^ black) return true;
            return false;
        }
        public static void boardReset(int[][] board){
            for (int i = 0; i<8; i++) {
                if (i == 0 || i == 2) {
                    for (int k = 0; k<8; k++) {
                        if (k%2!=0)board[i][k] = 1;
                    }
                }
                else if (i == 1) {
                    for (int k = 0; k<8; k++) {
                        if (k%2==0)board[i][k] = 1;
                    }
                }
                else if (i==5||i==7) {
                    for (int k = 0; k<8; k++) {
                        if (k%2==0)board[i][k] = 2;
                    }
                }
                else if (i==6) {
                    for (int k = 0; k<8; k++) {
                        if (k%2!=0)board[i][k] = 2;
                    }
                }
                else {
                    for (int k = 0; k<8; k++) {
                        board[i][k] = 0;
                    }
                }
            }
        }
        public static boolean canMove(int[] before,int[][] board) {
            if(board[before[0]][before[1]] == 1) {
                if(before[1]!=0&&before[1]!=7&&before[0]!=7) {
                    int[] first = {before[0]+1,before[1]-1};
                    int[] second = {before[0]+1,before[1]+1};

                    if (board[second[0]][second[1]] == 1&&board[first[0]][first[1]] == 1) {
                        System.out.println("There are no options for movement");
                        return false;
                    }
                }
            }
            return true;
        }
        public static boolean canCapture(int[] before,int[][] board) {
            if(board[before[0]][before[1]] == 1) {
                if(before[1]!=0&&before[1]!=7&&before[0]!=7) {
                    int[] first = {before[0]+1,before[1]-1};
                    int[] second = {before[0]+1,before[1]+1};
                    if (board[first[0]][first[1]] == 2) {
                        if(board[before[0]+2][before[1]-2] == 0) {
                            return true;
                        }
                    }
                    if (board[second[0]][second[1]] == 2) {
                        if(board[before[0]+2][before[1]+2] == 0) {
                            return true;
                        }

                    }
                }
            }
            return false;
        }
        public static void validMoves(int[] before,int[][] board) {
            if(board[before[0]][before[1]] == 1) {
                if(before[1]!=0&&before[1]!=7&&before[0]!=7) {
                    int[] first = {before[0]+1,before[1]-1};
                    int[] second = {before[0]+1,before[1]+1};

                    if (board[second[0]][second[1]] == 1&&board[first[0]][first[1]] == 1) {
                        System.out.println("There are no options for movement");
                    }
                    else if (board[first[0]][first[1]] == 1) {
                        System.out.println("Your only option for movement is " + parseSpace(second));
                    }
                    else if (board[second[0]][second[1]] == 1) {
                        System.out.println("Your only option for movement is " + parseSpace(first));
                    }

                    else System.out.println("Your options for movement are " + parseSpace(first)+ " and " + parseSpace(second));
                }
                else if (before[1]==0) {
                    int[] first = {before[0]+1,before[1]+1};
                    System.out.println("Your only option for movement is " + parseSpace(first));
                }
                else if (before[1]==7) {
                    int[] first = {before[0]+1,before[1]-1};
                    System.out.println("Your only option for movement is " + parseSpace(first));
                }
            }
            else if(board[before[0]][before[1]] == 2) {
                if(before[1]!=0&&before[1]!=7&&before[0]!=0) {
                    int[] first = {before[0]-1,before[1]-1};
                    int[] second = {before[0]-1,before[1]+1};
                    System.out.println("Your options for movement are " + parseSpace(first)+ " and " + parseSpace(second));
                }
            }
        }
        public static void attemptMove (int[] before, int[] after, int[][] board) {
            if(board[before[0]][before[1]] == 1) {
                if ((after[0]>before[0])&&(after[1]==before[1]-1)||after[1]==before[1]+1) {
                    movePiece(before,after,board);
                }
                else if ((after[0]>before[0])&&(after[1]==before[1]-2)) {
                    int[]middle = {after[0]-1,before[1]-1};
                    capturePiece(before,middle,after,board);
                }
                else if ((after[0]>before[0])&&after[1]==before[1]+2) {
                    int[]middle = {after[0]-1,before[1]+1};
                    capturePiece(before,middle,after,board);
                }
                else System.out.println("Move is not legal");
            }
            else if(board[before[0]][before[1]] == 2) {
                if ((after[0]<before[0])&&(after[1]==before[1]-1)||after[1]==before[1]+1) {
                    movePiece(before,after,board);
                }
                else if ((after[0]<before[0])&&after[1]==before[1]+2) {
                    int[]middle = {before[0]-1,before[1]+1};
                    capturePiece(before,middle,after,board);
                }
                else if ((after[0]<before[0])&&(after[1]==before[1]-2)) {
                    int[]middle = {after[0]-1,before[1]-1};
                    capturePiece(before,middle,after,board);
                }
                else System.out.println("Move is not legal");
            }
            else System.out.println("Move is not legal");
        }
        public static void movePiece(int[] before,int[] after,int[][]board) {
            if(board[before[0]][before[1]] == 1) {
                board[before[0]][before[1]] = 0;
                board[after[0]][after[1]] = 1;
            }
            else if(board[before[0]][before[1]] == 2) {
                board[before[0]][before[1]] = 0;
                board[after[0]][after[1]] = 2;
            }
        }
        public static void capturePiece(int[] before, int[] middle ,int[] after, int[][]board) {
            if(board[before[0]][before[1]] == 1) {
                board[before[0]][before[1]] = 0;
                board[middle[0]][middle[1]] = 0;
                board[after[0]][after[1]] = 1;
            }
            else if(board[before[0]][before[1]] == 2) {
                board[before[0]][before[1]] = 0;
                board[middle[0]][middle[1]] = 0;
                board[after[0]][after[1]] = 2;
            }
        }
        public static int[] parseNotation(String space) {
            char letter = space.charAt(0);
            int number = Integer.parseInt(space.substring(1));
            int row = 999;
            int column = 999;
            if (letter == 'a' || letter == 'A') row = 0;
            if (letter == 'b' || letter == 'B') row = 1;
            if (letter == 'c' || letter == 'C') row = 2;
            if (letter == 'd' || letter == 'D') row = 3;
            if (letter == 'e' || letter == 'E') row = 4;
            if (letter == 'f' || letter == 'F') row = 5;
            if (letter == 'g' || letter == 'G') row = 6;
            if (letter == 'h' || letter == 'H') row = 7;
            if (number == 1) column = 7;
            if (number == 2) column = 6;
            if (number == 3) column = 5;
            if (number == 4) column = 4;
            if (number == 5) column = 3;
            if (number == 6) column = 2;
            if (number == 7) column = 1;
            if (number == 8) column = 0;
            int[] r = {column,row};
            return r;

        }
        public static String parseSpace(int[] space){
            char a = 'a';
            int b = 0;
            if (space[1] == 0) a='a';
            if (space[1] == 1) a='b';
            if (space[1] == 2) a='c';
            if (space[1] == 3) a='d';
            if (space[1] == 4) a='e';
            if (space[1] == 5) a='f';
            if (space[1] == 6) a='g';
            if (space[1] == 7) a='h';
            if (space[0] == 0) b=8;
            if (space[0] == 1) b=7;
            if (space[0] == 2) b=6;
            if (space[0] == 3) b=5;
            if (space[0] == 4) b=4;
            if (space[0] == 5) b=3;
            if (space[0] == 6) b=2;
            if (space[0] == 7) b=1;
            String c = String.format("%c%d",a,b);
            return c;

        }

}
