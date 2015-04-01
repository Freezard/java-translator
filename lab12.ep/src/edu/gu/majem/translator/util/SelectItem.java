package edu.gu.majem.translator.util;

import edu.gu.majem.dict.LanguageName;

/**
 * Used to populate ComboBoxes (key shown in GUI because of overridden toString,
 * but value used internal by application). Will sort content of boxes so Comparable
 * 
 * @author hajo
 * 
 */
public final class SelectItem implements Comparable<SelectItem> {

	private final String key;
	private final LanguageName value;

	public SelectItem(String key, LanguageName value) {
		this.key = key;
		this.value = value;
	}

	// This is shown in GUI "the key"
	@Override
	public String toString() {
		return key;
	}

	// This is the selected item used by application
	public LanguageName getValue() {
		return value;
	}

	// ---- Don't care for now (the below) -------------
	
	@Override
	public int compareTo(SelectItem o) {
		return key.compareTo(o.key);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectItem other = (SelectItem) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
