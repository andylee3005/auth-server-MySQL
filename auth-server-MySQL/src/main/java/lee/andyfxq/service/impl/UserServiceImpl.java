package lee.andyfxq.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;
import lee.andyfxq.model.User;
import lee.andyfxq.repository.UserRepository;
import lee.andyfxq.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User _save(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public void removeById(Long id) {
		userRepository.deleteById(id);
		// need to delete portfolios and stocks associated
	}

	@Override
	public List<Portfolio> getPortfolioByUID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> getStockByUID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
