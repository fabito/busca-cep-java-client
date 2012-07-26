package org.talesolutions.cep;

/**
 * Exceção lançada na ocorrência de algum erro inesperado no acesso ao serviço de busca de CEPs dos Correios
 * 
 * http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do
 *  
 * @author Fábio Franco Uechi
 */
public class CEPServiceFailureException extends RuntimeException {

	private static final long serialVersionUID = 1462228622695384135L;
	
	public CEPServiceFailureException(Throwable cause) {
		super(cause);
	}

}
