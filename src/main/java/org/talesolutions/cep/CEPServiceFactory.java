package org.talesolutions.cep;

/**
 * Fábrica de CEPService
 *
 */
public class CEPServiceFactory {

	/**
	 * @return uma instância thread safe de CEPService
	 */
	public static CEPService getCEPService() {
		return new BuscaCEP();
	}
	
}
