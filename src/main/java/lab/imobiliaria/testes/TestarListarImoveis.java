package lab.imobiliaria.testes;


import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Repository.ImoviesRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestarListarImoveis {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            ImoviesRepository imoveisRepo = new ImoviesRepository(em);

            List<Imoveis> todosImoveis = imoveisRepo.ListarImoveis();

            System.out.println("Lista de todos os imóveis:");
            for (Imoveis imovel : todosImoveis) {
                System.out.println(imovel);
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
