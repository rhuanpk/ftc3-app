package com.fiaptech2024.fastfood.application.controllers.cliente.create;

import com.fiaptech2024.fastfood.adapters.controllers.ClienteController;
import com.fiaptech2024.fastfood.application.controllers.cliente.create.requests.ClienteCreateRequest;
import com.fiaptech2024.fastfood.application.infra.AuthenticationService;
import com.fiaptech2024.fastfood.core.applications.cliente.repositories.ClienteRepositoryInterface;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("clientes")
@AllArgsConstructor
public class CreateClienteController {

    private final ClienteRepositoryInterface clienteRepositoryInterface;
    private final AuthenticationService authenticationService;

    @PostMapping
    @Operation(tags = "Clientes")
    public ResponseEntity<Object> create(@RequestBody ClienteCreateRequest request) {
        UUID clienteID = UUID.fromString(authenticationService.authenticate(request.nome(), request.cpf(), request.email()));
        ClienteController clienteController = new ClienteController(this.clienteRepositoryInterface);
        return new ResponseEntity<>(clienteController.salvar(clienteID, request.nome(), request.cpf(), request.email()), HttpStatus.OK);
    }

}
