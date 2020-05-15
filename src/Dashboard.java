import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Dashboard extends javax.swing.JFrame {

    int chocoP = 10, iceP = 25, cocaP = 30, pepsiP = 30, dewP = 15, shakeP = 20, curP=0, totalP=0;
    boolean isSave = false;
    DefaultListModel selectedCart = new DefaultListModel();
    DefaultListModel selectedItem = new DefaultListModel();
    DefaultListModel selectedQuan = new DefaultListModel();
    DefaultListModel selectedPrice = new DefaultListModel();
    
    File dir = null;
    
    public Dashboard() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        DefaultListModel item = new DefaultListModel();
        item.addElement("Chocolate");
        item.addElement("Ice Cream");
        item.addElement("Coca Cola");
        item.addElement("Pepsi");
        item.addElement("Mountain Dew");
        item.addElement("Milk Shake");
        
        itemList.setModel(item);
        cartList.setModel(selectedCart);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dateT.setText(formatter.format(date).toString());
        init();
        autoUpdater();
        cartUpdater();
        visibility(false);
    }
    public void init(){
        isSave = true;
        totalP = 0;
        nameField.setText(null);
        quantityCombo.setSelectedItem("1");
        removeBtn.setVisible(false);
        totalLbl.setVisible(false);
        totalPrice.setVisible(false);
        saveBtn.setVisible(false);
        selectedCart.clear();
        selectedItem.clear();
        selectedPrice.clear();
        selectedQuan.clear();
        itemList.clearSelection();
        cartList.clearSelection();
        isSave = false;
    }
    public void visibility(boolean check){
        addBtn.setVisible(check);
        unitprice.setVisible(check);
        unitPriceLbl.setVisible(check);
        quantityCombo.setVisible(check);
        quantityLbl.setVisible(check);
    }
    public void priceVis(boolean check){
        totalLbl.setVisible(check);
        totalPrice.setVisible(check);
    }
    public void autoUpdater(){
        itemList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && !isSave) {
                  visibility(true);
                  if(itemList.getSelectedValue().toString() == "Chocolate"){
                      unitprice.setText(Integer.toString(chocoP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = chocoP;
                  }
                  else if(itemList.getSelectedValue().toString() == "Ice Cream"){
                      unitprice.setText(Integer.toString(iceP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = iceP;
                  }
                  else if(itemList.getSelectedValue().toString() == "Coca Cola"){
                      unitprice.setText(Integer.toString(cocaP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = cocaP;
                  }
                  else if(itemList.getSelectedValue().toString() == "Pepsi"){
                      unitprice.setText(Integer.toString(pepsiP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = pepsiP;
                  }
                  else if(itemList.getSelectedValue().toString() == "Mountain Dew"){
                      unitprice.setText(Integer.toString(dewP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = dewP;
                  }
                  else if(itemList.getSelectedValue().toString() == "Milk Shake"){
                      unitprice.setText(Integer.toString(shakeP) + " BDT");
                      quantityCombo.setSelectedItem("1");
                      curP = shakeP;
                  }
                }
            }
        });
    }
    public void fileSaved(boolean check){
        if(check){
            JOptionPane.showMessageDialog(null, "File Successfully Saved!");
        }
        else{
            JOptionPane.showMessageDialog(null, "File Not Saved!");
        }
    }
    public void fileCreator(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        SimpleDateFormat formatterD = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterT = new SimpleDateFormat("hh:mm:ss a");
        Date date = new Date();
        String fName = "Memo " + formatter.format(date).toString() + ".txt";
        File file = new File(dir, fName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write("--------------------------------------------------------\r\n");
            fw.write("\t\t\tCash Memo\r\n");
            fw.write("--------------------------------------------------------\r\n");
            fw.write("Name: " + nameField.getText() + "\r\n\r\n");
            fw.write("Date: " + formatterD.format(date).toString() + "\t\t   Time: "+ formatterT.format(date).toString() + "\r\n");
            fw.write("........................................................\r\n\r\n");
            fw.write("Items\t\t\t\tPcs\t\tTotal\r\n");
            fw.write("........................................................\r\n\r\n");
            for(int i=0; i<selectedItem.size(); i++){
                int idx=i+1;
                fw.write(idx + ". " + selectedItem.get(i).toString() + "\t\t\t" + selectedQuan.get(i).toString() + "\t\t" + selectedPrice.get(i).toString() + "\r\n\r\n");
            }
            fw.write("--------------------------------------------------------\r\n");
            fw.write("\t\t\t\tSub Total:\t" + totalP + " BDT\r\n");
            fw.write("--------------------------------------------------------\r\n");
            fw.flush();
            fw.close();
            fileSaved(true);
            init();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File Can't Saved!");
        }
    }
    public void filePathCreator(){
        
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resp = fc.showOpenDialog(null);
        if (resp == JFileChooser.APPROVE_OPTION) {
            dir = fc.getSelectedFile();
            fileCreator();
        }
        else{
            fileSaved(false);
        }
    }
    public void cartUpdater(){
        cartList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                  removeBtn.setVisible(true);
                  if(selectedCart.isEmpty()){
                      removeBtn.setVisible(false);
                  }
                }
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        unitPriceLbl = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemList = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        unitprice = new javax.swing.JLabel();
        quantityLbl = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        quantityCombo = new javax.swing.JComboBox<>();
        itemListLbl = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartList = new javax.swing.JList<>();
        cartLbl = new javax.swing.JLabel();
        removeBtn = new javax.swing.JButton();
        totalPrice = new javax.swing.JLabel();
        totalLbl = new javax.swing.JLabel();
        saveBtn = new javax.swing.JButton();
        dateLbl = new javax.swing.JLabel();
        dateT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Cash Memo Generator");

        unitPriceLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        unitPriceLbl.setText("Unit Price:");

        itemList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        itemList.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(itemList);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Name:");

        unitprice.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        quantityLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        quantityLbl.setText("Quantity:");

        addBtn.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        quantityCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        itemListLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        itemListLbl.setText("Item List");

        cartList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cartList.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(cartList);

        cartLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cartLbl.setText("Cart");

        removeBtn.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        removeBtn.setText("Remove");
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        totalPrice.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        totalLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        totalLbl.setText("Total:");

        saveBtn.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        dateLbl.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        dateLbl.setText("Date:");

        dateT.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(unitPriceLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(unitprice))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(quantityLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(quantityCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(totalLbl)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(totalPrice)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(dateLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateT)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(itemListLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cartLbl)
                .addGap(115, 115, 115))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(addBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(removeBtn)
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(dateLbl)
                    .addComponent(dateT))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(itemListLbl)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unitPriceLbl)
                                    .addComponent(unitprice))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(quantityLbl)
                                    .addComponent(quantityCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalPrice)
                                    .addComponent(totalLbl)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cartLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(removeBtn))
                .addGap(18, 18, 18)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel1.getAccessibleContext().setAccessibleName("titleLbl");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed

        int totalItem = Integer.parseInt((String)quantityCombo.getSelectedItem());
        int price = (totalItem*curP);
        totalP += price;
        selectedCart.addElement(itemList.getSelectedValue().toString() + " " + totalItem + "Pcs " + price + "BDT");
        selectedPrice.addElement(price);
        selectedItem.addElement(itemList.getSelectedValue().toString());
        selectedQuan.addElement(totalItem);
        totalPrice.setText(Integer.toString(totalP) + " BDT");
        priceVis(true);
        quantityCombo.setSelectedItem("1");
        saveBtn.setVisible(true);
        removeBtn.setVisible(false);
    }//GEN-LAST:event_addBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex != -1) {
            selectedCart.remove(selectedIndex);
            String temp = selectedPrice.get(selectedIndex).toString();
            totalP -= Integer.parseInt(temp);
            totalPrice.setText(Integer.toString(totalP) + " BDT");
            selectedPrice.remove(selectedIndex);
            selectedItem.remove(selectedIndex);
            selectedQuan.remove(selectedIndex);
            selectedIndex = -1;
            removeBtn.setVisible(false);
        }
        if(selectedCart.isEmpty()){
            saveBtn.setVisible(false);
            priceVis(false);
        }
    }//GEN-LAST:event_removeBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (nameField.getText().trim().length() == 0){
            JOptionPane.showMessageDialog(null, "Name Required!");
        }
        else{
            filePathCreator();
        }
    }//GEN-LAST:event_saveBtnActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JLabel cartLbl;
    private javax.swing.JList<String> cartList;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JLabel dateT;
    private javax.swing.JList<String> itemList;
    private javax.swing.JLabel itemListLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;
    private javax.swing.JComboBox<String> quantityCombo;
    private javax.swing.JLabel quantityLbl;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton saveBtn;
    private javax.swing.JLabel totalLbl;
    private javax.swing.JLabel totalPrice;
    private javax.swing.JLabel unitPriceLbl;
    private javax.swing.JLabel unitprice;
    // End of variables declaration//GEN-END:variables

    private void autoPrice(JList<String> itemList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
