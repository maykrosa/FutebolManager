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
	public int agilidade;
	public int forca;
	public int resistencia;
	/** Fator calculado correspondente as habilidades nas atuais condições do jogador*/
	public int cfh;

	/** Valor correspondente pelo desgaste */
	public int fadiga;
	/** Valor correspondente pelo entusiasmo */
	public int moral;
	
	/** Valor correspondente as semanas restantes para recuperação da lesão */
	public int recuperacaoLesao;
	
	/**
	 * Valor correspondente pelo entrosamento, afinidade com os demais jogadores
	 * da equipe
	 */
	public int entrosamento;
	
	public int gols;
	public int cartoesAmarelos;
	public int cartosVermelhos;
	public int jogos;

	/**
	 * Construtor da classe
	 * 
	 * @param String nome
	 * @param int idade
	 * @param int posição
	 * @param int fh
	 */
	public Jogador(String nome, int idade, int posicao, int agilidade, int forca, int resistencia) {
		this.nome = nome;
		this.idade = idade;
		this.posicao = posicao;
		this.agilidade = agilidade;
		this.forca = forca;
		this.resistencia = resistencia;
	}
	
	/**
	 * Calcula o fator de habilidade que sera utilizado em jogos
	 * levando em consideração entrosamento, fadiga e moral do jogador.
	 */
	public void calcularCFH(){
		int fh = 0;
		switch (posicao) {
		case 0:
			fh = (agilidade+forca+resistencia)/3;
			break;
		case 1:
			fh = (agilidade+forca*3+resistencia*2)/6;
			break;
		case 2:
			fh = (agilidade*2+forca+resistencia*3)/6;
			break;
		case 3:
			fh = (agilidade+forca*2+resistencia*2)/5;
			break;
		case 4:
			fh = (agilidade*2+forca*2+resistencia)/5;
			break;
		case 5:
			fh = (agilidade*3+forca+resistencia)/5;
			break;
		case 6:
			fh = (agilidade*3+forca*2+resistencia)/6;
			break;
		}
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
	
//	TODO
//	/**
//	 * Calcula fadiga do jogador, levando a intensidade da atividade realizada
//	 * 0 ficou em casa, 1 banco, 2 baixa, 3 moderada, 4 alta, 5 exaustiva
//	 * 
//	 * @return nome
//	 */
//	public void calcFadiga(int intensidade){
//		/* Não jogou  */
//		if(intensidade <= 1){
//			fadiga += (intensidade+1)*10;
//			if(fadiga > 100)
//				fadiga = 100;
//			
//			return;
//		}
//		
//		/* Este infeliz jogou */
//		fadiga -= intensidade*6;
//	}

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
