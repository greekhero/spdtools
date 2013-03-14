import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;

import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.xml.BankData;

public class BankDataImport {

	private static final String CHARSET = "windows-1251";// Cp1251
	private static final String[] EXTENSIONS = { "xml" };

	public static void main(final String[] args) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(BankData.class);
		Unmarshaller um = context.createUnmarshaller();
		File directory = new File(args[0]);
		for (Object file : FileUtils.listFiles(directory, EXTENSIONS, false)) {
			System.out.println(file);
			InputStreamReader reader = new InputStreamReader(new FileInputStream((File) file), CHARSET);
			BankData bankData = (BankData) um.unmarshal(reader);
			for (BankTransaction trans : bankData.getBankTransactions()) {
				System.out.println(trans.getId() + "  " + trans.getOperationId() + "  " + trans.getCurrSymbolCode()
						+ "  " + trans.getDocumentTypeId() + "." + trans.getDocSubTypesName() + "\t\t"
						+ trans.getCorrContragentsName() + "\t" + trans.getPlatPurpose());
			}
		}
	}
}