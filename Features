import java.awt.*;
import java.awt.event.*;
import java.util.*;

class RecipeRecommenderSystem {

    private Frame mainFrame;
    private CardLayout cardLayout;
    private Panel contentPanel;

    private TextField userNameField, timerField, feedbackField;
    private Choice dietChoice;
    private Checkbox tomatoCheck, spinachCheck, chickenCheck, cheeseCheck;
    private Label recipeLabel, timerDisplayLabel;
    private Button startTimerButton, stopTimerButton, nextButton, prevButton;
    private int remainingTime;
    private Timer cookingTimer;

    // Constructor to set up the main frame
    public RecipeRecommenderSystem() {
        mainFrame = new Frame("Recipe Recommender System");
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contentPanel = new Panel(cardLayout);

        setupLoginScreen();
        setupDietaryPreferenceScreen();
        setupFavoritesScreen();
        setupRecipeSuggestionScreen();
        setupTimerScreen();
        setupFeedbackScreen();

        mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
    }

    // Setup Login Screen
    private void setupLoginScreen() {
        Panel loginPanel = new Panel(new FlowLayout());
        Label label = new Label("Enter Your Name:");
        userNameField = new TextField(20);
        nextButton = new Button("Next");

        nextButton.addActionListener(e -> {
            if (!userNameField.getText().isEmpty()) {
                cardLayout.next(contentPanel);
            } else {
                showMessage("Please enter a name.");
            }
        });

        loginPanel.add(label);
        loginPanel.add(userNameField);
        loginPanel.add(nextButton);
        contentPanel.add(loginPanel, "Login");
    }

    // Setup Dietary Preference Screen
    private void setupDietaryPreferenceScreen() {
        Panel dietPanel = new Panel(new FlowLayout());
        Label label = new Label("Select Dietary Preferences:");
        dietChoice = new Choice();
        dietChoice.add("Vegetarian");
        dietChoice.add("Vegan");
        dietChoice.add("Non-Vegetarian");
        dietChoice.add("Gluten-Free");

        nextButton = new Button("Next");
        nextButton.addActionListener(e -> cardLayout.next(contentPanel));

        dietPanel.add(label);
        dietPanel.add(dietChoice);
        dietPanel.add(nextButton);
        contentPanel.add(dietPanel, "DietaryPreference");
    }

    // Setup Favorites Screen
    private void setupFavoritesScreen() {
        Panel favoritesPanel = new Panel(new FlowLayout());
        Label label = new Label("Select Your Favorite Ingredients:");

        // Replacing List with Checkboxes for easier selection
        tomatoCheck = new Checkbox("Tomato");
        spinachCheck = new Checkbox("Spinach");
        chickenCheck = new Checkbox("Chicken");
        cheeseCheck = new Checkbox("Cheese");

        nextButton = new Button("Next");
        nextButton.addActionListener(e -> cardLayout.next(contentPanel));

        favoritesPanel.add(label);
        favoritesPanel.add(tomatoCheck);
        favoritesPanel.add(spinachCheck);
        favoritesPanel.add(chickenCheck);
        favoritesPanel.add(cheeseCheck);
        favoritesPanel.add(nextButton);
        contentPanel.add(favoritesPanel, "Favorites");
    }

    // Setup Recipe Suggestion Screen
    private void setupRecipeSuggestionScreen() {
        Panel recipePanel = new Panel(new FlowLayout());
        recipeLabel = new Label("Suggested Recipe: Spaghetti with Tomato");

        nextButton = new Button("Next");
        nextButton.addActionListener(e -> cardLayout.next(contentPanel));

        recipePanel.add(recipeLabel);
        recipePanel.add(nextButton);
        contentPanel.add(recipePanel, "RecipeSuggestion");
    }

    // Setup Timer Screen
    private void setupTimerScreen() {
        Panel timerPanel = new Panel(new FlowLayout());
        Label timerLabel = new Label("Set Cooking Timer (seconds):");
        timerField = new TextField("60", 5);
        timerDisplayLabel = new Label("Time remaining: Not started");

        startTimerButton = new Button("Start Timer");
        stopTimerButton = new Button("Stop Timer");

        startTimerButton.addActionListener(e -> startCookingTimer());
        stopTimerButton.addActionListener(e -> stopCookingTimer());

        timerPanel.add(timerLabel);
        timerPanel.add(timerField);
        timerPanel.add(startTimerButton);
        timerPanel.add(stopTimerButton);
        timerPanel.add(timerDisplayLabel);
        contentPanel.add(timerPanel, "Timer");
    }

    // Setup Feedback Screen
    private void setupFeedbackScreen() {
        Panel feedbackPanel = new Panel(new FlowLayout());
        Label feedbackLabel = new Label("Leave Feedback:");
        feedbackField = new TextField(20);
        Button submitFeedbackButton = new Button("Submit");

        submitFeedbackButton.addActionListener(e -> showMessage("Feedback submitted: " + feedbackField.getText()));

        feedbackPanel.add(feedbackLabel);
        feedbackPanel.add(feedbackField);
        feedbackPanel.add(submitFeedbackButton);
        contentPanel.add(feedbackPanel, "Feedback");
    }

    // Start the cooking timer
    private void startCookingTimer() {
        try {
            remainingTime = Integer.parseInt(timerField.getText());
            cookingTimer = new Timer();
            cookingTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (remainingTime > 0) {
                        timerDisplayLabel.setText("Time remaining: " + remainingTime-- + " seconds");
                    } else {
                        cookingTimer.cancel();
                        timerDisplayLabel.setText("Cooking time is up!");
                    }
                }
            }, 0, 1000);
        } catch (NumberFormatException ex) {
            showMessage("Enter a valid number for the timer.");
        }
    }

    // Stop the cooking timer
    private void stopCookingTimer() {
        if (cookingTimer != null) {
            cookingTimer.cancel();
            timerDisplayLabel.setText("Timer stopped.");
        }
    }

    // Utility method to show a message dialog
    private void showMessage(String message) {
        Dialog dialog = new Dialog(mainFrame, "Message", true);
        dialog.setLayout(new FlowLayout());
        Label msgLabel = new Label(message);
        Button okButton = new Button("OK");
        okButton.addActionListener(e -> dialog.setVisible(false));

        dialog.add(msgLabel);
        dialog.add(okButton);
        dialog.setSize(250, 100);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new RecipeRecommenderSystem();
    }
}
