package me.fevralev.budgetapplication.controllers;

import me.fevralev.budgetapplication.model.Transaction;
import me.fevralev.budgetapplication.services.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final BudgetService budgetService;

    public TransactionController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Long> addTransaction(@RequestBody Transaction transaction){
    long id = budgetService.addTransaction(transaction);
    return ResponseEntity.ok(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id){

 Transaction transaction = budgetService.getTransaction(id);
 if (transaction==null){
     return ResponseEntity.notFound().build();
 }
 return ResponseEntity.ok(transaction);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> editTransaction(@RequestBody Transaction transaction, @PathVariable long id){

        Transaction transaction1 = budgetService.editTransaction(transaction, id);
        if (transaction1==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction1);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long id){
       if( budgetService.deleteTransaction(id)){
           return ResponseEntity.ok().build();
       }
       return ResponseEntity.notFound().build();
       }
    @DeleteMapping
    public ResponseEntity deleteAllTransactions(){
        budgetService.deleteAllTransactions();
        return ResponseEntity.ok().build();
    }
}
