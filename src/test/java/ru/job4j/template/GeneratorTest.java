package ru.job4j.template;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GeneratorTest {
//    @Test
//    public void whenAllDone() {
//        Generator gen = new GeneratorString();
//        String template = "I am a ${name}, Who are ${subject}?";
//        Map<String, String> map = Map.of("name", "Semen", "subject", "you");
//        String rsl = gen.produce(template, map);
//        assertThat(rsl, is("I am a Semen, Who are you?"));
//    }
//
//    @Test (expected = Exception.class)
//    public void whenKeyIsAbsentInMap() {
//        Generator gen = new GeneratorString();
//        String template = "I am a ${name}, Who are ${subject}?";
//        Map<String, String> map = Map.of("user", "Semen", "subject", "you");
//        String rsl = gen.produce(template, map);
//    }
//
//    @Test (expected = Exception.class)
//    public void whenRedundantKeysInMap() {
//        Generator gen = new GeneratorString();
//        String template = "I am a ${name}, Who are ${subject}?";
//        Map<String, String> map = Map.of("user", "Semen", "subject", "you", "name", "Ivan");
//        String rsl = gen.produce(template, map);
//    }
//
//    @Test (expected = Exception.class)
//    public void whenMapIsEmpty() {
//        Generator gen = new GeneratorString();
//        String template = "I am a ${name}, Who are ${subject}?";
//        Map<String, String> map = new HashMap<>();
//        String rsl = gen.produce(template, map);
//    }
//
//    @Test (expected = Exception.class)
//    public void whenMapHaveNull() {
//        Generator gen = new GeneratorString();
//        String template = "I am a ${name}, Who are ${subject}?";
//        Map<String, String> map = new HashMap<>();
//        map.put(null, "Ivan");
//        map.put("subject", "you");
//        String rsl = gen.produce(template, map);
//    }
//
//    @Test (expected = Exception.class)
//    public void whenStringIsEmpty() {
//        Generator gen = new GeneratorString();
//        String template = "";
//        Map<String, String> map = Map.of("name", "Semen", "subject", "you");
//        String rsl = gen.produce(template, map);
//    }
//
//    @Test (expected = Exception.class)
//    public void whenStringIsNull() {
//        Generator gen = new GeneratorString();
//        Map<String, String> map = Map.of("name", "Semen", "subject", "you");
//        String rsl = gen.produce(null, map);
//    }
}