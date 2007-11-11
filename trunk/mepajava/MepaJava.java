/*
 * MepaJava.java
 *
 * Created on November 8, 2007, 11:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mepajava;

import Lexico.*;
import java.util.LinkedList;

/**
 *
 * @author das
 */
public class MepaJava {
    
    private LinkedList<Token> listaTokens;
    private Lexico analiseLexica;
    
    /**
     * Creates a new instance of MepaJava
     */
    public MepaJava(String[] args) {
        this.analiseLexica = new Lexico();
        
        this.listaTokens = this.analiseLexica.getTokens(args[0]);
        
        for(Token tk : this.listaTokens) {
            System.out.print(tk.getNome() + "\t:\t");
            System.out.println(tk.getValor());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        // se entrada e saida nao estiverem especificadas a analise nao sera feita
        if(args.length < 2) {
            System.out.println("Compilador MEPA");
            System.out.println("Por Douglas Schmidt e Fernando Biesdorf");
            System.out.println("USO: mepajava <entrada.pas> <saida.mep>");
        }
        
        new MepaJava(args);
        
    }
}
