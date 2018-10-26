package graph;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {
	private static final int INFINITO = Integer.MAX_VALUE;
	private List<Node> s = new ArrayList();
	private List<Node> q = new ArrayList();
	private Node dest, orig;
	private static final Node nil = new Node("nil");
	private String paiMatriz = "", dMatriz = "", cabecalhoMatriz = "";

	public Dijkstra(Graph graph, Node orig, Node dest) {
		// Se tiver um destino, seta ele no atributo
		if (dest != null) {
			this.dest = dest;
		}
		
		this.orig = orig;

		dijkstra(graph, orig);
	}

	private void initialize(Graph graph, Node orig) {
		for (Node node : graph.getNodes()) {
			node.setDist(INFINITO);
			node.setStatus("ABERTO");
			node.setParent(nil);
			q.add(node);
		}

		// Origem inicia com a dist�ncia 0
		orig.setDist(0);
	}

	public void dijkstra(Graph graph, Node orig) {
		// Inicializa o grafo para aplicar o algoritmo
		initialize(graph, orig);

		while (!q.isEmpty()) {
			// Pega o v�rtice n�o explorado com menor dist�ncia
			Node u = extractMin(q);

			// Para o loop se tiver um destino determinado e o v�rtice u for ele
			if (this.dest != null)
				if (u.equals(this.dest)) {
					// Limpa q e para o loop
					q.clear();
					break;
				}

			// Seta o v�rtice na lista dos explorados e altera o status dele
			s.add(u);
			u.setStatus("EXPLORADO");

			// Remove o v�rtive u da lista de n�o explorados
			q.remove(u);

			// Relaxa cada v�rtice n�o explorado adjacente a v
			for (Node v : u.getAdjs(graph.isDirected()))
				if (!v.getStatus().equals("EXPLORADO"))
					relax(u, v);
		}

		// Monta matriz de roteamento
		setMatrizRoteamento(graph);
	}

	private void relax(Node u, Node v) {
		Edge edgeUV = u.getEdgeByNode(v);
		float wuv = edgeUV.getValue();

		// Se a dist�ncia do v�rice adjacente for maior do que a dist�ncia de u + o v�rtice que leva at� v
		// Atualiza a dist�ncia dele
		if (v.getDist() > (u.getDist() + wuv)) {
			// Atualiza valores do v�rtice
			v.setDist(u.getDist() + wuv);
			v.setParent(u);
		}
	}

	/**
	 * Utiliza stream na lista q para verificar qual o v�rtice com menor dist�ncia.
	 * @param q - lista de v�rtices n�o fechados
	 * @return V�rtice n�o explorado com menor dist�ncia
	 */
	private Node extractMin(List<Node> q) {
		return Collections.min(q);
	}

	public void printMatrizRoteamento() {
		System.out.println("\n\t" + cabecalhoMatriz);
		System.out.println("pai:\t" + paiMatriz);
		System.out.println("d:\t" + dMatriz + "\n");
	}

	public void setMatrizRoteamento(Graph grafo) {
		cabecalhoMatriz = "";
		paiMatriz = "";
		dMatriz = "";

		for (Node v : grafo.getNodes()) {
			if (v.getDist() == INFINITO) {
				cabecalhoMatriz += v.getId() + "\t";
				dMatriz += "- \t";
				paiMatriz += "- \t";
			} else {
				cabecalhoMatriz += v.getId() + "\t";
				paiMatriz += v.getParent().getId() + "\t";

				// Seta as casas decimais s� quando s�o significantes
				// dMatriz += v.getDistancia() + "\t";
				if (v.getDist() == (long) v.getDist()) {
					dMatriz += String.format("%d", (long) v.getDist()) + "\t";
				} else {
					dMatriz += String.format("%s", v.getDist()) + "\t";
				}
			}
		}
	}

	/**
	 * Constroi o caminho de v�rtices da origem ao destino. Para isso, baseia-se
	 * no pai de cada v�rtice. Ou seja, a partir do destino, pega o pai at�
	 * chegar na origem.
	 * 
	 * @param destino v�rtice de destino do caminho.
	 * @return List de v�rtices at� o destino.
	 */
	public List<Node> getCaminho(Node destino) {
		List caminho = new ArrayList();
		boolean chegou = false;
		Node atual = destino.getParent();

		// Enquanto n�o chegar ao destino
		while (!chegou) {
			if (atual.getParent() == null)
				System.out.println("P�, de ruim no " + atual.getId());

			// Se o v�rtice atual n�o for igual ao da origem
			if (!atual.equals(this.orig)) {
				// Add o v�rtice atual na lista de caminho
				caminho.add(atual);

				// O atual ser� seu pai
				atual = atual.getParent();
			}
			// Quando chegar no destino, add o atual na lista e sai do loop
			else {
				caminho.add(atual);
				chegou = true;
			}
		}

		// Inverte ordem da lista visto que o caminho � baseado no pai dos  v�rtices
		Collections.reverse(caminho);
		return caminho;
	}

	// Construtor vazio.
	public Dijkstra() {
	}
}
