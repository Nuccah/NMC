package view;

/**
 * Custom exception class FTPException inheriting from Exception
 * Used for all errors related to the FTP Upload process
 * @author Derek
 **/

public class FTPException extends Exception {

	private static final long serialVersionUID = 4292563065311276704L;

	public FTPException(String message) {
		super(message);
	}
}