package com.menotyou.JC;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Langas skirtas kurti kambarį.
 */
public class KambarioKurimas extends JFrame {

    private static final long serialVersionUID = -3747377694043578560L;
    private JPanel contentPane;
    private JTextField m_pavadinimas;
    private JTextArea m_pradineZinute;
    private NIOKlientas m_klientas;
    private JScrollPane langelis;
    private JProgressBar progressBar;
    private JButton btnKurtiKam;

    /**
     * Sukuriamas naujas kabario kūrimo langas.
     *
     * @param klientoLangas -> Langas iš kurio buvo iškvietas šis komponentas.
     * @param sriftas -> Dabartinis programos naudojamas šriftas.
     */
    public KambarioKurimas(final KlientoLangas klientoLangas, final Font sriftas) {
        setTitle("Kambario kūrimas");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 304, 318);
        m_klientas = klientoLangas.gaukKlienta();

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        m_pavadinimas = new JTextField();
        m_pavadinimas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    kambarioUzklausa(m_pavadinimas.getText(), m_pradineZinute.getText());
                }
            }
        });
        m_pavadinimas.setBounds(78, 67, 112, 20);
        contentPane.add(m_pavadinimas);
        m_pavadinimas.setColumns(10);

        btnKurtiKam = new JButton("Kurti kambar\u012F");
        btnKurtiKam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kambarioUzklausa(m_pavadinimas.getText(), m_pradineZinute.getText());
            }
        });
        btnKurtiKam.setBounds(78, 218, 115, 23);
        contentPane.add(btnKurtiKam);

        JLabel lblPavadinimas = new JLabel("Pavadinimas");
        lblPavadinimas.setBounds(101, 42, 71, 14);
        contentPane.add(lblPavadinimas);

        setContentPane(contentPane);

        JLabel lblkamprad = new JLabel("Kambario pradinė žinutė");
        lblkamprad.setBounds(76, 98, 132, 14);
        contentPane.add(lblkamprad);

        langelis = new JScrollPane();
        langelis.setBounds(32, 126, 237, 81);
        contentPane.add(langelis);

        m_pradineZinute = new JTextArea();
        m_pradineZinute.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    kambarioUzklausa(m_pavadinimas.getText(), m_pradineZinute.getText());
                }
            }
        });
        m_pradineZinute.setFont(sriftas);
        langelis.setViewportView(m_pradineZinute);
        langelis.setFont(klientoLangas.gaukSrifta());

        progressBar = new JProgressBar();
        progressBar.setBounds(0, 276, 298, 14);
        progressBar.setVisible(false);
        contentPane.add(progressBar);

        setVisible(true);
    }

    /**
     * Funkcija skirta pardoyti klaida su nurodytu tekstu.
     *
     * @param klaida -> klaidos tekstas.
     */
    public void klaida(String klaida) {
        JOptionPane.showMessageDialog(null, klaida, "Klaida!", JOptionPane.INFORMATION_MESSAGE);
        progressBar.setVisible(false);
        btnKurtiKam.setEnabled(true);
    }

    /**
     * Pašalinamas šis langas.
     */
    public void pasalink() {
        progressBar.setVisible(false);
        btnKurtiKam.setEnabled(true);
        dispose();
    }

    /**
     * Funkcija kuri patikrina varotojo įvestą informaciją ir
     * išsiunčia naujo kambario užklausą serveriui.
     *
     * @param pavadinimas -> kambario pavadinimas.
     * @param pradineZinute -> žinutė nurodyta kuriant kambario kurimo lange.
     * 		vartotojui leidžiama pradinės žinutės nerašyti.
     */
    private void kambarioUzklausa(String pavadinimas, String pradineZinute) {
        if (m_pavadinimas.getText().trim().isEmpty()) {
            klaida("Kambario pavadinimas negali būti tuščias!");
            return;
        }
        if (!pradineZinute.isEmpty()) m_klientas.siuskZinute("<NK>" + pavadinimas + "<KZ>" + pradineZinute);
        else
            m_klientas.siuskZinute("<NK>" + pavadinimas);
        progressBar.setVisible(true);
        progressBar.setValue(50);
        btnKurtiKam.setEnabled(false);
    }
}
