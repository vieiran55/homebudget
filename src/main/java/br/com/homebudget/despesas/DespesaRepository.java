package br.com.homebudget.despesas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DespesaRepository extends JpaRepository<DespesaEntity, Long>, JpaSpecificationExecutor<DespesaEntity>
{
}
