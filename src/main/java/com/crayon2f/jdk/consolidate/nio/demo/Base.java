package com.crayon2f.jdk.consolidate.nio.demo;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by feiFan.gou on 2018/5/17 17:00.
 */
public abstract class Base {

    protected static final int port = 1234;

    protected static final String host = "127.0.0.1";

    protected void polling(Selector selector) {

        try {
            //无论是否有读写事件发生，selector 每隔1s被唤醒一次
            selector.select(1000);
            Optional.ofNullable(selector.selectedKeys())
                    .filter(CollectionUtils::isNotEmpty)
                    .ifPresent(selectionKeys ->
                            Optional.of(selectionKeys.iterator())
                                    .ifPresent(iterator ->
                                            iterator.forEachRemaining(selectionKey -> {
                                                handle.accept(selectionKey);
                                                iterator.remove();
                                            })));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Consumer<SelectionKey> handle;


    protected void print(String message, Object... objects) {

        message = "========= " + message + " ========= ";
        System.out.printf(message, objects);
        System.out.print("\r\n");
    }
}
