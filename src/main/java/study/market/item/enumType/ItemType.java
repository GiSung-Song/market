package study.market.item.enumType;

public enum ItemType {
    FOOD("음식"),
    SNACK("과자"),
    ICE("냉동"),
    DRINK("음료"),
    GOODS("상품");

    private String korean;

    ItemType(String korean) {
        this.korean = korean;
    }

    public String getItemTypeKorean() {
        return korean;
    }
}
