package estruturaDados;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class Jogador {

	/**
	 * enum Posição, constantes que determinam em que posição o jogador atua no
	 * campo.
	 */
	public enum Posicao {
		Goleiro(0), Zagueiro(1), Lateal(2), Volante(3), MeiaCampo(4), SegundoAtacante(
				5), CentroAtacante(6);

		private int id;

		private Posicao(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}

	/**
	 * enum Condição, constantes que determinam em que condição o jogador pode
	 * apresentar.
	 */
	public enum CondicaoFisica {
		LesionadoGravemente(0), Lesionado(1), Terrivel(2), ruim(3), normal(4), boa(
				5), apice(6);

		private int id;

		private CondicaoFisica(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}

	public String nome;
	
	public int idade;
	public int posicao;
	/** Fator correspondente as habilidades */
	public int fh;
	/** Fator calculado correspondente as habilidades nas atuais condições do jogador*/
	public int cfh;

	/** Valor correspondente pelo desgaste */
	public int fadiga;
	/** Valor correspondente pelo entusiasmo */
	public int moral;
	/**
	 * Valor correspondente pelo entrosamento, afinidade com os demais jogadores
	 * da equipe
	 */
	public int entrosamento;
	
	public int gols;
	public int cartoesAmarelos;
	public int cartosVermelhos;

	/**
	 * Construtor da classe
	 * 
	 * @param String nome
	 * @param int idade
	 * @param int posição
	 * @param int fh
	 */
	public Jogador(String nome, int idade, int posicao, int fh) {
		this.nome = nome;
		this.idade = idade;
		this.posicao = posicao;
		this.fh = fh;
	}
	
	/**
	 * Calcula o fator de habilidade que sera utilizado em jogos
	 * levando em consideração entrosamento, fadiga e moral do jogador.
	 */
	public void calcularCFH(){
		int fadigaCalc = (fh*(fadiga+(100-fadiga)/2)/100);
		
		int moralCalc = fh;
		switch (moral) {
		case 2:
			moralCalc = (int) (fh*0.5f);
			break;
		case 3:
			moralCalc = (int) (fh*0.75f);
			break;
		case 5:
			moralCalc = (int) (fh*1.25f);
			break;
		case 6:
			moralCalc = (int) (fh*1.5f);
			break;
		}
		
		int entrosamentoCalc = (int) (fh*((entrosamento*2f)/100f));
		
		cfh = (fadigaCalc+moralCalc+entrosamentoCalc)/3;
	}

	/**
	 * Converte classe para uma string que a identifique, no caso o nome do jogador
	 * 
	 * @return nome
	 */
	@Override
	public String toString() {
		return nome;
	}
}
