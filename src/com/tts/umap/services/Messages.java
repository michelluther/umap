package com.tts.umap.services;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.uml2abap.umap.services.messages"; //$NON-NLS-1$
	public static String Dateformat; 
	public static String AbapPublic;
	public static String AbapPrivate;
	public static String AbapProtected;
	public static String AbapDirectionChanging;
	public static String AbapDirectionExporting;
	public static String AbapDirectionReturn;
	@Deprecated
	public static String nuggetname;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String parameterNameLenght;
	public static String methodNameLenght;
	public static String classNameLenght;

	private Messages() {
	}
}
