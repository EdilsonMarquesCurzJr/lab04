package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.ServicoImovel;

import javax.persistence.EntityManager;
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
}
