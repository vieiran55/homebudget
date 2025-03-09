package br.com.homebudget.receitas.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FonteReceitaEnum {
    SALARIO("Salário"),
    FREELANCE("Freelance"),
    BENEFICIOS("Benefícios"),
    INVESTIMENTOS("Investimentos"),
    EMPRESTIMOS("Empréstimo"),
    OUTROS("Outros");

    private final String descricao;

    FonteReceitaEnum(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String toString() {
        return descricao;
    }
}
