import java.util.*;

public class Vertice {
	String nome;
	int descobre; // quando o vértice foi descoberto
	int termina; // quando o vértice terminou
	ArrayList<Aresta> arestasEntrada; // arestas que chegam no vertice (quando este vertice é destino)
	ArrayList<Aresta> arestasSaida; // arestas que partem do vertice (quando este vertice é origem)
	int relacaoforte;

	public Vertice(String nomeV) {
		this.nome = nomeV;
		this.descobre = -1; // inicia com -1 (ainda não descoberto)
		this.termina = -1; // inicia com -1 (ainda não foi terminado)
		this.relacaoforte = -1; // inicia com -1 (ainda não tem relacao)
		this.arestasEntrada = new ArrayList<Aresta>();
		this.arestasSaida = new ArrayList<Aresta>();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getDescobre() {
		return this.descobre;
	}
	
	public int getTermina() {
		return this.termina;
	}
	
	public ArrayList<Aresta> getArestasEntrada() {
		return this.arestasEntrada;
	}
	
	public ArrayList<Aresta> getArestaSaida() {
		return this.arestasSaida;
	}
	
	public int getForca() {
		return this.relacaoforte;
	}
	
	public void setNome(String nomeV) {
		this.nome = nomeV;
	}
	
	public void setDescobre(int tempo) {
		this.descobre = tempo; 
	}
	
	public void setTermina(int tempo) {
		this.termina = tempo;
	}
	
	public void setForca(int relacaoforte) {
		this.relacaoforte = relacaoforte;
	}
	
	public void adicionaArestaEntrada(Aresta a) {
		this.arestasEntrada.add(a);
	}
	
	public void adicionaArestaSaida(Aresta a) {
		this.arestasSaida.add(a);
	}
	
	
}