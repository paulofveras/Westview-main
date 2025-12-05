package br.unitins.comics.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Pagamento {
    PIX(1, "Pix"),
    BOLETO(2, "Boleto"),
    CARTAO_CREDITO(3, "Cartão de Crédito"), // Novo
    EXEMPLO_DEMO(4, "Exemplo (Demonstração)"); // Novo para o professor

    private int id;
    private String descricao;

    Pagamento(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Pagamento valueOf(Integer id) throws IllegalArgumentException {
        for (Pagamento pagamento : Pagamento.values()) {
            if (pagamento.id == id)
                return pagamento;
        }
        throw new IllegalArgumentException("id pagamento inválido.");
    }
}