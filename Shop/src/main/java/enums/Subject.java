package enums;

public enum Subject {
    CUSTOMER_SERVICE("Customer service"),
    WEBMASTER("Webmaster"),
    CHOOSE("-- Choose --");

    private String subject;

    Subject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
