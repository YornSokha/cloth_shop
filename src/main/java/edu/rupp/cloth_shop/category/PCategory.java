package edu.rupp.cloth_shop.category;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.logic.Category;
import edu.rupp.cloth_shop.logic.Seller;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PCategory extends JPanel {
	private JTextField txtId;
	private JTextField txtName;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnAdd, btnDelete, btnUpdate, btnClear;
	private int seletedRow = -1;
	private TreeMap<Integer, Category> categories;
	private Object[] columns = new Object[2];
	/**
	 * Create the panel.
	 */
	public PCategory() {
		setLayout(null);
		initComponent();
		getCategories();
		addCategoryToTable(categories);
	}
	
	private void addCategoryToTable(TreeMap<Integer, Category> categories) {
		model.setRowCount(0);
		int index = 0;
		for(Entry<Integer, Category> entry : categories.entrySet()) {
			Category category = entry.getValue();
			columns[0] = ++index;
			columns[1] = category.getName();
			model.addRow(columns);
		}
		
	}
	
	private void initComponent() {
		JPanel panel = new JPanel();
		panel.setBounds(107, 144, 584, 32);
		add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] returnId = { "CategoryID" };
				String name = txtName.getText();
				if(name.equals("")) {
					JOptionPane.showMessageDialog(null, "Name must have value!", "Information", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
					
				int id = 0;
				try {
					Connection con = DB.getConnection();
					String sql = "INSERT INTO Categories(name) VALUES(?)";
					PreparedStatement ps = con.prepareStatement(sql, returnId);
					ps.setString(1, name);				
					int i = ps.executeUpdate();
					if (i != 0) {
				        JOptionPane.showMessageDialog(null, "Category has been added successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
				        int rowCount = model.getRowCount();
				        columns[0] = rowCount + 1;
						columns[1] = name;																						
				    } else {
				        System.out.println("not Inserted");
				    }
					//Get the last inserted id
				    try (ResultSet rs = ps.getGeneratedKeys()) {
				        if (rs.next()) {				            
				            id = rs.getInt(1);
				            categories.put(id, new Category(id, name));
				            btnClear.doClick();	
				        }
				        rs.close();
				    }
				    con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		panel.add(btnAdd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!idExisted())
					return;
				try {
					Connection con = DB.getConnection();
					con = DB.getConnection();
					String sql = "UPDATE Categories SET name = ? WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					String name = txtName.getText();
					ps.setString(1, name);
					ps.setInt(2, id);
					int i = ps.executeUpdate();
					if(i != 0) {
						 JOptionPane.showMessageDialog(null, "Update success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						 categories.put(id, new Category(id, name));
						 columns[0] = seletedRow + 1;
						 columns[1] = name;						
						 for(int j = 0; j < model.getColumnCount(); j++) {
							 model.setValueAt(columns[j], seletedRow, j);
						 }
					}else {
						JOptionPane.showMessageDialog(null, "Update failed!", "Information", JOptionPane.WARNING_MESSAGE);
					}
					con.close();
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnUpdate.setEnabled(false);
		panel.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!idExisted())
					return;
				int i = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
				if(i != 0)
					return;
				try {
					Connection con = DB.getConnection();
					String sql = "DELETE FROM Categories WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					ps.setInt(1, id);
					int j = ps.executeUpdate();								
					if(j != 0) {
						JOptionPane.showMessageDialog(null, "Delete success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						//Remove rows one by one from the end of the table
						categories.remove(id);
						clearRow();
					}else {
						JOptionPane.showMessageDialog(null, "Delete failed!", "Information", JOptionPane.WARNING_MESSAGE);
					}
					con.close();
					
				}catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Information", JOptionPane.WARNING_MESSAGE);
				}
			}
			private void clearRow() {
				model.setRowCount(0);
				addCategoryToTable(categories);
				btnClear.doClick();
			}
		});
		
		btnDelete.setEnabled(false);
		panel.add(btnDelete);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearControls();
				addCategoryToTable(categories);
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				txtName.requestFocus();
			}

			private void clearControls() {
				txtId.setText("");
				txtName.setText("");
			}
		});
		panel.add(btnClear);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(172, 57, 449, 56);
		add(panel_1);
		panel_1.setLayout(new GridLayout(2, 2, 5, 10));
		
		JLabel label = new JLabel("ID");
		panel_1.add(label);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		panel_1.add(txtId);
		
		JLabel label_1 = new JLabel("Name");
		panel_1.add(label_1);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		panel_1.add(txtName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(123, 222, 557, 222);
		add(scrollPane);
		
		table = new JTable(){
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model = new DefaultTableModel();
		model.addColumn("No");
		model.addColumn("Name");
		table.setModel(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seletedRow = table.getSelectedRow();
	        	String name = (String)table.getValueAt(seletedRow, 1);
	        	int id = 0;
	        	try {
	        		Connection con = DB.getConnection();
	        		String sql = "SELECT id FROM categories WHERE name = ?";
	        		PreparedStatement ps = con.prepareStatement(sql);
	        		ps.setString(1, name);
	        		ResultSet rs = ps.executeQuery();
	        		if(rs.next()) {
	        			id = rs.getInt(1);
	        		}
	        		rs.close();
	        		con.close();
	        	}catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	        	
	        	btnAdd.setEnabled(false);
	        	btnDelete.setEnabled(true);
	        	btnUpdate.setEnabled(true);
	        	txtId.setText("" + id);
	        	txtName.setText(name);	        			
			}
		});
		
		scrollPane.setViewportView(table);
		
	}

	private void getCategories() {
        categories = new TreeMap<Integer, Category>();
        try {
        	Connection con = DB.getConnection();
        	String sql = "SELECT * FROM categories";
        	Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            int id;
            String name;
            while (res.next()) {
            	id = res.getInt(1);
            	name = res.getString(2);
            	categories.put(id, new Category(id, name));
            }
            con.close();
        }catch (Exception e) {
        	e.printStackTrace();
		}

	}
	
	private boolean idExisted() {
		String checkId = txtId.getText();
		if(checkId.equals(""))
			return false;
		return true;
	}
}
