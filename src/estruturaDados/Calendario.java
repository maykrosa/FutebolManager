package estruturaDados;

import jogo.SimuladorDePartida;
import estruturaDados.Escalacao.EstiloDeJogo;
import estruturaDados.Escalacao.Marcacao;
import estruturaDados.Time.Formacao;
import estruturaDados.competicao.GenericoCompeticao;

public class Calendario {

	class DiaDeJogo {
		Time mandante;
		Time visitante;

		boolean concluido;

		GenericoCompeticao competicao;
		Object object;

		DiaDeJogo(Time mandante, Time visitante, GenericoCompeticao competicao, Object object) {
			this.mandante = mandante;
			this.visitante = visitante;

			this.competicao = competicao;
			this.object = object;
			concluido = false;
		}
	}

	public DiaDeJogo[] semanas;
	public SimuladorDePartida[] partidas;

	public Calendario() {
		semanas = new DiaDeJogo[100];
		partidas = new SimuladorDePartida[100];
	}

	public void setJogo(int semana, Time mandante, Time visitante, GenericoCompeticao competicao, Object object) {
		semanas[semana] = new DiaDeJogo(mandante, visitante, competicao, object);
	}

	public boolean getConcluido(int semana) {
		return semanas[semana].concluido;
	}

	public void realizarJogo(int semana) {
		DiaDeJogo dj = semanas[semana];
		SimuladorDePartida p = new SimuladorDePartida(dj.mandante.toEscalacao(
				Formacao.f532, 7, EstiloDeJogo.ataqueTotal, Marcacao.leve), 
				dj.visitante.toEscalacao(Formacao.f433, 7, EstiloDeJogo.equilibrado, Marcacao.Pesada),
				dj.competicao, dj.object);

		p.jogar();

		dj.mandante.calendario.partidas[semana] = p;
		dj.visitante.calendario.partidas[semana] = p;

		dj.mandante.calendario.semanas[semana].concluido = true;
		dj.visitante.calendario.semanas[semana].concluido = true;
	}
}
