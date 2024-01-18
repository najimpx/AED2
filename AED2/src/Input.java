import java.util.*;

public class Input {
	int tempo = 1;
	Grafo principal() {
		Scanner in = new Scanner(System.in);
		ArrayList<String> nomeVertices = new ArrayList<String>();
		ArrayList<String> ListaLinha = new ArrayList<String>();
		//quantidade de vértice determina o tamanho
		int qtVert = in.nextInt();
		in.nextLine();
		Grafo original = new Grafo();
		
		//adiciona os vertices
		for(int i = 0; i < qtVert;i++) {
			String entrada = in.nextLine();
			ListaLinha.add(entrada);
			String[] partes = entrada.split(":");
			String verticeAtual = partes[0];
	        //adiciona uma nova lista cujo primeiro valor e o vertice e os proximos os vertices que aponta
			original.adicionaVertice(verticeAtual); 
			nomeVertices.add(verticeAtual);
		}
		
		//adiciona as arestas com as linhas armazenadas
		for(int i = 0; i < qtVert;i++) {
			//pega o primeiro elemento para ser o vértice em partes[0]
			String entrada = ListaLinha.get(i);
			String[] partes = entrada.split(":");
			String verticeAtual = partes[0];

			//array onde cada elemento sao os vertices apontados
	        String[] elementos = null;
	        if(partes.length > 1) {
	        	elementos = partes[1].split(";");
				//armazena o vertice e cria caminho
				for(int k = 0; k < elementos.length;k++) {
					original.adicionaAresta(verticeAtual, elementos[k].replaceAll(" ",""));
				}
	        }
		}
		//coloca valores de descoberta e termino no grafo
		BuscaDeProfundidade(original,qtVert,nomeVertices);
		//metodo inverte o grafo
		Grafo transposto = inverteGrafo(original);
		
		//coloca valores de descoberta e termino no grafo transposto
		BuscaDeProfundidade(transposto,qtVert,nomeVertices);
		
		ArrayList<Integer> ListaTerminaOriginalInv = new ArrayList<Integer>();
		//lista crescente dos nomes dos vertices em valor terminado no grafo original
		OrdenaListaCresc(qtVert,ListaTerminaOriginalInv,original,transposto);
		//inverte a lista
		Collections.reverse(ListaTerminaOriginalInv);
		
		//determina os vertices fortemente conectados
		int forte = 0;
		for(int i = 0;i < qtVert; i++) {
			if(transposto.getVertices().get(ListaTerminaOriginalInv.get(i)).getForca() == -1) {
				forte++;
				conectadoFortemente(transposto,forte,ListaTerminaOriginalInv.get(i),qtVert);
			}
		}
		
		//valor para escolher a representacao
		int representacao = in.nextInt();
		
		int nGrupoVertConec = 0;
		//numero de componentes fortemente conectados
		nGrupoVertConec = checaGrafoConexo(transposto, qtVert);
		System.out.println(nGrupoVertConec);
		
		forcaGrafoOriginal(original,transposto,qtVert);
		
		if(representacao == 1) {
			//metodo de representacao coleção de listas de adjacências
			//ListasDeAdjacencia(matriz, qtVert);
			GrafoLista  listaForte = new GrafoLista(qtVert);
			for(int i = 1; i < nGrupoVertConec+1; i++) {
				juntaComponentesLista(original,listaForte,i);
			}
			arestaComponentesLista(original,listaForte);
			
			ordemTopLista(listaForte);
			//escreve os vertices em ordem topologica
			for(int i = 0; i < listaForte.getVertices().size(); i++) {
				System.out.print(listaForte.getVertices().get(i).getNome()+" ");
			}
			System.out.println();
			imprimeGrafoLista(listaForte,nomeVertices);
		}
		else if(representacao == 2) {
			//metodo de representacao  matriz de adjacências.
			GrafoMatriz  matrizForte = new GrafoMatriz(qtVert);
			for(int i = 1; i < nGrupoVertConec+1; i++) {
				juntaComponentesMatriz(original,matrizForte,i);
			}
			arestaComponentesMatriz(original,matrizForte);
			ordemTopMatriz(matrizForte);
			//escreve os vertices em ordem topologica
			for(int i = 0; i < matrizForte.getVertices().size(); i++) {
				System.out.print(matrizForte.getVertices().get(i).getNome()+" ");
			}
			System.out.println();
			imprimeGrafoMatriz(matrizForte);
		}
		in.close();

		return original;
	}
	
	// recebe o grafo construído anteriormente e devolve um outro com os vértices invertidos
	public Grafo inverteGrafo (Grafo grafo) {
		Grafo invertido = new Grafo();
		
		// para cada vertice do grafo original, faça:
		for(int i = 0; i < grafo.getVertices().size(); i++) {
			// criei um novo vertice (novoVert) para pertencer ao grafo invertido já copiando seu nome
			String nomeV = grafo.getVertices().get(i).nome;
			Vertice novoVert = new Vertice(nomeV);
			// coloca o novo vertice na lista de vertices do grafo invertido
			invertido.getVertices().add(novoVert);
			// *porém as array lists de entrada e saida do vertice criado ainda não foram preenchidas
		}
		
		// (para cada aresta do grafo original, faça:)
		for(int i = 0; i < grafo.getArestas().size(); i++) {
			String auxd, auxo;
			Vertice origem;
			Vertice destino;
			// busca uma aresta no grafo original e
			// inverte a aresta (entre origem e destino) 
			auxd = grafo.getArestas().get(i).getOrigem().getNome();
			destino = invertido.procuraVertice(auxd);
			auxo = grafo.getArestas().get(i).getDestino().getNome();
			origem = invertido.procuraVertice(auxo);
			// cria a aresta com a origem e destino invertidas e as coloca no grafo invertido
			Aresta novaArest = new Aresta(origem, destino);
			invertido.getArestas().add(novaArest);
			// adiciona a aresta nova nos array de entrada e saida dos vertices correspondentes
			invertido.procuraVertice(novaArest.getOrigem().getNome()).adicionaArestaSaida(novaArest);
			invertido.procuraVertice(novaArest.getDestino().getNome()).adicionaArestaEntrada(novaArest);
		}		
		
		return invertido;
	}
	
	public void imprimeGrafoMatriz(GrafoMatriz grafo) {
		int maior = 0; // tamanho do nome mais longo dos vertices
		int[] tamanho = new int[grafo.getVertices().size()]; // armazena o tamanho de todas as strings

		// procurar o nome de vertice com a String com mais caracteres
		for(int i = 0; i < grafo.getVertices().size(); i++) {
			if(maior < grafo.getVertices().get(i).getNome().length()) {
				maior = grafo.getVertices().get(i).getNome().length();
			}
			tamanho[i] = grafo.getVertices().get(i).getNome().length();
		}

		//imprimir a matriz com as formatações arranjadas anteriormente
		for(int i = 0; i <= grafo.getVertices().size(); i++) {
			// primeira linha:
			if(i == 0){
				for(int j = 0; j < maior; j++) System.out.print(" ");
				System.out.print(" ");
				for(int j = 0; j < grafo.getVertices().size(); j++) {
					System.out.print(grafo.getVertices().get(j).getNome() + " ");
				}
			}
			// da segunda linha até nro de componentes + 1
			else {
				System.out.print(grafo.getVertices().get(i-1).getNome());
				for(int j = 0; j < (maior - tamanho[i-1]); j++) System.out.print(" ");
				System.out.print(" ");

				for(int j = 0; j < tamanho.length; j++) {
					if(grafo.getMadj()[i-1][j] == false) {
						System.out.print("0");
					}else System.out.print("1");

					for(int k = 0; k < tamanho[j]; k++) System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	public void imprimeGrafoLista(GrafoLista grafo,ArrayList<String> nomeVertices) {
		// entra em cada vertice do grafo e imprime em ordem de entrada segundo nomeVertices
		for(int z = 0; z < nomeVertices.size(); z++) {
			for(int i = 0; i < grafo.getVertices().size(); i++) {
				if(nomeVertices.get(z).equals(grafo.getVertices().get(i).getNome())) {
					System.out.print(grafo.getVertices().get(i).getNome() + ":");
					for(int j = 0; j < grafo.getVertices().get(i).getArestaSaida().size(); j++){
						System.out.print(" ");
						System.out.print(grafo.getVertices().get(i).getArestaSaida().get(j).getDestino().getNome() + ";");
					}
					System.out.println();
				}
			}
		}
	}
	
	void conectadoFortemente(Grafo grafo,int forte,int index,int qtVert) {
		int temp = 0;
		grafo.getVertices().get(index).setForca(forte);
		for(int z = 0; z < grafo.getVertices().get(index).getArestaSaida().size(); z++) {
			if(grafo.getVertices().get(index).getArestaSaida().get(z).getDestino().getForca() == -1) {
				for(int t = 0; t < qtVert; t++) {
					if(grafo.getVertices().get(index).getArestaSaida().get(z).getDestino().getNome() == grafo.getVertices().get(t).getNome()) {
						temp = t;
					}
				}
				conectadoFortemente(grafo,forte,temp,qtVert);
			}
		}
	}

	void procura(Grafo grafo, int qtVertice, String nomeVertice) {
		//ida valor global para guardar o valor ida/volta
		
		grafo.procuraVertice(nomeVertice).setDescobre(tempo);
		tempo++;
		//esse for procura todos os vertices destino que as arestas apontam
		
		for(int seta = 0; seta < grafo.procuraVertice(nomeVertice).getArestaSaida().size(); seta++) {
			String dest = grafo.procuraVertice(nomeVertice).getArestaSaida().get(seta).getDestino().getNome();
			if(grafo.procuraVertice(dest).getDescobre() == -1) {
				procura(grafo,qtVertice,dest);
			}
		}
		
		grafo.procuraVertice(nomeVertice).setTermina(tempo);
		tempo++;
	}
	
	void BuscaDeProfundidade(Grafo grafo, int qtVertice,ArrayList<String> nomeVertices) {
		for(int iter = 0; iter < qtVertice;iter++) {
			if(grafo.procuraVertice(nomeVertices.get(iter)).getDescobre() == -1) {
				procura(grafo,qtVertice,nomeVertices.get(iter));
			}	
		}
		tempo = 1;
	}
	
	void OrdenaListaCresc(int qtVert,ArrayList<Integer>ListaTerminaOriginalInv,Grafo original, Grafo transposto) {
		for(int i = 0; i < qtVert; i++) {
			int temp = 999;
			int t = 0;
			for(int j = 0; j < qtVert;j++) {
				if(temp > original.getVertices().get(j).getTermina() && !ListaTerminaOriginalInv.contains(j)) {
					temp = original.getVertices().get(j).getTermina();
					t = j;
				}
			}
			ListaTerminaOriginalInv.add(t);
		}
	}
	
	int checaGrafoConexo(Grafo grafo, int qtVert) {
		boolean conexo = true;
		int temp = 0;
		for(int z = 0; z < qtVert; z++) {
			if(grafo.getVertices().get(z).getForca() != 1) {
				conexo = false;
			}
			if(temp < grafo.getVertices().get(z).getForca()) {
				temp = grafo.getVertices().get(z).getForca();
			}
		}
		if(conexo == true) {
			System.out.println("sim");
		}
		else {
			System.out.println("não");
		}
		return temp;
		
	}
	
	// passa a relacao forte do grafo transposto para o original
	void forcaGrafoOriginal(Grafo original, Grafo transposto, int qtVert) {
		for(int i = 0; i < qtVert; i++) {
			original.getVertices().get(i).setForca(transposto.getVertices().get(i).getForca());
		}
	}
	// passa como parâmetro o grafo em que os vértices a serem juntados pertencem,
	// o grafo dos componentes (3o grafo)
	// e o numero da relacao em comum entre os vertices a serem juntados
	// devolve o vértice correspondente ao componente no novo grafo de componentes conexos
	public Vertice juntaComponentes(Grafo original, Grafo compConexos, int relacao) {
		ArrayList<String> componenteNome = new ArrayList<String>();
		for(int i = 0; i < original.getVertices().size(); i++) {
			if(original.getVertices().get(i).getForca() == relacao) {
				componenteNome.add(original.getVertices().get(i).getNome());
			}
		}

		String nomeVertice = new String();
		for(int i = 0; i < componenteNome.size(); i++) nomeVertice.concat(componenteNome.get(i));

		Vertice componente = new Vertice(nomeVertice);
		compConexos.getVertices().add(componente);
		componente.setForca(relacao);
		return componente;
	}
	
	public Vertice juntaComponentesLista(Grafo original, GrafoLista compConexos, int relacao) {
		ArrayList<String> componenteNome = new ArrayList<String>();
		for(int i = 0; i < original.getVertices().size(); i++) {
			if(original.getVertices().get(i).getForca() == relacao) {
				componenteNome.add(original.getVertices().get(i).getNome());
			}
		}

		String nomeVertice = new String();
		for(int i = 0; i < componenteNome.size(); i++) nomeVertice+=componenteNome.get(i);
		Vertice componente = new Vertice(nomeVertice);
		compConexos.getVertices().add(componente);
		componente.setForca(relacao);
		return componente;
	}
	public Vertice juntaComponentesMatriz(Grafo original, GrafoMatriz compConexos, int relacao) {
		ArrayList<String> componenteNome = new ArrayList<String>();
		for(int i = 0; i < original.getVertices().size(); i++) {
			if(original.getVertices().get(i).getForca() == relacao) {
				componenteNome.add(original.getVertices().get(i).getNome());
			}
		}

		String nomeVertice = new String();
		for(int i = 0; i < componenteNome.size(); i++) nomeVertice+=componenteNome.get(i);
		Vertice componente = new Vertice(nomeVertice);
		compConexos.getVertices().add(componente);
		componente.setForca(relacao);
		return componente;
	}
	
	public void arestaComponentesLista(Grafo original, GrafoLista compConexos) {
		for(int i = 0; i < original.getArestas().size(); i++) {
			int relacaoOrigem = original.getArestas().get(i).getOrigem().getForca();
			int relacaoDestino = original.getArestas().get(i).getDestino().getForca();
			if(relacaoOrigem != relacaoDestino) {
				for(int j = 0; j < compConexos.getVertices().size(); j++) {
					if(compConexos.getVertices().get(j).getForca() == relacaoOrigem) {
						for(int k = 0; k < compConexos.getVertices().size(); k++) {
							if(compConexos.getVertices().get(k).getForca() == relacaoDestino) {
								Aresta novaArest = new Aresta(compConexos.getVertices().get(j), compConexos.getVertices().get(k));
								compConexos.getArestas().add(novaArest);
								compConexos.getVertices().get(j).adicionaArestaSaida(novaArest);
								compConexos.getVertices().get(k).adicionaArestaEntrada(novaArest);
								compConexos.adicionaAresta(compConexos.getVertices().get(j).getNome(), compConexos.getVertices().get(k).getNome());
							}
						}
					}
				}
			}
		}
	}
	public void arestaComponentesMatriz(Grafo original, GrafoMatriz compConexos) {
		for(int i = 0; i < original.getArestas().size(); i++) {
			int relacaoOrigem = original.getArestas().get(i).getOrigem().getForca();
			int relacaoDestino = original.getArestas().get(i).getDestino().getForca();
			if(relacaoOrigem != relacaoDestino) {
				for(int j = 0; j < compConexos.getVertices().size(); j++) {
					if(compConexos.getVertices().get(j).getForca() == relacaoOrigem) {
						for(int k = 0; k < compConexos.getVertices().size(); k++) {
							if(compConexos.getVertices().get(k).getForca() == relacaoDestino) {
								Aresta novaArest = new Aresta(compConexos.getVertices().get(j), compConexos.getVertices().get(k));
								compConexos.getArestas().add(novaArest);
								compConexos.getVertices().get(j).adicionaArestaSaida(novaArest);
								compConexos.getVertices().get(k).adicionaArestaEntrada(novaArest);
								compConexos.addAresta(j,k);
							}
						}
					}
				}
			}
		}
	}
	public void arestaComponentes(Grafo original, Grafo compConexos) {
		for(int i = 0; i < original.getArestas().size(); i++) {
			int relacaoOrigem = original.getArestas().get(i).getOrigem().getForca();
			int relacaoDestino = original.getArestas().get(i).getDestino().getForca();
			if(relacaoOrigem != relacaoDestino) {
				for(int j = 0; j < compConexos.getVertices().size(); j++) {
					if(compConexos.getVertices().get(j).getForca() == relacaoOrigem) {
						for(int k = 0; k < compConexos.getVertices().size(); k++) {
							if(compConexos.getVertices().get(k).getForca() == relacaoDestino) {
								Aresta novaArest = new Aresta(compConexos.getVertices().get(j), compConexos.getVertices().get(k));
								compConexos.getArestas().add(novaArest);
								compConexos.getVertices().get(j).adicionaArestaSaida(novaArest);
								compConexos.getVertices().get(k).adicionaArestaEntrada(novaArest);
							}
						}
					}
				}
			}
		}
	}
	public String[] ordemTopMatriz(GrafoMatriz compConexos) {
		int[] ordem = new int[compConexos.getVertices().size()];
		String[] nomesOrd = new String[compConexos.getVertices().size()];
		// i = iésimo vertice 
		// j = jesimo posicao no array
		// k = vezes que precisa deslocar pra direita para inserir um novo valor
		for(int i = 0; i < compConexos.getVertices().size(); i++) {
			if(i == 0) {
				ordem[0] = compConexos.getVertices().get(i).getTermina();
			}else {
				for(int j = 0; j < i; j++) {
					if(ordem[j] >= compConexos.getVertices().get(i).getTermina()) {
						for(int k = (ordem.length - 2) - j; k >= j; k--) {
							ordem[k+1] = ordem[k];
						}
						ordem[j] = compConexos.getVertices().get(i).getTermina();
					}
				}
			}
		}
		for(int i = 0; i < compConexos.getVertices().size(); i++) {
			for(int j = 0; j < compConexos.getVertices().size(); j++) {
				if(ordem[i] == compConexos.getVertices().get(j).getTermina()) {
					nomesOrd[i] = compConexos.getVertices().get(j).getNome();
					break;
				}
			}
		}
		return nomesOrd;
	}

	public String[] ordemTopLista(GrafoLista compConexos) {
		int[] ordem = new int[compConexos.getVertices().size()];
		String[] nomesOrd = new String[compConexos.getVertices().size()];
		// i = iésimo vertice 
		// j = jesimo posicao no array
		// k = vezes que precisa deslocar pra direita para inserir um novo valor
		for(int i = 0; i < compConexos.getVertices().size(); i++) {
			if(i == 0) {
				ordem[0] = compConexos.getVertices().get(i).getTermina();
			}else {
				for(int j = 0; j < i; j++) {
					if(ordem[j] >= compConexos.getVertices().get(i).getTermina()) {
						for(int k = (ordem.length - 2) - j; k >= j; k--) {
							ordem[k+1] = ordem[k];
						}
						ordem[j] = compConexos.getVertices().get(i).getTermina();
					}
				}
			}
		}
		for(int i = 0; i < compConexos.getVertices().size(); i++) {
			for(int j = 0; j < compConexos.getVertices().size(); j++) {
				if(ordem[i] == compConexos.getVertices().get(j).getTermina()) {
					nomesOrd[i] = compConexos.getVertices().get(j).getNome();
					break;
				}
			}
		}
		return nomesOrd;
	}
}
