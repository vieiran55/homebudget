package br.com.homebudget.receitas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceitaRepository extends JpaRepository<ReceitaEntity, Long>, JpaSpecificationExecutor<ReceitaEntity> {

    @Query("""
        SELECT d FROM ReceitaEntity d 
        WHERE d.user.id = :userId
        AND (
            YEAR(d.data) < :anoAtual
            OR (YEAR(d.data) = :anoAtual AND MONTH(d.data) <= :mesAtual)
        )
    """)
    Page<ReceitaEntity> findPastUntilCurrentMonth(
            @Param("userId") Long userId,
            @Param("anoAtual") int anoAtual,
            @Param("mesAtual") int mesAtual,
            Pageable pageable
    );
}
