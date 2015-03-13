package estruturaDados.competicao;

import java.util.ArrayList;

import jogo.ConteudoEstatico;
import estruturaDados.Confederacao;
import estruturaDados.ConfrontoIdaVolta;
import estruturaDados.Jogador;
import estruturaDados.Time;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class Copa extends GenericoCompeticao{
	
	/**
	 * enum Turno, constantes que determinam a que turno a partida pertence
	 */
	public enum Turno {
		primeiraRodada(0), oitavas(1), quartas(2), semi(3), finall(4);

		private int id;

		private Turno(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
	
	/** Rodadas */
	public ArrayList<ArrayList<ConfrontoIdaVolta>> jogos;
	
	/** Referencia da confederação a qual esta copa pertence */
	private Confederacao confederacao;
	
	/** Rodada atual em relação da semana atual */
	private int rodadaAtual;
	
	/** Dias em que a partida de volta dos confrontos acontecem sendo 
	 * necessário marcar os confrontos da rodada seguinte*/
	private ArrayList<Integer> diasChaves = new ArrayList<Integer>();
	
	/**
	 * Construtor da classe define o numero de participates e as datas
	 * dos confrontos.
	 * 
	 * @param Confederacao confederacao
	 */
	public Copa(Confederacao confederacao){
		this.confederacao = confederacao;		
		NUMERO_DE_PARTICIPANTES = 32;
		int[]tempDEJ = {27, 29, 37, 39, 51, 53, 63, 65, 77, 79};
		DIAS_DE_JOGOS = tempDEJ;
		
		/** Dias chaves são apenas os dias onde as partidas de volta acontecem */
		for(int i=0; i<DIAS_DE_JOGOS.length; i++){
			if(i%2 != 0)
				diasChaves.add(DIAS_DE_JOGOS[i]);
		}
	}
	
	/**
	 * Todo inicio de temporada os dados são limpos para que uma nova possa se iniciar.
	 */
	@Override
	public void comecarTemporada(){
		artilheiros = new ArrayList<Jogador>();
		cartoesAmarelos = new ArrayList<Jogador>();
		cartoesVermelhos = new ArrayList<Jogador>();

		jogos = new ArrayList<ArrayList<ConfrontoIdaVolta>>();
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		
		ArrayList<Integer> jaEscolhidos = new ArrayList<Integer>();
		int t1;
		int t2;
		
		for(int i=0; i<NUMERO_DE_PARTICIPANTES/2; i++){
			t1 = ConteudoEstatico.random.nextInt(16);
			t2 = ConteudoEstatico.random.nextInt(16)+16;
			while(jaEscolhidos.contains(t1) || jaEscolhidos.contains(t2)){
				t1 = ConteudoEstatico.random.nextInt(16);
				t2 = ConteudoEstatico.random.nextInt(16)+16;
			}
			
			jaEscolhidos.add(t1);
			jaEscolhidos.add(t2);
			
			jogos.get(Turno.primeiraRodada.getId()).add(new ConfrontoIdaVolta(confederacao.times.get(t1), confederacao.times.get(t1), Turno.primeiraRodada.getId(), this));
		}
		
		rodadaAtual = Turno.primeiraRodada.getId();
		System.out.println(jogos.get(0).size()+" jogos da primeira fase");
	}
	
	/**
	 * Atualizar a situação da competição, no caso da copa, quando confronto de ida e volta terminam
	 * é necessário marcar a rodada seguinte até que reste apenas o campeão.
	 * 
	 * @param int semana
	 */
	public void atualizar(int semana){
		ArrayList<Time> proximafase = new ArrayList<Time>();
		int i = diasChaves.indexOf(semana);
		if(i == -1)
			return;
		
		for(int j=0; j<jogos.get(i).size(); j++){
			ConfrontoIdaVolta confIV = jogos.get(i).get(j);

			if(confIV.turno < Turno.finall.getId()){
				proximafase.add(confIV.getVencedor());

				confIV.encerrado = true;
			}
		}

		/* Confrontos da fase seguinte são marcados */
		for(int j=0; j<proximafase.size()/2; j++){
			jogos.get(rodadaAtual+1).add(new ConfrontoIdaVolta(proximafase.get(j*2), proximafase.get((j*2)+1), rodadaAtual+1, this));
		}
				
		rodadaAtual++;
		System.out.println("avançaram: "+proximafase.size());
	}

	@Override
	public int getID() {
		return 0;
	}
}
