/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UserInterface;

import ReadFile.FileMap;
import ReadFile.Tokenizer;
import Storage.MyFileDirectory;
import Storage.MyFileWithPosition;
import Storage.TernarySearchTrie;
import java.awt.CardLayout;
import java.util.ArrayList;
import static java.util.Collections.swap;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author zhujinmeng
 */
public class SearchJPanel extends javax.swing.JPanel {

    public JPanel userProcessContainer;
    public TernarySearchTrie tst;
    TokenizerModel model;
    TokenizerME tokenizer;
    String[] tokens;
    ArrayList<String> searchList;
    
    
    /**
     * Creates new form SearchJPanel
     */
    public SearchJPanel(JPanel upc, TernarySearchTrie t) {
        initComponents();
        this.userProcessContainer = upc;
        this.tst = t;   
        this.model = Tokenizer.loadTokenizer();
        this.tokenizer = new TokenizerME(model);
        this.searchList = new ArrayList<String>();
        refreshTable();
    }
    
    
    
    
    
    public void searchAction(){
        String input = searchField.getText();
        tokens = tokenizer.tokenize(input);
        for(String item : tokens){
            StringBuffer br = new StringBuffer();
            for(int i=0;i<item.length();i++){
                char c = item.charAt(i);
                br.append(String.valueOf(c).replaceAll("[^A-Za-z0-9]", "").toLowerCase());
            }
            String pitt = br.toString();
            if(!pitt.equals("")){
                searchList.add(pitt);
            }               
        }
      
        if(searchList.size()==1){
            
            if(tst.get(searchList.get(0))!=null){
                MyFileDirectory smfd = sortByFrequency(tst.get(searchList.get(0)));
                ArrayList<String> resultList = new ArrayList<String>();
                for(MyFileWithPosition mfwp : smfd.myFileList){
                resultList.add(mfwp.fileName);
            }
            
            refreshTable1(resultList, searchList.get(0));
            }else{
                JOptionPane.showMessageDialog(null,"The word doesn't exist.");
            }
            //refreshTable1(searchOneWord(searchList),searchList.get(0));
            
        }else if(searchList.size()==2){
            
            searchTwoWords(searchList.get(0),searchList.get(1));
            if(searchTwoWords(searchList.get(0),searchList.get(1))!=null){
                refreshTable2(searchTwoWords(searchList.get(0),searchList.get(1)),searchList.get(0),searchList.get(1) );
            }
            //refreshTble2(searchTwoWords(searchList.get(0),searchList.get(1)),searchList.get(0),searchList.get(1));
            //searchTwoWords(searchList.get(0),searchList.get(1));
            
        }else{
            searchMoreWords2(searchList);
            //refreshTble3(searchMoreWords(searchList));
            
        }
       
        
        searchList.clear();
    }
/*    
    public ArrayList<String> searchOneWord(ArrayList<String> list){

        if(tst.get(list.get(0))!=null){
            MyFileDirectory smfd = sortByFrequency(tst.get(list.get(0)));
            ArrayList<String> resultList = new ArrayList<String>();
            for(MyFileWithPosition mfwp : smfd.myFileList){
            resultList.add(mfwp.fileName);
            return resultList;
        }

        //refreshTable1(resultList, searchList.get(0));
        }else{
            JOptionPane.showMessageDialog(null,"The word doesn't exist.");
        }
        return null;
    }
*/    
    public ArrayList<ArrayList<String>> searchTwoWords(String a, String b){
        if(tst.get(a)!=null&&tst.get(b)!=null){
        
        MyFileDirectory ma = tst.get(a);
        MyFileDirectory mb = tst.get(b);
        //MyFileDirectory mfd = new MyFileDirectory();
        ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
        ArrayList<String> exactFileNameList = new ArrayList<String>();
        ArrayList<String> highFileNameList = new ArrayList<String>();
        ArrayList<String> partFileNameList = new ArrayList<String>();
        
        boolean flag = false;
        
        for(MyFileWithPosition mfwp1 : ma.myFileList){
            for(MyFileWithPosition mfwp2 : mb.myFileList){
                if(mfwp1.fileName.equals(mfwp2.fileName)){
                    flag = true;
                    for(Integer p1 : mfwp1.posList){
                        if(mfwp2.posList.contains(p1+1)){
                            exactFileNameList.add(mfwp2.fileName);
                        }else if(!(mfwp2.posList.contains(p1+1))&&mfwp2.posList.contains(p1+2)&&mfwp2.posList.contains(p1+3)){
                            highFileNameList.add(mfwp2.fileName);
                        }else{
                            partFileNameList.add(mfwp2.fileName);
                        }
                    }
                    
                }
            }
        }
        
        if(flag){
            resultList.add(0, removeDuplicate(exactFileNameList));
            resultList.add(1, removeDuplicate(highFileNameList));
            resultList.add(2, removeDuplicate(partFileNameList));

            return resultList;
        }else{
            MyFileDirectory smfd1 = sortByFrequency(tst.get(a));
                ArrayList<String> resultList1 = new ArrayList<String>();
                for(MyFileWithPosition mfwp : smfd1.myFileList){
                resultList1.add(mfwp.fileName);
            }
            
            refreshTable1(resultList1, a);
            
            MyFileDirectory smfd2 = sortByFrequency(tst.get(b));
                ArrayList<String> resultList2 = new ArrayList<String>();
                for(MyFileWithPosition mfwp : smfd2.myFileList){
                resultList2.add(mfwp.fileName);
            }
            
            refreshTable11(resultList2, b);
                
            
            
            return null;
        }
        
        
        }else if(tst.get(a)==null&&tst.get(b)==null){
            JOptionPane.showMessageDialog(null,"The word doesn't exist.");
            return null;
        }else{
            String search = null;
            
            if(tst.get(a)==null){
                search = b;
            }else{
                search = a;
            }
            MyFileDirectory smfd = sortByFrequency(tst.get(search));
                ArrayList<String> resultList = new ArrayList<String>();
                for(MyFileWithPosition mfwp : smfd.myFileList){
                resultList.add(mfwp.fileName);
            }
            
            refreshTable1(resultList, search);
            
            return null;
        }
        
        
        
    }
    
    
    public void searchMoreWords2(ArrayList<String> list){
        
        Iterator<String> e = list.iterator();
        while(e.hasNext()){    
        String element = e.next();  
        if(!tst.contains(element)){  
            e.remove();        
            }
        }
        
        if(list.size()>=3){
            refreshTble3(searchMoreWords1(list), list);
        }
        
        
        else if(list.size()==2){
            searchTwoWords(list.get(0),list.get(1));
            if(searchTwoWords(list.get(0),list.get(1))!=null){
                refreshTable2(searchTwoWords(list.get(0),list.get(1)), list.get(0), list.get(1));
            }
            
        }else{
            
            if(!list.isEmpty()){
                MyFileDirectory smfd = sortByFrequency(tst.get(list.get(0)));
                ArrayList<String> resultList = new ArrayList<String>();
                for(MyFileWithPosition mfwp : smfd.myFileList){
                resultList.add(mfwp.fileName);
            }
                refreshTable1(resultList, searchList.get(0));
            }else{
                JOptionPane.showMessageDialog(null,"The word doesn't exist.");
            }
            
            
            
        }
        
        
    }
    
    public ArrayList<String> searchMoreWords1(ArrayList<String> list){

        ArrayList<String> stringlist = new ArrayList<String>();
                
        for(String word : list){
            for(MyFileWithPosition mfwp : tst.get(word).myFileList){
               if(!stringlist.contains(mfwp.fileName)){
                   stringlist.add(mfwp.fileName);
               }
            }
        }
        
        
        ArrayList<FileMap> maplist = new ArrayList<FileMap>();
        
        for(String name : stringlist){
            for(String word : list){
                int count = 0;
                for(MyFileWithPosition mfwp : tst.get(word).myFileList){
                    if(mfwp.fileName.equals(name)){
                        count++;
                    }
                }
                FileMap fileMap = new FileMap();
                fileMap.fileName = name;
                fileMap.fileContent = String.valueOf(count);
                maplist.add(fileMap);
            }
        }
        
        for(int i=0;i<maplist.size();i++){
            int max = i;
            for(int j=i+1;j<maplist.size();j++){
                if(Integer.parseInt(maplist.get(j).fileContent) > Integer.parseInt(maplist.get(max).fileContent)){
                    max = j;
                }
            }
            swap(maplist,i,max);
        }
        
        
        ArrayList<String> resultList = new ArrayList<String>();
        for(FileMap fm : maplist){
            resultList.add(fm.fileName);
        }
        
        //return resultList;      
        return removeDuplicate(resultList);
        
    }
    
    public ArrayList<String> removeDuplicate(ArrayList<String> list){
        HashSet set = new HashSet();
        ArrayList<String> newList = new ArrayList<String>();
        for (Iterator iter = list.iterator(); iter.hasNext();){
            Object element  =  iter.next();
            if(set.add(element)){
                newList.add((String)element);
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }
    
    
    public MyFileDirectory sortByFrequency(MyFileDirectory mfd){
        //MyFileDirectory smfd = new MyFileDirectory();
        for(int i=0;i<mfd.myFileList.size();i++){
            int max = i;
            for(int j=i+1;j<mfd.myFileList.size();j++){
                if(mfd.myFileList.get(j).posList.size()>mfd.myFileList.get(max).posList.size()){
                    max = j;                    
                }
            }
            swap(mfd.myFileList,i,max);
        }
        
        return mfd;
    }

    public void refreshTable(){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
    }
    
    public void refreshTable1(ArrayList<String> list, String search){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        
            for(String txt : list){
            Object[] row = new Object[3];
            row[0] = txt;
            MyFileWithPosition result = null;
            for(MyFileWithPosition a :tst.get(search).myFileList){
                if(a.fileName.equals(txt)){
                    result = a;
                    break;
                }
            }
            row[1] = result.posList.size();
            row[2] = "Match";
            
            model.addRow(row);
            }
                
        
    }
    
    
    public void refreshTable11(ArrayList<String> list, String search){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        //model.setRowCount(0);
        
            for(String txt : list){
            Object[] row = new Object[3];
            row[0] = txt;
            MyFileWithPosition result = null;
            for(MyFileWithPosition a :tst.get(search).myFileList){
                if(a.fileName.equals(txt)){
                    result = a;
                    break;
                }
            }
            row[1] = result.posList.size();
            row[2] = "Match";
            
            model.addRow(row);
            }
        
        
    }
    
    public void refreshTable2(ArrayList<ArrayList<String>> list, String a, String b){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        if(list!=null){
            for(String txt : list.get(0)){
            Object[] row = new Object[3];
            row[0] = txt;
            MyFileWithPosition result1 = null;
            for(MyFileWithPosition mfwp :tst.get(a).myFileList){
                if(mfwp.fileName.equals(txt)){
                    result1 = mfwp;
                    break;
                }
            }
            
            MyFileWithPosition result2 = null;
            for(MyFileWithPosition mfwp :tst.get(b).myFileList){
                if(mfwp.fileName.equals(txt)){
                    result2 = mfwp;
                    break;
                }
            }           
            row[1] = (result1.posList.size()+result2.posList.size())/2;
            row[2] = "Exact Match";
            model.addRow(row);
        }
        
            for(String txt : list.get(1)){
                Object[] row = new Object[3];
                row[0] = txt;
                MyFileWithPosition result1 = null;
                for(MyFileWithPosition mfwp :tst.get(a).myFileList){
                    if(mfwp.fileName.equals(txt)){
                        result1 = mfwp;
                        break;
                    }
                }

                MyFileWithPosition result2 = null;
                for(MyFileWithPosition mfwp :tst.get(b).myFileList){
                    if(mfwp.fileName.equals(txt)){
                        result2 = mfwp;
                        break;
                    }
                }  


                row[1] = (result1.posList.size()+result2.posList.size())/2;
                row[2] = "HighLy Match";
                model.addRow(row);
            }

            for(String txt : list.get(2)){
                Object[] row = new Object[3];
                row[0] = txt;
                MyFileWithPosition result1 = null;
                for(MyFileWithPosition mfwp :tst.get(a).myFileList){
                    if(mfwp.fileName.equals(txt)){
                        result1 = mfwp;
                        break;
                    }
                }
            
            MyFileWithPosition result2 = null;
                for(MyFileWithPosition mfwp :tst.get(b).myFileList){
                    if(mfwp.fileName.equals(txt)){
                        result2 = mfwp;
                        break;
                    }
                } 


                row[1] = (result1.posList.size()+result2.posList.size())/2;
                row[2] = "Partly Match";
                model.addRow(row);
            }
        }else{
            JOptionPane.showMessageDialog(null,"The word doesn't exist.");
        }
        
    }
    
    public void refreshTble3(ArrayList<String> list, ArrayList<String> search){
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        
        for(String txt : list){
            Object[] row = new Object[3];
            row[0] = txt;
            int i = 0;
            for(String s : search){
                for(MyFileWithPosition result : tst.get(s).myFileList){
                    if(result.fileName.equals(txt)){
                        i+=result.posList.size();
                    }
                }
            }
            
            row[1] = i;
            row[2] = "Match";
            model.addRow(row);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "File Name", "Frequency", "Match Type"
            }
        ));
        jScrollPane1.setViewportView(resultTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(searchButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchAction();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = resultTable.getSelectedRow();
        if(selectedRow<0) return;
        
        String filename= (String) resultTable.getValueAt(selectedRow, 0);
        
        ShowJPanel ljp = new ShowJPanel(userProcessContainer, tst, filename, searchList);
        userProcessContainer.add("ShowJPanel",ljp);
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables

}
