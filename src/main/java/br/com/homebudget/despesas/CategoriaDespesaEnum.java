package br.com.homebudget.despesas;

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

    private  final String descricao;

    CategoriaDespesaEnum(String descricao){
        this.descricao = descricao;
    }
}
