package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

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
        /**
         * Converte posicao de xadrez para posicao de matriz
         */
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
        proximoTurno();
        return (PecaXadrez) pecaCapturada;
    }

    private Peca realizaMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.peca(origem);
        tabuleiro.removePeca(origem);
        Peca pecaCapturada = tabuleiro.removePeca(destino);
        tabuleiro.posicaoPeca(p, destino);
        return pecaCapturada;
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
        /**
         * Se para a peça de origem a posição de destino não é um movimento possível
         */
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

    /**
     * Passa a posicao de coordenadas de matriz para a posição de coordenadas do xadrez
     * @param coluna
     * @param linha
     * @param peca
     */
    private void posicaoNovaPeca(char coluna, int linha, PecaXadrez peca) {
        System.out.println("posicao da peca: "+peca.getClass());
        tabuleiro.posicaoPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicaoMatriz());
    }

    private void initialSetup(){
        //tabuleiro.posicaPeca(new Torre(tabuleiro, Color.BRANCO), new Posicao(2,1));
        posicaoNovaPeca('b', 6, new Torre(tabuleiro, Color.BRANCO));
        //tabuleiro.posicaPeca(new Rei(tabuleiro, Color.PRETO), new Posicao(0,4));
        posicaoNovaPeca('a', 1, new Rei(tabuleiro, Color.PRETO));
        //tabuleiro.posicaPeca(new Torre(tabuleiro, Color.PRETO), new Posicao(0,5));
        posicaoNovaPeca('c', 2, new Torre(tabuleiro, Color.PRETO));
        posicaoNovaPeca('e', 2, new Rei(tabuleiro, Color.BRANCO));
    }
}
