package br.senac.tads.dsw;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exemplo obtido em https://www.baeldung.com/java-serversocket-simple-http-server
 * Alterado com apoio do Github Copilot para tratamento do request body
 *
 * @author fernando.tsuda
 */
public class SimpleWebServer {

	private final int port;
	private static final int THREAD_POOL_SIZE = 10;

	public SimpleWebServer(int port) {
		this.port = port;
	}

	public void start() throws IOException {

//		// PARA JAVA 21+ - usa virtual threads
//		ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();

		ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server started na porta " +  port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				threadPool.execute(() -> handleClient(clientSocket));
			}
		}
	}

	private void handleClient(Socket clientSocket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

			String requestLine = "";
			StringBuilder requestHeaders = new StringBuilder();
			boolean collectinRequestLine = true;

			// OBTENDO CABECALHOS DA REQUEST MESSAGE DO HTTP
			int contentLength = 0;
			String requestInputLine;
			while ((requestInputLine = in.readLine()) != null) {
				if (collectinRequestLine) {
					requestLine = requestInputLine;
					collectinRequestLine = false;
					continue;
				}
				if (requestInputLine.isEmpty()) {
					break;
				}
				requestHeaders.append(requestInputLine).append("\r\n");
				if (requestInputLine.toLowerCase().startsWith("content-length:")) {
					contentLength = Integer.parseInt(requestInputLine.split(":")[1].trim());
				}
			}
			String header = requestHeaders.toString();

			// OBTENDO CORPO DA REQUEST MESSAGE, BASEADO NO TAMANHO (CONTENT-LENGTH)
			String body = "";
			if (contentLength > 0) {
				char[] bodyChars = new char[contentLength];
				in.read(bodyChars, 0, contentLength);
				body = new String(bodyChars);
			}

			// RECONSTROI A REQUEST MESSAGE (PARA DEBUG/DIDATICO)
			String requestMessage = requestLine + "\r\n" + header + "\r\n" + body;
			System.out.println(requestMessage);

			// PROCESSAMENTO - GERAR CORPO DA RESPONSE
			String bodyOutTemplate = """
				<!doctype html>
				<html>
					<head>
						<meta charset="UTF-8">
						<title>TADS DSW</title>

					</head>
					<body>
						<h1>Exemplo Servidor Web Java</h1>
						<p>Teste alteração</p>
						<hr>
						<h2>Mensagem Request</h2>
						<pre>{0}</pre>
						<hr>
					</body>
				</html>
				""";
			String responseBody = MessageFormat.format(bodyOutTemplate.replace("'", "''"), requestMessage).trim();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

			int length = responseBody.length();

			// GERA RESPONSE LINE + CABECALHOS DA RESPONSE
			out.write("HTTP/1.1 200 OK\r\n");
			out.write("Date: " + formatter.format(now) + "\r\n");
			out.write("Server: Custom Server\r\n");
			out.write("Content-Type: text/html\r\n");
			out.write("Content-Length: " + length + "\r\n");
			out.write("\r\n");

			// ADICIONA CORPO NA RESPONSE
			out.write(responseBody);
		} catch (IOException ex) {
			System.err.println("Error handling client " + ex.getMessage());
		} finally {
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException ex) {
					System.err.println("Error handling client " + ex.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int port = 8080;
		SimpleWebServer server = new SimpleWebServer(port);
		server.start();
	}
}