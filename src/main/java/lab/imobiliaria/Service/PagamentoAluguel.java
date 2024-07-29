package lab.imobiliaria.Service;

import javax.persistence.EntityManager;

import lab.imobiliaria.Entity.Aluguel;
import lab.imobiliaria.Entity.Locacao;
import lab.imobiliaria.Repository.AlugueisRepository;
import lab.imobiliaria.Repository.LocacaoRepository;

import java.math.BigDecimal;
import java.time.Period;

public class PagamentoAluguel {
    private final AlugueisRepository repositorio;
    private final EntityManager em;

    //talvez usar o entity manager factory do professor melhore a usabilidade na main
    public PagamentoAluguel(EntityManager em) {
        this.em = em;
        this.repositorio = new AlugueisRepository(em);

    }

    public BigDecimal registrarPagamentoAluguel (Aluguel aluguel, Locacao locacao ) {
        try{
            em.getTransaction().begin();
            BigDecimal valorPago = locacao.getValorAluguel();

            aluguel.setIdLocacao(locacao);
            //se pagamento depois do vencimento
            if(aluguel.getDataPagamento().compareTo(aluguel.getDataVencimento()) > 0){
                Period diasDeAtraso = Period.between(aluguel.getDataPagamento(), aluguel.getDataVencimento());
                        //for() pra limitar em 20%
                multa = valorPago * 0.33 * diasDeAtraso;
            }

            aluguel = repositorio.criarOuAtualizar(aluguel);


            em.getTransaction().commit();
            return valorPago;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
/*
- Implementação de um serviço para o registro de pagamentos do aluguel
    Registrar Pagamento de Aluguel: Permitir a associação de pagamentos de aluguel a uma locação.
        Valor a ser pago com multa:
            Dado uma data de vencimento e a data de pagamento, calcule o valor a ser pago, incluindo a multa.
            Se o pagamento estiver dentro do prazo, deverá ser retornado o valor do aluguel sem acréscimo de multas.
    OBS 1: A multa é calculada da seguinte forma: 0,33% por dia de atraso, limitada a 20% do valor do aluguel.
* */