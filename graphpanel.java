package tcp_connections;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//import com.jcraft.jsch.JSchException;

public class graphpanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int globalcounter;
	int memtot;
	static int memused;
	Timer timer;
	// private int width = 800;
	// private int heigth = 600;
	private int padding = 25;
	private int labelPadding = 30;
	private Color lineColor = Color.green;
	// private Color axesColor = Color.black;
	private Color gridColor = Color.GRAY;
	private Color rectColor = Color.black;
	// private Color pointColor = Color.green;
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f); // helps in the ui
	private int pointWidth = 1;
	private int numberYDivisions = 3;
	protected static List<Integer> mem = new ArrayList<Integer>();// contains the real time memory usage details
	static String mem_tot, mem_used, mem_free, mem_shared, mem_buff, mem_avail;
	static List<String> dates = new ArrayList<String>();// contains the time at which you read the memory usage stats,
														// helps in x-axis
	int count;

	public graphpanel(List<Integer> mem) {
		graphpanel.mem = mem;
		count = 0;

		// TODO Auto-generated constructor stub

	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		count++;

		try {

			InputStream in = InnerOperations.connectServer(Mymethods.host, Mymethods.user, Mymethods.password,
					"free -m");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;

			line = reader.readLine();
			for (int i = 0; line != null; i++) {
				if (i == 1) {
					Date date = new Date();
					Format formatter = new SimpleDateFormat("HH:mm:ss");// time at which ram usage was reading
					String s = formatter.format(date);
					dates.add(s);
					String words[] = line.split("        ");
					mem_tot = words[1];
					mem_used = words[2];
					mem_free = words[3];
					mem_shared = words[4];
					mem_buff = words[5];
					mem_avail = words[6];
				}

				System.out.println(line);

				line = reader.readLine();

			}
			InnerOperations.closeserverconnection();
			System.out.println(mem_used);
			memtot = Integer.parseInt(mem_tot.trim());
			memused = Integer.parseInt(mem_used);
			System.out.println(memused);
			mem.add(memused);// adding dynamically read ram usage into the list which helps in plotting the
								// graph
		} catch (IOException e) {
			InnerOperations.ExceptionDisplay(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (mem.size() - 1);// setting the scale
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (memtot - 1500);

		List<Point> graphPoints = new ArrayList<>(); // the points which we'll be plotting
		for (int i = 0; i < mem.size(); i++) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((memtot - mem.get(i)) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}

		// draw black background
		g2.setColor(rectColor);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (mem.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(rectColor);
				String yLabel = (int) (((int) ((1500 + (memtot - 1500) * ((i * 1.0) / numberYDivisions)) * 100))
						/ 100.0) + " MB";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i < mem.size(); i++) {
			if (mem.size() > 1) {
				int x0 = i * (getWidth() - labelPadding) / (mem.size() - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((mem.size() / 20.0)) + 1)) == 0) { // this basically helps in dynamically updating the
																	// grid lines to fit all the ram usage details
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(rectColor);
					String xLabel = dates.get(i);
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}
		// create x and y axes
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
				getHeight() - padding - labelPadding);
		Stroke oldStroke = g2.getStroke();
		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);

		for (int i = 0; i < graphPoints.size() - 1; i++) {

			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;

			g2.drawLine(x1, y1, x2, y2);
		}

		g2.setStroke(oldStroke);
		g2.setColor(lineColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
		System.out.println(mem + " inside graphpanel");

		System.out.println(count);
		repaint();

		if (count > 15) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				InnerOperations.ExceptionDisplay(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	/*
	 * private ActionListener addComponentListener(ActionListener actionListener) {
	 * // TODO Auto-generated method stub return null; }
	 */

	private static void createAndShowGui(JFrame frame) {

		graphpanel mainPanel = new graphpanel(mem);
		mainPanel.setPreferredSize(new Dimension(1300, 730));

		frame.add(mainPanel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	@SuppressWarnings("static-access")
	public static void launchGraph(int counter) {
		JFrame frame = new JFrame(" Memory Graph");
		frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
		globalcounter = counter;
		if (counter > 1) {
			mem.clear();
			dates.clear();

		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui(frame);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}