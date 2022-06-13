package tabuleiro;

public abstract class Peca {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    /**
     * Assim que inicia a posicao da peça é nula
     * @param tabuleiro
     */
    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    /**
     * Tabuleiro associado a uma peça só fica acessível para as peças e subpeças
     * Assim protegendo um pouco mais o sistema e evitando erros de desenvolvimento
     * @return
     */
    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract boolean[][] movimentosPossiveis();

    /**
     * Aqui temos metodo concreto usado um método abstrato movimentosPossíveis
     * HookMétodos usado no Templetate Métodos
     * @param posicao
     * @return
     */
    public boolean movimentoPossivel(Posicao posicao) {
        return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
    }
    /**
     * Aqui verifica se existe algum movimento possível para aquele peça ou
     * se a mesma está travada
     * A matriz será varrida para verificar a possibilidade de movimento
     */
    public boolean temAlgumMovimentoPossivel(){
        boolean[][] matrizBoolean = movimentosPossiveis();
        for (int lin=0; lin < matrizBoolean.length; lin++){
            for (int col=0; col <matrizBoolean.length; col++){
                if (matrizBoolean[lin][col]) {
                    return true;
                }
            }
        }
        return false;
    }
}
