package com.crayon2f.jdk.consolidate.nio.buffer;

import com.crayon2f.jdk.consolidate.nio.base.Base;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by feiFan.gou on 2018/5/9 13:33.
 */
class BufferTest extends Base {

    @Test
    void intBuffer() {

        IntBuffer intBuffer = IntBuffer.allocate(15);
        for (int i = 0; i < 11; i++) {
            intBuffer.put(i);
        }
//        intBuffer.flip();
        intBuffer.rewind();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

    @Test
    void buffer() {

        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.printf("capacity ==> %d", buffer.capacity());
        System.out.println();
        System.out.printf("limit ==> %d", buffer.limit());
        System.out.println();
        System.out.printf("position ==> %d", buffer.position());
//        buffer.flip();
        buffer.put(1).put(1).put(1);
        System.out.println();

    }

    private static int index = 0;
    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
            "Help Me! Help Me!",
    };

    @Test
    void test() {

        CharBuffer buffer = CharBuffer.allocate(128);
        while (fill(buffer)) {
            buffer.flip();
            drain(buffer);
            buffer.clear();
        }

    }

    private boolean fill(CharBuffer buffer) {

        if (index >= strings.length) {
            return false;
        }
        String string = strings[index++];
        for (int i = 0, length = string.length(); i < length; i++) {
            buffer.put(string.charAt(i));
        }
        return true;
    }

    private void drain(CharBuffer buffer) {

        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();
    }

    /**
     * 这里发生了几件事。您会看到数据元素 2-5 被复制到 0-3 位置。位置 4 和 5 不受影响，
     * 但现在正在或已经超出了当前位置，因此是“死的”。它们可以被之后的 put()调用重写。
     * 还要注意的是，位置已经被设为被复制的数据元素的数目。也就是说，缓冲区现在被定位在缓
     * 冲区中最后一个“存活”元素后插入数据的位置。最后，上界属性被设置为容量的值，因此缓
     * 冲区可以被再次填满。调用 compact()的作用是丢弃已经释放的数据，保留未释放的数据，
     * 并使缓冲区对重新填充容量准备就绪。
     */
    @Test
    void compat() {

        IntBuffer buffer = IntBuffer.allocate(100);
        for (int i = 0; i < 10; i++) {
            buffer.put(i);
        }
        buffer.flip();
        for (int i = 0; buffer.hasRemaining(); i++) {
            if (i == 4) {
                buffer.compact(); // start from capacity - current_position
            }
            System.out.println(buffer.get());
        }
    }

    @Test
    void batchMove() {

        CharBuffer buffer = CharBuffer.allocate(1024);
        for (String string : strings) {
            buffer.put(string);
        }
//        buffer.flip();
//        while (buffer.hasRemaining()) {
//            System.out.print(buffer.get());
//        }
        System.out.println(buffer.remaining()); //limit - position

    }

    @Test
    void init() {

        val ints = new int[30];
        for (int i = 0, length = ints.length; i < length; i++) {
            ints[i] = i;
        }
        //buffer put 会影响数组
        System.out.println(Arrays.toString(ints));
        IntBuffer buffer = IntBuffer.wrap(ints);
        buffer.put(30);
        System.out.println(Arrays.toString(ints));


        //对数组更改,buffer也会更改
        ints[3] = 100;
        System.out.println(buffer.get(3));

        //获取数组
        System.out.println(buffer.hasArray());
        System.out.println(Arrays.toString(buffer.array()));
        System.out.println(buffer.arrayOffset()); //数组中哪些元素是被缓冲区使用的
        System.out.println(Arrays.toString(buffer.array()));


        CharBuffer helloWorld = CharBuffer.wrap("hello world");
        System.out.println(helloWorld.get(0)); //h

        System.out.println(CharBuffer.wrap("hello world ", 0, 4).length()); //4


    }

    @Test
    void duplicate() {

        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("1234567890");
        CharBuffer duplicateBuffer = buffer.duplicate();
        //对一个缓冲区内的数据元素所做的改变会反映在另外一个缓冲区上, 这是为啥呀 ？？？？？
        duplicateBuffer.put("string");
        duplicateBuffer.position(2).limit(6).mark().position(5);
        buffer.clear();
    }

    @Test
    void asReadOnlyBuffer() {

        int[] ints = {1, 2, 3, 4, 5, 6};
        IntBuffer buffer = IntBuffer.wrap(ints);
        IntBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        IntBuffer readOnlyBuffer1 = buffer.asReadOnlyBuffer();

        /*
         * 对这个可写的缓冲区或直接对这个数组的改变将反映在
         * 所有关联的缓冲区上，包括只读缓冲区。
         */
        ints[4] = 44;
//        buffer.put(33);

        //System.out.println(Arrays.toString(readOnlyBuffer.array())); //ReadOnlyBufferException
        while (readOnlyBuffer.hasRemaining()) {
            System.out.print(readOnlyBuffer.get() + " ");
        }

        int i = 58_700_999;

        System.out.println(i);

    }

    @Test
    @SneakyThrows
    void remind() {

        ByteBuffer buffer = ByteBuffer.allocate(100);
        String string = "this is remind ===========";
        buffer.put(string.getBytes(Charset.defaultCharset()));
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        buffer.rewind();
        FileChannel channel = FileChannel.open(null);
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        //Rewind()函数与 flip()相似，但不影响上界属性。它只是将位置值设回 0。您可以使
        //用 rewind()后退，重读已经被翻转的缓冲区中的数据

    }

}
