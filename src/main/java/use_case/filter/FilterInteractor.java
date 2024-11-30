package use_case.filter;

public class FilterInteractor implements FilterInputBoundary{

    private final FilterDataAccessInterface filterDataAccessObject;
    //private final FilterOutputBoundary filterPresenter;

    public FilterInteractor(FilterDataAccessInterface filterDataAccessObject) {
        this.filterDataAccessObject = filterDataAccessObject;
        //this.filterPresenter = filterPresenter;
    }

    @Override
    public void execute(FilterInputData filterInputData) {
        boolean failed = false;
        final String filter = filterInputData.getCurrentFilter();
        filterDataAccessObject.changeFilter(filter);
        //final FilterOutputData currentfilter = new FilterOutputData(filter, failed);
        //filterPresenter.prepareFilter(currentfilter);
    }
}
