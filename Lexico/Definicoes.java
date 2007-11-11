/*
 * Definicoes.java
 *
 * Created on November 10, 2007, 8:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lexico;

import java.util.LinkedList;
import java.util.Vector;

/**
 *
 * @author das
 */
public class Definicoes {
    
    // Enumeracao dos simbolos que serao classificados em tokens, lexico
    public enum Simbolos{
	MENOS ("-"),
	PONTO("."),
	VIRGULA(","),
	PONTO_VIRGULA(";"),
	ABRE_PARENTESES("("),
	FECHA_PARENTESES(")"),
	DOIS_PONTOS(":"),
	IGUAL("="),
	MENOR("<"),
	MAIOR(">"),
	MAIS("+"),
	VEZES("*"),
	ABRE_COLCHETES("["),
	FECHA_COLCHETES("]");
        
        Simbolos(String simbolo) {
            this.simbolo = simbolo;
        }
        
        // String simbolo refere-se ao valor "passado por parametro" em cada enum
        private String simbolo;
        // Retorna valor do simbolo
        public String getSimbolo() {
            return simbolo;
        }
    }
    
    public enum SimbolosCompostos{
        DIFERENTE("<>"),
        RECEBE(":="),
	PONTO_PONTO("..");
        
        SimbolosCompostos(String simbolo) {
            this.simbolo = simbolo;
        }
                
        // String simbolo refere-se ao valor "passado por parametro" em cada enum
        private String simbolo;
        // Retorna valor do simbolo
        public String getSimbolo() {
            return simbolo;
        }
    }
        
    // Enumeracao das palavras reservadas para uso na geracao de tokens, lexico
    public enum PalavrasReservadas {
	FALSE("false"),
        TRUE("true"),
        PROGRAM("program"),
        BEGIN("begin"),
        END("end"),
        LABEL("label"),
        TYPE("type"),
        ARRAY("array"),
        OF("of"),
        VAR("var"),
        PROCEDURE("procedure"),
	FUNCTION("function"),
        IF("if"),
        THEN("then"),
        ELSE("else"),
        WHILE("while"),
        DO("do"),
        OR("or"),
        AND("and"),
        DIV("div"),
        NOT("not"),
        CASE("case"),
        RECORD("record"),
        USES("uses"),
        INTEGER("integer"),
	READ("read"),
        WRITE("write");
        
        PalavrasReservadas(String palavra) {
            this.palavra = palavra;
        }
        
        // palavra assumira o valor do parametro passado para cada enum
        private String palavra;
        public String getPalavra() {
            return palavra;
        }
    }
    
    // Identifica os Tipos de Token classificados, lexico
    public enum TiposTokens {
	INTEIRO, 
        IDENTIFICADOR,
	PAL_RESERVADA,
	SIMBOLO,
	SIMBOLO_COMP,
	BOLEANO;
    }
    
    // Classificacao para os tokens nao terminais, sintatico
    public enum NaoTerminais{
	PROGRAMA,
	BLOCO,
	TIPO,
	PARAMETROS_FORMAIS,
	COMANDO,
	EXPRESSAO,
	EXPRESSAO_SIMPLES,
	TERMO,
	FATOR;
    }
    
    // Notificacoes para uso do compilador, lexico, sintatico e semantico
    enum Notificacoes{
        TERMINO,
	SEM_ERROS,
	ERRO_TOKEN_INVALIDO,
	ERRO_DESCONHECIDO,
	ERRO_ID_INVALIDO,
	ERRO_SIMBOLO_INV;
    }

    /** Classe publica explicita */
    public Definicoes() {
    }
    
}
