/**
 * 
 */
package com.edu.seu.MusicSpider;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.edu.seu.MusicSpider.SpiderUtiils.SpiderApi;
import com.edu.seu.MusicSpider.model.Song;
import com.edu.seu.MusicSpider.service.AppService;
import com.edu.seu.MusicSpider.service.PlaylistService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;

/**
 * @creat time 2017年4月23日 下午3:50:51
 * @author hao zhang
 * @version 1.0
 * @comment 
 */
public class SpiderEntrance {

	private JFrame frame;
	private JTextField textField;
	private static int DEFAULT_PLAYLIST_NUMBER = 25;//默认获取歌单数量
	private static int DEFAULT_DEPTH = 3;//默认相似歌曲深度s
	private static String DEFAULT_ID = "/song?id=247193";//默认推荐歌曲原始ID
	private static String DEFAULT_TYPE = "流行";//默认歌曲类型
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpiderEntrance window = new SpiderEntrance();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpiderEntrance() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LinkedHashMap<String, String> playListType = new LinkedHashMap();

		final AppService app = new AppService();
		playListType = app.getPlaylistType();
		
		Vector<String> vector = new Vector();

		Iterator iter = playListType.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
            vector.add(key.toString());
		}


		frame = new JFrame();
		frame.setBounds(100, 100, 793, 599);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 782, 563);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setToolTipText("请输入获取歌单数目");

		textField.setBounds(199, 84, 241, 21);
		panel.add(textField);
		textField.setColumns(10);
		
		final JComboBox list = new JComboBox(vector);
		list.setBounds(199, 282, 241, 21);
		panel.add(list);

		JButton mainButton = new JButton("Go!");
		mainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int playListNum;
				String songType = null;
				
				if (textField.getText() == null || textField.getText().equals("")) {
					playListNum = DEFAULT_PLAYLIST_NUMBER;
				}else {
					playListNum = Integer.parseInt(textField.getText());
				}
				
				if (list.getSelectedItem() == null || textField.getText().equals("")) {
					songType = DEFAULT_TYPE;
				}else {
					songType = (String) list.getSelectedItem();
				}
				app.MusicSpider(songType,playListNum);
			}
		});
		mainButton.setBounds(477, 83, 93, 23);
		panel.add(mainButton);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("请输入原始歌曲ID");
		textField_1.setBounds(199, 146, 241, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("请输入推荐歌曲深度");
		textField_2.setBounds(199, 214, 241, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Run!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpiderApi spiderApi = new SpiderApi();
				int depth;
				String sogID;
				
				if (textField_1.getText() == null || textField_1.getText().equals("")) {
					sogID = DEFAULT_ID;
				}else {
					sogID = "/song?id="+textField_1.getText();
				}
				if (textField_2.getText() == null || textField_2.getText().equals("")) {
					depth = DEFAULT_DEPTH;
				}else {
					depth = Integer.parseInt(textField_2.getText());
				}
		        spiderApi.getRecoSong(sogID,depth);
			}
		});
		
		
		btnNewButton.setBounds(477, 145, 93, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("所需爬取歌单数目：");
		lblNewLabel.setBounds(79, 87, 108, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("原始歌曲ID：");
		lblNewLabel_1.setBounds(79, 149, 108, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("获取推荐歌曲深度：");
		lblNewLabel_2.setBounds(79, 217, 110, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("歌曲类型：");
		lblNewLabel_3.setBounds(79, 288, 108, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("提示：");
		lblNewLabel_4.setBounds(79, 341, 54, 15);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("默认获取歌单数量： 25");
		lblNewLabel_5.setBounds(79, 385, 491, 15);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("默认相似歌曲深度: 3");
		lblNewLabel_6.setBounds(79, 428, 491, 15);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("默认推荐歌曲原始ID: 247193");
		lblNewLabel_7.setBounds(79, 472, 491, 15);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("默认歌曲类型: 流行");
		lblNewLabel_8.setBounds(79, 517, 499, 15);
		panel.add(lblNewLabel_8);
		
		
					
		
	}
}
