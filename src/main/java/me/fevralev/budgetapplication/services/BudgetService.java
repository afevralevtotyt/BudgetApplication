package me.fevralev.budgetapplication.services;

import me.fevralev.budgetapplication.model.Transaction;

public interface BudgetService {

    int getDailyBudget();

    int getBalance();


    long addTransaction(Transaction transaction);

    int getDailyBalance();

    int getAllSpent();

    int getVocationBonus(int daysCount);

    int getSalaryWithVocation(int vocationDaysCount, int vocationWorkingDaysCount, int workingDaysInMonth);

    Transaction getTransaction(long id);

    Transaction editTransaction(Transaction transaction, long id);

    boolean deleteTransaction(long id);

    void deleteAllTransactions();

    void saveToFile();

    void readFromFile();
}
