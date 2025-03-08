package br.com.homebudget.receitas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReceitaRepository extends JpaRepository<ReceitaEntity, Long>, JpaSpecificationExecutor<ReceitaEntity> {
}
