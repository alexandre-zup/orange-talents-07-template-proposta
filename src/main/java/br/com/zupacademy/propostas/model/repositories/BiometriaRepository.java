package br.com.zupacademy.propostas.model.repositories;

import br.com.zupacademy.propostas.model.entities.Biometria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiometriaRepository extends JpaRepository<Biometria, Long> {
}
