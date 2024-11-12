package Team6.Build_Week_Team_6.services;

import Team6.Build_Week_Team_6.dto.ClienteDTO;
import Team6.Build_Week_Team_6.entities.Cliente;
import Team6.Build_Week_Team_6.entities.Indirizzo;
import Team6.Build_Week_Team_6.enums.TipoCliente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.exceptions.NotFoundException;
import Team6.Build_Week_Team_6.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IndirizzoService indirizzoService;

    public List<Cliente> findAllClienti() {
        return clienteRepository.findAll();
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

}
