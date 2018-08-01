package com.crayon2f.jdk.consolidate.nio.channel;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.List;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by feiFan.gou on 2018/5/10 15:16.
 */
class ScatterGatherChannel extends Base {

    @Test
    @SneakyThrows
    void test() {

        int reps = 10;
        FileOutputStream fos = new FileOutputStream(empty_file_path);
        GatheringByteChannel gatheringByteChannel = fos.getChannel();
        ByteBuffer[] bs = utterBS(reps);

        while (gatheringByteChannel.write(bs) > 0);
        System.out.println("write complete");

    }

    private static String [] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };
    private static String [] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated"
    };
    private static String [] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };
    private static String newline = System.getProperty ("line.separator");
    private ByteBuffer[] utterBS(int howMany) {

        List<ByteBuffer> list = Lists.newLinkedList();
        for (int i = 0; i < howMany; i++) {
            list.add(pickRandom(col1, " "));
            list.add(pickRandom(col2, " "));
            list.add(pickRandom(col3, newline));
        }
        ByteBuffer[] bs = new ByteBuffer[list.size()];
        list.toArray(bs);
        return bs;
    }

    private static Random rand = new Random();
    private ByteBuffer pickRandom(String[] strings, String suffix) {

        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buffer = ByteBuffer.allocate(total);
        buffer.put(string.getBytes(UTF_8));
        buffer.put(suffix.getBytes(UTF_8));
        buffer.flip();
        return (buffer);
    }
}
