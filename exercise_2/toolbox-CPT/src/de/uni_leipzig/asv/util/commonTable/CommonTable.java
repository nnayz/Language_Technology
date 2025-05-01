package de.uni_leipzig.asv.util.commonTable;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * This class is to specifie an standardized JTable includet in an JScrollPane.
 * It comes with some usefull methods for the creation and modification of a
 * Table. The table itself is sortable by using a class named TableSorter
 * (external).
 * 
 * @author Daniel Zimmermann
 */
public class CommonTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public JTable commonTable;

	public TableSorter tableSorter;

	public int columns;

	public String[] header;

	/**
	 * Standart constructor for this class.
	 */
	public CommonTable() {

	}

	/**
	 * Standart constructor which creates a JScrollPane and a sortable JTable.
	 * 
	 * @param header
	 *            A String[]-array for the column-headers.
	 * @param data
	 *            A Vector wich contains String[]-arrays with the content of the
	 *            table.
	 */
	public CommonTable(String[] header, Vector data)// , int columnWidth ) //
													// der selbe Vektor wie aus
													// commonDB
	{
		/*
		 * if there are data & a header available, fill the table both
		 */
		if ((this.header != null || header != null) && data != null) {
			int actDatum = 0;
			this.header = header;
			this.columns = this.header.length;
			String rows[][] = new String[data.size()][columns];
			Iterator it = data.iterator();
			// this.columnWidth = columnWidth;

			while (it.hasNext()) {
				String[] o = (String[]) it.next();
				int l = o.length;

				for (int i = 0; i < l; i++) {
					rows[actDatum][i] = o[i];
				}

				actDatum += 1;
			}

			DefaultTableModel model = new DefaultTableModel(rows, this.header);
			tableSorter = new TableSorter(model);
			commonTable = new JTable(tableSorter);

			tableSorter.setTableHeader(commonTable.getTableHeader());
			// commonTable.getColumnModel().getColumn( 0 ).setWidth(
			// this.columnWidth );

			this.getViewport().add(commonTable, null);
		}
		/*
		 * if there is only a header available, create a table with a header,
		 * but w/o data
		 */
		else if (data == null && (this.header != null || header != null)) {
			if (header != null)
				this.header = header;

			this.columns = this.header.length;

			DefaultTableModel model = new DefaultTableModel(null, this.header);
			tableSorter = new TableSorter(model);
			commonTable = new JTable(tableSorter);

			tableSorter.setTableHeader(commonTable.getTableHeader());

			this.getViewport().add(commonTable, null);
		}
	}

	/**
	 * A method that creates a JScrollPane and a sortable JTable, if not done
	 * via the constructor.
	 * 
	 * @param header
	 *            A String[]-array for the column-headers.
	 * @param data
	 *            A Vector wich contains String[]-arrays with the content of the
	 *            table.
	 */
	public void createTable(String[] header, Vector data) {
		if (this.header == null && header == null)
			return;
		else if (data == null) // this is to set the tables header, if
								// available
		{
			if (header != null)
				this.header = header;

			this.columns = this.header.length;

			DefaultTableModel model = new DefaultTableModel(null, this.header);
			tableSorter = new TableSorter(model);
			commonTable = new JTable(tableSorter);

			tableSorter.setTableHeader(commonTable.getTableHeader());

			this.getViewport().add(commonTable, null);

			return;
		}

		/*
		 * if there are any data available, then fill the table
		 */
		if (header != null)
			this.header = header;

		int actDatum = 0;
		this.columns = this.header.length;
		String rows[][] = new String[data.size()][columns];
		Iterator it = data.iterator();

		while (it.hasNext()) {
			String[] o = (String[]) it.next();
			int l = o.length;

			for (int i = 0; i < l; i++) {
				rows[actDatum][i] = o[i];
			}

			actDatum += 1;
		}

		DefaultTableModel model = new DefaultTableModel(rows, this.header);
		tableSorter = new TableSorter(model);
		commonTable = new JTable(tableSorter);

		tableSorter.setTableHeader(commonTable.getTableHeader());
		// commonTable.getColumnModel().getColumn( 0 ).setWidth(
		// this.columnWidth );

		this.getViewport().add(commonTable, null);
	}

	/**
	 * This method sets the minimum and/or maximum width of a specified column
	 * and can also set the actual width.
	 * 
	 * @param column
	 *            The column to set the width to.
	 * @param maxColumnWidth
	 *            The maximum column width (use 0 or below, if you don't want to
	 *            specify this).
	 * @param minColumnWidth
	 *            The minimum column width (use 0 or below, if you don't want to
	 *            specify this).
	 * @param columnWidth
	 *            The actual column width (use 0 or below, if you don't want to
	 *            specify this).
	 */
	public void setColumnWidth(int column, int maxColumnWidth,
			int minColumnWidth, int columnWidth) {
		if (maxColumnWidth > 0)
			commonTable.getColumnModel().getColumn(column).setMaxWidth(
					maxColumnWidth);
		if (minColumnWidth > 0)
			commonTable.getColumnModel().getColumn(column).setMinWidth(
					maxColumnWidth);
		if (columnWidth > 0)
			commonTable.getColumnModel().getColumn(column)
					.setWidth(columnWidth);
	}

	/**
	 * This method adds a TableCellRenderer to a specified column.
	 * 
	 * @param column
	 *            The column to add the TableCellEditor to.
	 * @param tableCellEditor
	 *            The TableCellEditor to add.
	 */
	public void setTableCellRenderer(int column,
			TableCellRenderer tableCellRenderer) {
		if (column >= 0 && tableCellRenderer != null)
			commonTable.getColumnModel().getColumn(column).setCellRenderer(
					tableCellRenderer);
	}

	/**
	 * This method adds a TableCellEditor to a specified column.
	 * 
	 * @param column
	 *            The column to add the TableCellEditor to.
	 * @param tableCellEditor
	 *            The TableCellEditor to add.
	 */
	public void setTableCellEditor(int column, TableCellEditor tableCellEditor) {
		if (column >= 0 && tableCellEditor != null)
			commonTable.getColumnModel().getColumn(column).setCellEditor(
					tableCellEditor);
	}

	/**
	 * This method sets the header.<br>
	 * (only used, if you want to create a new table or you will add data, soon)
	 * 
	 * @param header
	 *            A String[]-array for the column-headers.
	 */
	public void setHeader(String[] header) {
		this.header = header;
		this.columns = this.header.length;
	}

	/**
	 * This method is to set wheter the JTable is editable or not.<br>
	 * This method causes a total disability to select it!<br>
	 * You cannot even use the deleteSelected()-method.
	 * 
	 * @param editable
	 *            true, if the JTable should be editable, else false
	 */
	public void setEditable(boolean editable) {

		if (editable) {
			this.commonTable.setEnabled(true);
		} else {
			this.commonTable.setEnabled(false);
		}
	}

	/**
	 * Adds a Vector with data (String[]-array) to the table - used when the
	 * table was modified.<br>
	 * Use createTable before you addData.
	 * 
	 * @param data
	 */
	public void addData(Vector data) {
		if (data == null)
			return;

		int actDatum = 0;
		String rows[][] = new String[data.size()][columns];
		Iterator it = data.iterator();

		while (it.hasNext()) {

			String[] o = (String[]) it.next();
			int l = o.length;

			for (int i = 0; i < l; i++) {
				rows[actDatum][i] = o[i];
			}

			actDatum += 1;
		}

		DefaultTableModel model = new DefaultTableModel(rows, this.header);
		tableSorter = new TableSorter(model);
		commonTable = new JTable(tableSorter);

		tableSorter.setTableHeader(commonTable.getTableHeader());
		// commonTable.getColumnModel().getColumn( 0 ).setWidth(
		// this.columnWidth );

		this.getViewport().add(commonTable, null);
	}

	/**
	 * This method can add a single row to the table.
	 * 
	 * @param row
	 *            The row you want to add to the table.
	 */
	public void addRow(String[] row) {
		if (row == null)
			return;

		Vector v = this.getTableData(); // get the tables content
		if (v == null)
			v = new Vector();

		v.add(row); // add the new row to the Vector

		this.addData(v); // add this 'new' Vector to the table

		this.getViewport().add(commonTable, null);

		this.setVisible(true);
		this.repaint();
	}

	/**
	 * This method remove the selected rows in the JTable.
	 */
	public void deleteSelected() {
		String[] delItem; // temporary Item to delete
		Vector v = this.getTableData(); // the content of the table
		Iterator it = v.iterator(); // the iterator for the content
		int rowCount = commonTable.getSelectedRowCount(); // how many rows are
															// selected?
		int[] selectedRows = commonTable.getSelectedRows(); // which rows are
															// the selected?
		int columnCount = commonTable.getColumnCount(); // how many columns do
														// the table have?

		int tmp = 0; // to delete from the Vector via Index-No.
		boolean deleteThis = false; // used to remember, if the last 'line' have
									// to be deleted

		if (v == null)
			return; // would be senseless to go on, with no data...

		/*
		 * watch the seleted rows
		 */
		for (int i = 0; i < rowCount; i++) {
			delItem = new String[columnCount];

			/*
			 * get the marked row now...
			 */
			for (int j = 0; j < columnCount; j++) {
				delItem[j] = new String();
				delItem[j] = (String) commonTable
						.getValueAt(selectedRows[i], j);
				// System.out.println(selectedRows[i]);
			}

			// System.out.println(v.indexOf(delItem));

			// v.remove( delItem ); // it is not so easy - what a pitty!

			// reset the tmp-value
			tmp = 0;

			/*
			 * ...find out which one is the right one...
			 */
			while (it.hasNext()) {
				String[] test = (String[]) it.next();

				for (int k = 0; k < columnCount; k++) {
					if (test[k].equals(delItem[k]))
						deleteThis = true; // if one datum to delete equals an
											// entry, mark this entry as to
											// remove
					else {
						deleteThis = false; // if the datum to delete does not
											// equals an entry in one partikular
											// value, it schould not be removed!

						break; // to secure that it will not be removed and
								// because it is not neccessary anymore to check
								// the rest of the entries
						// (if one not equals, it is not the right entry), we
						// can break this loop now
					}
					// System.out.println(deleteThis);
				}

				/*
				 * ...and remove it!
				 */
				if (deleteThis == true) {
					// System.out.println("columnCount="+columnCount+" |
					// deleteThis="+deleteThis);
					v.remove(tmp);
					tmp -= 1; // lower index by 1 - elsewise you would get an
								// indexOutOfBounds...
					// you have to do this, because you habe changed the size of
					// the Vector!
					it = v.iterator(); // get the iterator on the 'new' Vector
										// - I'm not sure if this is neccessary,
										// but - just for security
					deleteThis = false; // reset this value

					break; // if the item to delete was already found, it would
							// be a waste of the CPUs capacity to go on!
				}

				deleteThis = false;
				tmp += 1; // next index
			}
		}

		this.addData(v); // add this 'new' Vector to the table
							// (overwrite-process)

		this.getViewport().add(commonTable, null);

		this.setVisible(true);
		this.repaint();
	}

	/**
	 * This methods returns the data of the JTable.
	 * 
	 * @return the Vector that contains all the data from the JTable
	 *         (String[]-arrays)
	 */
	public Vector getTableData() {
		if (commonTable == null)
			return null;

		Vector result = new Vector();
		int rowCount = commonTable.getRowCount();
		int columnCount = commonTable.getColumnCount();

		for (int i = 0; i < rowCount; i++) {
			String[] value = new String[columnCount];

			for (int j = 0; j < columnCount; j++) {
				value[j] = new String();
				value[j] = (String) commonTable.getValueAt(i, j);
			}

			result.add(value);
		}

		return result;
	}
}
