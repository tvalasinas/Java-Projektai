package com.menotyou.JC.Serveris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class ServerioKlientas extends Thread {

	private Socket prieiga;
	private String vardas;
	private Serveris serveris;
	private PrintWriter rasymas;
	private BufferedReader skaitymas;
	private ArrayList<String> zinuciuEile = new ArrayList<String>();
	private boolean prisijunges = false;

	public ServerioKlientas(Socket prieiga, Serveris serveris) throws IOException {
		this.prieiga = prieiga;
		this.serveris = serveris;
		rasymas = new PrintWriter(new OutputStreamWriter(prieiga.getOutputStream()), true);
		skaitymas = new BufferedReader(new InputStreamReader(prieiga.getInputStream()));
		prisijunges = true;
	}

	public boolean Prisijungimas() {
		try {
			String eilute = skaitymas.readLine();
			if(eilute.startsWith("/NV/")){
				vardas = eilute.substring(4);
				System.out.println("Prisijunge naujas vartotojas: " + vardas);
				this.setName("Klientas [" + vardas + "]");
				return true;
			} else{
				System.out.println("Gauta netiketa zinute: " +  eilute + "\n Klientas atmestas");
				atsijunk();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void run() {
		try {
			while (!isInterrupted()) {
				if(skaitymas.ready()){
					String zinute = skaitymas.readLine();
					pirminisApdorojimas(zinute);
				} else if(zinuciuEile.size() > 0){
					System.out.println("Zinute siunciama vartotojui" + vardas);
					rasymas.println(zinuciuEile.remove(0));
					System.out.println("Zinute issiusta!");
				}
				if(prieiga.isClosed())
					throw new IOException();
					
			}
		} catch (IOException e) {
			System.out.println("Nutruko ry�ys...");
		}
		atsijunk(false, false);
	}
	public synchronized void siuskZinute(String zinute){
		System.out.println("I kliento " + vardas + " zinuciu eile prideta zinute: " + zinute);
		zinuciuEile.add(zinute); 
	}
	public String gaukVarda(){
		return vardas;
	}
	
	public synchronized void atsijunk(boolean isspirtas, boolean pranesti){
		this.serveris.pasalinkKlienta(this, isspirtas, pranesti);
		try {
			this.prieiga.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public synchronized void atsijunk(){
		if(!this.prieiga.isClosed()){
			try {
				this.prieiga.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void pirminisApdorojimas(String zinute){
		if(zinute.startsWith("/K/")){
			System.out.println("Serveris gavo zinute: " + zinute);
			zinute = zinute.substring(3);
			String kambarys = zinute.split("/Z/")[0];
			System.out.println("Zinute skirta kambariui: " + kambarys);
			zinute = zinute.split("/Z/")[1];
			Kambarys k = serveris.gaukKambari(kambarys);
			if(k != null)
				k.apdorokZinute(this, zinute);
			else
				System.out.println("Deja kambarys pavadinimu " + kambarys + " nebuvo rastas.");
		}
	}

}
