package br.com.homebudget.seeders;

import br.com.homebudget.despesas.DespesaEntity;
import br.com.homebudget.despesas.DespesaRepository;
import br.com.homebudget.despesas.enums.CategoriaDespesaEnum;
import br.com.homebudget.receitas.ReceitaEntity;
import br.com.homebudget.receitas.ReceitaRepository;
import br.com.homebudget.receitas.enums.FonteReceitaEnum;
import br.com.homebudget.investimentos.InvestimentoEntity;
import br.com.homebudget.investimentos.InvestimentoRepository;
import br.com.homebudget.investimentos.enums.TipoInvestimentoEnum;
import br.com.homebudget.users.UserEntity;
import br.com.homebudget.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final DespesaRepository despesaRepository;
    private final ReceitaRepository receitaRepository;
    private final InvestimentoRepository investimentoRepository;
    private final UserRepository userRepository;
    private final Random random = new Random();

    public DatabaseSeeder(DespesaRepository despesaRepository, ReceitaRepository receitaRepository,
                          InvestimentoRepository investimentoRepository, UserRepository userRepository) {
        this.despesaRepository = despesaRepository;
        this.receitaRepository = receitaRepository;
        this.investimentoRepository = investimentoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedDespesas();
        seedReceitas();
        seedInvestimentos();
    }

    private void seedUsers() {
        if (userRepository.findByEmail("usuario@example.com").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setName("Usuário Exemplo");
            user.setEmail("usuario@example.com");
            userRepository.save(user);
        }
    }

    private void seedDespesas() {
        userRepository.findByEmail("usuario@example.com").ifPresent(user -> {
            if (despesaRepository.count() < 20) {
                List<CategoriaDespesaEnum> categorias = List.of(CategoriaDespesaEnum.values());

                for (int i = 0; i < 30; i++) {
                    DespesaEntity despesa = new DespesaEntity();
                    despesa.setUser(user);
                    despesa.setCategoria(categorias.get(random.nextInt(categorias.size())));
                    despesa.setValor(BigDecimal.valueOf(random.nextDouble() * 500 + 10)); // Valores entre 10 e 510
                    despesa.setDescricao("Despesa aleatória #" + (i + 1));
                    despesa.setData(LocalDate.now().minusDays(random.nextInt(90)));

                    despesaRepository.save(despesa);
                }
            }
        });
    }

    private void seedReceitas() {
        userRepository.findByEmail("usuario@example.com").ifPresent(user -> {
            if (receitaRepository.count() < 20) {
                List<FonteReceitaEnum> fontes = List.of(FonteReceitaEnum.values());

                for (int i = 0; i < 12; i++) {
                    ReceitaEntity receita = new ReceitaEntity();
                    receita.setUser(user);
                    receita.setFonte(fontes.get(random.nextInt(fontes.size())));
                    receita.setValor(BigDecimal.valueOf(random.nextDouble() * 1000 + 100)); // Valores entre 100 e 1100
                    receita.setDescricao("Receita aleatória #" + (i + 1));
                    receita.setData(LocalDate.now().minusDays(random.nextInt(90)));

                    receitaRepository.save(receita);
                }
            }
        });
    }

    private void seedInvestimentos() {
        userRepository.findByEmail("usuario@example.com").ifPresent(user -> {
            if (investimentoRepository.count() < 20) {
                List<TipoInvestimentoEnum> tipos = List.of(TipoInvestimentoEnum.values());

                for (int i = 0; i < 15; i++) {
                    InvestimentoEntity investimento = new InvestimentoEntity();
                    investimento.setUser(user);
                    investimento.setTipo(tipos.get(random.nextInt(tipos.size())));
                    investimento.setValorInicial(BigDecimal.valueOf(random.nextDouble() * 5000 + 500)); // Valores entre 500 e 5500
                    investimento.setValorAtual(investimento.getValorInicial().multiply(BigDecimal.valueOf(1 + random.nextDouble() * 0.1))); // Valor atual entre 100% e 110% do valor inicial
                    investimento.setDescricao("Investimento aleatório #" + (i + 1));
                    investimento.setData(LocalDate.now().minusDays(random.nextInt(90)));

                    investimentoRepository.save(investimento);
                }
            }
        });
    }
}