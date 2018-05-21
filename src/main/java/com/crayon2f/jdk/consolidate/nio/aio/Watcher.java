package com.crayon2f.jdk.consolidate.nio.aio;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by feiFan.gou on 2018/5/18 17:53.
 */
public class Watcher {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("C:\\Users\\Lenovo\\Desktop");
        WatchService watcher = path.getFileSystem().newWatchService();
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey watchKey = watcher.take();
        while (true) {

            List<WatchEvent<?>> watchEventList = watchKey.pollEvents();
            watchEventList.forEach(event -> {
                WatchEvent.Kind<Path> kind = (WatchEvent.Kind<Path>) event.kind();
                if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                    System.out.println("Someone just created the file ==> " + event.context());
                } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                    System.out.println("Someone just deleted the file ==> " + event.context());
                } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                    System.out.println("Someone just modified the file ==> " + event.context());
                }
            });
        }

    }


}
