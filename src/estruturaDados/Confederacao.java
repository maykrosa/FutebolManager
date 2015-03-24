package estruturaDados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import estruturaDados.competicao.Campeonato;
import estruturaDados.competicao.Copa;

public class Confederacao {

	public ArrayList<Time> times;
	
	private LinkedList<Campeonato> campeonatos;
	private Copa copa;
	
	public Confederacao(ArrayList<Time> times){
		this.times = times;

		campeonatos = new LinkedList<Campeonato>();
		for(int i=0; i<times.size() / 20; i++)
			campeonatos.add(new Campeonato(this, i));
		
		copa = new Copa(this);
	}
	
	public void comecarTemporada(){
		for(int i=0; i<campeonatos.size(); i++)
			campeonatos.get(i).comecarTemporada();
		
		copa.comecarTemporada();
	}
	
	public void atualizar(int semana){
		for(int i=0; i<campeonatos.size(); i++)
			campeonatos.get(i).atualizar(semana);
		
		copa.atualizar(semana);
	}
	
	public void fimTemporada(){
		for(int i=0; i<campeonatos.size(); i++)
			campeonatos.get(i).fimTemporada();
		
		copa.fimTemporada();
		
		/* Promoção para a divisão de cima, ou rebaixamento para a de baixo */
		try {
			ordenarLista(times);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<campeonatos.size(); i++){
			/* Promoção */
			if(i > 0){
				times.get((20*i)).nivel -= 4;
				times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+1)).nivel -= 4;
				times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+2)).nivel -= 4;
				times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+3)).nivel -= 4;
			}
			
			times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+campeonatos.get(i).NUMERO_DE_PARTICIPANTES-4)).nivel += 4;
			times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+campeonatos.get(i).NUMERO_DE_PARTICIPANTES-3)).nivel += 4;
			times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+campeonatos.get(i).NUMERO_DE_PARTICIPANTES-2)).nivel += 4;
			times.get((campeonatos.get(i).NUMERO_DE_PARTICIPANTES*i+campeonatos.get(i).NUMERO_DE_PARTICIPANTES-1)).nivel += 4;
		}
		
		try {
			ordenarLista(times);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ordenarLista(ArrayList<Time> j) throws Exception {
		Collections.sort(j, new Comparator<Time>() {
			@Override
			public int compare(Time s1, Time s2) {
				if (s1.nivel > s2.nivel) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}
