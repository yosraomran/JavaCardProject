package javacardapp;
import javax.swing.*;
import javax.swing.border.Border;
import org.w3c.dom.css.RGBColor;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.sun.javacard.apduio.Apdu;
import com.sun.javacard.apduio.CadTransportException;

import javacardapp.Test;

public class AppJavaCard extends javax.swing.JFrame {
	private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private  JPasswordField password;
    JFrame f;
    public static AppJavaCard app;
    public  Test client;
    Apdu apdu;
    
	public static final byte CLA_MONAPPLET = (byte) 0xB0;
	
	public static final byte INS_TEST_CODE_PIN = 0x00;
	public static final byte INS_INTERROGER_SOLDE= 0x01;
	public static final byte INS_INCREMENTER_SOLDE= 0x02;
	public static final byte INS_DECREMENTER_SOLDE = 0x03;
	
    
    public AppJavaCard(Test client) {
    	f = new JFrame("Verification du Pin");
         this.client=client;
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        password= new JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
       f.setSize(563, 394);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setText("Entrer");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton2.setBounds(240, 230, 110, 40);
        jLabel1.add(jButton2);
        
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
    			byte[] pin_ok ;
    			
    			int pin=Integer.parseInt(password.getText().toString());
    			byte x1=(byte)((int)(pin/1000)%10);
    			byte x2=(byte)((int)(pin/100)%10);
    			byte x3=(byte)((int)(pin/10)%10);
    			byte x4=(byte)(pin%10);
    			pin_ok= new byte[] { x1,x2,x3,x4 };
    			
				try {
				Apdu apdu = client.Msg(AppJavaCard.INS_TEST_CODE_PIN, (byte) 0x04, pin_ok, (byte) 0x7f);
				 if (apdu.getStatus() == 0x6300) {
	    				showMessageDialog(null, "Code_PIN INCORRECT!");
	    				password.setText("");
	    				
	    			} else
	    				{InterfaceMain c=new InterfaceMain(f);
	                	jPanel1.setVisible(false);
	    				}
				} catch (IOException | CadTransportException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
    			
            	
            }
        });
        
        password.setForeground(new java.awt.Color(204, 204, 204));
        password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        password.setBounds(170, 180, 260, 30);
        jLabel1.add(password);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Eclipse\\JavaCardApp\\photo\\login.jpg"));// NOI18N
        jLabel1.setBounds(0, 0, 580, 300);
        jPanel1.add(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
       f.add(jPanel1);
        pack();
        setLocationRelativeTo(null);
    }

     class InterfaceMain  {
            private javax.swing.JButton jButton2;
            private javax.swing.JButton jButton3;
    	    private javax.swing.JButton jButton4;
    	    private javax.swing.JButton jButton5;
    	    private javax.swing.JLabel jLabel2;
    	    private javax.swing.JPanel jPanel2;

       public InterfaceMain(JFrame f) {
    	   
           jPanel2 = new javax.swing.JPanel();
           jButton3 = new javax.swing.JButton();
           jButton2 = new javax.swing.JButton();
           jButton4 = new javax.swing.JButton();
           jButton5 = new javax.swing.JButton();
           jLabel2 = new javax.swing.JLabel();
           
   		   f.setSize(563,460);
   		   
           jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
           jButton2.setForeground(new java.awt.Color(102, 0, 102));
           jButton2.setText("Ajouter Montant");
           jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102)));
           jButton2.setBounds(40,100, 200, 40);
           
           jButton2.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
               	AjouterMontant c=new AjouterMontant(f,jPanel2);
               	jPanel2.setVisible(false);
               }
           });
           
           
           jLabel2.add(jButton2);

           jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
           jButton3.setForeground(new java.awt.Color(102, 0, 102));
           jButton3.setText("Retirer Montant");
           jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102)));
           jButton3.setBounds(40,200, 200, 40);
           
           jButton3.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
            	   
            	   RetirerMontant c=new RetirerMontant(f,jPanel2);
               	jPanel2.setVisible(false);
               }
           });
           
           
           jLabel2.add(jButton3);

           jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
           jButton4.setForeground(new java.awt.Color(102, 0, 102));
           jButton4.setText("Consulter Solde");
           jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102)));
           
           jButton4.setBounds(40, 300, 200, 40);
           jLabel2.add(jButton4);
           jButton4.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
            	try {
					apdu = client.Msg(AppJavaCard.INS_INTERROGER_SOLDE, (byte) 0x00, null, (byte) 0x7f);
				} catch (IOException | CadTransportException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   				if (apdu.getStatus() != 0x9000) {
   					showMessageDialog(null,"Erreur : status word different de 0x9000");
   				} else {
   					InterfaceConsulter c=new InterfaceConsulter(f,apdu.dataOut[1],jPanel2);
   	               	jPanel2.setVisible(false);
   				}   
               }
           });
           
           jButton5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
           jButton5.setForeground(new java.awt.Color(102, 0, 102));
           jButton5.setText("Quitter");
           jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102), new java.awt.Color(102, 0, 102)));
           jButton5.setBounds(40,400, 200, 40);
           
           jButton5.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                  System.exit(1);
               }
           });
           
           
           jLabel2.add(jButton5);

           jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Eclipse\\JavaCardApp\\photo\\INTERFACE2.jpg"));
           jLabel2.setBounds(0, 0, 570, 390);
           jPanel2.add(jLabel2);

           javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
           getContentPane().setLayout(layout);
           layout.setHorizontalGroup(
               layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
           );
           layout.setVerticalGroup(
               layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
           );
          f.add(jPanel2);
           pack();
           setLocationRelativeTo(null);

       }    
    }
     
     public class AjouterMontant  {
    	    private javax.swing.JButton jButton1;
    	    private javax.swing.JButton jButton2;
    	    private javax.swing.JButton jButton3;
    	    private javax.swing.JButton jButton4;
    	    private javax.swing.JButton jButton5;
    	    private javax.swing.JButton jButton6;
    	    private javax.swing.JLabel jLabel1;
    	    private javax.swing.JLabel jLabel2;
    	    private javax.swing.JPanel jPanel1;
    	    public AjouterMontant(JFrame f,JPanel p) {
    	         
    	    	f.setSize(500,300);
    	        jPanel1 = new javax.swing.JPanel();
    	        jLabel1 = new javax.swing.JLabel();
    	        jButton1 = new javax.swing.JButton();
    	        jButton2 = new javax.swing.JButton();
    	        jButton3 = new javax.swing.JButton();
    	        jButton4 = new javax.swing.JButton();
    	        jButton5 = new javax.swing.JButton();
    	        jButton6 = new javax.swing.JButton();
    	        jLabel2 = new javax.swing.JLabel();


    	        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    	        jLabel1.setText("Montant à ajouter");
    	        jLabel1.setBounds(70, 10, 190, 30);
    	        jLabel2.add(jLabel1);

    	        jButton1.setText("10");
    	        jButton1.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	String mont=jButton1.getLabel();
    	            	byte mont1=(byte)(Integer.parseInt(mont));
    					byte[] montant = new byte[] {mont1 };
    					try {
							apdu = client.Msg(AppJavaCard.INS_INCREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
						} catch (IOException | CadTransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					if (apdu.getStatus() != 0x9000) {
    						showMessageDialog(null, "Erreuuuur!");
    					} else {
    						showMessageDialog(null, "Montant ajouter avec succés");
    					}
    	            }
    	        });
    	        jButton1.setBounds(0, 50,100, 40);
    	        jLabel2.add(jButton1);

    	        jButton2.setText("30");
    	        jButton2.setBounds(0, 90, 100, 40);
    	        jButton2.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	String mont=jButton2.getLabel();
    	            	byte mont1=(byte)(Integer.parseInt(mont));
    					byte[] montant = new byte[] {mont1 };
    					try {
							apdu = client.Msg(AppJavaCard.INS_INCREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
						} catch (IOException | CadTransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					if (apdu.getStatus() != 0x9000) {
    						showMessageDialog(null, "Erreuuuur!");
    					} else {
    						showMessageDialog(null, "Montant ajouter avec succés");
    					}
    	            }
    	        });
    	        jLabel2.add(jButton2);

    	        jButton3.setText("50");
    	        jButton3.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	String mont=jButton3.getLabel();
    	            	byte mont1=(byte)(Integer.parseInt(mont));
    					byte[] montant = new byte[] {mont1 };
    					try {
							apdu = client.Msg(AppJavaCard.INS_INCREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
						} catch (IOException | CadTransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					if (apdu.getStatus() != 0x9000) {
    						showMessageDialog(null, "Erreuuuur!");
    					} else {
    						showMessageDialog(null, "Montant ajouter avec succés");
    					}
    	            }
    	        });
    	        jButton3.setBounds(0, 130,100, 40);
    	        jLabel2.add(jButton3);

    	        jButton4.setText("70");
    	        jButton4.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	String mont=jButton4.getLabel();
    	            	byte mont1=(byte)(Integer.parseInt(mont));
    					byte[] montant = new byte[] {mont1 };
    					try {
							apdu = client.Msg(AppJavaCard.INS_INCREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
						} catch (IOException | CadTransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					if (apdu.getStatus() != 0x9000) {
    						showMessageDialog(null, "Erreuuuur!");
    					} else {
    						showMessageDialog(null, "Montant ajouter avec succés");
    					}
    	            }
    	        });
    	        jButton4.setBounds(0, 170,100, 40);
    	        jLabel2.add(jButton4);

    	        jButton5.setText("80");
    	        jButton5.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	String mont=jButton5.getLabel();
    	            	byte mont1=(byte)(Integer.parseInt(mont));
    					byte[] montant = new byte[] {mont1 };
    					try {
							apdu = client.Msg(AppJavaCard.INS_INCREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
						} catch (IOException | CadTransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					if (apdu.getStatus() != 0x9000) {
    						showMessageDialog(null, "Erreuuuur!");
    					} else {
    						showMessageDialog(null, "Montant ajouter avec succés");
    					}
    	            }
    	        });
    	        jButton5.setBounds(0, 210,100, 40);
    	        jLabel2.add(jButton5);

    	        jButton6.setText("Annuler");
    	        jButton6.addActionListener(new java.awt.event.ActionListener() {
    	            public void actionPerformed(java.awt.event.ActionEvent evt) {
    	            	jPanel1.setVisible(false);
    	            	p.setVisible(true);
    	            	f.setSize(563,460);
    	            }
    	        });
    	        jButton6.setBounds(0, 260, 100, 40);
    	        jLabel2.add(jButton6);

    	        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Eclipse\\JavaCardApp\\photo\\AjouterMontant.jpg")); 
    	        jLabel2.setBounds(0, 0, 500, 300);
    	        jPanel1.add(jLabel2);

    	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    	        getContentPane().setLayout(layout);
    	        layout.setHorizontalGroup(
    	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    	            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	        );
    	        layout.setVerticalGroup(
    	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    	            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	        );

    	         f.add(jPanel1);
	   	         pack();
	   	         setLocationRelativeTo(null);
    	    }
    	}
     
     
     public class RetirerMontant  {
 	    private javax.swing.JButton jButton10;
 	    private javax.swing.JButton jButton2;
 	    private javax.swing.JButton jButton3;
 	    private javax.swing.JButton jButton4;
 	    private javax.swing.JButton jButton5;
 	    private javax.swing.JButton jButton7;
 	    private javax.swing.JButton jButton8;
 	    private javax.swing.JButton jButton9;
 	    private javax.swing.JLabel jLabel1;
 	    private javax.swing.JPanel jPanel2;
     public RetirerMontant(JFrame f,JPanel p) {

         jPanel2 = new javax.swing.JPanel();
         jButton2 = new javax.swing.JButton();
         jButton3 = new javax.swing.JButton();
         jButton4 = new javax.swing.JButton();
         jButton5 = new javax.swing.JButton();
         jButton7 = new javax.swing.JButton();
         jButton8 = new javax.swing.JButton();
         jButton9 = new javax.swing.JButton();
         jButton10 = new javax.swing.JButton();
         jLabel1 = new javax.swing.JLabel();
         f.setSize(564,469);
   
         jButton2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton2.setForeground(new java.awt.Color(153, 0, 153));
         jButton2.setText("10");
         jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 15, true));
         jButton2.setBorderPainted(false);
         jButton2.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String mont=jButton2.getLabel();
            	byte mont1=(byte)(Integer.parseInt(mont));
 				byte[] montant = new byte[] {mont1 };
 				try {
					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
				} catch (IOException | CadTransportException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 				if (apdu.getStatus() == 0x6A85) {
 					showMessageDialog(null, "Vous avez depasser votre solde !");
 					
 				} else {
 					showMessageDialog(null, "Veuillez patienter...");
 				}
             }
         });
         jButton2.setBounds(-20, 90, 150, 40);
         jLabel1.add(jButton2);
         
         jButton3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton3.setForeground(new java.awt.Color(153, 0, 153));
         jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 15, true));
         jButton3.setBorderPainted(false);
         jButton3.setText("30");
         jButton3.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 String mont=jButton3.getLabel();
             	byte mont1=(byte)(Integer.parseInt(mont));
  				byte[] montant = new byte[] {mont1 };
  				try {
 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
 				} catch (IOException | CadTransportException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  				if (apdu.getStatus() == 0x6A85) {
  					showMessageDialog(null, "Vous avez depasser votre solde !");
  					
  				} else {
  					showMessageDialog(null, "Veuillez patienter...");
  				}
                
             }
         });
         jButton3.setBounds(-20, 170, 150, 40);
         jLabel1.add(jButton3);
         
         jButton4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton4.setForeground(new java.awt.Color(153, 0, 153));
         jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 15, true));
         jButton4.setBorderPainted(false);
         jButton4.setText("50");
         jButton4.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 String mont=jButton4.getLabel();
             	byte mont1=(byte)(Integer.parseInt(mont));
  				byte[] montant = new byte[] {mont1 };
  				try {
 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
 				} catch (IOException | CadTransportException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  				if (apdu.getStatus() == 0x6A85) {
  					showMessageDialog(null, "Vous avez depasser votre solde !");
  					
  				} else {
  					showMessageDialog(null, "Veuillez patienter...");
  				}
             }
         });
         jButton4.setBounds(-20, 240, 150, 40);
         jLabel1.add(jButton4);
         
         jButton5.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton5.setForeground(new java.awt.Color(153, 0, 153));
         jButton5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 8, true));
         jButton5.setBorderPainted(false);
         jButton5.setText("70");
         jButton5.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 String mont=jButton5.getLabel();
             	byte mont1=(byte)(Integer.parseInt(mont));
  				byte[] montant = new byte[] {mont1 };
  				try {
 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
 				} catch (IOException | CadTransportException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  				if (apdu.getStatus() == 0x6A85) {
  					showMessageDialog(null, "Vous avez depasser votre solde !");
  					
  				} else {
  					showMessageDialog(null, "Veuillez patienter...");
  				}
             }
         });
         jButton5.setBounds(-20, 320, 150, 40);
         jLabel1.add(jButton5);
         
         jButton7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton7.setForeground(new java.awt.Color(153, 0, 153));
         jButton7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 8, true));
         jButton7.setBorderPainted(false);
         jButton7.setText("80");
         jButton7.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 String mont=jButton7.getLabel();
             	byte mont1=(byte)(Integer.parseInt(mont));
  				byte[] montant = new byte[] {mont1 };
  				try {
 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
 				} catch (IOException | CadTransportException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  				if (apdu.getStatus() == 0x6A85) {
  					showMessageDialog(null, "Vous avez depasser votre solde !");
  					
  				} else {
  					showMessageDialog(null, "Veuillez patienter...");
  				}
             }
         });
         jButton7.setBounds(400, 90, 180, 40);
         jLabel1.add(jButton7);

         jButton8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton8.setForeground(new java.awt.Color(153, 0, 153));
         jButton8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 8, true));
         jButton8.setBorderPainted(false);
         jButton8.setText("90");
         jButton8.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 String mont=jButton8.getLabel();
             	byte mont1=(byte)(Integer.parseInt(mont));
  				byte[] montant = new byte[] {mont1 };
  				try {
 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
 				} catch (IOException | CadTransportException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
  				if (apdu.getStatus() == 0x6A85) {
  					showMessageDialog(null, "Vous avez depasser votre solde !");
  					
  				} else {
  					showMessageDialog(null, "Veuillez patienter...");
  				}
             }
         });
         jButton8.setBounds(400, 170, 180, 40);
         jLabel1.add(jButton8);

         jButton9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton9.setForeground(new java.awt.Color(153, 0, 153));
         jButton9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 8, true));
         jButton9.setBorderPainted(false);
         jButton9.setText("100");
         jButton9.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	           	 String mont=jButton9.getLabel();
	             	byte mont1=(byte)(Integer.parseInt(mont));
	  				byte[] montant = new byte[] {mont1 };
	  				try {
	 					apdu = client.Msg(AppJavaCard.INS_DECREMENTER_SOLDE, (byte) 0x01, montant, (byte) 0x7f);
	 				} catch (IOException | CadTransportException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	  				if (apdu.getStatus() == 0x6A85) {
	  					showMessageDialog(null, "Vous avez depasser votre solde !");
	  					
	  				} else {
	  					showMessageDialog(null, "Veuillez patienter...");
	  				}
	            }
	        });
         jButton9.setBounds(400, 240, 180, 40);
         jLabel1.add(jButton9);

         jButton10.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
         jButton10.setForeground(new java.awt.Color(153, 0, 153));
         jButton10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 102, 255), 8, true));
         jButton10.setBorderPainted(false);
         jButton10.setText("Retourner          ");
         jButton10.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	jPanel2.setVisible(false);
	            	p.setVisible(true);
	            	f.setSize(563,460);
	            }
	        });
         jButton10.setBounds(400, 320, 180, 40);
         jLabel1.add(jButton10);

         jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Eclipse\\JavaCardApp\\photo\\InterfaceRetrier.jpg")); // NOI18N
         jLabel1.setBounds(0, 0, 570, 470);
         jPanel2.add(jLabel1);

         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );
         f.add(jPanel2);
         pack();
         setLocationRelativeTo(null);
     }
     }
     
     class InterfaceConsulter  {
 	    private javax.swing.JButton jButton1;
 	    private javax.swing.JLabel jLabel1;
 	    private javax.swing.JLabel jLabel2;
 	    private javax.swing.JLabel jLabel3;
 	    private javax.swing.JPanel jPanel1;
 	    
       public InterfaceConsulter(JFrame f,byte data,JPanel p) {
    	   String stringValue = "" + data;
         jPanel1 = new javax.swing.JPanel();
         jLabel1 = new javax.swing.JLabel();
         jLabel2 = new javax.swing.JLabel();
         jButton1 = new javax.swing.JButton();
         jLabel3 = new javax.swing.JLabel();
         f.setSize(564,469);
         jLabel1.setBackground(new java.awt.Color(204, 204, 204));
         jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
         jLabel1.setForeground(new java.awt.Color(102, 0, 102));
         jLabel1.setText("   Votre Solde est :");
         jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 2, true));
         jLabel1.setBounds(20, 210, 230, 30);
         jLabel3.add(jLabel1);

         jLabel2.setBackground(new java.awt.Color(204, 204, 204));
         jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
         jLabel2.setForeground(new java.awt.Color(102, 0, 102));
         jLabel2.setText(stringValue);
         
         jLabel2.setBounds(80, 270, 130, 30);
         jLabel3.add(jLabel2);

         jButton1.setBackground(new java.awt.Color(204, 204, 204));
         jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
         jButton1.setForeground(new java.awt.Color(102, 0, 102));
         jButton1.setText("Retourner");
         jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153), new java.awt.Color(153, 0, 153)));
         jButton1.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPanel1.setVisible(false);
                p.setVisible(true);
                f.setSize(564,469);
             }
         });
         jButton1.setBounds(0, 350, 180, 40);
         jLabel3.add(jButton1);

         jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Eclipse\\JavaCardApp\\photo\\interfaceConsulter.jpg"));
         jLabel3.setBounds(0, 0, 560, 400);// NOI18N
         jPanel1.add(jLabel3);

         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         );
         f.add(jPanel1);
         pack();
         setLocationRelativeTo(null);
     }     
     }
     
    public static void main(String args[]) throws IOException, CadTransportException {
    	Test client = new Test();
        client.Connect();
		client.Select();
    	 app=new AppJavaCard(client);
        app.setVisible(true);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppJavaCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppJavaCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppJavaCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppJavaCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        

    }


}
