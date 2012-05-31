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

import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

/**
 * Test Klasse, als Anzeigebeispiel f&uuml;r den Kalender.
 * @author J. Nathanael Philipp
 * @version 1.0
 */
public class Test extends JFrame implements ActionListener {
	private Kalender k;
	private JMenuBar jmb;
	private JMenu jmd;
	private JMenuItem jmib;

	/**
	 * Legt einen JFrame an, mit dem Kalender-Objekt.
	 */
	public Test() {
		super("Kalender");

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(320, 300);
		this.setResizable(false);
		
		//Menu
		this.jmb = new JMenuBar();
		this.jmd = new JMenu("Datei");
		this.jmb.add(this.jmd);
		this.jmib = new JMenuItem("Beenden");
		this.jmib.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
		this.jmib.addActionListener(this);
		this.jmd.add(this.jmib);
		this.setJMenuBar(this.jmb);
		
		this.k = new Kalender(Sprachen.de, true);
		this.add(this.k);

		this.setVisible(true);
	}

	/**
	 * actionPerformed
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource().equals(this.jmib) )
			System.exit(0);
	}

	/**
	 * main
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new Test();
	}
}