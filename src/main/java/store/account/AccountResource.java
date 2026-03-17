package store.account;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
public class AccountResource implements AccountController {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<Void> create(AccountIn in) {
        final Account a = accountService.create(
            AccountParser.to(in)
        );

        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(a.id())
                .toUri()
        ).build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @Override
    public ResponseEntity<List<AccountOut>> findAll() {
        return ResponseEntity.ok(
            AccountParser.to(
                accountService.findByAll()
            )
        );
    }

    @Override
    public ResponseEntity<AccountOut> getById(String id) {
        Account out = accountService.findById(id);

        return out == null ?
            ResponseEntity.notFound().build() :
            ResponseEntity.ok(AccountParser.to(out));
    }
}