package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {
    public Cavalo(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override
    public String toString() {
        //no ingles retorna N por conta do King
        return "C";
    }

    /**
     * Pode mover se a peca nessa posicao nao for nula e for adversaria
     */
    private boolean podeMover(Posicao posicao) {
        PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
        return peca == null || peca.getColor() != getColor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao posicaoAuxiliar = new Posicao(0,0);

        posicaoAuxiliar.setValores(posicao.getLinha() -1, posicao.getColuna() -2);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        posicaoAuxiliar.setValores(posicao.getLinha() -2, posicao.getColuna() -1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Esquerda
        posicaoAuxiliar.setValores(posicao.getLinha() - 2, posicao.getColuna() +1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Direita
        posicaoAuxiliar.setValores(posicao.getLinha() - 1, posicao.getColuna() +2);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Noroeste (Diagonal superior esquerda)
        posicaoAuxiliar.setValores(posicao.getLinha() +1, posicao.getColuna() +2);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Nordeste (Diagonal superior direita)
        posicaoAuxiliar.setValores(posicao.getLinha() +2, posicao.getColuna() +1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Sudoeste (Diagonal inferior esquerda)
        posicaoAuxiliar.setValores(posicao.getLinha() +2, posicao.getColuna() -1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Sudeste (Diagonal inferior direita)
        posicaoAuxiliar.setValores(posicao.getLinha() +1, posicao.getColuna() -2);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        return mat;
    }
}
