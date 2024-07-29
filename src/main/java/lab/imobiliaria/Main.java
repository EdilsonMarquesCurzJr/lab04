package lab.imobiliaria;

import lab.imobiliaria.Entity.Aluguel;
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();
        AlugueisRepository repository = new AlugueisRepository(em);

        // Criar uma instância de Locacao
        Locacao locacao = new Locacao();
        locacao.setValorAluguel(new BigDecimal("1000"));
        locacao.setPecentualMulta(new BigDecimal("0.05"));
        locacao.setDataVencimento(5);
        locacao.setDataInicio(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        locacao.setDataFim(cal.getTime());
        locacao.setAtivo(true);

        // Persistir a locacao
        em.getTransaction().begin();
        em.persist(locacao);
        em.getTransaction().commit();

        // Criar uma instância de Aluguel com pagamento em atraso
        Aluguel aluguelAtrasado = new Aluguel();
        aluguelAtrasado.setLocacao(locacao);
        aluguelAtrasado.setDataVencimento(new Date());
        aluguelAtrasado.setValorPago(new BigDecimal("1000"));

        // Definindo data de pagamento após o vencimento
        cal.add(Calendar.DAY_OF_MONTH, 10);
        aluguelAtrasado.setDataPagamento(cal.getTime());

        // Persistir o aluguel
        em.getTransaction().begin();
        em.persist(aluguelAtrasado);
        em.getTransaction().commit();

        // Recuperar aluguéis pagos com atraso
        List<Aluguel> aluguelsAtraso = repository.recuperarAluguelPagoAtraso();

        // Exibir os resultados
        System.out.println("Aluguéis pagos com atraso:");
        for (Aluguel aluguel : aluguelsAtraso) {
            System.out.println(aluguel);
        }

        em.close();
        emf.close();
    }
}
