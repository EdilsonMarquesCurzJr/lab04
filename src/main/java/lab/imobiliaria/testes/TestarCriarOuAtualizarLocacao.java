package lab.imobiliaria.testes;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class TestarCriarOuAtualizarLocacao {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    public static void main(String[] args) {


        try {
            emf = Persistence.createEntityManagerFactory("lab_jpa");
            em = emf.createEntityManager();
                LocacaoRepository repo = new LocacaoRepository(em);

                // Criar um novo imóvel
            Imoveis imovel = new Imoveis();
            imovel.setValorAlugelSugerido(BigDecimal.valueOf(1500));
            em.getTransaction().begin();
            em.persist(imovel);
            em.getTransaction().commit();

                // Criar ou atualizar uma locação
            Locacao locacao = new Locacao();
            locacao.setImovel(imovel);
            locacao.setDataInicio(LocalDate.now());
            locacao.setDataFim(LocalDate.now().plusMonths(1));
            locacao.setValorAluguel(BigDecimal.valueOf(1500));
            locacao.setAtivo(true);
            em.getTransaction().begin();
            locacao = repo.criarOuAtualizar(locacao);
            em.getTransaction().commit();

            System.out.println("Locação criada ou atualizada: " + locacao);

            } finally {
                if (em != null) em.close();
                if (emf != null) emf.close();
            }
        }
    }
