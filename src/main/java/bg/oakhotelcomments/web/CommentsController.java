package bg.oakhotelcomments.web;

import bg.oakhotelcomments.Service.CommentsService;
import bg.oakhotelcomments.model.dto.AddCommentDTO;
import bg.oakhotelcomments.model.dto.CommentDTO;
import bg.oakhotelcomments.model.entity.CommentEntity;
import java.util.Optional;

import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.MemberSubstitution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final ModelMapper mapper;

    public CommentsController(CommentsService commentsService, ModelMapper mapper) {
        this.commentsService = commentsService;
        this.mapper = mapper;
    }

    @PostMapping("/")
    private ResponseEntity<AddCommentDTO> addComment(@RequestBody AddCommentDTO addCommentDTO){
        commentsService.addComment(addCommentDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/")
    private ResponseEntity<List<CommentDTO>> viewAllComments(){
        return ResponseEntity.ok(commentsService.getAllComments());
    }
    @GetMapping("/user/{id}")
    private ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable Long id){
        return ResponseEntity.ok(commentsService.getCommentsByUserId(id));
    }
    @PostMapping("/{id}")
    private ResponseEntity<CommentDTO> editCommentById(@PathVariable Long id, @RequestBody CommentDTO data){
        Optional<CommentEntity> byId = commentsService.findById(id);
        if(byId.isPresent()){
            commentsService.editComment(byId.get(),data);
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id){
        Optional<CommentEntity> byId = commentsService.findById(id);
        if(byId.isPresent()){
            return ResponseEntity.ok(mapper.map(byId.get(),CommentDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<CommentDTO> removeCommentById(@PathVariable Long id){
        Optional<CommentEntity> byId = commentsService.findById(id);
        if(byId.isPresent()){
            commentsService.removeById(id);
            return ResponseEntity.ok(mapper.map(byId.get(),CommentDTO.class));
        }
        return ResponseEntity.notFound().build();
    }
}

