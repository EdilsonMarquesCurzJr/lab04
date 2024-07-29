package lab.imobiliaria;

import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab_jpa");
        EntityManager em = emf.createEntityManager();
        LocacaoRepository locacaoRepository = new LocacaoRepository(em);

        try {
            // Testar criarOuAtualizar
            Locacao locacao = new Locacao();
            locacao.setId(1); // Assumindo que você está usando IDs auto-gerados, remova essa linha se não for o caso
            locacao.setValorAluguel(new BigDecimal("1300.00"));
            locacao.setAtivo(false);
            locacaoRepository.criarOuAtualizar(locacao);

            // Testar buscarPorId
            Locacao locacaoEncontrada = locacaoRepository.buscarPorId(1);
            System.out.println("Locação encontrada: " + locacaoEncontrada);

            // Testar buscarTodos
            List<Locacao> locacoes = locacaoRepository.buscarTodos(1); // Substitua 1 pelo ID do cliente real
            System.out.println("Locações encontradas: " + locacoes);

            // Testar verificarDisponibilidadeImovel
            boolean disponivel = locacaoRepository.verificarDisponibilidadeImovel(1); // Substitua 1 pelo ID do imóvel real
            System.out.println("Imóvel disponível: " + disponivel);

        } finally {
            em.close();
            emf.close();
        }
    }
}
