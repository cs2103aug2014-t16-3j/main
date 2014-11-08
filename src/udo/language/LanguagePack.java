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
			 new IndonesianLanguagePack();
//			new EnglishLanguagePack();
	
	
	
	
	/********************************************************
	 * To Language translators: do not touch the code below *
	 ********************************************************/
	
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
	protected String SEARCH;
	
	// delimiters
	protected String FROM;
	protected String TO;
	protected String BY;
	
	// days
	protected String YESTERDAY;
	protected String TODAY;
	protected String TONIGHT;
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
	
//@author A0114088H	
	// Welcome message
	protected String WELCOME_MESSAGE;
	protected String SUB_WELCOME_MESSAGE;
	
	// Popup messages
	protected String POPUP_SAVED;
	protected String POPUP_PARSING_FAIL;
	protected String POPUP_EXEC_FAIL;
	protected String POPUP_TOGGLE_DONE;
	protected String POPUP_MARK_AS_DONE;
	protected String POPUP_ADDED;
	protected String POPUP_DELETED;
	protected String POPUP_EDITED;
	protected String POPUP_NO_ITEMS_FOUND;
	protected String POPUP_LIST;
	protected String POPUP_SEARCH;
	protected String POPUP_QUERY_ALL;
	protected String POPUP_QUERY_DONE;
	protected String POPUP_QUERY_DATE;
	protected String POPUP_QUERY_EVENT;
	protected String POPUP_QUERY_PLAN;
	protected String POPUP_QUERY_TASK;
	
	// DayScreen messages
	protected String FREE_TODAY;
	
	// ToDoScreen messages
	protected String TO_DO_TITLE;
	protected String NO_UPCOMING_TASKS;
	
	// SingleView messages
	protected String SINGLE_MARK_AS_DONE;
	protected String SINGLE_TOGGLE_DONE;
	protected String SINGLE_EDITED;
	protected String SINGLE_FROM;
	protected String SINGLE_TO;
	protected String SINGLE_THIS_IS_EDITED_ITEM;
	protected String SINGLE_SUCCESFULLY_ADDED;
	protected String SINGLE_SUCCESSFULLY_DELETED;
	
	// Months
	protected String JANUARY;
	protected String FEBRUARY;
	protected String MARCH;
	protected String APRIL;
	protected String MAY;
	protected String JUNE;
	protected String JULY;
	protected String AUGUST;
	protected String SEPTEMBER;
	protected String OCTOBER;
	protected String NOVEMBER;
	protected String DECEMBER;
	
	//Abbreviated Months
	protected String JAN;
	protected String FEB;
	protected String MAR;
	protected String APR;
	protected String MAY_SHORT;
	protected String JUN;
	protected String JUL;
	protected String AUG;
	protected String SEP;
	protected String OCT;
	protected String NOV;
	protected String DEC;
	
	// the method below converts java string day format to the appropriate language
	public String convertDayToLanguage(String javaFormattedDay) {
		switch (javaFormattedDay) {
			case "Monday" :
				return getMONDAY();
			case "Tuesday" :
				return getTUESDAY();
			case "Wednesday" :
				return getWEDNESDAY();
			case "Thursday" :
				return getTHURSDAY();
			case "Friday" :
				return getFRIDAY();
			case "Saturday" :
				return getSATURDAY();
			case "Sunday" :
				return getSUNDAY();
			default :
				return "";
		}
	}

	public String convertMonthToLanguage(String javaFormattedMonth) {
		switch (javaFormattedMonth) {
			case "January" :
				return getJANUARY();
			case "Jan":
				return getJAN();
			case "February" :
				return getFEBRUARY();
			case "Feb" :
				return getFEB();
			case "March" :
				return getMARCH();
			case "Mar" :
				return getMAR();
			case "April" :
				return getAPRIL();
			case "Apr" :
				return getAPR();
			case "May" :
				return getMAY();
			case "June" :
				return getJUNE();
			case "Jun" :
				return getJUN();
			case "July" :
				return getJULY();
			case "Jul" :
				return getJUL();
			case "August" :
				return getAUGUST();
			case "Aug" :
				return getAUG();
			case "September" :
				return getSEPTEMBER();
			case "Sep" :
				return getSEP();
			case "October" :
				return getOCTOBER();
			case "Oct" :
				return getOCT();
			case "November" :
				return getNOVEMBER();
			case "Nov" :
				return getNOV();
			case "December" :
				return getDECEMBER();
			case "Dec" :
				return getDEC();
			default :
				return "";
		}
	}
	
//@author A0114847B	
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
	public String getSEARCH() {
		return SEARCH;
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
	public String getYESTERDAY() {
		return YESTERDAY;
	}
	public String getTODAY() {
		return TODAY;
	}
	public String getTONIGHT() {
		return TONIGHT;
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

	public static LanguagePack getUserLanguagePack() {
		return userLanguagePack;
	}

	public String getPOPUP_SAVED() {
		return POPUP_SAVED;
	}

	public String getPOPUP_PARSING_FAIL() {
		return POPUP_PARSING_FAIL;
	}

	public String getPOPUP_EXEC_FAIL() {
		return POPUP_EXEC_FAIL;
	}

	public String getPOPUP_TOGGLE_DONE() {
		return POPUP_TOGGLE_DONE;
	}

	public String getPOPUP_MARK_AS_DONE() {
		return POPUP_MARK_AS_DONE;
	}

	public String getPOPUP_ADDED() {
		return POPUP_ADDED;
	}

	public String getPOPUP_DELETED() {
		return POPUP_DELETED;
	}

	public String getPOPUP_EDITED() {
		return POPUP_EDITED;
	}

	public String getPOPUP_NO_ITEMS_FOUND() {
		return POPUP_NO_ITEMS_FOUND;
	}

	public String getPOPUP_LIST() {
		return POPUP_LIST;
	}

	public String getPOPUP_QUERY_ALL() {
		return POPUP_QUERY_ALL;
	}

	public String getPOPUP_QUERY_DONE() {
		return POPUP_QUERY_DONE;
	}

	public String getPOPUP_QUERY_DATE() {
		return POPUP_QUERY_DATE;
	}

	public String getPOPUP_QUERY_EVENT() {
		return POPUP_QUERY_EVENT;
	}

	public String getPOPUP_QUERY_PLAN() {
		return POPUP_QUERY_PLAN;
	}

	public String getPOPUP_QUERY_TASK() {
		return POPUP_QUERY_TASK;
	}

	public String getPOPUP_SEARCH() {
		return POPUP_SEARCH;
	}

	public String getWELCOME_MESSAGE() {
		return WELCOME_MESSAGE;
	}

	public String getSUB_WELCOME_MESSAGE() {
		return SUB_WELCOME_MESSAGE;
	}

	public String getFREE_TODAY() {
		return FREE_TODAY;
	}

	public String getJANUARY() {
		return JANUARY;
	}

	public String getFEBRUARY() {
		return FEBRUARY;
	}

	public String getMARCH() {
		return MARCH;
	}

	public String getAPRIL() {
		return APRIL;
	}

	public String getMAY() {
		return MAY;
	}

	public String getJUNE() {
		return JUNE;
	}

	public String getJULY() {
		return JULY;
	}

	public String getAUGUST() {
		return AUGUST;
	}

	public String getSEPTEMBER() {
		return SEPTEMBER;
	}

	public String getOCTOBER() {
		return OCTOBER;
	}

	public String getNOVEMBER() {
		return NOVEMBER;
	}

	public String getDECEMBER() {
		return DECEMBER;
	}

	public String getJAN() {
		return JAN;
	}

	public String getFEB() {
		return FEB;
	}

	public String getMAR() {
		return MAR;
	}

	public String getAPR() {
		return APR;
	}

	public String getMAY_SHORT() {
		return MAY_SHORT;
	}

	public String getJUN() {
		return JUN;
	}

	public String getJUL() {
		return JUL;
	}

	public String getAUG() {
		return AUG;
	}

	public String getSEP() {
		return SEP;
	}

	public String getOCT() {
		return OCT;
	}

	public String getNOV() {
		return NOV;
	}

	public String getDEC() {
		return DEC;
	}

	public String getTO_DO_TITLE() {
		return TO_DO_TITLE;
	}

	public String getNO_UPCOMING_TASKS() {
		return NO_UPCOMING_TASKS;
	}

	public String getSINGLE_MARK_AS_DONE() {
		return SINGLE_MARK_AS_DONE;
	}

	public String getSINGLE_TOGGLE_DONE() {
		return SINGLE_TOGGLE_DONE;
	}

	public String getSINGLE_EDITED() {
		return SINGLE_EDITED;
	}

	public String getSINGLE_FROM() {
		return SINGLE_FROM;
	}

	public String getSINGLE_TO() {
		return SINGLE_TO;
	}

	public String getSINGLE_THIS_IS_EDITED_ITEM() {
		return SINGLE_THIS_IS_EDITED_ITEM;
	}

	public String getSINGLE_SUCCESFULLY_ADDED() {
		return SINGLE_SUCCESFULLY_ADDED;
	}

	public String getSINGLE_SUCCESSFULLY_DELETED() {
		return SINGLE_SUCCESSFULLY_DELETED;
	}

}
