package back.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import back.server.model.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    public Citizen findByPassportID(Long passportID);
}
