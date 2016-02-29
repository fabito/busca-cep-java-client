package org.talesolutions.cep;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Invoca a busca de CEP disponível no site dos correios e extrai os resultados
 * do html retornado. Esta implementação utiliza Jsoup para fazer a requisição
 * GET ao serviço dos Correios e também fazer o extrair os dados do HTML
 * 
 * O serviço invocado por essa implementação roda na seguinte URL:
 * 
 * http://www.buscacep.correios.com.br/sistemas/buscacep/
 * resultadoBuscaCepEndereco.cfm
 * 
 * @author Fábio Franco Uechi
 */
class BuscaCEP implements CEPService {

	private static final String EMPTY = "";
	private static final String NBSP = "\u00a0";
	private static final int NUM_INDEX = 3;
	private static final int LOGRADOURO_INDEX = 0;
	private static final int BAIRRO_INDEX = 1;
	private static final int LOCALIDADE_INDEX = 2;
	private static final int UF_INDEX = 2;
	private static final String HIFEN = "-";
	private static final String SLASH = "/";

	private static final String BUSCA_CEP_SERVICE_BASE_URL = "http://www.buscacep.correios.com.br/sistemas/buscacep/resultadoBuscaCepEndereco.cfm";

	@Override
	public CEP obtemPorNumeroCEP(String numeroCEP) {
		Elements rows = busca(numeroCEP);
		for (Element row : rows) {
			CEP cep = cep(row);
			return cep;
		}
		throw new CEPNaoEncontradoException(numeroCEP, null);
	}

	private Elements busca(String q) {
		try {
			Document doc = Jsoup.connect(BUSCA_CEP_SERVICE_BASE_URL)
					.header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
					.data("relaxation", q)
					.data("tipoCEP", "ALL")
					.data("semelhante", "N")
					.post();
			Elements rows = doc.select("table.tmptabela tr:not(:first-child)");
			return rows;
		} catch (IOException e) {
			throw new CEPServiceFailureException(e);
		}
	}

	private CEP cep(Element row) {
		Elements cols = row.getElementsByTag("td");
		CEP cep = new CEP(numero(cols), logradouro(cols), bairro(cols),
				localidade(cols), uf(cols));
		return cep;
	}

	private String uf(Elements cols) {
		String cidadeEstado = cols.get(UF_INDEX).text().replace(NBSP, EMPTY);
		return cidadeEstado.split(SLASH)[1];
	}

	private String localidade(Elements cols) {
		String cidadeEstado = cols.get(LOCALIDADE_INDEX).text().replace(NBSP, EMPTY);
		return cidadeEstado.split(SLASH)[0];
	}

	private String bairro(Elements cols) {
		return cols.get(BAIRRO_INDEX).text().replace(NBSP, EMPTY);
	}

	private String logradouro(Elements cols) {
		return cols.get(LOGRADOURO_INDEX).text().replace(NBSP, EMPTY);
	}

	private String numero(Elements cols) {
		String num = cols.get(NUM_INDEX).text().replace(NBSP, EMPTY);
		return num.contains(HIFEN) ? num.replace(HIFEN, EMPTY) : num;
	}

	@Override
	public List<CEP> obtemPorEndereco(String query) {
		Elements rows = busca(query);
		List<CEP> ceps = new ArrayList<CEP>(rows.size());
		for (Element row : rows) {
			CEP cep = cep(row);
			ceps.add(cep);
		}
		return ceps;
	}

}