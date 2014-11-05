//@author A0114847B
package udo.language;

/**
 * This class is an abstract class that has to be extended. 
 * <p>
 * Extend this class by making a language class that gives values 
 * to all the language variables. 
 * <p>
 * Assign the userLanguagePack variable to the class you extended.
 *
 */
public abstract class LanguagePack {
	
	public static LanguagePack userLanguagePack =
			// replace this with the desired LanguagePack
			new EnglishLanguagePack();
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	/**
	 * This methods returns a LanguagePack object that represents the current user's
	 * desired language.
	 * @return the implemented LanguagePack object 
	 */
	public static LanguagePack getInstance() {
		userLanguagePack.setLanguage();
		return userLanguagePack;
	}
	
	/**
	 * This method must be overridden in the extending class. The method should
	 * contain variable assignments that set the language variables. 
	 */
	public abstract void setLanguage();
	
	
	// the following language variables hold the string values for 
	// their equivalent in english. The must be overridden 
	// in the extended class in the setLanguage() method. 
	
	// commands
	protected String ADD;
	protected String LIST;
	protected String DELETE;
	protected String SAVE;
	protected String EXIT;
	protected String UNDO;
	protected String EDIT;
	protected String MARK_DONE;
	protected String TOGGLE_DONE;
	
	// delimiters
	protected String FROM;
	protected String TO;
	protected String BY;
	
	// days
	protected String TODAY;
	protected String SUNDAY;
	protected String MONDAY;
	protected String TUESDAY;
	protected String WEDNESDAY;
	protected String THURSDAY;
	protected String FRIDAY;
	protected String SATURDAY;
	protected String TOMORROW;
	
	// edit fields
	protected String TITLE;
	protected String START_TIME;
	protected String END_TIME;
	protected String START_DATE;
	protected String END_DATE;
	protected String DUE_TIME;
	protected String DUE_DATE;

	// list queries
	protected String DAY;
	protected String ALL;
	protected String EVENT;
	protected String TASK;
	protected String PLAN;
	protected String DONE;
	
	
	
	// below are the getters for the language variables
	
	public String getADD() {
		return ADD;
	}
	public String getLIST() {
		return LIST;
	}
	public String getDELETE() {
		return DELETE;
	}
	public String getSAVE() {
		return SAVE;
	}
	public String getEXIT() {
		return EXIT;
	}
	public String getUNDO() {
		return UNDO;
	}
	public String getEDIT() {
		return EDIT;
	}
	public String getMARK_DONE() {
		return MARK_DONE;
	}
	public String getTOGGLE_DONE() {
		return TOGGLE_DONE;
	}
	public String getFROM() {
		return FROM;
	}
	public String getTO() {
		return TO;
	}
	public String getBY() {
		return BY;
	}
	public String getTODAY() {
		return TODAY;
	}
	public String getSUNDAY() {
		return SUNDAY;
	}
	public String getMONDAY() {
		return MONDAY;
	}
	public String getTUESDAY() {
		return TUESDAY;
	}
	public String getWEDNESDAY() {
		return WEDNESDAY;
	}
	public String getTHURSDAY() {
		return THURSDAY;
	}
	public String getFRIDAY() {
		return FRIDAY;
	}
	public String getSATURDAY() {
		return SATURDAY;
	}
	public String getTOMORROW() {
		return TOMORROW;
	}
	public String getTITLE() {
		return TITLE;
	}
	public String getSTART_TIME() {
		return START_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public String getDUE_TIME() {
		return DUE_TIME;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public String getDAY() {
		return DAY;
	}
	public String getALL() {
		return ALL;
	}
	public String getEVENT() {
		return EVENT;
	}
	public String getTASK() {
		return TASK;
	}
	public String getPLAN() {
		return PLAN;
	}
	public String getDONE() {
		return DONE;
	}
	
}
