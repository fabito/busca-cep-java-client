package org.talesolutions.cep;

public abstract class AbstractCEPServiceDecorator implements CEPService {

	protected CEPService decorated;

	public AbstractCEPServiceDecorator(CEPService decorated) {
		super();
		this.decorated = decorated;
	}	
}