package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Entity.ServicoImovel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ServicoImovelRepository {
    private final EntityManager em;

    public ServicoImovelRepository(EntityManager em) {
        this.em = em;
    }

    public ServicoImovel salvarOuAtualizar(ServicoImovel servico) {
        if(servico.getId() != null){
            return em.merge(servico);
        }else {
            em.persist(servico);
            em.flush();
        }
        return servico;
    }

    public ServicoImovel buscarPorId(Integer id) {
        return em.find(ServicoImovel.class, id);
    }

    public List<ServicoImovel> buscarTodos() {
        String jpql = "SELECT s FROM ServicoImovel s";
        return em.createQuery(jpql, ServicoImovel.class).getResultList();
    }

    public List<ServicoImovel> buscarServicosPorLocacao(Locacao locacao) {
        String jpql = "SELECT s FROM ServicoImovel s WHERE s.idImovel = :idImovel";
        TypedQuery<ServicoImovel> query = em.createQuery(jpql, ServicoImovel.class);
        query.setParameter("idImovel", locacao.getImovel().getId());
        return query.getResultList();
    }
}
