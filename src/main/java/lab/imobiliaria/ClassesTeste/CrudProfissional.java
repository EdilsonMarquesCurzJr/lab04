package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.*;
import lab.imobiliaria.Repository.ProfissionaisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CrudProfissional {

    public static void main(String[] args) {
        // Configuração do cenário
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            // Início da transação
            transaction.begin();

            // Criação de um cliente
            Cliente cliente = new Cliente();
            cliente.setNome("João Silva");
            cliente.setCpf("12345678900");
            cliente.setTelefone("999999999");
            cliente.setEmail("joao.silva@example.com");
            cliente.setDtNacimento(LocalDate.of(1990, 1, 1));
            manager.persist(cliente);

            // Criação de um tipo de imóvel
            TipoImovel tipoImovel = new TipoImovel();
            tipoImovel.setDescricao("Apartamento");
            manager.persist(tipoImovel);

            // Criação de um imóvel
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
            imovel.setValorAluguelSugerido(new BigDecimal("1500.00"));
            imovel.setObs("Nenhuma observação");
            manager.persist(imovel);

            // Criação de um profissional
            Profissional profissional = new Profissional();
            profissional.setNome("Carlos Mendes");
            profissional.setTelefone1("888888888");
            profissional.setTelefone2("877777777");
            profissional.setValorHora(new BigDecimal("50.00"));
            profissional.setObs("Nenhuma observação");
            manager.persist(profissional);

            // Criação de um serviço no imóvel
            ServicoImovel servicoImovel = new ServicoImovel();
            servicoImovel.setIdProfissional(profissional);
            servicoImovel.setIdImovel(imovel);
            servicoImovel.setDataServico(LocalDate.now());
            servicoImovel.setValorTotal(new BigDecimal("300.00"));
            servicoImovel.setObs("Reparos gerais");
            manager.persist(servicoImovel);

            // Commit da transação
            transaction.commit();

            // Testando o repositório
            ProfissionaisRepository repo = new ProfissionaisRepository(manager);

            // Buscar por ID
            Profissional encontrado = repo.buscarPorId(profissional.getId());
            System.out.println("Profissional encontrado: " + encontrado);

            // Buscar todos
            List<Profissional> todosProfissionais = repo.buscarTodos();
            System.out.println("Todos os profissionais: " + todosProfissionais);

            // Atualizar o profissional
            encontrado.setNome("Carlos Mendes Silva");
            transaction.begin();
            repo.criarOuAtualizar(encontrado);
            transaction.commit();
            System.out.println("Profissional atualizado: " + repo.buscarPorId(encontrado.getId()));

            // Listar profissionais por imóveis
            List<Imoveis> imoveisPorProfissional = repo.listarPorfissionaisPorImoveis(encontrado);
            System.out.println("Imóveis associados ao profissional: " + imoveisPorProfissional);

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
            factory.close();
        }
    }
}
