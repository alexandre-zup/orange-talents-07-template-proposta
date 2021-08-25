package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumentoHash(String documento);

    List<Proposta> findAllByEstado(EstadoProposta estado);
}
