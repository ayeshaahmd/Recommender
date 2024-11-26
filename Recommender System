import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class RecipeRecommenderSystem extends Frame {
    private CardLayout cardLayout = new CardLayout();
    private Panel containerPanel = new Panel(cardLayout);
    private TextField usernameField = new TextField(20);
    private Choice dietaryChoice = new Choice();
    private TextArea messageArea = new TextArea(10, 40);
    private Button loginButton = new Button("Login");
    private Button suggestButton = new Button("Get Recipe Suggestions");
    private Button rateButton = new Button("Rate Recipe");
    private Button saveFeedbackButton = new Button("Save Feedback");
    private TextField feedbackField = new TextField(20);
    private String loggedInUser = "";

    private java.util.List<Recipe> recipes = new ArrayList<>();

    public RecipeRecommenderSystem() {
        setTitle("Recipe Recommender System");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Initialize panels
        Panel loginPanel = createLoginPanel();
        Panel dashboardPanel = createDashboardPanel();

        // Add panels to card layout container
        containerPanel.add(loginPanel, "Login");
        containerPanel.add(dashboardPanel, "Dashboard");

        add(containerPanel, BorderLayout.CENTER);
        loadRecipes();

        // Window closing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    private Panel createLoginPanel() {
        Panel panel = new Panel(new GridLayout(5, 1));

        panel.add(new Label("Enter Username:"));
        panel.add(usernameField);

        panel.add(new Label("Select Dietary Preference:"));
        dietaryChoice.add("None");
        dietaryChoice.add("Vegetarian");
        dietaryChoice.add("Vegan");
        dietaryChoice.add("Gluten-Free");
        dietaryChoice.add("Other");
        panel.add(dietaryChoice);

        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        return panel;
    }

    private Panel createDashboardPanel() {
        Panel panel = new Panel(new BorderLayout());
        Panel topPanel = new Panel(new FlowLayout());
        Panel bottomPanel = new Panel(new FlowLayout());

        topPanel.add(new Label("Recipe Suggestions and Messages:"));
        topPanel.add(messageArea);
        messageArea.setEditable(false);

        bottomPanel.add(suggestButton);
        bottomPanel.add(new Label("Rate Recipe (1-5):"));
        bottomPanel.add(feedbackField);
        bottomPanel.add(rateButton);
        bottomPanel.add(saveFeedbackButton);

        suggestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suggestRecipes();
            }
        });

        rateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rateRecipe();
            }
        });

        saveFeedbackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFeedback();
            }
        });

        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void handleLogin() {
        loggedInUser = usernameField.getText();
        String dietaryPreference = dietaryChoice.getSelectedItem();

        if (loggedInUser.isEmpty()) {
            showMessage("Username cannot be empty.");
            return;
        }

        saveUserPreferences(loggedInUser, dietaryPreference);
        showMessage("Welcome, " + loggedInUser + "! Your dietary preference is saved as " + dietaryPreference + ".");
        cardLayout.show(containerPanel, "Dashboard");
    }

    private void suggestRecipes() {
        StringBuilder suggestions = new StringBuilder("Recipe Suggestions:\n");

        for (Recipe recipe : recipes) {
            suggestions.append(recipe.toString()).append("\n");
        }

        showMessage(suggestions.toString());
    }

    private void rateRecipe() {
        String feedback = feedbackField.getText();
        try {
            int rating = Integer.parseInt(feedback);
            if (rating < 1 || rating > 5) {
                showMessage("Please enter a rating between 1 and 5.");
            } else {
                showMessage("Thank you for rating!");
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid rating input. Please enter a number between 1 and 5.");
        }
    }

    private void saveFeedback() {
        try (FileWriter writer = new FileWriter("ratings.txt", true)) {
            writer.write(loggedInUser + " rated: " + feedbackField.getText() + "\n");
            showMessage("Feedback saved.");
        } catch (IOException e) {
            showMessage("Failed to save feedback.");
        }
    }

    private void saveUserPreferences(String username, String dietaryPreference) {
        try (FileWriter writer = new FileWriter("preferences.txt", true)) {
            writer.write(username + "," + dietaryPreference + "\n");
        } catch (IOException e) {
            showMessage("Failed to save preferences.");
        }
    }

    private void showMessage(String message) {
        messageArea.setText(message);
    }

    private void loadRecipes() {
        // Example recipes are added manually for this demo
        recipes.add(new Recipe("Vegetable Stir Fry", "Vegan"));
        recipes.add(new Recipe("Chicken Salad", "Gluten-Free"));
        recipes.add(new Recipe("Pasta Alfredo", "Vegetarian"));
    }

    public static void main(String[] args) {
        new RecipeRecommenderSystem();
    }

    // Recipe class to represent recipes
    private static class Recipe {
        private String name;
        private String dietaryType;

        public Recipe(String name, String dietaryType) {
            this.name = name;
            this.dietaryType = dietaryType;
        }

        @Override
        public String toString() {
            return name + " (" + dietaryType + ")";
        }
    }
}
