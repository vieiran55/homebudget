package br.com.homebudget.receitas;

import org.springframework.data.jpa.domain.Specification;

public class ReceitaSpecifications {
    public static Specification<ReceitaEntity> byUser(Long user_id){
        return (root, query, criteriaBuilder) -> {
            if (user_id == null){
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + user_id + "%");
        };
    }
}
