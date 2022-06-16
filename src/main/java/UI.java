import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {

    //TODO: Extrair para Color.java essas Cores ;)
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static void limpaTela(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new PosicaoXadrez(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException(
                    "Erro ao ler posição de xadrez, posições válidas são de a1 até h8."
            );
        }

    }

    public static void printPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> pecasCapturadas) {
        printBoard(partidaXadrez.getPecasXadrez());
        System.out.println();
        printPecasCatpuradas(pecasCapturadas);
        System.out.println();
        System.out.println("Rodada: "+ partidaXadrez.getTurno());
        System.out.println("Aguardando jogador de peça: " + partidaXadrez.getJogadorAtual());
    }

    public static void printBoard(PecaXadrez[][] pecas){
        for (int i = 0; i < pecas.length; i++) {
            System.out.printf((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                printPiece(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    public static void printBoard(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis){
        for (int i = 0; i < pecas.length; i++) {
            System.out.printf((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                printPiece(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");

    }

    private static void printPiece(PecaXadrez peca, boolean background){
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }

        if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (peca.getColor().equals(Color.BRANCO)) {
                System.out.print(ANSI_WHITE_BACKGROUND + peca.toString() + ANSI_RESET);
            } else {
                System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + peca.toString() + ANSI_RESET);
            }
        }
        System.out.printf(" ");
    }

    private static void printPecasCatpuradas(List<PecaXadrez> capturada){
        List<PecaXadrez> brancas = capturada.stream().filter(elemento -> elemento.getColor() == Color.BRANCO)
                .collect(Collectors.toList());

        List<PecaXadrez> pretas = capturada.stream().filter(elemento -> elemento.getColor() == Color.PRETO)
                .collect(Collectors.toList());

        System.out.println("Peças Capturadas:");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.print(Arrays.toString(brancas.toArray()));
        System.out.println(ANSI_RESET);
        System.out.print("Pretas: ");
        System.out.print(ANSI_WHITE_BACKGROUND);
        System.out.print(Arrays.toString(pretas.toArray()));
        System.out.println(ANSI_RESET);
    }
}
