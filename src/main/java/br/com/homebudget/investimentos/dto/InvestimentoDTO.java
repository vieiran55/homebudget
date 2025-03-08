package br.com.homebudget.investimentos.dto;

import br.com.homebudget.investimentos.TipoInvestimentoEnum;
import br.com.homebudget.receitas.FonteReceitaEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestimentoDTO(
        Long id,
        Long userId,
        TipoInvestimentoEnum tipo,
        BigDecimal valorInicial,
        BigDecimal valorAtual,
        String descricao,
        LocalDate data
) {
}
