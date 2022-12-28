package me.fevralev.budgetapplication.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.fevralev.budgetapplication.model.Transaction;
import me.fevralev.budgetapplication.services.BudgetService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BudgetServiceImpl implements BudgetService {
    final private FilesServiceImpl filesService;
    public static final int SALARY = 30_000 - 9_750;
    public static final int AVG_SALARY = SALARY;
    public static final int SAVING = 3000;

    public static final int DAILY_BUDGET = (SALARY - SAVING) / LocalDate.now().lengthOfMonth();
    public static int balance = 0;
    //    public static final int AVG_SALARY = (10000+ 10000+ 10000+ 10000+ 10000+ 10000+ 15000+ 15000+ 15000+ 15000+ 15000+ 20000)/12;
    public static final double AVG_DAYS = 29.3;
    private static long lastId = 0;
    private static Map<Month, LinkedHashMap<Long, Transaction>> transactions = new TreeMap<>();

    public BudgetServiceImpl(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @Override
    public int getDailyBudget() {
        return SALARY / 30;
    }

    @Override
    public int getBalance() {
        return SALARY - SAVING - getAllSpent();
    }

    @Override
    public long addTransaction(Transaction transaction) {
        LinkedHashMap<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        monthTransactions.put(lastId, transaction);
        transactions.put(LocalDate.now().getMonth(), monthTransactions);
        saveToFile();
        return lastId++;
    }
    @PostConstruct
    private void init(){
        readFromFile();
    }

    @Override
    public int getDailyBalance() {
        return (DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpent());
    }

    @Override
    public int getAllSpent() {
        int sum = 0;
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        for (Transaction transaction : monthTransactions.values()) {
            sum += transaction.getSum();
        }
        return sum;
    }

    @Override
    public int getVocationBonus(int daysCount) {
        double avgDaySalary = AVG_SALARY / AVG_DAYS;
        return (int) avgDaySalary * daysCount;
    }


    @Override
    public int getSalaryWithVocation(int vocationDaysCount, int vocationWorkingDaysCount, int workingDaysInMonth) {

        int salary = SALARY / workingDaysInMonth * (workingDaysInMonth - vocationWorkingDaysCount);
        return salary + getVocationBonus(vocationDaysCount);
    }

    @Override
    public Transaction getTransaction(long id) {
        for (Map<Long, Transaction> transactionByMonth : transactions.values()
        ) {
            Transaction transaction = transactionByMonth.get(id);
            if (transaction != null) {
                return transaction;
            }
        }

        return null;
    }

    @Override
    public Transaction editTransaction(Transaction transaction, long id) {
        for (Map<Long, Transaction> transactionByMonth : transactions.values()
        ) {
            if (transactionByMonth.containsKey(id)) {
                transactionByMonth.put(id, transaction);
                return transaction;
            }
        }
        saveToFile();

        return null;
    }

    @Override
    public boolean deleteTransaction(long id) {
        for (Map<Long, Transaction> transactionByMonth : transactions.values()
        ) {
            if (transactionByMonth.containsKey(id)) {
                transactionByMonth.remove(id);
                return true;
            }

        }
        return false;
    }
    @Override
    public void deleteAllTransactions(){
        transactions = new TreeMap<>();
    }

    @Override
    public void saveToFile(){
        try {
           String json = new ObjectMapper().writeValueAsString(transactions);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void readFromFile(){
        try {
            String json = filesService.readFromFile();
            transactions = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Month, LinkedHashMap<Long, Transaction>>>(){});
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
