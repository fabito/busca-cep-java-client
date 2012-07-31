package org.talesolutions.cep;

/**
 * Decorator base para criação de decorators de {@link CEPService}
 * @author Fábio Franco Uechi
 *
 */
public abstract class AbstractCEPServiceDecorator implements CEPService {

	protected CEPService decorated;

	public AbstractCEPServiceDecorator(CEPService decorated) {
		super();
		this.decorated = decorated;
	}	
}