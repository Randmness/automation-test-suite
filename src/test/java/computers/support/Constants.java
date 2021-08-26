package computers.support;

public final class Constants {
    //Urls
    public static final String NEW_COMPUTER_URL = "http://computer-database.herokuapp.com/computers/new";

    //Element attributes
    public static final String CLASS_ATTR = "class";

    //Class values
    public static final String CLEARFIX_ERROR = "clearfix error";

    //Buttons
    public static final String ADD_NEW_COMPUTER_ID = "add";
    public static final String DELETE_COMPUTER_XPATH = "/html/body/section/form/input";
    public static final String CANCEL_XPATH = "/html/body/section/form/div/a";
    public static final String FILTER_BY_NAME_ID = "searchsubmit";
    public static final String SAVE_CREATE_COMPUTER_XPATH = "/html/body/section/form/div/input[@type='submit']";

    //Field entry
    public static final String COMPUTER_NAME_FIELD_ID="name";
    public static final String INTRODUCED_NAME_FIELD_ID="introduced";
    public static final String DISCONTINUED_NAME_FIELD_ID="discontinued";
    public static final String COMPANY_FIELD_XPATH = "/html/body/section/form/fieldset/div[4]/div/select";
    public static final String SEARCH_FIELD_ID = "searchbox";
    public static final String COMPUTER_NAME_FIELD_XPATH="/html/body/section/form/fieldset/div[1]";
    public static final String INTRODUCED_FIELD_XPATH="/html/body/section/form/fieldset/div[2]";
    public static final String DISCONTINUED_FIELD_XPATH="/html/body/section/form/fieldset/div[3]";

    //Results table
    public static final String COMPUTER_NAME_RESULT_XPATH="/html/body/section/table/tbody/tr/td[1]/a";
    public static final String INTRODUCED_RESULT_XPATH="/html/body/section/table/tbody/tr/td[2]";
    public static final String DISCONTINUED_RESULT_XPATH="/html/body/section/table/tbody/tr/td[3]";
    public static final String COMPANY_SELECT_RESULT_XPATH="/html/body/section/table/tbody/tr/td[4]";

    //Page Elements
    public static final String PAGE_HEADER_XPATH ="/html/body/section/h1";
    public static final String MESSAGE_XPATH = "/html/body/section/div[1]";

    private Constants() {}
}
