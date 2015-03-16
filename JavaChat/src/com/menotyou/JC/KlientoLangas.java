package com.menotyou.JC;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.menotyou.JC.NIOBiblioteka.NIOAptarnavimas;

public class KlientoLangas extends JFrame {

//	private final Font NUMATYTASIS_SRIFTAS = new Font();
	private static final long serialVersionUID = 1L;
	private JTabbedPane jtp;
	private JTextArea Istorija;
	private SriftoPasirinkimas fc;
	private Font pasirinktasSriftas;
	private KambarioKurimas kk;
	private static NIOKlientas klientas;

	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOnlineUsers;
	private JMenuItem mntmExit;
	private JMenu mnNustatymai;
	private JMenuItem mntmTekstoNustatymai;
	private JMenuItem mntmVartotojoNustatymai;
	private JMenu mnKambariai;
	private JMenuItem mntmPridtiKambar;

	public KlientoLangas() {
		sukurkLanga();
	}

	private void sukurkLanga() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 550);
		setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
		setJMenuBar(menuBar);

		mnFile = new JMenu("Meniu");
		menuBar.add(mnFile);
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			   }
		});

		mntmOnlineUsers = new JMenuItem("Prisijung\u0119 vartotojai");
		mntmOnlineUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				onlineUsers.setVisible(true);
			}
		});
		mnFile.add(mntmOnlineUsers);
		

		mntmExit = new JMenuItem("I\u0161eiti");
		mnFile.add(mntmExit);
		
		mnNustatymai = new JMenu("Nustatymai");
		menuBar.add(mnNustatymai);
		
		mntmTekstoNustatymai = new JMenuItem("Teksto nustatymai");
		mntmTekstoNustatymai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new SriftoPasirinkimas(KlientoLangas.this, Istorija.getFont());
				fc.setVisible(true);
				pasirinktasSriftas = fc.gaukPasirinktaSrifta();
				Istorija.setFont(pasirinktasSriftas);
			}
		});
		mnNustatymai.add(mntmTekstoNustatymai);
		
		mntmVartotojoNustatymai = new JMenuItem("Vartotojo nustatymai");
		mnNustatymai.add(mntmVartotojoNustatymai);
		
		mnKambariai = new JMenu("Kambariai");
		menuBar.add(mnKambariai);
		
		mntmPridtiKambar = new JMenuItem("Prid\u0117ti Kambar\u012F");
		mntmPridtiKambar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kk = new KambarioKurimas(KlientoLangas.this);
			}
		});
		mnKambariai.add(mntmPridtiKambar);
		
		jtp = new JTabbedPane();
		jtp.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(jtp);

	}
	public void sukurkKambarioInterfeisa(String pavadinimas){
		System.out.println("Kuriamas kambarys pavadinimu:" + pavadinimas);
		KambarioInterfeisas k = new KambarioInterfeisas(jtp, klientas, pavadinimas);
		if(klientas.pridekKambari(pavadinimas, k))
			jtp.addTab(pavadinimas, k);
	}
	public NIOKlientas gaukKlienta(){
		return klientas;
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KlientoLangas frame = new KlientoLangas();
					frame.setVisible(false);
					SvecioPrisijungimas svecias = new SvecioPrisijungimas(frame);
					svecias.setVisible(true);
					klientas = new NIOKlientas(frame, svecias);
					klientas.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	




}