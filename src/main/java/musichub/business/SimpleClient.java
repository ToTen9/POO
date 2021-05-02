package musichub.business;

import java.io.*;  
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * 
 * @author antho
 *
 */
public class SimpleClient {
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	Scanner scan = new Scanner(System.in);
	InputStream is;
	PlaySound play = new PlaySound();
	
/**
 * @param ip
 */
	public void connect(String ip)
	{
		int port = 6666;
		
		// Once connected to the server go to client interface
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            // Creation of the InputStream on the socket
			is = socket.getInputStream();

            //Launching interface client
            Interface();
					
	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
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
			/**
			 * We ask to the client which song he want to listen
			 * and then send it to the server via the stream
			 */
			System.out.println("What do you want in your ears ? ");
			String textToSend;
			textToSend = scan.nextLine();
			System.out.println("Music to play: " + textToSend + ".wav");			
			output.writeObject(textToSend);		//serialize and write the String to the stream
		
			//Playing music 
			play.playSound(is, scan);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void Interface () {
		try {
			LinkedList<Album> albums =  (LinkedList<Album>) input.readObject();
			LinkedList<PlayList> playlists = (LinkedList<PlayList>) input.readObject();
			LinkedList<AudioElement> elements = (LinkedList<AudioElement>) input.readObject();
			MusicHub theHub = new MusicHub(albums, playlists, elements);
				
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
				case 'b':
					// update function
					String update_req = "update";
					System.out.println("Update request");			
					output.writeObject(update_req);

					albums =  (LinkedList<Album>) input.readObject();
					playlists = (LinkedList<PlayList>) input.readObject();
					elements = (LinkedList<AudioElement>) input.readObject();
					theHub = new MusicHub(albums, playlists, elements);
					System.out.println("Update ok");

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
				case 'q':
					System.exit(0);
					break;
				case 'k':
					// Play/pause function
					play.PP();
					printAvailableCommands();
					choice = scan.nextLine();
					break;
				default:
					System.out.println("Please enter correct input");
					printAvailableCommands();
					choice = scan.nextLine();
					break;
				}
			}
			scan.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private static void printAvailableCommands() {
		System.out.println("b: update the database");
		System.out.println("t: display the album titles, ordered by date");
		System.out.println("g: display songs of an album, ordered by genre");
		System.out.println("d: display songs of an album");
		System.out.println("u: display audiobooks ordered by author");
		System.out.println("p: play some musics");
		System.out.println("k: pause / play the song");
		System.out.println("q: quit program");
	}
}

class PlaySound {
	AudioInputStream audioStream;
	AudioFormat format;
	Clip clip;
	Long currentFrame;

	/**
	 * 
	 * @param is
	 * @param scan
	 */
	public void playSound(InputStream is, Scanner scan) {

    	try {
    		/**
    		 *  Convert Input Stream to AudioStream,
    		 *  we need to pass by a bufferedInputstream function because otherwise it doesn't have the rigth format
    		 */
    		InputStream bufferedIS = new BufferedInputStream(is);
            audioStream = AudioSystem.getAudioInputStream(bufferedIS);
            format = audioStream.getFormat();
            /**
             * Once we have the format we use it to open a clip class who will support our music
             * And the we start our clip
             */
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

	/**
	 * Play / Pause function
	 */
	public void PP () {
		/**
		 * Check if any music "in the pipes" -> any music running or not
		 */
		if(clip != null && clip.isOpen()) {
			/**
			 * If the music is running then get the corect time and stop it 
			 */
			if (clip.isActive() == true) {
				this.currentFrame = this.clip.getMicrosecondPosition();
				clip.stop();
			
				/**
				 * If the music is here but not running at the moment, get the time where it stop and play it
				 */
			}else if (clip.isActive() == false) {
				clip.setMicrosecondPosition(currentFrame);
				clip.start();
			}
		}		
	}
}