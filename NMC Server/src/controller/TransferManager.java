package controller;



import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Config;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.Session;
import org.apache.sshd.common.file.FileSystemView;
import org.apache.sshd.common.file.nativefs.NativeFileSystemFactory;
import org.apache.sshd.common.file.nativefs.NativeFileSystemView;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.UserAuth;
import org.apache.sshd.server.auth.UserAuthPublicKey;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;



/**
 * Classe de gestion du module SFTP
 * <br />
 * Permet l'envoi et la réception de fichiers médias
 * @author Antoine Ceyssens
 *
 */
public class TransferManager extends Thread {
	private static TransferManager instance = null;
	public static final File ROOTDIR = new File(Config.getInstance().getProp("root_dir"));
	public static final String USERNAME = Config.getInstance().getProp("ftp_user");	

	@Override
	public void run(){

		SshServer sshd = SshServer.setUpDefaultServer();		
		File hostkey = new File(".config/security/");
		hostkey.mkdirs();
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostkey.getAbsolutePath()));
		
		List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<NamedFactory<UserAuth>>();
		userAuthFactories.add(new UserAuthPublicKey.Factory());
		sshd.setUserAuthFactories(userAuthFactories);
		sshd.setPublickeyAuthenticator(new PublickeyAuthenticator() {
			private String expectedUser = USERNAME;
			@Override
			public boolean authenticate(String user, PublicKey publicKey, ServerSession serverSession) {
				return expectedUser.equals(user);
			}
		});
		
		sshd.setCommandFactory(new ScpCommandFactory());
		
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
		try {
			sshd.start();
		} catch (IOException e) {
			System.out.println("[Error] - Unable to launch the SFTP Server");
			if(Main.getDebug()) e.printStackTrace();
		}
	}
	
	public static TransferManager getInstance(){
		if(instance == null){
			instance = new TransferManager();
		}
		return instance;
	}
}
