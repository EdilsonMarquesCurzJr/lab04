package lab.imobiliaria;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();

        AlugueisRepository alugueisRepository = new AlugueisRepository(em);

        em.getTransaction().begin();

        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setCpf("12345678900");
        cliente.setTelefone("123456789");
        cliente.setEmail("johndoe@example.com");
        cliente.setDtNacimento(LocalDate.of(1990, 1, 1));

        em.persist(cliente);

        Locacao locacao = new Locacao();

        locacao.setValorAluguel(new BigDecimal("1200.00"));
        locacao.setPecentualMulta(new BigDecimal("10.00"));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(LocalDate.of(2025,1,1));
        locacao.setDataFim(LocalDate.of(2026, 1, 1));
        locacao.setAtivo(true);

        em.persist(locacao);

        Aluguel aluguel = new Aluguel();
        aluguel.setIdLocacao(locacao);
        aluguel.setDataVencimento(LocalDate.of(2025, 2, 4));
        aluguel.setValorPago(new BigDecimal("1200.00"));
        aluguel.setDataPagamento(LocalDate.of(2025, 2, 5));
        aluguel.setObs("Pagamento em dia");

        alugueisRepository.criarOuAtualizar(aluguel);

        em.getTransaction().commit();

        List<Aluguel> alugueis = alugueisRepository.buscarTodos();
        System.out.println("Todos os aluguéis: " + alugueis);

        List<Aluguel> alugueisPorNome = alugueisRepository.buscarAluguelPorNome("John Doe");
        System.out.println("Aluguéis por nome: " + alugueisPorNome);

        List<Aluguel> alugueisPorPreco = alugueisRepository.recuperarAluguelPorLimitePreco(new BigDecimal("500000000.00"));
        System.out.println("Aluguéis por limite de preço: " + alugueisPorPreco);

        List<Aluguel> alugueisAtraso = alugueisRepository.recuperarAluguelPagoAtraso();
        System.out.println("Aluguéis pagos em atraso: " + alugueisAtraso);

        em.close();
        emf.close();
    }
}
