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
	
	public Time vencedor;
	
	public ConfrontoIdaVolta(Time t1, Time t2, int turno, Copa copa){
		this.t1 = t1;
		this.t2 = t2;
		
		this.turno = turno;
		this.encerrado = false;
		vencedor = null;
		
		this.copa = copa;

		t1.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2], t1, t2, copa, this);
		t1.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2 +1], t2, t1, copa, this);
		
		t2.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2], t1, t2, copa, this);
		t2.calendario.setJogo(copa.DIAS_DE_JOGOS[turno*2 +1], t2, t1, copa, this);
	}
	
	public boolean precisaPenalti(SimuladorDePartida p2){
		SimuladorDePartida p1 = getPartida1();
		
		int gols1 = p1.times[0].gols + p2.times[1].gols;
		int gols2 = p1.times[1].gols + p2.times[0].gols;
		
		if(gols1 > gols2)
			return false;
		else if(gols2 > gols1)
			return false;

		gols1 = p1.times[0].gols + (p2.times[1].gols*2);
		gols2 = (p1.times[1].gols*2) + p2.times[0].gols;
		
		if(gols1 > gols2)
			return false;
		else if(gols2 > gols1)
			return false;
		
		return true;
	}
	
	public SimuladorDePartida getPartida1(){
		return t1.calendario.partidas[copa.DIAS_DE_JOGOS[turno*2]];
	}
	
	public SimuladorDePartida getPartida2(){
		return t1.calendario.partidas[copa.DIAS_DE_JOGOS[turno*2 +1]];
	}
}
