package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CrudLocacao {

    public static void main(String[] args) {

        // cenário
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transacao = manager.getTransaction();

        LocacaoRepository locacaoRepository = new LocacaoRepository(manager);

        transacao.begin();

        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setTelefone("123456789");
        cliente.setEmail("maria@example.com");

        Imoveis imovel = new Imoveis();
        imovel.setLogradouro("Rua das Palmeiras");
        imovel.setBairro("Centro");
        imovel.setCep("12345-678");
        imovel.setMetragem(150);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAlugelSugerido(new BigDecimal("2500.00"));
        imovel.setObs("Bem localizado");

        // Persistindo o cliente e o imóvel primeiro
        manager.persist(cliente);
        manager.persist(imovel);
        manager.flush();  // Garante que as IDs foram geradas

        Locacao locacao = new Locacao();
        locacao.setIdInquilino(cliente);
        locacao.setImovel(imovel);
        locacao.setDataInicio(LocalDate.now());
        locacao.setDataFim(LocalDate.now().plusYears(1));
        locacao.setValorAluguel(new BigDecimal("2500.00"));
        locacao.setObs("Contrato anual");

        // Persistindo a locação
        locacaoRepository.criarOuAtualizar(locacao);

        transacao.commit();

        // Busca e exibe a locação salva
        Locacao locacaoSalva = locacaoRepository.buscarPorId(locacao.getId());
        System.out.println(locacaoSalva);

        manager.close();
        factory.close();
    }
}
