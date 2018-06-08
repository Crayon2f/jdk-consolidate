package com.crayon2f.jdk.consolidate.design.patterns;

import lombok.Data;
import org.junit.Test;

import java.util.Date;

/**
 * Created by feiFan.gou on 2018/2/13 15:42.
 */
public class Prototype {

    @Test
    public void test() {

        Person student = new Student();
        Person doctor = new Doctor();
        System.out.println(student.getName());
        System.out.println(doctor.getName());
    }

}

@Data
abstract class Person {
    private String name;
    private Integer age;
    private Date birthday;

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        return birthday != null ? birthday.equals(person.birthday) : person.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}

class Doctor extends Person {

    public Doctor() {
        setName("doctor");
    }
}

class Student extends Person {

    public Student() {
        setName("student");
    }
}