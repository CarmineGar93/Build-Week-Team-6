package Team6.Build_Week_Team_6.runners;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.services.ComuniService;
import Team6.Build_Week_Team_6.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class InizializzaDbRunner implements CommandLineRunner {
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ProvinceService provinceService;

    @Override
    public void run(String... args) {
        if (provinceService.findAllProvince().isEmpty()) {
            try {
                populateProvince();
            } catch (Exception e) {
                System.out.println("Qualcosa è andato storto nella popolazione del db");
            }

        }

        if (comuniService.findAllComuni().isEmpty()) {
            try {
                populateComuni();
            } catch (Exception e) {
                System.out.println("Qualcosa è andato storto nella popolazione del db");
            }
        }

    }

    private void populateComuni() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("comuni-italiani.csv"));
        reader.readLine();
        List<String> broken = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            String[] splitted = line.split(";");
            try {
                Provincia provincia = provinceService.findProvinciaByNome(splitted[3]);
                Comune comune = new Comune(splitted[2], provincia);
                comuniService.saveComune(comune);
            } catch (NotFoundException e) {
                broken.add(line);
            }
            line = reader.readLine();
        }
        if (!broken.isEmpty()) {
            broken.forEach(s -> {
                String[] splitted = s.split(";");
                try {
                    Provincia provincia = provinceService.findProvinciaByNomeStartingWith(splitted[3].substring(0, 3));
                    Comune comune = new Comune(splitted[2], provincia);
                    comuniService.saveComune(comune);
                } catch (NotFoundException e) {
                    System.out.println(splitted[3]);
                    System.out.println("Boh");

                }
            });
        }
    }

    private void populateProvince() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("province-italiane.csv"));
        reader.readLine();
        List<String> broken = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            String[] splitted = line.split(";");
            if (splitted[0].length() != 2) {
                broken.add(line);
            } else {
                Provincia provincia = new Provincia(splitted[1], splitted[0]);
                provinceService.saveProvincia(provincia);
            }
            line = reader.readLine();
        }
        if (!broken.isEmpty()) {
            broken.forEach(s -> {
                String[] splitted = s.split(";");
                int i = 1;
                while (true) {
                    String provaSigla = "" + splitted[1].charAt(0) + splitted[1].charAt(i);
                    try {
                        Provincia provincia = new Provincia(splitted[1], provaSigla.toUpperCase());
                        provinceService.saveProvincia(provincia);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getClass());
                        i++;
                    }
                }
            });
        }
    }
}
