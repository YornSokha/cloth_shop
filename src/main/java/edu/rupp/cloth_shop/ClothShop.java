package edu.rupp.cloth_shop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JSplitPane;
import java.awt.Color;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class ClothShop extends JFrame {

	private JPanel contentPane;
	private JTree tree;
	private JTabbedPane jtab;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClothShop frame = new ClothShop();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClothShop() {
		initConponent();
		
	}

	private void initConponent() {
		// TODO Auto-generated method stub
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JMenu menu = new JMenu("File");
		menu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Save");
		menuItem_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		menu.add(menuItem_1);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItem_2 = new JMenuItem("Exit");
		menuItem_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		menu.add(menuItem_2);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(7);
		splitPane.setContinuousLayout(true);
		splitPane.setBackground(new Color(173, 216, 230));
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		tree = new JTree();
		tree.setRootVisible(false);
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("tree") {
				{
					add(new DefaultMutableTreeNode("sale"));
					add(new DefaultMutableTreeNode("customer"));
					add(new DefaultMutableTreeNode("product"));
					add(new DefaultMutableTreeNode("category"));
					add(new DefaultMutableTreeNode("seller"));
					add(new DefaultMutableTreeNode("report"));
				}
			}
		));
		tree.setShowsRootHandles(true);
		tree.setFont(new Font("Tahoma", Font.PLAIN, 13));
		splitPane.setLeftComponent(tree);
		
		jtab = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(jtab);
		
		//double click on tree event
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//find selected node of tree
				int selectedNode = tree.getRowForLocation(e.getX(), e.getY());
					//condition when mouse pressed on a specific node
					if(selectedNode != -1) {
						//when mouse pressed is double click
						if(e.getClickCount() == 2) {
							//Get for whole tree path
							TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
							//Get last path of tree
							String lastPath = treePath.getLastPathComponent().toString();
							System.out.println(lastPath);
							if(lastPath.equalsIgnoreCase("Sale")) {
								
							}
							if(lastPath.equalsIgnoreCase("Customer")) {
////								EmployeeList empList = new EmployeeList();
////								empList.displayEmployeeInTable();
////								JScrollPane jspEmpList = new JScrollPane(empList);
////								jtap.addTab("New Employee", jspEmpList);
////								jtap.setSelectedComponent(jspEmpList);
//						
							}else if(lastPath.equalsIgnoreCase("Product")) {
////								
//									
							}else if(lastPath.equalsIgnoreCase("Category")) {
//								
							}else if(lastPath.equalsIgnoreCase("Seller")) {
	
							}else if(lastPath.equalsIgnoreCase("Report")){
								
							}
//							else{
//								
//								JScrollPane jspTaxCal = new JScrollPane(new TaxCalculator());
//								//ImageIcon iconTaxCal = new ImageIcon("resource/1.jpg");
//								jtap.addTab("Tax Calculator",  jspTaxCal);
//								jtap.setSelectedComponent(jspTaxCal);
//							
//							}
							
						}
					}
			}
		});
		splitPane.setDividerLocation(170);
	}

	
}
