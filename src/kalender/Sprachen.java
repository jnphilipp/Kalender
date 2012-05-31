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

import java.util.Arrays;

/**
 * Sprache f&uuml;r den <code>Kalender</code>.
 * @author J. Nathanael Philipp
 * @version 1.0
 */
public enum Sprachen {
	/**
	 * Deutsch
	 */
	de,
	/**
	 * Englisch
	 */
	en,
	/**
	 * Finnisch
	 */
	fi;

	/**
	 * Gibt die Wochentage in der entsprechenden Sprache zur&uuml;ck.
	 * @return Wochentage
	 */
	public String[] getWochentage() {
		if ( this == de ) {
			String[] wt = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
			return wt;
		}
		else if ( this == en ) {
			String[] wt = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
			return wt;
		}
		else if ( this == fi ) {
			String[] wt = {"ma", "ti", "ke", "to", "pe", "la", "su"};
			return wt;
		}

		return null;
	}

	/**
	 * Gibt die Monate in der entsprechenden Sprache zur&uuml;ck.
	 * @return Monate
	 */
	public String[] getMonate() {
		if ( this == de ) {
			String[] m = {"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
			return m;
		}
		else if ( this == en ) {
			String[] m = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
			return m;
		}
		else if ( this == fi ) {
			String[] m = {"tammikuu", "helmikuu", "maaliskuu", "huhtikuu", "toukokuu", "kesäkuu", "heinäkuu", "elokuu", "syyskuu", "lokakuu", "marraskuu", "joulukuu"};
			return m;
		}

		return null;
	}

	/**
	 * Gibt die Tabs in der entsprechenden Sprache zur&uuml;ck.
	 * @return Tabs
	 */
	public String[] getTabs() {
		if ( this == de ) {
			String[] m = {"Kalender", "Tage"};
			return m;
		}
		else if ( this == en ) {
			String[] m = {"calendar", "days"};
			return m;
		}
		else if ( this == fi ) {
			String[] m = {"kalenteri", "päivät"};
			return m;
		}

		return null;
	}

	/**
	 * Gibt die Funktionen in der entsprechenden Sprache zur&uuml;ck.
	 * @return Funktionen
	 */
	public String[] getFunktionen() {
		if ( this == de ) {
			String[] m = {"hinzugefügt", "entfernt"};
			return m;
		}
		else if ( this == en ) {
			String[] m = {"added", "removed"};
			return m;
		}
		else if ( this == fi ) {
			String[] m = {"	lisäsi", "poisti"};
			return m;
		}

		return null;
	}

	/**
	 * Pr&uuml;ft, ob der angegebne Monat enthalten ist und gibt dementsprechend <code>true</code> oder <code>false</code> zur&uuml;ck.
	 * @param monat Monat Zupr&uuml;fender Monat.
	 * @return <code>true</code> oder <code>false</code>, jenachdem ob die Pr&uuml;fung erfolgreich war oder nicht.
	 */
	public boolean contains(String monat) {
		return Arrays.asList(this.getMonate()).contains(monat);
	}
}