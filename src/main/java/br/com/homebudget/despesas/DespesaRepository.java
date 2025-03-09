package br.com.homebudget.despesas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DespesaRepository extends JpaRepository<DespesaEntity, Long>, JpaSpecificationExecutor<DespesaEntity> {

    @Query("""
        SELECT d FROM DespesaEntity d 
        WHERE d.user.id = :userId
        AND (
            YEAR(d.data) < :anoAtual
            OR (YEAR(d.data) = :anoAtual AND MONTH(d.data) <= :mesAtual)
        )
    """)
    Page<DespesaEntity> findPastUntilCurrentMonth(
            @Param("userId") Long userId,
            @Param("anoAtual") int anoAtual,
            @Param("mesAtual") int mesAtual,
            Pageable pageable
    );
}
