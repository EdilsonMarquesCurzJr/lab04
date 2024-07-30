package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Imoveis;

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

    public List<Aluguel> buscarAluguelPorNome(String nomeInquilino) {
        String jpql = "select a from Aluguel a join a.idLocacao l join l.idInquilino c WHERE c.nome = :nome";
        TypedQuery<Aluguel> query = em.createQuery(jpql, Aluguel.class);
        query.setParameter("nome", nomeInquilino);
        return query.getResultList();
    }

    public List<Imoveis> recuperarImoveisPorLimitePreco(BigDecimal limitePreco) {
        String jpql = "select distinct i from Imoveis i left join i.idLocacoes l " +
                "where i.valorAlugelSugerido <= :limitePreco " +
                "and (l is null or l.ativo = false)";
        TypedQuery<Imoveis> query = em.createQuery(jpql, Imoveis.class);
        query.setParameter("limitePreco", limitePreco);
        List<Imoveis> resultList = query.getResultList();

        return resultList;
    }





    public List<Aluguel> recuperarAluguelPagoAtraso() {
        TypedQuery<Aluguel> query = em.createQuery("select a from Aluguel  a WHERE a.dataPagamento > a.dataVencimento", Aluguel.class);
        return query.getResultList();
    }


}
