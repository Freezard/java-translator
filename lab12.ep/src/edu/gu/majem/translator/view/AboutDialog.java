package edu.gu.majem.translator.view;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AboutDialog extends javax.swing.JDialog {
   private JLabel jLabelAboutText;

   public AboutDialog(JFrame frame) {
      super(frame);
      initGUI();
   }
   
   private void initGUI() {
      try {
         {
            this.setTitle("About");
            {
               jLabelAboutText = new JLabel();
               getContentPane().add(jLabelAboutText, BorderLayout.CENTER);
               jLabelAboutText.setText("Translator by Hajo");
               jLabelAboutText.setFont(new java.awt.Font("Bitstream Charter",0,22));
            }
         }
         setSize(200, 150);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
