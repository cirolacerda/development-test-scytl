package br.com.oaks.test.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.oaks.test.model.ClienteSocket;
import br.com.oaks.test.model.ProtocoloX;


// TODO: Auto-generated Javadoc
/**
 * The Class ProtocoloXService.
 * 
 *  @author Ciro Lacerda
 */
public class ProtocoloXService {

	/** The cliente skt. */
	static ClienteSocket clienteSkt = new ClienteSocket();
	
	/** The msg entrada. */
	static InputStream msgEntrada;
	
	/** The msg saida. */
	static OutputStream msgSaida;
	

	/**
	 * Conectar.
	 *
	 * @param ip the ip
	 * @param porta the porta
	 * @return the string
	 */
	public static String conectar(String ip, Integer porta) {

		String msgConexao;

		msgConexao = clienteSkt.abrirConexao(ip, porta);

		return msgConexao;

	}

	/**
	 * Receber pacote.
	 *
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] receberPacote() throws IOException {

		msgEntrada = clienteSkt.getMsgEntrada();

		byte[] data = new byte[14];

		int contador = msgEntrada.read(data);

		System.out.println("<--- : MSG RECEBIDA [SERVER] --- ");
		System.out.println("Total de Bytes:" + contador
				+ " [SERVER] Received: ");

		for (byte theByte : data) {
			System.out.print(String.format("0x%02X ", theByte));

		}

		return data;

	}

	/**
	 * Decodificar msg.
	 *
	 * @param mensagemByte the mensagem byte
	 * @return the string
	 */
	public static String decodificarMsg(byte[] mensagemByte) {

		String msgDecodificadaASCII;

		msgDecodificadaASCII = ProtocoloX.decodificarMsg(mensagemByte);

		System.out.println(" ");
		System.out.println("****  MSG DECODIFICADA  **** ");
		System.out.println(msgDecodificadaASCII);
		System.out.println("--------------------------------");

		return msgDecodificadaASCII;

	}
	
	/**
	 * Codificar msg.
	 *
	 * @param mensagemASCII the mensagem ascii
	 * @return the byte[]
	 */
	public static byte[] codificarMsg(String mensagemASCII) {
		
		byte [] msgCodificada;
		
		msgCodificada =  ProtocoloX.codificarMsg(mensagemASCII);
		
		return msgCodificada;
		
	}
	
	/**
	 * Enviar pacote.
	 *
	 * @param pacoteMsg the pacote msg
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String enviarPacote(byte [] pacoteMsg) throws IOException{
		
		String msg = "Mensagem enviada para o Servidor!";
		
		msgSaida = clienteSkt.getMsgSaida();
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("--- Enviando MSG CODIFICADA para o [SERVER]: --->");
		
		// Envia MSG Pacote para o Servidor
		msgSaida.write(pacoteMsg);
		
		return msg;
	}
	
	/**
	 * Receber pacote confirmacao.
	 *
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte [] receberPacoteConfirmacao() throws IOException{
		
		System.out.println(" ");
		System.out.println("<--- : MSG RECEBIDA [SERVER] --- ");
		System.out.println(" ");
		
		byte [] msgRecebida = new byte[7];
		
		int contador = msgEntrada.read(msgRecebida);
		
		 System.out.println("Total de Bytes:"+ contador +" [SERVER] Received: " );
		 
		 for (byte theByte : msgRecebida) {
				System.out.print(String.format("0x%02X ", theByte));

			}
		 
		 return msgRecebida;
	}

	/**
	 * Inverter msg.
	 *
	 * @param msgDecodificadaASCII the msg decodificada ascii
	 * @return the string
	 */
	public static String inverterMsg(String msgDecodificadaASCII) {
		
		String mensagemInvertidaASCII;
		
		mensagemInvertidaASCII = ProtocoloX.inverteMensagem(msgDecodificadaASCII);

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("**** MSG INVERTIDA **** ");
		System.out.println(mensagemInvertidaASCII);
		
		return mensagemInvertidaASCII;
	}

}
