package lab.imobiliaria.Service;

import javax.persistence.EntityManager;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class PagamentoAluguelService {
    private final AlugueisRepository repositorio;
    private final EntityManager em;

    // talvez usar o entity manager factory do professor melhore a usabilidade na main
    public PagamentoAluguelService(EntityManager em) {
        this.em = em;
        this.repositorio = new AlugueisRepository(em);
    }

    public BigDecimal registrarPagamentoAluguel(Aluguel aluguel, Locacao locacao) {
        boolean transactionActive = em.getTransaction().isActive();
        try {
            if (!transactionActive) {
                em.getTransaction().begin();
            }

            BigDecimal valorAluguel = locacao.getValorAluguel();
            BigDecimal valorPago = valorAluguel;
            BigDecimal multa = BigDecimal.ZERO;

            aluguel.setIdLocacao(locacao);

            // Se pagamento após vencimento
            if (aluguel.getDataPagamento().isAfter(aluguel.getDataVencimento())) {
                long diasDeAtraso = ChronoUnit.DAYS.between(aluguel.getDataVencimento(), aluguel.getDataPagamento());
                multa = valorAluguel.multiply(BigDecimal.valueOf(0.0033)).multiply(BigDecimal.valueOf(diasDeAtraso));

                // Limita a multa a 20% do valor do aluguel
                BigDecimal limiteMulta = valorAluguel.multiply(BigDecimal.valueOf(0.20));
                if (multa.compareTo(limiteMulta) > 0) {
                    multa = limiteMulta;
                }

                valorPago = valorPago.add(multa);
            }

            aluguel.setValorPago(valorPago);

            repositorio.criarOuAtualizar(aluguel);

            if (!transactionActive) {
                em.getTransaction().commit();
            }
            return valorPago;
        } catch (Exception e) {
            if (!transactionActive) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
