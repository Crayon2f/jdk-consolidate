package com.crayon2f.jdk.consolidate.nio.channel;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by feiFan.gou on 2018/5/8 11:23.
 */

public class FileChannelTest extends Base {

    @Test(timeout = 1000L * 6)
    public void test() throws InterruptedException {

        Thread.sleep(5000);
        System.out.println("=================");
    }

    @Test
    public void byteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.position());
        String string = "buffer char short boolean int long float double";
        System.out.println(buffer.get());
        System.out.println(string.length());
        buffer.put(string.getBytes());
        System.out.println(buffer.position());

        System.out.println(new String(buffer.array()));
    }

    @Test
    public void read() {

        File file = new File(file_path);
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) > 0) {
                buffer.flip();
                byte[] bytes = new byte[100];
                while (buffer.hasRemaining()) {
                    int length = Math.min(buffer.remaining(), bytes.length);
                    buffer.get(bytes, 0, length);
                    System.out.print(new String(bytes));
                }
                buffer.clear();
            }
            System.out.println(channel.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try (FileInputStream stream = new FileInputStream(file)) {
//            byte[] bytes = new byte[1024];
//            int temp;
//            while (((temp = stream.read(bytes)) != -1)) {
//                stream.
//            }
//        } catch (IOException e) {
//
//        }
    }

    @Test
    public void write() {

        File file = new File(file_path_2);
        try (FileChannel channel = new FileOutputStream(file).getChannel()) {
            String text = getText();
            byte[] bytes = text.getBytes(UTF_8);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void copy() {

        try (FileChannel sourceChannel = new FileInputStream(large_file_path).getChannel();
             FileChannel targetChannel = new FileOutputStream(desktop_dir + File.separator + "temp2.pdf").getChannel()
        ) {
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
//            targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            System.out.println("copy complete !");
            sourceChannel.close(); //关闭后，才可删除源文件
            Files.deleteIfExists(Paths.get(large_file_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @SneakyThrows
    public void holeFile() {

        File temp = File.createTempFile("hole", null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        @Cleanup
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(100);
        putData(0, buffer, channel);
        putData(5000000, buffer, channel);
        putData(50000, buffer, channel);

        System.out.printf("Wrote temp file '%s', size = %d", temp.getPath(), channel.size());

    }

    @Test
    @SneakyThrows
    public void position() {

        RandomAccessFile randomAccessFile = new RandomAccessFile (file_path, "r");
        // Set the file position
        randomAccessFile.seek (1000);
        // Create a channel from the file
        FileChannel fileChannel = randomAccessFile.getChannel( );
        // This will print "1000"
        System.out.println ("file pos: " + fileChannel.position( ));
        // Change the position using the RandomAccessFile object
        randomAccessFile.seek (500);
        // This will print "500"
        System.out.println ("file pos: " + fileChannel.position( ));
        // Change the position using the FileChannel object
        fileChannel.position (200);
        // This will print "200"
        System.out.println ("file pos: " + randomAccessFile.getFilePointer( ));
    }


    @SneakyThrows
    private void putData(int position, ByteBuffer byteBuffer, FileChannel channel) {

        String string = "*<-- location " + position;
        byteBuffer.clear();
        byteBuffer.put(string.getBytes(Charset.defaultCharset()));
        byteBuffer.flip();
        channel.position(position);
        channel.write(byteBuffer);
    }

    private String getText() {

        String property = System.getProperty("line.separator");
        Stream.Builder<String> builder = Stream.builder();
        for (int i = 0; i < 6; i++) {
            System.out.println(property.equals("\r\n"));
            System.out.println(property);
            builder.add("8").add(property).add("测试");
        }
        return String.join("", builder.build().collect(Collectors.toList()));
    }

    @Test
    @SneakyThrows
    public void force() {

        FileChannel channel = new FileOutputStream(empty_file_path).getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put("this is force =====".getBytes(Charset.defaultCharset()));

        channel.write(byteBuffer);
    }


}
