package org.wxy.express;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxy.express.constant.Constants;
import org.wxy.express.service.QueryService;
import org.wxy.express.service.impl.UPSService;

public class SearchJP extends JPanel implements ActionListener,MouseListener,ChangeListener {
	private static final Logger logger = LoggerFactory.getLogger(SearchJP.class);
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_TEXT = "请输入单号!";
	
	JButton search,stop,clear = null;
	JTextArea sourceOrders = null;
	JProgressBar progressBar = null;
	
	/**
	 * 查询框
	 */
	public SearchJP() {
		setPreferredSize(new Dimension(400,650));//设置最优大小  
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(14, 13, 372, 525);
		add(scrollPane);
		
		// 输入框
		sourceOrders = new JTextArea();
		sourceOrders.setText(DEFAULT_TEXT);
		sourceOrders.addMouseListener(this);// 增加mouse监听
		
		scrollPane.setViewportView(sourceOrders);
		
		search = new JButton("查询");
		search.addActionListener(this);
		search.setBounds(14, 599, 113, 27);
		add(search);
		
		stop = new JButton("中止");
		stop.addActionListener(this);
		stop.setBounds(141, 599, 113, 27);
		add(stop);
		
		clear = new JButton("清空");
		clear.addActionListener(this);
		clear.setBounds(273, 599, 113, 27);
		add(clear);
		
		progressBar = new JProgressBar();
		progressBar.setOrientation(JProgressBar.HORIZONTAL);
		progressBar.setToolTipText("");
		progressBar.setBorderPainted(true);
		
		progressBar.setStringPainted(true); // 是否显示字符串
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setString("未启用");
		progressBar.setBackground(new Color(0xcf, 0xd1, 0xd3));
		progressBar.setForeground(new Color(0xa3, 0xef, 0xb0));
		progressBar.setBounds(14, 558, 372, 27);
		progressBar.addChangeListener(this);
		
		add(progressBar);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.debug("按钮:"+e.getActionCommand());
		final QueryService query = new UPSService();
		if (e.getSource() == search) {
			String text = sourceOrders.getText();
			
			// 为空或默认值不查询
			if(StringUtils.isBlank(text) || DEFAULT_TEXT.equals(text)) {
				JOptionPane.showMessageDialog(null, DEFAULT_TEXT);
				return ;
			}
			Map<String, Object> result = query.find(sourceOrders.getText());
			
			if(Constants.STATE_SUCCESS.equals(result.get(Constants.STATE))) {
				JOptionPane.showMessageDialog(null, "查询成功,请查看输出目录!");
			}else if(Constants.STATE_FAIL.equals(result.get(Constants.STATE))) {
				JOptionPane.showMessageDialog(null, "查询失败,请联系开发者!");
			}else {
				JOptionPane.showMessageDialog(null, "未知异常,请联系开发者!");
			}

		} else if (e.getSource() == stop) {
			int option = JOptionPane.showConfirmDialog(this, "确认中止当前查询任务?", "温馨提示", JOptionPane.YES_NO_OPTION);
			if(JOptionPane.YES_OPTION == option) {
				JOptionPane.showMessageDialog(null, "中止");
			}
		} else if (e.getSource() == clear) {
			sourceOrders.setText("");
		} 

	}
	
	@Override
	/** 鼠标单击事件 */
	public void mouseClicked(MouseEvent e) {
//		int c = e.getButton();// 得到按下的鼠标键
//		String mouseInfo = null;// 接收信息
//		// 推断是鼠标左键按下
//		if (c == MouseEvent.BUTTON1) {
//			mouseInfo = "左键";
//		}
//		// 推断是鼠标右键按下
//		else if (c == MouseEvent.BUTTON3) {
//			mouseInfo = "右键";
//		} 
//		// 如下目前错误错误
//		else {
//			mouseInfo = "滚轴";
//		}
//		logger.debug("鼠标单击：" + mouseInfo);
	}

	/** 鼠标进入组件 */
	@Override
	public void mouseEntered(MouseEvent e) {
		logger.debug("鼠标进入组件.");
	}

	/** 鼠标退出组件 */
	@Override
	public void mouseExited(MouseEvent e) {
		logger.debug("鼠标退出组件.");
	}

	/** 鼠标按下 */
	@Override
	public void mousePressed(MouseEvent e) {
		int c = e.getButton();// 得到按下的鼠标键
		if (c == MouseEvent.BUTTON1) {
			logger.debug("鼠标左键按下.");
			String text = sourceOrders.getText();
			if(StringUtils.isNotBlank(text) && DEFAULT_TEXT.equals(text)) {
				sourceOrders.setText("");
			}
		}
	}

	/** 鼠标松开 */
	public void mouseReleased(MouseEvent e) {
		int c = e.getButton();// 得到按下的鼠标键
		if (c == MouseEvent.BUTTON1) {
			logger.debug("鼠标左键松开");
			// String text = sourceOrders.getText();
			// if(StringUtils.isBlank(text)) {
			// sourceOrders.setText(DEFAULT_TEXT);
			// }
		} else if (c == MouseEvent.BUTTON3) {
			logger.debug("鼠标右键松开");
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		  int value=progressBar.getValue();
	        if(e.getSource()==progressBar){
	            logger.info("目前已完成进度："+Integer.toString(value)+"%");
	        }
	}
	
}
