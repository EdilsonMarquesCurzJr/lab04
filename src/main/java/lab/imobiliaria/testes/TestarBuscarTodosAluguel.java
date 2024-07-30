package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestarBuscarTodosAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            List<Aluguel> todosAlugueis = alugueisRepo.buscarTodos();

            System.out.println("Lista de todos os aluguéis:");
            for (Aluguel aluguel : todosAlugueis) {
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
