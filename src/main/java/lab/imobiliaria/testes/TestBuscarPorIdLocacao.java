package lab.imobiliaria.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

public class TestBuscarPorIdLocacao {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("lab_jpa");
            em = emf.createEntityManager();

            LocacaoRepository repo = new LocacaoRepository(em);

            // Suponha que você já tenha uma locação com o ID 1
            Integer locacaoId = 1; // Substitua pelo ID real

            Locacao locacao = repo.buscarPorId(locacaoId);
            System.out.println("Locação encontrada por ID: " + locacao);

        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}
