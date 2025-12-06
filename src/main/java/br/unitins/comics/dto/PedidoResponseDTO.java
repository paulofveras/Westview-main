package br.unitins.comics.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.unitins.comics.model.Pagamento;
import br.unitins.comics.model.Pedido;
import br.unitins.comics.model.Status;

public record PedidoResponseDTO(
    Long id,
    LocalDateTime data, // <--- CAMPO NOVO
    ClienteResponseDTO cliente,
    Double total,
    List<ItemPedidoResponseDTO> itens,
    Pagamento pagamento,
    String chavePagamento,
    Status statusPagamento
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        List<ItemPedidoResponseDTO> lista = pedido.getItens()
                                            .stream()
                                            .map(ItemPedidoResponseDTO::valueOf)
                                            .toList();
        return new PedidoResponseDTO(
            pedido.getId(), 
            pedido.getData(), // <--- ENVIANDO A DATA AGORA
            ClienteResponseDTO.valueOf(pedido.getCliente()),
            pedido.getTotal(),
            lista,
            pedido.getFormaPagamento(),
            String.valueOf(UUID.randomUUID()),
            pedido.getStatusPagamento());
    }
}