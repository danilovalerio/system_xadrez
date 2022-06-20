package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

public class PartidaXadrez {
    /**
     * Uma partida tem que ter um TABULEIRO
     * Tem que saber o TAMANHO do TABULEIRO
     * Tem que ter rodada (turno)
     * Jogador
     */
    private Tabuleiro tabuleiro;
    private int turno;
    private Color jogadorAtual;
    private boolean check;
    private boolean xequeMate;
    private PecaXadrez pecaVulneravelDePassagem;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez(){
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogadorAtual = Color.BRANCO;
        initialSetup();
    }

    public int getTurno() {
        return turno;
    }

    public Color getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getChequeMate(){
        return xequeMate;
    }

    public PecaXadrez getPecaVulneravelDePassagem() {
        return pecaVulneravelDePassagem;
    }

    public PecaXadrez[][] getPecasXadrez(){
        PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        //percorre a matriz e faz um downcast para Peca de xadrez ao inves de usar uma peça comum do tabuleiro
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                matriz[i][j] = (PecaXadrez) tabuleiro.peca(i,j);
            }
        }
        return matriz;
    }

    /**
     * Exibe os movimentos possíveis a partir de uma posição de origem
     * @param posicaoOrigem posição atual da peça que será movida
     * @return os movimentos possíveis
     */
    public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
        // Converte posicao de xadrez para posicao de matriz
        Posicao posicao = posicaoOrigem.paraPosicaoMatriz();
        validaPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();

    }

    public PecaXadrez performXadrezMove(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao source = origem.paraPosicaoMatriz();
        Posicao target = destino.paraPosicaoMatriz();
        validaPosicaoOrigem(source);
        validaPosicaoDestino(source, target);
        Peca pecaCapturada = realizaMovimento(source, target);

        PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(target);

        //Valida se deixou o jogar em xeque
        if (testaReiEmXeque(jogadorAtual)){
            desfazMovimento(source, target, pecaCapturada);
            throw new XadrezException("Você não pode se colocar em xeque");
        }

        check = (testaReiEmXeque(corOponente(jogadorAtual))) ? true : false;

        if (testaReiEmXequeMate(corOponente(jogadorAtual))){
            xequeMate = true;
        } else {
            proximoTurno();
        }

        //#movimento especial specialmove en passant
        if (
                pecaMovida instanceof Peao
                        && (target.getColuna() == source.getLinha() - 2
                        || target.getLinha() == source.getLinha() + 2)
        ){
            pecaVulneravelDePassagem = pecaMovida;
        } else {
            pecaVulneravelDePassagem = null;
        }


        return (PecaXadrez) pecaCapturada;
    }

    private Peca realizaMovimento(Posicao origem, Posicao destino){
        PecaXadrez p = (PecaXadrez) tabuleiro.peca(origem);
        p.incrementaMovimentoContador();
        tabuleiro.removePeca(origem);
        Peca capturada = tabuleiro.peca(destino);
        tabuleiro.removePeca(destino);
        tabuleiro.posicaoPeca(p, destino);

        if (capturada != null) {
            pecasNoTabuleiro.remove(capturada);
            pecasCapturadas.add(capturada);
        }

        //#Movimento especial Roque - especialmove castling kingside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.peca(origemTorre);
            tabuleiro.removePeca(origemTorre);
            tabuleiro.posicaoPeca(torre, destinoTorre);
            torre.incrementaMovimentoContador();
        }

        //#Movimento especial Roque - especialmove castling queenside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.peca(origemTorre);
            tabuleiro.removePeca(origemTorre);
            tabuleiro.posicaoPeca(torre, destinoTorre);
            torre.incrementaMovimentoContador();
        }

        //#Movimento especial En Passant especialmove
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && capturada == null){
                Posicao peaoPosicao;
                if (p.getColor() == Color.BRANCO) {
                    peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                capturada = tabuleiro.peca(peaoPosicao);
                tabuleiro.removePeca(peaoPosicao);
                pecasCapturadas.add(capturada);
                pecasNoTabuleiro.remove(capturada);
            }
        }

        return capturada;
    }

    private void desfazMovimento(Posicao origem, Posicao destino, Peca capturada) {
        PecaXadrez p = (PecaXadrez) tabuleiro.peca(destino);
        p.decrementaMovimentoContador();
        tabuleiro.posicaoPeca(p, origem);

        if (capturada != null) {
            tabuleiro.posicaoPeca(capturada, destino);
            pecasCapturadas.remove(capturada);
            pecasNoTabuleiro.add(capturada);
        }

        //#Desfaz Movimento especial Roque - especialmove castling kingside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
            tabuleiro.posicaoPeca(torre, origemTorre);
            torre.decrementaMovimentoContador();
        }

        //#Desfaz Movimento especial Roque - especialmove castling queenside rook
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
            tabuleiro.posicaoPeca(torre, origemTorre);
            torre.decrementaMovimentoContador();
        }

        //#DesfazMovimento especial En Passant especialmove
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && capturada == pecaVulneravelDePassagem){
                PecaXadrez peao = (PecaXadrez) tabuleiro.peca(destino);
                tabuleiro.removePeca(destino);
                Posicao peaoPosicao;
                if (p.getColor() == Color.BRANCO) {
                    peaoPosicao = new Posicao(3, destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(4, destino.getColuna());
                }

                tabuleiro.posicaoPeca(peao, peaoPosicao);
            }
        }
    }

    private void validaPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.posicaoJaTemPeca(posicao)) {
            throw new XadrezException("Não tem peça na posição de origem");
        }
        if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getColor()) {
            throw new XadrezException("Peça escolhida não é sua.");
        }
        if (!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()) {
            throw new XadrezException("Não tem movimentos possíveis para peça escolhida");
        }
    }

    private void validaPosicaoDestino(Posicao origem, Posicao destino){
        /* Se para a peça de origem a posição de destino não é um movimento possível */
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
            throw new XadrezException("A peça de origem não pode se mover para a posição de destino.");
        }
    }

    /**
     * Troca de turno e o Jogador Atual que terá a vez de fazer a jogada
     */
    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private Color corOponente(Color color) {
        return (color == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private PecaXadrez rei(Color cor) {
        List<Peca> pecas = pecasNoTabuleiro.stream().filter(
                x -> ((PecaXadrez)x).getColor() == cor)
                .collect(Collectors.toList());

        for (Peca p : pecas) {
            if (p instanceof Rei) {
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException(
                "Não existe o rei da "+cor+" no tabuleiro"
        );
    }

    /**
     * Verifica se o Rei está em xeque
     * @param color cor do rei
     * @return verdadeiro ou falso
     */
    private boolean testaReiEmXeque(Color color) {
        Posicao posicaoRei = rei(color).getPosicaoXadrez().paraPosicaoMatriz();
        List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(
                x -> ((PecaXadrez)x).getColor() == corOponente(color))
                .collect(Collectors.toList());

        for (Peca p : pecasOponentes) {
            boolean[][] matrizMovPossiveis = p.movimentosPossiveis();
            if (matrizMovPossiveis[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;

    }

    private boolean testaReiEmXequeMate(Color color) {
        if (!testaReiEmXeque(color)) {
            return false;
        }

        List<Peca> pecas = pecasNoTabuleiro.stream().filter(
                x -> ((PecaXadrez)x).getColor() == color)
                .collect(Collectors.toList());

        for (Peca p : pecas) {
            boolean[][] matMovimentosPossiveis = p.movimentosPossiveis();
            for (int lin = 0; lin > tabuleiro.getLinhas(); lin++){
                for (int col = 0; col < tabuleiro.getColunas(); col++) {
                    if (matMovimentosPossiveis[lin][col]){
                        Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicaoMatriz();
                        Posicao destino = new Posicao(lin, col);
                        Peca capturada = realizaMovimento(origem, destino);
                        boolean testCheck = testaReiEmXeque(color);
                        desfazMovimento(origem, destino, capturada);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }

    /**
     * Passa a posicao de coordenadas de matriz para a posição de coordenadas do xadrez
     * @param coluna da matriz
     * @param linha da matriz
     * @param peca peca de xadrez
     */
    private void posicaoNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.posicaoPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicaoMatriz());
        pecasNoTabuleiro.add(peca);
    }

    private void initialSetup(){
        //tabuleiro.posicaPeca(new Torre(tabuleiro, Color.BRANCO), new Posicao(2,1));
        posicaoNovaPeca('a', 1, new Torre(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('b', 1, new Cavalo(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('c', 1, new Bispo(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('d', 1, new Rainha(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('e', 1, new Rei(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('f', 1, new Bispo(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('g', 1, new Cavalo(tabuleiro, Color.BRANCO));
        posicaoNovaPeca('h', 1, new Torre(tabuleiro, Color.BRANCO));

        posicaoNovaPeca('a', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('b', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('c', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('d', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('e', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('f', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('g', 2, new Peao(tabuleiro, Color.BRANCO, this));
        posicaoNovaPeca('h', 2, new Peao(tabuleiro, Color.BRANCO, this));

        posicaoNovaPeca('a', 8, new Torre(tabuleiro, Color.PRETO));
        posicaoNovaPeca('b', 8, new Cavalo(tabuleiro, Color.PRETO));
        posicaoNovaPeca('c', 8, new Bispo(tabuleiro, Color.PRETO));
        posicaoNovaPeca('d', 8, new Rainha(tabuleiro, Color.PRETO));
        posicaoNovaPeca('e', 8, new Rei(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('f', 8, new Bispo(tabuleiro, Color.PRETO));
        posicaoNovaPeca('g', 8, new Cavalo(tabuleiro, Color.PRETO));
        posicaoNovaPeca('h', 8, new Torre(tabuleiro, Color.PRETO));

        posicaoNovaPeca('a', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('b', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('c', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('d', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('e', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('f', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('g', 7, new Peao(tabuleiro, Color.PRETO, this));
        posicaoNovaPeca('h', 7, new Peao(tabuleiro, Color.PRETO, this));
    }
}
