package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;
import lab.imobiliaria.Repository.ImoveisRepository;
import lab.imobiliaria.Repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CrudImoveis {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);
            ImoveisRepository imoveisRepo = new ImoveisRepository(em);
            LocacaoRepository locacaoRepo = new LocacaoRepository(em);

            em.getTransaction().begin();

            // Instanciação dos objetos necessários
            Cliente cliente = new Cliente();
            cliente.setNome("Victor José");
            cliente.setCpf("125945100");
            cliente.setTelefone("9989515199");
            cliente.setEmail("victorjose@example.com");
            cliente.setDtNacimento(LocalDate.from(LocalDate.of(1999, 7, 25)));
            em.persist(cliente);

            Imoveis imovelAlugado = new Imoveis();
            imovelAlugado.setLogradouro("Rua Teste");
            imovelAlugado.setBairro("Bairro Teste");
            imovelAlugado.setCep("12345-678");
            imovelAlugado.setMetragem(100);
            imovelAlugado.setDormitorios(3);
            imovelAlugado.setBanheiros(2);
            imovelAlugado.setSuites(1);
            imovelAlugado.setVagasGaragem(2);
            imovelAlugado.setValorAlugelSugerido(new BigDecimal("1000.00"));
            imovelAlugado.setObs("");
            imoveisRepo.criarOuAtualizar(imovelAlugado);

            Imoveis imovelVago = new Imoveis();
            imovelVago.setLogradouro("Rua 2");
            imovelVago.setBairro("Longíquo");
            imovelVago.setCep("65345-678");
            imovelVago.setMetragem(150);
            imovelVago.setDormitorios(2);
            imovelVago.setBanheiros(2);
            imovelVago.setSuites(1);
            imovelVago.setVagasGaragem(0);
            imovelVago.setValorAlugelSugerido(new BigDecimal("1500.00"));
            imovelVago.setObs("");
            imoveisRepo.criarOuAtualizar(imovelVago);

            em.getTransaction().commit();

            System.out.println("Antes do imóvel 1 ser alugado");
            List<Imoveis> imoveisPorPreco = alugueisRepo.recuperarImoveisPorLimitePreco(new BigDecimal("2000.00"));
            System.out.println("Imóveis disponíveis por limite de preço: ");
            for (Imoveis imoveis : imoveisPorPreco) {
                System.out.println(imoveis);
            }

            em.getTransaction().begin();

            Locacao locacao = new Locacao();
            locacao.setImovel(imovelAlugado);
            locacao.setValorAluguel(imovelAlugado.getValorAlugelSugerido());
            locacao.setPecentualMulta(new BigDecimal("5.00"));
            locacao.setDataVencimento(5);
            locacao.setDataInicio(LocalDate.of(2025, 1, 1));
            locacao.setDataFim(LocalDate.of(2026, 1, 1));
            locacao.setAtivo(true);
            locacaoRepo.criarOuAtualizar(locacao);

            //Ligando locacao ao imóvel
            //imovelAlugado.getIdLocacoes().add(locacao);
            //imoveisRepo.criarOuAtualizar(imovelAlugado);

            // Atualizando o Imóvel Vago
            imovelVago.setObs("É longe de verdade.");
            imoveisRepo.criarOuAtualizar(imovelVago);

            // Commit da segunda operação
            em.getTransaction().commit();

            System.out.println("Após o imóvel 1 ser alugado");
            imoveisPorPreco = alugueisRepo.recuperarImoveisPorLimitePreco(new BigDecimal("2000.00"));
            System.out.println("Imóveis disponíveis por limite de preço: ");
            for (Imoveis imoveis : imoveisPorPreco) {
                System.out.println(imoveis);
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            emf.close();
        }
    }


}