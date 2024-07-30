package lab.imobiliaria.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

import java.util.List;

public class TestBuscarTodosLocacoes {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("lab_jpa");
            em = emf.createEntityManager();

            LocacaoRepository repo = new LocacaoRepository(em);

            // Suponha que você tenha um cliente com o ID 1
            Integer clienteId = 1; // Substitua pelo ID real

            List<Locacao> locacoes = repo.buscarTodos(clienteId);
            System.out.println("Todas as locações para o cliente com ID " + clienteId + ": " + locacoes);

        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}
