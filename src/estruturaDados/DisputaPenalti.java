package estruturaDados;

import jogo.ConteudoEstatico;
import jogo.SimuladorDePartida;

public class DisputaPenalti {
	
	public SimuladorDePartida partida;
	
	public Time vencedor;
	private Escalacao[] times;
	private int[] gols;

	public DisputaPenalti(SimuladorDePartida partida){
		this.partida = partida;
		
		times = partida.times;
		gols = new int[2];
	}
	
	public void jogar(){
		/* 5 cobranças */
		for(int i=0; i<5; i++){
			gols[0] += chute(getJogador(times[0], i), times[1].goleiro);
			gols[1] += chute(getJogador(times[1], i), times[0].goleiro);
			
			/* Distancia necessaria para ganhar */
			if(gols[0] + Math.abs(i-4) < gols[1]){
				vencedor = times[1].time;
				return;
			}
			
			/* Distancia necessaria para ganhar */
			if(gols[1] + Math.abs(i-4) < gols[0]){
				vencedor = times[0].time;
				return;
			}
		}
		
		/* Cobranças alternadas*/
		int i=5;
		while(true){
			gols[0] += chute(getJogador(times[0], i), times[1].goleiro);
			gols[1] += chute(getJogador(times[1], i), times[0].goleiro);
			
			if(gols[0] > gols[1]){
				vencedor = times[0].time;
				return;
			}
			
			if(gols[1] > gols[0]){
				vencedor = times[1].time;
				return;
			}
			
			i++;
			if(i > 11){
				i = 0;
			}
		}
	}
	
	private Jogador getJogador(Escalacao e, int i){
		if(i < e.atacantes.size())
			return e.atacantes.get(i);
		
		if(i < e.atacantes.size() + e.meias.size())
			return e.meias.get(i - e.atacantes.size());
		
		if(i < e.atacantes.size() + e.meias.size() + e.defensores.size())
			return e.defensores.get(i - e.atacantes.size() - e.meias.size());
		
		return e.goleiro;
	}
	
	private int chute(Jogador chutador, Jogador goleiro){
		int numJ = ConteudoEstatico.random.nextInt(chutador.cfh);
		int numG = ConteudoEstatico.random.nextInt((int)(goleiro.cfh*0.5f));
		
		return numJ > numG? 1:0;
	}

}
