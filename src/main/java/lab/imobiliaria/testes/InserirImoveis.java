package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Repository.ImoviesRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class InserirImoveis {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            ImoviesRepository imoveisRepo = new ImoviesRepository(em);

            em.getTransaction().begin();

            Imoveis imovel = new Imoveis();
            imovel.setLogradouro("Avenida das Flores, 123");
            imovel.setBairro("Jardim Primavera");
            imovel.setCep("12345-678");
            imovel.setMetragem(150);
            imovel.setDormitorios(3);
            imovel.setBanheiros(2);
            imovel.setSuites(1);
            imovel.setVagasGaragem(2);
            imovel.setValorAlugelSugerido(new BigDecimal("1800.00"));
            imovel.setObs("Imóvel novo com piscina");

            imovel = imoveisRepo.criarOuAtualizar(imovel);

            System.out.println("Imóvel inserido/atualizado: " + imovel);

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
}
