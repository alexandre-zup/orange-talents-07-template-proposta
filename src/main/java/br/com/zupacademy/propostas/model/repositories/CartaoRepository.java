package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.enums.EstadoCartao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartaoRepository extends JpaRepository<Cartao, String> {
    Page<Cartao> findAllByEstado(EstadoCartao estado, Pageable pageable);
}
