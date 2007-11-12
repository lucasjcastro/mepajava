/*
 * Lexico.java
 *
 * Created on November 8, 2007, 11:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author das
 */
public class Lexico {
    
    private BufferedReader entrada;
    private LinkedList<Token> listaTokens;
    
    private int lineMark = 0;
    
    private boolean Debug = true;
    
    private int collunMark = 1;
    
    /** Creates a new instance of Lexico */
    public Lexico () {
        listaTokens = new LinkedList<Token>();
    }
    
    public LinkedList<Token> getTokens (String entrada) {
        File arquivo = new File (entrada);
        if(!arquivo.exists ()) {
            System.err.println ("Erro: Arquivo nao encontrado ou nao existe.");
            System.out.println ("Compilador MEPA");
            System.out.println ("Por Douglas Schmidt e Fernando Biesdorf");
            System.out.println ("USO: mepajava <entrada.pas> <saida.mep>");
            
            System.exit (1);
        }
        try {
            
            this.entrada = new BufferedReader (new FileReader (arquivo));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace ();
            System.err.println ("Arquivo nao encontrado :" + ex.getMessage ());
            
            System.exit (1);
        }
        try {
            analisar ();
        } catch (IOException ex) {
            ex.printStackTrace ();
            System.err.println ("Erro ao analisar entrada: " + ex.getMessage ());
        }
        return listaTokens;
    }
    
    private void analisar () throws IOException {
        String linha = null;
        String palavra = new String ("");
        char caracter;
        int collunCount;
        while ((linha = this.entrada.readLine ()) != null) {
            lineMark++;
            collunCount = 0;
            do {
                //zera palavra e char auxiliar para proximo loop
                palavra = "";
                caracter = ' ';
                
                collunMark = collunCount+1;
                
                if (collunCount < linha.length ())
                    caracter = linha.charAt (collunCount);
                
                if (Character.isLetter (caracter)) {
                    
                    do {
                        palavra = palavra + String.valueOf (caracter);
                        collunCount++;
                        
                        if (collunCount < linha.length ())
                            caracter = linha.charAt (collunCount);
                        
                    } while(Character.isLetterOrDigit (caracter) || String.valueOf (caracter) == "_");
                    
                    // verifica se eh palavra reservada
                    if (getListaPalavrasReservadas ().contains (palavra)) {
                        
                        if (palavra.equals (Definicoes.PalavrasReservadas.TRUE.getPalavra ()) ||
                                palavra.equals (Definicoes.PalavrasReservadas.FALSE.getPalavra ())){
                            
                            listaTokens.add (new Token (palavra, getListaPalavrasReservadas ().indexOf (palavra), lineMark, collunMark,
                                    Definicoes.TiposTokens.BOLEANO, Definicoes.Notificacoes.SEM_ERROS));
                            
                            if (Debug) {
                                printToken (listaTokens.getLast ());
                            }
                            
                        } else {
                            listaTokens.add (new Token (palavra, getListaPalavrasReservadas ().indexOf (palavra), lineMark, collunMark,
                                    Definicoes.TiposTokens.PAL_RESERVADA, Definicoes.Notificacoes.SEM_ERROS));
                            if (Debug) {
                                printToken (listaTokens.getLast ());
                            }
                            
                        }
                        // se nao eh palavra res. eh identificador
                    } else {
                        
                        listaTokens.add (new Token (palavra, palavra.hashCode (), lineMark, collunMark,
                                Definicoes.TiposTokens.IDENTIFICADOR, Definicoes.Notificacoes.SEM_ERROS));
                        
                        // CASO SEJA NECESSARIO GUARDAR OS IDENTIFICADORES EM UMA HASH, OS IDENT DEVEM SER ADICIONADOS A ELA AQUI!
                        
                        if (Debug) {
                            printToken (listaTokens.getLast ());
                        }
                    }
                    
                    // se for digito
                } else if (Character.isDigit (caracter)) {
                    do {
                        palavra = palavra + String.valueOf (caracter);
                        collunCount++;
                        
                        if (collunCount < linha.length ())
                            caracter = linha.charAt (collunCount);
                        
                    } while(Character.isDigit (caracter));
                    
                    listaTokens.add (new Token (palavra, Integer.parseInt (palavra), lineMark, collunMark,
                            Definicoes.TiposTokens.INTEIRO, Definicoes.Notificacoes.SEM_ERROS));
                    
                    if (Debug) {
                        printToken (listaTokens.getLast ());
                    }
                    
                    // se for simbolo
                } else if (getListaSimbolos ().contains (String.valueOf (caracter))) {
                    palavra = palavra + String.valueOf (caracter);
                    collunCount++;
                    
                    if (collunCount < linha.length ())
                        caracter = linha.charAt (collunCount);
                    
                    if (getListaSimbolosCompostos ().contains (palavra + String.valueOf (caracter))) {
                        palavra = palavra + String.valueOf (caracter);
                        collunCount++;
                        
                        listaTokens.add (new Token (palavra, getListaSimbolosCompostos ().indexOf (palavra), lineMark, collunMark,
                                Definicoes.TiposTokens.SIMBOLO_COMP, Definicoes.Notificacoes.SEM_ERROS));
                        
                        if (Debug) {
                            printToken (listaTokens.getLast ());
                        }
                        
                    } else {
                        
                        listaTokens.add (new Token (palavra, getListaSimbolos ().indexOf (palavra), lineMark, collunMark,
                                Definicoes.TiposTokens.SIMBOLO, Definicoes.Notificacoes.SEM_ERROS));
                        
                        if (Debug) {
                            printToken (listaTokens.getLast ());
                        }
                    }
                    
                } else if (String.valueOf (caracter).equals (" ") || String.valueOf (caracter).equals ("")) {
                    collunCount++;
                    continue;
                    
                } else if (String.valueOf (caracter).equals ("\\")) {
                    collunCount++;
                    
                    if (collunCount < linha.length ())
                        caracter = linha.charAt (collunCount);
                    
                    if (String.valueOf (caracter).equals ("t")) {
                        continue;
                    } else {
                        System.err.println ("ERRO LEXICO. Caracter '\' encontrado invalido.");
                        System.exit (2);
                    }
                    
                } else {
                    System.err.println ("ERRO LEXICO. Caracter nao pode ser classificado.");
                    System.exit (2);
                }
                
            } while(collunCount < linha.length ()); // LOOP DE PALAVRAS
            
        } // LOOP DE LINHAS
    }
    
    /** Metodo que retorna valores reais (Strings em uma lista) dos simbolos enumerados em Definicoes */
    private LinkedList<String> getListaSimbolos () {
        LinkedList<String> simbolos = new LinkedList<String>();
        for (int c = 0; c < Definicoes.Simbolos.values ().length; c++) {
            simbolos.add (Definicoes.Simbolos.values ()[c].getSimbolo ());
        }
        return simbolos;
    }
    
    private LinkedList<String> getListaSimbolosCompostos () {
        LinkedList<String> simbolos = new LinkedList<String>();
        for (int c = 0; c < Definicoes.SimbolosCompostos.values ().length; c++) {
            simbolos.add (Definicoes.SimbolosCompostos.values ()[c].getSimbolo ());
        }
        return simbolos;
    }
    
    private LinkedList<String> getListaPalavrasReservadas () {
        LinkedList<String> palavrasReservadas = new LinkedList<String>();
        for (int c = 0; c < Definicoes.PalavrasReservadas.values ().length; c++) {
            palavrasReservadas.add (Definicoes.PalavrasReservadas.values ()[c].getPalavra ());
        }
        return palavrasReservadas;
    }
    
    private void printToken (Token tk) {
        System.out.println ("Nome: " + tk.getNome ());
        System.out.println ("Tipo: " + tk.getTipo ());
        System.out.println ("Valor: "+ tk.getValor ());
        System.out.println ("Nota: " + tk.getNotificacao ());
        System.out.println ("Linha: " + tk.getLinha ());
        System.out.println ("Coluna: " + tk.getColuna ());
        System.out.println ("");
    }
    
}
