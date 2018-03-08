/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package estruturas;

import estruturas.algoritmos.grafos.AlgoritmosBasicosGrafo;
import estruturas.algoritmos.grafos.BuscaLargura;
import estruturas.algoritmos.grafos.BuscaProfundidade;

/**
 * Implementacao de um grafo utilizando lista de adjacencias.
 * 
 * @author David Buzatto
 */
public class Grafo {
    
    private int v;
    private int e;
    private Lista<Integer>[] adj;
    
    /**
     * Constroi um grafo com v vertices.
     * 
     * @param v Quantidade de vertices.
     */
    public Grafo( int v ) {
        
        this.v = v;
        
        // cria o array da lista de adjacencias
        adj = (Lista<Integer>[]) new Lista[v];
        
        // inicializa cada posicao com uma lista vazia
        for ( int i = 0; i < v; i++ ) {
            adj[i] = new Lista<>();
        }
        
    }
    
    /**
     * Adicionar uma aresta v-w.
     * 
     * @param v Uma extremidade da aresta.
     * @param w Outra extremidade da aresta.
     */
    public void adicionarAresta( int v, int w ) {
        
        // como e um grafo, a aresta e de ida e volta, ou seja,
        // v-w e w-v. em lacos, havera dois lacos iguais para
        // cada vertice com laco
        adj[v].inserirInicio( w );
        adj[w].inserirInicio( v );
        
        e += 1;
        
    }
    
    /**
     * Retorna o conjunto de vertices adjacentes a v.
     * 
     * @param v Vertique que se deseja obter os adjacentes.
     * @return Vertices adjacentes a v.
     */
    public Iterable<Integer> adj( int v ) {
        return adj[v];
    }
    
    public int v() {
        return v;
    }
    
    public int e() {
        return e;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        int cont;
        
        sb.append( "Vertices: " ).append( v() ).append( "\n" );
        sb.append( "Arestas: " ).append( e() ).append( "\n" );
        
        for ( int v = 0; v < v(); v++ ) {
            
            cont = 0;
            sb.append( "v: " ).append( v ).append( " -> { " );
            
            for ( int w : adj[v] ) {
                if ( cont == adj[v].getTamanho() - 1 ) {
                    sb.append( w ).append( " " );
                } else {
                    sb.append( w ).append( ", " );
                }
                cont++;
            }
            
            sb.append( "}\n" );
            
        }
        
        return sb.toString();
        
    }
    
}
