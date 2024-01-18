//Rafael Nazima 11208311
//Daniel Yuji Yamada 10430920
public class Main {
	public static void main(String args[]) {
		Input inp = new Input();
		Grafo teste = inp.principal();
		Grupo amigos = new Grupo();
		
		for(int i = 0; i<teste.getVertices().size(); i++) {
			if(teste.getVertices().get(i).getNome().equals("Paul")) {
				int forcaAmigo = teste.getVertices().get(i).getForca();
				amigos.criaGrupo(teste, forcaAmigo);
			}	
		}
		System.out.println("");
		for(int j = 0; j < amigos.getPessoas().size(); j++) {
			System.out.print(amigos.getPessoas().get(j).getNome()+" ");
		}
	}
}
