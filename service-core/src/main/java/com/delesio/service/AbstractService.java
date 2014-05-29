package com.delesio.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.delesio.SpringBean;

public abstract class AbstractService extends SpringBean {

	// the transaction manager
//	protected PlatformTransactionManager transactionManager;
	

	
	
//	public PlatformTransactionManager getTransactionManager() {
//		return transactionManager;
//	}
//	public void setTransactionManager(PlatformTransactionManager transactionManager) {
//		this.transactionManager = transactionManager;
//	}
//		
//	public TransactionTemplate getTransactionTemplate() {
//		  TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//		  transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); 
//		  return transactionTemplate;
//		 }
	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
	
	
	

	
//	protected List<String> tokenize(String taskDelimiter)
//	{
//		StringTokenizer stringTokenizer = new StringTokenizer(taskDelimiter, ",");	
//		List<String> ids = new ArrayList<String>();
//		while (stringTokenizer.hasMoreTokens())
//		{
//			String id = stringTokenizer.nextToken();
//			ids.add(id);
//		}
//		
//		return ids;
//	}
}
