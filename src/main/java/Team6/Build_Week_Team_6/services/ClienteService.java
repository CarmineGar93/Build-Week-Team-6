package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.ClienteDTO;
import Team6.Build_Week_Team_6.dto.UpdateUltimoContattoDTO;
import Team6.Build_Week_Team_6.entities.Cliente;
import Team6.Build_Week_Team_6.entities.Indirizzo;
import Team6.Build_Week_Team_6.enums.TipoCliente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IndirizzoService indirizzoService;
    @Autowired
    private List<DateTimeFormatter> formatters;

    public List<Cliente> findAllClienti(Double fatturatoAnnuale, String inserimento,
                                        String ultimoContatto, String ragioneSociale, String sortBy) {
        String direction = "ASC";
        if (sortBy.equals("fatturatoAnnuale") || sortBy.equals("dataInserimento") || sortBy.equals("dataUltimoContatto"
        )) {
            direction = "DESC";
        }
        if (sortBy.equals("provincia")) sortBy = "indirizzoSedeLegale.comune.provincia.nome";
        if (fatturatoAnnuale == null && inserimento == null && ultimoContatto == null && ragioneSociale == null)
            return clienteRepository.findAll(Sort.by(direction.equalsIgnoreCase("ASC") ?
                    Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        Specification<Cliente> specification = Specification.where(null);
        if (fatturatoAnnuale != null) {
            specification =
                    specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("fatturatoAnnuale"), fatturatoAnnuale)));
        }
        if (inserimento != null) {
            LocalDate dataInserimento = validateDate(inserimento);
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(
                    "dataInserimento"), dataInserimento)));
        }
        if (ultimoContatto != null) {
            LocalDate dataUltimoContatto = validateDate(ultimoContatto);
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(
                    "dataUltimoContatto"), dataUltimoContatto)));
        }
        if (ragioneSociale != null) {
            specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(
                    "ragioneSociale"), ragioneSociale)));
        }
        return clienteRepository.findAll(specification, Sort.by(direction.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
    }

    public Cliente findSingleClienteById(UUID clienteId) {
        return clienteRepository.findById(clienteId).orElseThrow(() -> new NotFoundException("Cliente con id " + clienteId + " non trovato"));
    }

    public Cliente salvaCLiente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByEmail(clienteDTO.email()))
            throw new BadRequestException("Email cliente già in uso");
        if (clienteRepository.existsByPec(clienteDTO.pec())) throw new BadRequestException("Pec cliente già in uso");
        if (clienteRepository.existsByTelefono(clienteDTO.telefono()))
            throw new BadRequestException("Telefono cliente già in uso");
        if (clienteRepository.existsByPartitaIva(clienteDTO.partitaIva()))
            throw new BadRequestException("Partita IVA già in uso");
        if (clienteRepository.existsByRagioneSociale(clienteDTO.ragioneSociale()))
            throw new BadRequestException("Ragione sociale già in uso");
        Indirizzo indirizzoSedeLegale = indirizzoService.findById(clienteDTO.indirizzoSedeLegale());
        Indirizzo indirizzoSedeOperativa = indirizzoService.findById(clienteDTO.indirizzoSedeOperativa());
        TipoCliente tipoCliente = TipoCliente.valueOf(clienteDTO.tipoCliente());
        Cliente cliente = new Cliente(clienteDTO.ragioneSociale(), clienteDTO.partitaIva(), clienteDTO.email(),
                clienteDTO.pec(), clienteDTO.telefono(), clienteDTO.emailContatto(), clienteDTO.nomeContatto(),
                clienteDTO.cognomeContatto(), clienteDTO.telefonoContatto(), tipoCliente, indirizzoSedeLegale,
                indirizzoSedeOperativa);
        return clienteRepository.save(cliente);
    }

    public Cliente findClienteByIdAndUpdate(UUID clienteId, ClienteDTO clienteDTO) {
        if (clienteRepository.existsByEmail(clienteDTO.email()))
            throw new BadRequestException("Email cliente già in uso");
        if (clienteRepository.existsByPec(clienteDTO.pec())) throw new BadRequestException("Pec cliente già in uso");
        if (clienteRepository.existsByTelefono(clienteDTO.telefono()))
            throw new BadRequestException("Telefono cliente già in uso");
        if (clienteRepository.existsByPartitaIva(clienteDTO.partitaIva()))
            throw new BadRequestException("Partita IVA già in uso");
        if (clienteRepository.existsByRagioneSociale(clienteDTO.ragioneSociale()))
            throw new BadRequestException("Ragione sociale già in uso");
        Indirizzo indirizzoSedeLegale = indirizzoService.findById(clienteDTO.indirizzoSedeLegale());
        Indirizzo indirizzoSedeOperativa = indirizzoService.findById(clienteDTO.indirizzoSedeOperativa());
        TipoCliente tipoCliente = TipoCliente.valueOf(clienteDTO.tipoCliente());
        Cliente clienteDaModificare = findSingleClienteById(clienteId);
        clienteDaModificare.setRagioneSociale(clienteDTO.ragioneSociale());
        clienteDaModificare.setPartitaIva(clienteDTO.partitaIva());
        clienteDaModificare.setEmail(clienteDTO.email());
        clienteDaModificare.setPec(clienteDTO.pec());
        clienteDaModificare.setTelefono(clienteDTO.telefono());
        clienteDaModificare.setEmailContatto(clienteDTO.emailContatto());
        clienteDaModificare.setNomeContatto(clienteDTO.nomeContatto());
        clienteDaModificare.setCognomeContatto(clienteDTO.cognomeContatto());
        clienteDaModificare.setTelefonoContatto(clienteDTO.telefonoContatto());
        clienteDaModificare.setTipoCliente(tipoCliente);
        clienteDaModificare.setIndirizzoSedeLegale(indirizzoSedeLegale);
        clienteDaModificare.setIndirizzoSedeOperativa(indirizzoSedeOperativa);
        return clienteRepository.save(clienteDaModificare);
    }

    public void findClienteByIdAndDelete(UUID clienteId) {
        Cliente cliente = findSingleClienteById(clienteId);
        clienteRepository.delete(cliente);
    }

    public Cliente updateUltimoContatto(UUID cliente, UpdateUltimoContattoDTO body) {
        Cliente cercato = findSingleClienteById(cliente);
        LocalDate data = validateDate(body.ultimoContatto());
        if (data.isBefore(cercato.getDataInserimento()))
            throw new BadRequestException("La data dell'ultimo contatto non puo venire prima della data inserimento " +
                    "cliente");
        if (data.isAfter(LocalDate.now()))
            throw new BadRequestException("La data non può essere nel futuro");
        cercato.setDataUltimoContatto(data);
        return clienteRepository.save(cercato);
    }

    private LocalDate validateDate(String date) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate dataFormattata = LocalDate.parse(date, formatter);
                return dataFormattata;
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new BadRequestException("Formato data non supportato");
    }
}
