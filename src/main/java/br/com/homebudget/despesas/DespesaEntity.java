package br.com.homebudget.despesas;

import br.com.homebudget.despesas.enums.CategoriaDespesaEnum;
import br.com.homebudget.despesas.enums.CategoriaDespesaEnumConverter;
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
@Table(name = "despesas")
@Getter
@Setter
@NoArgsConstructor
public class DespesaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Convert(converter = CategoriaDespesaEnumConverter.class)
    @Column(name = "categoria", length = 255, nullable = false)
    CategoriaDespesaEnum categoria;

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
