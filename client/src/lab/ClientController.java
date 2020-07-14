package lab;

import lab.Commands.CommandInvoker;
import lab.Commands.CommandReceiver;
import lab.Commands.ConcreteCommands.*;
import lab.Commands.SerializedCommands.Message;
import lab.Commands.Utils.Creaters.ElementCreator;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class ClientController implements Runnable {
	private static final int BUFF_SIZE = 1000000;
	String hostname = null;
	int port = -1;

	public ClientController(String hostname, String port) {
		this.hostname = hostname;
		this.port = Integer.parseInt(port);
	}

	public void run() {
		if(hostname == null || port == -1){
			System.out.println("Класс не инициализирован");
			throw new RuntimeException("Не инициализирован hostname и port");
		}
		else {
			try {
				Selector selector = Selector.open();
				SocketChannel connectionClient = SocketChannel.open();
				connectionClient.connect(new InetSocketAddress("localhost", port));
				connectionClient.configureBlocking(false);
				//connectionClient.register(selector, SelectionKey.OP_CONNECT);
				connectionClient.register(selector, SelectionKey.OP_WRITE);
				int reconect_schetchick = 1;

				ElementCreator elementCreator = new ElementCreator();
				CommandInvoker commandInvoker = new CommandInvoker();
				CommandReceiver commandReceiver = new CommandReceiver(commandInvoker, elementCreator);

				commandInvoker.register("help", new Help(commandReceiver));
				commandInvoker.register("add", new Add(commandReceiver));
				commandInvoker.register("info", new Info(commandReceiver));
				commandInvoker.register("show", new Show(commandReceiver));
				commandInvoker.register("update", new Update(commandReceiver));
				commandInvoker.register("remove_by_id", new RemoveByID(commandReceiver));
				commandInvoker.register("remove_by_description", new RemoveByDescription(commandReceiver));
				commandInvoker.register("filter_contains_name", new FilterContainsName(commandReceiver));
				commandInvoker.register("reorder", new Reorder(commandReceiver));
				commandInvoker.register("clear", new Clear(commandReceiver));
				commandInvoker.register("exit", new Exit(commandReceiver));
				commandInvoker.register("remove_greater", new RemoveGreater(commandReceiver));
				commandInvoker.register("remove_lower", new RemoveLower(commandReceiver));
				commandInvoker.register("execute_script", new ExecuteScript(commandReceiver));
				Scanner scanner = new Scanner(System.in);
				//session.closeSession();
				System.out.println("Введите help");
				while (true) {
					selector.select();

					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

					while (iterator.hasNext()) {
						SelectionKey key = (SelectionKey) iterator.next();
						//iterator.remove();
						if (key.isValid()) {
							SocketChannel client = (SocketChannel) key.channel();
							if (client != null) {
								try {
									/*if ((key.interestOps() & SelectionKey.OP_CONNECT) != 0) {
										try {
											if (client.finishConnect()) {
												key.interestOps(SelectionKey.OP_WRITE);
												client.register(selector, SelectionKey.OP_WRITE);
												System.out.println("Введите help");
											} else {
												System.out.println("В данный момент сервер не доступен, повторная попытка: " + reconect_schetchick);
												if (reconect_schetchick > 20) {
													System.exit(0);
												}
												selector = Selector.open();
												connectionClient = SocketChannel.open();
												connectionClient.configureBlocking(false);
												connectionClient.connect(new InetSocketAddress(hostname, port));
												connectionClient.register(selector, SelectionKey.OP_WRITE);
												reconect_schetchick++;
												sleep(1000);
												continue;
											}
										}
										catch (IOException e) {
											System.out.println("В данный момент сервер не доступен, повторная попытка: " + reconect_schetchick);
											if (reconect_schetchick > 20) {
												System.exit(0);
											}
											selector = Selector.open();
											connectionClient = SocketChannel.open();
											connectionClient.configureBlocking(false);
											connectionClient.connect(new InetSocketAddress(hostname, port));
											connectionClient.register(selector, SelectionKey.OP_WRITE);
											reconect_schetchick++;
											sleep(1000);
											continue;
										}
									}
									 */
									if ((key.interestOps() & SelectionKey.OP_WRITE) != 0) {
										try {
											System.out.print(">");
											Message message = commandInvoker.executeCommand(scanner.nextLine().trim().split(" "));
											if (message == null) {
												continue;
											} else {
												sendSocketObject(client, message);
												key.interestOps(SelectionKey.OP_READ);
												client.register(selector, SelectionKey.OP_READ);
												break;
											}
										} catch (IOException e){
											System.out.println("Повторное подлючение");
											//client.register(selector, SelectionKey.OP_CONNECT);
											//connectionClient.register(selector, SelectionKey.OP_CONNECT);
											try {
												selector = Selector.open();
												connectionClient = SocketChannel.open();
												connectionClient.connect(new InetSocketAddress("localhost", port));
												connectionClient.configureBlocking(false);
												//connectionClient.register(selector, SelectionKey.OP_CONNECT);
											}catch (Exception ignored){}
											key.interestOps(SelectionKey.OP_WRITE);
											client.register(selector, SelectionKey.OP_WRITE);
											continue;
										}
									}
									if ((key.interestOps() & SelectionKey.OP_READ) != 0) {
										Message message = getSocketObject(client);
										System.out.println(message.getString());
										key.interestOps(SelectionKey.OP_WRITE);
										client.register(selector, SelectionKey.OP_WRITE);
										sleep(500);
										continue;
									}
								} catch (IOException | NoSuchElementException | InterruptedException e) {
									System.out.println("Завершение работы.");
									client.close();
									e.printStackTrace();

									System.exit(0);
								}
							}
						}
					}
				}
			}catch (ConnectException e){
				System.out.println("Невозможно подключиться к данному хосту или порту");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Message getSocketObject(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
		ByteBuffer data = ByteBuffer.allocate(BUFF_SIZE);
		socketChannel.read(data);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		return (Message) objectInputStream.readObject();
	}

	private static void sendSocketObject(SocketChannel client, Message message) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(message);
		objectOutputStream.flush();
		client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
	}

}