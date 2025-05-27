package org.clinicaOndot.paciente;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface PacienteRepository extends PanacheRepository<Paciente> {
}
