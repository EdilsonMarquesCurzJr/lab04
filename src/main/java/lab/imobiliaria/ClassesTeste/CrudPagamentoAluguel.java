package lab.imobiliaria.ClassesTeste;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Entity.TipoImovel;
import lab.imobiliaria.Service.PagamentoAluguelService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CrudPagamentoAluguel {

    public static void main(String[] args) {
        // cenário
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transacao = manager.getTransaction();

        transacao.begin();

        // Criação de entidades necessárias para o teste
        Cliente inquilino = new Cliente();
        inquilino.setNome("João Silva");
        inquilino.setCpf("123.456.789-00");
        inquilino.setTelefone("99999-9999");
        inquilino.setEmail("joao.silva@example.com");
        inquilino.setDtNacimento(LocalDate.of(1985, 1, 1));
        manager.persist(inquilino);

        Cliente proprietario = new Cliente();
        proprietario.setNome("Maria Oliveira");
        proprietario.setCpf("987.654.321-00");
        proprietario.setTelefone("88888-8888");
        proprietario.setEmail("maria.oliveira@example.com");
        proprietario.setDtNacimento(LocalDate.of(1975, 5, 20));
        manager.persist(proprietario);

        TipoImovel tipoImovel = new TipoImovel();
        tipoImovel.setDescricao("Apartamento");
        manager.persist(tipoImovel);

        Imoveis imovel = new Imoveis();
        imovel.setIdProprietario(proprietario);
        imovel.setIdTipoImovel(tipoImovel);
        imovel.setLogradouro("Rua das Flores, 123");
        imovel.setBairro("Centro");
        imovel.setCep("12345-678");
        imovel.setMetragem(100);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAluguelSugerido(BigDecimal.valueOf(1500.00));
        imovel.setObs("Excelente localização");
        manager.persist(imovel);

        Locacao locacao = new Locacao();
        locacao.setImovel(imovel);
        locacao.setIdInquilino(inquilino);
        locacao.setValorAluguel(BigDecimal.valueOf(1500.00));
        locacao.setPecentualMulta(BigDecimal.valueOf(0.10));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(LocalDate.of(2023, 1, 1));
        locacao.setDataFim(LocalDate.of(2023, 12, 31));
        locacao.setAtivo(true);
        locacao.setObs("Contrato de 1 ano");
        manager.persist(locacao);

        Aluguel aluguel = new Aluguel();
        aluguel.setDataVencimento(LocalDate.of(2023, 2, 5));
        aluguel.setDataPagamento(LocalDate.of(2023, 2, 10));
        aluguel.setObs("Pagamento com atraso de 5 dias");

        // Criando a instância do serviço de pagamento
        PagamentoAluguelService pagamentoAluguelService = new PagamentoAluguelService(manager);

        // Registrando o pagamento
        BigDecimal valorPago = pagamentoAluguelService.registrarPagamentoAluguel(aluguel, locacao);

        System.out.println("Valor pago com multa: " + valorPago);

        transacao.commit();

        manager.close();
        factory.close();
    }
}
