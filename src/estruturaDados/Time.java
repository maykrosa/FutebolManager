package estruturaDados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Set;

import jogo.ConteudoEstatico;
import estruturaDados.Jogador.CondicaoFisica;
import estruturaDados.Jogador.Posicao;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class Time {

	/**
	 * enum Formação, constantes que determinam em que formação o time vai atuar
	 */
	public enum Formacao {
		f343(3, 4, 3), f433(4, 3, 3), f442(4, 4, 2), f523(5, 2, 3), f532(5, 3,
				2), f541(5, 4, 1), f640(6, 4, 0), f352(3, 5, 2);

		private int def, mc, at;

		private Formacao(int def, int mc, int at) {
			this.def = def;
			this.mc = mc;
			this.at = at;
		}

		public int getDef() {
			return this.def;
		}

		public int getMC() {
			return this.mc;
		}

		public int getAt() {
			return this.at;
		}
	}

	/** Hash com todos os jogadores do plantel */
	public Hashtable<String, Jogador> plantel;
	public ArrayList<Jogador> goleiros;
	private ArrayList<Jogador> defensores;
	private ArrayList<Jogador> meias;
	private ArrayList<Jogador> atacantes;

	public String nome;
	public String confederacao;
	public int nivel;
	public int pontos;
	public Tecnico tecnico;

	public Calendario calendario;

	/**
	 * Construtor da classe Inicializa e ordena os arrayLists, põe valores
	 * iniciais em fadiga, moral e entrosamento.
	 * 
	 * @param String
	 *            nome
	 * @param Hashtable
	 *            <String, Jogador> plantel
	 */
	public Time( String name, String confederacao, int nivel, Hashtable<String, Jogador> plantel, String tecnicoString) {
		this.nome = name;
		this.confederacao = confederacao;
		this.nivel = nivel;		

		this.calendario = new Calendario();
		this.plantel = plantel;
		this.tecnico = new Tecnico(tecnicoString, this);
		ConteudoEstatico.tecnicos.put(tecnicoString, tecnico);

		goleiros = new ArrayList<Jogador>();
		defensores = new ArrayList<Jogador>();
		meias = new ArrayList<Jogador>();
		atacantes = new ArrayList<Jogador>();

		Jogador p = null;
		Set<String> keys = plantel.keySet();
		for (String s : keys) {
			p = plantel.get(s);
			p.fadiga = 100;
			p.moral = ConteudoEstatico.random.nextInt(4) + 2;
			p.entrosamento = ConteudoEstatico.random.nextInt(30) + 20;

			if (p.posicao == Posicao.Goleiro.getId()) {
				goleiros.add(p);
			} else if (p.posicao == Posicao.Zagueiro.getId()
					|| p.posicao == Posicao.Lateal.getId()) {
				defensores.add(p);
			} else if (p.posicao == Posicao.Volante.getId()
					|| p.posicao == Posicao.MeiaCampo.getId()) {
				meias.add(p);
			} else if (p.posicao == Posicao.SegundoAtacante.getId()
					|| p.posicao == Posicao.CentroAtacante.getId()) {
				atacantes.add(p);
			}
		}

		try {
			ordenarLista(goleiros);
			ordenarLista(defensores);
			ordenarLista(meias);
			ordenarLista(atacantes);
		} catch (Exception e) {
			System.err
					.println("Time Construtor - line 84 - Erro ao ordenar listas");
		}

	}

	/**
	 * Converte classe para uma string que a identifique, no caso o nome do time
	 * 
	 * @return nome
	 */
	@Override
	public String toString() {
		return nome;
	}

	/**
	 * Ordena uma lista de jogadores por fh(Fator de Habilidade)
	 * 
	 * @param ArrayList
	 *            <Jogador>
	 */
	public void ordenarLista(ArrayList<Jogador> j) throws Exception {
		Collections.sort(j, new Comparator<Jogador>() {
			@Override
			public int compare(Jogador s1, Jogador s2) {
				if (s1.cfh < s2.cfh) {
					return -1;
				} else {
					return 1;
				}
			}
		});
	}

	/**
	 * Calcula o cfh de todos os jogadores do time
	 */
	public void calculaCFHJogadores() {
		for (String s : plantel.keySet()) {
			plantel.get(s).calcularCFH();
		}
	}

	/**
	 * Converte time para escalação usando os melhores jogadores em cada setor,
	 * caso o jogador esteja lecionado ele não pode jogar
	 * 
	 * @param Formacao
	 *            f
	 * @int numero de suplentes
	 * 
	 * @return Escalação
	 */
	public Escalacao toEscalacao(Formacao f, int numSuplentes) {
		calculaCFHJogadores();

		Escalacao e = new Escalacao(this);

		for (Jogador j : goleiros) {
			if (j.moral != CondicaoFisica.Lesionado.getId()
					&& j.moral != CondicaoFisica.LesionadoGravemente.getId())
				e.goleiro = j;

			if (e.goleiro != null)
				break;
		}

		for (Jogador j : defensores) {
			if (j.moral != CondicaoFisica.Lesionado.getId()
					&& j.moral != CondicaoFisica.LesionadoGravemente.getId())
				e.defensores.add(j);

			if (e.defensores.size() >= f.def)
				break;
		}

		for (Jogador j : meias) {
			if (j.moral != CondicaoFisica.Lesionado.getId()
					&& j.moral != CondicaoFisica.LesionadoGravemente.getId()) {
				if (j.posicao == Posicao.Volante.getId())
					e.volantes.add(j);
				else
					e.meias.add(j);
			}

			if (e.volantes.size() + e.meias.size() >= f.mc)
				break;
		}

		for (Jogador j : atacantes) {
			if (j.moral != CondicaoFisica.Lesionado.getId()
					&& j.moral != CondicaoFisica.LesionadoGravemente.getId())
				e.atacantes.add(j);

			if (e.atacantes.size() >= f.at)
				break;
		}

		for (String s : plantel.keySet()) {
			Jogador j = plantel.get(s);

			if (e.goleiro != j && !e.defensores.contains(j)
					&& !e.volantes.contains(j) && !e.meias.contains(j)
					&& !e.atacantes.contains(j))
				e.suplentes.add(j);

			if (e.suplentes.size() >= numSuplentes)
				break;
		}

		return e;
	}
}
