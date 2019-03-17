package tcp_connections;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

//import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.WindowConstants;
//import com.jcraft.jsch.ChannelExec;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;

public class InnerOperations {
	static String data;
	static JFrame frame;
	static com.jcraft.jsch.Channel channel;
	static Session session;

	static String writeIntoFile(String s) {
		try {
			FileWriter fo = new FileWriter(s);
			File f = new File(s);
			String path = f.getAbsolutePath();
			// List<String> disk_lines_write = new ArrayList<String>();
			InputStream in = connectServer(Mymethods.host, Mymethods.user, Mymethods.password, "df -m");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			// int i = 0;
			while (line != null) {

				String str = line;
				fo.write(str + System.getProperty("line.separator"));
				line = reader.readLine();
			}

			closeserverconnection();
			fo.close();// important}
			return path;

		} catch (Exception e) {
			InnerOperations.ExceptionDisplay(e);
		}
		return "error occured";

	}

	public static String advancedcheck(String service) {
		try {
			String name = service;

			InputStream in;

			in = connectServer(Mymethods.host, Mymethods.user, Mymethods.password, "service " + name + " status");

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();// first line of output
			int i = 0;
			if (line == null) {
				closeserverconnection();
				return "NOT FOUND";

			}
			while (line != null) {
				i++;
				line = reader.readLine();
				if (i == 2) {
					String words[] = line.split("Active:");

					data = words[1];
					break;
				}
				

			}
			closeserverconnection();
			return data;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			ExceptionDisplay(e);
		}
		return null;

	}

	public static String check_server(String service) {
		try {
			String term = service;
			InputStream in;

			in = connectServer(Mymethods.host, Mymethods.user, Mymethods.password, "service " + term + " status");

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			int i = 0;
			while (line != null) {
				i++;
				line = reader.readLine();
				if (i == 2) {
					String words[] = line.split("Active:");

					term = words[1];
					break;
				}

			}
			closeserverconnection();
			return term;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		}
		return null;

	}

	public static String url_check(String url, boolean header) {
		try {
			String result = "";
			System.out.println(url);
			URL url_obj;

			url_obj = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();// google this
			if (header) {
				conn.setRequestProperty("deviceid", "device1234");// to access some apis, you need a header
			}
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6000);// setting a timeout for trying to connect
			try {
				conn.connect();
			} catch (Exception e) {
				ExceptionDisplay(e);
				return "Could not connect";
			}
			int code = conn.getResponseCode();
			if (code == 200) {
				result = "Connected";
				System.out.println(" Code is 200");
				System.out.println(conn.getResponseMessage());
			} else {
				result = "Not Connected";
				System.out.println("I'm in here,I'm not connected");
			}

			return result;
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public static void removeexcept(int x, JPanel content) { // this is where i'll be clearing the orange panel for the
																// respective panels-p1,p2,p3,p4,p5,p6 which have
																// corresponding numbers -1,2,3,4,5,6 respectively
		switch (x) {
		case 1:
			// removing all internal panels in p2
			MenuMod.p2.removeAll();
			MenuMod.p3.removeAll();
			MenuMod.p4.removeAll();
			MenuMod.p5.removeAll();
			MenuMod.p6.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p5);
			content.remove(MenuMod.p2);
			content.remove(MenuMod.p4);
			content.remove(MenuMod.p3);
			content.remove(MenuMod.p6);
			content.remove(MenuMod.p7);

			break;
		case 2:
			MenuMod.p1.removeAll();
			MenuMod.p3.removeAll();
			MenuMod.p4.removeAll();
			MenuMod.p5.removeAll();
			MenuMod.p6.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p5);
			content.remove(MenuMod.p1);
			content.remove(MenuMod.p4);
			content.remove(MenuMod.p3);
			content.remove(MenuMod.p6);
			content.remove(MenuMod.p7);

			break;

		case 3:
			MenuMod.p2.removeAll();
			MenuMod.p1.removeAll();
			MenuMod.p4.removeAll();
			MenuMod.p5.removeAll();
			MenuMod.p6.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p5);
			content.remove(MenuMod.p2);
			content.remove(MenuMod.p4);
			content.remove(MenuMod.p1);
			content.remove(MenuMod.p6);
			content.remove(MenuMod.p7);
			break;

		case 4:
			MenuMod.p2.removeAll();
			MenuMod.p3.removeAll();
			MenuMod.p1.removeAll();
			MenuMod.p5.removeAll();
			MenuMod.p6.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p5);
			content.remove(MenuMod.p2);
			content.remove(MenuMod.p1);
			content.remove(MenuMod.p3);
			content.remove(MenuMod.p6);
			content.remove(MenuMod.p7);
			break;

		case 5:
			MenuMod.p2.removeAll();
			MenuMod.p3.removeAll();
			MenuMod.p4.removeAll();
			MenuMod.p1.removeAll();
			MenuMod.p6.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p1);
			content.remove(MenuMod.p2);
			content.remove(MenuMod.p4);
			content.remove(MenuMod.p3);
			content.remove(MenuMod.p6);
			content.remove(MenuMod.p7);
			break;
		case 6:
			MenuMod.p2.removeAll();
			MenuMod.p3.removeAll();
			MenuMod.p4.removeAll();
			MenuMod.p1.removeAll();
			MenuMod.p5.removeAll();
			MenuMod.p7.removeAll();
			content.remove(MenuMod.p1);
			content.remove(MenuMod.p2);
			content.remove(MenuMod.p4);
			content.remove(MenuMod.p3);
			content.remove(MenuMod.p5);
			content.remove(MenuMod.p7);
			break;

		default:
			System.out.println(" Error occured ");
			break;
		}
	}

	public static InputStream connectServer(String host, String user, String password, String command)

	{ // This is that method which we used so many times to connect to the server
		try {
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			// make sure you add then jsch jar to your project
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("exec");
			System.out.println("Connected to Server  :" + channel.getId());
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			return in;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		}
		return null;

	}

	public static InputStream jdbcexec(String command) throws JSchException, IOException {
		channel = session.openChannel("exec");
		System.out.println("Connected to Server  :" + channel.getId());
		((ChannelExec) channel).setCommand(command);
		channel.setInputStream(null);
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in = channel.getInputStream();
		channel.connect();
		return in;
	}

	public static void closeserverconnection() {
		int id = channel.getId();
		System.out.println(channel.getId());
		if (channel.isConnected())
			channel.disconnect();
		// int sesid = session.getPort();
		if (session.isConnected())
			session.disconnect();

		System.out.println("Closed  :" + id);
	}

	public static JFrame waitFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JLabel label = new JLabel("Please wait...Fetching data");
		label.setFont(new Font("Serif", Font.PLAIN, 15));
		frame.getContentPane().add(label, BorderLayout.NORTH);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		return frame;
	}

	public static void getCredentials() { // to connect to the server , you need credentials which you'll read from a
											// properties file
		try {
			Properties prop = new Properties();
			InputStream input = ClassLoader.getSystemResourceAsStream("credentials.properties");
			;

			input = new FileInputStream("credentials.properties");

			prop.load(input);

			Mymethods.host = prop.getProperty("host");
			Mymethods.user = prop.getProperty("user");
			Mymethods.password = prop.getProperty("password");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		}

	}

	public static void getjdbcCredentials() // gets credentials to connect to mysql database :)
	{
		try {
			Properties prop = new Properties();
			InputStream input = null;

			input = new FileInputStream("jdbccredentials.properties");

			prop.load(input);
			Mymethods.jdbc_url = prop.getProperty("url");
			Mymethods.jdbc_driver = prop.getProperty("driver");
			Mymethods.dbuserName = prop.getProperty("user");
			Mymethods.dbpassword = prop.getProperty("password");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InnerOperations.ExceptionDisplay(e);
		}
	}

	public static void ExceptionDisplay(Exception e) {
		frame.dispose();

		// StringBuilder sb = new StringBuilder(e.toString());
		// StackTraceElement[] ste = e.getStackTrace();
		// String trace = sb.toString();
		JFrame dialog = new JFrame();
		// dialog.setDefaultCloseOperation(dialog.DISPOSE_ON_CLOSE);
		String mess = (String) e.getMessage();
		if (mess.equalsIgnoreCase("null"))
			mess = "File not Found!";
		e.printStackTrace();
		JOptionPane.showMessageDialog(dialog, "  " + mess + "Try again !", "Error", JOptionPane.ERROR_MESSAGE);
	}

}
