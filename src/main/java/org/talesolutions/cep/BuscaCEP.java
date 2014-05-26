package org.talesolutions.cep;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * Invoca a busca de CEP disponível no site dos correios e extrai os resultados do html retornado.
 * Esta implementação utiliza HtmlUnit para fazer a requisição GET ao serviço dos Correios e também 
 * fazer o extrair os dados do HTML via XPath
 * 
 * O serviço invocado por essa implementação roda na seguinte URL:
 * 
 * http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do
 * 
 * @author Fábio Franco Uechi
 */
class BuscaCEP implements CEPService {

	private static final String HIFEN = "-";
	private static final String XPATH_TABELA_RESULTADO_CONSULTA = "//table[@bgcolor='gray']";
	private static final String BUSCA_CEP_SERVICE_BASE_URL = "http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do?Metodo=listaLogradouro&TipoConsulta=relaxation&StartRow=1&EndRow=10&relaxation=";

	private WebClient getWebClient() {
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		webClient.setCssEnabled(false);
		return webClient;
	}

	/* (non-Javadoc)
	 * @see org.talesolutions.cep.CEPService#obtemPorNumeroCEP(int)
	 */
	@Override
	public CEP obtemPorNumeroCEP(String numeroCEP) {
		final HtmlTable table = getHtmlTableWithResults(numeroCEP);
		HtmlTableRow row = table.getRow(0);
		return criaCEP(row);
	}

	private HtmlTable getHtmlTableWithResults(String query){
		try {
			final HtmlPage page = getWebClient()
					.getPage(BUSCA_CEP_SERVICE_BASE_URL + query);
			final HtmlTable table = (HtmlTable) page.getByXPath(
					XPATH_TABELA_RESULTADO_CONSULTA).get(
					0);
			return table;
		} catch (FailingHttpStatusCodeException e) {
			throw new CEPServiceFailureException(e);
		} catch (MalformedURLException e) {
			throw new CEPServiceFailureException(e);
		} catch (IndexOutOfBoundsException e) {
			throw new CEPNaoEncontradoException(query, e);
		} catch (IOException e) {
			throw new CEPServiceFailureException(e);
		} finally {
			getWebClient().closeAllWindows();
		}
	}

	/* (non-Javadoc)
	 * @see org.talesolutions.cep.CEPService#obtemPorEndereco(java.lang.String)
	 */
	@Override
	public List<CEP> obtemPorEndereco(String query) {
		final HtmlTable table = getHtmlTableWithResults(query);
		List<CEP> ceps = new ArrayList<CEP>(table.getRows().size());
		for (HtmlTableRow row : table.getRows()) {
			ceps.add(criaCEP(row));
		}
		return ceps;
	}

	private CEP criaCEP(HtmlTableRow row) {
		return new CEP(
				StringUtils.remove(row.getCell(4).asText(), HIFEN), 
				row.getCell(0).asText(),
				row.getCell(1).asText(),
				row.getCell(2).asText(),
				row.getCell(3).asText()
		);
	}
}