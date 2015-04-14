package estruturaDados;

import java.util.ArrayList;

import estruturaDados.Jogador.CondicaoFisica;
import jogo.ConteudoEstatico;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class Escalacao {

	/**
	 * enum EstiloDeJogo, constantes que determinam em que estilo o time vai atuar
	 */
	public enum EstiloDeJogo {
		contraAtaque(0), equilibrado(1), ataqueTotal(2);

		private int id;

		private EstiloDeJogo(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
	
	/**
	 * enum EstiloDeJogo, constantes que determinam a marcação que o time vai empregar
	 */
	public enum Marcacao {
		leve(0), moderada(1), Pesada(2);

		private int id;

		private Marcacao(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
	
	public Jogador goleiro;
	public ArrayList<Jogador> defensores;
	public ArrayList<Jogador> volantes;
	public ArrayList<Jogador> meias;
	public ArrayList<Jogador> atacantes;
	/** Lista com os jogadores reservas que podem ser utilizar na partida */
	public ArrayList<Jogador> suplentes;

	public Time time;
	public EstiloDeJogo estiloDeJogo;
	public Marcacao marcacao;
	
	/** Contador dos gols feitos durante a partida */
	public int gols;
	public int cartoesAmarelo;
	public int cartoesVermelho;
	
	private ArrayList<Jogador> jogadoresAmarelados;

	/**
	 * Construtor da classe Inicializa os arrayLists
	 * 
	 * @param Time time
	 */
	public Escalacao(Time time, EstiloDeJogo estiloDeJogo,  Marcacao marcacao) {
		this.time = time;
		this.estiloDeJogo = estiloDeJogo;
		this.marcacao = marcacao;
		gols = 0;

		goleiro = null;
		defensores = new ArrayList<Jogador>();
		volantes = new ArrayList<Jogador>();
		meias = new ArrayList<Jogador>();
		atacantes = new ArrayList<Jogador>();
		suplentes = new ArrayList<Jogador>();
		
		jogadoresAmarelados = new ArrayList<Jogador>();
	}

	/**
	 * Sorteia um jogador de uma lista
	 *
	 * @param ArrayList<Jogador> jogadores
	 * 
	 * @return Jogador
	 */
	public Jogador getJogador(ArrayList<Jogador> jogadores) {
		Jogador j;
		try{
			j = jogadores.get(ConteudoEstatico.random.nextInt(jogadores.size()));
		}catch(Exception e){
			j = null;
		}
		
		return j;
	}
	
	/**
	 * Pega o total dos FH de uma lista de jogadores
	 *
	 * @param ArrayList<Jogador> jogadores
	 *
	 * @return int total FH
	 */
	public int getTotalFH(ArrayList<Jogador> jogadores) {
		int total = 0;
		
		for(Jogador j : jogadores)
			total += j.cfh;
		
		return total;
	}

	/**
	 * Método executado quando um gol for feito
	 * 
	 * @param Jogador j, jogador que efetuou o gol
	 */
	public void gol(Jogador j) {
		gols++;
		j.gols++;
	}
	
	/**
	 * Método executado quando um jogador recebe carão amarelo
	 * 
	 * @param Jogador j, jogador que efetuou o gol
	 */
	public void amarelo(Jogador j) {
		cartoesAmarelo++;
		j.cartoesAmarelos++;
		
		if (jogadoresAmarelados.contains(j)) {
			/* Remover o jogador expulso da sua lista */
			if(j.posicao == 0){
				goleiro = null;
			}else if(j.posicao < 3){
				defensores.remove(j);
			}else if(j.posicao < 4){
				volantes.remove(j);
			}else if(j.posicao < 5){
				meias.remove(j);
			}else{
				atacantes.remove(j);
			}
		}else{
			jogadoresAmarelados.add(j);
		}
	}
	
	/**
	 * Método executado quando um jogador recebe carão vermelho
	 * 
	 * @param Jogador j, jogador que efetuou o gol
	 */
	public void vermelho(Jogador j) {
		cartoesVermelho++;
		j.cartosVermelhos++;
		
		/* Remover o jogador expulso da sua lista */
		if(j.posicao == 0){
			goleiro = null;
		}else if(j.posicao < 3){
			defensores.remove(j);
		}else if(j.posicao < 4){
			volantes.remove(j);
		}else if(j.posicao < 5){
			meias.remove(j);
		}else{
			atacantes.remove(j);
		}
	}
	
	/**
	 * Método executado quando um jogador se lesiona
	 * 
	 * @param Jogador j, jogador que se lesionou
	 * @param int g, gravidade da lesao
	 */
	public void lesao(Jogador j, int g) {
		j.moral = g;
		/* Lesionado de 1 a 3 semanas */
		if(g == CondicaoFisica.Lesionado.getId()){
			j.recuperacaoLesao = ConteudoEstatico.random.nextInt(4)+2;
		/* Lesionado de 1 a 6 meses */
		}else if(g == CondicaoFisica.LesionadoGravemente.getId()){
			j.recuperacaoLesao = ConteudoEstatico.random.nextInt(40)+8;
		}
		
		/* Remover o jogador expulso da sua lista */
		if(j.posicao == 0){
			goleiro = suplentes.get(0);
		}else if(j.posicao < 3){
			defensores.remove(j);
			getJogador(defensores);
		}else if(j.posicao < 4){
			volantes.remove(j);
			getJogador(volantes);
		}else if(j.posicao < 5){
			meias.remove(j);
			getJogador(meias);
		}else{
			atacantes.remove(j);
			getJogador(atacantes);
		}
	}
}
