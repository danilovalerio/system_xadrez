import java.util.*;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PartidaXadrez chessMatch = new PartidaXadrez();
        List<PecaXadrez> capturada = new ArrayList<>();

        while (!chessMatch.getChequeMate()) {
            try {
                UI.limpaTela();
                UI.printPartida(chessMatch, capturada);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                boolean[][] movimentosPossiveis = chessMatch.movimentosPossiveis(origem);
                UI.limpaTela();
                UI.printBoard(chessMatch.getPecasXadrez(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                PecaXadrez pecaCapturada = chessMatch.performXadrezMove(origem, destino);

                if (pecaCapturada != null) {
                    capturada.add(pecaCapturada);
                }

                if (chessMatch.getPromovido() != null) {
                    System.out.println("Entre com a peça para promoção (B/C/T/Q)");
                    String tipoPeca = sc.nextLine().toUpperCase(Locale.ROOT);

                    while (!tipoPeca.equals("T") && !tipoPeca.equals("C") && !tipoPeca.equals("B") && !tipoPeca.equals(
                            "Q")){
                        System.out.println("Tipo inválido de Peça, escolha outra (B/C/T/Q):");
                    }
                    chessMatch.substituiPecaPromovida(tipoPeca);
                }
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.print("Pression ENTER para continuar...");
                sc.nextLine();
            }
        }
        UI.limpaTela();
        UI.printPartida(chessMatch, capturada);
    }
}





