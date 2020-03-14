package UI;

import domain.WebInfo;
import domain.Webpage;
import exceptions.ParameterIsNotJSONStringException;
import utils.JSONStringParser;
import utils.JSONStringRetriever;
import utils.Sorter;
import utils.WebpageBuilder;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RevisionViewerUI extends JFrame implements ActionListener {

    public RevisionViewerUI() {
        super("Wikipedia Revision Viewer");

        // Make fonts bigger in the whole app
        UIManager.put("Label.font", new FontUIResource(new Font("Roboto", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Roboto", Font.PLAIN, 20)));

        //Create grid panel that buttons and label will go into
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        //Input Label
        JLabel inputLabel = new JLabel("Input:");
        var inputLabelConstraints = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(inputLabel, inputLabelConstraints);

        //Title Label
        JLabel titleLabel = new JLabel("Title:");
        var titleLabelConstraints = new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(titleLabel, titleLabelConstraints);

        //Redirected Label
        JLabel redirectedLabel = new JLabel("Redirected:");
        var redirectedLabelConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(redirectedLabel, redirectedLabelConstraints);

        //From Label
        JLabel fromLabel = new JLabel("From:");
        var fromLabelConstraints = new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(fromLabel, fromLabelConstraints);

        //To Label
        JLabel toLabel = new JLabel("To:");
        var toLabelConstraints = new GridBagConstraints(4, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(toLabel, toLabelConstraints);

        //30Recent Label
        JLabel recentLabel = new JLabel("30 Recent: ");
        var recentLabelConstraints = new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(recentLabel, recentLabelConstraints);

        //MostEdits Label
        JLabel editsNumLabel = new JLabel("Number of Edits:");
        var editsNumLabelConstraints = new GridBagConstraints(3, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(editsNumLabel, editsNumLabelConstraints);

        //Error Label
        JLabel errorLabel = new JLabel("Error:");
        var errorLabelConstraints = new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(errorLabel, errorLabelConstraints);

        //ErrorDisplay Label
        JLabel errorDisplayLabel = new JLabel("");
        var errorDisplayLabelConstraints = new GridBagConstraints(1, 4, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(errorDisplayLabel, errorDisplayLabelConstraints);

        //Make the input window
        JTextField txtInput = new JTextField("");
        var txtInputConstraints = new GridBagConstraints(1, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);;
        panel.add(txtInput, txtInputConstraints);

        //Make empty Jlist for timestamps
        JList<Object> timestampArray = new JList<>();
        var timestampArrayConstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(timestampArray, timestampArrayConstraints);

        //Make empty Jlist for usernames
        JList<Object> usernameArray = new JList<>();
        var usernameArrayConstraints = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(usernameArray, usernameArrayConstraints);

        //Make empty Jlist for editsUsernames
        JList<Object> usernameEditsArray = new JList<>();
        var usernameEditsArrayConstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(usernameEditsArray, usernameEditsArrayConstraints);

        //Make empty Jlist for editNumbers
        JList<Object> editNumberArray = new JList<>();
        var editNumberArrayConstraints = new GridBagConstraints(4, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(editNumberArray, editNumberArrayConstraints);

        //Make getRevisions Button
        JButton revisionsButton = new JButton("get revisions");
        var revisionsButtonConstraints = new GridBagConstraints(3, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(revisionsButton, revisionsButtonConstraints);
        revisionsButton.addActionListener(e -> {
            String searchEntry = txtInput.getText();
            if (!JSONStringRetriever.isConnected()) { errorDisplayLabel.setText("Internet is not connected.");}
            try {
                String JSONString = JSONStringRetriever.getJSONstring(searchEntry);
                if (JSONString.equals("Error")) { errorDisplayLabel.setText("No article exists. Try again.");}
                else {
                    WebInfo webInfo = JSONStringParser.parseJSONString(JSONString);
                    Webpage webpage = WebpageBuilder.buildAWebpage(webInfo);

                    titleLabel.setText("Title: " + webpage.getTitle());
                    fromLabel.setText("From: " + webpage.getFrom());
                    toLabel.setText("To: " + webpage.getTo());

                    Sorter sorter = new Sorter(webpage.getSortedByTimeStamp(),webpage.getSortedByQuantity());

                    panel.remove(timestampArray);
                    panel.add(new JList(sorter.sortedFormattedTimeStamps),timestampArrayConstraints);
                    panel.remove(usernameArray);
                    panel.add(new JList(sorter.sortedByTSUsernames),usernameArrayConstraints);

                    panel.remove(usernameEditsArray);
                    panel.add(new JList(sorter.sortedByEUsernames),usernameEditsArrayConstraints);
                    panel.remove(editNumberArray);
                    panel.add(new JList(sorter.sortedUserEdits),editNumberArrayConstraints);

                }
            } catch (Exception | ParameterIsNotJSONStringException ex) {
                ex.printStackTrace();
            }

        });

        //Closing thoughts, final window creation
        setPreferredSize(new Dimension(400, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){ new RevisionViewerUI(); }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}