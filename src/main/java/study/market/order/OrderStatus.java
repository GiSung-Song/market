package study.market.order;

public enum OrderStatus {

    OUTSTANDING("미결제"),
    READY_DELIVERY("상품준비중"),
    DELIVERY("배송중"),
    FINISH_DELIVERY("배송완료");

    private String korean;

    OrderStatus(String korean) {
        this.korean = korean;
    }

    public String getOrderStatus() {
        return korean;
    }

}
