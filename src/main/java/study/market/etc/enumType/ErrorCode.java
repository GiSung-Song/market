package study.market.etc.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE-4001", "사용자를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE-4002", "해당 상품을 찾을 수 없습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE-4003", "해당 장바구니 정보를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "CODE-4004", "해당 주문 정보를 찾을 수 없습니다."),
    DELIVERY_NOT_FOUNT(HttpStatus.NOT_FOUND, "CODE-4005", "해당 배달 정보를 찾을 수 없습니다."),

    ORDER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "CODE-4011", "잘못된 주문 요청입니다."),
    CART_BAD_REQUEST(HttpStatus.BAD_REQUEST, "CODE-4012", "잘못된 장바구니 요청입니다."),

    MAIL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "CODE-5001", "이메일 전송 중 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
