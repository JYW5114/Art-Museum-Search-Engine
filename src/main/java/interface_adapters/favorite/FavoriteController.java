package interface_adapters.favorite;

import entities.Artwork;
import use_case.favorite.FavoriteInputBoundary;
import use_case.favorite.FavoriteInputData;

import java.io.IOException;
import java.util.List;

public class FavoriteController {
    private final FavoriteInputBoundary favoriteUseCaseInteractor;

    public FavoriteController(FavoriteInputBoundary favoriteUseCaseInteractor) {
        this.favoriteUseCaseInteractor = favoriteUseCaseInteractor;
    }

    public void execute(Artwork artwork) throws IOException {
        final FavoriteInputData favoriteInputData = new FavoriteInputData(
                artwork.getId());

        favoriteUseCaseInteractor.execute(favoriteInputData);
    }

    public void goToFavorite(){
        favoriteUseCaseInteractor.getFavoriteArtworks();
    }

    public List<Artwork> getFavorites() {
        return favoriteUseCaseInteractor.getFavorites();
    }
}
