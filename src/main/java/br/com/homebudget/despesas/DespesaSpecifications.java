package br.com.homebudget.despesas;

import org.springframework.data.jpa.domain.Specification;

public class DespesaSpecifications {
    public static Specification<DespesaEntity> byUser(Long user_id){
        return (root, query, criteriaBuilder) -> {
         if (user_id == null){
             return null;
         }
         return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + user_id + "%");
        };
    }
}
