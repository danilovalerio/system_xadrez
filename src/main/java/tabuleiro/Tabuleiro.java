package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    /**
     * Matriz de pecas do tabuleiro
     */
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new TabuleiroException(
              "Erro ao criar tabuleiro, que deve ter ao menos uma linha e uma coluna."
            );
        }

        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if (!posicaoExiste(linha, coluna)) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {
        if (!posicaoExiste(posicao.getLinha(), posicao.getColuna())) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void posicaPeca(Peca peca, Posicao posicao){
        if (posicaoJaTemPeca(posicao)) {
            throw new TabuleiroException(
              "Já tem uma peça nessa posição "+posicao
            );
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removePeca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new TabuleiroException("Posição não disponível no tabuleiro.");
        }
        if (peca(posicao) == null) {
            return null;
        }
        Peca auxiliar = peca(posicao);
        //retiramos essa peca do tabuleiro
        auxiliar = null;

        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return auxiliar;
    }

    public boolean posicaoExiste(Posicao posicao) {
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    private boolean posicaoExiste(int linha, int coluna){
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoJaTemPeca(Posicao posicao) {
        if (!posicaoExiste(posicao.getLinha(), posicao.getColuna())) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }
        return peca(posicao) != null;
    }


}
