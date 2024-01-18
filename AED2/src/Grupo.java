import java.util.*;

public class Grupo {
	ArrayList<Vertice> pessoas; // conjunto de pessoas (grupo)
	public Grupo criaGrupo(Grafo grafo, int relacao) {
		this.pessoas = new ArrayList<Vertice>();
		for(int i = 0; i < grafo.getVertices().size(); i++) {
			if(grafo.getVertices().get(i).getForca() == relacao) {
				this.pessoas.add(grafo.getVertices().get(i));
			}
		}
		return this;
	}
	

	public ArrayList<Vertice> getPessoas() {
		return this.pessoas;
	}
}