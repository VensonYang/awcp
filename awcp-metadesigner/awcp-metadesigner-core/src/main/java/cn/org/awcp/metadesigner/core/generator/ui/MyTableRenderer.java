package cn.org.awcp.metadesigner.core.generator.ui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyTableRenderer extends JCheckBox implements TableCellRenderer {

	private static final long serialVersionUID = 821683972106982411L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Boolean b = (Boolean) value;
		this.setSelected(b.booleanValue());
		return this;
	}

}
