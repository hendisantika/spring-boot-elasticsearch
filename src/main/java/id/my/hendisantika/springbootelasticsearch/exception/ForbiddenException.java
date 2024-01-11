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
public class ForbiddenException extends ServiceException {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
