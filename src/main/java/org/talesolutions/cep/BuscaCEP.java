package org.talesolutions.cep;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Invoca a busca de CEP disponível no site dos correios e extrai os resultados
 * do html retornado. Esta implementação utiliza Jsoup para fazer a
 * requisição GET ao serviço dos Correios e também fazer o extrair os dados do
 * HTML
 * 
 * O serviço invocado por essa implementação roda na seguinte URL:
 * 
 * http://www.buscacep.correios.com.br/servicos/dnec/consultaEnderecoAction.do
 * 
 * @author Fábio Franco Uechi
 */
class BuscaCEP implements CEPService {

	private static final int NUM_INDEX = 4;
	private static final int LOGRADOURO_INDEX = 0;
	private static final int BAIRRO_INDEX = 1;
	private static final int LOCALIDADE_INDEX = 2;
	private static final int UF_INDEX = 3;
	private static final String HIFEN = "-";
	private static final String BUSCA_CEP_SERVICE_BASE_URL = "http://www.buscacep.correios.com.br/servicos/dnec/consultaEnderecoAction.do?relaxation=%s&TipoCep=ALL&semelhante=S&cfm=1&Metodo=listaLogradouro&TipoConsulta=relaxation";

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
			Document doc = Jsoup.connect(
					String.format(BUSCA_CEP_SERVICE_BASE_URL, URLEncoder.encode(q, "ISO-8859-1"))).get();
			Elements rows = doc.getElementsByAttributeValueMatching("onclick",
					"javascript:detalharCep.*");
			return rows;
		} catch (IOException e) {
			throw new CEPServiceFailureException(e);
		}		
	}

	private CEP cep(Element row) {
		Elements cols = row.getElementsByTag("td");
		CEP cep = new CEP(numero(cols), logradouro(cols), bairro(cols), localidade(cols), uf(cols));
		return cep;
	}

	private String uf(Elements cols) {
		return cols.get(UF_INDEX).text();
	}

	private String localidade(Elements cols) {
		return cols.get(LOCALIDADE_INDEX).text();
	}

	private String bairro(Elements cols) {
		return cols.get(BAIRRO_INDEX)
				.text();
	}

	private String logradouro(Elements cols) {
		return cols.get(LOGRADOURO_INDEX).text();
	}

	private String numero(Elements cols) {
		String num = cols.get(NUM_INDEX).text();
		return num.contains(HIFEN) ?  num.replace(HIFEN, "") : num ;
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