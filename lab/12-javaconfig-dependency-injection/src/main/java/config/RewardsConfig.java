package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

/**
 * TODO-00: In this lab, you are going to exercise the following:
 * - Creating Spring configuration class
 * - Defining bean definitions within the configuration class
 * - Specifying the dependency relationships among beans
 * - Injecting dependencies through constructor injection
 * - Creating Spring application context in the test code
 *   (WITHOUT using Spring testContext framework)
 *
 * TODO-01: Make this class a Spring configuration class
 * - Use an appropriate annotation.
 *
 * TODO-02: Define four empty @Bean methods, one for the
 *          reward-network and three for the repositories.
 * - The names of the beans should be:
 *   - rewardNetwork
 *   - accountRepository
 *   - restaurantRepository
 *   - rewardRepository
 *
 * TODO-03: Inject DataSource through constructor injection
 * - Each repository implementation has a DataSource
 *   property to be set, but the DataSource is defined
 *   elsewhere (TestInfrastructureConfig.java), so you
 *   will need to define a constructor for this class
 *   that accepts a DataSource parameter.
 * - As it is the only constructor, @Autowired is optional.
 *
 * TODO-04: Implement each @Bean method to contain the code
 *          needed to instantiate its object and set its
 *          dependencies
 * - You can create beans from the following implementation classes
 *   - rewardNetwork bean from RewardNetworkImpl class
 *   - accountRepository bean from JdbcAccountRepository class
 *   - restaurantRepository bean from JdbcRestaurantRepository class
 *   - rewardRepository bean from JdbcRewardRepository class
 * - Note that return type of each bean method should be an interface
 *   not an implementation.
 */

// TODO-01 make this class a spring configuration class
@Configuration
public class RewardsConfig {

	// Set this by adding a constructor.
	private DataSource dataSource;

	// TODO-03 Inject DataSource through constructor injection
	// Autowired is optional as it is only constructor
	@Autowired
	public RewardsConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// TODO-02 Defining empty bean methods
	// TODO-02 - rewardNetwork
	@Bean
	public RewardNetwork rewardNetwork() {
		return new RewardNetworkImpl(accountRepository(), restaurantRepository(), rewardRepository());
	}

	// TODO-02 - accountRepository
	@Bean
	public AccountRepository accountRepository() {
		// TODO-04 instantiate JdbcAccountRepository
		JdbcAccountRepository jdbcAccountRepository = new JdbcAccountRepository();
		jdbcAccountRepository.setDataSource(dataSource);
		return jdbcAccountRepository;
	}

	// TODO-02 - restaurantRepository
	@Bean
	public RestaurantRepository restaurantRepository() {
		// TODO-04 Instantiate JdbcRestaurantRepository
		JdbcRestaurantRepository jdbcRestaurantRepository = new JdbcRestaurantRepository();
		jdbcRestaurantRepository.setDataSource(dataSource);
		return jdbcRestaurantRepository;
	}

	// TODO-02 - rewardRepository
	@Bean
	public RewardRepository rewardRepository() {
		// TODO-04 instantiate JdbcRewardRepository
		JdbcRewardRepository jdbcRewardRepository = new JdbcRewardRepository();
		jdbcRewardRepository.setDataSource(dataSource);
		return jdbcRewardRepository;
	}

}
