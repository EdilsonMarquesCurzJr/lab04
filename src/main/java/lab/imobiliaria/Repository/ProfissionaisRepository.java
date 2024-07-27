package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Profissional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProfissionaisRepository {
    private final EntityManager manager;

    public ProfissionaisRepository(EntityManager manager) {
        this.manager = manager;
    }

    public void criarOuAtualizar(Profissional profissional) {
        if (profissional.getId() == null) {
            manager.persist(profissional);
        } else {
            manager.merge(profissional);
        }
    }

    public Profissional buscarPorId(Integer id) {
        return manager.find(Profissional.class, id);
    }

    public List<Profissional> buscarTodos() {
        return manager.createQuery("from Profissional", Profissional.class).getResultList();
    }

    public List<Imoveis> listarPorfissionaisPorImoveis(Profissional profissional) {
        String jpql = "SELECT i FROM Imoveis i JOIN ServicoImovel s ON s.idImovel = i WHERE s.idProfissional.id = :profissionalId";
        TypedQuery<Imoveis> query = manager.createQuery(jpql, Imoveis.class);
        query.setParameter("profissionalId", profissional.getId());
        return query.getResultList();
    }
}
