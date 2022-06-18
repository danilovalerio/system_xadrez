package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    public Peao(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao posicaoInicial = new Posicao(0, 0);

        if (getColor() == Color.BRANCO) {
            posicaoInicial.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }
            posicaoInicial.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao pos2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)
            && getQuantidadeMovimentos() == 0 &&
                    getTabuleiro().posicaoExiste(pos2) && !getTabuleiro().posicaoJaTemPeca(pos2)
            ) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            }
            posicaoInicial.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
            if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }

            posicaoInicial.setValores(posicao.getLinha() - 1, posicao.getColuna() +1);
            if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }
        } else {
            posicaoInicial.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }
            posicaoInicial.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao pos2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)
                    && getQuantidadeMovimentos() == 0 &&
                    getTabuleiro().posicaoExiste(pos2) && !getTabuleiro().posicaoJaTemPeca(pos2)
            ) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            }
            posicaoInicial.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
            if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }

            posicaoInicial.setValores(posicao.getLinha() + 1, posicao.getColuna() +1);
            if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
                mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;

            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
