package computers.support;

import org.openqa.selenium.By;

public enum Button {
    ADD_A_NEW_COMPUTER("Add a new computer", By.id(Constants.ADD_NEW_COMPUTER_ID)),
    DELETE_COMPUTER("Delete this computer", By.xpath(Constants.DELETE_COMPUTER_XPATH)),
    CANCEL("Cancel", By.xpath(Constants.CANCEL_XPATH)),
    FILTER_BY_NAME("Filter by name", By.id(Constants.FILTER_BY_NAME_ID)),
    SAVE_THIS_COMPUTER("Save this computer", By.xpath(Constants.SAVE_CREATE_COMPUTER_XPATH)),
    CREATE_THIS_COMPUTER("Create this computer", By.xpath(Constants.SAVE_CREATE_COMPUTER_XPATH));

    Button(String label, By match) {
        this.label = label;
        this.match = match;
    }

    private String label;
    private By match;

    public static Button retrieveButtonByLabel(String label) {
        for (Button button:values()) {
            if (label.equals(button.label)) return button;
        }
        throw new IllegalArgumentException("Button label does not exist: "+ label);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public By getMatch() {
        return match;
    }

    public void setMatch(By match) {
        this.match = match;
    }
}
