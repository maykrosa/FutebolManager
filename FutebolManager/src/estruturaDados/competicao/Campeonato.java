package estruturaDados.competicao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import estruturaDados.Confederacao;
import estruturaDados.Jogador;
import estruturaDados.Time;

/**
 * @author Mayk Rosa
 * @version 1.0
 * */
public class Campeonato extends GenericoCompeticao {

	public Confederacao confederacao;
	public ArrayList<Time> times;
	public ArrayList<Integer> pontuacao;

	public int turnoAtual;
	public boolean campeonatoFinish = false;

	/**
	 * Classe responsável pelo campeonato nacional de acordo com a confederacao
	 * escolhida, respeitando suas respectivas series tais como seria A, serie B
	 * 
	 * @param Confederacao
	 *            confederacao
	 * 
	 * */
	public Campeonato(Confederacao confederacao) {
		this.confederacao = confederacao;
		NUMERO_DE_PARTICIPANTES = 20;
		pontuacao = new ArrayList<Integer>();
		int[] jog = { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30,
				32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62,
				64, 66, 68, 70, 72, 74, 76 };
		DIAS_DE_JOGOS = jog;
	}

	@Override
	public void comecarTemporada() {
//		ArrayList<ArrayList<Confronto>> tabela = new ArrayList<ArrayList<Confronto>>();
//		ArrayList<Confronto> rodada = new ArrayList<Campeonato.Confronto>();
		ArrayList<Integer> clubes = new ArrayList<Integer>();
		times = confederacao.times;
		for (int i = 0; i < NUMERO_DE_PARTICIPANTES; i++) {
			clubes.add(i);
		}

		Collections.sort(clubes);

		int t = NUMERO_DE_PARTICIPANTES;
		int m = NUMERO_DE_PARTICIPANTES / 2;

		Time t1;
		Time t2;
		int djm = 0;
		for (int turno = 0; turno < 2; turno++) {
			for (int pPorTurno = 0; pPorTurno < t - 1; pPorTurno++) {
				for (int tParticip = 0; tParticip < m; tParticip++) {
					// Teste para ajustar o mando de campo
					if (tParticip % 2 == 1 || pPorTurno % 2 == 1 && tParticip == 0) {
						t1 = times.get(clubes.get(t - tParticip - 1));
						t2 = times.get(clubes.get(tParticip));
						
						t1.calendario.setJogo(DIAS_DE_JOGOS[djm], t1, t2, this, null);
						t2.calendario.setJogo(DIAS_DE_JOGOS[djm], t1, t2, this, null);
//						rodada.add(new Confronto(times.get(clubes.get(t - tParticip - 1)), times.get(clubes.get(tParticip))));
					} else {
						t1 = times.get(clubes.get(tParticip));
						t2 = times.get(clubes.get(t - tParticip - 1));
						
						t1.calendario.setJogo(DIAS_DE_JOGOS[djm], t1, t2, this, null);
						t2.calendario.setJogo(DIAS_DE_JOGOS[djm], t1, t2, this, null);
					}
				}
				djm++;
				
				// remove o ultimo e traz ele para o indece1
				clubes.add(1, clubes.remove(clubes.size() - 1));
			}
			Collections.sort(clubes, Collections.reverseOrder());
		}
		artilheiros = new ArrayList<Jogador>();
		cartoesAmarelos = new ArrayList<Jogador>();
		cartoesVermelhos = new ArrayList<Jogador>();
	}

	public void atualizar(int semana) {}

	public void resultados(boolean finish) {
		try {
			ordenarLista(confederacao.times);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < NUMERO_DE_PARTICIPANTES; i++) {
			System.out.println(confederacao.times.get(i).nome + " = "
					+ confederacao.times.get(i).pontos);

		}
		System.out.println("Campeao Brasileiro - " + confederacao.times.get(0));
	}
	
	/**
	 * Ordena a lista com a pontuacao dos times
	 * 
	 * @param ArrayList
	 *            <Time>
	 */
	public void ordenarLista(ArrayList<Time> j) throws Exception {
		Collections.sort(j, new Comparator<Time>() {
			@Override
			public int compare(Time s1, Time s2) {
				if (s1.pontos > s2.pontos) {
					return -1;
				} else {
					return 1;
				}
			}
		});
	}
	
	@Override
	public int getID() {
		return 1;
	}
}
