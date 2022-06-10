package xadrez;

import tabuleiro.Tabuleiro;

public class PartidaXadrez {
    /**
     * Uma partida tem que ter um TABULEIRO
     * Tem que saber o TAMANHO do TABULEIRO
     */
    private Tabuleiro tabuleiro;

    public PartidaXadrez(){
        tabuleiro = new Tabuleiro(8,8);
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
}
