package Team6.Build_Week_Team_6.runners;

import Team6.Build_Week_Team_6.entities.Comune;
import Team6.Build_Week_Team_6.entities.Provincia;
import Team6.Build_Week_Team_6.entities.RuoloUtente;
import Team6.Build_Week_Team_6.entities.StatoFattura;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.services.ComuneService;
import Team6.Build_Week_Team_6.services.ProvinciaService;
import Team6.Build_Week_Team_6.services.RuoloUtenteService;
import Team6.Build_Week_Team_6.services.StatoFatturaService;
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
    private ComuneService comuneService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private RuoloUtenteService ruoloUtenteService;
    @Autowired
    private StatoFatturaService statoFatturaService;

    @Override
    public void run(String... args) {
        if (provinciaService.findAllProvince().isEmpty()) {
            try {
                populateProvince();
            } catch (Exception e) {
                System.out.println("Qualcosa è andato storto nella popolazione del db");
            }

        }

        if (comuneService.findAllComuni().isEmpty()) {
            try {
                populateComuni();
            } catch (Exception e) {
                System.out.println("Qualcosa è andato storto nella popolazione del db");
            }
        }
        if (ruoloUtenteService.findAll().isEmpty()) populateRuoli();
        if (statoFatturaService.findAll().isEmpty()) populateStati();
        provinciaService.cercaProvinceNonAssociate().forEach(System.out::println);


    }

    private void populateRuoli() {
        RuoloUtente admin = new RuoloUtente("ADMIN");
        RuoloUtente user = new RuoloUtente("USER");
        RuoloUtente contabile = new RuoloUtente("CONTABILE");
        ruoloUtenteService.saveRuoloUtente(admin);
        ruoloUtenteService.saveRuoloUtente(contabile);
        ruoloUtenteService.saveRuoloUtente(user);
    }

    private void populateStati() {
        StatoFattura emessa = new StatoFattura("EMESSA");
        StatoFattura accettata = new StatoFattura("ACCETTATA");
        StatoFattura rifiutata = new StatoFattura("RIFIUTATA");
        StatoFattura pagata = new StatoFattura("PAGATA");
        StatoFattura scaduta = new StatoFattura("SCADUTA");
        statoFatturaService.saveStatoFattura(emessa);
        statoFatturaService.saveStatoFattura(accettata);
        statoFatturaService.saveStatoFattura(pagata);
        statoFatturaService.saveStatoFattura(rifiutata);
        statoFatturaService.saveStatoFattura(scaduta);

    }

    private void populateComuni() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("comuni-italiani.csv"));
        reader.readLine();
        List<String> broken = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            String[] splitted = line.split(";");
            String nomeProvincia = splitted[3].replaceAll(" ", "-");
            if (splitted[3].equals("Reggio nell'Emilia")) nomeProvincia = "Reggio-Emilia";
            else if (splitted[3].equals("Valle d'Aosta/Vallée d'Aoste")) nomeProvincia = "Aosta";
            try {
                Provincia provincia = provinciaService.findProvinciaByNome(nomeProvincia);
                Comune comune = new Comune(splitted[2], provincia);
                comuneService.saveComune(comune);
            } catch (NotFoundException e) {
                broken.add(line);
            }
            line = reader.readLine();
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
                String nomeProvincia = splitted[1].replaceAll(" ", "-");
                String sigla = splitted[0];
                if (nomeProvincia.equals("Carbonia-Iglesias") || nomeProvincia.equals("Medio-Campidano") || nomeProvincia.equals("Ogliastra") || nomeProvincia.equals("Olbia-Tempio")) {
                    nomeProvincia = "Sud-Sardegna";
                    sigla = "SU";
                } else if (nomeProvincia.equals("Bolzano")) nomeProvincia = "Bolzano/Bozen";
                else if (nomeProvincia.equals("Monza-Brianza")) nomeProvincia = "Monza-e-della-Brianza";
                else if (nomeProvincia.equals("Pesaro-Urbino")) nomeProvincia = "Pesaro-e-Urbino";
                else if (nomeProvincia.equals("Verbania")) nomeProvincia = "Verbano-Cusio-Ossola";
                else if (nomeProvincia.equals("Forli-Cesena")) nomeProvincia = "Forlì-Cesena";
                try {
                    Provincia provincia = new Provincia(nomeProvincia, sigla);
                    provinciaService.saveProvincia(provincia);
                } catch (Exception ignored) {

                }

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
                        provinciaService.saveProvincia(provincia);
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
