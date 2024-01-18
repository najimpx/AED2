public class Aresta {
	private Vertice origem;
	private Vertice destino;
	
	public Aresta(Vertice inicio, Vertice fim) {
		this.origem = inicio;
		this.destino = fim;
	}
	
	public Vertice getOrigem() {
		return this.origem;
	}
	
	public Vertice getDestino() {
		return this.destino;
	}
	
}