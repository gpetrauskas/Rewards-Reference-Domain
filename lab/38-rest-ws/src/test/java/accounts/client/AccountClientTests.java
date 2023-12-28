package accounts.client;

import common.money.Percentage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rewards.internal.account.Account;
import rewards.internal.account.Beneficiary;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AccountClientTests {

	private static final String BASE_URL = "http://localhost:8080";
	
	private RestTemplate restTemplate = new RestTemplate();
	private Random random = new Random();
	
	@Test
	public void listAccounts() {
		// TODO-03: Run this test
		// - Remove the @Disabled on this test method.
		// - Then, use the restTemplate to retrieve an array containing all Account instances.
		// - Use BASE_URL to help define the URL you need: BASE_URL + "/..."
		// - Run the test and ensure that it passes.
		Account[] accounts = restTemplate.getForObject(BASE_URL + "/accounts", Account[].class);
		
		assertNotNull(accounts);
		assertTrue(accounts.length >= 21);
		assertEquals("Keith and Keri Donald", accounts[0].getName());
		assertEquals(2, accounts[0].getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"), accounts[0].getBeneficiary("Annabelle").getAllocationPercentage());
	}
	
	@Test
	public void getAccount() {
		// TODO-05: Run this test
		// - Remove the @Disabled on this test method.
		// - Then, use the restTemplate to retrieve the Account with id 0 using a URI template
		// - Run the test and ensure that it passes.
		Account account = restTemplate.getForObject(BASE_URL + "/accounts/0", Account.class);
		
		assertNotNull(account);
		assertEquals("Keith and Keri Donald", account.getName());
		assertEquals(2, account.getBeneficiaries().size());
		assertEquals(Percentage.valueOf("50%"), account.getBeneficiary("Annabelle").getAllocationPercentage());
	}
	
	@Test
	public void createAccount() {
		// Use a unique number to avoid conflicts
		String number = String.format("12345%4d", random.nextInt(10000));
		Account account = new Account(number, "John Doe");
		account.addBeneficiary("Jane Doe");
		
		//	TODO-08: Create a new Account
		URI newAccountLocation = restTemplate
				.postForLocation(BASE_URL + "/accounts", account);

		assertNotNull(newAccountLocation);

		//	TODO-09: Retrieve the Account you just created from
		//	         the location that was returned.
		Account retrievedAccount = restTemplate
				.getForObject(newAccountLocation, Account.class);

		assertNotNull(retrievedAccount);
		
		assertEquals(account.getNumber(), retrievedAccount.getNumber());
		
		Beneficiary accountBeneficiary = account.getBeneficiaries().iterator().next();
		Beneficiary retrievedAccountBeneficiary = retrievedAccount.getBeneficiaries().iterator().next();
		
		assertEquals(accountBeneficiary.getName(), retrievedAccountBeneficiary.getName());
		assertNotNull(retrievedAccount.getEntityId());
	}
	
	@Test
	public void addAndDeleteBeneficiary() {
		// perform both add and delete to avoid issues with side effects

		// TODO-13: Create a new Beneficiary
		// create new beneficiary called David for the account with id 1
		ResponseEntity<Void> response = restTemplate
				.postForEntity(BASE_URL + "/accounts/1/beneficiaries?beneficiaryName=David", null, Void.class);

		// ensure that the request was successful
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// store the returned location URI in variable
		URI location = response.getHeaders().getLocation();
		// ensure that location is not null
		assertNotNull(location);

		// TODO-14: Retrieve the Beneficiary you just created from the location that was returned
		// retrieve beneficiary from location was just created.
		Beneficiary newBeneficiary = restTemplate
				.getForObject(location, Beneficiary.class);

		// check if retrieved beneficiary match with specific name, "David" at this current check
		assertNotNull(newBeneficiary);
		assertEquals("David", newBeneficiary.getName());

		// TODO-15: Delete the newly created Beneficiary
		restTemplate.delete(location);

		HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
			System.out.println("You SHOULD get the exception \"No such beneficiary with name 'David'\" in the server.");
			// TODO-16: Try to retrieve the newly created Beneficiary again.
			restTemplate.getForObject(location, Beneficiary.class);
		});
		assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
	}
	
}
