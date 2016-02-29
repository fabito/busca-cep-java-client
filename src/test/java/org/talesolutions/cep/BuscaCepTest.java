package org.talesolutions.cep;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BuscaCepTest {

	private static final String VALID_CEP = "13084440";
	private static final CEP FLORDALISA_AMALIA_GRIGOL_COGHI = new CEP("13084440",
			"Rua Flordalisa Amália Grigol Coghi", "Jardim América", "Campinas",
			"SP");
	private static final CEP FLORDALISA_MEIRA_MONTE = new CEP("17065340",
			"Rua Flordalisa Meira Monte", "Núcleo Habitacional Vereador Edson Francisco da Silva", "Bauru",
			"SP");
	
	private CEPService buscaCEP;

	@Before
	public void setUp() throws Exception {
		buscaCEP = CEPServiceFactory.getCEPService();
	}

	@After
	public void tearDown() throws Exception {
		buscaCEP = null;
	}

	@Test
	public void busca_por_cep_valido() {
		CEP cep = buscaCEP.obtemPorNumeroCEP(VALID_CEP);
		assertThat(cep, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

	@Test
	public void multiplas_buscas_por_cep_valido() {
		CEP cep = buscaCEP.obtemPorNumeroCEP(VALID_CEP);
		assertThat(cep, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
		cep = buscaCEP.obtemPorNumeroCEP(VALID_CEP);
		assertThat(cep, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
		cep = buscaCEP.obtemPorNumeroCEP(VALID_CEP);
		assertThat(cep, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}
	
	@Test(expected=CEPNaoEncontradoException.class)
	public void busca_por_cep_invalido_deve_lancar_excecao() {
		buscaCEP.obtemPorNumeroCEP("1308444000");
	}

	@Test
	public void busca_por_enereco_invalido_deve_retornar_lista_vazia() {
		List<CEP> ceps = buscaCEP.obtemPorEndereco("adoleca");
		assertThat(ceps.isEmpty(), equalTo(true));
	}
	
	@Test
	public void busca_por_endereco_com_um_match() {
		List<CEP> ceps = buscaCEP.obtemPorEndereco("Rua Flordalisa Amalia Grigol Coghi");
		assertNotNull(ceps);
		assertThat(ceps.size(), equalTo(1));
		assertThat(ceps.get(0), equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

	@Test
	public void busca_por_endereco_com_acento() {
		List<CEP> ceps = buscaCEP.obtemPorEndereco("Rua Flordalisa Amália Grigol Coghi");
		assertNotNull(ceps);
		assertThat(ceps.size(), equalTo(1));
		assertThat(ceps.get(0), equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}
	@Test
	public void busca_por_endereco_com_varios_matches() {
		List<CEP> ceps = buscaCEP.obtemPorEndereco("Flordalisa");
		assertNotNull(ceps);
		assertThat(ceps.size(), equalTo(2));
		assertThat(ceps.get(0), equalTo(FLORDALISA_MEIRA_MONTE));
		assertThat(ceps.get(1), equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

}
