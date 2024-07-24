package lab.imobiliaria;

import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab_jpa");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Imoveis imovel = new Imoveis();
        imovel.setLogradouro("Rua Exemplo");
        imovel.setBairro("Bairro Exemplo");
        imovel.setCep("12345-678");
        imovel.setMetragem(100);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAlugelSugerido(new BigDecimal("1500.00"));
        imovel.setObs("nada a comentar");

        entityManager.persist(imovel);
        entityManager.getTransaction().commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager(); // Create a new EntityManager
        entityManager.getTransaction().begin();

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.MAY, 20);

        Cliente cliente = new Cliente();
        cliente.setCpf("12345");
        cliente.setEmail("gfds00");
        cliente.setDtNacimento(cal.getTime());
        cliente.setTelefone("2347");
        cliente.setNome("Edilson o fodão");
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        Imoveis foundImovel = entityManager.find(Imoveis.class, imovel.getId());
        if (foundImovel != null) {
            System.out.println("Imovel encontrado: " + foundImovel.toString());
        } else {
            System.out.println("Imóvel não encontrado.");
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
