package id.my.hendisantika.springbootelasticsearch.util;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-elasticsearch
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/12/24
 * Time: 05.51
 * To change this template use File | Settings | File Templates.
 */
public class Page<T> {
    public static final Page EMPTY = new Page(Collections.emptyList(), null, 0, 0);

    private final List<T> products;
    private final String input;
    private final int from;
    private final int size;

    public Page(List<T> products, String input, int from, int size) {
        this.products = products;
        this.input = input;
        this.from = from;
        this.size = size;
    }

    List<T> get() {
        return Collections.unmodifiableList(products);
    }

    public String getInput() {
        return input;
    }

    public int getFrom() {
        return from;
    }

    public int getSize() {
        return size;
    }
}
