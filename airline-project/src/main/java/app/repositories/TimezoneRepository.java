package app.repositories;

import app.entities.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimezoneRepository extends JpaRepository<Timezone, Long> {
}