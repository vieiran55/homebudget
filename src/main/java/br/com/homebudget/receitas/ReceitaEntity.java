package br.com.homebudget.receitas;

import br.com.homebudget.despesas.CategoriaDespesaEnum;
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
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
public class ReceitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "fonte", length = 255, nullable = false)
    FonteReceitaEnum fonte;

    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    BigDecimal valor;

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
