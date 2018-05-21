package com.crayon2f.jdk.consolidate.nio.selector;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.SneakyThrows;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.*;
import java.util.List;

/**
 * Created by feiFan.gou on 2018/5/8 16:20.
 */
public class SelectorTest extends Base {

    @Test
    @SneakyThrows
    public void select() {

        Selector selector = Selector.open();
        SelectableChannel channel = SocketChannel.open(new InetSocketAddress("http://jenkov.com", 80));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    @Test
    public void test() {

        Path this_dir = Paths.get(dir_path);
        System.out.println("Now watching the current directory ...");

        try {
            WatchService watcher = this_dir.getFileSystem().newWatchService();
            this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey watchKey = watcher.take();

            List<WatchEvent<?>> events = watchKey.pollEvents();
            for (WatchEvent event : events) {
                System.out.println("Someone just created the file '" + event.context().toString() + "'.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
