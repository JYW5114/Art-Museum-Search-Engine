package use_case.rating;

import entities.Artwork;

import java.util.List;

public class RatingInteractor implements RatingInputBoundary{
    private final RatingDataAccessInterface ratingDataAccessObject;
    private final RatingOutputBoundary ratingOutputBoundary;


    public RatingInteractor(RatingDataAccessInterface ratingDataAccessObject, RatingOutputBoundary ratingOutputBoundary) {
        this.ratingDataAccessObject = ratingDataAccessObject;
        this.ratingOutputBoundary = ratingOutputBoundary;
    }

    @Override
    public void execute(RatingInputData ratingOutputData) {
        int ratingValue = ratingOutputData.getRating();
        if (ratingValue < 1) {
            ratingValue = 1;
        } else if (ratingValue > 5) {
            ratingValue = 5;
        }
        //ratingDataAccessObject.incrementRatingCount(ratingValue);
        ratingDataAccessObject.setRating(ratingOutputData.getId(), ratingValue);
        RatingOutputData result = new RatingOutputData(ratingValue);
        ratingOutputBoundary.prepareRatingView(result);
    }

    @Override
    public List<Artwork> getRatedArtworks() {
        return ratingDataAccessObject.getRatedArtworks();
    }

}
