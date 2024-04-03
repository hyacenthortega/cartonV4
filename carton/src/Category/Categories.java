package Category;

import Controller.RemoveCategories;
import Supplier.SupplierList;
import com.mysql.cj.jdbc.Blob;
import databaseconnection.DataBaseConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import model.CategoryModel;
import raven.glasspanepopup.GlassPanePopup;

public class Categories extends javax.swing.JPanel {

    private AddCategory addCat;
    private Timer timer;

    public Categories() {
        initComponents();
        setOpaque(false);
        addCat = new AddCategory();

        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setLayout(new GridLayout(0, 6));
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        timer = new Timer(1000, (e) -> {
            try {
                populateImagePanel();
            } catch (Exception ex) {
                Logger.getLogger(SupplierList.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        timer.start();
    }

    private PreparedStatement p;

    private void populateImagePanel() throws Exception {
        try {
            DataBaseConnection.getInstance().ConnectToDatabase();
            String sql = "SELECT * FROM addcategory";
            PreparedStatement p = DataBaseConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = p.executeQuery();

            imagePanel.removeAll();
            imagePanel.revalidate();
            imagePanel.repaint();
            imagePanel.setBackground(Color.WHITE);

            JPanel panel = new JPanel(new GridLayout(0, 6, 10, 10)); 
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            while (rs.next()) {
                Blob blob = (Blob) rs.getBlob("Image");
                byte[] imageData = blob.getBytes(1, (int) blob.length());

                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                BufferedImage bufferedImage = ImageIO.read(bis);

                if (bufferedImage != null) {
                    int scaledWidth = 100;
                    int scaledHeight = 100;
                    Image scaledImage = bufferedImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    JLabel imageLabel = new JLabel(scaledIcon);
                    JLabel categoryName = new JLabel(rs.getString("categoryname"));
                    categoryName.setHorizontalAlignment(SwingConstants.CENTER);
                    JPanel imagePanelWithLabel = new JPanel(new BorderLayout());
                    imagePanelWithLabel.add(imageLabel, BorderLayout.CENTER);
                    imagePanelWithLabel.add(categoryName, BorderLayout.SOUTH);
                    panel.add(imagePanelWithLabel);
                }
                bis.close();
            }
            imagePanel.add(panel);
            imagePanel.revalidate();
            imagePanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.close();
            }
        }
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        imagePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        remove = new javax.swing.JButton();
        add = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        imagePanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(imagePanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("List of Categories");

        remove.setText("Remove");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(remove)
                            .addComponent(add))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        try {
              Component[] components = imagePanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;

                Component[] panelComponents = panel.getComponents();
                if (panelComponents.length > 1 && panelComponents[1] instanceof JLabel) {
                    JLabel categoryLabel = (JLabel) panelComponents[1];
                    String categoryName = categoryLabel.getText();
                    byte[] imageData = (byte[]) panel.getClientProperty("imageData");

                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this category?",
                                    "Confirm", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                
                                imagePanel.remove(panel);
                                imagePanel.revalidate();
                                imagePanel.repaint();

                                RemoveCategories removeCategories = new RemoveCategories();
                                removeCategories.removeCategoryFromDatabase(new CategoryModel(categoryName, imageData));
                            }
                        }
                    });
                }
            }
        }          
        } catch (Exception e) {
        }
    }//GEN-LAST:event_removeActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        GlassPanePopup.showPopup(addCat);
    }//GEN-LAST:event_addActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton remove;
    // End of variables declaration//GEN-END:variables
}
