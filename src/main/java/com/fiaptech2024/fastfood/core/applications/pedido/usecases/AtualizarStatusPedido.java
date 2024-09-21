package com.fiaptech2024.fastfood.core.applications.pedido.usecases;

import com.fiaptech2024.fastfood.core.applications.exception.EntityNotFoundException;
import com.fiaptech2024.fastfood.core.applications.pedido.repositories.PedidoRepositoryInterace;
import com.fiaptech2024.fastfood.core.domain.pedido.Pedido;
import com.fiaptech2024.fastfood.core.domain.pedido.enums.StatusPedido;

import java.util.UUID;

public class AtualizarStatusPedido {

    private final PedidoRepositoryInterace pedidoRepository;

    public AtualizarStatusPedido(PedidoRepositoryInterace pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(UUID id, StatusPedido statusPedido) {
        Pedido pedido = this.pedidoRepository.getById(id);
        if (pedido == null) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        pedido.setStatus(statusPedido);
        return this.pedidoRepository.atualizarStatus(pedido);
    }

}
