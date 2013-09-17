package com.tts.umap.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.VisibilityKind;




public class AbapService {

	private static Logger logger;
	private static FileHandler fh;
	
	private String getPref(String key){
		String a = Platform.getPreferencesService().getString("com.tts.umap.ui", key , null, null );
		System.out.println("test: " + a );
		return a;
	}
	public String testMeth() {
		System.out.println("test");
		return "blubbberrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr";
	}

	/**
	 * Returns a UUID which is formated to a SAP valid UUID
	 * 
	 * @param dummy
	 * @return 32 length uuid
	 */
	@Deprecated
	public String getUUID(String dummy) {
		return UUID.randomUUID().toString()
				.replaceAll("-", "").trim().toUpperCase(); //$NON-NLS-1$ //$NON-NLS-2$
	}
	@Deprecated
	public String getAuthor(String dummy) {
		return Validation.author;
	}

	/**
	 * Language
	 * 
	 * @param dummy
	 * @return the Language
	 */
	@Deprecated
	public String getLangu(String dummy) {
		return Validation.langu;
	}

	/**
	 * R3 release
	 * 
	 * @param dummy
	 * @return r3release e.g. 700
	 */
	@Deprecated
	public String getRelease(String dummy) {
		return Validation.r3release;
	}

	/**
	 * Determines the current date. used as creation date
	 * 
	 * @param dummy
	 * @return current date
	 */
	@Deprecated
	public String getDate(String dummy) {
		SimpleDateFormat d = new SimpleDateFormat(Messages.Dateformat);
		return d.format(new Date());
	}

	@Deprecated
	public String getNuggetName(String dummy) {
		return Messages.nuggetname;

	}

	/**
	 * Returns the integer value of ABAP visibility
	 * 
	 * @param visibility
	 *            pass public, private or protected
	 * @return public = 2 private = 0 protected = 1
	 */
	public int getAbapVisibility(String visibility) {
		if (visibility.toLowerCase().equals(Messages.AbapPublic)) {
			return 2;
		} else if (visibility.toLowerCase().equals(Messages.AbapPrivate)) {
			return 0;
		} else if (visibility.toLowerCase().equals(Messages.AbapProtected)) {
			return 1;
		}
		return -1;

	}

	public boolean hasAllowedParameterCombination(Class c) {
		for (Operation op : c.getAllOperations()) {
			Vector<String> list = new Vector<String>();
			for (Parameter pa : op.getOwnedParameters()) {
				list.add(pa.getDirection().toString());
			}
			if ((list.contains(Messages.AbapDirectionChanging) || list
					.contains(Messages.AbapDirectionExporting))
					&& list.contains(Messages.AbapDirectionReturn)) {
				// getLogger().info("start");
				// getLogger().log(Level.SEVERE,
				// "Parameter Combination not allowed", op.getName());
				// getLogger().fine("END");
				return false;
			}
			// list.clear();
		}
		return true;
	}

	/**
	 * This Method validates (if switched on in validation.properties) according
	 * to the settings the validity of the class (names, length, correctness of
	 * parameter combination)
	 * 
	 * @param c
	 *            an UML2 Class element
	 * @return true all checks where passed
	 */
	public boolean isValid(Class c) {
//		if (getPref("booleanPreference").equals(true)) {
//			// getLogger().info("Start Logging");
//			if ((c.getAppliedStereotype("extensions::exception") != null)
//					|| (c.getAppliedStereotype("extensions::baseException") != null)) {
//				if (!c.getName().toUpperCase()
//						.startsWith(getPref("exceptionAbbreviation").toUpperCase())) {
//					return false;
//				}
//			} else {
//				if (!c.getName().toUpperCase()
//						.startsWith(getPref("classAbbreviation").toUpperCase())) {
//					return false;
//				}
//			}
//			if (c.getName().length() > Integer
//					.parseInt(Messages.classNameLenght))
//				return false;
//			if (hasAllowedParameterCombination(c) == false)
//				return false;
//		}
		return true;
	}

	/**
	 * This Method validates (if switched on in validation.properties) according
	 * to the settings the validity of the interface (names, length, correctness
	 * of parameter combination)
	 * 
	 * @param i
	 *            an UML2 Interface element
	 * @return true all checks where passed
	 */
	public boolean isValid(Interface i) {
		if (getPref("booleanPreference").equals(true)) {
			if (!i.getName().toUpperCase()
					.startsWith(getPref("interfaceAbbreviation").toUpperCase()))
				return false;
			if (i.getName().length() > Integer
					.parseInt(Messages.classNameLenght))
				return false;
			if (hasAllowedParameterCombination(i) == false)
				return false;
		}
		return true;
	}

	public boolean hasAllowedParameterCombination(Interface i) {
		Vector<String> list = new Vector<String>();
		for (Operation op : i.getAllOperations()) {
			if (op.getVisibility() != VisibilityKind.PUBLIC_LITERAL)
				return false;
			for (Parameter pa : op.getOwnedParameters()) {
				list.add(pa.getDirection().toString());
			}
			if ((list.contains(Messages.AbapDirectionChanging) || list
					.contains(Messages.AbapDirectionExporting))
					&& list.contains(Messages.AbapDirectionReturn)) {
				return false;
			}
			list.clear();
		}
		return true;
	}

	@SuppressWarnings("unused")
	private static Logger getLogger() {
		if (logger == null)
			logger = Logger.getLogger("com.tts.umap");
		if (fh == null) {
			try {
				fh = new FileHandler("c:/mylog.txt");
				logger.addHandler(fh);
			} catch (SecurityException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
		}
		return logger;

	}

	public String setDescription(Classifier c) {
		EList<Comment> comment = c.getOwnedComments();
		if (!comment.isEmpty()) {
			if ((!Validation.projektAbbreviation.trim().equalsIgnoreCase(""))
					&& (!comment.get(0).getBody().trim().equalsIgnoreCase(""))) {
				return Validation.projektAbbreviation.concat(" - ")
						.concat(comment.get(0).getBody()).trim();
			}
			if ((Validation.projektAbbreviation.trim().equalsIgnoreCase(""))
					&& (!comment.get(0).getBody().trim().equalsIgnoreCase(""))) {
				return comment.get(0).getBody().trim();
			}
			if ((!Validation.projektAbbreviation.trim().equalsIgnoreCase(""))
					&& (comment.get(0).getBody().trim().equalsIgnoreCase(""))) {
				return Validation.projektAbbreviation.trim();
			}
		}
		return "Dummy please replace";
	}



	@Deprecated
	public static void main(String[] args) {
//		String a = Platform.getPreferencesService().getString(Activator.PLUGIN_ID, "P_CLASS_NAME", "Test", null );
//		System.out.println("test: " + a );
		

	}
}
