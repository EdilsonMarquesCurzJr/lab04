package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestarBuscarAluguelPorNomeAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            String nomeInquilino = "João Silva"; // Substitua pelo nome do inquilino que deseja buscar
            List<Aluguel> alugueis = alugueisRepo.buscarAluguelPorNome(nomeInquilino);

            System.out.println("Aluguéis encontrados para o inquilino " + nomeInquilino + ":");
            for (Aluguel aluguel : alugueis) {
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
