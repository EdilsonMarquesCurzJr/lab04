package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Locacao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class AlugueisRepository {
    private final EntityManager em;

    public AlugueisRepository(EntityManager em) {
        this.em = em;
    }

    public Aluguel criarOuAtualizar(Aluguel aluguel){
        if(aluguel.getId() != null){
            return em.merge(aluguel);
        }else {
            em.persist(aluguel);
        }
        return aluguel;
    }

    public Aluguel buscarPorId(Integer id){
        return em.find(Aluguel.class, id);
    }
    public List<Aluguel> buscarTodos(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluguel> cq = cb.createQuery(Aluguel.class);
        Root<Aluguel> root = cq.from(Aluguel.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    public List<Aluguel> buscarAluguelPorNome(String nome){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluguel> cq = cb.createQuery(Aluguel.class);
        Root<Aluguel> root = cq.from(Aluguel.class);
        cq.select(root);
        cq.where(cb.equal(root.get("nome"), nome));
        return em.createQuery(cq).getResultList();
    }

    public List<Aluguel> recuperarAluguelPorLimitePreco(BigDecimal limitePreco) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluguel> cq = cb.createQuery(Aluguel.class);
        Root<Aluguel> root = cq.from(Aluguel.class);

        // Junção com a entidade Locacao
        Join<Aluguel, Locacao> locacaoJoin = root.join("idLocacao");

        // Criação da condição para o aluguel ser menor que o limite e a locação estar ativa
        Predicate precoPredicate = cb.lessThan(root.get("valorPago"), limitePreco);
        Predicate disponibilidadePredicate = cb.isTrue(locacaoJoin.get("ativo"));

        cq.select(root).where(cb.and(precoPredicate, disponibilidadePredicate));

        TypedQuery<Aluguel> query = em.createQuery(cq);
        return query.getResultList();
    }


}
