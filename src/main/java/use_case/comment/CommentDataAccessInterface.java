package use_case.comment;

import entities.Artwork;
import java.io.IOException;

public interface CommentDataAccessInterface {
    void addCommentToArtwork(Artwork artwork, String comment) throws IOException;
    Artwork getArtworkById(String id) throws IOException;
}
