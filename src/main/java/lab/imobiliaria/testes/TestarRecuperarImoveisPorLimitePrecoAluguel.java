package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Repository.AlugueisRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class TestarRecuperarImoveisPorLimitePrecoAluguel {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            AlugueisRepository alugueisRepo = new AlugueisRepository(em);

            BigDecimal limitePreco = new BigDecimal("2000.00"); // Substitua pelo limite de preço que deseja utilizar
            List<Imoveis> imoveis = alugueisRepo.recuperarImoveisPorLimitePreco(limitePreco);

            System.out.println("Imóveis encontrados com limite de preço " + limitePreco + ":");
            for (Imoveis imovel : imoveis) {
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
