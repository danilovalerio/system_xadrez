package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Peao(Tabuleiro tabuleiro, Color color, PartidaXadrez partida) {
        super(tabuleiro, color);
        this.partidaXadrez = partida;
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

            //#specialmove en passant white
            if (posicao.getLinha() == 3){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda)
                        && eUmaPecaDoOponente(esquerda)
                        && getTabuleiro().peca(esquerda) == partidaXadrez.getPecaVulneravelDePassagem()){
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita)
                        && eUmaPecaDoOponente(direita)
                        && getTabuleiro().peca(direita) == partidaXadrez.getPecaVulneravelDePassagem()){
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
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

            //#specialmove en passant black
            if (posicao.getLinha() == 4){
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda)
                        && eUmaPecaDoOponente(esquerda)
                        && getTabuleiro().peca(esquerda) == partidaXadrez.getPecaVulneravelDePassagem()){
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita)
                        && eUmaPecaDoOponente(direita)
                        && getTabuleiro().peca(direita) == partidaXadrez.getPecaVulneravelDePassagem()){
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        //em ingles é Pawn
        return "P";
    }
}
