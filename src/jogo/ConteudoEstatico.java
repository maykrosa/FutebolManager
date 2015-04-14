package jogo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;

import estruturaDados.Jogador;
import estruturaDados.Tecnico;
import estruturaDados.Time;

/**
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class ConteudoEstatico {

	public static Hashtable<String, Tecnico> tecnicos = new Hashtable<String, Tecnico>();
	public static Random random = new Random();

	public static Time carregarTime(String caminho) {
		try {
			InputStream is = ConteudoEstatico.class.getResourceAsStream(caminho);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String linha;
			String[] subLinha;
			
			subLinha = br.readLine().split(";");
			String timeNome = subLinha[0];
			int nivel = Integer.parseInt(subLinha[1]);
			String confederacao = subLinha[2];
			String tecnicoNome = subLinha[3];
			
			Hashtable<String, Jogador> jogadores = new Hashtable<String, Jogador>();
			while ((linha = br.readLine()) != null) {
				if(linha.equals(""))
					continue;
				
				subLinha = linha.split(";");
				jogadores.put(subLinha[0],new Jogador(subLinha[0], Integer.parseInt(subLinha[1]),
								Integer.parseInt(subLinha[2]), Integer.parseInt(subLinha[3]), Integer.parseInt(subLinha[4]), Integer.parseInt(subLinha[5])));
			}
			return new Time(timeNome, confederacao, nivel, jogadores, tecnicoNome);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
