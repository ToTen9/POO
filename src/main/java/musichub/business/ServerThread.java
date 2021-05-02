package musichub.business;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible to handle client connection.
 */
/**
 * @author antho
 */
public class ServerThread extends Thread {
	// Usefull declarations
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	String text_received;
	InputStream is;
	MusicHub MHub;
	
	/**
	 * 
	 * @param socket
	 */
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Open connection and files thanks to the server thread and socket
	 */
	public void run() {
		try {
			// create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
			// Creation of an OutputStream on the socket (we're going to use it to send data through this port)
			OutputStream os = socket.getOutputStream();
			
			// Sending xml data needed to the client 
			XML_Update();
			
			while(true) {
				// read the object received through the stream and deserialize it
				text_received = (String) input.readObject();
				
				if(text_received.equals("update")) {
					System.out.println("Updating client with new XML files...");
					XML_Update();
					System.out.println("Update done !");
				}
				else {
					
					// Assuming that the only other case that client send data to the server is when he wants to listen a song
					// if one day we add a new features we need to do as above in the "if"
                    InputStream in = getClass().getClassLoader().getResourceAsStream(/*"files/"+*/text_received + ".wav");

                    byte[] bytes = new byte[4096];
                    int count;
                    // by this while we send data by packet of bytes using the OutputStream opened before
                    while ((count = in.read(bytes)) > 0) {
                        os.write(bytes, 0, count);
                    }
                    in.close();
				}
			}

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
	
	/**
	 * Send new XML files for the client by the output stream
	 */
	public void XML_Update() {
		MHub = new MusicHub();
		// Sending Objects (XML files) to the client, like this he can knows what's in his playlists/Albums..
		try {
			output.writeObject(MHub.getAlbums());
			output.writeObject(MHub.getPlaylists());
			output.writeObject(MHub.getElements());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}