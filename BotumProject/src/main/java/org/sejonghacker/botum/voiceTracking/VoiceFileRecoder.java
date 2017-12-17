package org.sejonghacker.botum.voiceTracking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VoiceFileRecoder {
	private String path;
	private String fileNo;
	private String owner;
	private String contextPath;

	public VoiceFileRecoder(String contextPath, String fileNo, String owner) {
		super();
		this.contextPath = contextPath;
		this.fileNo = fileNo;
		this.owner = owner;
		this.path = contextPath + "\\" + owner;
	}

	private boolean mkFile() {

		return false;
	}
	
	public String recodeAndRead(String sentence) {
		FileReader fr = null;
		FileWriter fw = null;
		
		BufferedReader br = null;
		BufferedWriter bw = null;

		char[] cbuf = new String().toCharArray() ;
		try {
			File file = new File(path);
			if(file.exists()) {
				file = new File(path + "\\" + fileNo);
				if(!file.exists()) {
					file.createNewFile();
				}
			}else {
				file.mkdir();
				file = new File(path + "\\" + fileNo);
				file.createNewFile();
			}
			
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			bw.write(sentence);
			bw.flush();
			
			br.read(cbuf);
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cbuf.toString();
	}
	
	public String read(String owner, String fileNo) {
		
		return null;
	}
	
	@Override
	public String toString() {
		return "VoiceFileRecoder [path=" + path + ", fileNo=" + fileNo + ", owner=" + owner + ", contextPath="
				+ contextPath + "]";
	}
}
