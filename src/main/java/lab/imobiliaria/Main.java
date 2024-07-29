package lab.imobiliaria;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializar EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();

        // Inicializar repositório
        AlugueisRepository alugueisRepository = new AlugueisRepository(em);

        // Iniciar transação
        em.getTransaction().begin();

        // Criar e salvar um cliente
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setCpf("12345678900");
        cliente.setTelefone("123456789");
        cliente.setEmail("johndoe@example.com");
        cliente.setDtNacimento(new Date(90, Calendar.JANUARY, 1)); // data de nascimento: 01/01/1990

        em.persist(cliente);

        // Criar e salvar uma locação
        Locacao locacao = new Locacao();
        locacao.setIdInquilino(cliente);
        locacao.setValorAluguel(new BigDecimal("1200.00"));
        locacao.setPecentualMulta(new BigDecimal("10.00"));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(new Date());
        locacao.setDataFim(new Date(2024, Calendar.JANUARY, 1)); // 1 ano depois
        locacao.setAtivo(true);

        em.persist(locacao);

        // Criar e salvar um aluguel
        Aluguel aluguel = new Aluguel();
        aluguel.setLocacao(locacao);
        aluguel.setDataVencimento(new Date(2030, Calendar.JANUARY, 14));
        aluguel.setValorPago(new BigDecimal("1200.00"));
        aluguel.setDataPagamento(new Date(2031, Calendar.JANUARY, 1));
        aluguel.setObs("Pagamento em dia");

        alugueisRepository.criarOuAtualizar(aluguel);

        // Commit transação
        em.getTransaction().commit();

        // Testar busca de todos os aluguéis
        List<Aluguel> alugueis = alugueisRepository.buscarTodos();
        System.out.println("Todos os aluguéis: " + alugueis);

        // Testar busca por nome do inquilino
        List<Aluguel> alugueisPorNome = alugueisRepository.buscarAluguelPorNome("John Doe");
        System.out.println("Aluguéis por nome: " + alugueisPorNome);

        // Testar busca por limite de preço
        List<Aluguel> alugueisPorPreco = alugueisRepository.recuperarAluguelPorLimitePreco(new BigDecimal("1300.00"));
        System.out.println("Aluguéis por limite de preço: " + alugueisPorPreco);

        // Testar busca de aluguéis pagos em atraso
        List<Aluguel> alugueisAtraso = alugueisRepository.recuperarAluguelPagoAtraso();
        System.out.println("Aluguéis pagos em atraso: " + alugueisAtraso);

        // Fechar EntityManager e EntityManagerFactory
        em.close();
        emf.close();
    }
}
