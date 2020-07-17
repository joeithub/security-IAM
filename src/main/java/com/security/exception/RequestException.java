package com.security.exception;

import com.security.common.http.HttpCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: tongq
 * @Date: 2020/3/12 20:36
 * @since：
 */

@Data
@NoArgsConstructor
public class RequestException  extends RuntimeException{
    private String code;
    private String messages;

    public RequestException(String code, String messages) {
        super(messages);
        this.code = code;
        this.messages = messages;
    }

    public RequestException(HttpCode httpCode) {
        super(httpCode.desc());
        this.code = httpCode.code();
        this.messages = httpCode.desc();
    }
}
