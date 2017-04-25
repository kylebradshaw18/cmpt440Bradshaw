/**
 * @Author Kyle Bradshaw
 * @Course Formal Languages
 * @Date March 30th 2017
 * @File NFAFrame.java
 * @ClassName NFAFrame
 * @Description This program builds a java JFrame desktop window.
 *  This desktop app will look through all of the files in the folder.
 *  It will then see if the string you are looking for is in that file.
 *  If it is then a new row will be added to the table and show the file 
 *  name and the line number where we have found the match
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class NFAFrame extends JFrame {
  /**
   * @Constructor This will set all of the properties for the NFAFrame Class.
   *   This will also set the look and feel of the JFrame
   */
  public NFAFrame() {
    //Allow the user to close the application by clicking the x on the frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //set the frame to show
    setVisible(true);
    //Set the title of the app
    setTitle("NFA Text Parser");
    //Create a border for the search JPanel
    searchPanel.setBorder(BorderFactory.createTitledBorder( null, 
                                                            searchTextFieldDefaultText, 
                                                            TitledBorder.CENTER, 
                                                            TitledBorder.TOP, 
                                                            defaultPanelFont, 
                                                            Color.BLUE));
    //Create a tooltip for the directory text field and make it not enabled
    directoryTextField.setToolTipText("Choose a directory to look in");
    directoryTextField.setEnabled(false);
    //Whenever the directory textfield is clicked show the File Chooser Dialog
    directoryTextField.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        showFileChooser();
      }
    });
    //Set the font of the directory button to the default font of the app
    directoryButton.setFont(defaultFont);
    directoryButton.setToolTipText("Choose a directory to look in");
    //Added a click event to the directory button which will show the File Chooser
    directoryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        showFileChooser();
      }
    });
    //Set the search textfields font to the default font for the application
    searchTextField.setFont(defaultFont);
    //Set the tooltip for the search textfield
    searchTextField.setToolTipText("Keyword to search");
    //Add a focus listener which will highlight everything in the textfield
    searchTextField.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent evt) {
        searchTextField.selectAll();
      }
    });
    //Add an action listener to the Checkbox which will change how the NFA
    //Looks for the string in the line of the file
    caseSensitiveCheckBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        //Set the global boolean to true of false depending on what it is now
        caseSensitive = !caseSensitive;
      }
    });
    //Set the font on the Search button to the default font
    searchButton.setFont(defaultFont);
    //Add an action listener to the button to perform the text parse search
    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        searchButtonActionPerformed(evt);
      }
    });
    //Create the layout for the Search Panel
    GroupLayout searchPanelLayout = new GroupLayout(searchPanel);
    searchPanel.setLayout(searchPanelLayout);
    //Create the horizontal group with all of the components
    searchPanelLayout.setHorizontalGroup(
      searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(searchPanelLayout.createSequentialGroup()
        .addComponent(searchTextField)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(caseSensitiveCheckBox)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(searchButton))
        .addGroup(searchPanelLayout.createSequentialGroup()
        .addComponent(directoryTextField)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(directoryButton))
    );
    //Create the vertical group for the search panel
    searchPanelLayout.setVerticalGroup(
      searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(searchPanelLayout.createSequentialGroup()
        .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(directoryTextField, 
                      GroupLayout.PREFERRED_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.PREFERRED_SIZE)
        .addComponent(directoryButton))
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(searchTextField, 
                      GroupLayout.PREFERRED_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.PREFERRED_SIZE)
        .addComponent(caseSensitiveCheckBox)
        .addComponent(searchButton))
        .addGap(0, 0, Short.MAX_VALUE))
    );
    //Create the border for the File Panel
    filePanel.setBorder(BorderFactory.createTitledBorder(null, 
                                                         "Files", 
                                                         TitledBorder.CENTER, 
                                                         TitledBorder.TOP, 
                                                         defaultPanelFont, 
                                                         Color.BLUE));
    //Set the cursor to the default cursor for the file panel
    filePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    //Create the border for the file table
    fileTable.setBorder(BorderFactory.createEtchedBorder());
    //Set the table to the default table model
    fileTable.setModel(model);
    //Do not let the user rearrange the table column order
    fileTable.getTableHeader().setReorderingAllowed(false);
    //Created an ActionListener when the user clicks on the rows in the table
    fileTable.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent me) {
        //Get the Source of the J File Table
        JTable table = (JTable) me.getSource();
        //Check if it was a double click
        if (me.getClickCount() == 2) {
          //Get the row the user clicked
          int row = table.rowAtPoint(me.getPoint());
          //Create a temporary file from the one in the table
          File editorFile = new File(model.getValueAt(row,1).toString());
          try{
            //Open the file in the users default text editor
            Desktop.getDesktop().open(editorFile);
          } catch (IOException e){}
        }
      }
    });

    //Set the layout of the file panel
    GroupLayout filePanelLayout = new GroupLayout(filePanel);
    filePanel.setLayout(filePanelLayout);
    //Set the horizontal group for the file panel layout
    filePanelLayout.setHorizontalGroup(
      filePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(filePanelLayout.createSequentialGroup()
        .addComponent(fileScrollPane, 
                      GroupLayout.DEFAULT_SIZE, 
                      885, 
                      Short.MAX_VALUE)
        .addContainerGap())
    );
    //Set the vertical group layout for the file panel
    filePanelLayout.setVerticalGroup(
      filePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(filePanelLayout.createSequentialGroup()
        .addComponent(fileScrollPane, 
                      GroupLayout.DEFAULT_SIZE, 
                      286, 
                      Short.MAX_VALUE)
        .addContainerGap())
    );
    //Set the layout to the contentpane
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    //Set the horizontal group of the layout
    layout.setHorizontalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(filePanel, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      Short.MAX_VALUE)
        .addComponent(searchPanel, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      Short.MAX_VALUE))
        .addContainerGap())
    );
    //Set the vertical group of the layout
    layout.setVerticalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(searchPanel, 
                      GroupLayout.PREFERRED_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(filePanel, 
                      GroupLayout.DEFAULT_SIZE, 
                      GroupLayout.DEFAULT_SIZE, 
                      Short.MAX_VALUE)
        .addContainerGap())
    );
    //Sets the content panes size depending on the window size
    pack();
  }  
                                                                                                                              
  /**
   * @Description Function that is called when the search button is click
   * It will run the NFA and update any rows in the file table with the file found
   * When the process is finished it will show a message telling the user 
   * 1) How many rows were found
   * 2) The number of files that were searched
   * 3) The time it took to run the process
   * @ActionEvent evt passes in the action that triggers this function
   */
  private void searchButtonActionPerformed(ActionEvent evt) {     
    //Clear all rows in the file table
    model.setRowCount(0); 
    //Reset the file counter
    numberFilesSearched = 0; 
    //Get the start time so we can see how long the process took
    Date startTime = new Date();
    try{
      //set the pattern for the regex
      if(caseSensitive)
        pattern = Pattern.compile(searchTextField.getText());
      else
        pattern = Pattern.compile(searchTextField.getText(), Pattern.CASE_INSENSITIVE);
      //Create a list of files from the directory text field and run them
      runFiles(new File(directoryTextField.getText()).listFiles());
    } catch (Exception e){
    } finally{
      //Create the message for the information popup
      StringBuilder message = new StringBuilder(String.valueOf(model.getRowCount()));
      message.append(" Row's Found \n");
      message.append(String.valueOf(numberFilesSearched));
      message.append(" Files Searched \n");
      message.append(String.valueOf(new Date().getTime() - startTime.getTime()));
      message.append(" Milliseconds");
      //Show the popup with the message
      JOptionPane.showMessageDialog(this, message.toString());
    }
  } 

  /**
   * @Description Function which will loop through the files and check if that 
   * file is a directory and if it is it will use recursion to call the same  
   * function with the list of files
   * @files A list of files to be looped through
   */
  public void runFiles(File[] files) {
    //Loop through the files passed in
    for (File file : files) {
      //Check if the file is a directory and call the same function to loop
      //through the files in that folder
      if (file.isDirectory()){
        try{
          runFiles(file.listFiles());
        } catch (Exception e){
          continue;
        }
      } else {
        //Read the file and increment the file counter by one
        readFile(file);
        numberFilesSearched++;
      }
    }
  }

  /**
   * @Description Function which takes a file and will read it line by line  
   * @file Parameter which is the file we are trying to read
   */
  private void readFile(File file){
    //Wrap the inputstream and BufferedReader in a try so it will catch any exceptions
    //And we do not have to worry about closing them because it will do it for us
    try(InputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
      //Declare and initialize local variables
      String line;
      int lineIndex = 1;
      //Read each line of the file until the end
      while ((line = reader.readLine()) != null) {
        //Check if the line passes the NFA if it does add the info to the table
        if(runNFA(line))
          model.addRow( new Object[]{file.getName(), 
                        file.getAbsolutePath(), 
                        Integer.toString(lineIndex) });
        //Increment the line number for the file
        lineIndex++;
      }
    } catch (IOException ex) {
    }
  }

  /**
   * @Description Function which will see if the search text is in the string passed in
   * @Line The line from the file that gets passed in
   * @Return true or false depending on if the value is in the line parameter
   */
  private boolean runNFA(String line){ 
    //Set the return value to false
    boolean returnValue = false;
    try{
      //Set the return value to the boolean if it was found or not
      returnValue = pattern.matcher(line).find();
    } catch (Exception e){
      //Reset the return value to false and return it
      returnValue = false;
    } finally {
      return returnValue;
    }
  }

  /**
   * @Description Function which will create a popup and show a JFileChooser
   * This will set the uneditable directoryTextField to the value it returns
   */                                               
  private void showFileChooser(){
    //Create a new File Chooser
    JFileChooser chooser = new JFileChooser();
    //If the directory text field has been changed then set the default Location
    //to the last directory that the user was looking in
    if(!directoryTextField.getText().equals(directoryTextFieldDefaultText))
      chooser.setCurrentDirectory(new File(directoryTextField.getText()));
    chooser.setDialogTitle(directoryTextFieldDefaultText);
    //Set the filechooser to only look at Directories
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //When the user clicks ok then set the directory text field to the value that was returned
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
      directoryTextField.setText(chooser.getSelectedFile().getAbsolutePath());
  }
  /**
   * @Description Main method to run the NFA Frame application
   */

  public static void main(String args[]) {
    try {
      //Loop through info to check if the user has Nimbus and set it
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch ( ClassNotFoundException | 
              InstantiationException | 
              IllegalAccessException | 
              UnsupportedLookAndFeelException ex) {
    } finally{
      //Run the Application
      new NFAFrame();
    }
  }

  /**
   * @Properties for the application
   */
  private boolean caseSensitive = false;
  private int numberFilesSearched = 0;
  private final String [] fileTableColumnName = new String[]{"Name", "Location", "Line Number"};
  private final String searchTextFieldDefaultText = "Search";
  private final String directoryTextFieldDefaultText = "Choose Directory";
  private DefaultTableModel model = new DefaultTableModel(new Object [][]{},
                                                          fileTableColumnName){
    @Override //Sets the columns so they are not editable
    public boolean isCellEditable(int row, int column) {
      return false;
    }
  };
  private final String defaultFontFamily = "Monaco";
  private final Font defaultFont = new Font(defaultFontFamily, 0, 13);
  private final Font defaultPanelFont = new Font(defaultFontFamily, 0, 18);
  private JCheckBox caseSensitiveCheckBox = new JCheckBox("Case Sensitive", caseSensitive);
  private JButton directoryButton = new JButton(directoryTextFieldDefaultText);
  private JTextField directoryTextField = new JTextField(directoryTextFieldDefaultText);
  private Pattern pattern;
  private JPanel filePanel = new JPanel();
  private JTable fileTable = new JTable(model);
  private JScrollPane fileScrollPane = new JScrollPane(fileTable);
  private JButton searchButton = new JButton(searchTextFieldDefaultText);
  private JPanel searchPanel = new JPanel();
  private JTextField searchTextField = new JTextField(searchTextFieldDefaultText);               
}