package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {
    public Rainha(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override
    public String toString() {
        //no ingles retorna Q de Queen
        return "Q";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        /**
         * Vamos "olhar" nas 4 direcoes da peca para ver aonde ela pode percorrer
         */
        Posicao posicaoInicial = new Posicao(0, 0);

        /**
         * Verifica as alinhas ACIMA
         */
        posicaoInicial.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getLinha() > 0) {
                posicaoInicial.setLinha(posicaoInicial.getLinha() - 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Verifica as colunas a ESQUERDA
         */
        //posicaoInicial = new Posicao(0,0);
        posicaoInicial.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getColuna() > 0) {
                posicaoInicial.setColuna(posicaoInicial.getColuna() - 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Verifica as colunas a DIREITA
         */
        posicaoInicial.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getColuna() < 7) {
                posicaoInicial.setColuna(posicaoInicial.getColuna() + 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Verifica as linhas ABAIXO
         */
        //posicaoInicial = new Posicao(0,0);
        posicaoInicial.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getLinha() < 7) {
                posicaoInicial.setLinha(posicaoInicial.getLinha() + 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicao) && eUmaPecaDoOponente(posicao)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Noroeste diagonal superior esquerda
         */
        posicaoInicial.setValores(posicao.getLinha() - 1, posicao.getColuna()-1);
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getLinha() > 0) {
                posicaoInicial.setValores(posicaoInicial.getLinha() - 1, posicaoInicial.getColuna()-1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Nordeste - diagonalsuperior direita
         */
        //posicaoInicial = new Posicao(0,0);
        posicaoInicial.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getColuna() > 0) {
                posicaoInicial.setValores(posicaoInicial.getLinha() -1, posicaoInicial.getColuna() + 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * SUDESTE - diagonal inferior direita
         */
        posicaoInicial.setValores(posicao.getLinha() +1, posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getColuna() < 7) {
                posicaoInicial.setValores(posicaoInicial.getLinha() + 1, posicaoInicial.getColuna() + 1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicaoInicial) && eUmaPecaDoOponente(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        /**
         * Sudoeste - diagonal esquerda inferior
         */
        posicaoInicial.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1 );
        while (getTabuleiro().posicaoExiste(posicaoInicial) && !getTabuleiro().posicaoJaTemPeca(posicaoInicial)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
            if (posicaoInicial.getLinha() < 7) {
                posicaoInicial.setValores(posicaoInicial.getLinha() + 1, posicaoInicial.getColuna() -1);
            } else {
                break;
            }
        }

        if (getTabuleiro().posicaoExiste(posicao) && eUmaPecaDoOponente(posicao)) {
            mat[posicaoInicial.getLinha()][posicaoInicial.getColuna()] = true;
        }

        return mat;
    }
}
