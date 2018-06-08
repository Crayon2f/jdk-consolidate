package com.crayon2f.jdk.consolidate.base;

import com.crayon2f.jdk.consolidate.pojo.PojoForSerial;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by feiFan.gou on 2018/6/8 16:55.
 */
public class $Serializable {

    @Test
    @SneakyThrows
    public void serialize() {

        PojoForSerial serial = new PojoForSerial("jack", 80);

        @Cleanup
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\serial.txt"));
        oos.writeObject(serial);
        System.out.println("serial success");
    }

    @Test
    @SneakyThrows
    public void deserialize() {

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Lenovo\\Desktop\\serial.txt"));
        PojoForSerial serial = (PojoForSerial) ois.readObject();
        System.out.println("reverse success");
        System.out.println(serial);
    }
}
