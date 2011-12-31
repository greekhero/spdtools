import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;

/**
 * @author Yuriy Tumakha
 */
public class DocxXmlUtil {

	private final File docxFile;
	private final File xmlFile;

	public DocxXmlUtil(String filename) {
		filename = filename.replace(".docx", "").replace(".xml", "");
		docxFile = new File(filename + ".docx");
		xmlFile = new File(filename + ".xml");
		System.out.println(docxFile.getAbsolutePath());
	}

	public static void main(String[] args) throws Exception {
		new DocxXmlUtil(args[0]).convert();
	}

	public void convert() throws Docx4JException, FileNotFoundException,
			JAXBException, IOException {
		// Open a document from the file system
		// 1. Load the Package
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(docxFile);
		// 2. Fetch the document part
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

		org.docx4j.wml.Document wmlDocumentEl = documentPart.getJaxbElement();

		if (!xmlFile.exists()) {
			// xml --> string --> file
			String xml = XmlUtils.marshaltoString(wmlDocumentEl, true);
			FileUtils.writeStringToFile(xmlFile, xml);
			System.out.println("Saved XML output to: "
					+ xmlFile.getAbsolutePath());

		}

		Object obj = XmlUtils.unmarshal(new FileInputStream(xmlFile));
		// change JaxbElement
		documentPart.setJaxbElement((Document) obj);

		SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
		saver.save(docxFile);
		System.out.println("Saved DOCX output to: "
				+ docxFile.getAbsolutePath());
	}

}
