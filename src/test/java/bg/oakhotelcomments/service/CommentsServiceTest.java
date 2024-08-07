package bg.oakhotelcomments.service;

import bg.oakhotelcomments.Repository.CommentsRepository;
import bg.oakhotelcomments.Service.CommentsService;
import bg.oakhotelcomments.model.dto.AddCommentDTO;
import bg.oakhotelcomments.model.dto.CommentDTO;
import bg.oakhotelcomments.model.dto.EditCommentDTO;
import bg.oakhotelcomments.model.entity.CommentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {
    CommentsService toTest;
    final Long USER_ID = 1l;
    final Long COMMENT_ID = 1l;

    @Mock
    CommentsRepository mockCommentsRepository;

    @BeforeEach
    void toTest(){
        toTest = new CommentsService(mockCommentsRepository, new ModelMapper());
    }
    @Test
    void testAddsComment(){
        AddCommentDTO data = new AddCommentDTO();
        data.setCreatorId(1L);
        data.setDescription("test");

        CommentEntity expected = new CommentEntity();
        expected.setId(1L);
        expected.setDescription("test");
        expected.setCreatorId(USER_ID);

        CommentEntity actual = toTest.addComment(data);
        Assertions.assertTrue(expected.getDescription().equals(actual.getDescription()));
    }
    @Test
    void getCommentsByUserIdReturns(){
        CommentEntity entity = new CommentEntity();
        entity.setId(1L);
        entity.setCreatorId(USER_ID);
        entity.setDescription("test");

        when(mockCommentsRepository.findAllByCreatorId(USER_ID)).thenReturn(List.of(entity));

        List<CommentDTO> actual = toTest.getCommentsByUserId(USER_ID);

        Assertions.assertTrue(actual.get(0).getDescription().equals(entity.getDescription()));
    }
    @Test
    void getAllCommentsReturns(){
        CommentEntity entity = new CommentEntity();
        entity.setId(1L);
        entity.setCreatorId(USER_ID);
        entity.setDescription("test");

        when(mockCommentsRepository.findAll()).thenReturn(List.of(entity));

        Assertions.assertTrue(toTest.getAllComments().size()>0);
    }
    @Test
    void findByIdReturns(){
        CommentEntity entity = new CommentEntity();
        entity.setId(1L);
        entity.setCreatorId(USER_ID);
        entity.setDescription("test");

        when(mockCommentsRepository.findById(USER_ID)).thenReturn(Optional.of(entity));

        Assertions.assertTrue(toTest.findById(USER_ID).get().getDescription().equals(entity.getDescription()));
    }
    @Test
    void editCommentEdits(){
        EditCommentDTO data = new EditCommentDTO();
        data.setDescription("test");

        CommentEntity entity = new CommentEntity();
        entity.setId(1L);
        entity.setCreatorId(USER_ID);
        entity.setDescription("test");

        when(mockCommentsRepository.findById(COMMENT_ID)).thenReturn(Optional.of(entity));

        Assertions.assertTrue(toTest.editComment(data,COMMENT_ID));
    }
    @Test
    void editCommentDoesNotEdit(){
        EditCommentDTO data = new EditCommentDTO();
        data.setDescription("test");

        when(mockCommentsRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        Assertions.assertFalse(toTest.editComment(data,COMMENT_ID));
    }
    @Test
    void deleteCommentDeletes(){
        CommentEntity entity = new CommentEntity();
        entity.setId(1L);
        entity.setCreatorId(USER_ID);
        entity.setDescription("test");

        when(mockCommentsRepository.findById(USER_ID)).thenReturn(Optional.of(entity));

        Assertions.assertTrue(toTest.removeById(USER_ID));
    }
    @Test
    void deleteCommentDoesNotDelete(){
        when(mockCommentsRepository.findById(USER_ID)).thenReturn(Optional.empty());

        Assertions.assertFalse(toTest.removeById(USER_ID));
    }
}
