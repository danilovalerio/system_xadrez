package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {
    private char coluna;
    private int linha;

    public PosicaoXadrez(char coluna, int linha) {
        if (
                coluna < 'a' || coluna > 'h' ||
                        linha < 1 || linha > 8
        ) {
            throw new XadrezException(
                    "Erro ao instancia a posicao no xadrez, valores tem que ser de a1 até h8."
            );
        }

        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao paraPosicaoMatriz() {
        int linhaPos = 8 - linha;
        int colPos = coluna -'a';
//        System.out.println("contato ok");
        return new Posicao(linhaPos, colPos);
    }

    protected static PosicaoXadrez dePosicaoParaPosicaXadrez(Posicao posicao){
        return new PosicaoXadrez((char) ('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "PosicaoXadrez{" +
                "coluna=" + coluna +
                ", linha=" + linha +
                '}';
    }
}
