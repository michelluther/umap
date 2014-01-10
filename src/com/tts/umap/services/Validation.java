package com.tts.umap.services;

import org.eclipse.osgi.util.NLS;

public class Validation extends NLS {
	private static final String BUNDLE_NAME = "org.uml2abap.umap.services.validation"; //$NON-NLS-1$
	
	public static String classAbbreviation;
	public static String exceptionAbbreviation;
	public static String interfaceAbbreviation;
	public static String langu;
	public static String r3release;
	public static String author;
	public static String validation;
	public static String projektAbbreviation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Validation.class);
	}

	private Validation() {
	}
}
