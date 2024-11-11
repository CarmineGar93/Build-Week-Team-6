package Team6.Build_Week_Team_6.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class InizializzaDbRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
       /* BufferedReader reader = new BufferedReader(new FileReader("province-italiane.csv"));
        reader.readLine();
        String line = reader.readLine();
        while (line != null) {
            String[] splitted = line.split(";");
            if (splitted[0].length() != 2) {
                while (true) {
                    int i = 1;
                    String prova = "" + splitted[0].charAt(0) + splitted[0].charAt(1);
                    try {
                        Provincia provincia = new Provincia(splitted[1], prova);
                        break;
                    } catch (Exception e) {
                        i++;
                    }
                }

            }
            Provincia provincia = new Provincia(splitted[1], splitted[0]);
            System.out.println(provincia);
            line = reader.readLine();
        }*/
    }
}
