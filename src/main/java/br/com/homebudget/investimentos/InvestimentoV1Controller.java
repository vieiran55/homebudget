package br.com.homebudget.investimentos;

import br.com.homebudget.investimentos.dto.InvestimentoDTO;
import br.com.homebudget.investimentos.dto.InvestimentoInputDTO;
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
public class InvestimentoV1Controller {

    private final InvestimentoService investimentoService;
    private final InvestimentoMapper investimentoMapper;

    @GetMapping("/investimentos")
    public ResponseEntity<PagedResponse<InvestimentoDTO>> index(
            @RequestParam(required = false) Long user_id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InvestimentoDTO> investimentoDTOPage = investimentoService.getAll(user_id, pageable);

        PagedResponse<InvestimentoDTO> response = new PagedResponse<>(
                investimentoDTOPage.getContent(),
                new MetaResponse(
                        (int) investimentoDTOPage.getTotalElements(),
                        investimentoDTOPage.getTotalPages(),
                        investimentoDTOPage.getNumber() + 1,
                        investimentoDTOPage.getSize()
                )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/investimentos/{id}")
    public ResponseEntity<InvestimentoDTO> getInvestimentoById(@PathVariable Long id) {
        InvestimentoEntity entity = investimentoService.getById(id);
        InvestimentoDTO investimentoDTO = investimentoMapper.toDto(entity);
        return ResponseEntity.ok(investimentoDTO);
    }

    @PostMapping("/investimentos")
    public ResponseEntity<InvestimentoDTO> createInvestimento(@Valid @RequestBody InvestimentoInputDTO investimentoInputDTO) {
        InvestimentoEntity entity = investimentoService.create(investimentoInputDTO);
        InvestimentoDTO investimentoDTO = investimentoMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimentoDTO);
    }

    @PutMapping("/investimentos/{id}")
    public ResponseEntity<InvestimentoDTO> update(@PathVariable Long id, @Valid @RequestBody InvestimentoInputDTO investimentoInputDTO) {
        InvestimentoEntity entity = investimentoService.update(id, investimentoInputDTO);
        InvestimentoDTO updatedInvestimento = investimentoMapper.toDto(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedInvestimento);
    }

    @DeleteMapping("/investimentos/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        investimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
