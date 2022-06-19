/*  Diane Game Engine
Copyleft (C) 2019 Eric Ahnell

Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;

public class JDataReader implements DataIOReader {
    // Fields
    private final BufferedReader fileIO;
    private final File file;

    // Constructors
    public JDataReader(final String filename) throws IOException {
	this.fileIO = new BufferedReader(new FileReader(filename));
	this.file = new File(filename);
	this.readJHeader();
    }

    public JDataReader(final File filename) throws IOException {
	this.fileIO = new BufferedReader(new FileReader(filename));
	this.file = filename;
	this.readJHeader();
    }

    public JDataReader(final InputStream stream) throws IOException {
	this.fileIO = new BufferedReader(new InputStreamReader(stream));
	this.file = null;
	this.readJHeader();
    }

    // Methods
    @Override
    public DataMode getDataIOMode() {
	return DataMode.JSON;
    }

    @Override
    public File getFile() {
	return this.file;
    }

    @Override
    public void close() throws DataIOException {
	try {
	    this.readJFooter();
	    this.fileIO.close();
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public double readDouble() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return Double.parseDouble(strobj);
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public int readInt() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return Integer.parseInt(strobj);
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public long readLong() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return Long.parseLong(strobj);
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public byte readByte() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return Byte.parseByte(strobj);
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public byte[] readBytes(final int len) throws DataIOException {
	try {
	    final byte[] buf = new byte[len];
	    for (int b = 0; b < len; b++) {
		buf[b] = readByte();
	    }
	    return buf;
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public boolean readBoolean() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return Boolean.parseBoolean(strobj);
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public String readString() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	    if (line != null) {
		final String strobj = Jsoner.deserialize(line).toString();
		return strobj;
	    }
	} catch (IOException | JsonException e) {
	    throw new DataIOException(e);
	}
	throw new DataIOException("End of file!"); //$NON-NLS-1$
    }

    @Override
    public int readUnsignedByte() throws DataIOException {
	return readInt();
    }

    @Override
    public int readUnsignedShortByteArrayAsInt() throws DataIOException {
	try {
	    final byte[] buf = new byte[Short.BYTES];
	    for (int b = 0; b < Short.BYTES; b++) {
		buf[b] = readByte();
	    }
	    return DataIOUtilities.unsignedShortByteArrayToInt(buf);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public String readWindowsString(final byte[] buflen) throws DataIOException {
	try {
	    final byte[] buf = new byte[buflen.length];
	    for (int b = 0; b < buflen.length; b++) {
		buf[b] = readByte();
	    }
	    return DataIOUtilities.decodeWindowsStringData(buf);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public boolean atEOF() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
	return line == null;
    }

    private void readJHeader() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
	if (line == null) {
	    throw new DataIOException("Corrupt or invalid header!"); //$NON-NLS-1$
	}
	if (!line.equals(JDataConstants.J_HEADER)) {
	    throw new DataIOException("Corrupt or invalid header!"); //$NON-NLS-1$
	}
    }

    private void readJFooter() throws DataIOException {
	String line = "";
	try {
	    line = this.fileIO.readLine();
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
	if (line == null) {
	    throw new DataIOException("Corrupt or invalid footer!"); //$NON-NLS-1$
	}
	if (!line.equals(JDataConstants.J_FOOTER)) {
	    throw new DataIOException("Corrupt or invalid footer!"); //$NON-NLS-1$
	}
    }
}
