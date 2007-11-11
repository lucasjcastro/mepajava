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
    
    private int lineMark = 1, collunMark = 1;
    
    /** Creates a new instance of Lexico */
    public Lexico() {
        listaTokens = new LinkedList<Token>();
    }
    
    public LinkedList<Token> getTokens(String entrada) {
        File arquivo = new File(entrada);
        if(!arquivo.exists()) {
            System.err.println("Erro: Arquivo nao encontrado ou nao existe.");
            System.out.println("Compilador MEPA");
            System.out.println("Por Douglas Schmidt e Fernando Biesdorf");
            System.out.println("USO: mepajava <entrada.pas> <saida.mep>");
            
            System.exit(1);
        }
        try {
            
            this.entrada = new BufferedReader(new FileReader(arquivo));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Arquivo nao encontrado :" + ex.getMessage());
            
            System.exit(1);
        }
        try {
            analisar();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao analisar entrada: " + ex.getMessage());
        }
        return listaTokens;
    }
    
    private void analisar() throws IOException {
        String linha = null;
        String palavra = new String("");
        char caracter;
        int collunCount;
        while ((linha = this.entrada.readLine()) != null) {
            lineMark++;
            collunCount = 0;
            do {
                collunMark = collunCount+1;
                caracter = linha.charAt(collunCount);
                if (Character.isLetter(caracter)) {
                    
                    do {
                        palavra = palavra + String.valueOf(caracter);
                        collunCount++;
                        caracter = linha.charAt(collunCount);
                    } while(Character.isLetterOrDigit(caracter) || String.valueOf(caracter) == "_");
                    
                    for(Definicoes.PalavrasReservadas reservada : Definicoes.PalavrasReservadas.values()) {
                        // palavra reservada
                        if (reservada.getPalavra().equals(palavra)) {
                            if (reservada.getPalavra().equals(Definicoes.PalavrasReservadas.TRUE) ||
                                    reservada.getPalavra().equals(Definicoes.PalavrasReservadas.FALSE))
                                
                                listaTokens.add(new Token(palavra, reservada.ordinal(), lineMark, collunMark,
                                        Definicoes.TiposTokens.BOLEANO, Definicoes.Notificacoes.SEM_ERROS));
                            
                            else
                                listaTokens.add(new Token(palavra, reservada.ordinal(), lineMark, collunMark,
                                        Definicoes.TiposTokens.PAL_RESERVADA, Definicoes.Notificacoes.SEM_ERROS));
                            break;
                        }
                    }
                    // identificador
                    listaTokens.add(new Token(palavra, palavra.hashCode(), lineMark, collunMark,
                            Definicoes.TiposTokens.IDENTIFICADOR, Definicoes.Notificacoes.SEM_ERROS));
                    
                    // se for digito
                } else if (Character.isDigit(caracter)) {
                    do {
                        palavra = palavra + String.valueOf(caracter);
                        collunCount++;
                        caracter = linha.charAt(collunCount);
                    } while(Character.isDigit(caracter));
                    
                    listaTokens.add(new Token(palavra, Integer.parseInt(palavra), lineMark, collunMark,
                            Definicoes.TiposTokens.INTEIRO, Definicoes.Notificacoes.SEM_ERROS));
                    
                    // se for simbolo
                } else if (getListaSimbolos().contains(String.valueOf(caracter))) {
                    palavra = palavra + String.valueOf(caracter);
                    collunCount++;
                    caracter = linha.charAt(collunCount);
                    
                    if (getListaSimbolosCompostos().contains(palavra + String.valueOf(caracter))) {
                        palavra = palavra + String.valueOf(caracter);
                        collunCount++;
                        caracter = linha.charAt(collunCount);
                        
                        for(int c = 0; c <= Definicoes.SimbolosCompostos.values().length; c++) {
                            if (palavra.equals(Definicoes.SimbolosCompostos.values()[c].getSimbolo())) {
                                listaTokens.add(new Token(palavra, Definicoes.SimbolosCompostos.values()[c].ordinal(), lineMark, collunMark,
                                        Definicoes.TiposTokens.SIMBOLO_COMP, Definicoes.Notificacoes.SEM_ERROS));
                                break;
                            }
                        }
                        
                    } else {
                        for(int c = 0; c <= Definicoes.Simbolos.values().length; c++) {
                            if (palavra.equals(Definicoes.Simbolos.values()[c].getSimbolo())) {
                                listaTokens.add(new Token(palavra, Definicoes.Simbolos.values()[c].ordinal(), lineMark, collunMark,
                                        Definicoes.TiposTokens.SIMBOLO, Definicoes.Notificacoes.SEM_ERROS));
                                break;
                            }
                        }
                    }
                    
                } else if (String.valueOf(caracter).equals(" ")) {
                    continue;
                    
                } else if (String.valueOf(caracter).equals("\\")) {
                    collunCount++;
                    caracter = linha.charAt(collunCount);
                    if (String.valueOf(caracter).equals("t")) {
                        continue;
                    } else {
                        System.err.println("ERRO LEXICO. Caracter '\' encontrado invalido.");
                        System.exit(2);
                    }
                    
                } else {
                    System.err.println("ERRO LEXICO. Caracter nao pode ser classificado.");
                    System.exit(2);
                }
                
                //zera palavra para proximo loop
                palavra = "";
            } while(collunCount < linha.length()); // LOOP DE PALAVRAS
            
        } // LOOP DE LINHAS
    }
    
    /** Metodo que retorna valores reais (em uma lista) dos simbolos enumerados em Definicoes */
    private LinkedList<String> getListaSimbolos() {
        LinkedList<String> simbolos = new LinkedList<String>();
        for (int c = 0; c <= Definicoes.Simbolos.values().length; c++) {
            simbolos.add(Definicoes.Simbolos.values()[c].getSimbolo());
        }
        return simbolos;
    }
    
    private LinkedList<String> getListaSimbolosCompostos() {
        LinkedList<String> simbolos = new LinkedList<String>();
        for (int c = 0; c <= Definicoes.SimbolosCompostos.values().length; c++) {
            simbolos.add(Definicoes.Simbolos.values()[c].getSimbolo());
        }
        return simbolos;
    }
    
}
