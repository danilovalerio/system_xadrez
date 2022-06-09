package tabuleiro;

public class Peca {
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
}
