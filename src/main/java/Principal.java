import java.util.InputMismatchException;
import java.util.Scanner;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez chessMatch = new PartidaXadrez();

        while (true) {
            try {
                UI.limpaTela();
                UI.printBoard(chessMatch.getPecasXadrez());
                System.out.println();
                System.out.println("Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                boolean[][] movimentosPossiveis = chessMatch.movimentosPossiveis(origem);
                UI.limpaTela();
                UI.printBoard(chessMatch.getPecasXadrez(), movimentosPossiveis);

                System.out.println();
                System.out.println("Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                PecaXadrez pecaCapturada = chessMatch.performXadrezMove(origem, destino);
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
    }
}





