package edu.mum.core.util;

import java.io.File;

/**
 * File Buffer Callback is a buffer interface to allow user to process file
 * data in sections, rather than load it into memory all at once. 
 */

public interface FileReadCb {
    
    /**
     * Callback to handle file read 
     */
    public void process(String str) throws Exception;

}
