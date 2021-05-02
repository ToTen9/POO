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
	String text_received;
	InputStream is;
	File audio; 
	MusicHub MHub;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			// create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			
			// Creation du os sur le socket
			OutputStream os = socket.getOutputStream();
			
			XML_Update();
			
			while(true) {
				
				text_received = (String) input.readObject(); // read the object received through the stream and deserialize it
				if(text_received.equals("update")) {
					System.out.println("Sending update");
					XML_Update();
					System.out.println("Update ok");

				}
				else {
					
                    InputStream in = getClass().getClassLoader().getResourceAsStream(/*"files/"+*/text_received + ".wav");
//                    if (in == null) output.writeObject(NOT_FOUND_RESPONSE);
//                    else output.writeObject(OK_RESPONSE);
                    byte[] bytes = new byte[4096];
                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        os.write(bytes, 0, count);
                    }
                    in.close();
				}
//				else {
//					System.out.println("Client whishes: " + text_received);
//
//					audio = new File("files\\"+ text_received +".wav");
//					System.out.print("\nOk, sending " + audio.getName() + "\n");
//					byte[] b = new byte[4096];
//					is = new FileInputStream(audio);
//					
//					int i;
//
//					while((i = is.read(b)) > 0) {
//						os.write(b, 0, i);
//					}
//					is.close();
//				}
				
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
	
	public void XML_Update() {
		MHub = new MusicHub();
		
		try {
			output.writeObject(MHub.getAlbums());
			output.writeObject(MHub.getPlaylists());
			output.writeObject(MHub.getElements());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}