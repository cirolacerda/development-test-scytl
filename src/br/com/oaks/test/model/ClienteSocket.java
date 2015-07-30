package br.com.oaks.test.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * The Class ClienteSocket.
 * 
 * @author Ciro Lacerda
 */
public class ClienteSocket {

	/** The socket cliente. */
	private Socket socketCliente;

	/** The ip. */
	private String ip;

	/** The porta. */
	private Integer porta;

	/** The msg entrada. */
	private InputStream msgEntrada;

	/** The msg saida. */
	private OutputStream msgSaida;

	/**
	 * Instantiates a new cliente socket.
	 */
	public ClienteSocket() {

	}

	/**
	 * Abrir conexao.
	 *
	 * @param ip
	 *            the ip
	 * @param porta
	 *            the porta
	 */
	public String abrirConexao(String ip, Integer porta) {
		try {
			
			String msgConectado = "O cliente se conectou ao servidor!";
			
						
			// Conectar no servidor pelo ip e porta
			this.socketCliente = new Socket(ip, porta);

			// Canal para receber mensagens do servidor
			this.msgEntrada = socketCliente.getInputStream();

			// Canal para enviar mensagens para o servidor
			this.msgSaida = socketCliente.getOutputStream();

			System.out.println(msgConectado);
			
			return msgConectado;

		} catch (UnknownHostException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return e.toString();
		}
	}

	public String fecharConexao() {
		
		String msgConectado = "Conexao com o  servidor encerrada !";
		
		try {
			// Fecha os canais de receber e enviar mensagens para o servidor
			msgEntrada.close();
			msgSaida.close();
			
			// Fecha o socket do cliente
			socketCliente.close();
			
			return msgConectado;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return e.toString();
		}
		

	}
	
	// TODO: Auto-generated Javadoc
	/**
	 * Gets the socket cliente.
	 *
	 * @return the socket cliente
	 */
	public Socket getSocketCliente() {
		return socketCliente;
	}

	/**
	 * Sets the socket cliente.
	 *
	 * @param socketCliente
	 *            the new socket cliente
	 */
	public void setSocketCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the porta.
	 *
	 * @return the porta
	 */
	public Integer getPorta() {
		return porta;
	}

	/**
	 * Sets the porta.
	 *
	 * @param porta
	 *            the new porta
	 */
	public void setPorta(Integer porta) {
		this.porta = porta;
	}

	/**
	 * Gets the msg entrada.
	 *
	 * @return the msg entrada
	 */
	public InputStream getMsgEntrada() {
		return msgEntrada;
	}

	/**
	 * Sets the msg entrada.
	 *
	 * @param msgEntrada
	 *            the new msg entrada
	 */
	public void setMsgEntrada(InputStream msgEntrada) {
		this.msgEntrada = msgEntrada;
	}

	/**
	 * Gets the msg saida.
	 *
	 * @return the msg saida
	 */
	public OutputStream getMsgSaida() {
		return msgSaida;
	}

	/**
	 * Sets the msg saida.
	 *
	 * @param msgSaida
	 *            the new msg saida
	 */
	public void setMsgSaida(OutputStream msgSaida) {
		this.msgSaida = msgSaida;
	}

}
