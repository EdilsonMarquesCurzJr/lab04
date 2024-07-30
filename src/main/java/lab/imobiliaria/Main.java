package lab.imobiliaria;

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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepository = new AlugueisRepository(em);

            em.getTransaction().begin();

            // Insira dados básicos e simples para testes
            Cliente cliente = new Cliente();
            cliente.setNome("John Doe");
            cliente.setCpf("12345678900");
            cliente.setTelefone("123456789");
            cliente.setEmail("johndoe@example.com");
            cliente.setDtNacimento(Date.from(LocalDate.of(1990, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            em.persist(cliente);

            Imoveis imovel = new Imoveis();
            imovel.setLogradouro("Rua Teste");
            imovel.setBairro("Bairro Teste");
            imovel.setCep("12345-678");
            imovel.setMetragem(100);
            imovel.setDormitorios(3);
            imovel.setBanheiros(2);
            imovel.setSuites(1);
            imovel.setVagasGaragem(2);
            imovel.setValorAlugelSugerido(new BigDecimal("1000.00"));
            imovel.setObs("Observação");
            em.persist(imovel);

            Locacao locacao = new Locacao();
            locacao.setImovel(imovel);

            locacao.setValorAluguel(new BigDecimal("1000.00"));
            locacao.setPecentualMulta(new BigDecimal("5.00"));
            locacao.setDataVencimento(5);
            locacao.setDataInicio(new Date());
            locacao.setDataFim(Date.from(LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            locacao.setAtivo(true);
            em.persist(locacao);

            em.getTransaction().commit();

            List<Imoveis> alugueisPorPreco = alugueisRepository.recuperarImoveisPorLimitePreco(new BigDecimal("2000.00"));
            System.out.println("Imóveis por limite de preço: " + alugueisPorPreco);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            emf.close();
        }
    }


}
