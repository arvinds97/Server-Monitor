package tcp_connections;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
//import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
//import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
//import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
//import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
//import java.util.Properties;
//import java.util.Scanner;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
//import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
//import javax.tools.DocumentationTool.DocumentationTask;
import javax.swing.*;

public class Mymethods {
	static String user;
	static String password;
	static String host;// you'll need the above two lines including this one for connecting to the
						// server
	static String jdbc_url;
	static String jdbc_driver;
	static String dbuserName;
	static String dbpassword;
	// you'll need this to connect to the mysql database
	static PrintStream ps;
	static int n;
	static Thread thread = new Thread("Button is busy"); // i haven't used this anywhere,feel free to clear it :)
	// just increasing the number of lines in the code :)
	static Date date = new Date();
	static Format formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
	static String s = formatter.format(date); // storing date in the above format
	File file = new File("/"); // was a part of developing this project
	String path;
	static JLabel extra = new JLabel(); // you will come across this in one of the methods
	static JLabel waiting = new JLabel();
	static int saving = 0;
	static int counter;
	static String pathh;
	static String filepath;
	static String ip;
	static JLabel ldisk1 = new JLabel();
	static JLabel lram = new JLabel();
	static JLabel l_check = new JLabel();
	static JPanel pdisk1 = new JPanel(new FlowLayout()), pdisk_table = new JPanel(new FlowLayout()),
			pram_save = new JPanel(new FlowLayout()), pram_table = new JPanel(new FlowLayout());
	static int val = 0;
	static String result = "";

	public static void jdbc_work(JPanel p2, JPanel content, int count) throws IOException {

		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			// make sure you add then jsch jar to your project
			JSch jsch = new JSch();
			Session session;

			session = jsch.getSession(Mymethods.user, Mymethods.host, 22);

			session.setPassword(Mymethods.password);
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("exec");
			System.out.println("Connected to Server  :" + channel.getId());
			((ChannelExec) channel).setCommand("mysql -h localhost -u " + Mymethods.dbuserName + " --password="
					+ Mymethods.dbpassword + " -e \"show status like 'Con%'\";\n" + "");
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();// at first line of output
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();// moving to next line of output
				if (line.contains("Connections")) {

					break;
				}
			}
			String[] words = line.split("Connections");
			Mymethods.val = Integer.parseInt(words[1].trim());
			System.out.println(Mymethods.val);
			Mymethods.prepare_sql(p2, content, count);
			channel.disconnect();
			if (session != null && session.isConnected()) {
				System.out.println("Closing SSH Connection");
				session.disconnect();
			}
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void prepare_servercheck(JPanel p1, JPanel content, int count) {
		InnerOperations.removeexcept(1, content);// clears content for smooth display of p1
		JLabel label = new JLabel("");

		String data;
		data = InnerOperations.check_server("mysql");

		String sub = data.substring(0, 17);
		System.out.println(data);
		System.out.println(sub);
		if (sub.contains("active (running)"))// investigating !

		{
			label.setText(" SERVER IS RUNNING !");
			label.setForeground(Color.BLUE);
			label.setFont(new Font("Serif", Font.PLAIN, 20));
		} else {
			label.setText("SERVER IS NOT RUNNING !");
			label.setForeground(Color.red);
			label.setFont(new Font("Serif", Font.PLAIN, 20));
		}
		p1.add(label);// adding the label to the panel
		label.setBounds(260, 210, 800, 40);// setting the boundary is very important when you are not using any layouts

	}

	public static void prepare_sql(JPanel p2, JPanel content, int count) {
		System.out.println("Button clicked ");// helps in debugging
		JPanel psqldate, psql_conn, psql_ip;
		JLabel lsql3, lsql4, lsql5, lsql6;
		JLabel lsql1 = new JLabel();
		lsql4 = new JLabel();
		if (count != 1) { // saves the day when the button is pressed more than once, basically refreshes
							// the panel
			p2.removeAll();
			p2.revalidate();
			System.out.println("Count is greater than 1");
		}
		JPanel psql_back = new JPanel(new FlowLayout(FlowLayout.CENTER));// this panel is where i'll put my other panels
		// just imagine keeping books in an organised way on a study table, in this
		// case, the study table is psql_back
		psqldate = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));
		psqldate.setBorder(BorderFactory.createLineBorder(Color.black));
		psql_conn = new JPanel(new GridLayout(3, 1));
		psql_conn.setBorder(BorderFactory.createLineBorder(Color.black));
		psql_ip = new JPanel(new GridLayout(2, 1));
		psql_ip.setBorder(BorderFactory.createLineBorder(Color.black));
		PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
		System.setOut(stdout);
		getIP();// gets the ip of your network alloted by the dhcp,was used in during the
				// development of this project
		stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
		String noOfCon;

		noOfCon = Integer.toString(number_tcp_connect());

		// int x1, a;
		String x2 = Integer.toString(number_udp_connect());
		JLabel lsql2 = new JLabel("    Number of tcp connections :" + noOfCon);
		lsql5 = new JLabel("HOST IP ADDRESS :" + host);
		lsql6 = new JLabel("  MySQL SERVER CONNECTIONS  :" + val);
		lsql6.setFont(new Font("Serif", Font.PLAIN, 18));
		System.out.println("got server connections");
		lsql3 = new JLabel("    Number of udp connections :" + x2);
		System.setOut(stdout);
		System.out.println("Got udp connections");
		String loc = " Details Saved at :" + pathh;
		lsql4.setText(loc);
		lsql1.setText(s);
		lsql1.setVisible(true);// helps in the long run
		lsql2.setVisible(true);
		lsql3.setVisible(true);
		lsql5.setVisible(true);
		lsql6.setVisible(true);
		lsql1.setBounds(580, 0, 800, 20);// setting boundary,very important
		lsql6.setBounds(200, 0, 800, 40);
		lsql2.setBounds(250, 40, 800, 20);
		lsql5.setBounds(290, 20, 800, 20);
		lsql3.setBounds(250, 90, 800, 20);
		lsql4.setBounds(0, 120, 800, 20);
		// while adding components to the panels, make sure you add the components to
		// the smaller panels and then add the smaller panels to the bigger panels
		psqldate.add(lsql1);
		psql_conn.add(lsql3);
		psql_conn.add(lsql2);
		psql_conn.add(lsql6);
		psql_ip.add(lsql5);
		psql_ip.add(lsql4);
		psql_back.add(psql_ip);
		psql_back.add(psql_conn);
		psql_back.add(psqldate);
		p2.add(psql_back);
		System.out.println("Reached here also");
		psql_back.setBounds(5, 100, 795, 400);
		psqldate.setBounds(0, 0, 795, 40);
		psql_conn.setBounds(0, 40, 795, 200);
		psql_ip.setBounds(0, 240, 795, 160);
		InnerOperations.removeexcept(2, content);// For smooth display of p2
	}

	public static void prepare_disk(JPanel p3, int count, JPanel content) {
		try {
			List<String> disk_lines = new ArrayList<String>();
			InputStream in = InnerOperations.connectServer(Mymethods.host, Mymethods.user, Mymethods.password, "df -m");// this
																														// method
																														// uses
																														// jsch
																														// to
																														// connect
																														// to
																														// the
																														// server
																														// and
																														// excecute
																														// commands
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));// Reading the output of the command
																					// passed
			String line = reader.readLine();// at first line of output
			line = reader.readLine();// at second line of ouput
			int i = 0;
			for (i = 0; line != null; i++) {

				disk_lines.add(line);// adding the outputs to a list
				System.out.println(line);

				line = reader.readLine();

			}

			InnerOperations.closeserverconnection();// Closing the connection to the server is important
			List<String> usingalist = new ArrayList<String>();
			// int sizee;
			Date date = new Date();
			Format formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			String s = formatter.format(date);
			JButton bdisk1 = new JButton("Save");
			System.out.println("Reached here");
			pdisk1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
			pdisk_table = new JPanel(new FlowLayout(FlowLayout.CENTER));
			ldisk1.setText("");

			int j = 0;
			/// int test = 0;

			while (j < i) {
				String[] temp = (disk_lines.get(j)).split(" ");

				for (int k = 0; k < temp.length; k++) {
					usingalist.add(temp[k]);

				}

				j++;

			}

			usingalist.removeAll(Collections.singleton(""));
			usingalist.removeAll(Collections.singleton("on"));
			System.out.println(usingalist.size());
			String row[][] = new String[j][6];
			int u = 0, v = 0;
			System.out.println(i);
			int p = 0;
			for (u = 0; u < i; u++) {
				for (v = 0; v < 6; v++) {

					row[u][v] = usingalist.get(p);// gets all the info for your JTable
					p++;
				}

			}

			u = 0;
			v = 0;
			for (u = 0; u < i; u++) {
				for (v = 0; v < 6; v++)
					System.out.print(row[u][v] + " ");
				System.out.println("");

			}
			System.out.println("added");
			String column[] = { "FileSystem", "1M-blocks", "Used", "Available", "Use %", "Mounted" };// Headings for
																										// your JTable
			JTable jt = new JTable(row, column);
			jt.setBounds(0, 60, 800, 150);
			TableColumnModel columnModel = jt.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(150);
			columnModel.getColumn(1).setPreferredWidth(150);
			columnModel.getColumn(2).setPreferredWidth(120);
			columnModel.getColumn(3).setPreferredWidth(120);
			columnModel.getColumn(4).setPreferredWidth(50);
			columnModel.getColumn(5).setPreferredWidth(190);
			JScrollPane sp1 = new JScrollPane(jt);
			if (count != 1) { // Refreshes the panel
				p3.removeAll();
				p3.revalidate();
				System.out.println("Count is greater than 1");
			}
			sp1.setBounds(0, 100, 800, 200);
			pdisk_table.add(sp1);
			pdisk_table.setBounds(0, 0, 800, 500);
			p3.add(bdisk1);
			p3.add(pdisk_table);
			p3.add(pdisk1);
			sp1.setVisible(true);
			bdisk1.setVisible(true);
			pdisk_table.setVisible(true);
			bdisk1.setBounds(350, 550, 80, 40);

			bdisk1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					preparedisklabel(p3, "Saved at " + InnerOperations.writeIntoFile(s));

					System.out.println("Saved ");
				}
			});
			InnerOperations.removeexcept(3, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		}
	}

	public static void preparedisklabel(JPanel p3, String data) { // displays the path of saved file
		JLabel extra = new JLabel(data);
		p3.add(extra);
		extra.setVisible(true);
		extra.setForeground(Color.red);
		extra.setBounds(190, 500, 600, 20);
		System.out.println(" Executed ");
	}

	public static int number_tcp_connect() {
		String tcp = "tcp connections " + date;
		String exec = " netstat -anp --tcp ";
		String txt[] = { "tcp", "tcp6" };
		int t;
		t = getFile(tcp, exec, txt);

		return t;

	}

	public static int number_udp_connect() {
		String tcp = "tcp udp connections " + date;
		String exec = " netstat -anp --tcp --udp";
		String txt[] = { "udp", "udp6" };
		int u;
		u = getFile(tcp, exec, txt);

		return u;

	}

	public static int getFile(String name, String exec, String[] txt) { // this function basically goes to another
																		// function, where you can connect to the server
																		// or cloud and run commands on the server
		try {
			File f = new File(name);
			int q = 0;
			FileOutputStream fos;

			fos = new FileOutputStream(f);

			ps = new PrintStream(fos);
			System.setOut(ps);// shifting stream of printing into file
			pathh = f.getAbsolutePath();
			InputStream in = InnerOperations.connectServer(host, user, password, exec);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();// at the first line of the output
			while (line != null) {
				String words[] = line.split("\\s");
				if (words[0].equalsIgnoreCase(txt[0]) || words[0].equalsIgnoreCase(txt[1]))
					q++;
				System.out.println(line);
				line = reader.readLine();// moving to the next line of the output
			}
			InnerOperations.closeserverconnection();// important step
			PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
			System.setOut(stdout);// shifting stream of printing back to console
			System.out.println("Exiting " + txt[0] + " method");
			return q;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		}
		return 0;
	}

	static String getIP() {
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session;

			session = jsch.getSession(user, host, 22);

			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			Enumeration<NetworkInterface> nets;
			nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface nic = nets.nextElement();
				if (nic.isUp() && !nic.isLoopback()) {
					System.out.println("Inside getnetworkinterface");
					Enumeration<InetAddress> addrs = nic.getInetAddresses();
					while (addrs.hasMoreElements()) {
						InetAddress add = addrs.nextElement();
						if (add instanceof Inet4Address && !add.isLoopbackAddress()) {
							ip = add.getHostAddress();// givs the ip of your network -dhcp
							System.out.println(ip);
						}
					}
				}
			}

			session.disconnect();
			return ip;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		}
		return null;
	}

	public static void prepare_ram(String s, String mem_tot, String mem_used, String mem_free, String mem_shared,
			String mem_buff, String mem_avail, JPanel p4, JPanel pram1, int count, JPanel content) {

		System.out.println("In ram component");// helps in debugging

		try {

			InputStream in = InnerOperations.connectServer(host, user, password, "free -m");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();// at first line of output
			for (int i = 0; line != null; i++) {
				if (i == 1) {

					String words[] = line.split("        ");
					mem_tot = words[1];
					mem_used = words[2];
					mem_free = words[3];
					mem_shared = words[4];
					mem_buff = words[5];
					mem_avail = words[6];
				}

				line = reader.readLine();// moving to next line of output

			}
			InnerOperations.closeserverconnection();
			System.out.println("DONE");
		} catch (Exception e) {
			InnerOperations.ExceptionDisplay(e);
			InnerOperations.ExceptionDisplay(e);
			e.printStackTrace();
		}

		pram_save = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pram_table = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pram_save.setVisible(false);
		JButton bram1 = new JButton("Save");
		JButton bram2 = new JButton("Show Graph");
		String data[][] = { { "TOTAL MEMORY", mem_tot }, { "USED MEMORY", "   " + mem_used },
				{ "FREE MEMORY", "   " + mem_free }, { "SHARED MEMORY", "   " + mem_shared },
				{ "BUFF/CACHE MEMORY", "   " + mem_buff }, { "AVAILABLE MEMORY", "   " + mem_avail }, };
		String column[] = { "MAIN MEMORY", "SIZE in MB" };
		JTable jt = new JTable(data, column);
		jt.setBounds(0, 60, 150, 50);
		if (count != 1) { // refreshing panel
			p4.removeAll();
			p4.revalidate();
			System.out.println("Count is greater than 1");
		}

		PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
		System.setOut(stdout);// shifting stream of printing back to console

		JScrollPane sp1 = new JScrollPane(jt);
		sp1.setBounds(0, 20, 800, 150);// remember, table is inside scrollpane
		pram1.setVisible(true);
		pram1.setSize(800, 550);
		pram_table.add(sp1);
		pram_table.setBounds(0, 0, 800, 300);
		// do not mix up this sequence of adding components to the panel
		p4.add(bram1);
		p4.add(bram2);
		p4.add(pram_table);
		p4.add(pram1);
		sp1.setVisible(true);
		bram1.setVisible(true);
		pram_table.setVisible(true);
		bram2.setBounds(300, 450, 200, 40);// setting bounds,essential
		bram1.setBounds(350, 500, 80, 40);
		bram1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println(" Button pressed");
				saving++;// helps update path on multiple clicks on save

				System.out.println("going to go to function");// helps in debugging
				Date ramdate = new Date();
				Format ramformatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
				String s_ram = ramformatter.format(ramdate);
				String ram = "ram info " + s_ram;// this is your file name
				File f_new = new File(ram);
				counter = 1;
				final JFrame frame = InnerOperations.waitFrame();

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							FileOutputStream fos = new FileOutputStream(f_new);

							PrintStream ps = new PrintStream(fos);

							final String path = f_new.getAbsolutePath();
							try {
								System.setOut(stdout);
								InputStream in1 = InnerOperations.connectServer(host, user, password, "free -m");
								System.setOut(ps);
								BufferedReader reader = new BufferedReader(new InputStreamReader(in1));
								String line = reader.readLine();
								while (line != null) {

									System.out.println(line);

									line = reader.readLine();

								}

								InnerOperations.closeserverconnection();
								System.setOut(stdout);
								System.out.println("DONE");
							} catch (Exception e) {
								InnerOperations.ExceptionDisplay(e);
								InnerOperations.ExceptionDisplay(e);
								e.printStackTrace();
							}
							PrintStream stdout = new PrintStream(new FileOutputStream(FileDescriptor.out));
							System.setOut(stdout);
							if (saving > 1) {
								;
								System.out.println(saving + ":" + path);
							}
							prepareramlabel(p4, "Saved at : " + path);
						} catch (Exception e) {
							InnerOperations.ExceptionDisplay(e);
						}
						frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
					}
				}).start();

				System.out.println("went to function");

			}
		});
		bram2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				counter++;
				final JFrame frame = InnerOperations.waitFrame();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							graphpanel.launchGraph(counter); // Displaying graph which shows real time ram usage
						} catch (Exception e) {
							InnerOperations.ExceptionDisplay(e);
						}
						frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
					}
				}).start();

			}
		});
		InnerOperations.removeexcept(4, content);

	}

	public static void prepareramlabel(JPanel p4, String data) {
		p4.remove(extra);
		extra.setText(data);
		p4.add(extra);
		System.out.println("Added extra");
		extra.setVisible(true);
		extra.setForeground(Color.red);
		extra.setBounds(190, 550, 600, 20);
		System.out.println(" Executed ");
	}

	public static void prepare_service(JPanel p5, JPanel content, int count) {

		if (count != 1) { // refreshing
			p5.removeAll();
			p5.revalidate();
			System.out.println("Count is greater than 1");
		}
		JPanel p_back = new JPanel();
		// int secondchoice = 1;
		JTextField field = new JTextField(" Enter services with delimiter ';' ");
		JButton check_list = new JButton(" CHECK SERVICE LIST STATUS");
		JButton check_service = new JButton(" CHECK DEFAULT SERVICE LIST");
		p_back.add(check_service);
		p_back.add(check_list);
		p_back.add(field);
		p_back.add(l_check);
		l_check.setBounds(50, 300, 600, 20);
		field.setBounds(200, 50, 400, 30);
		check_list.setBounds(200, 90, 400, 30);
		check_service.setBounds(200, 130, 400, 30);
		p5.add(p_back);
		p_back.setBounds(0, 0, 800, 600);
		check_list.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { // action performed on clicking button
				final JFrame frame = InnerOperations.waitFrame();
				String service_pref = field.getText();
				/*
				 * p_back.removeAll(); p_back.add(check_list); p_back.add(field);
				 */
				String list[] = service_pref.split(";");
				int i = 0;
				while (i < list.length) {
					try {
						new Thread(new Runnable() { // displaying a loading frame until the program fetched data from
													// the server
							@Override
							public void run() {
								try {
									prepareservices(p5, p_back, list, list.length);
									// HERE IS THE FUNCTION THAT TAKES A
									// LONG TIME
								} catch (Exception e1) {
									InnerOperations.ExceptionDisplay(e1);
								}
								frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE
							}

						}).start();// spawning a new thread
					} catch (Exception e2) {
						InnerOperations.ExceptionDisplay(e2);
					}

					i++;

				}
			}
		});

		check_service.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// p_back.removeAll();
				final JFrame frame = InnerOperations.waitFrame();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							preparedefservices(p5, p_back); // HERE IS THE FUNCTION THAT TAKES A LONG TIME
						} catch (Exception e) {
							InnerOperations.ExceptionDisplay(e);
						}
						frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
					}
				}).start();

			}
		});
		InnerOperations.removeexcept(5, content);
	}

	public static int preparedefservices(JPanel p5, JPanel p_back) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("checkservicehealth.properties"));// this
																										// properties
																										// file has a
																										// predefined
																										// service list
			int size = 0;
			while (reader.readLine() != null) {
				String line = reader.readLine();
				System.out.println(line);
				size++;
			}
			System.out.println(size);
			reader.close();

			preparedefservicelist(p5, p_back);
		} catch (IOException e1) {
			InnerOperations.ExceptionDisplay(e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}

	public static int preparedefservicelist(JPanel p5, JPanel p_back) {
		try {
			Properties prop = new Properties();

			InputStream input = null;

			input = new FileInputStream("checkservicehealth.properties");

			prop.load(input);
			int lines = 0;
			// String result = "";
			List<String> serVice = new ArrayList<String>();
			List<String> results = new ArrayList<String>();
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				lines++;
				String key = (String) e.nextElement();
				serVice.add(key);
				String value = prop.getProperty(key);
				System.out.println("Key : " + key + ", Value : " + value);
				results.add(value);
			}
			JPanel pserviceTable = new JPanel();
			String data[][] = new String[lines][2];
			int count = 0;
			System.out.println(serVice);
			for (count = 0; count < lines; count++) {
				data[count][0] = serVice.get(count);
				try {
					data[count][1] = InnerOperations.advancedcheck(serVice.get(count));// return status
				} catch (Exception e2) {
					InnerOperations.ExceptionDisplay(e2);
				}
			}
			input.close();
			String column[] = { "SERVICE", "STATUS" };
			JTable jt = new JTable(data, column);
			jt.setBounds(0, 60, 150, lines * 20);// dynamic size remodelling
			TableColumnModel columnModel = jt.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(250);
			columnModel.getColumn(1).setPreferredWidth(550);
			JScrollPane sp1 = new JScrollPane(jt);
			pserviceTable.add(sp1);
			sp1.setBounds(0, 0, 800, lines * 40);// dynamic size remodelling
			p5.add(pserviceTable);
			pserviceTable.setBounds(0, 190, 1200, 500);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}

	public static void prepareservices(JPanel p5, JPanel p_back, String services[], int size) {

		JPanel pservicetable = new JPanel();
		String data[][] = new String[size][2];

		int count = 0;
		// dynamically filling the table with data
		for (count = 0; count < size; count++) {
			data[count][0] = services[count];

			try {
				data[count][1] = InnerOperations.advancedcheck(services[count]);
			} catch (Exception e2) {
				InnerOperations.ExceptionDisplay(e2);
			}
		}
		String column[] = { "SERVICE", "STATUS" };
		JTable jt = new JTable(data, column);
		jt.setBounds(0, 60, 150, size * 20);
		TableColumnModel columnModel = jt.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(250);
		columnModel.getColumn(1).setPreferredWidth(550);
		JScrollPane sp1 = new JScrollPane(jt);
		pservicetable.add(sp1);
		sp1.setBounds(0, 0, 800, size * 40);// dynamic size remodelling
		p5.add(pservicetable);
		pservicetable.setBounds(0, 190, 1200, 500);

	}

	public static void prepare_url(JPanel p6, JPanel content, int count) {

		if (count != 1) { // refreshing
			p6.removeAll();
			p6.revalidate();
			System.out.println("Count is greater than 1");
		}

		JPanel p_back = new JPanel();
		JTextField field = new JTextField(" Enter url(s) with delimiter ';'");
		JButton check_list = new JButton(" CHECK URL LIST STATUS");
		JButton check_api = new JButton(" CHECK  PREDEFINED API LIST");
		p_back.add(check_list);
		p_back.add(field);
		p_back.add(l_check);
		p_back.add(check_api);
		check_api.setBounds(200, 130, 400, 30);// setting boundaries which are very important when you're not using any
												// layouts
		l_check.setBounds(50, 300, 600, 20);
		field.setBounds(200, 50, 400, 30);
		check_list.setBounds(200, 90, 400, 30);
		p6.add(p_back);
		p_back.setBounds(0, 0, 800, 500);
		// button 1
		check_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JFrame frame = InnerOperations.waitFrame();
				String url_pref = field.getText();

				String list[] = url_pref.split(";");// taking multiple service names separated by a delimiter and
													// putting them in an array
				int i = 0;
				while (i < list.length) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							try {

								prepareurl(p6, p_back, list, list.length);// Collects data from server
							} catch (Exception e) {
								InnerOperations.ExceptionDisplay(e);
							}
							frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
						}
					}).start();// new thread spawned

					i++;

				}
			}
		});
		// button2
		check_api.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				final JFrame frame = InnerOperations.waitFrame();// reassures the user that the application is not
																	// hanging, but just waiting
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							prepare_api(p6, content, p_back);// HERE IS THE FUNCTION THAT TAKES A LONG TIME
						} catch (Exception e) {
							InnerOperations.ExceptionDisplay(e);
						}
						frame.dispose(); // AFTER THE LONG FUNCTION FINISHES, DISPOSE JFRAME
					}
				}).start();

				// thread2.stop();

			}
		});
		InnerOperations.removeexcept(6, content);

	}

	public static void prepareurl(JPanel p6, JPanel p_back, String[] url, int size) {
		JPanel purltable = new JPanel();
		String data[][] = new String[size][2];
		int count = 0;
		for (count = 0; count < size; count++) {
			data[count][0] = url[count];
			try {
				data[count][1] = InnerOperations.url_check(url[count], false);// connects to server or cloud and gets
																				// data
			} catch (Exception e2) {
				InnerOperations.ExceptionDisplay(e2);
			}
		}
		String column[] = { "URL", "STATUS" };
		JTable jt = new JTable(data, column);
		jt.setBounds(0, 60, 150, size * 20);// dynamic size remodelling
		TableColumnModel columnModel = jt.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(600);
		columnModel.getColumn(1).setPreferredWidth(200);
		JScrollPane sp1 = new JScrollPane(jt);
		purltable.add(sp1);
		sp1.setBounds(0, 0, 800, size * 40);
		p6.add(purltable);
		purltable.setBounds(0, 190, 1200, 500);
	}

	public static void prepare_api(JPanel p6, JPanel content, JPanel p_back) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("checkheader.properties"));
			int lines = 0;
			while (reader.readLine() != null)
				lines++;
			System.out.println(lines);
			reader.close();
			prepareapi(p6, p_back, lines);
		} catch (IOException e1) {
			InnerOperations.ExceptionDisplay(e1);
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	static void prepareapi(JPanel p7, JPanel p_back, int lines) {
		try {
			Properties prop = new Properties();
			InputStream input = null;

			input = new FileInputStream("checkheader.properties");

			prop.load(input);
			// String result = "";
			List<String> apiurl = new ArrayList<String>();
			List<String> results = new ArrayList<String>();
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				apiurl.add(key);
				String value = prop.getProperty(key);// reading api list from the properties file
				System.out.println("Key : " + key + ", Value : " + value);
				results.add(value);
			}
			JPanel papitable = new JPanel();
			String data[][] = new String[lines][2];
			int count = 0;
			for (count = 0; count < lines; count++) {
				data[count][0] = apiurl.get(count);
				try {
					data[count][1] = InnerOperations.url_check(results.get(count), true);
				} catch (Exception e2) {
					InnerOperations.ExceptionDisplay(e2);
				}
			}
			String column[] = { "API", "STATUS" };
			JTable jt = new JTable(data, column);
			jt.setBounds(0, 60, 150, lines * 20);
			TableColumnModel columnModel = jt.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(600);
			columnModel.getColumn(1).setPreferredWidth(200);
			JScrollPane sp1 = new JScrollPane(jt);
			papitable.add(sp1);
			sp1.setBounds(0, 0, 800, lines * 40);
			p7.add(papitable);
			papitable.setBounds(0, 190, 1200, 500);
			input.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
