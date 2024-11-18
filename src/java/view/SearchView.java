package view;

import interface_adapters.click_art.ClickArtViewModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static use_case.search.SearchInteractor.searchArtwork;

/**
 * The View for the Search Use Case.
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "search";

    private final SearchViewModel searchViewModel;
    private final JTextField keywordInputField = new JTextField(15);
    private final JTextArea searchResultsArea = new JTextArea(10, 30);
    private final SearchController searchController;
    private final ClickArtViewModel clickArtViewModel;

    private final JButton searchButton;
    private final JButton clearButton;
    private JPanel panelPictures;
    //private final JButton nextButton;
    //private final JButton detailButton;


    public SearchView(SearchController controller, SearchViewModel searchViewModel, ClickArtViewModel clickArtViewModel) {
        this.searchController = controller;
        this.searchViewModel = searchViewModel;
        this.clickArtViewModel = clickArtViewModel;
        //this.nextButton = nextButton;
        //this.detailButton = detailButton;
        searchViewModel.addPropertyChangeListener(this);

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        final LabelTextPanel keywordInfo = new LabelTextPanel(
                new JLabel("Search Keywords:"), keywordInputField);

        keywordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(keywordInfo);
        JPanel panelPictures = new JPanel();
        // Panel for action buttons
        final JPanel buttons = new JPanel();
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // clear previous search results
                if (!(panelPictures.getComponents() == null)) {
                    panelPictures.removeAll();
                }

                String searchword = keywordInputField.getText();
                List<Artwork> all = searchArtwork(searchword, "Hasimages");
                StringBuilder artworks = new StringBuilder();
                for (Artwork art: all) {
                    artworks.append(art.getTitle() + "\n");
                    try {
                        URI newuri = new URI(art.getImageUrl());
                        // System.out.println(newuri);
                        ImageIcon imageIcon;
                        if (newuri.isAbsolute()) {
                            imageIcon = new ImageIcon(newuri.toURL());
                        } else {
                            imageIcon = new ImageIcon(art.getImageUrl());
                        } // load the image to a imageIcon
                        Image image = imageIcon.getImage(); // transform it
                        Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                        imageIcon = new ImageIcon(newimg);  // transform it back
                        JLabel imagelabel = new JLabel(imageIcon);
                        // Add a mouse listener to the image label for clicks
                        final Artwork artwork = art; // Final reference for use in the mouse listener
                        imagelabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                // When image is clicked, set selected artwork and switch to ClickView
                                clickArtViewModel.setSelectedArtwork(artwork);
                                clickArtViewModel.firePropertyChanged();
                                // Switch to ClickView
                                // This assumes you are using CardLayout for view switching
                                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                                cardLayout.show(getParent(), "ClickView");
                            }
                        });
                        panelPictures.add(imagelabel);
                    } catch (URISyntaxException | MalformedURLException ew) {
                        throw new RuntimeException(ew);
                    }
                }
                repaint();
                revalidate();
            }
        });
        buttons.add(searchButton);
        buttons.add(clearButton);

        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Set up search results area
        searchResultsArea.setEditable(false);
        searchResultsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        /**
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         */

        // Add action listeners
        /**
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                final String keyword = keywordInputField.getText();
                searchController.executeSearch(keyword);
            }
        });
         */

        clearButton.addActionListener(this);

        // Add listeners to input fields
        addKeywordListener();

        // Arrange components in layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(inputPanel);
        this.add(buttons);
        JLabel searchResults = new JLabel("Search Results:");
        searchResults.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(searchResults);
        // this.add(scrollPane);
        this.add(panelPictures);
    }


    private void addKeywordListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        keywordInputField.setText("");
        searchResultsArea.setText("");
        panelPictures.removeAll(); // Clear images
        revalidate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public String getViewName() {
        return viewName;
    }
}