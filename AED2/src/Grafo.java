import java.util.*;

public class Grafo {
	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	
	public Grafo() {
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
	}
	
	// adiciona o vertice no grafo
	public void adicionaVertice(String nomeV) {
		Vertice novoV = new Vertice(nomeV);
		this.vertices.add(novoV);
	}
	
	// adiciona aresta de vertice v para u no grafo
	public void adicionaAresta(String nomeV, String nomeU) {
		Vertice origem = this.procuraVertice(nomeV);
		Vertice destino = this.procuraVertice(nomeU);
		
		if(origem == null) {
			System.out.println("Voce tentou adicionar uma aresta mas o Vertice de origem nao existe!");
			return;
		}
		if(destino == null) {
			System.out.println("Voce tentou adicionar uma aresta mas o Vertice de destino nao existe!");
			return;
		}
		
		if(origem != null && destino != null) {
			Aresta novaA = new Aresta(origem, destino);
			this.arestas.add(novaA);
			origem.adicionaArestaSaida(novaA);
			destino.adicionaArestaEntrada(novaA);
		}
	}
	
	// procura o vertice pelo nome no grafo
	public Vertice procuraVertice(String nome) {
		for(int i = 0; i < this.vertices.size(); i++) {
			if(this.vertices.get(i).getNome().equals(nome)) {
				Vertice v = this.vertices.get(i);
				return v;
			}
		}
		return null;
	}
	
	public ArrayList<Vertice> getVertices() {
		return this.vertices;
	}
	
	public ArrayList<Aresta> getArestas() {
		return this.arestas;
	}
}
