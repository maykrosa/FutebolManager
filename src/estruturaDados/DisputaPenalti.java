package estruturaDados;

import jogo.SimuladorDePartida;

public class DisputaPenalti {
	
	public SimuladorDePartida partida;
	
	private Escalacao vencedor;
	private Escalacao[] times;
	private int[] gols;

	public DisputaPenalti(SimuladorDePartida partida){
		this.partida = partida;
		
		times = partida.times;
		gols = new int[2];
	}
	
	public void jogar(){
		
	}
	
	public Time getVencedor(){
		return times[0].time;
	}

}
