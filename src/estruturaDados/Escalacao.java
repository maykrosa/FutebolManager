package estruturaDados;

import java.util.ArrayList;

import jogo.ConteudoEstatico;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class Escalacao {

	public Jogador goleiro;
	public ArrayList<Jogador> defensores;
	public ArrayList<Jogador> volantes;
	public ArrayList<Jogador> meias;
	public ArrayList<Jogador> atacantes;
	/** Lista com os jogadores reservas que podem ser utilizar na partida */
	public ArrayList<Jogador> suplentes;

	public Time time;
	
	/** Contador dos gols feitos durante a partida */
	public int gols;
	public int cartoesAmarelos;
	public int cartosVermelhos;
	
	private ArrayList<Jogador> jogadoresAmarelados;

	/**
	 * Construtor da classe Inicializa os arrayLists
	 * 
	 * @param Time time
	 */
	public Escalacao(Time time) {
		this.time = time;
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
		cartoesAmarelos++;
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
		cartosVermelhos++;
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
}
