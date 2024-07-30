package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.*;
import lab.imobiliaria.Repository.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CrudServicoImovel {

    public static void main(String[] args) {
        // Configuração do cenário
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transacao = manager.getTransaction();

        // Início da transação
        transacao.begin();

        // Criação das entidades necessárias
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf("12345678900");
        cliente.setTelefone("999999999");
        cliente.setEmail("joao.silva@example.com");
        cliente.setDtNacimento(LocalDate.of(1990, 1, 1));
        manager.persist(cliente);

        TipoImovel tipoImovel = new TipoImovel();
        tipoImovel.setDescricao("Apartamento");
        manager.persist(tipoImovel);

        Imoveis imovel = new Imoveis();
        imovel.setIdProprietario(cliente);
        imovel.setIdTipoImovel(tipoImovel);
        imovel.setLogradouro("Rua das Flores");
        imovel.setBairro("Centro");
        imovel.setCep("12345-678");
        imovel.setMetragem(85);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAlugelSugerido(new BigDecimal("1500.00"));
        imovel.setObs("Nenhuma observação");
        manager.persist(imovel);

        Profissional profissional = new Profissional();
        profissional.setNome("Carlos Mendes");
        profissional.setTelefone1("888888888");
        profissional.setTelefone2("877777777");
        profissional.setValorHora(new BigDecimal("50.00"));
        profissional.setObs("Nenhuma observação");
        manager.persist(profissional);

        // Criação do serviço no imóvel
        ServicoImovel servicoImovel = new ServicoImovel();
        servicoImovel.setIdProfissional(profissional);
        servicoImovel.setIdImovel(imovel);
        servicoImovel.setDataServico(LocalDate.now());
        servicoImovel.setValorTotal(new BigDecimal("300.00"));
        servicoImovel.setObs("Reparos gerais");
        manager.persist(servicoImovel);

        // Finalização da transação
        transacao.commit();

        // Testando o repositório
        ServicoImovelRepository servicoImovelRepo = new ServicoImovelRepository(manager);

        // Buscar por ID
        ServicoImovel servicoEncontrado = servicoImovelRepo.buscarPorId(servicoImovel.getId());
        System.out.println("Serviço encontrado: " + servicoEncontrado);

        // Buscar todos os serviços
        List<ServicoImovel> todosServicos = servicoImovelRepo.buscarTodos();
        System.out.println("Todos os serviços: " + todosServicos);

        // Buscar serviços por locação
        Locacao locacao = new Locacao();
        locacao.setImovel(imovel);
        locacao.setIdInquilino(cliente);
        locacao.setValorAluguel(new BigDecimal("1500.00"));
        locacao.setPecentualMulta(new BigDecimal("0.10"));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(LocalDate.now());
        locacao.setDataFim(LocalDate.now().plusYears(1));
        locacao.setAtivo(true);
        locacao.setObs("Nenhuma observação");
        manager.persist(locacao);

        // Certifique-se de ajustar o método se necessário, passando o ID do imóvel ou locação como parâmetro
        List<ServicoImovel> servicosPorLocacao = servicoImovelRepo.buscarServicosPorLocacao(locacao);
        //servicoImovelRepo.listarServicosPorLocacao(locacao.getId());

        System.out.println("Serviços por locação: " + servicosPorLocacao);

        // Encerrando a transação e liberando recursos
        manager.close();
        factory.close();
    }
}
