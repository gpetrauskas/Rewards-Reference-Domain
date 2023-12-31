package accounts.web;

import accounts.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;
import rewards.internal.account.Account;

import java.util.List;

/**
 * A Spring MVC REST Controller handling requests to retrieve Account information.
 *
 * Note that some of the Account related classes are imported from the
 * rewards-db project:
 *
 * -Domain objects: Account and Beneficiary
 * -Service layer: AccountManager interface
 * -Repository layer: AccountRepository interface
 *
 */
// TODO-03: Add an appropriate annotation to make this class a REST controller

@RestController
public class AccountController {

	private final AccountManager accountManager;

	/**
	 * Creates a new AccountController with a given account manager.
	 */
	@Autowired
	public AccountController(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	/**
	 * Return a list of all accounts
	 */
	// TODO-04: Add an appropriate annotation to make this method handle "/accounts"

	@GetMapping("/accounts")
	public List<Account> accountList() {

		// TODO-05: Implement the logic to find and return all accounts
		// returns all accounts
		return accountManager.getAllAccounts();
		
		// TODO-06: (If you are using STS) We are about to make lots of
		//          changes, so stop the application otherwise Devtools
		//          will keep restarting it.
	}

	// TODO-08: Implement the /accounts/{entityId} request handling method.
	@GetMapping("/accounts/{entityId}")
	public ResponseEntity<Account> accountDetails(@PathVariable String entityId) {
		// try block to try parse entityId as Long
		try {
			Long accountId = Long.parseLong(entityId);
			/// if success parsing, checks if its not a null
			if (accountId == null || accountManager.getAccount(accountId) == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(accountManager.getAccount(accountId));
			// if fail to parse, throws an exception
		} catch (NumberFormatException e) {
			return ResponseEntity.notFound().build();
		}
	}


	// TODO-10b: If AccountControllerTests.testHandleDetailsRequest()
	//  fails, fix errors before moving on

	// TODO-11: Run the application
	// - You should now be able to invoke http://localhost:8080/accounts/N
	//   where N is 0-20 and get a response. You can use curl, Postman or
	//   your browser to do this.

}
