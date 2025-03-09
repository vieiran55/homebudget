package br.com.homebudget.despesas;

import br.com.homebudget.despesas.dto.DespesaDTO;
import br.com.homebudget.despesas.dto.DespesaInputDTO;
import br.com.homebudget.shared.exceptions.NotFoundException;
import br.com.homebudget.users.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final DespesaMapper despesaMapper;
    private final UserRepository userRepository;

    public Page<DespesaDTO> getAll(Long user_id, Pageable pageable) {
        Specification<DespesaEntity> spec = Specification.where(DespesaSpecifications.byUser(user_id));

        return despesaRepository.findAll(spec, pageable)
                .map(despesaMapper::toDto);
    }

    public DespesaEntity getById(Long id) {
        return despesaRepository.findById(id).orElseThrow(() -> new NotFoundException("Despesa não encontrada"));
    }

    @Transactional
    public DespesaEntity create(@Valid DespesaInputDTO despesaInputDTO) {

        boolean userExists = userRepository.existsById(despesaInputDTO.userId());

        if (!userExists) {
            throw new NotFoundException("Usuário com ID " + despesaInputDTO.userId() + " não encontrado.");
        }

        DespesaEntity despesaEntity = DespesaMapper.INSTANCE.toEntity(despesaInputDTO);
        return despesaRepository.save(despesaEntity);
    }

    @Transactional
    public DespesaEntity update(Long id, DespesaInputDTO despesaInputDTO){
        DespesaEntity entity = despesaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não encontrada"));

        despesaMapper.updateEntityFromDto(despesaInputDTO, entity);
        return despesaRepository.save(entity);
    }

    @Transactional
    public void delete(Long id){
        DespesaEntity entity = despesaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despesa não econtrada"));
        despesaRepository.deleteById(entity.getId());
    }

    public Page<DespesaDTO> buscarPorMesEAno(Long userId, int mes, int ano, Pageable pageable){
        YearMonth yearMonth = YearMonth.of(ano, mes);
        LocalDate dataInicio = yearMonth.atDay(1);
        LocalDate dataFim = yearMonth.atEndOfMonth();

        Specification<DespesaEntity> spec = Specification
                .where(DespesaSpecifications.byUserId(userId))
                .and(DespesaSpecifications.byDateBetween(dataInicio, dataFim));

        return despesaRepository.findAll(spec, pageable).map(despesaMapper::toDto);
    }

    public Page<DespesaDTO> getPastUntilCurrentMonth(Long userId, Pageable pageable){
        LocalDate currentDate = LocalDate.now();

        return despesaRepository
                .findPastUntilCurrentMonth(userId, currentDate.getYear(), currentDate.getMonthValue(), pageable)
                .map(despesaMapper::toDto);
    }
}
