package br.com.homebudget.despesas;

import br.com.homebudget.despesas.dto.DespesaDTO;
import br.com.homebudget.despesas.dto.DespesaInputDTO;
import br.com.homebudget.shared.dto.MetaResponse;
import br.com.homebudget.shared.response.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DespesaV1Controller {

    private final DespesaService despesaService;
    private final DespesaMapper despesaMapper;

    @GetMapping("/despesas")
    public ResponseEntity<PagedResponse<DespesaDTO>> index (
            @RequestParam(required = false) Long user_id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DespesaDTO> despesaDTOPage = despesaService.getAll(user_id, pageable);

        PagedResponse<DespesaDTO> response = new PagedResponse<>(
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

    @GetMapping("/despesas/{id}")
    public ResponseEntity<DespesaDTO> getDespesaById(@PathVariable Long id){
        DespesaEntity entity = despesaService.getById(id);
        DespesaDTO despesaDTO = despesaMapper.toDto(entity);
        return ResponseEntity.ok(despesaDTO);
    }

    @PostMapping("/despesas")
    public ResponseEntity<DespesaDTO> createUser(@Valid @RequestBody DespesaInputDTO despesaInputDTO){
        DespesaEntity entity = despesaService.create(despesaInputDTO);
        DespesaDTO despesaDTO = despesaMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaDTO);
    }

    @PutMapping("/despesas/{id}")
    public ResponseEntity<DespesaDTO> update (@PathVariable Long id,@Valid @RequestBody DespesaInputDTO despesaInputDto){
        DespesaEntity entity = despesaService.update(id, despesaInputDto);
        DespesaDTO updatedDespesa = despesaMapper.toDto(entity);
        return ResponseEntity.ok(updatedDespesa);
    }

    @DeleteMapping("/despesas/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        despesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
