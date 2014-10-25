package controller;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import model.Config;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.Session;
import org.apache.sshd.common.file.FileSystemView;
import org.apache.sshd.common.file.nativefs.NativeFileSystemFactory;
import org.apache.sshd.common.file.nativefs.NativeFileSystemView;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;



/**
 * Classe de gestion de la réception des fichiers médias
 * @author Antoine Ceyssens
 *
 */
public class TransferManager extends Thread {
	private static TransferManager instance = null;
	public static final File ROOTDIR = new File(Config.getInstance().getProp("root_dir"));
	public static final String USERNAME = Config.getInstance().getProp("ftp_user");
	public static final String PASSWORD = Config.getInstance().getProp("ftp_port");

	@Override
	public void run(){
		SshServer sshd = SshServer.setUpDefaultServer();		
		sshd.setFileSystemFactory(new NativeFileSystemFactory(){
			@Override
			public FileSystemView createFileSystemView(final Session session){
				return new NativeFileSystemView(session.getUsername(), false){
					@SuppressWarnings("unused")
					public String getVirtualUserDir(){
						return ROOTDIR.getAbsolutePath();
					}
				};
			}
		});
		sshd.setPort(Integer.valueOf(Config.getInstance().getProp("ftp_port")));
		sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>>asList(new SftpSubsystem.Factory()));
        sshd.setCommandFactory(new ScpCommandFactory());
        File hostKey = new File("hostkey.ser");
        try{
        	if(!hostKey.exists()) hostKey.createNewFile();
        } catch(IOException e){
        	System.out.println("[Error] - Unable to create the hostkey.ser file");
        }
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostKey.getAbsolutePath()));
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {
            @Override
            public boolean authenticate(final String username, final String password, final ServerSession session) {
                return (username.compareTo(USERNAME) == 0) && (password.compareTo(PASSWORD) == 0);
            }
        });
		try {
			sshd.start();
		} catch (IOException e) {
			System.out.println("[Erro] - Unable to launch the SFTP Server");
			e.printStackTrace();
		}
	}
	
	public static TransferManager getInstance(){
		if(instance == null){
			instance = new TransferManager();
		}
		return instance;
	}
}
