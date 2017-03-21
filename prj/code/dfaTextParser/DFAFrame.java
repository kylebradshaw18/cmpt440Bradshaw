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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kyle
 */
public class DFAFrame extends javax.swing.JFrame {

    /**
     * Creates new form DFAFrame
     */
    public DFAFrame() {
        //initComponents();

        searchPanel = new javax.swing.JPanel();
        directoryTextField = new javax.swing.JTextField();
        directoryButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        caseSensitiveCheckBox = new javax.swing.JCheckBox();
        searchButton = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        fileScrollPane = new javax.swing.JScrollPane();
        fileTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DFA Text Parser");
        setName("frame"); // NOI18N

        searchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monaco", 0, 18), new java.awt.Color(255, 153, 51))); // NOI18N
        searchPanel.setName("searchPanel"); // NOI18N

        directoryTextField.setText("Choose Directory");
        directoryTextField.setToolTipText("Choose a directory to look in");
        directoryTextField.setEnabled(false);
        directoryTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directoryTextFieldMouseClicked(evt);
            }
        });

        directoryButton.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        directoryButton.setText("Choose Directory");
        directoryButton.setToolTipText("Choose a directory to look in");
        directoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryButtonActionPerformed(evt);
            }
        });

        searchTextField.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        searchTextField.setText("Search");
        searchTextField.setToolTipText("Keyword to search");
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });

        caseSensitiveCheckBox.setText("Case Sensitive");
        caseSensitiveCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseSensitiveCheckBoxActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        searchButton.setText("Search");
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

        filePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monaco", 0, 18), new java.awt.Color(255, 153, 51))); // NOI18N
        filePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        fileTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            fileTableColumnName
        ) );
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileScrollPane.setViewportView(fileTable);

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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        searchPanel = new javax.swing.JPanel();
        directoryTextField = new javax.swing.JTextField();
        directoryButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        caseSensitiveCheckBox = new javax.swing.JCheckBox();
        searchButton = new javax.swing.JButton();
        filePanel = new javax.swing.JPanel();
        fileScrollPane = new javax.swing.JScrollPane();
        fileTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DFA Text Parser");
        setName("frame"); // NOI18N

        searchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monaco", 0, 18), new java.awt.Color(255, 153, 51))); // NOI18N
        searchPanel.setName("searchPanel"); // NOI18N

        directoryTextField.setText("Choose Directory");
        directoryTextField.setToolTipText("Choose a directory to look in");
        directoryTextField.setEnabled(false);
        directoryTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directoryTextFieldMouseClicked(evt);
            }
        });

        directoryButton.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        directoryButton.setText("Choose Directory");
        directoryButton.setToolTipText("Choose a directory to look in");
        directoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryButtonActionPerformed(evt);
            }
        });

        searchTextField.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        searchTextField.setText("Search");
        searchTextField.setToolTipText("Keyword to search");
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });

        caseSensitiveCheckBox.setText("Case Sensitive");
        caseSensitiveCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseSensitiveCheckBoxActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Monaco", 0, 13)); // NOI18N
        searchButton.setText("Search");
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

        filePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Monaco", 0, 18), new java.awt.Color(255, 153, 51))); // NOI18N
        filePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        fileTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            fileTableColumnName
        ) );
        //fileTable.setModel(model);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileScrollPane.setViewportView(fileTable);

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
    }// </editor-fold>                        

    private void directoryButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                
        showFileChooser();
    }                                               

    private void directoryTextFieldMouseClicked(java.awt.event.MouseEvent evt) {                                                
        showFileChooser();
    }                                               

    private void caseSensitiveCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        caseSensitive = !caseSensitive;
    }                                                     

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {     
        DefaultTableModel model = (DefaultTableModel) fileTable.getModel();  
        model.setRowCount(0);
        if(searchTextField.getText().length() > 0){
            //Get all of the files and run the dfa
            try{
                files = new File(directoryTextField.getText()).listFiles();
                showFiles(files);
            } catch (Exception e){
            } finally{
                String message = fileTable.getRowCount() + " Row's Found!";
                JOptionPane.showMessageDialog(this, message);
            }
        } else { 
            //Show Information Dialog
            JOptionPane.showMessageDialog(this, "You are missing a search value.");
        }
    }                                            
    public void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again (Recursion).
            } else {
                readFile(file);
            }
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
                if(runDFA(line)){ 
                    //LINE PASSED SO NOW WE NEED TO ADD IT TO THE TABLE WITH THE LINE NUMBER
                    DefaultTableModel model = (DefaultTableModel) fileTable.getModel();
                    model.addRow(new Object[]{file.getName(), 
                                                file.getAbsolutePath(), 
                                                Integer.toString(lineIndex) });
                }
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
            return false;
        } finally {
            return returnValue;
        }
    }
    private void searchTextFieldFocusGained(java.awt.event.FocusEvent evt) {                                            
        searchTextField.selectAll();
    }                                           

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {                                          
        if(searchTextField.getText().equals(searchTextFieldDefaultText))
            searchTextField.setText(searchTextFieldDefaultText);
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
          System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
          System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
        } else {
          System.out.println("No Selection ");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 javax.swing.UnsupportedLookAndFeelException ex) {
        } finally{
            new DFAFrame().setVisible(true);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox caseSensitiveCheckBox;
    private javax.swing.JButton directoryButton;
    private javax.swing.JTextField directoryTextField;
    private javax.swing.JPanel filePanel;
    private javax.swing.JScrollPane fileScrollPane;
    private javax.swing.JTable fileTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration                   
    private File [] files;
    private boolean caseSensitive = false;
    private final String [] fileTableColumnName = new String[]{"Name", "Location", "Line Number"};
    private final String searchTextFieldDefaultText = "Search";
}

