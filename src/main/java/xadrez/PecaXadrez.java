package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

/**
 * Peça genérica que serve como base para representar uma peça concreta de xadrez
 */
public abstract class PecaXadrez extends Peca {
    private Color color;
    private int quantidadeMovimentos;

    public PecaXadrez(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getQuantidadeMovimentos(){
        return quantidadeMovimentos;
    }

    public void incrementaMovimentoContador(){
        quantidadeMovimentos++;
    }

    public void decrementaMovimentoContador(){
        quantidadeMovimentos--;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.dePosicaoParaPosicaXadrez(posicao);
    }

    /**
     * operações disponível somente para classe e subclasses do mesmo pacote
     */
    protected boolean eUmaPecaDoOponente(Posicao posicao) {
        PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
        return peca != null && peca.getColor() != color;
    }
}
