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
import java.util.List;

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
        cliente.setCpf("123456789");

        Imoveis imovel = new Imoveis();
        imovel.setLogradouro("Rua das Palmeiras");
        imovel.setBairro("Centro");
        imovel.setCep("12345-678");
        imovel.setMetragem(150);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAluguelSugerido(new BigDecimal("2500.00"));
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

        Locacao locacao1Persisted = locacaoRepository.criarOuAtualizar(locacao);
        System.out.println("Persistindo primeira locação...\n" + locacao1Persisted + "\n");
        transacao.commit(); // Finaliza a transação para garantir persistência

        // Inicia nova transação para a segunda locação
        transacao.begin();
        Locacao locacao2 = new Locacao();
        locacao2.setIdInquilino(cliente);
        locacao2.setImovel(imovel);
        locacao2.setDataInicio(LocalDate.now());
        locacao2.setDataFim(LocalDate.now().plusYears(2));
        locacao2.setValorAluguel(new BigDecimal("2500.00"));
        locacao2.setObs("Contrato bianual");

        // Essa segunda locação deve ser barrada, pois a primeira está ativa

        Locacao locacao2Persisted = locacaoRepository.criarOuAtualizar(locacao2);
        System.out.println("Tentando persistir segunda locação...\n" + locacao2Persisted + "\n");
        transacao.commit();

        // Busca todas as locações já feitas por determinado inquilino
        List<Locacao> locacoesDoCliente = locacaoRepository.buscarTodos(cliente.getId());
        System.out.println();
        for (Locacao x : locacoesDoCliente) {
            System.out.println(x);
        }

        // Busca e exibe a locação salva
        Locacao locacaoSalva = locacaoRepository.buscarPorId(locacao.getId());
        System.out.println();
        System.out.println("Locação buscada por id: " + locacaoSalva);

        manager.close();
        factory.close();
    }
}
