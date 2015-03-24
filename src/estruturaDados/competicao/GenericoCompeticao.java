package estruturaDados.competicao;

import java.util.ArrayList;

import estruturaDados.Jogador;

public abstract class GenericoCompeticao {

	/* Regras */
	public int NUMERO_DE_PARTICIPANTES;
	public int[] DIAS_DE_JOGOS;
	
	public ArrayList<Jogador> artilheiros;
	public ArrayList<Jogador> cartoesAmarelos;
	public ArrayList<Jogador> cartoesVermelhos;
	
	public abstract void comecarTemporada();
	public abstract void atualizar(int semana);
	public abstract void fimTemporada();
	
	/**
	 * 0 - Copa
	 * 1 - Campeonato
	 */
	public abstract int getID();
}
