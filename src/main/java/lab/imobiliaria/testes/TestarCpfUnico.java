package lab.imobiliaria.testes;

import lab.imobiliaria.Repository.ImoviesRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestarCpfUnico {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            ImoviesRepository imoveisRepo = new ImoviesRepository(em);

            String cpf = "123.456.789-00"; // Substitua pelo CPF que deseja verificar
            boolean unico = imoveisRepo.cpfUnico(cpf);

            System.out.println("CPF " + cpf + " é único? " + unico);
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
