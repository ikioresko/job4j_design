package ru.job4j.design.srp;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.function.Predicate;

/**
 * Класс генерирует полный отчет в формате XML по всем полям
 * При создании экземпляра этого класса необходимо передать в конструктор объект
 * имплементирующий интерфейс Store, для получения списка сотрудников из БД
 *
 * @author Kioresko Igor
 * @version 0.1
 */
public class ReportXml implements Report {
    private final Store store;

    public ReportXml(Store store) {
        this.store = store;
    }

    /**
     * Собирает список сотрудников по предикату через интерфейс Store
     * Генерирует полный отчет в формате XML по всем полям
     *
     * @param filter Предикат для выборки работников при сборе из БД
     * @return Отчет в XML формате
     * @throws JAXBException JAXBContext
     */
    @Override
    public String generate(Predicate<Employee> filter) throws JAXBException {
        StringBuilder text = new StringBuilder();
        List<Employee> empList = store.findBy(filter);
        JAXBContext context = JAXBContext.newInstance(Employee.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (StringWriter writer = new StringWriter()) {
            for (Employee emp : empList) {
                marshaller.marshal(emp, writer);
                text.append(writer.getBuffer().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}