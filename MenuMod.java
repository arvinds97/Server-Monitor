package tcp_connections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
//import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
//import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.SocketException;
//import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//import javax.swing.BorderFactory;
//import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.WindowConstants;

import com.jcraft.jsch.JSchException;

//import com.jcraft.jsch.JSchException;
    


   
    // There are four classes under the package tcp_connections, MenuMod-->Mymethods-->InnerOperations
																//            ||
																//			  ||
																//            \/
																//		graphpanel
    // Make sure you have a backup of all classes.
    // Make sure you add external packages like  jsch to your library.
    // i would have shared the jar files ,or you can also download it .
    /* Ok,let's start, this is the main class, this is where, i'm defining my main window
     Ok, i'm  assuming that  you know about the difference between JFrame and Jpanel, If you don't ,google
     I'm also assuming that you can identify all the different layouts that i've used, if not google
     These websites can help you grasp the basics:-
     1. https://www.javatpoint.com/java-swing
     2.https://www.tutorialspoint.com/swing/
     ok ,  so in this code i have few elements which i'm using to build the appplication
     main_window - the main frame on which i'm showing everything, nothing goes out of this
     content_panel - this is where i have all my content.
     If you observe, I have used a BorderLayout.
     So, I have a left ,right,top,bottom and a center to my layout
     Now, i have given the left,right,top and bottom borders a dark gray colour and in the center , i have my CONTENT_PANEL
     As understood by the name itself, this is where i have all my content of my frame.
     Moving on,
      So, in my content_panel, I'm using the gridbaglayout, this is like a modified version of the gridlayout,by the way, google has the answer to all  doubts!
     content - the orange panel
     selectpanel -the panel with all the buttons
     p1-panel for the first button
     p2-panel for the second button
     p3-panel for the third button
     p4-panel for the fourth button
     p5-panel for the fifth button
     p6-panel for the sixth button
     Every panel has a seperate method in tcp_connections.Mymethods and some of the methods in tcp_connections.Mymethods uses some of the methods in tcp_connections.InnerOperations
     For the graph, I'm using the class , tcp_connections.graphpanel
     */
public class MenuMod {
	JFrame main_window;
	JPanel content_panel;
	JPanel selectpanel, pdisk1;
	JPanel content;
	static JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static JPanel p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static JPanel p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static JPanel p6 = new JPanel(new GridLayout(1, 2));
	static JPanel p7 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	static int i = 0, j = 0, k = 0, l = 0, iram = 0, isql = 0, idisk = 0, iserver = 0, icheckserver = 0, iurl = 0,
			icheckapi = 0;
	static JButton close = new JButton("CLOSE");
	static int letscount = 0;
	static String mem_tot, mem_used, mem_free, mem_buff, mem_shared, mem_avail;
	static Date date = new Date();
	static Format formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
	static String s = formatter.format(date);
	String mod = s + ".txt";
	final JPanel p_server = new JPanel(new GridLayout(3, 1)), p_sql = new JPanel(new GridLayout(3, 1)),
			p_disk = new JPanel(), p_ram = new JPanel();
	JPanel pram1 = new JPanel(new GridLayout(2, 1));
	JLabel l1 = new JLabel(" This is the first try");

	MenuMod() {

		main_window = new JFrame();
		main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_window.setResizable(false);
		selectpanel = new JPanel(new GridLayout());//used gridlayout for panel with main 7 buttons
		content_panel = new JPanel(new GridBagLayout());// used gridbaglyout for the center of border layout
		content = new JPanel(new FlowLayout());// the orange panel
		GridBagConstraints c = new GridBagConstraints();// a component used with gridbaglayout
		GridBagConstraints gbc = new GridBagConstraints();
		border();// using borderlayout
		prepgridbag(c);
		selectBar(c, gbc);
		contentWindow(c);
		content.setSize(800, 800);
		content_panel.setSize(1200, 800);
		main_window.setSize(1200, 735);
		main_window.setVisible(true);
	}
    // Preparing the components of gridbaglayout
	void prepgridbag(GridBagConstraints c) {
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		content_panel.add(selectpanel, c);
		c.ipady = 600; // make this component tall
		c.ipadx = 1200;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		content_panel.add(content, c);
	}

	void border() {
		JPanel right_border = new JPanel();
		JPanel left_border = new JPanel();
		JPanel top_border = new JPanel();
		JPanel bottom_border = new JPanel();
		right_border.setBackground(Color.GRAY);
		left_border.setBackground(Color.GRAY);
		top_border.setBackground(Color.GRAY);
		bottom_border.setBackground(Color.GRAY);
		content_panel.setBackground(Color.lightGray);
		main_window.add(right_border, BorderLayout.EAST);
		main_window.add(left_border, BorderLayout.WEST);
		main_window.add(top_border, BorderLayout.NORTH);
		main_window.add(bottom_border, BorderLayout.SOUTH);
		main_window.add(content_panel, BorderLayout.CENTER);// the part where all the content comes into picture
	}

	void selectBar(GridBagConstraints c, GridBagConstraints gbc) {

		JPanel selectbar = new JPanel(new GridLayout(1, 7));//JPanel(new GridLayout(1 row, 7 columns)) 
		JButton select_server = new JButton("SERVER STATUS");
		JButton select_sql = new JButton("SERVER DETAILS");
		JButton select_disk = new JButton(" DISK DETAILS");
		JButton select_ram = new JButton(" RAM DETAILS");
		JButton select_service = new JButton("SERVICE STATUS");
		JButton select_url = new JButton("URL CONNECTION STATUS");
		// JButton select_api = new JButton(" API STATUS");// dont need this anymore :)
		selectbar.add(select_server);
		selectbar.add(select_sql);
		selectbar.add(select_disk);
		selectbar.add(select_ram);
		selectbar.add(select_service);
		selectbar.add(select_url);
		// selectbar.add(select_api);
		selectbar.add(close);
		selectpanel.setBackground(Color.YELLOW);
		selectpanel.setBounds(0, 0, 1400, 50);
		selectpanel.add(selectbar);
        // button 1
		select_server.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				letscount++;
				if (letscount == 1) { // This if ensures smooth functioning of this button because connecting to the server takes time ,
					// so wer're displaying the wait frame only if this button is the first to be clicked so that the user doesn't think
					// that the application is lagging
					final JFrame frame = InnerOperations.waitFrame();// has declaration for the frame which tells the user that the application is fetching data
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								
								addserverdetails(p1); // This is the inner part
								content.add(p1);// adding p1 to orange  panel
								p1.setBounds(200, 5, 800, 600);//next 7 lines including this one , reduces complications of switching buttons
								p2.setBounds(200, 0, 0, 0);// better not to touch this part
								p3.setBounds(200, 0, 0, 0);
								p4.setBounds(200, 0, 0, 0);
								p5.setBounds(200, 0, 0, 0);
								p6.setBounds(200, 0, 0, 0);
								p7.setBounds(200, 0, 0, 0);
								p1.setVisible(true);
								// now , 
								content_panel.add(content, c);
								main_window.add(content_panel, BorderLayout.CENTER);
								p1.setBounds(200, 5, 800, 600);

							} catch (Exception e) { InnerOperations.ExceptionDisplay(e);
							}
							frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
						}
					}).start();
				} else {
					try {
                        
						addserverdetails(p1);// again this has the actual operations, this is where i add actual operations to p1, or this is where i modify p1
						// every button from here on will have some add____details() function , this is where i'm adding all information to p1,p2,p3,p4,p5,p6 depending on the button
						content.add(p1);// adding p1 to my orange panel
						p1.setBounds(200, 5, 800, 600);
						p2.setBounds(200, 0, 0, 0);
						p3.setBounds(200, 0, 0, 0);
						p4.setBounds(200, 0, 0, 0);
						p5.setBounds(200, 0, 0, 0);
						p6.setBounds(200, 0, 0, 0);
						p7.setBounds(200, 0, 0, 0);
						p1.setVisible(true);
						content_panel.add(content, c);
						main_window.add(content_panel, BorderLayout.CENTER);
						p1.setBounds(200, 5, 800, 600);
					} catch (Exception e) { InnerOperations.ExceptionDisplay(e);
					}
				}
				// p1.setBackground(Color.lightGray);
			}
		});
		//button 2
		select_sql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					letscount++;// let this stay here, this helps p1
					final JFrame frame = InnerOperations.waitFrame();
					new Thread(new Runnable() {  
						@Override
						public void run() {
							try {

								addsqldetails(p2); // Again, actual operations
								content.add(p2);// adding p2 to the orange panel
								p2.setBounds(200, 5, 800, 600);
								p3.setBounds(200, 0, 0, 0);
								p1.setBounds(200, 0, 0, 0);
								p4.setBounds(200, 0, 0, 0);
								p5.setBounds(200, 0, 0, 0);
								p6.setBounds(200, 0, 0, 0);
								p7.setBounds(200, 0, 0, 0);
								p2.setVisible(true);
								content_panel.add(content, c);//adding orange frame to center layout of border layout
								main_window.add(content_panel, BorderLayout.CENTER);// adding center layout of border layout to the main frame
								p2.setBounds(200, 5, 800, 600);// Don't care about this, this should be there
								p2.setBackground(Color.LIGHT_GRAY);
								System.out.println("Debugging");
							} catch (Exception e) { InnerOperations.ExceptionDisplay(e);
							} catch (Throwable e) {
								
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
						}
					}).start();// spawning a new thread

				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		//button 3
		select_disk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				letscount++;
				final JFrame frame = InnerOperations.waitFrame();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							adddiskdetails(p3); // Actual operations
							content.add(p3);// adding p3 to orange frame
							p3.setBounds(200, 5, 800, 600);// here, i'm downsizing all other panels so that only only p3 is visible properly
							p2.setBounds(200, 0, 0, 0);
							p1.setBounds(200, 0, 0, 0);
							p4.setBounds(200, 0, 0, 0);
							p5.setBounds(200, 0, 0, 0);
							p6.setBounds(200, 0, 0, 0);
							p7.setBounds(200, 0, 0, 0);
							p3.setVisible(true);
							content_panel.add(content, c);
							main_window.add(content_panel, BorderLayout.CENTER);
							p3.setBounds(200, 5, 800, 600);
							p3.setBackground(Color.lightGray);

						} catch (Exception e) {
							InnerOperations.ExceptionDisplay(e);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE the wait frame
					}
				}).start();// Spawning a new thread

			}
		});
		// button 4
		select_ram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					letscount++;
					final JFrame frame = InnerOperations.waitFrame();
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {

								addramdetails(p4); // Actual operations
								content.add(p4);// adding p4 to orange frame
								p4.setBounds(200, 5, 800, 600);
								p2.setBounds(200, 0, 0, 0);
								p1.setBounds(200, 0, 0, 0);
								p3.setBounds(200, 0, 0, 0);
								p5.setBounds(200, 0, 0, 0);
								p6.setBounds(200, 0, 0, 0);
								p7.setBounds(200, 0, 0, 0);
								p4.setVisible(true);
								content_panel.add(content, c);// you know what this does :)
								main_window.add(content_panel, BorderLayout.CENTER);
								p4.setBounds(200, 5, 800, 600);
								p4.setBackground(Color.lightGray);
							} catch (Exception e) {
								InnerOperations.ExceptionDisplay(e);
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
						}
					}).start();

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		});
		// button 5
		select_service.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				letscount++;

				addservicedetails(p5, content); // you know what this does :)
				content.add(p5);// adding p5 to orange panel
				p5.setBounds(200, 5, 800, 600);
				p2.setBounds(200, 0, 0, 0);
				p1.setBounds(200, 0, 0, 0);
				p4.setBounds(200, 0, 0, 0);
				p3.setBounds(200, 0, 0, 0);
				p6.setBounds(200, 0, 0, 0);
				p7.setBounds(200, 0, 0, 0);
				p5.setVisible(true);
				content_panel.add(content, c);
				main_window.add(content_panel, BorderLayout.CENTER);
				p5.setBounds(200, 5, 800, 600);
				p5.setBackground(Color.BLACK);

			}
		});
		// button 6
		select_url.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				letscount++;

				addurlconnectiondetails(p6); // :) adding url connection details to the panel
				content.add(p6);//adding p6 to the orange panel
				p6.setBounds(200, 5, 800, 600);
				p2.setBounds(200, 0, 0, 0);
				p1.setBounds(200, 0, 0, 0);
				p4.setBounds(200, 0, 0, 0);
				p3.setBounds(200, 0, 0, 0);
				p5.setBounds(200, 0, 0, 0);
				p7.setBounds(200, 0, 0, 0);
				p6.setVisible(true);
				content_panel.add(content, c);
				main_window.add(content_panel, BorderLayout.CENTER);
				p6.setBounds(200, 5, 800, 600);

			}
		});
       // button 7
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				main_window.dispose();
			}
		});
	}

	void contentWindow(GridBagConstraints c) {

		content.setBackground(Color.ORANGE); //this is that orange panel to which i added p1,p2,p3,p4.p5,p6
		final int x = 2000;
		final int y = 800;
		content.setSize(x, y);

	}

	void addramdetails(JPanel p4)  {
		iram++;
		content.removeAll();
		Mymethods.prepare_ram(s, mem_tot, mem_used, mem_free, mem_shared, mem_buff, mem_avail, p4, pram1, iram,
				content);// calling the method from Mymethods class, this will feed p4 with information to display

	}

	void adddiskdetails(JPanel p3)  {
		idisk++;
		content.removeAll();
		Mymethods.prepare_disk(p3, idisk, content);// calling the method from Mymethods class, this will feed p3 with information to display
	}

	void addsqldetails(JPanel p2) throws IOException, JSchException  {
		isql++;
		content.removeAll();
		Mymethods.jdbc_work(p2, content, isql);// calling the method from Mymethods class, this will feed p2 with information to display
	}

	void addservicedetails(JPanel p5, JPanel content) {
		content.removeAll();
		iserver++;
		Mymethods.prepare_service(p5, content, iserver);// calling the method from Mymethods class, this will feed p5 with information to display
	}

	void addurlconnectiondetails(JPanel p6) {
		content.removeAll();
		iurl++;
		Mymethods.prepare_url(p6, content, iurl);// calling the method from Mymethods class, this will feed p6 with information to display
	}

	void addserverdetails(JPanel p1){
		p1.setBackground(Color.LIGHT_GRAY);
		InnerOperations.getjdbcCredentials();// You need a mysql connector, to do so , you need credentials, read them from a properties file, this method does that
		Mymethods.prepare_servercheck(p1, content, icheckserver);// calling the method from Mymethods class, this will feed p1 with information to display
	}

	void updatemygridbag(GridBagConstraints gbc) { 
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 800;
		gbc.ipady = 600;
		gbc.gridheight = 0;
		gbc.gridwidth = 0;

	}

	public static void main(String[] args) {
		// Auto-generated method stub
		InnerOperations.getjdbcCredentials();// for connecting to the mysql database , we take the credentials from the properties file
		InnerOperations.getCredentials();// for connecting to the server , we take the credentials from the properties file
		new MenuMod(); // let's run this whole project !
		// Programmatically , this is where the program control reaches when you stop the application
		// Signing off- Arvind Sudheer
	}

}
