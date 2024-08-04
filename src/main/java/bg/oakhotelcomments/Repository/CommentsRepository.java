package bg.oakhotelcomments.Repository;

import bg.oakhotelcomments.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
    public List<CommentEntity> findAllByCreatorId(Long id);


}
