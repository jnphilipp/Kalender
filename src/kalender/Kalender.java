/**
 *     Kalender ist ein Kalender-Object für GUIs.
 *     Copyright (C) 2011 J. Nathanael philipp
 * 
 *	   This file is part of Kalender.
 * 
 *     Kalender is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     Kalender is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kalender;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

/**
 * Dies ist ein Kalender, den man auf jedem, wie jedes andere Objekt von JComponent ein binden kann. Die Sprache des Kalenders wird durch die Enumeration <code>Sprachen</code> angegeben.<br>
 * Jeden Tag, der Angelickt wird, wird einer Liste hinzugefuegt, bei erneuter Auswahl wird er wieder entfernt. Man kann diese Liste mit den entsprechenden Methoden bekommen.
 * @author J. Nathanael Philipp
 * @version 1.0
 */
public class Kalender extends JPanel implements MouseListener, KeyListener {
	/**
	 * Komponenten des Kalenders
	 */
	private JComponent[] comp = new JComponent[24];
	/**
	 * Tage
	 */
	private JLabel[] days = new JLabel[42];
	/**
	 * GregorianCalendar auf dem gearbeitet wird.
	 */
	private GregorianCalendar calendar;
	/**
	 * Speichert die ausgewaehlten Tage.
	 */
	private ArrayList<String> datum = new ArrayList<String>();
	/**
	 * Sprache des Kalenders.
	 */
	private Sprachen sprache;

	/**
	 * Gibt die ausgew&auml;hlten Tage als Array zur&uuml;ck.
	 * @return ausgew&auml;hlte Tage
	 */
	public String[] getTage() {
		String[] a = new String[this.datum.size()];
		return this.datum.toArray(a);
	}

	/**
	 * Gibt die ausgew&auml;hlten Tage als <code>ArrayList<String></code> zur&uuml;ck.
	 * @return ausgew&auml;hte Tage
	 */
	public ArrayList<String> getATage() {
		ArrayList<String> t = new ArrayList<String>();
		for ( String s : this.datum )
			t.add(s);
		return t;
	}

	/**
	 * F&uuml;gt die &uuml;bergebenen Tage der Liste mit den ausgew&auml;hlten Tagen hinzu. Dabei wird gepr&uuml;ft, ob das Datum existiert und in der Form <code>dd.MM.yyyy</code> ist.
	 * @param tage Tage, die hinzugef&uuml;gt werden sollen.
	 * @throws ParseException
	 */
	public void setTage(String[] tage) throws ParseException {
		for ( int i = 0; i < tage.length; i++ ) {
			SimpleDateFormat df = new SimpleDateFormat(tage[i]);
			df.setLenient(false);

			try {
				Date d = df.parse(tage[i]);
				this.datum.add(tage[i]);
			}
			catch ( ParseException e ) {
				throw e;
			}
		}
	}

	/**
	 * F&uuml;gt die &uuml;bergebenen Tage der Liste mit den ausgew&auml;hlten Tagen hinzu. Dabei wird gepr&uuml;ft, ob das Datum existiert und in der Form <code>dd.MM.yyyy</code> ist.
	 * @param tage Tage, die hinzugef&uuml;gt werden sollen.
	 * @throws ParseException
	 */
	public void setTage(ArrayList<String> tage) throws ParseException {
		for ( int i = 0; i < tage.size(); i++ ) {
			SimpleDateFormat df = new SimpleDateFormat(tage.get(i));
			df.setLenient(false);

			try {
				Date d = df.parse(tage.get(i));
				this.datum.add(tage.get(i));
			}
			catch ( ParseException e ) {
				throw e;
			}
		}
	}

	/**
	 * Erstellt ein neuen Kalender mit der angegebenen Sprache.
	 * @param sprache Gibt die Sprache des Kalenders an.
	 */
	public Kalender(Sprachen sprache) {
		this.sprache = sprache;
		this.calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1);

		this.erstelleKalendar(this);
	}

	/**
	 * Erstellt ein neuen Kalender mit der angebenen Sprache. Ist <code>tabs true</code>, so werden zwei Tabs angelegt, wobei auf dem einem der Kalender ist, auf dem anderen ist eine Lsite mit den ausgew&auml;hlten Tage zu sehen.
	 * @param sprache Gibt die Sprache des Kalenders an.
	 * @param tabs Gibt an, ob die Tabs erzeugt werden sollen oder nicht.
	 */
	public Kalender(Sprachen sprache, boolean tabs) {
		this.sprache = sprache;
		this.calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1);

		if ( !tabs )
			this.erstelleKalendar(this);
		else {
			this.setLayout(new GridLayout(1,1));
			this.comp[18] = new JTabbedPane();
			this.add(this.comp[18]);
			this.comp[19] = new JPanel();
			this.erstelleKalendar(((JPanel)this.comp[19]));
			((JTabbedPane)this.comp[18]).addTab(this.sprache.getTabs()[0], this.comp[19]);
			this.comp[20] = new JPanel();
			((JPanel)this.comp[20]).setLayout(new BorderLayout(5,5));
			String[] a = {""};
			this.comp[21] = new JList(a);
			((JList)this.comp[21]).setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
			this.comp[22] = new JScrollPane();
			((JScrollPane)this.comp[22]).setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			((JScrollPane)this.comp[22]).setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			((JScrollPane)this.comp[22]).setViewportView(this.comp[21]);
			((JPanel)this.comp[20]).add(this.comp[22]);
			this.comp[23] = new JLabel("-");
			this.comp[23].setOpaque(true);
			this.comp[23].setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			((JLabel)this.comp[23]).setHorizontalAlignment(SwingConstants.CENTER);
			this.comp[23].addMouseListener(this);
			((JPanel)this.comp[20]).add(this.comp[23], BorderLayout.LINE_END);
			((JTabbedPane)this.comp[18]).addTab(this.sprache.getTabs()[1], this.comp[20]);
		}
	}

	/**
	 * Legt die einzelnen Tage usw. an.
	 * @param panel Panel, auf dem der Kalender angelegt wird.
	 */
	private void erstelleKalendar(JPanel panel) {
		this.setSize(320, 300);
		panel.setLayout(new BorderLayout(5,5));
		panel.setOpaque(true);
		panel.setBackground(Color.DARK_GRAY);

		//Grundpanel fuer den Kalender
		this.comp[0] = new JPanel();
		((JPanel)this.comp[0]).setLayout(new BorderLayout(5,5));
		panel.add(this.comp[0], BorderLayout.NORTH);
		this.comp[1] = new JPanel();
		this.comp[1].setOpaque(true);
		this.comp[1].setBackground(Color.DARK_GRAY);
		((JPanel)this.comp[1]).setLayout(new GridLayout(7,7));
		panel.add(this.comp[1]);

		//Wochentage
		this.comp[2] = new JLabel("");
		this.comp[2].setForeground(Color.WHITE);
		((JLabel)this.comp[2]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[2]);
		this.comp[3] = new JLabel("");
		this.comp[3].setForeground(Color.WHITE);
		((JLabel)this.comp[3]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[3]);
		this.comp[4] = new JLabel("");
		this.comp[4].setForeground(Color.WHITE);
		((JLabel)this.comp[4]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[4]);
		this.comp[5] = new JLabel("");
		this.comp[5].setForeground(Color.WHITE);
		((JLabel)this.comp[5]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[5]);
		this.comp[6] = new JLabel("");
		this.comp[6].setForeground(Color.WHITE);
		((JLabel)this.comp[6]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[6]);
		this.comp[7] = new JLabel("");
		this.comp[7].setForeground(Color.WHITE);
		((JLabel)this.comp[7]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[7]);
		this.comp[8] = new JLabel("");
		this.comp[8].setForeground(Color.WHITE);
		((JLabel)this.comp[8]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[1]).add(this.comp[8]);

		String[] wochentage = this.sprache.getWochentage();
		for ( int i = 0; i < 7; i++ ) {
			int j = 0;
			if ( this.sprache.startWocheMitMontag() )
				j = i;
			else
				j = (i + 6) % 7;

			((JLabel)this.comp[i + 2]).setText(wochentage[j]);
		}

		//Tage
		for ( int i = 0; i < this.days.length; i++ ) {
			this.days[i] = new JLabel("0");
			((JLabel)this.days[i]).setHorizontalAlignment(SwingConstants.CENTER);
			this.days[i].setOpaque(true);
			this.days[i].addMouseListener(this);
			((JPanel)this.comp[1]).add(this.days[i]);
		}
		this.tage();

		this.comp[9] = new JPanel();
		((JPanel)this.comp[0]).add(this.comp[9], BorderLayout.LINE_START);
		this.comp[10] = new JPanel();
		((JPanel)this.comp[0]).add(this.comp[10], BorderLayout.LINE_END);

		//Monat
		this.comp[11] = new JLabel("<");
		this.comp[11].setOpaque(true);
		((JLabel)this.comp[11]).setHorizontalAlignment(SwingConstants.CENTER);
		this.comp[11].addMouseListener(this);
		((JPanel)this.comp[9]).add(this.comp[11]);
		this.comp[12] = new JTextField(this.monat(), 7);
		((JTextField)this.comp[12]).setHorizontalAlignment(SwingConstants.CENTER);
		((JTextField)this.comp[12]).addKeyListener(this);
		((JPanel)this.comp[9]).add(this.comp[12]);
		this.comp[13] = new JLabel(">");
		this.comp[13].setOpaque(true);
		((JLabel)this.comp[13]).setHorizontalAlignment(SwingConstants.CENTER);
		this.comp[13].addMouseListener(this);
		((JPanel)this.comp[9]).add(this.comp[13]);

		//Jahre
		this.comp[14] = new JLabel("<");
		this.comp[14].setOpaque(true);
		((JLabel)this.comp[14]).setHorizontalAlignment(SwingConstants.CENTER);
		this.comp[14].addMouseListener(this);
		((JPanel)this.comp[10]).add(this.comp[14]);
		this.comp[15] = new JTextField(String.valueOf(this.calendar.get(Calendar.YEAR)), 3);
		((JTextField)this.comp[15]).addKeyListener(this);
		((JTextField)this.comp[15]).setHorizontalAlignment(SwingConstants.CENTER);
		((JPanel)this.comp[10]).add(this.comp[15]);
		this.comp[16] = new JLabel(">");
		this.comp[16].setOpaque(true);
		((JLabel)this.comp[16]).setHorizontalAlignment(SwingConstants.CENTER);
		this.comp[16].addMouseListener(this);
		((JPanel)this.comp[10]).add(this.comp[16]);
		//Statuslabel
		this.comp[17] = new JLabel(" ");
		this.comp[17].setOpaque(true);
		panel.add(this.comp[17], BorderLayout.SOUTH);
	}

	/**
	 * MouseClicked Event
	 * @param e MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if ( e.getSource().equals(this.comp[11]) ) {
			this.calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH)-1);
			((JTextField)this.comp[12]).setText(this.monat());
			((JTextField)comp[15]).setText(String.valueOf(calendar.get(Calendar.YEAR)));
			this.tage();
		}
		else if ( e.getSource().equals(this.comp[13]) ) {
			this.calendar.set(Calendar.MONTH, this.calendar.get(Calendar.MONTH)+1);
			((JTextField)this.comp[12]).setText(this.monat());
			((JTextField)comp[15]).setText(String.valueOf(calendar.get(Calendar.YEAR)));
			this.tage();
		}
		else if ( e.getSource().equals(comp[14]) ) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)-1);
			((JTextField)comp[15]).setText(String.valueOf(calendar.get(Calendar.YEAR)));
			this.tage();
		}
		else if ( e.getSource().equals(comp[16]) ) {
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
                    ((JTextField)comp[15]).setText(String.valueOf(calendar.get(Calendar.YEAR)));
                    this.tage();
		}
		else if ( Arrays.asList(this.days).contains(e.getSource()) ) {
                    String s = "";
			if ( Integer.parseInt(((JLabel)e.getSource()).getText()) >= 23 && ((JLabel)e.getSource()).getBackground().equals(Color.GRAY) ) {
				int m = (this.calendar.get(Calendar.MONTH) == 0 ? 1 : this.calendar.get(Calendar.MONTH));
				s = ((JLabel)e.getSource()).getText() + "." + (m <= 9 ? "0" + m : m) + "." + this.calendar.get(Calendar.YEAR);
			}
			else if ( Integer.parseInt(((JLabel)e.getSource()).getText()) <= 14 && ((JLabel)e.getSource()).getBackground().equals(Color.GRAY) ) {
				int m = ((this.calendar.get(Calendar.MONTH) + 2) == 13 ? 1 : (this.calendar.get(Calendar.MONTH) + 2));
				s =  (((JLabel)e.getSource()).getText().length() == 1 ? "0" + ((JLabel)e.getSource()).getText() : ((JLabel)e.getSource()).getText()) + "." + (m <= 9 ? "0" + m : m) + "." + this.calendar.get(Calendar.YEAR);
			}
			else {
				int m = ((this.calendar.get(Calendar.MONTH) + 1) == 13 ? 1 : (this.calendar.get(Calendar.MONTH) + 1));
				s = (((JLabel)e.getSource()).getText().length() == 1 ? "0" + ((JLabel)e.getSource()).getText() : ((JLabel)e.getSource()).getText()) + "." + (m <= 9 ? "0" + m : m) + "." + this.calendar.get(Calendar.YEAR);
			}

			if ( this.datum.contains(s) ) {
				this.datum.remove(s);
				((JLabel)this.comp[17]).setText(this.sprache.getFunktionen()[1] + ": " + s);
			}
			else {
				this.datum.add(s);
				((JLabel)this.comp[17]).setText(this.sprache.getFunktionen()[0] + ": " + s);
			}

			if ( this.comp[21] != null )
				((JList)comp[21]).setListData(datum.toArray());
		}
		else if ( e.getSource().equals(comp[23]) ) {
			datum.remove(((JList)comp[21]).getSelectedIndex());
			((JList)comp[21]).setListData(datum.toArray());
		}
	}

	/**
	 * MouseEntered Event
	 * @param e MouseEvent
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Color c = ((JLabel)e.getSource()).getBackground();
		((JLabel)e.getSource()).setBackground(((JLabel)e.getSource()).getForeground());
		((JLabel)e.getSource()).setForeground(c);
	}

	/**
	 * MouseExited Event
	 * @param e MouseEvent
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		Color c = ((JLabel)e.getSource()).getBackground();
		((JLabel)e.getSource()).setBackground(((JLabel)e.getSource()).getForeground());
		((JLabel)e.getSource()).setForeground(c);
	}

	/**
	 * MousePressed Event
	 * @param e MouseEvent
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	/**
	 * MouseReleased Event
	 * @param e MouseEvent
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}

	/**
	* KeyTyped
	* @param e KeyEvent
	*/
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	* KeyPressed
	* @param e KeyEvent
	*/
	@Override
	public void keyPressed(KeyEvent e) {}

	/**
	* KeyReleased
	* @param e KeyEvent
	*/
	@Override
	public void keyReleased(KeyEvent e) {
		if ( e.getSource().equals(this.comp[15]) ) {
			try {
				int jahr = Integer.parseInt(((JTextField)this.comp[15]).getText());
				calendar.set(Calendar.YEAR, jahr);
				this.tage();
			}
			catch ( NumberFormatException x ) {}
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
		}
		else if ( e.getSource().equals(this.comp[12]) ) {
			if ( this.sprache.contains(((JTextField)this.comp[12]).getText()) ) {
				this.monat(((JTextField)this.comp[12]).getText());
				this.tage();
			}
		}
	}

	/**
	 * MinimumSize
	 * @return minimum Size
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(320, 300);
	}

	/**
	 * Gibt den aktuellen Monat in der angegebenen Sprache zur&uuml;ck.
	 * Wenn das nicht m&ooml;glich ist wird ein leerer String zur&uuml;ck gegebenen.
	 * @return aktueller Monat
	 */
	private String monat() {
		switch ( this.calendar.get(Calendar.MONTH)) {
			case Calendar.JANUARY: {
				return this.sprache.getMonate()[0];
			}
			case Calendar.FEBRUARY: {
				return this.sprache.getMonate()[1];
			}
			case Calendar.MARCH: {
				return this.sprache.getMonate()[2];
			}
			case Calendar.APRIL: {
				return this.sprache.getMonate()[3];
			}
			case Calendar.MAY: {
				return this.sprache.getMonate()[4];
			}
			case Calendar.JUNE: {
				return this.sprache.getMonate()[5];
			}
			case Calendar.JULY: {
				return this.sprache.getMonate()[6];
			}
			case Calendar.AUGUST: {
				return this.sprache.getMonate()[7];
			}
			case Calendar.SEPTEMBER: {
				return this.sprache.getMonate()[8];
			}
			case Calendar.OCTOBER: {
				return this.sprache.getMonate()[9];
			}
			case Calendar.NOVEMBER: {
				return this.sprache.getMonate()[10];
			}
			case Calendar.DECEMBER: {
				return this.sprache.getMonate()[11];
			}
		}
		return "";
	}

	/**
	 * Setz den Monat auf den angegebenen Monat.
	 * @param monat neuer Monat
	 */
	private void monat(String monat) {
		if ( this.sprache.getMonate()[0].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.JANUARY);
		else if ( this.sprache.getMonate()[1].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
		else if ( this.sprache.getMonate()[2].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.MARCH);
		else if ( this.sprache.getMonate()[3].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.APRIL);
		else if ( this.sprache.getMonate()[4].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.MAY);
		else if ( this.sprache.getMonate()[5].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.JUNE);
		else if ( this.sprache.getMonate()[6].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.JULY);
		else if ( this.sprache.getMonate()[7].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.AUGUST);
		else if ( this.sprache.getMonate()[8].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		else if ( this.sprache.getMonate()[9].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.OCTOBER);
		else if ( this.sprache.getMonate()[10].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
		else if ( this.sprache.getMonate()[11].equals(monat) )
			this.calendar.set(Calendar.MONTH, Calendar.DECEMBER);
	}

	/**
	 * Setzt entsprechden dem ausgew&auml;hlten Monat die Tage, sodass die Wochentage passen. Sollte der Anfang und/oder Ende des Monats nicht auf den Anfang bzw. Ende der Woche fallen, so werden die Tage noch mit den entsprechenden Tagen des vorherigen bzw. n&auml;chsten Monats aufgef&uuml;llt. Die Methode <code>Sprache.startWocheMitMontag()</code> gibt dabei an ob die Woche mit Montag oder mit Sonntag anfangen soll.
	 */
	private void tage() {
		switch ( this.calendar.get(Calendar.MONTH) ) {
			case Calendar.JANUARY:
			case Calendar.MARCH:
			case Calendar.MAY:
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.OCTOBER:
			case Calendar.DECEMBER: {
				if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 31; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i + 1));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 31; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 30));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						((JLabel)this.days[0]).setText("31");
						this.days[0].setForeground(Color.GRAY);
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 )
							((JLabel)this.days[0]).setText("29");
						else
							((JLabel)this.days[0]).setText("28");

						this.days[0].setForeground(Color.GRAY);
					}
					else {
						((JLabel)this.days[0]).setText("30");
						this.days[0].setForeground(Color.GRAY);
					}
					for ( int i = 1; i < 32; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 32; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 31));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						((JLabel)this.days[0]).setText("30");
						this.days[0].setForeground(Color.GRAY);
						((JLabel)this.days[1]).setText("31");
						this.days[1].setForeground(Color.GRAY);
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
							((JLabel)this.days[0]).setText("28");
							((JLabel)this.days[1]).setText("29");
						}
						else {
							((JLabel)this.days[0]).setText("27");
							((JLabel)this.days[1]).setText("28");
						}

						this.days[0].setForeground(Color.GRAY);
						this.days[1].setForeground(Color.GRAY);
					}
					else {
						((JLabel)this.days[0]).setText("29");
						this.days[0].setForeground(Color.GRAY);
						((JLabel)this.days[1]).setText("30");
						this.days[1].setForeground(Color.GRAY);
					}
					for ( int i = 2; i < 33; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 1));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 33; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 32));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						for ( int i = 0; i < 3; i++ ) {
							((JLabel)this.days[i]).setText("" + (29 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
							for ( int i = 0; i < 3; i++ )
							((JLabel)this.days[i]).setText("" + (27 + i));
						}
						else {
							for ( int i = 0; i < 3; i++ )
							((JLabel)this.days[i]).setText("" + (26 + i));
						}

						for ( int i = 0; i < 3; i++ )
							this.days[i].setForeground(Color.GRAY);
					}
					else {
						for ( int i = 0; i < 3; i++ ) {
							((JLabel)this.days[i]).setText("" + (28 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					for ( int i = 3; i < 34; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 2));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 34; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 33));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						for ( int i = 0; i < 4; i++ ) {
							((JLabel)this.days[i]).setText("" + (28 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
							for ( int i = 0; i < 4; i++ )
								((JLabel)this.days[i]).setText("" + (26 + i));
						}
						else {
							for ( int i = 0; i < 4; i++ )
								((JLabel)this.days[i]).setText("" + (25 + i));
						}

						for ( int i = 0; i < 4; i++ )
							this.days[i].setForeground(Color.GRAY);
					}
					else {
						for ( int i = 0; i < 4; i++ ) {
							((JLabel)this.days[i]).setText("" + (27 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					for ( int i = 4; i < 35; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 3));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 35; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 34));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						for ( int i = 0; i < 5; i++ ) {
							((JLabel)this.days[i]).setText("" + (27 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
							for ( int i = 0; i < 5; i++ )
								((JLabel)this.days[i]).setText("" + (25 + i));
						}
						else {
							for ( int i = 0; i < 5; i++ )
								((JLabel)this.days[i]).setText("" + (24 + i));
						}

						for ( int i = 0; i < 5; i++ )
							this.days[i].setForeground(Color.GRAY);
					}
					else {
						for ( int i = 0; i < 5; i++ ) {
							((JLabel)this.days[i]).setText("" + (26 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					for ( int i = 5; i < 36; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 4));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 36; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 35));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.MONTH) == Calendar.AUGUST || this.calendar.get(Calendar.MONTH) == Calendar.JANUARY ) {
						for ( int i = 0; i < 6; i++ ) {
							((JLabel)this.days[i]).setText("" + (26 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else if ( this.calendar.get(Calendar.MONTH) == Calendar.MARCH ) {
						if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
							for ( int i = 0; i < 6; i++ )
								((JLabel)this.days[i]).setText("" + (24 + i));
						}
						else {
							for ( int i = 0; i < 6; i++ )
								((JLabel)this.days[i]).setText("" + (23 + i));
						}

						for ( int i = 0; i < 6; i++ )
							this.days[i].setForeground(Color.GRAY);
					}
					else {
						for ( int i = 0; i < 6; i++ ) {
							((JLabel)this.days[i]).setText("" + (25 + i));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					for ( int i = 6; i < 37; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 5));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 37; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 36));
						this.days[i].setForeground(Color.GRAY);
					}
				}
			} break;
			case Calendar.APRIL:
			case Calendar.JUNE:
			case Calendar.SEPTEMBER:
			case Calendar.NOVEMBER: {
				if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 30; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i + 1));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 30; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 29));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && !this.sprache.startWocheMitMontag()) ) {
					((JLabel)this.days[0]).setText("31");
					this.days[0].setForeground(Color.GRAY);
					for ( int i = 1; i < 31; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 31; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 30));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 2; i++ ) {
						((JLabel)this.days[i]).setText("" + (30 + i));
						this.days[i].setForeground(Color.GRAY);
					}
					for ( int i = 2; i < 32; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 1));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 32; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 31));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 3; i++ ) {
						((JLabel)this.days[i]).setText("" + (29 + i));
						this.days[i].setForeground(Color.GRAY);
					}
					for ( int i = 3; i < 33; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 2));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 33; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 32));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 4; i++ ) {
						((JLabel)this.days[i]).setText("" + (28 + i));
						this.days[i].setForeground(Color.GRAY);
					}
					for ( int i = 4; i < 34; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 3));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 34; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 33));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 5; i++ ) {
						((JLabel)this.days[i]).setText("" + (27 + i));
						this.days[i].setForeground(Color.GRAY);
					}
					for ( int i = 5; i < 35; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 4));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 35; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 34));
						this.days[i].setForeground(Color.GRAY);
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 6; i++ ) {
						((JLabel)this.days[i]).setText("" + (26  + i));
						this.days[i].setForeground(Color.GRAY);
					}
					for ( int i = 6; i < 36; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 5));
						this.days[i].setForeground(Color.BLACK);
					}
					for ( int i = 36; i < this.days.length; i++ ) {
						((JLabel)this.days[i]).setText(String.valueOf(i - 35));
						this.days[i].setForeground(Color.GRAY);
					}
				}
			} break;
			case Calendar.FEBRUARY: {
				if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && !this.sprache.startWocheMitMontag()) ) {
					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 0; i < 29; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i + 1));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 29; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 28));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 0; i < 28; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i + 1));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 28; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 27));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && !this.sprache.startWocheMitMontag()) ) {
					((JLabel)this.days[0]).setText("31");
					this.days[0].setForeground(Color.GRAY);

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 1; i < 30; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 30; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 29));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 1; i < 29; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 29; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 28));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 2; i++ ) {
						((JLabel)this.days[i]).setText("" + (30 + i));
						this.days[i].setForeground(Color.GRAY);
					}

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 2; i < 31; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 1));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 31; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 30));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 2; i < 30; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 1));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 30; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 29));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 3; i++ ) {
						((JLabel)this.days[i]).setText("" + (29 + i));
						this.days[i].setForeground(Color.GRAY);
					}

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 3; i < 32; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 2));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 32; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 31));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 3; i < 31; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 2));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 31; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 30));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 4; i++ ) {
						((JLabel)this.days[i]).setText("" + (28 + i));
						this.days[i].setForeground(Color.GRAY);
					}

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 4; i < 33; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 3));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 33; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 32));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 4; i < 32; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 3));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 32; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 31));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 5; i++ ) {
						((JLabel)this.days[i]).setText("" + (27 + i));
						this.days[i].setForeground(Color.GRAY);
					}

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 5; i < 34; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 4));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 34; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 33));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 5; i < 33; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 4));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 33; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 32));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
				else if ( (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && this.sprache.startWocheMitMontag()) || (this.calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && !this.sprache.startWocheMitMontag()) ) {
					for ( int i = 0; i < 6; i++ ) {
						((JLabel)this.days[i]).setText("" + (26 + i));
						this.days[i].setForeground(Color.GRAY);
					}

					if ( this.calendar.get(Calendar.YEAR) % 400 == 0 || this.calendar.get(Calendar.YEAR) % 4 == 0 && this.calendar.get(Calendar.YEAR) % 100 != 0 ) {
						for ( int i = 6; i < 35; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 5));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 35; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 34));
							this.days[i].setForeground(Color.GRAY);
						}
					}
					else {
						for ( int i = 6; i < 34; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 5));
							this.days[i].setForeground(Color.BLACK);
						}
						for ( int i = 34; i < this.days.length; i++ ) {
							((JLabel)this.days[i]).setText(String.valueOf(i - 33));
							this.days[i].setForeground(Color.GRAY);
						}
					}
				}
			} break;
		}
	}
}