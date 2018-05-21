package com.crayon2f.jdk.consolidate.nio.buffer;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by feiFan.gou on 2018/5/11 13:37.
 */
public class MappedBufferTest extends Base {

    @Test
    @SneakyThrows
    public void test() {

        File tempFile = File.createTempFile("mapped", ".txt");
        @Cleanup
        RandomAccessFile accessFile = new RandomAccessFile(tempFile, "rw");
        @Cleanup
        FileChannel channel = accessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
//        buffer.put("This is the file content".getBytes(Charset.defaultCharset()));
//        buffer.flip();
//        channel.write(buffer);

        // Put something else in the file, starting at location 8192.
        // 8192 is 8 KB, almost certainly a different memory/FS page.
        // This may cause a file hole, depending on the
        // filesystem page size.

        buffer.clear();
        buffer.put("This is the file content".getBytes(Charset.defaultCharset()));
        buffer.flip();
        channel.write(buffer, 8192);

        // Create three types of mappings to the same file
        MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0, channel.size());
        // the buffer states before any modifications
        System.out.println("begin...");

        showBuffers(ro, rw, cow);
        // Modify the copy-on-write buffer
        cow.position(8);
        cow.put("COW".getBytes(Charset.defaultCharset()));
        System.out.println("Change to COW buffer");
        showBuffers(ro, rw, cow);
        // Modify the read/write buffer92
        rw.position(9);
        rw.put(" R/W ".getBytes());
        rw.position(8194);
        rw.put(" R/W ".getBytes());
        rw.force();
        System.out.println("Change to R/W buffer");
        showBuffers(ro, rw, cow);
        // Write to the file through the channel; hit both pages
        buffer.clear();
        buffer.put("Channel write ".getBytes());
        buffer.flip();
        channel.write(buffer, 0);
        buffer.rewind();
        channel.write(buffer, 8202);
        System.out.println("Write on channel");
        showBuffers(ro, rw, cow);
        // Modify the copy-on-write buffer again
        cow.position(8207);
        cow.put(" COW2 ".getBytes());
        System.out.println("Second change to COW buffer");
        showBuffers(ro, rw, cow);
        // Modify the read/write buffer
        rw.position(0);
        rw.put(" R/W2 ".getBytes());
        rw.position(8210);
        rw.put(" R/W2 ".getBytes());
        rw.force();
        System.out.println("Second change to R/W buffer");
        showBuffers(ro, rw, cow);
        // cleanup
//        channel.close( );
//        accessFile.close( );
        tempFile.delete();
    }

    // Show the current content of the three buffers
    private void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) {

        dumpBuffer("R/O", ro);
        dumpBuffer("R/W", rw);
        dumpBuffer("COW", cow);
        System.out.println();
    }

    // Dump buffer content, counting and skipping nulls
    private void dumpBuffer(String prefix, ByteBuffer buffer) {
        System.out.print(prefix + ": '");
        int nulls = 0;
        int limit = buffer.limit();
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get(i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }
            if (nulls != 0) {
                System.out.print("|[" + nulls
                        + " nulls]|");
                nulls = 0;
            }
            System.out.print(c);
        }
        System.out.println("'");
    }

    // MappedByteBuffer是java nio引入的文件内存映射方案，读写性能极高

    @Test
    @SneakyThrows
    public void write() {

        // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。
        int length = 0x8000000;
        @Cleanup
        FileChannel fc = new RandomAccessFile(empty_file_path, "rw").getChannel();
        //注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
        //写128M的内容
        for (int i = 0; i < length / 1024; i++) {
            out.put("java".getBytes(Charset.defaultCharset()));
        }
        System.out.println("Finished writing");
        //读取文件中间6个字节内容
        for (int i = length / 2; i < length / 2 + 6; i++) {
            System.out.print((char) out.get(i));
        }
    }

    @Test
    @SneakyThrows
    public void read() {

        @Cleanup
        FileChannel channel = FileChannel.open(Paths.get(large_file_path), StandardOpenOption.READ, StandardOpenOption.WRITE);
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
        @Cleanup
        FileChannel outChannel = new FileOutputStream(desktop_dir + File.separator + "temp2.pdf").getChannel();
        outChannel.write(buffer);
//        Files.deleteIfExists(Paths.get(large_file_path));


    }

    @SneakyThrows
    public void forceClean(final MappedByteBuffer buffer) {

        buffer.force();
        //TODO jdk1.9 sun.misc.Cleaner 已经去掉了 ！！！
//        AccessController.doPrivileged((PrivilegedAction<Object>) () -> {

//            try {
//                // System.out.println(buffer.getClass().getName());
//                Method getCleanerMethod = buffer.getClass().getMethod("cleaner");
//                getCleanerMethod.setAccessible(true);
//                sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
//                cleaner.clean();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//
//        //        mapBuffer.clear();
//        Cleaner cleaner = Cleaner.create(buffer, () -> {
//
//        });
//        cleaner.clean();
    }

    @Test
    @SneakyThrows
    public void read2() {

        int men_map_size = (1 << 20) * (5 << 2);
        RandomAccessFile file = new RandomAccessFile("C:\\Users\\Lenovo\\Desktop\\mapped_buffer.txt", "rw");

        MappedByteBuffer buffer = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, men_map_size);

        for (int i = 0; i < men_map_size; i++) {
            buffer.put((byte) 'A');
        }

    }
}
