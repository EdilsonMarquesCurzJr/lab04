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
        if (locacao.getImovel() == null || locacao.getImovel().getId() == null) {
            System.out.println("Imóvel inválido.");
            return locacao;
        }

        boolean disponivel = verificarDisponibilidadeImovel(locacao.getImovel().getId());
        if (disponivel) {
            if (locacao.getId() != null) {
                locacao = em.merge(locacao);
            } else {
                em.persist(locacao);
            }
            locacao.setAtivo(true);
        } else {
            System.out.println("Imóvel não disponível para locação.");
            return locacao; // Retornar sem persistir
        }
        em.flush();
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
        System.out.println("Buscando todas as locações do cliente " + clienteId);
        System.out.println("Número de locações encontradas: " + result.size());
        return result;
    }

    public boolean verificarDisponibilidadeImovel(Integer imovelId) {
        String jpql = "select l from Locacao l where l.imovel.id = :imovelId and l.ativo = true";
        TypedQuery<Locacao> query = em.createQuery(jpql, Locacao.class);
        query.setParameter("imovelId", imovelId);
        List<Locacao> result = query.getResultList();
        boolean disponivel = result.isEmpty();
        System.out.println("Disponibilidade do imóvel com ID " + imovelId + ": " + disponivel);
        return disponivel;
    }

}
