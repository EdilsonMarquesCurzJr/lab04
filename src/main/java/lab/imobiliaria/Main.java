package lab.imobiliaria;

import lab.imobiliaria.Entity.Cliente;
import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Configuração do EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


    }
}
