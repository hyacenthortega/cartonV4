/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Orders;

import Items.ImageRenderer;
import Supplier.TableActionCellEditor;
import Supplier.TableActionCellRender;
import Supplier.TableActionEvent;
import com.mysql.cj.jdbc.Blob;
import databaseconnection.DataBaseConnection;
import java.awt.Color;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.glasspanepopup.GlassPanePopup;

/**
 *
 * @author home
 */
public class ViewOrder extends javax.swing.JPanel {

    private CreateOrder createOrder;
    private Timer timer;
    private Status status;

    public ViewOrder() throws Exception {
        initComponents();
        createOrder = new CreateOrder();
        populateTable();
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onDelete(int row) {
                delete(row);
            }
        };
        orderListTbl.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        orderListTbl.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(event));

        timer = new Timer(1000, (e) -> {
            try {
                populateTable();
            } catch (Exception ex) {

            }

        });
        timer.start();
        status = new Status();

    }

    private PreparedStatement p;

    private void populateTable() {
        try {

            DataBaseConnection.getInstance().ConnectToDatabase();

            String sql = "SELECT * FROM orderlist";
            p = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);

            ResultSet rs = p.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) orderListTbl.getModel();

            model.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                for (int i = 1; i <= c; i++) {
                    Blob blob = (Blob) rs.getBlob("Image");
                    orderListTbl.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
                    ImageIcon imageicon = ImageRenderer.blobToImageIcon(blob, 100, 100);
                    v.add(imageicon);
                    v.add(rs.getString("orderid"));
                    v.add(rs.getString("date"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("price"));
                    v.add(rs.getString("quantity"));
                    v.add(rs.getString("cost"));
                    v.add(rs.getString("status"));

                }
                model.addRow(v);
            }
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void delete(int row) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete the record?", "Warning", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                DataBaseConnection.getInstance().ConnectToDatabase();
                DefaultTableModel model = (DefaultTableModel) orderListTbl.getModel();
                String name = model.getValueAt(row, 3).toString(); // Assuming name is in the first column (index 0)

                String sql = "DELETE FROM orderlist WHERE name = ?";
                PreparedStatement p = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);
                p.setString(1, name); // Set the parameter value
                int rowsDeleted = p.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Successfully Deleted");
                    model.removeRow(row); // Remove the row from the table view
                } else {
                    JOptionPane.showMessageDialog(null, "Record not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderListTbl = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        createOrderBtn = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        orderListTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Image", "Order ID", "Order Date", "Product Name", "Price", "Quantity", "Total Cost", "Status", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderListTbl.setRowHeight(40);
        orderListTbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderListTblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(orderListTbl);
        if (orderListTbl.getColumnModel().getColumnCount() > 0) {
            orderListTbl.getColumnModel().getColumn(8).setMinWidth(50);
            orderListTbl.getColumnModel().getColumn(8).setPreferredWidth(50);
            orderListTbl.getColumnModel().getColumn(8).setMaxWidth(50);
        }

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setText("Search here");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        createOrderBtn.setText("Create Order");
        createOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrderBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 338, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(createOrderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrderBtnActionPerformed
        GlassPanePopup.showPopup(createOrder);
    }//GEN-LAST:event_createOrderBtnActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if (jTextField1.getText().equals("Search here")) {
            jTextField1.setText("");
            jTextField1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if (jTextField1.getText().equals("")) {
            jTextField1.setText("Search here");
            jTextField1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel table = (DefaultTableModel) orderListTbl.getModel();
        TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<>(table);
        orderListTbl.setRowSorter(tbl);
        tbl.setRowFilter(RowFilter.regexFilter(jTextField1.getText()));
    }//GEN-LAST:event_jTextField1KeyReleased

    private void orderListTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderListTblMouseClicked
        DefaultTableModel model = (DefaultTableModel) orderListTbl.getModel();
        int SelectedRows = orderListTbl.getSelectedRow();

        ImageIcon image = (ImageIcon) orderListTbl.getValueAt(SelectedRows, 0);
        Image pic = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(pic);
        status.ImgLabel.setIcon(img);
        status.orderid.setText(orderListTbl.getValueAt(SelectedRows, 1).toString());
        status.proname.setText(orderListTbl.getValueAt(SelectedRows, 3).toString());
        status.price.setText(orderListTbl.getValueAt(SelectedRows, 4).toString());
        status.status.setText(orderListTbl.getValueAt(SelectedRows, 7).toString());
        status.quan.setText(orderListTbl.getValueAt(SelectedRows, 5).toString());
        status.cost.setText(orderListTbl.getValueAt(SelectedRows, 6).toString());
        try {
            String dateString = orderListTbl.getValueAt(SelectedRows, 2).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Modify the format as per your date string
            java.util.Date date = dateFormat.parse(dateString);

            // Format the date into a string before setting it to jTextField5
            String formattedDate = dateFormat.format(date);
            status.date.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlassPanePopup.showPopup(status);

    }//GEN-LAST:event_orderListTblMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createOrderBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable orderListTbl;
    // End of variables declaration//GEN-END:variables

}
