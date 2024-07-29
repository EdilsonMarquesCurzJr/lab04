package lab.imobiliaria.Repository;

import lab.imobiliaria.Entity.Imoveis;
import lab.imobiliaria.Entity.Locacao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class LocacaoRepository {
    private final EntityManager em;

    public LocacaoRepository(EntityManager em) {
        this.em = em;
    }

    public Locacao criarOuAtualizar(Locacao locacao) {
        if(verificarDisponibilidadeImovel(locacao.getIdImovel())){
            if(locacao.getId() != null){
                return em.merge(locacao);
            }else {
                em.persist(locacao);
            }
        }
        return locacao;
    }


    public Locacao buscarPorId(Integer id) {
        return em.find(Locacao.class, id);
    }

    public List<Locacao> buscarTodos(Integer clienteId) {
        String jpql = "select l from Locacao l join l.idInquilino c where c.id = :clienteId";
        TypedQuery<Locacao> query = em.createQuery(jpql, Locacao.class);
        query.setParameter("clienteId", clienteId);
        List<Locacao> result = query.getResultList();
        System.out.println("Número de locações encontradas: " + result.size());
        return result;
    }
    public boolean verificarDisponibilidadeImovel(Imoveis imovelId) {
        String jpql = "select l from Locacao l where l.idImovel.id = :imovelId and l.ativo = true";
        TypedQuery<Locacao> query = em.createQuery(jpql, Locacao.class);
        query.setParameter("imovelId", imovelId);
        List<Locacao> result = query.getResultList();
        return result.isEmpty();
    }



}
