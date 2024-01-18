
public class GrafoMatriz extends Grafo{
	// o grafo tera um vetor armazenando a ordem dos vertices (ordem do arquivo de entrada) e uma matriz de adj
	private String[] ordem; // ordem dos vertices (armazena o nome, a posição no array indica o seu nro respectivo)
	private boolean[][] matrizadj; // matriz de adj
	
	// instancia Matriz de adjacencia
	public GrafoMatriz(int nVertices) {
		this.ordem = new String[nVertices];
		this.matrizadj = new boolean[nVertices][nVertices];
	}
	
	public String[] getOrdem() {
		return this.ordem;
	}
	
	public boolean[][] getMadj() {
		return this.matrizadj;
	}
	
	// preenche uma posição do array de ordem
	public void setOrdem(int i, String vertice) {
		ordem[i] = vertice;
	}
	
	// adiciona na matriz a aresta de um vertice v (origem) e u(destino)
	public void addAresta(int v, int u) {
		this.matrizadj[v][u] = true;
	} 
	
	public void removeAresta(int v, int u) {
		this.matrizadj[v][u] = false;
	}
}
