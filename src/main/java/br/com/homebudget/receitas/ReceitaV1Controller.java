package br.com.homebudget.receitas;

import br.com.homebudget.despesas.dto.DespesaInputDTO;
import br.com.homebudget.receitas.dto.ReceitaDTO;
import br.com.homebudget.receitas.dto.ReceitaInputDTO;
import br.com.homebudget.shared.dto.MetaResponse;
import br.com.homebudget.shared.response.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReceitaV1Controller {

    private final ReceitaService receitaService;
    private final  ReceitaMapper receitaMapper;

    @GetMapping("/receitas")
    public ResponseEntity<PagedResponse<ReceitaDTO>> index (
            @RequestParam(required = false) Long user_id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReceitaDTO> despesaDTOPage = receitaService.getAll(user_id, pageable);

        PagedResponse<ReceitaDTO> response = new PagedResponse<>(
                despesaDTOPage.getContent(),
                new MetaResponse(
                        (int) despesaDTOPage.getTotalElements(),
                        despesaDTOPage.getTotalPages(),
                        despesaDTOPage.getNumber() + 1,
                        despesaDTOPage.getSize()
                )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/receitas/{id}")
    public ResponseEntity<ReceitaDTO> getReceitaById(@PathVariable Long id){
        ReceitaEntity entity = receitaService.getById(id);
        ReceitaDTO receitaDTO = receitaMapper.toDto(entity);
        return ResponseEntity.ok(receitaDTO);
    }

    @PostMapping("/receitas")
    public ResponseEntity<ReceitaDTO> createReceita(@Valid @RequestBody ReceitaInputDTO receitaInputDTO){
        ReceitaEntity entity = receitaService.create(receitaInputDTO);
        ReceitaDTO receitaDTO = receitaMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(receitaDTO);
    }

    @PutMapping("/receitas/{id}")
    public ResponseEntity<ReceitaDTO> update(@PathVariable Long id, @Valid @RequestBody ReceitaInputDTO receitaInputDTO){
        ReceitaEntity entity = receitaService.update(id, receitaInputDTO);
        ReceitaDTO updatedReceita = receitaMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedReceita);
    }

    @DeleteMapping("/receitas/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        receitaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
