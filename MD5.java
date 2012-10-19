import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


public class MD5 {
	
	private FileInputStream openFile(String fileName) throws FileNotFoundException {
		return (new FileInputStream(fileName));
	}
	
	public Boolean checksum(String filePath1, String filePath2) {
		
		FileInputStream file1 = null, file2 = null;
		MessageDigest md = null;
		
		try {
			file1 = openFile(filePath1);
			file2 = openFile(filePath2);
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String hash1 = generateHashForFile(file1, md);
		
		String hash2 = generateHashForFile(file2, md);
		
		System.out.println("======= MD5 Checksum =======");
		System.out.println("File 1: " + filePath1 + "\t| Checksum: " + hash1);
		System.out.println("File 2: " + filePath2 + "\t| Checksum: " + hash2);
		if(hash1.equals(hash2))
			System.out.println("These files are equals.");
		else
			System.out.println("These files are different.");
		
		return (hash1.equals(hash2));
	}
	
	private String generateHashForFile(FileInputStream file, MessageDigest md) {
		DigestInputStream fileDigest = new DigestInputStream(file, md);
		
		try {
			while (fileDigest.read() != -1);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		byte[] digested1 = md.digest();
		String hash = byteToHex(digested1);
		return hash;
	}

	private String byteToHex(byte[] bytes) {
		Formatter formatter = new Formatter();
		
		for(byte b : bytes) {
			formatter.format("%02x", b);
		}
		
		String hash = formatter.toString();
		formatter.close();
		return hash;
	}
	
	public static void main(String[] args) {
		MD5 md5 = new MD5();
		String file1;
		String file2;
		
		if(args.length >= 2) {
			file1 = args[0];
			file2 = args[1];
		} else {
			file1 = "file.txt";
			file2 = "file_3.txt";
		}
		
		Boolean isEquals = md5.checksum(file1, file2);
		System.out.println("Equals? " + isEquals);
	}
	
}