import java.util.Scanner;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez chessMatch = new PartidaXadrez();

        while (true) {
        UI.printBoard(chessMatch.getPecasXadrez());
            System.out.println();
            System.out.println("Origem: ");
            PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

            System.out.println();
            System.out.println("Destino: ");
            PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

            PecaXadrez pecaCapturada = chessMatch.performXadrezMove(origem, destino);

        }
    }
}





