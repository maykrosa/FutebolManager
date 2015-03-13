package estruturaDados;

import java.util.ArrayList;

import estruturaDados.competicao.Campeonato;
import estruturaDados.competicao.Copa;

public class Confederacao {
	
	public ArrayList<Time> times;
	
	public Campeonato campeonato;
	public Copa copa;
	
	public Confederacao(ArrayList<Time> times){
		this.times = times;

//		copa = new Copa(this);
//		copa.comecarTemporada();
		campeonato = new Campeonato(this);
		campeonato.comecarTemporada();
	}
}
