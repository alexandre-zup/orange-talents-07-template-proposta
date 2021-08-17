package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumento(String documento);

    List<Proposta> findAllByEstadoAndCartao(EstadoProposta estado, Long cartaoId);
}
