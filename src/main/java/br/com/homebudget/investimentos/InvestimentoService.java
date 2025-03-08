package br.com.homebudget.investimentos;


import br.com.homebudget.investimentos.dto.InvestimentoDTO;
import br.com.homebudget.investimentos.dto.InvestimentoInputDTO;
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
public class InvestimentoService {

    private final InvestimentoRepository investimentoRepository;
    private final InvestimentoMapper investimentoMapper;
    private final UserRepository userRepository;

    public Page<InvestimentoDTO> getAll(Long user_id, Pageable pageable) {
        Specification<InvestimentoEntity> spec = Specification.where(InvestimentoSpecifications.byUser(user_id));

        return investimentoRepository.findAll(spec, pageable)
                .map(investimentoMapper::toDto);
    }

    public InvestimentoEntity getById(Long id) {
        return investimentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
    }

    @Transactional
    public InvestimentoEntity create(@Valid InvestimentoInputDTO investimentoInputDTO){

        boolean userExists = userRepository.existsById(investimentoInputDTO.userId());

        if (!userExists) {
            throw new NotFoundException("Usuário com ID " + investimentoInputDTO.userId() + " não encontrado.");
        }

        InvestimentoEntity receitaEntity = InvestimentoMapper.INSTANCE.toEntity(investimentoInputDTO);
        return investimentoRepository.save(receitaEntity);
    }

    @Transactional
    public InvestimentoEntity update (Long id, InvestimentoInputDTO investimentoInputDTO){
        InvestimentoEntity entity = investimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receita não encontrada"));

        investimentoMapper.updateEntityFromDto(investimentoInputDTO, entity);
        return investimentoRepository.save(entity);
    }

    @Transactional
    public void delete(Long id){
        InvestimentoEntity entity = investimentoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não econtrada"));
        investimentoRepository.deleteById(entity.getId());
    }
}
