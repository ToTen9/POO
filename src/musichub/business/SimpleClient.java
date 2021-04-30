package musichub.business;

import java.io.*;  
import java.net.*;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SimpleClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	Scanner scan = new Scanner(System.in);

	
	public void connect(String ip)
	{
		int port = 6666;
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
 
			//Student student = (Student) input.readObject();	//de-serialize and read the Student object from the stream
			//System.out.println("Received student id: " + student.getID() + " and student name:" + student.getName() + " from server");
			
		Interface();
			// Get music and Play it


			
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
//		catch  (ClassNotFoundException cnfe) {
//			cnfe.printStackTrace();
//		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	public void Play() {
		try {
			System.out.println("What do you want in your hears ? ");
			String textToSend;
			textToSend = scan.nextLine();
			System.out.println("Music to play:  " + textToSend + ".wav");			
			output.writeObject(textToSend);		//serialize and write the String to the stream
			InputStream is = socket.getInputStream();
			File temp = File.createTempFile("current_song", ".wav");
			FileOutputStream os = new FileOutputStream(temp);
			
			byte[] b = new byte[4096];
			int i;
			while((i = is.read(b)) > 0) {
				os.write(b, 0, i);
			}
			os.close();is.close();
			String temp_url = temp.getPath();
			System.out.println(temp.getName() + " recu\n");
			
			//Playing music 
			PlaySound play = new PlaySound();
	        play.playSound(temp_url, scan);
	        temp.deleteOnExit();
			System.out.println("The END\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public void Interface () {
		MusicHub theHub = new MusicHub();
		System.out.print("Welcome to the MusicHub Client Interface\n");
		System.out.println("Type h for available commands");
		
		String choice;
		choice = scan.nextLine();
		
		String albumTitle = null;
		
		if (choice.length() == 0)
			System.exit(0);
		
		while (choice.charAt(0) != 'q') {
			switch (choice.charAt(0)) {
			case 'h':
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 't':
				// album titles, ordered by date
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'g':
				// songs of an album, sorted by genre
				System.out.println(
						"Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
		
				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
				} catch (NoAlbumFoundException ex) {
					System.out.println("No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'd':
				// songs of an album
				System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
		
				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongs(albumTitle));
				} catch (NoAlbumFoundException ex) {
					System.out.println("No album found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'u':
				// audiobooks ordered by author
				System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'p':
				// Play music entered by the client
				Play();
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			default:
		
				break;
			}
		}
		scan.close();
	}

	private static void printAvailableCommands() {
		System.out.println("t: display the album titles, ordered by date");
		System.out.println("g: display songs of an album, ordered by genre");
		System.out.println("d: display songs of an album");
		System.out.println("u: display audiobooks ordered by author");
		System.out.println("p: play some musics");
		System.out.println("q: quit program");
	}
}

class PlaySound implements LineListener{
	
	private boolean play;
	
	public void playSound(String FILE_PATH, Scanner scan) {
    	File file = new File(FILE_PATH);
 //   	Scanner scan = new Scanner(System.in);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioStream.getFormat();
            //System.out.println(format);
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioStream);
            clip.start();
            String c = "s";
            
            while(!play) {
            	while(c.length() > 0 && c.charAt(0) != 'k') {
            		System.out.println("Press k to stop the music");
            		c = scan.nextLine();
            	}
            	clip.stop();
            }
            clip.close();
            play = false;

        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
	
@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if (type == LineEvent.Type.START) {
			System.out.println("Playback started");
		} else if (type == LineEvent.Type.STOP) {
			play = true;
			System.out.println("Playback finished");
		}
	}
}