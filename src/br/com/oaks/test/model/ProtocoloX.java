package br.com.oaks.test.model;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



// TODO: Auto-generated Javadoc
/**
 * The Class ProtocoloX.
 * 
 * @author Ciro Lacerda
 */
public class ProtocoloX {

	
	/** The tabela1. */
	private static Map<String, String> tabela1 = new HashMap<String, String>();
	
	/** The tabela1 inverso. */
	private static Map<String, String> tabela1Inverso = new HashMap<String, String>();
	
	// VARIAVEIS PARA TEST
	/** The Constant MESSAGE. 
	private static final byte[] MESSAGE = { (byte) 0xC6, 0x57, 0x54,
			(byte) 0x95, 0x5E, (byte) 0x9E, 0x6B, (byte) 0xC6, 0x55, 0x17,
			0x55, 0x52, (byte) 0x9E, 0x21 };

	/** The Constant OK. 
	private static final byte[] OK = { (byte) 0xC6, 0x57, 0x55, 0x7A, 0x7A,
			(byte) 0x9E, 0x21 };
	
	/** The Constant ERR. 
	private static final byte[] ERR = { (byte) 0xC6, 0x52, (byte) 0xD7, 0x45,
			(byte) 0xD2, (byte) 0x9E, 0x21 }; */

	
	
	/**
	 * Decodificar msg.
	 *
	 * @param mensagemByte the mensagem byte
	 * @return the string
	 */
	public static String decodificarMsg(byte[] mensagemByte) {
		
		byte [] pacoteDesmontado;
		
		pacoteDesmontado =  desmontaPacote(mensagemByte);
		
		System.out.println(" ");
		System.out.println("---  DECODIFICADANDO MSG  --- ");
		// Imprime pacote desmontado 
		for (byte theByte : pacoteDesmontado) {
			System.out.print(String.format("0x%02X ", theByte));

		}
		
		String strBinario = paraBinario(pacoteDesmontado);
		
		System.out.println(" ");
		// Imprime pacote desmontado em binario
		System.out.print(strBinario);
		
		ArrayList<String> blocos5Bits;
		
		blocos5Bits = agruparBlocos5Bits(strBinario);
		
		System.out.println(" ");
		// Imprime pacote desmontado em array blocos de 5 bits
		System.out.println(blocos5Bits);
		
		// Imprime tabela 1 e tabela 1 inverso
		montarTabela1();
		System.out.println(tabela1);
					
		montarTabela1Inverso(tabela1);
		System.out.println(tabela1Inverso);
		
		String strConvertido = para4bitsTabela1Inverso(blocos5Bits);
		
		System.out.println(" ");
		System.out.println("**** MSG DECODIFICADA ASCII **** ");
		
		byte [] byteMsgConvertida = paraByte(strConvertido);
		
		String strASCII = paraASCII(byteMsgConvertida);
		
		System.out.println(strASCII +"<-Tamanho:"+strASCII.length());
		
		System.out.println(" ");
		System.out.println("**** REMOVER ESPAÇO **** ");
		
		strASCII = removeEspaco(strASCII);
		
		System.out.println(strASCII +"<-Tamanho:"+strASCII.length());
		
		
		return strASCII;
		

		
	}
	
	/**
	 * Codificar msg.
	 *
	 * @param mensagemASCII the mensagem ascii
	 * @return the byte[]
	 */
	public static byte[] codificarMsg(String mensagemASCII) {
		System.out.println("---- CODIFICANDO MSG ---- ");

		for (byte theByte : paraVetorByte(mensagemASCII)) {
			System.out.print(String.format("0x%02X ", theByte));

		}

		String strBinario = paraBinario(paraVetorByte(mensagemASCII));
		System.out.println(strBinario);

		System.out.println(agruparBlocos4Bits(strBinario));

		System.out.println(" ");

		String strConvertido = para5bitsTabela1(agruparBlocos4Bits(strBinario));
		System.out.println(strConvertido);

		System.out.println(" ");

		for (byte theByte : paraByte(strConvertido)) {
			System.out.print(String.format("0x%02X ", theByte));

		}

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("**** MSG CODIFICADA **** ");

		for (byte theByte : montarPacote(paraByte(strConvertido))) {
			System.out.print(String.format("0x%02X ", theByte));

		}
		
		return montarPacote(paraByte(strConvertido));

	}
	
	/**
	 * Desmonta pacote.
	 *
	 * @param msgPacote the msg pacote
	 * @return the byte[]
	 */
	public static byte [] desmontaPacote(byte [] msgPacote){
		
		byte[] pacoteBytes = new byte[10];
		
		int posicao = 0;
		for (int i = 0; i < msgPacote.length; i++) {

			// Se for inicio do pacote
			if (msgPacote[i] == (byte) 0xC6) {
				int j = i + 1;

				

				// Enquanto não for o final do pacote
				while (msgPacote[j] != (byte) 0x6B) {

					pacoteBytes[posicao] = msgPacote[j];
					posicao++;
					j++;
					i = j;

					// Se chegou no final da transmissão
					if (msgPacote[j] == (byte) 0x21) {

						// Sai do laço
						break;
					}

				}
				

			}

		}
		
		return pacoteBytes;

		
		
	}
	
	/**
	 * Metodo monta mensagem de bytes em pacote do protocoloX.
	 *
	 * @param msgBytes the msg bytes
	 * @return the byte[]
	 */
	public static byte[] montarPacote(byte[] msgBytes) {

		// Instancia vetor de bytes conforme o tamanho do vetor msgBytes para
		// inserir Start pkt, End pkt e End
		byte[] pacoteBytes = new byte[(msgBytes.length / 5) * 7];

		int inicioPkt = 0;
		int finalPkt = 6;
		int posicao = 0;

		for (int i = 0; i < pacoteBytes.length; i++) {

			if (i == inicioPkt) {
				
				// Se for inicio do pacote insere Start packet
				pacoteBytes[i] = (byte) 0xC6;

				inicioPkt += finalPkt + 1;

			} else if (i == finalPkt) {
				
				// Se for fim do pacote insere End Transmission
				if (i == pacoteBytes.length -1 ) {

					pacoteBytes[i] = (byte) 0x21;
					
				}else{ // Senao insere End Packet
					
					pacoteBytes[i] = (byte) 0x6B;
					
					finalPkt += inicioPkt;
				}
			}  else if (posicao <= msgBytes.length) {
                
				// Senao for inicio ou nem final do pacote insere mensagem byte
				pacoteBytes[i] = msgBytes[posicao];

				posicao++;
			}

		}

		return pacoteBytes;

	}
	
	/**
	 * Metodo monta o inverso da tabela1 em HashMap .
	 *
	 * @param tabela1 the tabela1
	 */
	public static void montarTabela1Inverso(Map< String, String> tabela1){
		
		for( Map.Entry<String, String> entrada : tabela1.entrySet()){
		
		tabela1Inverso.put( entrada.getValue(), entrada.getKey());
		
		}
		
		
	}

	/**
	 * Metodo monta a tabela1 em HashMap.
	 */
	public static void montarTabela1() {

		tabela1.put("0000", "11110");
		tabela1.put("0001", "01001");
		tabela1.put("0010", "10100");
		tabela1.put("0011", "10101");
		tabela1.put("0100", "01010");
		tabela1.put("0101", "01011");
		tabela1.put("0110", "01110");
		tabela1.put("0111", "01111");
		tabela1.put("1000", "10010");
		tabela1.put("1001", "10011");
		tabela1.put("1010", "10110");
		tabela1.put("1011", "10111");
		tabela1.put("1100", "11010");
		tabela1.put("1101", "11011");
		tabela1.put("1110", "11100");
		tabela1.put("1111", "11101");

	}
	
	/**
	 * Metodo agrupa string de binarios em blocos de 4 bits.
	 *
	 * @param strBits the str bits
	 * @return the array list
	 */
	public static ArrayList<String> agruparBlocos4Bits(String strBits) {

		ArrayList<String> listaBlocos = new ArrayList<String>();
		
		StringBuilder strb = new StringBuilder();

		// Percorre a string char por char
		int inicio = 0;
		for (int i = 0; i <= strBits.length(); i++) {
			
			// Divide a String em blocos de 4 bits
			if (i % 4 == 0) {
				
				// Copia 4 bits em array list
				if (i > 0) {

					strb.append(strBits.substring(inicio, i));

					listaBlocos.add(strb.toString());

					strb.delete(0, strb.length());
					
					inicio = i;

				}
			}

		}
		
		return listaBlocos;
	}

	/**
	 * Metodo agrupa string de binarios em blocos de 5 bits.
	 *
	 * @param strBits the str bits
	 * @return the array list
	 */
	public static ArrayList<String> agruparBlocos5Bits(String strBits) {

		ArrayList<String> listaBlocos = new ArrayList<String>();
		
		StringBuilder strb = new StringBuilder();

		// Percorre a string char por char
		int inicio = 0;
		for (int i = 0; i <= strBits.length(); i++) {
			
			// Divide a String em blocos de 5 bits
			if (i % 5 == 0) {
				
				// Copia 5 bits em array list
				if (i > 0) {

					strb.append(strBits.substring(inicio, i));

					listaBlocos.add(strb.toString());

					strb.delete(0, strb.length());
					
					inicio = i;

				}
			}

		}
		
		return listaBlocos;
	}

	/**
	 * Metodo converte vetor de bytes em uma string de binario.
	 *
	 * @param mensagemByte
	 *            the mensagem byte
	 * @return the string builder
	 */
	public static String paraBinario(byte[] mensagemByte) {

		StringBuilder strb = new StringBuilder();

		for (int i = 0; i < mensagemByte.length; i++) {

			strb.append(String.format("%8s",
					Integer.toBinaryString(mensagemByte[i] & 0xFF)).replace(
					' ', '0'));

		}

		return strb.toString();

	}
	
	/**
	 * Metodo converte um vetor de bytes para string ASCII.
	 *
	 * @param mensagemByte the mensagem byte
	 * @return the string
	 */
	public static String paraASCII (byte [] mensagemByte){
		
		StringBuilder strb = new StringBuilder();
		
		for (byte theByte : mensagemByte) {
			
			strb.append((char) theByte);
		}
		
		
		return strb.toString();
	}
	
	/**
	 * Metodo Remove espaço em branco no inicio e/ou no final de uma string.
	 *
	 * @param mensagemStr the mensagem str
	 * @return the string
	 */
	public static String removeEspaco(String mensagemStr){
					
		return mensagemStr.trim();
				
	}
	
	/**
	 * Metodo inverte mensagem de string.
	 *
	 * @param mensagemStr the mensagem str
	 * @return the string
	 */
	public static String inverteMensagem(String mensagemStr){
		
		StringBuilder strb = new StringBuilder(mensagemStr);
		
		
		
		return strb.reverse().toString();
		
	}
	
	/**
	 * Metodo converte uma mensagem de string para vetor de bytes.
	 *
	 * @param mensagemStr the mensagem str
	 * @return the byte[]
	 */
	public static byte [] paraVetorByte(String mensagemStr){
		
		StringBuilder strb = new StringBuilder();
		
		byte [] listaBytes;
		
		
		if(mensagemStr.length()%4 != 0){
			
			strb.append(mensagemStr);
			
			strb.append(" ");
			
	        listaBytes = new byte [strb.length()];
			
		}else{
			
			strb.append(mensagemStr);
			
			listaBytes = new byte [strb.length()];
							
			}
		
		for (int i = 0; i < (strb.length()); i++) {
			
			listaBytes[i] = (byte)(int)Integer.valueOf(strb.charAt(i));
			
		}
		
		return listaBytes;
		
		
	}
	
	/**
	 * Metodo converte uma string de binario para vetor de bytes.
	 *
	 * @param strBits the str bits
	 * @return the byte[]
	 */
	public static byte [] paraByte(String strBits){
		
				
		byte [] listaBytes = new byte [strBits.length()/8];
		
		int inicio = 0;
		int pos = 0;
		for (int i = 0; i <= strBits.length(); i++) {
			
			if(i % 8 == 0){
				
				if(i > 0){
					
					listaBytes[pos] = (byte)(int)Integer.valueOf(strBits.substring(inicio, i), 2);
					
					pos++;
					inicio = i;
				}
				
			}
			
			
		}
		
		
		return listaBytes;
	}
	
	/**
	 * Metodo transforma lista de blocos 4-bits para blocos de 5-bits usando a tabela1.
	 *
	 * @param listaBlocos4Bits the lista blocos4 bits
	 * @return the string
	 */
	public static String para5bitsTabela1 (ArrayList<String> listaBlocos4Bits){
		
		StringBuilder strb = new StringBuilder();
		
		for (String bloco4bits : listaBlocos4Bits) {
			
			if(tabela1.containsKey(bloco4bits)){
				
				strb.append(tabela1.get(bloco4bits));
			}
			
		}
		
		return strb.toString();
	}
	
	/**
	 * Metodo transforma lista de blocos 5-bits para blocos de 4-bits usando a tabela1 inverso.
	 *
	 * @param listaBlocos5Bits the lista blocos5 bits
	 * @return the string
	 */
	public static String para4bitsTabela1Inverso (ArrayList<String> listaBlocos5Bits){
		
		StringBuilder strb = new StringBuilder();
		
		for (String bloco5bits : listaBlocos5Bits) {
			
			if(tabela1Inverso.containsKey(bloco5bits)){
				
				strb.append(tabela1Inverso.get(bloco5bits));
			}
			
		}
		
		return strb.toString();
	}
	
	

	// Metodo MAIN para teste
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		
		
		String ip = "xxx.xx.xxx.xx";
		Integer porta = 50005;
		
		ClienteSocket cliSocket = new ClienteSocket();
		
		cliSocket.abrirConexao(ip, porta );
		
		InputStream msgEntrada = cliSocket.getMsgEntrada();
		
		byte [] data = new byte[14];
		
		int contador = msgEntrada.read(data);
		System.out.println("<--- : MSG RECEBIDA [SERVER] --- ");
		 System.out.println("Total de Bytes:"+ contador +" [SERVER] Received: " );
		 
		 for (byte theByte : data) {
				System.out.print(String.format("0x%02X ", theByte));

			}
		
		OutputStream msgSaida = cliSocket.getMsgSaida();
		System.out.println(" ");
		

		String strASCII = decodificarMsg(data);
		
		System.out.println(" ");
		System.out.println("****  MSG DECODIFICADA  **** ");
		System.out.println(strASCII);
		System.out.println("--------------------------------");

		
        // Imprime array pacote em decimal
		//System.out.println(Arrays.toString(pacoteAux));
		
		

		
		
		System.out.println(" ");
		
		System.out.println(" ");
		
		
			
			
			
			System.out.println("**** MSG INVERTIDA **** ");
			System.out.println(inverteMensagem(strASCII));
			
			System.out.println(" ");
			
			System.out.println("---- CODIFICANDO MSG ---- ");
			
			for (byte theByte : paraVetorByte(inverteMensagem(strASCII))) {
				System.out.print(String.format("0x%02X ", theByte));

			}
			
			
		String strBinario =	paraBinario(paraVetorByte(inverteMensagem(strASCII)));
		System.out.println(strBinario);
		
		System.out.println(agruparBlocos4Bits(strBinario));
		
		System.out.println(" ");
		
		String strConvertido = para5bitsTabela1(agruparBlocos4Bits(strBinario));
		System.out.println(strConvertido);
		
		System.out.println(" ");
		
		for (byte theByte : paraByte(strConvertido)) {
			System.out.print(String.format("0x%02X ", theByte));

		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("**** MSG CODIFICADA **** ");
		
		for (byte theByte : montarPacote( paraByte(strConvertido))) {
			System.out.print(String.format("0x%02X ", theByte));

		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("--- Enviando MSG CODIFICADA para o [SERVER]: --->");
		
		// Envia MSG Pacote para o Servidor
		msgSaida.write(montarPacote( paraByte(strConvertido)));
		
		System.out.println(" ");
		System.out.println("<--- : MSG RECEBIDA [SERVER] --- ");
		System.out.println(" ");
		
		byte [] msgRecebida = new byte[7];
		
		contador = msgEntrada.read(msgRecebida);
		
		 System.out.println("Total de Bytes:"+ contador +" [SERVER] Received: " );
		 
		 for (byte theByte : msgRecebida) {
				System.out.print(String.format("0x%02X ", theByte));

			}
		 
		 System.out.println(" ");
			
			System.out.println(decodificarMsg(msgRecebida));
		 
		

			msgEntrada.close();
			msgSaida.close();
			
			cliSocket.fecharConexao();
			
			

		
	}	

}
