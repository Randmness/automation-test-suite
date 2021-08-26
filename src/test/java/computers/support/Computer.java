package computers.support;

public class Computer {
    private String computerName;
    private String introduced;
    private String discontinuedDate;
    private String company;


    public Computer(String computerName, String introduced, String discontinuedDate, String company) {
        this.computerName = computerName;
        this.introduced = introduced;
        this.discontinuedDate = discontinuedDate;
        this.company = company;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinuedDate() {
        return discontinuedDate;
    }

    public void setDiscontinuedDate(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
