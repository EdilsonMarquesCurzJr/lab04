package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Repository.ImoviesRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteBuscarImovelPorId {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            ImoviesRepository imoveisRepo = new ImoviesRepository(em);

            Integer imovelId = 1; // Substitua pelo ID do imóvel que deseja buscar
            Imoveis imovel = imoveisRepo.bucarPor(imovelId);

            if (imovel != null) {
                System.out.println("Imóvel encontrado: " + imovel);
            } else {
                System.out.println("Imóvel com ID " + imovelId + " não encontrado.");
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
