package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.application.output.TransactionOutput;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {

    private final TransactionRepository repository;

    public PersistTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionOutput execute(PersistTransactionInput input) {
        if (input.amount() <= 0) {
            throw new IllegalArgumentException("Erro de validação: O valor da transação deve ser estritamente maior que zero. Avise o usuário que a transação não foi salva.");
        }

        Transaction transaction = new Transaction(input.description(), input.amount(), input.category());
        repository.save(transaction);

        return new TransactionOutput(
                transaction.getId().toString(),
                transaction.getDescription(),
                transaction.getCategory().name(), 
                (double) transaction.getAmount() 
        );
    }
}
