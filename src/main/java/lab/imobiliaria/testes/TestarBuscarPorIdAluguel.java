package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestarBuscarPorIdAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            Integer aluguelId = 1; // Substitua pelo ID do aluguel que deseja buscar
            Aluguel aluguel = alugueisRepo.buscarPorId(aluguelId);

            if (aluguel != null) {
                System.out.println("Aluguel encontrado: " + aluguel);
            } else {
                System.out.println("Aluguel com ID " + aluguelId + " não encontrado.");
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
