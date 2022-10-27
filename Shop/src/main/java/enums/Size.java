package enums;

public enum Size {
    S("S"),
    M("M"),
    L("L");

    private String size;

    Size(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
