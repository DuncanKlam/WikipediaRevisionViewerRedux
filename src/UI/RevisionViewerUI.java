package UI;

import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;
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
        var inputLabelConstraints = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(inputLabel, inputLabelConstraints);

        //#orevisions Label
        JLabel numORevisionsLabel = new JLabel("# o' revisions");
        var numORevisionsLabelConstraints = new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(numORevisionsLabel, numORevisionsLabelConstraints);

        //Title Label
        JLabel titleLabel = new JLabel("Title:");
        var titleLabelConstraints = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(titleLabel, titleLabelConstraints);

        //Redirected Label
        JLabel redirectedLabel = new JLabel("Redirected:");
        var redirectedLabelConstraints = new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(redirectedLabel, redirectedLabelConstraints);

        //From Label
        JLabel fromLabel = new JLabel("From:");
        var fromLabelConstraints = new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(fromLabel, fromLabelConstraints);

        //To Label
        JLabel toLabel = new JLabel("To:");
        var toLabelConstraints = new GridBagConstraints(4, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(toLabel, toLabelConstraints);

        //Recent Label
        JLabel recentLabel = new JLabel("Recent: ");
        var recentLabelConstraints = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
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
        var errorDisplayLabelConstraints = new GridBagConstraints(1, 4, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(errorDisplayLabel, errorDisplayLabelConstraints);

        //Make the input window
        JTextField txtInput = new JTextField("");
        var txtInputConstraints = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);;
        panel.add(txtInput, txtInputConstraints);

        //Make the revisions number window
        JTextField numberInput = new JTextField("");
        var numberInputConstraints = new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);;
        panel.add(numberInput, numberInputConstraints);

        //Make Scrollable List for Timestamps
        JList<Object> timestampArray = new JList<>();
        timestampArray.setLayoutOrientation(JList.VERTICAL);

        //Make Scrollable List for Username
        JList<Object> usernameArray = new JList<>();
        usernameArray.setLayoutOrientation(JList.VERTICAL);

        //Make SplitPane to put Username and Timestamps in
        JSplitPane USTSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, usernameArray, timestampArray);
        JScrollPane USTScrollPane = new JScrollPane(USTSplitPane);
        USTScrollPane.setMinimumSize(new Dimension(400,500));
        USTScrollPane.setMaximumSize(new Dimension(800,1500));
        var USTScrollPaneConstraints = new GridBagConstraints(0, 3, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(USTScrollPane, USTScrollPaneConstraints);

        //Make Scrollable List for editsUsername
        JList<Object> usernameEditsArray = new JList<>();
        usernameEditsArray.setLayoutOrientation(JList.VERTICAL);

        //Make Scrollable List for editNumbers
        JList<Object> editNumberArray = new JList<>();
        editNumberArray.setLayoutOrientation(JList.VERTICAL);

        //Make SplitPane to put editsUsername and editsNumbers
        JSplitPane USNSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, usernameEditsArray, editNumberArray);
        JScrollPane USNScrollPane = new JScrollPane(USNSplitPane);
        USNScrollPane.setMinimumSize(new Dimension(400,500));
        USNScrollPane.setMaximumSize(new Dimension(800, 1500));
        var USNScrollPaneConstraints = new GridBagConstraints(3, 3, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(USNScrollPane,USNScrollPaneConstraints);

        //Make Quit button
        JButton quitButton = new JButton("Quit");
        var quitButtonConstraints = new GridBagConstraints(4, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(quitButton, quitButtonConstraints);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        //Make getRevisions Button
        JButton revisionsButton = new JButton("fetch");
        var revisionsButtonConstraints = new GridBagConstraints(4, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(revisionsButton, revisionsButtonConstraints);
        revisionsButton.addActionListener(e -> {
            errorDisplayLabel.setText("");
            String searchEntry = txtInput.getText();
            String number = numberInput.getText();
            if (!JSONStringRetriever.isConnected()) { errorDisplayLabel.setText("Internet is not connected.");}
            try {
                String JSONString = JSONStringRetriever.getJSONstring(searchEntry,number);
                if (JSONString.equals("Error")) { errorDisplayLabel.setText("No article exists. Try again.");}
                else {
                    WebInfo webInfo = JSONStringParser.parseJSONString(JSONString);
                    Webpage webpage = WebpageBuilder.buildAWebpage(webInfo);
                    Sorter sorter = new Sorter(webpage.getSortedByTimeStamp(),webpage.getSortedByQuantity());

                    recentLabel.setText(number + " Recent:");
                    editsNumLabel.setText("Number of Edits (based on " + number + " recent):");
                    titleLabel.setText("Title: " + webpage.getTitle());
                    fromLabel.setText("From: " + webpage.getFrom());
                    toLabel.setText("To: " + webpage.getTo());
                    timestampArray.setListData(sorter.sortedFormattedTimeStamps);
                    usernameArray.setListData(sorter.sortedByTSUsernames);
                    usernameEditsArray.setListData(sorter.sortedByEUsernames);
                    editNumberArray.setListData(sorter.sortedEValues);
                }
            } catch (Exception | ParameterIsNotJSONStringException ex) {
                ex.printStackTrace();
            }

        });

        //Closing thoughts, final window creation
        setPreferredSize(new Dimension(1300, 1000));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){ new RevisionViewerUI(); }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}