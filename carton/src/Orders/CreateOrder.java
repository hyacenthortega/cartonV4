/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Orders;

import Items.AddItem;
import Items.ImageRenderer;
import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.xdevapi.Statement;
import databaseconnection.DataBaseConnection;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author home
 */
public class CreateOrder extends javax.swing.JPanel {

    public JTable orderListTbl;

    public CreateOrder() throws Exception {
        initComponents();
        orderListTbl = new JTable();
        orderID.setText(generateOrderId());
        orderTable();
    }

    public static String generateOrderId() {
        int productIdLength = 5;

        Random random = new Random();
        StringBuilder orderIdBuilder = new StringBuilder();

        orderIdBuilder.append((char) (random.nextInt(26) + 'A'));
        orderIdBuilder.append((char) (random.nextInt(26) + 'A'));

        for (int i = 0; i < productIdLength - 2; i++) {
            orderIdBuilder.append(random.nextInt(10));
        }

        String orderId = orderIdBuilder.toString();
        return orderId;
    }

    private PreparedStatement p;

    private void orderTable() throws Exception {

        try {
            DataBaseConnection.getInstance().ConnectToDatabase();

            String sql = "SELECT image, productName, price, quantity FROM additems";
            PreparedStatement p = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 0; i <= c; i++) {
                    Blob blob = (Blob) rs.getBlob("Image");
                    orderTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
                    ImageIcon imageicon = ImageRenderer.blobToImageIcon(blob, 100, 100);
                    v.add(imageicon);
                    v.add(rs.getString("productName"));
                    v.add(rs.getString("quantity"));
                    v.add(rs.getString("price"));
                }
                model.addRow(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    private void updateTotalCost() {
        try {
            double price = Double.parseDouble(priceFld.getText());
            int quantity = Integer.parseInt(quanFld.getText());

            double totalCost = price * quantity;
            String costString = String.format("%.2f", totalCost);
            totalcostFld.setText(costString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private int getAvailableStock(String productName) {
        int availableStock = 0;
        try {
            DataBaseConnection.getInstance().ConnectToDatabase();
            String sql = "SELECT quantity FROM additems WHERE productName = ?";
            PreparedStatement pstmt = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                availableStock = rs.getInt("quantity");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return availableStock;
    }

    private byte[] getImageBytes(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        FileInputStream fis = new FileInputStream(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }

        fis.close();
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        okBtn = new javax.swing.JButton();
        statusFld = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        orderID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        totalcostFld = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        proName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        quanFld = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        priceFld = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        orderDate = new com.toedter.calendar.JDateChooser();
        decreaseBtn = new javax.swing.JButton();
        increaseBtn = new javax.swing.JButton();
        imageLbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Total Cost:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Status:");

        okBtn.setText("Place Order");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        statusFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        statusFld.setText("Processing");

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

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Image", "Product Name", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderTable.setRowHeight(40);
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(orderTable);

        orderID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Order Date:");

        totalcostFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Product Details:");

        proName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Product Name:");

        quanFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Price:");

        priceFld.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Quantity:");

        decreaseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/home/images/icons8_reduce_24px_1.png"))); // NOI18N
        decreaseBtn.setContentAreaFilled(false);
        decreaseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decreaseBtnActionPerformed(evt);
            }
        });

        increaseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/home/images/icons8_add_new_24px.png"))); // NOI18N
        increaseBtn.setContentAreaFilled(false);
        increaseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increaseBtnActionPerformed(evt);
            }
        });

        imageLbl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imageLbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Order ID:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(decreaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(quanFld, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(increaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(priceFld, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(statusFld, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(totalcostFld, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(proName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(okBtn, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(orderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(orderID, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(imageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(orderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(proName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(statusFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalcostFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(quanFld, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(increaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(decreaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okBtn)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(9, Short.MAX_VALUE))
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

    private String selectedImagePath = "";
    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        try {
            // Get the image from the selected row in the orderTable
            ImageIcon imageIcon = (ImageIcon) orderTable.getValueAt(orderTable.getSelectedRow(), 0);
            Image image = imageIcon.getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] selectedImageBytes = baos.toByteArray();

            // Get other details from the selected row
            String productName = proName.getText();
            String orderId = generateOrderId();
            int quantity = Integer.parseInt(quanFld.getText());
            double cost = Double.parseDouble(totalcostFld.getText());
            int price = Integer.parseInt(priceFld.getText());
            String status = statusFld.getText();
            String dateString = orderDate.getDate().toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date(dateString));

            try {
                DataBaseConnection.getInstance().ConnectToDatabase();

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "You don't have enough stock available for this item.", "Insufficient Stock", JOptionPane.WARNING_MESSAGE);
                    return; // Stop further processing
                }

                int availableStock = getAvailableStock(productName);
                if (quantity > availableStock) {
                    JOptionPane.showMessageDialog(this, "Sorry, we don't have enough stock available for this item. Please adjust the quantity", "Insufficient Stock", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Insert the new product without specifying the productID
                String sql = "INSERT INTO orderlist (image, orderID, date, name, price, quantity, cost, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement p = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);

                // Set image bytes
                p.setBytes(1, selectedImageBytes);
                // Set other parameters
                p.setString(2, orderId);
                p.setString(3, date);
                p.setString(4, productName);
                p.setInt(5, price);
                p.setInt(6, quantity);
                p.setDouble(7, cost);
                p.setString(8, status);

                // Execute the insert query
                p.executeUpdate();

                int selectedRow = orderTable.getSelectedRow();
                int currentQuantity = Integer.parseInt(orderTable.getValueAt(selectedRow, 3).toString());
                int orderedQuantity = Integer.parseInt(quanFld.getText());
                int updatedQuantity = currentQuantity - orderedQuantity;
                orderTable.setValueAt(updatedQuantity, selectedRow, 3);

                String updateSql = "UPDATE additems SET quantity = ? WHERE productName = ?";
                PreparedStatement updateStatement = DataBaseConnection.getInstance().getConnection().prepareStatement(updateSql);
                updateStatement.setInt(1, availableStock - quantity);
                updateStatement.setString(2, productName);
                updateStatement.executeUpdate();

                p.executeUpdate();
                JOptionPane.showMessageDialog(this, "Successfully Added");
                populateTable();

                // Clear fields after insertion
                proName.setText("");
                quanFld.setText("");
                totalcostFld.setText("");
                priceFld.setText("");
                orderDate.setDate(null);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_okBtnActionPerformed

    private byte[] selectedImageBytes;

    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        int SelectedRows = orderTable.getSelectedRow();

        int maxQuantity = Integer.parseInt(orderTable.getValueAt(SelectedRows, 3).toString());
        int MAX_QUANTITY = maxQuantity; // Set the maximum quantity

        ImageIcon image = (ImageIcon) orderTable.getValueAt(SelectedRows, 0);
        Image pic = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(pic);
        imageLbl.setIcon(img);
        proName.setText(orderTable.getValueAt(SelectedRows, 1).toString());
        priceFld.setText(orderTable.getValueAt(SelectedRows, 2).toString());
        quanFld.setText(orderTable.getValueAt(SelectedRows, 3).toString());

        try {

            double price = Double.parseDouble(priceFld.getText());
            int quantity = Integer.parseInt(quanFld.getText());

            double totalCost = price * quantity;
            String costString = String.format("%.2f", totalCost);
            totalcostFld.setText(costString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_orderTableMouseClicked

    private void decreaseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decreaseBtnActionPerformed
        try {
            int quantity = Integer.parseInt(quanFld.getText());
            if (quantity > 0) {
                quantity--;
                quanFld.setText(String.valueOf(quantity));
                updateTotalCost();
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_decreaseBtnActionPerformed

    int maxQuantity;
    private void increaseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increaseBtnActionPerformed
        try {
            int quantity = Integer.parseInt(quanFld.getText());

            quantity++;
            quanFld.setText(String.valueOf(quantity));
            updateTotalCost();

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_increaseBtnActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel table = (DefaultTableModel) orderTable.getModel();
        TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<>(table);
        orderTable.setRowSorter(tbl);
        tbl.setRowFilter(RowFilter.regexFilter(jTextField1.getText()));
    }//GEN-LAST:event_jTextField1KeyReleased

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decreaseBtn;
    private javax.swing.JLabel imageLbl;
    private javax.swing.JButton increaseBtn;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton okBtn;
    private com.toedter.calendar.JDateChooser orderDate;
    private javax.swing.JTextField orderID;
    private javax.swing.JTable orderTable;
    private javax.swing.JTextField priceFld;
    private javax.swing.JTextField proName;
    private javax.swing.JTextField quanFld;
    private javax.swing.JTextField statusFld;
    private javax.swing.JTextField totalcostFld;
    // End of variables declaration//GEN-END:variables

}
