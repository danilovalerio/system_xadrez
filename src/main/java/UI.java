import xadrez.Color;
import xadrez.PecaXadrez;



public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printBoard(PecaXadrez[][] pecas){
        for (int i = 0; i < pecas.length; i++) {
            System.out.printf((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                printPiece(pecas[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    private static void printPiece(PecaXadrez peca){
        if (peca == null) {
            System.out.print("-");
        } else {
            if (peca.getColor().equals(Color.BRANCO)) {
                System.out.print(ANSI_WHITE_BACKGROUND + peca.toString() + ANSI_RESET);
            } else {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + peca.toString() + ANSI_RESET);
            }
        }
        System.out.printf(" ");
    }
}
