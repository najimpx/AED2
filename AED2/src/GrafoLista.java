import java.util.*;

public class GrafoLista extends Grafo{
	private ArrayList<ArrayList<String>> listaadj;
	
	
	// instancia Lista de Adjacencia
	public GrafoLista(int nVertices) {
		this.listaadj = new ArrayList<ArrayList<String>>();
	}
	
	public ArrayList<ArrayList<String>> getListaadj() {
		return this.listaadj;
	}
	
	// adiciona na lista de adjacencia do vertice v o vertice u
	public void adicionaAresta(String v, String u) {
		for(int i = 0; i < this.listaadj.size(); i++) {
			this.listaadj.get(i).add(u);
			break;
		}
	}
	
	
}

