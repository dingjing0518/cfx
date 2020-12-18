/**
 * 
 */
package cn.cf.json;

/**
 * @author bin
 *
 */
public class ExtTreeState {
	private boolean selected=false;
	private boolean opened=true;
	private boolean  disabled=false;
	
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
}
