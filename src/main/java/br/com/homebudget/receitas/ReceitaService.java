package br.com.homebudget.receitas;


import br.com.homebudget.receitas.dto.ReceitaDTO;
import br.com.homebudget.receitas.dto.ReceitaInputDTO;
import br.com.homebudget.shared.exceptions.NotFoundException;
import br.com.homebudget.users.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ReceitaMapper receitaMapper;
    private final UserRepository userRepository;

    public Page<ReceitaDTO> getAll(Long user_id, Pageable pageable) {
        Specification<ReceitaEntity> spec = Specification.where(ReceitaSpecifications.byUser(user_id));

        return receitaRepository.findAll(spec, pageable)
                .map(receitaMapper::toDto);
    }

    public ReceitaEntity getById(Long id) {
        return receitaRepository.findById(id).orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
    }

    @Transactional
    public ReceitaEntity create(@Valid ReceitaInputDTO receitaInputDTO){

        boolean userExists = userRepository.existsById(receitaInputDTO.userId());

        if (!userExists) {
            throw new NotFoundException("Usuário com ID " + receitaInputDTO.userId() + " não encontrado.");
        }

        ReceitaEntity receitaEntity = ReceitaMapper.INSTANCE.toEntity(receitaInputDTO);
        return receitaRepository.save(receitaEntity);
    }

    @Transactional
    public ReceitaEntity update (Long id, ReceitaInputDTO receitaInputDTO){
        ReceitaEntity entity = receitaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receita não encontrada"));

        receitaMapper.updateEntityFromDto(receitaInputDTO, entity);
        return receitaRepository.save(entity);
    }

    @Transactional
    public void delete(Long id){
        ReceitaEntity entity = receitaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não econtrada"));
        receitaRepository.deleteById(entity.getId());
    }
}
