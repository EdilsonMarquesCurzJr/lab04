package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
/*
* Setando um cliente, uma locação e dois aluguéis, um em dia e um com atraso.
* Não está sendo setado imóvel para a locação para simplificar o teste, já que as funcionalidades
* deste estão sendo testadas em outro arquivo teste (CrudImoveis).
* As funções de AluguelRepositorie são testadas ao final.
* */
public class CrudAlugueis {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();

        AlugueisRepository alugueisRepository = new AlugueisRepository(em);

        em.getTransaction().begin();

        Cliente cliente = new Cliente();
        cliente.setNome("Pedro Ariel");
        cliente.setCpf("12345678900");
        cliente.setTelefone("123456789");
        cliente.setEmail("pedroariel@example.com");
        cliente.setDtNacimento(LocalDate.of(1990, 1, 1));
        em.persist(cliente);

        Locacao locacao = new Locacao();
        locacao.setValorAluguel(new BigDecimal("1200.00"));
        locacao.setPecentualMulta(new BigDecimal("10.00"));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(LocalDate.of(2025,1,1));
        locacao.setDataFim(LocalDate.of(2026, 1, 1));
        locacao.setAtivo(true);
        locacao.setIdInquilino(cliente);
        em.persist(locacao); //poderia se importar o repositorie de locação

        Aluguel aluguelEmDia = new Aluguel();
        aluguelEmDia.setIdLocacao(locacao);
        aluguelEmDia.setDataVencimento(LocalDate.of(2025, 2, 4));
        aluguelEmDia.setValorPago(new BigDecimal("1200.00"));
        aluguelEmDia.setDataPagamento(LocalDate.of(2025, 2, 3));
        aluguelEmDia.setObs("Pagamento em dia");

        alugueisRepository.criarOuAtualizar(aluguelEmDia);

        Aluguel aluguelAtrasado = new Aluguel();
        aluguelAtrasado.setIdLocacao(locacao);
        aluguelAtrasado.setDataVencimento(LocalDate.of(2025, 3, 4));
        aluguelAtrasado.setValorPago(new BigDecimal("1320.00"));
        aluguelAtrasado.setDataPagamento(LocalDate.of(2025, 3, 5));
        aluguelAtrasado.setObs("Pagamento Atrasado");

        alugueisRepository.criarOuAtualizar(aluguelAtrasado);

        em.getTransaction().commit();

        List<Aluguel> alugueis = alugueisRepository.buscarTodos();
        System.out.println("Todos os aluguéis: " );
        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel);
        }

        List<Aluguel> alugueisPorNome = alugueisRepository.buscarAluguelPorNome("Pedro Ariel");
        System.out.println("Aluguéis por nome: " );
        for (Aluguel aluguel : alugueisPorNome) {
            System.out.println(aluguel);
        }

        List<Aluguel> alugueisAtraso = alugueisRepository.recuperarAluguelPagoAtraso();
        System.out.println("Aluguéis pagos em atraso: " );
        for (Aluguel aluguel : alugueisAtraso) {
            System.out.println(aluguel);
        }

        List<Imoveis> imoveisPorLimitePreco = alugueisRepository.recuperarImoveisPorLimitePreco(new BigDecimal("2320.00"));
        for (Imoveis imoveis : imoveisPorLimitePreco) {
            System.out.println(imoveis);
        }
        em.close();
        emf.close();
    }



}