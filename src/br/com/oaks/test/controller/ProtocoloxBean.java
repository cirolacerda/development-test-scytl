package br.com.oaks.test.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;

import br.com.oaks.test.service.ProtocoloXService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProtocoloxBean.
 * 
 *  @author Ciro Lacerda
 */
@ManagedBean
public class ProtocoloxBean {

	/** The ip. */
	String ip = "xxx.xx.xxx.xx";
	
	/** The porta. */
	Integer porta = 50005;

	/** The msg conexao. */
	String msgConexao;
	
	/** The msg pacote enviado. */
	String msgPacoteEnviado;

	/** The msg decodificada. */
	String msgDecodificada;

	/** The msg invertida. */
	String msgInvertida;
	
	/** The msg confirmacao. */
	String msgConfirmacao;

	/** The pacote recebido. */
	byte[] pacoteRecebido;

	/** The pacote recebido str. */
	StringBuilder pacoteRecebidoStr = new StringBuilder();

	/** The msg codificada str. */
	StringBuilder msgCodificadaStr = new StringBuilder();
	
	/** The msg confirmacao str. */
	StringBuilder msgConfirmacaoStr = new StringBuilder();
	

	/**
	 * Conectar.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void conectar() throws IOException {

		msgConexao = ProtocoloXService.conectar(ip, porta);

		pacoteRecebido = ProtocoloXService.receberPacote();
		
		

		for (byte theByte : pacoteRecebido) {

			pacoteRecebidoStr.append(String.format("0x%02X ", theByte));

		}

		msgDecodificada = ProtocoloXService.decodificarMsg(pacoteRecebido);

		msgInvertida = ProtocoloXService.inverterMsg(msgDecodificada);

		byte[] msgCodificada;
		msgCodificada = ProtocoloXService.codificarMsg(msgInvertida);

		for (byte theByte : msgCodificada) {

			msgCodificadaStr.append(String.format("0x%02X ", theByte));

		}
		
		msgPacoteEnviado = ProtocoloXService.enviarPacote(msgCodificada);
		
				
		byte [] msgRecebida ;
		msgRecebida = ProtocoloXService.receberPacoteConfirmacao();

		for (byte theByte : msgRecebida) {

			msgConfirmacaoStr.append(String.format("0x%02X ", theByte));

		}
	}
	
	
	
	

	/**
	 * Gets the msg confirmacao str.
	 *
	 * @return the msg confirmacao str
	 */
	public StringBuilder getMsgConfirmacaoStr() {
		return msgConfirmacaoStr;
	}


	/**
	 * Sets the msg confirmacao str.
	 *
	 * @param msgConfirmacaoStr the new msg confirmacao str
	 */
	public void setMsgConfirmacaoStr(StringBuilder msgConfirmacaoStr) {
		this.msgConfirmacaoStr = msgConfirmacaoStr;
	}

	/**
	 * Gets the msg confirmacao.
	 *
	 * @return the msg confirmacao
	 */
	public String getMsgConfirmacao() {
		return msgConfirmacao;
	}

	/**
	 * Sets the msg confirmacao.
	 *
	 * @param msgConfirmacao the new msg confirmacao
	 */
	public void setMsgConfirmacao(String msgConfirmacao) {
		this.msgConfirmacao = msgConfirmacao;
	}

	/**
	 * Gets the msg pacote enviado.
	 *
	 * @return the msg pacote enviado
	 */
	public String getMsgPacoteEnviado() {
		return msgPacoteEnviado;
	}

	/**
	 * Sets the msg pacote enviado.
	 *
	 * @param msgPacoteEnviado the new msg pacote enviado
	 */
	public void setMsgPacoteEnviado(String msgPacoteEnviado) {
		this.msgPacoteEnviado = msgPacoteEnviado;
	}

	/**
	 * Gets the msg codificada str.
	 *
	 * @return the msg codificada str
	 */
	public StringBuilder getMsgCodificadaStr() {
		return msgCodificadaStr;
	}

	/**
	 * Sets the msg codificada str.
	 *
	 * @param msgCodificadaStr the new msg codificada str
	 */
	public void setMsgCodificadaStr(StringBuilder msgCodificadaStr) {
		this.msgCodificadaStr = msgCodificadaStr;
	}

	/**
	 * Gets the msg invertida.
	 *
	 * @return the msg invertida
	 */
	public String getMsgInvertida() {
		return msgInvertida;
	}

	/**
	 * Sets the msg invertida.
	 *
	 * @param msgInvertida the new msg invertida
	 */
	public void setMsgInvertida(String msgInvertida) {
		this.msgInvertida = msgInvertida;
	}

	/**
	 * Gets the msg decodificada.
	 *
	 * @return the msg decodificada
	 */
	public String getMsgDecodificada() {
		return msgDecodificada;
	}

	/**
	 * Sets the msg decodificada.
	 *
	 * @param msgDecodificada the new msg decodificada
	 */
	public void setMsgDecodificada(String msgDecodificada) {
		this.msgDecodificada = msgDecodificada;
	}

	/**
	 * Gets the pacote recebido str.
	 *
	 * @return the pacote recebido str
	 */
	public StringBuilder getPacoteRecebidoStr() {
		return pacoteRecebidoStr;
	}

	/**
	 * Sets the pacote recebido str.
	 *
	 * @param pacoteRecebidoStr the new pacote recebido str
	 */
	public void setPacoteRecebidoStr(StringBuilder pacoteRecebidoStr) {
		this.pacoteRecebidoStr = pacoteRecebidoStr;
	}

	/**
	 * Gets the pacote recebido.
	 *
	 * @return the pacote recebido
	 */
	public byte[] getPacoteRecebido() {
		return pacoteRecebido;
	}

	/**
	 * Sets the pacote recebido.
	 *
	 * @param pacoteRecebido the new pacote recebido
	 */
	public void setPacoteRecebido(byte[] pacoteRecebido) {
		this.pacoteRecebido = pacoteRecebido;
	}

	/**
	 * Gets the msg conexao.
	 *
	 * @return the msg conexao
	 */
	public String getMsgConexao() {
		return msgConexao;
	}

	/**
	 * Sets the msg conexao.
	 *
	 * @param msgConexao the new msg conexao
	 */
	public void setMsgConexao(String msgConexao) {
		this.msgConexao = msgConexao;
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
	 * @param ip the new ip
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
	 * @param porta the new porta
	 */
	public void setPorta(Integer porta) {
		this.porta = porta;
	}

}
