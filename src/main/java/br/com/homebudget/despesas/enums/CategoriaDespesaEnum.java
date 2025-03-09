package br.com.homebudget.despesas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CategoriaDespesaEnum {
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    MORADIA("Moradia"),
    SAUDE("Saúde"),
    LAZER("Lazer"),
    EDUCACAO("Educação"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaDespesaEnum(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String toString() {
        return descricao;
    }
}
