/*
 * Token.java
 *
 * Created on November 10, 2007, 8:47 PM
 */

package Lexico;

/**
 *
 * @author das
 */
public class Token {
    
    private String nome;
    private int valor;
    private int linha, coluna;
    private Definicoes.TiposTokens tipo;
    private Definicoes.Notificacoes notificacao;
    
    /** Creates a new instance of Token */
    public Token(String nome, int valor, int linha, int coluna, Definicoes.TiposTokens tipo,
            Definicoes.Notificacoes notificacao) {
        this.nome = nome;
        this.valor = valor;
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;
        this.notificacao = notificacao;
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public int getValor() {
        return valor;
    }
    public void setValor() {
        this.valor = valor;
    }
    
    public int getLinha(){
        return linha;
    }
    public int getColuna(){
        return coluna;
    }
    public void setLinhaColuna(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public Definicoes.TiposTokens getTipo() {
        return this.tipo;
    }
    public void setTipo(Definicoes.TiposTokens tipo) {
        this.tipo = tipo;
    }
    
    public Definicoes.Notificacoes getNotificacao() {
        return notificacao;
    }
    public void setNotificacao(Definicoes.Notificacoes notificacao) {
        this.notificacao = notificacao;
    }
}
