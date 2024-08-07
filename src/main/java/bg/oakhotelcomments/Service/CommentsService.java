package bg.oakhotelcomments.Service;

import bg.oakhotelcomments.Repository.CommentsRepository;
import bg.oakhotelcomments.model.dto.AddCommentDTO;
import bg.oakhotelcomments.model.dto.CommentDTO;
import bg.oakhotelcomments.model.dto.EditCommentDTO;
import bg.oakhotelcomments.model.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Optional;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper mapper;

    public CommentsService(CommentsRepository commentsRepository, ModelMapper mapper) {
        this.commentsRepository = commentsRepository;
        this.mapper = mapper;
    }

    public CommentEntity addComment(AddCommentDTO data){
        CommentEntity comment = new CommentEntity();
        comment.setDescription(data.getDescription());
        comment.setCreatorId(data.getCreatorId());
        commentsRepository.saveAndFlush(comment);
        return comment;
    }

    public List<CommentDTO> getCommentsByUserId(Long id) {
        return commentsRepository.findAllByCreatorId(id).stream().map(x-> mapper.map(x,CommentDTO.class)).toList();
    }

    public List<CommentDTO> getAllComments(){
        return commentsRepository.findAll().stream().map(x->mapper.map(x,CommentDTO.class)).toList();
    }
    public Optional<CommentEntity> findById(Long id){
        return commentsRepository.findById(id);
    }
    public boolean editComment(EditCommentDTO data, Long id){
        Optional<CommentEntity> comment  = commentsRepository.findById(id);
        if(comment.isPresent()){
            comment.get().setDescription(data.getDescription());
            commentsRepository.save(comment.get());
            return true;
        }
        return false;
    }

    public boolean removeById(Long id) {
        Optional<CommentEntity> entity = commentsRepository.findById(id);
        if(entity.isPresent()){
            commentsRepository.delete(entity.get());
            return true;
        }
        else return false;

    }
}
