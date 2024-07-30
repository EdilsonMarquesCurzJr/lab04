package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.*;
import lab.imobiliaria.Repository.ServicoImovelRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CrudProfissionais {

    public static void main(String[] args) {

        // cenário
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transacao = manager.getTransaction();

        ServicoImovelRepository servicoImovelRepository = new ServicoImovelRepository(manager);

        transacao.begin();

        Profissional profissional = new Profissional();
        profissional.setNome("Carlos");
        profissional.setTelefone1("123456789");
        profissional.setTelefone2("987654321");
        profissional.setValorHora(new BigDecimal("50.00"));
        profissional.setObs("Eletricista");

        Imoveis imovel = new Imoveis();
        imovel.setLogradouro("Rua das Flores");
        imovel.setBairro("Centro");
        imovel.setCep("12345-678");
        imovel.setMetragem(100);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAlugelSugerido(new BigDecimal("1200.00"));
        imovel.setObs("Bem localizado");

        ServicoImovel servico = new ServicoImovel();
        servico.setIdProfissional(profissional);
        servico.setIdImovel(imovel);
        servico.setDataServico(LocalDate.now());
        servico.setValorTotal(new BigDecimal("300.00"));
        servico.setObs("Manutenção elétrica");

        // Persistindo as entidades
        manager.persist(profissional);
        manager.persist(imovel);
        servicoImovelRepository.salvarOuAtualizar(servico);

        transacao.commit();

        // Busca e exibe o serviço salvo
        ServicoImovel servicoSalvo = servicoImovelRepository.buscarPorId(servico.getId());
        System.out.println(servicoSalvo);

        manager.close();
        factory.close();
    }
}
