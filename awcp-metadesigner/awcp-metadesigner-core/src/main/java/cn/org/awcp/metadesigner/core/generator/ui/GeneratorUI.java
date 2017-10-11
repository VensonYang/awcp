package cn.org.awcp.metadesigner.core.generator.ui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.org.awcp.metadesigner.core.generator.GeneratorMain;

public class GeneratorUI extends JFrame {
	private static final long serialVersionUID = 4639733782232677081L;
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(GeneratorUI.class);
	static JTable table = null;
	static DefaultTableModel defaultModel = null;
	static JFrame tablejf = new JFrame();

	public GeneratorUI() {
		this.setTitle("代码生成器");
		this.getContentPane().setLayout(null);
		this.setSize(600, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);
		add(getjlabelRootOut());
		add(getJTextCreatePath());
		add(getJButtonSubmit());
		add(getJButtonReset());
		add(getJLabelTableName());
		add(getJtextfiledTableName());
		add(getjlabelTempleteSrc());
		add(getJtextfiledClassName());
		add(getJlabelDataBase());
		add(getJTextDataBase());
		add(getJButtonRootOut());
		add(getJButtonTableName());
		add(getJButtonTemplete());
		add(getJReadiButtonDefault());
		add(getJReadiButtonGeneratorUI());
		add(getJLabelChooseSrc());
		add(getJtextfiledClassName());
		add(getJLabelUserName());
		add(getJTextUserName());
		add(getJLabelPwd());
		add(getJTextPwd());
		add(getJTextDataBaseName());
		add(getJLabelDataBaseName());
		add(getJTPackageName());
		add(getJLabelPackageName());
		add(getJLabelPersi());
		add(getJTPersi());
		this.setVisible(true);
	}

	private Map<String, Object> map = new HashMap<String, Object>();
	private JLabel jlabelDataBase = new JLabel("数据库连接:");
	private JLabel jlabelRootOut = new JLabel("生成路径:");
	private JLabel jlabelChooseSrc = new JLabel("模板文件夹:");
	private JLabel jlabelTabelName = new JLabel("表名:");
	private JLabel jlabelTempleteSrc = new JLabel("模板路径:");
	private JTextField jtextDataBase = new JTextField();
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private JTextField jtextfiledTableName = new JTextField();
	private JTextField jtextfiledClass = new JTextField();
	private JTextField jtextfiledCreatePath = new JTextField();
	private JButton jbuttonSubmit = new JButton("生成");
	private JButton jbuttonReset = new JButton("重置");
	private JButton jbuttonRootOut = new JButton("选择");
	private JButton jbuttonTableName = new JButton("选择");
	private JRadioButton jrbDefault = new JRadioButton("默认");
	private JRadioButton jrbGeneratorUI = new JRadioButton("自定义");
	private JFileChooser jfc = new JFileChooser();// 文件选择器
	private JLabel jlabelUserName = new JLabel("用户名:");
	private JLabel jlabelPwd = new JLabel("密码:");
	private JTextField jtextUserName = new JTextField();
	private JPasswordField jtextPwd = new JPasswordField();

	public JRadioButton getJReadiButtonDefault() {
		jrbDefault.setBounds(180, 420, 80, 30);
		jrbDefault.setSelected(true);
		jrbDefault.setFont(new Font("宋体", Font.BOLD, 15));
		jrbDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jrbDefault.setSelected(true);
				jrbGeneratorUI.setSelected(false);
				jlabelChooseSrc.setEnabled(false);
				jtextfiledClass.setEditable(false);
				jbuttonTempleteSrc.setEnabled(false);
			}
		});
		return jrbDefault;
	}

	public JRadioButton getJReadiButtonGeneratorUI() {
		jrbGeneratorUI.setBounds(270, 420, 100, 30);
		jrbGeneratorUI.setFont(new Font("宋体", Font.BOLD, 15));
		jrbGeneratorUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jrbDefault.setSelected(false);
				jlabelChooseSrc.setEnabled(true);
				jtextfiledClass.setEditable(true);
				jbuttonTempleteSrc.setEnabled(true);
			}
		});
		return jrbGeneratorUI;
	}

	private JLabel jlabelPackageName = new JLabel("包名:");

	public JLabel getJLabelPackageName() {
		jlabelPackageName.setBounds(80, 20, 100, 30);
		jlabelPackageName.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelPackageName;
	}

	private JLabel jlabelPersi = new JLabel("模块名:");
	private JTextField jtPersi = new JTextField();

	public JLabel getJLabelPersi() {
		jlabelPersi.setBounds(80, 70, 100, 30);
		jlabelPersi.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelPersi;
	}

	public JTextField getJTPersi() {
		jtPersi.setBounds(180, 70, 250, 30);
		return jtPersi;
	}

	private JTextField jtPackageName = new JTextField();

	public JTextField getJTPackageName() {
		jtPackageName.setBounds(180, 20, 250, 30);
		return jtPackageName;
	}

	public JLabel getJlabelDataBase() {
		jlabelDataBase.setBounds(80, 120, 100, 30);
		jlabelDataBase.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelDataBase;
	}

	public JLabel getJLabelUserName() {
		jlabelUserName.setBounds(80, 220, 250, 30);
		jlabelUserName.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelUserName;
	}

	public JTextField getJTextUserName() {
		jtextUserName.setBounds(180, 220, 250, 30);
		jtextUserName.setText("root");
		return jtextUserName;
	}

	public JLabel getJLabelPwd() {
		jlabelPwd.setBounds(80, 270, 250, 30);
		jlabelPwd.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelPwd;
	}

	public JTextField getJTextPwd() {
		jtextPwd.setBounds(180, 270, 250, 30);
		jtextPwd.setText("123456");
		return jtextPwd;
	}

	static JTextField jttable = new JTextField();

	List<String> list = null;

	JPanel jptable = new JPanel();

	private JButton getJButtonTableName() {
		jfc.setCurrentDirectory(new File("d:\\"));// 文件选择器的初始目录定为d盘
		jbuttonTableName.setBounds(440, 370, 60, 30);
		jbuttonTableName.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				jtextfiledTableName.setText("");
				if (jtextUserName.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "用户名不能为空！");
					return;
				}
				if (jtextPwd.getText().trim().toString().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "密码不能为空！");
					return;
				}
				String sql = "select table_name from tables where TABLE_SCHEMA='" + jtDataBaseName.getText() + "'";
				list = new ArrayList<String>();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					// mysql连接,如果要用别的数据库，要修改连接
					conn = DriverManager.getConnection(jtextDataBase.getText().trim().toString() + "information_schema",
							jtextUserName.getText().trim().toString(), jtextPwd.getText().trim().toString());
					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						list.add(rs.getString("table_name"));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(rootPane, "用户名或密码错误！");
				}
				if (conn == null) {
					JOptionPane.showMessageDialog(rootPane, "请打开数据库连接！");
				} else {
					jtextfiledTableName.setText("");
					jptable.removeAll();
					jptable.repaint();
					final String[] array = new String[list.size()];
					list.toArray(array);
					tablejf.getContentPane().setLayout(null);
					final String[] title = { " ", "表名" };
					Object[][] data = new Object[array.length][array.length];
					for (int i = 0; i < array.length; i++) {
						data[i][0] = new Boolean(false);
						data[i][1] = array[i];
					}
					defaultModel = new DefaultTableModel(data, title);
					table = new JTable(defaultModel);
					TableColumnModel tcm = table.getColumnModel();
					tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
					tcm.getColumn(0).setCellRenderer(new MyTableRenderer());
					tcm.getColumn(0).setPreferredWidth(40);
					tcm.getColumn(0).setWidth(40);
					tcm.getColumn(0).setMaxWidth(20);
					jptable.setBounds(40, 70, 500, 450);
					jptable.add(new JScrollPane(table));
					tablejf.add(jptable);
					JLabel jltable = new JLabel("请输入表名:");
					jltable.setBounds(60, 20, 100, 25);

					jttable.setBounds(170, 20, 250, 25);
					JButton jbtable = new JButton("查询");
					jbtable.setBounds(430, 20, 70, 25);
					jbtable.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (jttable.getText().trim().equals("")) {

							} else {
								table.removeAll();
								List<String> list = new ArrayList<String>();
								for (int i = 0; i < array.length; i++) {
									if (array[i].contains(jttable.getText().trim().toString())) {
										list.add(array[i]);
										logger.debug(array[i]);
									}
								}
								logger.debug(list.size() + "");
								String[] str = new String[list.size()];
								list.toArray(str);
								final String[] title = { " ", "表名" };
								Object[][] data = new Object[str.length][str.length];
								for (int i = 0; i < str.length; i++) {
									data[i][0] = new Boolean(false);
									data[i][1] = str[i];
								}
								defaultModel.setDataVector(data, title);
								TableColumnModel tcm = table.getColumnModel();
								tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
								tcm.getColumn(0).setCellRenderer(new MyTableRenderer());
								tcm.getColumn(0).setPreferredWidth(40);
								tcm.getColumn(0).setWidth(40);
								tcm.getColumn(0).setMaxWidth(20);
								table.repaint();
							}
						}
					});
					JButton jbok = new JButton("OK");
					jbok.setBounds(180, 520, 60, 25);
					jbok.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								jtextfiledTableName.setText("");
								conn.close();
							} catch (Exception e2) {

							}
							for (int i = 0; i < table.getRowCount(); i++) {
								if ((Boolean) table.getValueAt(i, 0)) {
									if (str.contains(table.getValueAt(i, 1).toString())) {
										continue;
									} else {
										str += table.getValueAt(i, 1) + ",";
									}
								}
							}
							jtextfiledTableName.setText(str);
							table.removeAll();
							table.repaint();
							tablejf.setVisible(false);
						}
					});
					JButton jball = new JButton("全选");
					jball.setBounds(250, 520, 60, 25);
					jball.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int num = table.getRowCount();
							for (int i = 0; i < num; i++) {
								table.setValueAt(new Boolean(true), i, 0);
							}
						}
					});
					JButton jballtoo = new JButton("反选");
					jballtoo.setBounds(320, 520, 60, 25);
					jballtoo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int num = table.getRowCount();
							for (int i = 0; i < num; i++) {
								table.setValueAt(new Boolean(false), i, 0);
							}
						}
					});
					tablejf.pack();
					tablejf.add(jltable);
					tablejf.add(jttable);
					tablejf.add(jbtable);
					tablejf.add(jbok);
					tablejf.add(jball);
					tablejf.add(jballtoo);
					tablejf.setSize(600, 600);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					int x = (int) (toolkit.getScreenSize().getWidth() - tablejf.getWidth()) / 2;
					int y = (int) (toolkit.getScreenSize().getHeight() - tablejf.getHeight()) / 2;
					tablejf.setLocation(x, y);
					tablejf.setVisible(true);
				}
			}
		});
		return jbuttonTableName;
	}

	String str = "";
	private JButton jbuttonTempleteSrc = new JButton("选择");

	private JButton getJButtonTemplete() {
		jfc.setCurrentDirectory(new File("d:\\"));// 文件选择器的初始目录定为d盘
		jbuttonTempleteSrc.setBounds(440, 470, 60, 30);
		jbuttonTempleteSrc.setEnabled(false);
		jbuttonTempleteSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(jbuttonTempleteSrc)) {// 判断触发方法的按钮是哪个
					jfc.setFileSelectionMode(1);// 设定只能选择到文件
					int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
					if (state == 1) {
						return;// 撤销则返回
					} else {
						File f = jfc.getSelectedFile();// f为选择到的目录
						jtextfiledClass.setText(f.getAbsolutePath());
					}
				}
			}
		});
		return jbuttonTempleteSrc;
	}

	private JButton getJButtonRootOut() {
		jfc.setCurrentDirectory(new File("d:\\"));// 文件选择器的初始目录定为d盘
		jbuttonRootOut.setBounds(440, 320, 60, 30);
		jbuttonRootOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(jbuttonRootOut)) {// 判断触发方法的按钮是哪个
					jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
					int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
					if (state == 1) {
						return;// 撤销则返回
					} else {
						File f = jfc.getSelectedFile();// f为选择到的目录
						jtextfiledCreatePath.setText(f.getAbsolutePath());
					}
				}
			}
		});
		return jbuttonRootOut;
	}

	public JTextField getJTextDataBase() {
		jtextDataBase.setBounds(180, 120, 250, 30);
		jtextDataBase.setText("jdbc:mysql://localhost:3306/");
		return jtextDataBase;
	}

	private JLabel jlabelDataBaseName = new JLabel("数据库名:");

	public JLabel getJLabelDataBaseName() {
		jlabelDataBaseName.setBounds(80, 170, 100, 30);
		jlabelDataBaseName.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelDataBaseName;
	}

	private JTextField jtDataBaseName = new JTextField();

	public JTextField getJTextDataBaseName() {
		jtDataBaseName.setBounds(180, 170, 250, 30);
		jtDataBaseName.setText("platformcloud");
		return jtDataBaseName;
	}

	public JLabel getjlabelRootOut() {
		jlabelRootOut.setBounds(80, 320, 100, 30);
		jlabelRootOut.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelRootOut;
	}

	public JTextField getJTextCreatePath() {
		jtextfiledCreatePath.setBounds(180, 320, 250, 30);
		return jtextfiledCreatePath;
	}

	public JLabel getJLabelTableName() {
		jlabelTabelName.setBounds(80, 370, 100, 30);
		jlabelTabelName.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelTabelName;
	}

	public JTextField getJtextfiledTableName() {
		jtextfiledTableName.setBounds(180, 370, 250, 30);
		return jtextfiledTableName;
	}

	public JLabel getjlabelTempleteSrc() {
		jlabelTempleteSrc.setBounds(80, 420, 100, 30);
		jlabelTempleteSrc.setFont(new Font("宋体", Font.BOLD, 15));
		return jlabelTempleteSrc;
	}

	public JTextField getJtextfiledClassName() {
		jtextfiledClass.setBounds(180, 470, 250, 30);
		jtextfiledClass.setEditable(false);
		return jtextfiledClass;
	}

	public JLabel getJLabelChooseSrc() {
		jlabelChooseSrc.setBounds(80, 470, 100, 30);
		jlabelChooseSrc.setFont(new Font("宋体", Font.BOLD, 15));
		jlabelChooseSrc.setEnabled(false);
		return jlabelChooseSrc;
	}

	/**
	 * @return
	 */
	public JButton getJButtonSubmit() {
		jbuttonSubmit.setBounds(160, 520, 80, 30);
		jbuttonSubmit.setFont(new Font("宋体", Font.BOLD, 15));
		jbuttonSubmit.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (conn == null) {
					JOptionPane.showMessageDialog(rootPane, "请打开数据库连接！");
					return;
				}
				if (jtPackageName.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "包名不能为空！");
					return;
				}
				if (jtPersi.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(rootPane, "模块名不能为空！");
					return;
				}
				String dataBaseConn = jtextDataBase.getText().trim().toString();
				if (dataBaseConn.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "数据库连接不能为空！");
					return;
				}
				String userName = jtextUserName.getText().trim().toString();
				if (userName.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "用户名不能为空！");
					return;
				}
				String pwd = jtextPwd.getText().trim().toString();
				if (pwd.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "密码不能为空！");
					return;
				}
				String rootOut = jtextfiledCreatePath.getText().trim().toString();
				if (rootOut.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "输出路径不能为空！");
					return;
				}
				map.put("outRoot", rootOut);
				String tableName = jtextfiledTableName.getText().toString();
				if (tableName.equals("")) {
					JOptionPane.showMessageDialog(rootPane, "表名不能为空！");
					return;
				}
				map.put("tableName", tableName);
				// 判断是否选中默认
				if (jrbDefault.isSelected()) {
					// 调用接口，传参数，执行代码生成。
					try {
						map.put("basePackage", jtPackageName.getText().toString());
						map.put("templementSrc", "");
						map.put("persistence", jtPersi.getText().trim().toString());
						JFrame frame = new JFrame("test");
						JLabel label = new JLabel();
						frame.add(label);
						frame.setSize(200, 300);
						label.setText("正在验证，请稍后...");
						frame.setBounds(600, 300, 200, 150);
						frame.setTitle("提示信息");
						frame.setVisible(true);
						// 调用代码生成器接口
						GeneratorMain.StartGenerator(map);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else// 否则是选中自定义
				{
					String templementSrc = jtextfiledClass.getText().trim().toString();
					if (templementSrc.equals("")) {
						JOptionPane.showMessageDialog(rootPane, "模板路径不能为空！");
						return;
					}
					map.put("templementSrc", templementSrc);
					if (jtPersi.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(rootPane, "模块不能为空！");
						return;
					}
					map.put("persistence", jtPersi.getText().trim().toString());
					map.put("basePackage", jtPackageName.getText().trim().toString());
					try {
						// 调用代码生成器接口
						GeneratorMain.StartGenerator(map);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		return jbuttonSubmit;
	}

	public JButton getJButtonReset() {
		jbuttonReset.setBounds(340, 520, 80, 30);
		jbuttonReset.setFont(new Font("宋体", Font.BOLD, 15));
		jbuttonReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jtextDataBase.setText("");
				jtextfiledTableName.setText("");
				jtextfiledCreatePath.setText("");
				jtextUserName.setText("");
				jtextPwd.setText("");
				jrbDefault.setSelected(true);
				jrbGeneratorUI.setSelected(false);
				jtextfiledClass.setEnabled(false);
				jlabelChooseSrc.setEnabled(false);
				jbuttonTempleteSrc.setText("");
				jbuttonTempleteSrc.setEnabled(false);
			}
		});
		return jbuttonReset;
	}
}
