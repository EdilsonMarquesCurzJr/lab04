package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Imoveis;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImoviesRepository {
    private final EntityManager manager;

    public ImoviesRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Imoveis criarOuAtualizar(Imoveis movie) {
        if (movie != null) {
            this.manager.persist(movie);
        } else {
            movie = this.manager.merge(movie);
        }
        return movie;
    }

    public Imoveis bucarPor(Integer id) {
        return manager.find(Imoveis.class, id);
    }

    public List<Imoveis> ListarImoveis() {
        return manager.createQuery("from Imoveis", Imoveis.class).getResultList();
    }

    public boolean cpfUnico(String cpf) {
        String hql = "SELECT count(*) FROM Imoveis i JOIN i.idProprietario p WHERE p.cpf = :cpf";
        Query query = manager.createQuery(hql);
        query.setParameter("cpf", cpf);
        long count = (long) query.getSingleResult();
        return count == 0;
    }

    public List<Imoveis> findImoveisByProfissionalId(Integer profissionalId) {
        String jpql = "SELECT i FROM Imoveis i JOIN ServicoImovel s ON i.id = s.idImovel WHERE s.idProfissional.id = :profissionalId";
        TypedQuery<Imoveis> query = manager.createQuery(jpql, Imoveis.class);
        query.setParameter("profissionalId", profissionalId);
        return query.getResultList();
    }
}
