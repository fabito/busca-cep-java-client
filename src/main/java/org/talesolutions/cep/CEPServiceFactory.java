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
		return getCEPService(3);
	}

    /**
     *
     * @return uma instância thread safe de CEPService
     */
    public static CEPService getCEPService(int numRetries) {
        return new BuscaCEP(numRetries);
    }

	
}
