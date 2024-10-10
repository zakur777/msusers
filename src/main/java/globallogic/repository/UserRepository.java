package globallogic.repository;

import globallogic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByToken(String token);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.token = :token WHERE u.id = :userId")
    int updateToken(@Param("userId") UUID userId,@Param("token") String token);
}
