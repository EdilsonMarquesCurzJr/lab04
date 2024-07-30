/*package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Repository.AlugueisRepository;
import org.hibernate.type.LocalDateType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class TestarCriarOuAtualizarAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            em.getTransaction().begin();

            Aluguel aluguel = new Aluguel();
            aluguel.setDataVencimento(new LocalDate());
            aluguel.setDataPagamento(new Date());
            aluguel.setValorPago(new BigDecimal("1200.00"));

            aluguel = alugueisRepo.criarOuAtualizar(aluguel);

            System.out.println("Aluguel inserido/atualizado: " + aluguel);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            emf.close();
        }
    }
}*/