package jogo;

import estruturaDados.ConfrontoIdaVolta;
import estruturaDados.DisputaPenalti;
import estruturaDados.Escalacao;
import estruturaDados.Jogador;
import estruturaDados.Jogador.Posicao;
import estruturaDados.competicao.GenericoCompeticao;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class SimuladorDePartida {
	
	/**
	 * enum SetorCampo, constantes que determinam os setor do campo
	 */
	public enum SetorCampo {
		Zaga(0), MeioDef(1), MeioOff(2), Ataque(3);

		private final int id;

		private SetorCampo(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}

	public Escalacao[] times;
	/** Minutos da partida */
	public int time;
	
	/** Posse de bola do mandante */
	public int posseMandante = 0;
	/** Posse de bola do visitante */
	public int posseVisitante = 0;
	
	/** Posse de bola. 0 = mandante, 1 = visitante*/
	private int posseDaBola;
	/** Setor atual */
	private int setor;
	
	/** Competição da qual esta partida pertence */
	private GenericoCompeticao competicao;

	/** Informação passada para a partida*/
	private Object object;
	
	/**
	 * Construtor da classe Inicializa os arrays
	 * 
	 * @param Escalacao mandante
	 * @param Escalacao visitante
	 */
	public SimuladorDePartida(Escalacao t1, Escalacao t2, GenericoCompeticao competicao, Object object) {
		times = new Escalacao[2];
		
		times[0] = t1;
		times[1] = t2;

		this.competicao = competicao;
		this.object = object;
		posseDaBola = 0;
		setor = SetorCampo.MeioDef.getId();
	}
	
	/**
	 * Jogar
	 * 
	 * @return boolean termino da partida
	 */
	public void jogar() {
		while(time < 90){
			time++;

			disputaDeBola();
//			System.out.println("Time: "+time+" Posse: "+posseDaBola+" Setor: "+setor+" Time1: "+times[0].gols+" x Time2: "+times[1].gols);
		}
		System.out.println(times[0].time.nome+" "+times[0].gols+ "  X  "+times[1].gols+" "+times[1].time.nome);
		fimDeJogoEvento();
	}
	
	/**
	 * Disputa de Bola
	 */
	private void disputaDeBola() {
		Jogador pExecute = null;
		
		float totalSetor = 0;
		float totalSetorAdver = 0;
		
		int numSetor = 0;

		switch (setor) {
		case 0:
			pExecute = times[posseDaBola].getJogador(times[posseDaBola].defensores);
			totalSetor = times[posseDaBola].getTotalFH(times[posseDaBola].defensores);
			numSetor = times[posseDaBola].defensores.size();

			totalSetorAdver = times[posseDaBola==0?1:0].getTotalFH(times[posseDaBola==0?1:0].atacantes);
		case 1:
			pExecute = times[posseDaBola].getJogador(times[posseDaBola].volantes);
			totalSetor = times[posseDaBola].getTotalFH(times[posseDaBola].volantes);
			numSetor = times[posseDaBola].volantes.size();

			totalSetorAdver = times[posseDaBola==0?1:0].getTotalFH(times[posseDaBola==0?1:0].meias);
			break;
		case 2:
			pExecute = times[posseDaBola].getJogador(times[posseDaBola].meias);
			totalSetor = times[posseDaBola].getTotalFH(times[posseDaBola].meias);
			numSetor = times[posseDaBola].meias.size();
			
			totalSetorAdver = times[posseDaBola==0?1:0].getTotalFH(times[posseDaBola==0?1:0].volantes);
			break;
		case 3:
			pExecute = times[posseDaBola].getJogador(times[posseDaBola].atacantes);
			totalSetor = times[posseDaBola].getTotalFH(times[posseDaBola].atacantes);
			numSetor = times[posseDaBola].atacantes.size();
			
			totalSetorAdver = times[posseDaBola==0?1:0].getTotalFH(times[posseDaBola==0?1:0].defensores);
			break;
		}
		
		/* Não a nenhum jogador neste setor, a posse da bola é perdida*/
		if(pExecute == null){
			posseDaBola = posseDaBola==0?1:0;
			setor = Math.abs(setor-3);
			
			return;
		}
		
		totalSetor -= pExecute.cfh;
		/* Jogador + Bonus de importância do jogador pro setor */
		int bonusJogValor = (int) (pExecute.cfh*((pExecute.cfh/totalSetor)*numSetor));
		totalSetor += bonusJogValor;
		
		/* Caso o time que estiver com a posse da bola vença ele pode tentar executar uma ação
		 * caso perca a posse de bola é dada ao oponente, e uma chance de uma falta tenha 
		 * ocorrido é randomizada*/
		int rndNum = ConteudoEstatico.random.nextInt((int) (totalSetor+totalSetorAdver));
		if(rndNum*1.5f <= totalSetor){
			executaAcao(pExecute);
		}else{
			/* Penalti */
			if (setor == SetorCampo.Ataque.getId()) {
				int rndPenalti = ConteudoEstatico.random.nextInt(100);
				if(rndPenalti < 5){
					penaltiEvento(pExecute);
					return;
				}
			}
			int rndFalta = ConteudoEstatico.random.nextInt(100);
			/* Jogadores do sistema defensivo tem 10 porcento de receber um cartão */
			if(pExecute.posicao == Posicao.Zagueiro.getId() || pExecute.posicao == Posicao.Lateal.getId() 
					|| pExecute.posicao == Posicao.Volante.getId()){
				if(rndFalta < 10)
					times[posseDaBola].amarelo(pExecute);
				else if(rndFalta == 99)
					times[posseDaBola].vermelho(pExecute);
			/* Jogadores do sistema ofensivo tem 5 porcento de receber um cartão */
			}else if(pExecute.posicao == Posicao.MeiaCampo.getId() || pExecute.posicao == Posicao.MeiaCampo.getId() ||
					pExecute.posicao == Posicao.MeiaCampo.getId()){
				if(rndFalta < 5)
					times[posseDaBola].amarelo(pExecute);
				else if(rndFalta == 99)
					times[posseDaBola].vermelho(pExecute);
			}
				
			posseDaBola = posseDaBola==0?1:0;
			setor = Math.abs(setor-3);
		}
		
		/** Contabilizando a posse de bola */
		if(posseDaBola==0)
			posseMandante++;
		else
			posseVisitante++;
	}

	/**
	 * Executa a ação
	 * 
	 * @param Jogador j
	 */
	private void executaAcao(Jogador j){
		int acao = ConteudoEstatico.random.nextInt(100);
		int numJ = ConteudoEstatico.random.nextInt(j.cfh);
		int numG = ConteudoEstatico.random.nextInt((int) (times[posseDaBola==0?1:0].goleiro.cfh*4f));
		
		/* Zaga, passar para o meio, chutão para o ataque, chute ao gol */
		if(setor == SetorCampo.Zaga.getId()){
			/* Passe simple para o meio defensivo */
			if(acao < 50){
				setor = SetorCampo.MeioDef.getId();
			/* Chute para o outro lado do campo, bola disputada entre os times */
			}else if(acao < 70){
				setor = SetorCampo.MeioOff.getId();
				posseDaBola = ConteudoEstatico.random.nextInt(2);
			/* Chute para o ataque, bola disputada entre os times */
			}else if(acao < 90){
				setor = SetorCampo.Ataque.getId();
				posseDaBola = ConteudoEstatico.random.nextInt(2);
			/* 
			 * Chute a gol, gol de algum dos defensores.
			 * devido aos apenas 10 porcento de chance, o fh do goleiro é apenas metade 
			 */
			}else{
				if(numJ > (numG/2)){
					times[posseDaBola].gol(j);
					setor = SetorCampo.MeioDef.getId();
					posseDaBola = posseDaBola==0?1:0;
				}else{
					setor = SetorCampo.Zaga.getId();
					posseDaBola = posseDaBola==0?1:0;
				}
			}
		/* MeioDef, passa para o meioOff, passe longo pro ataque, chute a gol */
		}else if(setor == SetorCampo.MeioDef.getId()){
			if(acao < 50){
				setor = SetorCampo.MeioOff.getId();
			/* Chute para o outro lado do campo, bola disputada entre os times */
			}else if(acao < 80){
				setor = SetorCampo.Ataque.getId();
				posseDaBola = ConteudoEstatico.random.nextInt(2);
			/* 
			 * Chute a gol, gol de algum dos defensores.
			 * devido aos apenas 10 porcento de chance, o fh do goleiro é apenas metade 
			 */
			}else{
				if(numJ > (numG/2)){
					times[posseDaBola].gol(j);
					setor = SetorCampo.MeioDef.getId();
					posseDaBola = posseDaBola==0?1:0;
				}else{
					setor = SetorCampo.Zaga.getId();
					posseDaBola = posseDaBola==0?1:0;
				}
			}
		/* Meio, passe para o ataque, chute ao gol */
		}else if(setor == SetorCampo.MeioOff.getId()){
			if(acao < 60){
				setor = SetorCampo.Ataque.getId();
			}else{ 
				if(numJ > numG){
					times[posseDaBola].gol(j);
					setor = SetorCampo.MeioDef.getId();
					posseDaBola = posseDaBola==0?1:0;
				}else{
					setor = SetorCampo.Zaga.getId();
					posseDaBola = posseDaBola==0?1:0;
				}
			}
		/* Chute ao gol */
		}else if(setor == SetorCampo.Ataque.getId()){	
			if(numJ > numG){
				times[posseDaBola].gol(j);
				setor = SetorCampo.MeioDef.getId();
				posseDaBola = posseDaBola==0?1:0;
			}else{
				setor = SetorCampo.Zaga.getId();
				posseDaBola = posseDaBola==0?1:0;
			}
		}
	}
	
	/**
	 * Evento que é executado quando um penalti acontece na partida
	 * 
	 * @param Jogador j
	 */
	private void penaltiEvento(Jogador j){
		int numJ = ConteudoEstatico.random.nextInt(j.cfh);
		int numG = ConteudoEstatico.random.nextInt((int) (times[posseDaBola==0?1:0].goleiro.cfh*0.5f));
		
		if(numJ > numG){
			times[posseDaBola].gol(j);
			setor = SetorCampo.MeioDef.getId();
			posseDaBola = posseDaBola==0?1:0;
		}else{
			setor = SetorCampo.Zaga.getId();
			posseDaBola = posseDaBola==0?1:0;
		}
	}
	
	private void fimDeJogoEvento(){
		switch (competicao.getID()) {
		case 0:
			times[0].time.dadosCompeticoes.get(0).golsPro += times[0].gols;
			times[0].time.dadosCompeticoes.get(0).golsContra += times[1].gols;
			times[0].time.dadosCompeticoes.get(0).cartoesAmarelo += times[0].cartoesAmarelo;
			times[0].time.dadosCompeticoes.get(0).cartoesVermelho+= times[0].cartoesVermelho;
			
			times[1].time.dadosCompeticoes.get(0).golsPro += times[1].gols;
			times[1].time.dadosCompeticoes.get(0).golsContra += times[0].gols;
			times[1].time.dadosCompeticoes.get(0).cartoesAmarelo += times[1].cartoesAmarelo;
			times[1].time.dadosCompeticoes.get(0).cartoesVermelho+= times[1].cartoesVermelho;
			
			ConfrontoIdaVolta confronto = ((ConfrontoIdaVolta)object);
			if(confronto.getPartida1() == null){
				break;
			}else{
				if(confronto.precisaPenalti(this)){
					/* Penalti */
					DisputaPenalti penaltis = new DisputaPenalti(this);
					penaltis.jogar();
					confronto.vencedor = penaltis.vencedor;
					System.out.println("Vencedor "+confronto.vencedor);
				}
				
				int golsT1;
				int golsT2;
				SimuladorDePartida partida1 = confronto.getPartida1();
				SimuladorDePartida partida2 = this;
				
				golsT1 = partida1.times[0].gols + partida2.times[1].gols;
				golsT2 = partida1.times[1].gols + partida2.times[0].gols;
				
				/* Um dos times ganhou com maior numero de gols */
				if(golsT1 > golsT2){
					confronto.vencedor = confronto.t1;
				}else if(golsT2 > golsT1){
					confronto.vencedor = confronto.t2;
				}
				
				golsT1 = partida1.times[0].gols + partida2.times[1].gols*2;
				golsT2 = partida1.times[1].gols*2 + partida2.times[0].gols;
				
				/* Um dos times ganhou por gols fora de casa */
				if(golsT1 > golsT2){
					confronto.vencedor = confronto.t1;
				}else if(golsT2 > golsT1){
					confronto.vencedor = confronto.t2;
				}
			}
			break;
		case 1:
			times[0].time.dadosCompeticoes.get(1).golsPro += times[0].gols;
			times[0].time.dadosCompeticoes.get(1).golsContra += times[1].gols;
			times[0].time.dadosCompeticoes.get(1).cartoesAmarelo += times[0].cartoesAmarelo;
			times[0].time.dadosCompeticoes.get(1).cartoesVermelho+= times[0].cartoesVermelho;
			
			times[1].time.dadosCompeticoes.get(1).golsPro += times[1].gols;
			times[1].time.dadosCompeticoes.get(1).golsContra += times[0].gols;
			times[1].time.dadosCompeticoes.get(1).cartoesAmarelo += times[1].cartoesAmarelo;
			times[1].time.dadosCompeticoes.get(1).cartoesVermelho+= times[1].cartoesVermelho;
			
			/* Empate */
			if (times[0].gols == times[1].gols) {
				times[0].time.dadosCompeticoes.get(1).pontos += 1;
				times[0].time.dadosCompeticoes.get(1).empates++;
				
				times[1].time.dadosCompeticoes.get(1).pontos += 1;
				times[1].time.dadosCompeticoes.get(1).empates++;
			/* Vitoria time 0 */
			} else if (times[0].gols > times[1].gols) {
				times[0].time.dadosCompeticoes.get(1).pontos +=3;
				times[0].time.dadosCompeticoes.get(1).vitorias++;
				
				times[0].time.dadosCompeticoes.get(1).derrotas++;
			/* Vitoria time 1 */
			} else {
				times[0].time.dadosCompeticoes.get(1).derrotas++;
				
				times[1].time.dadosCompeticoes.get(1).pontos +=3;
				times[1].time.dadosCompeticoes.get(1).vitorias++;
			}
			break;
		}
	}
}
