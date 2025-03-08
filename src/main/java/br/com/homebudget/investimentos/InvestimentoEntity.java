package br.com.homebudget.investimentos;

import br.com.homebudget.despesas.CategoriaDespesaEnumConverter;
import br.com.homebudget.receitas.FonteReceitaEnum;
import br.com.homebudget.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "investimentos")
@Getter
@Setter
@NoArgsConstructor
public class InvestimentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Convert(converter = TipoInvestimentoEnumConverter.class)
    @Column(name = "tipo", length = 255, nullable = false)
    TipoInvestimentoEnum tipo;

    @Column(name = "valor_inicial", precision = 10, scale = 2, nullable = false)
    BigDecimal valorInicial;

    @Column(name = "valor_atual", precision = 10, scale = 2, nullable = false)
    BigDecimal valorAtual;

    @Column(name = "descricao", columnDefinition = "TEXT")
    String descricao;

    @Column(name="data", nullable = false)
    LocalDate data;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    Timestamp updatedAt;
}
