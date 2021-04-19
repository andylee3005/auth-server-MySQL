package lee.andyfxq.service;

import java.util.List;
import java.util.Optional;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;
import lee.andyfxq.model.User;

public interface UserService {
	
	List<User> getAll();
	Optional<User> getById(Long id);
	User _save(User user);
	boolean checkEmail(String email);
	void removeById(Long id);
	List<Portfolio> getPortfolioByUID(Long id);
	List<Stock> getStockByUID(Long id);

}
