package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestarRecuperarAluguelPagoAtrasoAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            List<Aluguel> alugueisAtraso = alugueisRepo.recuperarAluguelPagoAtraso();

            System.out.println("Aluguéis pagos em atraso:");
            for (Aluguel aluguel : alugueisAtraso) {
                System.out.println(aluguel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            emf.close();
        }
    }
}
