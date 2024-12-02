import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RecipeRecommender {

    private JFrame frame;
    private JTextField usernameField;
    private JComboBox<String> dietaryComboBox;
    private JTextArea recipeDisplayArea;
    private JTextArea groceryListArea;
    private JPanel mainPanel;
    private JPanel usernamePanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private Timer timer;
    private int timeRemaining; // in seconds
    private JTextField minutesField;
    private JTextField secondsField;
    private JLabel timerLabel;

    private String username; // To hold the username

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecipeRecommender().showUsernameScreen());
    }

    public RecipeRecommender() {
        // Constructor
    }

    public void showUsernameScreen() {
        frame = new JFrame("Recipe Recommender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(true);

        usernamePanel = new BackgroundPanel("image2.jpg"); // Replace with your image path
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = createLabel("Welcome to Savory Sense!", 24, Color.BLACK);
        JLabel usernameLabel = createLabel("Enter Username:", 16, Color.BLACK);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        usernameField.setMaximumSize(new Dimension(250, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton continueButton = createButton("Login", 18, e -> handleUsernameInput());

        usernamePanel.add(Box.createVerticalStrut(200));
        usernamePanel.add(welcomeLabel);
        usernamePanel.add(Box.createVerticalStrut(20));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createVerticalStrut(10));
        usernamePanel.add(usernameField);
        usernamePanel.add(Box.createVerticalStrut(20));
        usernamePanel.add(continueButton);

        frame.add(usernamePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleUsernameInput() {
        username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            saveUsername(username); // Save username to a file
            JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            showMainScreen();
        }
    }

    private void saveUsername(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("username.txt"))) {
            writer.write(username);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showMainScreen() {
        frame.remove(usernamePanel);

        mainPanel = new BackgroundPanel("image2.jpg"); // Replace with your image path
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel sidebar = createSidebar();

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        JPanel recipePanel = new JPanel(new BorderLayout());
        recipeDisplayArea = new JTextArea();
        recipeDisplayArea.setEditable(false);
        recipeDisplayArea.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        recipePanel.add(new JScrollPane(recipeDisplayArea), BorderLayout.CENTER);

        JPanel groceryPanel = new JPanel(new BorderLayout());
        groceryListArea = new JTextArea();
        groceryListArea.setEditable(false);
        groceryListArea.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        groceryPanel.add(new JScrollPane(groceryListArea), BorderLayout.CENTER);

        JPanel timerPanel = createTimerPanel();

        contentPanel.add(recipePanel, "recipes");
        contentPanel.add(groceryPanel, "groceryList");
        contentPanel.add(timerPanel, "timer");

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(216, 191, 216));
        sidebar.setPreferredSize(new Dimension(250, 600));

        JLabel appTitle = createLabel("Recipe Recommender", 21, Color.WHITE);
        JLabel dietaryLabel = createLabel("Select Dietary Preferences", 16, Color.WHITE);

        dietaryComboBox = new JComboBox<>(new String[] {
                "None", "Vegetarian", "Non-Vegan", "Gluten-Free", "Low-Carb", "High-Protein"
        });
        dietaryComboBox.setMaximumSize(new Dimension(150, 30));
        dietaryComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton recommendButton = createButton("Recommend Recipes", 16, e -> {
            cardLayout.show(contentPanel, "recipes");
            recommendRecipes();
        });

        JButton generateGroceryListButton = createButton("Grocery List", 16, e -> {
            cardLayout.show(contentPanel, "groceryList");
            generateGroceryList();
        });

        JButton timerButton = createButton("Cooking Timer", 16, e -> {
            cardLayout.show(contentPanel, "timer");
        });

        JButton feedbackButton = createButton("Submit Feedback", 16, e -> showFeedbackDialog());

        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(appTitle);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(dietaryLabel);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(dietaryComboBox);
        sidebar.add(Box.createVerticalStrut(30));
        sidebar.add(recommendButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(generateGroceryListButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(timerButton);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(feedbackButton);

        return sidebar;
    }

    private JPanel createTimerPanel() {
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBackground(new Color(211, 211, 211));

        JLabel timerTitle = createLabel("Cooking Timer", 24, Color.BLACK);

        minutesField = new JTextField("00", 2);
        secondsField = new JTextField("00", 2);
        minutesField.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        secondsField.setFont(new Font("Sans Serif", Font.PLAIN, 36));
        minutesField.setHorizontalAlignment(JTextField.CENTER);
        secondsField.setHorizontalAlignment(JTextField.CENTER);

        JPanel timeInputPanel = new JPanel();
        timeInputPanel.add(new JLabel("Minutes: "));
        timeInputPanel.add(minutesField);
        timeInputPanel.add(Box.createHorizontalStrut(10));
        timeInputPanel.add(new JLabel("Seconds: "));
        timeInputPanel.add(secondsField);

        timerLabel = createLabel("00:00", 36, Color.BLACK);

        JButton startButton = createButton("Start", 16, e -> startTimer());
        JButton pauseButton = createButton("Pause", 16, e -> pauseTimer());
        JButton resetButton = createButton("Reset", 16, e -> resetTimer());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);

        timerPanel.add(timerTitle);
        timerPanel.add(Box.createVerticalStrut(20));
        timerPanel.add(timeInputPanel);
        timerPanel.add(Box.createVerticalStrut(20));
        timerPanel.add(timerLabel);
        timerPanel.add(Box.createVerticalStrut(20));
        timerPanel.add(buttonPanel);

        return timerPanel;
    }

    private void startTimer() {
        int minutes = Integer.parseInt(minutesField.getText());
        int seconds = Integer.parseInt(secondsField.getText());
        timeRemaining = minutes * 60 + seconds;

        timer = new Timer(1000, e -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                updateTimerLabel();
            } else {
                ((Timer) e.getSource()).stop();
                JOptionPane.showMessageDialog(frame, "Time's up!", "Timer", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        timer.start();
    }

    private void pauseTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        minutesField.setText("00");
        secondsField.setText("00");
        timerLabel.setText("00:00");
    }

    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void recommendRecipes() {
        String selectedDiet = (String) dietaryComboBox.getSelectedItem();
        File file = new File("recipes.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "Recipe file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(selectedDiet.toLowerCase())) {
                    builder.append(line).append("\n");
                }
            }
            recipeDisplayArea.setText(builder.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading recipes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateGroceryList() {
        groceryListArea
                .setText("Here are the ingredients you need to buy:\n- Onion \n- Potatoes \n- Tomato  \n-Chicken");
    }

    private void showFeedbackDialog() {
        String feedback = JOptionPane.showInputDialog(frame, "Enter your feedback:", "Feedback",
                JOptionPane.QUESTION_MESSAGE);
        if (feedback != null && !feedback.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Thank you for your feedback!", "Feedback Received",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JLabel createLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans Serif", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, int fontSize, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans Serif", Font.BOLD, fontSize));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        return button;
    }

    // Background Panel Class
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error loading background image.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
