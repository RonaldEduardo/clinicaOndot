package org.clinicaOndot.operador;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;

@ApplicationScoped
public interface OperadorRepository extends PanacheRepository<Operador> {
}
