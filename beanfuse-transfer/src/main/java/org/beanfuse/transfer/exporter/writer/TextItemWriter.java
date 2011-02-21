package org.beanfuse.transfer.exporter.writer;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.beanfuse.transfer.Transfer;

public class TextItemWriter extends AbstractItemWriter {

	private String delimiter = ",";

	protected OutputStreamWriter osw;

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public TextItemWriter(OutputStream outputStream) {
		setOutputStream(outputStream);
	}

	public void write(Object obj) {
		if (null == obj)
			return;
		try {
			if (obj.getClass().isArray()) {
				Object[] values = (Object[]) obj;
				for (int i = 0; i < values.length; i++) {
					if (null == values[i]) {
						osw.write("");
					} else {
						osw.write(values[i].toString());
					}
					if (i < values.length - 1) {
						osw.write(delimiter);
					} else {
						osw.write("\r\n");
					}
				}
			} else {
				osw.write(obj.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void writeTitle(Object title) {
		write(title);
	}

	public void close() {
		try {
			osw.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getFormat() {
		return Transfer.TXT;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.osw = new OutputStreamWriter(outputStream);
	}

}
