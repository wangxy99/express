package org.wxy.express;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressJF extends JFrame {
	private static final Logger logger = LoggerFactory.getLogger(ExpressJF.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExpressJF frame = new ExpressJF();
					frame.setVisible(true);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExpressJF() {
		setTitle("UPS单号查询V2.0.0 By wxy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(415,690));//设置最优大小  
		//参考（JFrame自动调整大小适应子控件）：https://blog.csdn.net/lihailinlihailin/article/details/6949155?locationNum=15 
		SearchJP search = new SearchJP();
		setContentPane(search);
		setResizable(false);	//禁止改变大小在调用显示之前  
	    setVisible(true);		//显示JFrame  
	    pack();					//调用pack()适应子控件大小 
		setLocationRelativeTo(null);
	}

}
