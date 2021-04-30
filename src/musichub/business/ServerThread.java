package musichub.business;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("unused")
	public void run() {
		try {
			// create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			String text_received = (String) input.readObject(); // read the object received through the stream and deserialize it
			System.out.println("Client whishes: " + text_received);

			//Student student = new Student(54321, "john.doe");
			//output.writeObject(student); // serialize and write the Student object to the stream

			File audio = new File("files\\"+ text_received +".wav"); // Upgrade -> faire en sorte que ce soit l'input du client ici
			if(audio == null) System.exit(0);

			System.out.print("\nOk, sending " + audio.getName() + "\n");
			byte[] b = new byte[4096];
			InputStream is = new FileInputStream(audio);
			OutputStream os = socket.getOutputStream();

			int i;
			while((i = is.read(b)) > 0) {
				os.write(b, 0, i);
			}
			//is.close();
			//os.close();

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}