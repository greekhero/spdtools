package ua.org.tumakha.spdtool.template.xls.row;

import ua.org.tumakha.spdtool.entity.BankTransaction;
import ua.org.tumakha.spdtool.template.model.*;
import ua.org.tumakha.util.PaymentPurposeUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yuriy Tumakha
 */
public class BankDataBuilder {

    private Map<Integer, Object> metaDataMap = new LinkedHashMap<Integer, Object>();
    private Map<String, Integer> sellsUSD = new HashMap<String, Integer>();
    private Map<String, Integer> incomeFromUSD = new HashMap<String, Integer>();
    private static final String SELL_DATE_FORMAT = "dd/MM/yyyy";

    public void prepareTemplateModel(Map<String, Object> beans, List<BankTransaction> transactions) throws ParseException {
        Map<Integer, BankDay> bankDataDays = getBankDataDays(transactions);
        List<BankQuarter> bankData = new ArrayList<BankQuarter>();
        BankQuarter lastQuarter = null;
        Calendar calendar = Calendar.getInstance();

        int rowNum = 9;
        for (BankDay bankDay : bankDataDays.values()) {
            calendar.setTime(bankDay.getDate());
            int month = calendar.get(Calendar.MONTH) + 1;
            int lastQuarterNumber = lastQuarter == null ? (int) Math.ceil((double) month / 3) - 1 : lastQuarter.getNumber();
            if (month > lastQuarterNumber * 3) {
                lastQuarter = new BankQuarter(lastQuarterNumber + 1);
                bankData.add(lastQuarter);
                if (bankData.size() > 1) {
                    rowNum += lastQuarter.getNumber() > 2 ? 8 : 7;
                }
            }
            for (BankOperationRow bankTransaction : bankDay.getTransactions()) {
                lastQuarter.addTransaction(bankTransaction);
                saveMetaData(rowNum, bankTransaction);
                rowNum++;
            }
        }

        beans.put("bankData", bankData);
        beans.put("metaData", metaDataMap);
        beans.put("spdBook", getSpdBook());
    }

    private Integer getUSDTransitAccount(List<BankTransaction> transactions) {
        int i = 0;
        for (BankTransaction transaction : transactions) {
            String purpose = transaction.getPlatPurpose();
            if (purpose != null && purpose.startsWith("@")) {
                return transaction.getAccountId();
            }
            if (i++ > 20) {
                return null;
            }
        }
        return null;
    }

    private Map<Integer, BankDay> getBankDataDays(List<BankTransaction> transactions) {
        //Integer usdTransitAccount = getUSDTransitAccount(transactions);
        Map<Integer, BankDay> bankDataDays = new LinkedHashMap<Integer, BankDay>();
        for (BankTransaction transaction : transactions) {
            String purpose = transaction.getPlatPurpose();
            //if ((usdTransitAccount != null && usdTransitAccount.equals(transaction.getAccountId()))
            String account = transaction.getAccountNo() != null ? transaction.getAccountNo().toString() : "";
            if (account.startsWith("2603") || account.startsWith("2605")
                    || (purpose != null && purpose.length() < 20 && purpose.startsWith("RBU"))) {
                continue; // ignore transaction
            }

            Integer transactionDate = transaction.getArcDate();
            if (bankDataDays.get(transactionDate) == null) {
                bankDataDays.put(transactionDate, new BankDay(transactionDate, transaction));
            } else {
                bankDataDays.get(transactionDate).addTransaction(transaction);
            }
        }
        return bankDataDays;
    }

    private void saveMetaData(int rowNum, BankOperationRow bankTransaction) throws ParseException {
        if (bankTransaction.isIncomeUAH() || bankTransaction.isAccountUSD()) {
            OperationRowMetaData rowMetaData = new OperationRowMetaData();
            rowMetaData.setDate(bankTransaction.getDate());
            rowMetaData.setIncomeUSD(bankTransaction.isIncomeUSD());
            rowMetaData.setExpenseUSD(bankTransaction.isExpenseUSD());
            rowMetaData.setIncomeUAH(bankTransaction.isIncomeUAH());
            rowMetaData.setCommission(bankTransaction.getCommission() instanceof Double);
            if (bankTransaction.isExpenseUSD()) {
                String dateAmountKey = PaymentPurposeUtil.getDateAmountKey(bankTransaction.getDate(), bankTransaction.getSellAmountUSD());
                if (incomeFromUSD.get(dateAmountKey) != null) {
                    Integer row = incomeFromUSD.get(dateAmountKey);
                    OperationRowMetaData rowData = (OperationRowMetaData) metaDataMap.get(row);
                    if (rowData != null) {
                        rowData.setUsdRowNum(rowNum);
                    }
                    incomeFromUSD.remove(dateAmountKey);
                } else {
                    sellsUSD.put(dateAmountKey, rowNum);
                }
            }
            if (rowMetaData.isCommission()) {
                String dateAndAmountUSD = PaymentPurposeUtil.getIncomeDateAndAmountUSD(bankTransaction.getOriginalPurpose());
                Integer usdRowNum = sellsUSD.get(dateAndAmountUSD);
                if (usdRowNum == null) {
                    usdRowNum = searchSellUSD(dateAndAmountUSD, rowMetaData.getDate());
                } else {
                    sellsUSD.remove(dateAndAmountUSD);
                }
                if (usdRowNum != null) {
                    rowMetaData.setUsdRowNum(usdRowNum);
                } else {
                    incomeFromUSD.put(dateAndAmountUSD, rowNum);
                }
            }
            metaDataMap.put(rowNum, rowMetaData);
        }
    }

    /**
     * Search USD sell with other date.
     */
    private Integer searchSellUSD(String dateAndAmountUSD, Date endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(SELL_DATE_FORMAT);
        Date startDate = dateFormat.parse(dateAndAmountUSD.substring(0, 10));
        String keySuffix = dateAndAmountUSD.substring(10);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        do {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String key = dateFormat.format(calendar.getTime()) + keySuffix;
            Integer usdRowNum = sellsUSD.get(key);
            if (usdRowNum != null) {
                sellsUSD.remove(dateAndAmountUSD);
                return usdRowNum;
            }
        } while (calendar.getTime().getTime() <= endDate.getTime());
        return null;
    }

    private SpdBook getSpdBook() {
        SpdBook spdBook = new SpdBook();
        SpdBookDay spdBookDay = null;
        for (Map.Entry<Integer, Object> metaEntry : metaDataMap.entrySet()) {
            Integer rowNum = metaEntry.getKey();
            OperationRowMetaData rowMetaData = (OperationRowMetaData) metaEntry.getValue();
            if (rowMetaData.isIncomeUSD() || rowMetaData.isIncomeUAH()) {
                if (spdBookDay == null || !spdBookDay.getDate().equals(rowMetaData.getDate())) {
                    spdBookDay = new SpdBookDay(rowMetaData.getDate());
                    spdBook.addDay(spdBookDay);
                }

                if (rowMetaData.isIncomeUSD()) {
                    spdBookDay.addCellRef("G", rowNum);
                }
                if (rowMetaData.isFinRes()) {
                    spdBookDay.addCellRef("L", rowNum);
                } else if (rowMetaData.isOtherIncome()) {
                    spdBookDay.addCellRef("M", rowNum);
                }
            }
        }
        return spdBook;
    }

}
