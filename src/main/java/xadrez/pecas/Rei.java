package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Color color, PartidaXadrez partida) {
        super(tabuleiro, color);
        this.partidaXadrez = partida;
    }

    @Override
    public String toString() {
        //no ingles retorna K de King
        return "R";
    }

    /**
     * Pode mover se a peca nessa posicao nao for nula e for adversaria
     */
    private boolean podeMover(Posicao posicao) {
        PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);
        return peca == null || peca.getColor() != getColor();
    }

    private boolean torreEstaAptaParaRoque(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null && p instanceof Torre && p.getColor() == getColor()&& p.getQuantidadeMovimentos() == 0;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao posicaoAuxiliar = new Posicao(0,0);

        /**
         * A seguir vamos testar todas as posicoes que o Rei pode se mover
         */
        //Acima
        posicaoAuxiliar.setValores(posicao.getLinha() -1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Abaixo
        posicaoAuxiliar.setValores(posicao.getLinha() +1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Esquerda
        posicaoAuxiliar.setValores(posicao.getLinha(), posicao.getColuna() -1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Direita
        posicaoAuxiliar.setValores(posicao.getLinha(), posicao.getColuna() +1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //Noroeste (Diagonal superior esquerda)
        posicaoAuxiliar.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Nordeste (Diagonal superior direita)
        posicaoAuxiliar.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Sudoeste (Diagonal inferior esquerda)
        posicaoAuxiliar.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        // Sudeste (Diagonal inferior direita)
        posicaoAuxiliar.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
        if (getTabuleiro().posicaoExiste(posicaoAuxiliar) && podeMover(posicaoAuxiliar)) {
            mat[posicaoAuxiliar.getLinha()][posicaoAuxiliar.getColuna()] = true;
        }

        //#movimento especial Roque (castling)
        if (getQuantidadeMovimentos() == 0 && !partidaXadrez.getCheck()){
            //#special move roque pequeno ou roque do rei (castling kingside rook)
            Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (torreEstaAptaParaRoque(posicaoTorre1)) {
                Posicao casaDaDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao casaDuasADireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(casaDaDireita) == null && getTabuleiro().peca(casaDuasADireita) == null){
                    mat[posicao.getLinha()][posicao.getColuna() +2] = true;
                }
            }
            //#special move roque grande ou roque da rainha (castling queenside rook)
            Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (torreEstaAptaParaRoque(posicaoTorre1)) {
                Posicao casaAEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao duasCasasAEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao casaTresAEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(casaAEsquerda) == null
                        && getTabuleiro().peca(duasCasasAEsquerda) == null
                        && getTabuleiro().peca(casaTresAEsquerda) == null
                ){
                    mat[posicao.getLinha()][posicao.getColuna() -2] = true;
                }
            }
        }

        return mat;
    }
}
