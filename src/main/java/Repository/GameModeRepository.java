package Repository;
import Entity.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameModeRepository extends JpaRepository<GameMode, Long>{
}
