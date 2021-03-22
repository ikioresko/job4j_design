package ru.job4j.serialization.json;

import com.sun.xml.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlAttribute;

@XmlElement(value = "contact")
public class Children {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private int age;

    public Children() {
    }

    public Children(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Children{"
                + "name='" + name + '\''
                + ", age=" + age + '}';
    }
}
