package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {
	
		Locale.setDefault(Locale.US);
		
		List<Product> products = new ArrayList<>();
		
		try (Scanner sc = new Scanner(System.in)) {		
			System.out.print("Digite o caminho do arquivo:\n> ");
			String sourceFileStr = sc.nextLine(); // caminho completo do arquivo
			String sourceFolderStr = new File(sourceFileStr).getParent(); // só o caminho
			boolean success = new File(sourceFolderStr + "/out").mkdir(); // crio a pasta out
			String finalTarget = sourceFolderStr + "/out/summary.csv"; // caminho completo e final pra onde vai o arquivo
			
			try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
				String line = br.readLine();
				while (line != null) {
					String[] lineSplitter = line.split(",");
					String name = lineSplitter[0];
					double price = Double.parseDouble(lineSplitter[1]);
					int quantity = Integer.parseInt(lineSplitter[2]);
					products.add(new Product(name, price, quantity));
					line = br.readLine();
				}		
				
				String created = success ? "Pasta out criada com sucesso." : "Pasta out já existe.";
				System.out.println(created);
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(finalTarget))) {
					for (Product product : products) {
					    bw.write(product.toString());
						bw.newLine();
					}
				}
				System.out.println("Arquivo criado com sucesso.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
