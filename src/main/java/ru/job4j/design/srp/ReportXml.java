package ru.job4j.design.srp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате XML по всем полям
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.2
 */
public class ReportXml implements Report {
    private final Store store;

    public ReportXml(Store store) {
        this.store = store;
    }

    /**
     * Сбор списка работников выполняется в классе Employees
     * Затем экземпляр класса со списком работников сериализуется в XML
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в XML формате
     * @throws JAXBException JAXBContext
     */
    @Override
    public String generate(Predicate<Employee> filter) throws JAXBException {
        StringBuffer sb = new StringBuffer();
        Employees emp = new Employees(filter, store);
        JAXBContext context = JAXBContext.newInstance(Employees.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (StringWriter writer = new StringWriter()) {
                marshaller.marshal(emp, writer);
               sb = writer.getBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}