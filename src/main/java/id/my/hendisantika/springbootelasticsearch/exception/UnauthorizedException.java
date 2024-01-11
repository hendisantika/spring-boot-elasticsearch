package id.my.hendisantika.springbootelasticsearch.exception;

import id.my.hendisantika.springbootelasticsearch.exception.base.ServiceException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 1/11/24
 * Time: 09:43
 * To change this template use File | Settings | File Templates.
 */
public class UnauthorizedException extends ServiceException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
