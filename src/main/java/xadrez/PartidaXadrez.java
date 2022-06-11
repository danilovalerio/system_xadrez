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
     */
    private Tabuleiro tabuleiro;

    public PartidaXadrez(){
        tabuleiro = new Tabuleiro(8,8);
        initialSetup();
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

    public PecaXadrez performXadrezMove(PosicaoXadrez origem, PosicaoXadrez destino){
        Posicao source = origem.paraPosicaoMatriz();
        Posicao target = destino.paraPosicaoMatriz();
        validaPosicaoOrigem(source);
        Peca pecaCapturada = realizaMovimento(source, target);
        return (PecaXadrez) pecaCapturada;
    }

    private Peca realizaMovimento(Posicao origem, Posicao destino){
        Peca p = tabuleiro.removePeca(origem);
        Peca pecaCapturada = tabuleiro.removePeca(destino);
        tabuleiro.posicaoPeca(p, destino);
        return pecaCapturada;
    }

    private void validaPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.posicaoJaTemPeca(posicao)) {
            throw new XadrezException("Não tem peça na posição de origem");
        }
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
    }
}
