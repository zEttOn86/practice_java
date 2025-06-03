package com.kitsutsuki.example.digestvaluecalculator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DigestValueCalculator {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		DigestValueCalculator calculator = new DigestValueCalculator();
		calculator.run();
	}

	public void run() {
		while (true) {
			showMenu();
			String choice = scanner.nextLine().trim();
			switch (choice) {
			case "1":
				calcDigestValueMd5Sum();
				break;
			case "6":
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}

	private void showMenu() {
		System.out.println("====Digest Value Calculator===");
		System.out.println("1.Calculate the digest value with ");
		System.out.println("6.Exit");
		System.out.print("Choose an option:");
	}

	private void calcDigestValueMd5Sum() {
		System.out.println("Enter the file path to be calculated the digest value:");
		String filepath = scanner.nextLine();
		Path path = Paths.get(filepath);
		byte[] hash = null;
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// https://qiita.com/yasushi-jp/items/ac8c7ead98794aed9905
		// https://unageanu.hatenablog.com/entry/20070827/1188195493
		
//		try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path))){
//			int data;
////			while((data = bis.read()) != -1) {
////				System.out.println((char) data);
////			}
////			
//			byte[] buffer = new byte[8192];
//			int bytesRead;
//			while((bytesRead = bis.read(buffer)) != -1) {
//				String chunk = new String(buffer, 0, bytesRead);
//				System.out.println(chunk);
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		try {
			DigestInputStream dis = new DigestInputStream(new BufferedInputStream(Files.newInputStream(path)),
					md);
			while (dis.read() != -1) {

			}
			
			hash = md.digest();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		for(byte b: hash) {
			String hex = String.format("%02x", b);
			sb.append(hex);
		}
		System.out.println(sb.toString());
//		return sb.toString();

	}

}
