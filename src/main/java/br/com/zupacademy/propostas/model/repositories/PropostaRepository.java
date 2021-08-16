package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumento(String documento);
}
