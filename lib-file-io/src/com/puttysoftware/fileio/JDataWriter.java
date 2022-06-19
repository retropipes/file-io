/*  Diane Game Engine
Copyleft (C) 2019 Eric Ahnell

Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.github.cliftonlabs.json_simple.Jsoner;

public class JDataWriter implements DataIOWriter {
    // Fields
    private final BufferedWriter fileIO;
    private final File file;

    // Constructors
    public JDataWriter(final String filename) throws IOException {
	this.fileIO = new BufferedWriter(new FileWriter(filename));
	this.file = new File(filename);
	this.writeJHeader();
    }

    public JDataWriter(final File filename) throws IOException {
	this.fileIO = new BufferedWriter(new FileWriter(filename));
	this.file = filename;
	this.writeJHeader();
    }

    public JDataWriter(final OutputStream stream) throws IOException {
	this.fileIO = new BufferedWriter(new OutputStreamWriter(stream));
	this.file = null;
	this.writeJHeader();
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
	    this.writeJFooter();
	    this.fileIO.close();
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeDouble(final double d) throws DataIOException {
	try {
	    Jsoner.serialize(d, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeInt(final int i) throws DataIOException {
	try {
	    Jsoner.serialize(i, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeLong(final long l) throws DataIOException {
	try {
	    Jsoner.serialize(l, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeByte(final byte b) throws DataIOException {
	try {
	    Jsoner.serialize(b, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeUnsignedByte(final int b) throws DataIOException {
	this.writeInt(b);
    }

    @Override
    public void writeBoolean(final boolean b) throws DataIOException {
	try {
	    Jsoner.serialize(b, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    @Override
    public void writeString(final String s) throws DataIOException {
	try {
	    Jsoner.serialize(s, this.fileIO);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    private void writeJHeader() throws DataIOException {
	try {
	    this.fileIO.write(JDataConstants.J_HEADER);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }

    private void writeJFooter() throws DataIOException {
	try {
	    this.fileIO.write(JDataConstants.J_FOOTER);
	} catch (IOException e) {
	    throw new DataIOException(e);
	}
    }
}
