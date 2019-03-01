package edu.rupp.cloth_shop;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JSplitPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.rupp.cloth_shop.category.PCategory;
import edu.rupp.cloth_shop.customer.PCustomer;
import edu.rupp.cloth_shop.report.PSaleDetailReport;
import edu.rupp.cloth_shop.report.PSaleReport;
import edu.rupp.cloth_shop.sale.PSale;
import edu.rupp.cloth_shop.seller.PSeller;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
		setExtendedState(Frame.MAXIMIZED_BOTH);
//		JFrame.setDefaultLookAndFeelDecorated(true);
		initConponent();
		welcomeImage();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void welcomeImage() {
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				PWelcome welcome = new PWelcome();
                jtab.addTab("Welcome", welcome);
                jtab.setSelectedComponent(welcome);
                
                PSale sale = new PSale();
				jtab.addTab("Sale", sale);
				jtab.setSelectedComponent(sale);
				
				PSaleReport sales = new PSaleReport();
				jtab.addTab("Sale Report", sales);
				jtab.setSelectedComponent(sales);
				
				PSaleDetailReport saleDetail = new PSaleDetailReport();
				jtab.addTab("Sale Detail Report", saleDetail);
				jtab.setSelectedComponent(saleDetail);
				
				jtab.setSelectedIndex(0);
			}
		});
		
	}

	private void initConponent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 470);
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
					DefaultMutableTreeNode node_1;
					add(new DefaultMutableTreeNode("Sale"));
					add(new DefaultMutableTreeNode("Customer"));
					add(new DefaultMutableTreeNode("Category"));
					add(new DefaultMutableTreeNode("Seller"));
					node_1 = new DefaultMutableTreeNode("report");
						node_1.add(new DefaultMutableTreeNode("Sales"));
						node_1.add(new DefaultMutableTreeNode("Sale Details"));
					add(node_1);
				}
			}
		));
		tree.setShowsRootHandles(true);
		tree.setFont(new Font("Tahoma", Font.PLAIN, 13));
		splitPane.setLeftComponent(tree);
		
		jtab = new JTabbedPane(JTabbedPane.TOP);
		jtab.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				int selectedIndex = jtab.getSelectedIndex();
				if(selectedIndex == 2) {
					jtab.setComponentAt(selectedIndex, new PSaleReport());
				}else if(selectedIndex == 3) {
					jtab.setComponentAt(selectedIndex, new PSaleDetailReport());
				}
			}
		});
		
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
							if(lastPath.equalsIgnoreCase("Sale")) {
//								PSale sale = new PSale();
//								jtab.addTab("Sale", sale);
//								jtab.setSelectedComponent(sale);
//								jtab.setComponentAt(1, new PSale());
								jtab.setSelectedIndex(1);
							}
							if(lastPath.equalsIgnoreCase("Customer")) {
								
								PCustomer customer = new PCustomer();
								jtab.addTab("Customer", customer);
								jtab.setSelectedComponent(customer);
							}else if(lastPath.equalsIgnoreCase("Product")) {
								
//						            PProduct product = new PProduct();
//                                    jtab.addTab("Product", product);
//                                    jtab.setSelectedComponent(product);								
							}else if(lastPath.equalsIgnoreCase("Category")) {
								
                                    PCategory category = new PCategory();
                                    jtab.addTab("Category", category);
                                    jtab.setSelectedComponent(category);
							}else if(lastPath.equalsIgnoreCase("Seller")) {
								
                                    PSeller seller = new PSeller();
                                    jtab.addTab("Seller", seller);
                                    jtab.setSelectedComponent(seller);
							}else if(lastPath.equalsIgnoreCase("Sales")){
								
//								PSaleReport sales = new PSaleReport();
//								jtab.addTab("Sale Report", sales);
//								jtab.setSelectedComponent(sales);
								jtab.setComponentAt(2, new PSaleReport());
								jtab.setSelectedIndex(2);
							}
							else if(lastPath.equalsIgnoreCase("Sale Details")){
								
//								PSaleDetailReport saleDetail = new PSaleDetailReport();
//								jtab.addTab("Sale Detail Report", saleDetail);
//								jtab.setSelectedComponent(saleDetail);
								jtab.setComponentAt(3, new PSaleDetailReport());
								jtab.setSelectedIndex(3);
							
							}
							
						}
					}
			}
		});
		splitPane.setDividerLocation(170);
	}

	
}
