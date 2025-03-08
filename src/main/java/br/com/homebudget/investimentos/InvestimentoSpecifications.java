package br.com.homebudget.investimentos;

import org.springframework.data.jpa.domain.Specification;

public class InvestimentoSpecifications {
    public static Specification<InvestimentoEntity> byUser(Long user_id){
        return (root, query, criteriaBuilder) -> {
            if (user_id == null){
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + user_id + "%");
        };
    }
}
