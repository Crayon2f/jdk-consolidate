package com.crayon2f.jdk.consolidate.nio.buffer;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by feiFan.gou on 2018/5/10 20:43.
 */
public class MappedBufferWithScatterAndGatherTest extends Base {

    private static final String OUTPUT_FILE = empty_file_path;
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void main(String[] argv) throws Exception {
//        if (argv.length < 1) {
//            System.err.println("Usage: filename");
//            return;
//        }
        String file = file_path;
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynhdrs, null};
        String contentType = "unknown/unknown";
        long contentLength = -1;
        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer fileData = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            gather[2] = fileData;
            contentLength = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        } catch (IOException e) {
            // file could not be opened; report problem
            ByteBuffer buf = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;
            buf.put(bytes(msg));
            buf.flip();
            // Use the HTTP error response
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buf;
            contentLength = msg.length();
            contentType = "text/plain";
        }
        String sb = ("Content-Length: " + contentLength) +
                LINE_SEP +
                "Content-Type: " + contentType +
                LINE_SEP + LINE_SEP;
        dynhdrs.put(bytes(sb));
        dynhdrs.flip();
        FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
        FileChannel out = fos.getChannel();
        // All the buffers have been prepared; write 'em out
        while (out.write(gather) > 0) {
            // Empty body; loop until all buffers are empty
        }
        out.close();
        System.out.println("output written to " + OUTPUT_FILE);
    }

    // Convert a string to its constituent bytes
    // from the ASCII character set
    private static byte[] bytes(String string){
        return (string.getBytes(Charset.defaultCharset()));
    }

    @Test
    @SneakyThrows
    public void test() {

        @Cleanup
        FileChannel channel = new FileInputStream(file_path).getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        @Cleanup
        FileChannel outChannel = new FileOutputStream(empty_file_path).getChannel();
//        buffer.flip();
        outChannel.write(buffer, 0);
    }
}
