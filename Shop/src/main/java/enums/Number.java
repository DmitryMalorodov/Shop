package enums;

public enum Number {
    NULL(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int number;

    Number(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
