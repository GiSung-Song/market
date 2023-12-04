package study.market.item.enumType;

public enum ItemStatus {

    SELL("판매중"),
    SOLD_OUT("매진"),
    RECEIVING("입고중"),
    STOP_SELL("판매중지");

    private String korean;

    ItemStatus(String korean) {
        this.korean = korean;
    }

    public String getItemStatusKorean() {
        return korean;
    }
}