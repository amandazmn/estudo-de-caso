package visao;

import java.util.ArrayList;
import java.util.Scanner;

import controle.GeneDAO;
import modelo.Gene;

public class Principal {
	public static int menu() {
		Scanner leitura = new Scanner(System.in);
		System.out.println("            Menu            ");
		System.out.println("0 - Inserir gene");
		System.out.println("1 - Listar genes cadastrados");
		System.out.println("2 - Alterar sequência");
		System.out.println("3 - Excluir gene");
		System.out.println("4 - Exibir tabela");
		System.out.println("5 - Fechar");
		Integer opcao = Integer.valueOf(leitura.nextLine());
		return opcao;
	}

	public static void main(String[] args) {

		Scanner leitura = new Scanner(System.in);

		Integer opcao = Integer.MAX_VALUE;
		GeneDAO dao = GeneDAO.getInstancia();
		ArrayList<Gene> genes = dao.listarGenes();

		while (opcao != 5) {
			
			opcao = menu();

			switch (opcao) {
			case 0: {

				System.out.println("Tipo 1 - DNA para RNAm | 2 - DNA para RNAm para AA): ");
				String read = leitura.nextLine();
				Integer tipo = Integer.valueOf(read);

				System.out.println("Digita nome: ");
				String nome = leitura.nextLine();

				Gene gene = new Gene();
				gene.setNome(nome);
				System.out.println("Sequência: ");
				String sequencia = leitura.nextLine();
				gene.setSequencia(sequencia);
				switch (tipo) {
				case 1: {
					String mRNA = dao.getmRNA(gene.getSequencia());
					gene.setTraducao(mRNA);
				}
				case 2: {
					String AA = dao.getProteina(gene.getSequencia());
					gene.setTraducao(AA);
				}
				}
				boolean inseriu = dao.inserir(gene);
				if (inseriu == true) {
					System.out.println("Cadastrou o gene com sucesso.");
				} else {
					System.out.println("Erro ao cadastrar o gene.");
				}
				
				break;
			}
			
			
			case 1: {
				System.out.println("Listagem de genes cadastrados: ");
				for (Gene gen : genes) {
					System.out.println("\nNome: " + gen.getNome());
					System.out.println("Sequência: " + gen.getSequencia());
					System.out.println("Tradução: " + gen.getTraducao());
				}
				break;
			}
			
			
			case 2: {
				System.out.println("Nome do gene que deseja editar: ");
				String nome = leitura.nextLine();
				for (Gene gene : genes) {
					if (gene.getNome().equals(nome)) {
						System.out.println("Sequência: ");
						System.out.println("Número do códon     Códon");
						String sequencia = gene.getSequencia();
						Integer j = 0;
						for (int i = 0; i < sequencia.length(); i += 3) {
							System.out.println(j + "                   " + gene.getSequencia().substring(i, i + 3));
							j++;
						}
						j = 0;
						ArrayList<String> codons = new ArrayList<>();
						for (int i = 0; i < sequencia.length(); i += 3) {
							String codon = gene.getSequencia().substring(i, i + 3);
							codons.add(codon);
						}
						System.out.println("Quantidade de códons que deseja editar: ");
						Integer n = Integer.valueOf(leitura.nextLine());
						for (int i = 0; i < n; i++) {
							System.out.println("Número do códon que deseja editar: ");
							Integer nCodon = Integer.valueOf(leitura.nextLine());
							System.out.println("Códon: " + codons.get(nCodon));
							System.out.println("Novo códon: ");
							codons.set(nCodon, leitura.nextLine());
						}
						String novaSequencia = "";
						for (int i = 0; i < codons.size(); i++) {
							novaSequencia = novaSequencia + codons.get(i);
						}

						boolean alterou = dao.alterar(gene, novaSequencia);

						System.out.println("Nova sequência: " + gene.getSequencia());
						if (alterou == true) {
							System.out.println("Alterou a sequência com sucesso.");
						} else {
							System.out.println("Erro ao alterar a sequência.");
						}
					}
				}
				break;
			}
			
			
			case 3: {
				System.out.println("O nome do gene que deseja excluir:");
				String nome = leitura.nextLine();
				boolean end = false;
				for (Gene gene : genes) {
					while(end==false) {
					if (gene.getNome().equals(nome)) {
						end = true;
						System.out.println("Confirme as informações a serem excluidas");
						System.out.println("Nome: " + gene.getNome());
						System.out.println("Sequência: " + gene.getSequencia());
						System.out.println("Tradução: " + gene.getTraducao());
						System.out.println("\nVocê confirma a exclusão desses dados?\n1 - sim | 2 - não");
						Integer confirmacao = Integer.valueOf(leitura.nextLine());
						switch (confirmacao) {
						case 1: {
							boolean excluiu = genes.remove(gene);
							if (excluiu == true) {
								System.out.println("Excluiu gene com sucesso. ");
							}
							else {
								System.out.println("Erro ao excluir gene. ");
							}
							break;
						}
						case 2: {
							break;
						}
						}
					}	
				}
					break;
				}
				
				break;
			}
			
			
			case 4: {
				exibirTabela();
				break;
			}
			
			
			case 5: {
				leitura.close();
				break;
			}
			}
		}
	}

	private static void exibirTabela() {
		System.out.println("====================================================");
		System.out.println("----------------------------------------------------");
		System.out.println("                TABELA DE AMINOÁCIDOS               ");
		System.out.println("----------------------------------------------------");
		System.out.println("====================================================");
		System.out.println("AUG            Met(Metionina)        CÓDON DE INÍCIO");
		System.out.println("UAA UAG  UGA                         CÓDON DE PARADA");
		System.out.println("====================================================");
		System.out.println("UUU  UUC       Phe(Fenilalamina)                    ");
		System.out.println("====================================================");
		System.out.println("UUA  UUG  CUU\n" + "CUC  CUA  CUG  Leu(Leucina)     ");
		System.out.println("====================================================");
		System.out.println("AUU  AUC  AUA  Ile(Isoleucina)                      ");
		System.out.println("====================================================");
		System.out.println("GUU  GUC\n" + "GUA  GUG       Val(Valina)           ");
		System.out.println("====================================================");
		System.out.println("UCU  UCC  UCA\n" + "UCG  AGU  AGC  Ser(Serina)      ");
		System.out.println("====================================================");
		System.out.println("CCU  CCC\n" + "CCA  CCG       Pro(Prolina)          ");
		System.out.println("====================================================");
		System.out.println("ACU  ACC\n" + "ACA  ACG       Thr(Treonina)         ");
		System.out.println("====================================================");
		System.out.println("GCU  GCC\n" + "GCA  GCG       Ala(Alanina)          ");
		System.out.println("====================================================");
		System.out.println("UAU  UAC       Tyr(Tirosina)                        ");
		System.out.println("====================================================");
		System.out.println("CAU  CAC       His(Histidina)                       ");
		System.out.println("====================================================");
		System.out.println("CAA  CAG       Gln(Glutamina)                       ");
		System.out.println("====================================================");
		System.out.println("AAU  AAC       Asn(Aspargina)                       ");
		System.out.println("====================================================");
		System.out.println("AAA  AAG       Lys(Lisina)                          ");
		System.out.println("====================================================");
		System.out.println("GAU  GAC       Asp(Ácido aspártico)                 ");
		System.out.println("====================================================");
		System.out.println("GAA  GAG       Glu(Ácido glutâmino)                 ");
		System.out.println("====================================================");
		System.out.println("UGU  UGC       Cys(Cisteína)                        ");
		System.out.println("====================================================");
		System.out.println("UGG            Trp(Tripcofano)                      ");
		System.out.println("====================================================");
		System.out.println("CGU  CGC  CGA\n" + "CGG  AGA  AGG   Arg(Arginina)   ");
		System.out.println("====================================================");
		System.out.println("GGU  GGC\n" + "GGA  GGG        Gly(Glicina)         ");
		System.out.println("====================================================");

	}
}
