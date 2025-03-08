package br.com.homebudget.investimentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvestimentoRepository extends JpaRepository<InvestimentoEntity, Long>, JpaSpecificationExecutor<InvestimentoEntity> {
}
