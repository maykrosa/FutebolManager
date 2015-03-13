package jogo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;

import estruturaDados.Jogador;
import estruturaDados.Time;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class ConteudoEstatico {

	public static Random random = new Random();

	public static Time carregarTime(String caminho) {
		try {
			InputStream is = ConteudoEstatico.class
					.getResourceAsStream(caminho);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String timeNome = br.readLine().split(";")[0];
			Hashtable<String, Jogador> jogadores = new Hashtable<String, Jogador>();

			String linha;
			String[] subLinha;
			while ((linha = br.readLine()) != null) {
				subLinha = linha.split(";");

				jogadores.put(
						subLinha[0],
						new Jogador(subLinha[0], Integer.parseInt(subLinha[1]),
								Integer.parseInt(subLinha[2]), Integer
										.parseInt(subLinha[3])));
			}
			return new Time(timeNome, "Brasil", 25, jogadores);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
