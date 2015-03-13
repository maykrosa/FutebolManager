package estruturaDados;

import jogo.SimuladorDePartida;
import estruturaDados.competicao.Copa;

/**
 * Jogos da copa são disputados em caráter de ida e
 *  volta, gols fora de casa são o primeiro critério de desempate.
 * 
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class ConfrontoIdaVolta{
	public Time t1;
	public Time t2;
	
	public int turno;
	public boolean encerrado;
	
	public Copa copa;
	
	private Time vencedor;
	
	public ConfrontoIdaVolta(Time t1, Time t2, int turno, Copa copa){
		this.t1 = t1;
		this.t2 = t2;
		
		this.turno = turno;
		this.encerrado = false;
		this.vencedor = null;
		
		this.copa = copa;

		t1.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2], t1, t2, copa, this);
		t1.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2 +1], t2, t1, copa, this);
		
		t2.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2], t1, t2, copa, this);
		t2.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2 +1], t2, t1, copa, this);
	}
	
	public void setVencedor(Time t){
		vencedor = t;
	}
	
	public Time getVencedor(){
		if(vencedor == null){
			return t1;
		}
		return vencedor;
	}
	
	public SimuladorDePartida getPartida1(){
		return t1.calendario.partidas[copa.DIAS_DE_JOGOS[turno*2]];
	}
	
	public SimuladorDePartida getPartida2(){
		return t1.calendario.partidas[copa.DIAS_DE_JOGOS[turno*2 +1]];
	}
}
