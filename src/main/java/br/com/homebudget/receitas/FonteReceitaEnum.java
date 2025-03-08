package br.com.homebudget.receitas;

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
}
