package com.fiaptech2024.fastfood.core.applications.cliente.usecases;

import com.fiaptech2024.fastfood.core.applications.cliente.repositories.ClienteRepositoryInterface;
import com.fiaptech2024.fastfood.core.domain.cliente.Cliente;

import java.time.Instant;
import java.util.UUID;

public class SalvarCliente {

    private final ClienteRepositoryInterface clienteRepository;

    public SalvarCliente(ClienteRepositoryInterface clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente execute(UUID clienteID, String nome, String cpf, String email) {
        Cliente cliente = new Cliente(clienteID, nome, cpf, email, Instant.now());
        return this.clienteRepository.saveCliente(cliente);
    }

}
