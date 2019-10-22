import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CSVTester {
    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/cars.csv";
        //try with resources
        try (
                Reader reader = Files.newBufferedReader(Paths.get(fileName));
        ) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(Car.class);//jakiej klasy obiekty w zawartosc pliku
            String[] memberFieldsToBindTo = {"id", "name", "price"};//nazwa pól klasy wedlug kolejnosci w pliku csv
            strategy.setColumnMapping(memberFieldsToBindTo);
            //zamiana csv na Car
            //skip line 1 (naglowek)
            //ignore leading white spaces
            CsvToBean<Car> csvToBean = new CsvToBeanBuilder(reader).withMappingStrategy(strategy).withSkipLines(1).withIgnoreLeadingWhiteSpace(true).build();
            //iterator po obiektach car
            Iterator<Car> carIterator = csvToBean.iterator();
            while (carIterator.hasNext()) {
                Car car = carIterator.next();
                System.out.println("Id: " + car.getId());
                System.out.println("Name: " + car.getName());
                System.out.println("Price: " + car.getPrice());
                System.out.println("--------------------------");
            }
        }

    }
}
