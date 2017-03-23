/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kyle
 */
public class DFAFrame extends JFrame {
    public DFAFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DFA Text Parser");
        searchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, 
                                                                           searchTextFieldDefaultText, 
                                                                           javax.swing.border.TitledBorder.CENTER, 
                                                                           javax.swing.border.TitledBorder.TOP, 
                                                                           defaultPanelFont, 
                                                                           new Color(255, 153, 51)));
        directoryTextField.setToolTipText("Choose a directory to look in");
        directoryTextField.setEnabled(false);
        directoryTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showFileChooser();
            }
        });

        directoryButton.setFont(defaultFont);
        directoryButton.setText("Choose Directory");
        directoryButton.setToolTipText("Choose a directory to look in");
        directoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFileChooser();
            }
        });

        searchTextField.setFont(defaultFont);
        searchTextField.setToolTipText("Keyword to search");
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextField.selectAll();
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(!searchTextField.getText().equals(searchTextFieldDefaultText))
                    searchTextField.setText(searchTextFieldDefaultText);
            }
        });

        caseSensitiveCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseSensitive = !caseSensitive;
            }
        });

        searchButton.setFont(defaultFont);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(searchTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseSensitiveCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton))
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(directoryTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directoryButton))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(directoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caseSensitiveCheckBox)
                    .addComponent(searchButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        filePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, defaultPanelFont, new java.awt.Color(255, 153, 51)));
        filePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        fileTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fileTable.setModel(model);
        fileTable.getTableHeader().setReorderingAllowed(false);

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addComponent(fileScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                .addContainerGap())
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addComponent(fileScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }                                                                                                                              

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {     
        model.setRowCount(0); //Clear all rows in the table
        if(searchTextField.getText().length() > 0){
            //Get all of the files and run the dfa
            try{
                files = new File(directoryTextField.getText()).listFiles();
                showFiles(files);
            } catch (Exception e){
            } finally{
                JOptionPane.showMessageDialog(this, model.getRowCount() + " Row's Found!");
            }
        } else { 
            //Show Information Dialog
            JOptionPane.showMessageDialog(this, "You are missing a search value.");
        }
    }                                            
    public void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory())
                showFiles(file.listFiles());
            else 
                readFile(file);
        }
    }
    private void readFile(File file){
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            int lineIndex = 1;
            while ((line = reader.readLine()) != null) {
                if(runDFA(line))
                    model.addRow(new Object[]{file.getName(), file.getAbsolutePath(), Integer.toString(lineIndex) });
                lineIndex++;
            }
            reader.close();
        } catch (IOException ex) {} 
        finally {
            try {
                in.close();
            } catch (IOException ex) {}
        }
    }
    private boolean runDFA(String line){ 
        boolean returnValue = false;
        try{
            Pattern p;
            if(caseSensitive)
                p = Pattern.compile(line);
            else 
                p = Pattern.compile(line, Pattern.CASE_INSENSITIVE);

            returnValue = p.matcher(searchTextField.getText()).find();
        } catch (Exception e){
            returnValue = false;
        } finally {
            return returnValue;
        }
    }                                                    
    private void showFileChooser(){
        JFileChooser chooser = new JFileChooser();
        if(!directoryTextField.getText().equals("Choose Directory"))
            chooser.setCurrentDirectory(new File(directoryTextField.getText()));
        chooser.setDialogTitle("Choose a Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            directoryTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            searchButton.setEnabled(true);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 UnsupportedLookAndFeelException ex) {
        } finally{
            new DFAFrame().setVisible(true);
        }
    }
    private File [] files;
    private boolean caseSensitive = false;
    private final String [] fileTableColumnName = new String[]{"Name", "Location", "Line Number"};
    private final String searchTextFieldDefaultText = "Search";
    private DefaultTableModel model = new DefaultTableModel(new Object [][]{},fileTableColumnName);
    private final String defaultFontFamily = "Monaco";
    private final Font defaultFont = new Font(defaultFontFamily, 0, 13);
    private final Font defaultPanelFont = new Font(defaultFontFamily, 0, 18);
    private JCheckBox caseSensitiveCheckBox = new JCheckBox("Case Sensitive", caseSensitive);
    private JButton directoryButton = new JButton(searchTextFieldDefaultText);
    private JTextField directoryTextField = new JTextField("Choose Directory");
    private JPanel filePanel = new JPanel();
    private JTable fileTable = new JTable(model);
    private JScrollPane fileScrollPane = new JScrollPane(fileTable);
    private JButton searchButton = new JButton(searchTextFieldDefaultText);
    private JPanel searchPanel = new JPanel();
    private JTextField searchTextField = new JTextField(searchTextFieldDefaultText);               
}

