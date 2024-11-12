package Team6.Build_Week_Team_6.controllers;

import Team6.Build_Week_Team_6.dto.ClienteDTO;
import Team6.Build_Week_Team_6.entities.Cliente;
import Team6.Build_Week_Team_6.exceptions.BadRequestException;
import Team6.Build_Week_Team_6.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClienti() {
        return clienteService.findAllClienti();
    }

    @GetMapping("/{clienteId}")
    public Cliente getCliente(@PathVariable UUID clienteId) {
        return clienteService.findSingleClienteById(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvaCliente(@RequestBody @Validated ClienteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return clienteService.salvaCLiente(body);
    }

    @PutMapping("/{clienteId}")
    public Cliente modificaCliente(@PathVariable UUID clienteId, @RequestBody @Validated ClienteDTO body,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return clienteService.findClienteByIdAndUpdate(clienteId, body);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaCliente(@PathVariable UUID clienteId) {
        clienteService.findClienteByIdAndDelete(clienteId);
    }

}